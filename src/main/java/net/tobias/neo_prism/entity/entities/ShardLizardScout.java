package net.tobias.neo_prism.entity.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.tobias.neo_prism.item.ModArmorMaterials;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.entity.ai.shard_lizards.goals.ShardLizardScoutAttackGoal;
import net.tobias.neo_prism.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShardLizardScout extends Monster implements InventoryCarrier {
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(ShardLizardScout.class, EntityDataSerializers.INT);
    private static final int VARIANT_COUNT = 9;
    private final SimpleContainer inventory = new SimpleContainer(3);
    private static final Logger LOGGER = LoggerFactory.getLogger(ShardLizardScout.class);
    private static final EntityDataAccessor<Boolean> CUSTOM_DYING =
            SynchedEntityData.defineId(ShardLizardScout.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SHEDING_SCALE =
            SynchedEntityData.defineId(ShardLizardScout.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_ATTACKING =
            SynchedEntityData.defineId(ShardLizardScout.class, EntityDataSerializers.BOOLEAN);
    private static final int CUSTOM_DEATH_ANIMATION_TICKS = 40;
    private static final int SHED_SCALE_ANIMATION_TICKS = 60;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();
    public final AnimationState shedScaleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private int customDeathAnimationTicks = 0;
    private int shedScaleAnimationTicks = 0;
    private boolean shouldDropLizardScale = false;
    public int attackAnimationTicks = 0;

    public ShardLizardScout(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        setCanPickUpLoot(true);
        xpReward = 10;
    }

    public void setIsAttacking(boolean isAttacking) {
        entityData.set(IS_ATTACKING, isAttacking);
    }

    public boolean isAttacking() {
        return entityData.get(IS_ATTACKING);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(VARIANT, 0);
        entityData.define(CUSTOM_DYING, false);
        entityData.define(SHEDING_SCALE, false);
        entityData.define(IS_ATTACKING, false);
    }

    public int getVariant() {
        return Mth.clamp(entityData.get(VARIANT), 0, VARIANT_COUNT - 1);
    }

    public void setVariant(int variant) {
        entityData.set(VARIANT, Mth.clamp(variant, 0, VARIANT_COUNT - 1));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType,
                                        SpawnGroupData spawnGroupData, CompoundTag tag) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, tag);
        setVariant(random.nextInt(VARIANT_COUNT));
        return data;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Variant", Tag.TAG_INT)) {
            setVariant(tag.getInt("Variant"));
        }
    }

    public boolean isCustomDying() {
        return entityData.get(CUSTOM_DYING);
    }

    public boolean isShedingScale() {
        return entityData.get(SHEDING_SCALE);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new ShardLizardScoutAttackGoal(this, 1.5f, true));
        goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        goalSelector.addGoal(3, new RandomLookAroundGoal(this));

        targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 32, true, false,
                target -> target instanceof Player player
                        && (getTarget() == player
                        || getLastHurtByMob() == player
                        || !isWearingLizardScaleFullSet(player))));
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        if (target instanceof Player player) {
            if (getTarget() == target || getLastHurtByMob() == player) {
                return true;
            }
            if (isWearingLizardScaleFullSet(player)) {
                return false;
            }
        }
        return target != null && !target.is(this);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean hurt = super.hurt(source, amount);
        if (!level().isClientSide) {
            alertNearbyLizardEntities(source.getEntity() instanceof LivingEntity living ? living : null);
        }
        return hurt;
    }

    private void alertNearbyLizardEntities(LivingEntity attacker) {
        AABB alertBox = new AABB(getX() - 20.0D, getY() - 10.0D, getZ() - 20.0D,
                getX() + 20.0D, getY() + 10.0D, getZ() + 20.0D);

        if (attacker == null) {
            return;
        }

        for (ShardLizardScout scout : level().getEntitiesOfClass(ShardLizardScout.class, alertBox,
                other -> other != this)) {
            scout.setTarget(attacker);
            scout.setLastHurtByMob(attacker);
        }

        for (ShardLizardCharger charger : level().getEntitiesOfClass(ShardLizardCharger.class, alertBox,
                other -> !other.is(this))) {
            charger.setTarget(attacker);
            charger.setLastHurtByMob(attacker);
        }
    }

    private static boolean isWearingLizardScaleFullSet(Player player) {
        ItemStack helmet = player.getInventory().getArmor(3);
        ItemStack chestplate = player.getInventory().getArmor(2);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack boots = player.getInventory().getArmor(0);

        if (helmet.isEmpty() || chestplate.isEmpty() || leggings.isEmpty() || boots.isEmpty()) {
            return false;
        }

        return armorIsLizardScale(helmet) && armorIsLizardScale(chestplate)
                && armorIsLizardScale(leggings) && armorIsLizardScale(boots);
    }

    private static boolean armorIsLizardScale(ItemStack stack) {
        if (!(stack.getItem() instanceof ArmorItem armorItem)) {
            return false;
        }
        return armorItem.getMaterial() == ModArmorMaterials.LIZARD_SCALE;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50f)
                .add(Attributes.ARMOR, 5f)
                .add(Attributes.ARMOR_TOUGHNESS, 2f)
                .add(Attributes.ATTACK_DAMAGE, 5f)
                .add(Attributes.ATTACK_SPEED, 2f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.0f)
                .add(Attributes.FOLLOW_RANGE, 50f)
                .add(Attributes.JUMP_STRENGTH, 3f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 3f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f);
    }

    @Override
    public SimpleContainer getInventory() {
        return inventory;
    }

    protected void pickUpItem(ItemEntity itemEntity) {
        ItemStack item = itemEntity.getItem();
        if (wantsToPickUp(item)) {
            SimpleContainer container = getInventory();
            if (!container.canAddItem(item)) {
                return;
            }

            onItemPickup(itemEntity);
            int count = item.getCount();
            ItemStack itemStack = container.addItem(item);
            take(itemEntity, count - itemStack.getCount());
            if (itemStack.isEmpty()) {
                itemEntity.discard();
            } else {
                item.setCount(itemStack.getCount());
            }
        }
    }

    public boolean wantsToPickUp(ItemStack item) {
        boolean isShard = false;
        if (item.isEmpty()) {
            return false;
        } else {
            for (int possibleShards = 0; possibleShards < ModItems.getShards().size(); possibleShards++) {
                if (item.is(ModItems.getShards().get(possibleShards))) {
                    isShard = true;
                    break;
                }
            }
            return isShard;
        }
    }

    public void tick() {
        super.tick();

        LivingEntity target = getTarget();
        if (target instanceof Player player && isWearingLizardScaleFullSet(player)
                && getTarget() != player && getLastHurtByMob() != player) {
            setTarget(null);
        }

        if (level().isClientSide) {
            setUpAnimationStates();
        }

        if (level().isClientSide || isCustomDying()) {
            return;
        }

        if (isShedingScale()) {
            ++shedScaleAnimationTicks;
            if (shedScaleAnimationTicks >= SHED_SCALE_ANIMATION_TICKS) {
                entityData.set(SHEDING_SCALE, false);
                shedScaleAnimationTicks = 0;
                if (shouldDropLizardScale) {
                    shouldDropLizardScale = false;
                    ItemStack drop = new ItemStack(ModItems.ALL.get("lizard_scale").get());
                    ItemEntity itemEntity = new ItemEntity(level(),
                            position().x + 0.5, position().y + 1.0,
                            position().z + 0.5, drop);
                    level().addFreshEntity(itemEntity);
                    setSpeed(0.3f);
                }
            }
            return;
        }

        int totalItemCount = 0;
        for (int cnt = 0; cnt < inventory.getContainerSize(); cnt++) {
            totalItemCount += inventory.getItem(cnt).getCount();
        }
        if (totalItemCount >= 10) {
            int itemsRemoved = 0;
            for (int cnt = 0; cnt < inventory.getContainerSize(); cnt++) {
                int leftToRemove = 10 - itemsRemoved;
                int itemCount = inventory.getItem(cnt).getCount();
                int remove = 0;
                if (leftToRemove >= itemCount) {
                    remove = itemCount;
                } else {
                    remove = leftToRemove;
                }
                inventory.removeItem(cnt, remove);
                itemsRemoved += remove;

                if (itemsRemoved == 10) {
                    setSpeed(0.0f);
                    entityData.set(SHEDING_SCALE, true);
                    shedScaleAnimationTicks = 0;
                    shouldDropLizardScale = true;
                    break;
                }
            }

        }
    }

    private void setUpAnimationStates() {
        if (isCustomDying()) {
            idleAnimationState.stop();
            shedScaleAnimationState.stop();
            if (!deathAnimationState.isStarted()) {
                deathAnimationState.start(tickCount);
            }
            return;
        }

        deathAnimationState.stop();
        if (isShedingScale()) {
            idleAnimationState.stop();
            if (!shedScaleAnimationState.isStarted()) {
                shedScaleAnimationState.start(tickCount);
            }
            return;
        }

        shedScaleAnimationState.stop();

        if (isAttacking()) {
            idleAnimationState.stop();
            if (!attackAnimationState.isStarted()) {
                attackAnimationState.start(tickCount);
            }
            return;
        }

        attackAnimationState.stop();

        if (getDeltaMovement().horizontalDistanceSqr() > 0.0001D) {
            idleAnimationState.stop();
            return;
        }

        if (!idleAnimationState.isStarted()) {
            idleAnimationState.start(tickCount);
        }
    }

    @Override
    public void die(DamageSource pDamageSource) {
        if (!level().isClientSide() && !isCustomDying()) {
            entityData.set(CUSTOM_DYING, true);
            customDeathAnimationTicks = 0;
            setNoAi(true);
            setNoGravity(true);
            setDeltaMovement(Vec3.ZERO);
            setHealth(0.0F);
            return;
        }
        super.die(pDamageSource);
    }

    @Override
    protected void tickDeath() {
        if (isCustomDying()) {
            ++deathTime;
            ++customDeathAnimationTicks;

            if (customDeathAnimationTicks >= CUSTOM_DEATH_ANIMATION_TICKS && !level().isClientSide()) {
                dropAllDeathLoot(damageSources().generic());
                remove(RemovalReason.KILLED);
            }
            return;
        }

        super.tickDeath();
    }

    @Override
    protected void updateWalkAnimation(float partialTick) {
        float f;
        if (getPose() == Pose.STANDING) {
            f = Math.min(partialTick * 6f, 1f);
        } else {
            f = 0;
        }
        walkAnimation.update(f, 0.2f);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource damageSource, int lootingLevel, boolean recentlyHit) {
        super.dropCustomDeathLoot(damageSource, lootingLevel, recentlyHit);

        if (!level().isClientSide) {
            Containers.dropContents(level(), this, inventory);
            inventory.clearContent();
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.CAMEL_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.TURTLE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.TURTLE_HURT;
    }

    @Override
    public ResourceLocation getDefaultLootTable() {
        return new ResourceLocation(Neo_Prism.MOD_ID, "entities/shard_lizards/scout");
    }

    @Override
    public boolean isPreventingPlayerRest(Player player) {
        return false;
    }
}