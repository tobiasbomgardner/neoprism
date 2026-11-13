package net.tobias.neo_prism.entity.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.entity.ai.shard_lizards.goals.ShardLizardThrasherAttackGoal;

public class ShardLizardThrasher extends Monster {
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(ShardLizardThrasher.class, EntityDataSerializers.INT);
    private static final int VARIANT_COUNT = 9;
    private static final EntityDataAccessor<Boolean> CUSTOM_DYING =
            SynchedEntityData.defineId(ShardLizardThrasher.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_ATTACKING =
            SynchedEntityData.defineId(ShardLizardThrasher.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ATTACK_TYPE =
            SynchedEntityData.defineId(ShardLizardThrasher.class, EntityDataSerializers.INT);
    public static final int ATTACK_SPIN = 0;
    public static final int ATTACK_SLAM = 1;
    public static final int ATTACK_COMBO = 2;
    private static final int CUSTOM_DEATH_ANIMATION_TICKS = 124;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState spinAnimationState = new AnimationState();
    public final AnimationState slamAnimationState = new AnimationState();
    public final AnimationState comboAnimationState = new AnimationState();
    private int customDeathAnimationTicks = 0;
    public int attackAnimationTicks = 0;
    private boolean attackDamageApplied = false;
    private float lastComboDamageTicksReached = -1.0f;

    public ShardLizardThrasher(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        xpReward = 30;
    }

    public void setIsAttacking(boolean isAttacking) {
        boolean wasAttacking = entityData.get(IS_ATTACKING);
        if (wasAttacking != isAttacking) {
            entityData.set(IS_ATTACKING, isAttacking);
        }
        attackAnimationTicks = 0;
        attackDamageApplied = false;
        lastComboDamageTicksReached = -1.0f;
    }

    public void startAttackAnimation() {
        float requiredHealthPercent = getHealth() / getMaxHealth();
        boolean canUseCombo = requiredHealthPercent <= 0.5f;

        if (canUseCombo) {
            setAttackType(getRandom().nextInt(3));
        } else {
            setAttackType(getRandom().nextInt(2));
        }
        attackAnimationTicks = 0;
        attackDamageApplied = false;
        lastComboDamageTicksReached = -1.0f;
        setIsAttacking(true);
    }

    public boolean hasAttackDamageApplied() {
        return attackDamageApplied;
    }

    public void setAttackDamageApplied(boolean attackDamageApplied) {
        this.attackDamageApplied = attackDamageApplied;
    }

    public float getLastComboDamageTicksReached() {
        return lastComboDamageTicksReached;
    }

    public void setLastComboDamageTicksReached(float lastComboDamageTicksReached) {
        this.lastComboDamageTicksReached = lastComboDamageTicksReached;
    }

    public float[] getAttackDamageTick() {
        return switch (getAttackType()) {
            case ATTACK_SPIN -> new float[] {64.0f};
            case ATTACK_SLAM -> new float[] {32.5f};
            case ATTACK_COMBO -> new float[] {
                    66.0f, 76.0f, 86.0f, 96.0f, 105.0f, 115.0f, 124.0f, 125.0f, 132.0f,
                    133.0f, 139.0f, 140.0f, 148.0f, 149.0f, 155.0f, 156.0f, 172.0f
            };
            default -> new float[] {};
        };
    }

    public float getAttackAnimationDurationTicks() {
        return switch (getAttackType()) {
            case ATTACK_SPIN -> 80.0f;
            case ATTACK_SLAM -> 70.0f;
            case ATTACK_COMBO -> 175.0f;
            default -> 0.0f;
        };
    }

    public float[] getCurrentAttackDamage() {
        return switch (getAttackType()) {
            case ATTACK_SPIN -> new float[] {5.0f};
            case ATTACK_SLAM -> new float[] {10.0f};
            case ATTACK_COMBO -> new float[] {
                    5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 7.0f, 7.5f, 7.5f, 7.5f, 7.5f, 7.5f, 7.5f, 7.5f, 7.5f, 7.5f, 7.5f, 10.0f
            };
            default -> new float[] {};
        };
    }

    public boolean isAttacking() {
        return entityData.get(IS_ATTACKING);
    }

    public int getAttackType() {
        return entityData.get(ATTACK_TYPE);
    }

    public void setAttackType(int attackType) {
        entityData.set(ATTACK_TYPE, Mth.clamp(attackType, ATTACK_SPIN, ATTACK_COMBO));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(VARIANT, 0);
        entityData.define(CUSTOM_DYING, false);
        entityData.define(IS_ATTACKING, false);
        entityData.define(ATTACK_TYPE, ATTACK_SPIN);
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
        tag.putInt("AttackType", getAttackType());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Variant", Tag.TAG_INT)) {
            setVariant(tag.getInt("Variant"));
        }
        if (tag.contains("AttackType", Tag.TAG_INT)) {
            setAttackType(tag.getInt("AttackType"));
        }
    }

    public boolean isCustomDying() {
        return entityData.get(CUSTOM_DYING);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new ShardLizardThrasherAttackGoal(this, 1.5f, true));
        goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        goalSelector.addGoal(3, new RandomLookAroundGoal(this));

        targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 100f)
                .add(Attributes.ARMOR, 20f)
                .add(Attributes.ARMOR_TOUGHNESS, 10f)
                .add(Attributes.ATTACK_DAMAGE, 7f)
                .add(Attributes.ATTACK_SPEED, 1f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.0f)
                .add(Attributes.FOLLOW_RANGE, 50f)
                .add(Attributes.JUMP_STRENGTH, 3f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 5f)
                .add(Attributes.MOVEMENT_SPEED, 0.1f);
    }

    public boolean advanceAttackAnimation() {
        if (!isAttacking()) {
            return false;
        }

        ++attackAnimationTicks;

        if (attackAnimationTicks < getAttackAnimationDurationTicks()) {
            return false;
        }

        setIsAttacking(false);
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (level().isClientSide) {
            setUpAnimationStates();
            return;
        }

        if (isCustomDying()) {
            return;
        }

        if (isAttacking()) {
            advanceAttackAnimation();
        }
    }

    public void setUpAnimationStates() {
        if (isCustomDying()) {
            idleAnimationState.stop();
            spinAnimationState.stop();
            slamAnimationState.stop();
            comboAnimationState.stop();
            if (!deathAnimationState.isStarted()) {
                deathAnimationState.start(tickCount);
            }
            return;
        }

        deathAnimationState.stop();

        if (isAttacking()) {
            idleAnimationState.stop();

            switch (getAttackType()) {
                case ATTACK_SLAM:
                    spinAnimationState.stop();
                    comboAnimationState.stop();
                    if (!slamAnimationState.isStarted()) {
                        slamAnimationState.start(tickCount);
                    }
                    break;
                case ATTACK_COMBO:
                    spinAnimationState.stop();
                    slamAnimationState.stop();
                    if (!comboAnimationState.isStarted()) {
                        comboAnimationState.start(tickCount);
                    }
                    break;
                case ATTACK_SPIN:
                default:
                    slamAnimationState.stop();
                    comboAnimationState.stop();
                    if (!spinAnimationState.isStarted()) {
                        spinAnimationState.start(tickCount);
                    }
                    break;
            }
            return;
        }

        spinAnimationState.stop();
        slamAnimationState.stop();
        comboAnimationState.stop();
        attackAnimationState.stop();

        if (getDeltaMovement().horizontalDistanceSqr() > 0.0001d) {
            idleAnimationState.stop();
            return;
        }

        if (!idleAnimationState.isStarted()) {
            idleAnimationState.start(tickCount);
        }
    }

    @Override
    public void die(DamageSource source) {
        if (!level().isClientSide() && !isCustomDying()) {
            entityData.set(CUSTOM_DYING, true);
            customDeathAnimationTicks = 0;
            setNoAi(true);
            setNoGravity(true);
            setDeltaMovement(Vec3.ZERO);
            setHealth(0.0f);
            return;
        }
        super.die(source);
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
    protected SoundEvent getAmbientSound() {
        return SoundEvents.CAMEL_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.TURTLE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.TURTLE_HURT;
    }

    @Override
    public ResourceLocation getDefaultLootTable() {
        return new ResourceLocation(Neo_Prism.MOD_ID, "entities/shard_lizards/thrasher");
    }

    @Override
    public boolean isPreventingPlayerRest(Player player) {
        return false;
    }
}