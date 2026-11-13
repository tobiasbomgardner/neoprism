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
import net.tobias.neo_prism.effects.ModEffects;
import net.tobias.neo_prism.entity.ai.shard_lizards.goals.ShardLizardSlasherAttackGoal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShardLizardSlasher extends Monster {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShardLizardSlasher.class);
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(ShardLizardSlasher.class, EntityDataSerializers.INT);
    private static final int VARIANT_COUNT = 9;
    private static final EntityDataAccessor<Boolean> CUSTOM_DYING =
            SynchedEntityData.defineId(ShardLizardSlasher.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_ATTACKING =
            SynchedEntityData.defineId(ShardLizardSlasher.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ATTACK_TYPE =
            SynchedEntityData.defineId(ShardLizardSlasher.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COMBO_STEP =
            SynchedEntityData.defineId(ShardLizardSlasher.class, EntityDataSerializers.INT);
    public static final int ATTACK_RIGHT_SLASH = 0;
    public static final int ATTACK_LEFT_SLASH = 1;
    public static final int ATTACK_SLAM_SLASH = 2;
    public static final int ATTACK_COMBO = 3;
    private static final int COMBO_STEP_LEFT = 0;
    private static final int COMBO_STEP_RIGHT = 1;
    private static final int COMBO_STEP_SLAM = 2;
    private static final int CUSTOM_DEATH_ANIMATION_TICKS = 35;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState rightSlashAnimationState = new AnimationState();
    public final AnimationState leftSlashAnimationState = new AnimationState();
    public final AnimationState slamSlashAnimationState = new AnimationState();
    private int customDeathAnimationTicks = 0;
    public int attackAnimationTicks = 0;
    private boolean attackDamageApplied = false;

    public ShardLizardSlasher(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        xpReward = 20;
    }

    public void setIsAttacking(boolean isAttacking) {
        if (entityData.get(IS_ATTACKING) != isAttacking) {
            entityData.set(IS_ATTACKING, isAttacking);
        }
        attackAnimationTicks = 0;
        attackDamageApplied = false;
        if (!isAttacking) {
            setComboStep(COMBO_STEP_LEFT);
        }
    }

    public void startAttackAnimation() {
        LivingEntity target = getTarget();
        boolean isTargetParalyzed = target != null && target.hasEffect(ModEffects.PARALYSIS.get());
        if (isTargetParalyzed) {
            setAttackType(getRandom().nextInt(4));
        } else {
            setAttackType(getRandom().nextInt(3));
        }
        setComboStep(COMBO_STEP_LEFT);
        setIsAttacking(true);
    }

    public boolean tryForcedComboOnParalyzedTarget() {
        LivingEntity target = getTarget();
        if (!isAttacking() || target == null) return false;
        if (getAttackType() == ATTACK_COMBO) return false;
        if (!target.hasEffect(ModEffects.PARALYSIS.get())) return false;

        setAttackType(ATTACK_COMBO);
        setComboStep(COMBO_STEP_LEFT);
        attackAnimationTicks = 0;
        attackDamageApplied = false;
        return true;
    }

    public boolean hasAttackDamageApplied() {
        return attackDamageApplied;
    }

    public void setAttackDamageApplied(boolean attackDamageApplied) {
        this.attackDamageApplied = attackDamageApplied;
    }

    public int getAttackDamageTick() {
        if (getActiveAttackType() == ATTACK_LEFT_SLASH || getActiveAttackType() == ATTACK_RIGHT_SLASH || getActiveAttackType() == ATTACK_SLAM_SLASH) {
            return 20;
        } else {
            throw new IllegalStateException("Unexpected value: " + getActiveAttackType());
        }
    }

    public int getAttackAnimationDurationTicks() {
        if (getActiveAttackType() == ATTACK_LEFT_SLASH || getActiveAttackType() == ATTACK_RIGHT_SLASH || getActiveAttackType() == ATTACK_SLAM_SLASH) {
            return 45;
        } else {
            throw new IllegalStateException("Unexpected value: " + getActiveAttackType());
        }
    }

    public float getCurrentAttackDamage() {
        float baseDamage = switch (getActiveAttackType()) {
            case ATTACK_SLAM_SLASH -> 7.0f;
            case ATTACK_LEFT_SLASH, ATTACK_RIGHT_SLASH -> 5.0f;
            default -> throw new IllegalStateException("Unexpected value: " + getActiveAttackType());
        };

        if (getAttackType() == ATTACK_COMBO) {
            return baseDamage * 3.0f;
        }

        return baseDamage;
    }

    public boolean isAttacking() {
        return entityData.get(IS_ATTACKING);
    }

    public int getAttackType() {
        return entityData.get(ATTACK_TYPE);
    }

    public int getComboStep() {
        return entityData.get(COMBO_STEP);
    }

    public void setComboStep(int comboStep) {
        entityData.set(COMBO_STEP, Mth.clamp(comboStep, COMBO_STEP_LEFT, COMBO_STEP_SLAM));
    }

    public int getActiveAttackType() {
        if (getAttackType() != ATTACK_COMBO) {
            return getAttackType();
        }

        return switch (getComboStep()) {
            case COMBO_STEP_LEFT -> ATTACK_LEFT_SLASH;
            case COMBO_STEP_RIGHT -> ATTACK_RIGHT_SLASH;
            case COMBO_STEP_SLAM -> ATTACK_SLAM_SLASH;
            default -> ATTACK_LEFT_SLASH;
        };
    }

    public boolean isComboInProgress() {
        return isAttacking() && getAttackType() == ATTACK_COMBO;
    }

    public boolean advanceAttackAnimation() {
        ++attackAnimationTicks;

        if (attackAnimationTicks < getAttackAnimationDurationTicks()) {
            return false;
        }

        if (getAttackType() == ATTACK_COMBO && getComboStep() < COMBO_STEP_SLAM) {
            setComboStep(getComboStep() + 1);
            attackAnimationTicks = 0;
            attackDamageApplied = false;
            return false;
        }

        setIsAttacking(false);
        return true;
    }

    public void setAttackType(int attackType) {
        entityData.set(ATTACK_TYPE, Mth.clamp(attackType, ATTACK_RIGHT_SLASH, ATTACK_COMBO));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(VARIANT, 0);
        entityData.define(CUSTOM_DYING, false);
        entityData.define(IS_ATTACKING, false);
        entityData.define(ATTACK_TYPE, ATTACK_RIGHT_SLASH);
        entityData.define(COMBO_STEP, COMBO_STEP_LEFT);
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
        tag.putInt("ComboStep", getComboStep());
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
        if (tag.contains("ComboStep", Tag.TAG_INT)) {
            setComboStep(tag.getInt("ComboStep"));
        }
    }

    public boolean isCustomDying() {
        return this.entityData.get(CUSTOM_DYING);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new ShardLizardSlasherAttackGoal(this, 1.5f, true));
        goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        goalSelector.addGoal(3, new RandomLookAroundGoal(this));

        targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 60f)
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
    public void tick() {
        super.tick();

        if (level().isClientSide) {
            setUpAnimationStates();
        }

        if (level().isClientSide || isCustomDying()) {
            return;
        }
    }

    private void setUpAnimationStates() {
        if (isCustomDying()) {
            idleAnimationState.stop();
            rightSlashAnimationState.stop();
            leftSlashAnimationState.stop();
            slamSlashAnimationState.stop();
            if (!deathAnimationState.isStarted()) {
                deathAnimationState.start(tickCount);
            }
            return;
        }

        deathAnimationState.stop();

        if (isAttacking()) {
            idleAnimationState.stop();

            switch (getActiveAttackType()) {
                case ATTACK_LEFT_SLASH:
                    rightSlashAnimationState.stop();
                    slamSlashAnimationState.stop();
                    if (!leftSlashAnimationState.isStarted()) {
                        leftSlashAnimationState.start(tickCount);
                    }
                    break;
                case ATTACK_SLAM_SLASH:
                    rightSlashAnimationState.stop();
                    leftSlashAnimationState.stop();
                    if (!slamSlashAnimationState.isStarted()) {
                        slamSlashAnimationState.start(tickCount);
                    }
                    break;
                case ATTACK_RIGHT_SLASH:
                default:
                    leftSlashAnimationState.stop();
                    slamSlashAnimationState.stop();
                    if (!rightSlashAnimationState.isStarted()) {
                        rightSlashAnimationState.start(tickCount);
                    }
                    break;
            }
            return;
        }

        rightSlashAnimationState.stop();
        leftSlashAnimationState.stop();
        slamSlashAnimationState.stop();
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
    public void die(DamageSource damageSource) {
        if (!level().isClientSide() && !isCustomDying()) {
            entityData.set(CUSTOM_DYING, true);
            customDeathAnimationTicks = 0;
            setNoAi(true);
            setNoGravity(true);
            setDeltaMovement(Vec3.ZERO);
            setHealth(0.0f);
            return;
        }
        super.die(damageSource);
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
        return new ResourceLocation(Neo_Prism.MOD_ID, "entities/shard_lizards/slasher");
    }

    @Override
    public boolean isPreventingPlayerRest(Player player) {
        return false;
    }
}