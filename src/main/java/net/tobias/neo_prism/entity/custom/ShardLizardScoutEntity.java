package net.tobias.neo_prism.entity.custom;

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
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.entity.ai.shard_lizards.goals.ShardLizardScoutAttackGoal;
import net.tobias.neo_prism.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShardLizardScoutEntity extends Monster implements InventoryCarrier {
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(ShardLizardScoutEntity.class, EntityDataSerializers.INT);
    private static final int VARIANT_COUNT = 9;
    private final SimpleContainer inventory = new SimpleContainer(3);
    private static final Logger LOGGER = LoggerFactory.getLogger(ShardLizardScoutEntity.class);
    private static final EntityDataAccessor<Boolean> CUSTOM_DYING =
            SynchedEntityData.defineId(ShardLizardScoutEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SHEDING_SCALE =
            SynchedEntityData.defineId(ShardLizardScoutEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_ATTACKING =
            SynchedEntityData.defineId(ShardLizardScoutEntity.class, EntityDataSerializers.BOOLEAN);
    private static final int CUSTOM_DEATH_ANIMATION_TICKS = 40;
    private static final int SHED_SCALE_ANIMATION_TICKS = 60;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();
    public final AnimationState shedScaleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private int idleAnimationTicks = 0;
    private int customDeathAnimationTicks = 0;
    private int shedScaleAnimationTicks = 0;
    private boolean shouldDropLizardScale = false;
    public int attackAnimationTicks = 0;

    public ShardLizardScoutEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setCanPickUpLoot(true);
        this.xpReward = 10;
    }

    public void setIsAttacking(boolean isAttacking) {
        this.entityData.set(IS_ATTACKING, isAttacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(IS_ATTACKING);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(CUSTOM_DYING, false);
        this.entityData.define(SHEDING_SCALE, false);
        this.entityData.define(IS_ATTACKING, false);
    }

    public int getVariant() {
        return Mth.clamp(this.entityData.get(VARIANT), 0, VARIANT_COUNT - 1);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, Mth.clamp(variant, 0, VARIANT_COUNT - 1));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType,
                                        SpawnGroupData spawnGroupData, CompoundTag tag) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, tag);
        this.setVariant(this.random.nextInt(VARIANT_COUNT));
        return data;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Variant", Tag.TAG_INT)) {
            this.setVariant(tag.getInt("Variant"));
        }
    }

    public boolean isCustomDying() {
        return this.entityData.get(CUSTOM_DYING);
    }

    public boolean isShedingScale() {
        return this.entityData.get(SHEDING_SCALE);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ShardLizardScoutAttackGoal(this, 1.5f, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50f)
                .add(Attributes.ARMOR, 5f)
                .add(Attributes.ARMOR_TOUGHNESS, 2f)
                .add(Attributes.ATTACK_DAMAGE, 5f)
                .add(Attributes.ATTACK_SPEED, 2f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1f)
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
        if (this.wantsToPickUp(item)) {
            SimpleContainer container = this.getInventory();
            if (!container.canAddItem(item)) {
                return;
            }

            this.onItemPickup(itemEntity);
            int count = item.getCount();
            ItemStack itemStack = container.addItem(item);
            this.take(itemEntity, count - itemStack.getCount());
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

        if (this.level().isClientSide) {
            setUpAnimationStates();
        }

        if (this.level().isClientSide || this.isCustomDying()) {
            return;
        }

        if (this.isShedingScale()) {
            ++this.shedScaleAnimationTicks;
            if (this.shedScaleAnimationTicks >= SHED_SCALE_ANIMATION_TICKS) {
                this.entityData.set(SHEDING_SCALE, false);
                this.shedScaleAnimationTicks = 0;
                if (this.shouldDropLizardScale) {
                    this.shouldDropLizardScale = false;
                    ItemStack drop = new ItemStack(ModItems.ALL.get("lizard_scale").get());
                    ItemEntity itemEntity = new ItemEntity(this.level(),
                            position().x + 0.5, position().y + 1.0,
                            position().z + 0.5, drop);
                    this.level().addFreshEntity(itemEntity);
                    this.setSpeed(0.3f);
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
                    this.setSpeed(0.0f);
                    this.entityData.set(SHEDING_SCALE, true);
                    this.shedScaleAnimationTicks = 0;
                    this.shouldDropLizardScale = true;
                    break;
                }
            }

        }
    }

    private void setUpAnimationStates() {
        if (this.isCustomDying()) {
            this.idleAnimationState.stop();
            this.shedScaleAnimationState.stop();
            if (!this.deathAnimationState.isStarted()) {
                this.deathAnimationState.start(this.tickCount);
            }
            return;
        }

        this.deathAnimationState.stop();
        if (this.isShedingScale()) {
            this.idleAnimationState.stop();
            if (!this.shedScaleAnimationState.isStarted()) {
                this.shedScaleAnimationState.start(this.tickCount);
            }
            return;
        }

        this.shedScaleAnimationState.stop();

        if (this.idleAnimationTicks <= 0) {
            this.idleAnimationTicks = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTicks;
        }

        if (this.isAttacking() && attackAnimationTicks <= 0) {
            attackAnimationTicks = 20;
            attackAnimationState.start(this.tickCount);
        } else {
            --this.attackAnimationTicks;
        }

        if (!this.isAttacking()) {
            attackAnimationState.stop();
        }
    }

    @Override
    public void die(DamageSource pDamageSource) {
        if (!this.level().isClientSide() && !this.isCustomDying()) {
            this.entityData.set(CUSTOM_DYING, true);
            this.customDeathAnimationTicks = 0;
            this.setNoAi(true);
            this.setNoGravity(true);
            this.setDeltaMovement(Vec3.ZERO);
        }
        super.die(pDamageSource);
    }

    @Override
    protected void tickDeath() {
        if (this.isCustomDying()) {
            ++this.deathTime;
            ++this.customDeathAnimationTicks;

            if (this.customDeathAnimationTicks >= CUSTOM_DEATH_ANIMATION_TICKS && !this.level().isClientSide()) {
                this.remove(RemovalReason.KILLED);
            }
            return;
        }

        super.tickDeath();
    }

    @Override
    protected void updateWalkAnimation(float partialTick) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(partialTick * 6f, 1f);
        } else {
            f = 0;
        }
        this.walkAnimation.update(f, 0.2f);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource damageSource, int lootingLevel, boolean recentlyHit) {
        super.dropCustomDeathLoot(damageSource, lootingLevel, recentlyHit);

        if (!this.level().isClientSide) {
            Containers.dropContents(this.level(), this, this.inventory);
            this.inventory.clearContent();
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SILVERFISH_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLAZE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.CAMEL_HURT;
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