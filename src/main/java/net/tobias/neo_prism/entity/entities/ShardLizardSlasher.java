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
    private DamageSource lastDamageSource = null;
    public int attackAnimationTicks = 0;
    private boolean attackDamageApplied = false;

    public ShardLizardSlasher(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 20;
    }

    public void setIsAttacking(boolean isAttacking) {
        if (this.entityData.get(IS_ATTACKING) != isAttacking) {
            this.entityData.set(IS_ATTACKING, isAttacking);
        }
        this.attackAnimationTicks = 0;
        this.attackDamageApplied = false;
        if (!isAttacking) {
            this.setComboStep(COMBO_STEP_LEFT);
        }
    }

    public void startAttackAnimation() {
        this.setAttackType(this.getRandom().nextInt(4));
        this.setComboStep(COMBO_STEP_LEFT);
        this.setIsAttacking(true);
    }

    public boolean hasAttackDamageApplied() {
        return this.attackDamageApplied;
    }

    public void setAttackDamageApplied(boolean attackDamageApplied) {
        this.attackDamageApplied = attackDamageApplied;
    }

    public int getAttackDamageTick() {
        if (this.getActiveAttackType() == ATTACK_LEFT_SLASH || this.getActiveAttackType() == ATTACK_RIGHT_SLASH || this.getActiveAttackType() == ATTACK_SLAM_SLASH) {
            return 20;
        } else {
            throw new IllegalStateException("Unexpected value: " + this.getActiveAttackType());
        }
    }

    public int getAttackAnimationDurationTicks() {
        if (this.getActiveAttackType() == ATTACK_LEFT_SLASH || this.getActiveAttackType() == ATTACK_RIGHT_SLASH || this.getActiveAttackType() == ATTACK_SLAM_SLASH) {
            return 45;
        } else {
            throw new IllegalStateException("Unexpected value: " + this.getActiveAttackType());
        }
    }

    public float getCurrentAttackDamage() {
        float baseDamage = switch (this.getActiveAttackType()) {
            case ATTACK_SLAM_SLASH -> 7.0F;
            case ATTACK_LEFT_SLASH, ATTACK_RIGHT_SLASH -> 5.0F;
            default -> throw new IllegalStateException("Unexpected value: " + this.getActiveAttackType());
        };

        if (this.getAttackType() == ATTACK_COMBO) {
            return baseDamage * 3.0F;
        }

        return baseDamage;
    }

    public boolean isAttacking() {
        return this.entityData.get(IS_ATTACKING);
    }

    public int getAttackType() {
        return this.entityData.get(ATTACK_TYPE);
    }

    public int getComboStep() {
        return this.entityData.get(COMBO_STEP);
    }

    public void setComboStep(int comboStep) {
        this.entityData.set(COMBO_STEP, Mth.clamp(comboStep, COMBO_STEP_LEFT, COMBO_STEP_SLAM));
    }

    public int getActiveAttackType() {
        if (this.getAttackType() != ATTACK_COMBO) {
            return this.getAttackType();
        }

        return switch (this.getComboStep()) {
            case COMBO_STEP_LEFT -> ATTACK_LEFT_SLASH;
            case COMBO_STEP_RIGHT -> ATTACK_RIGHT_SLASH;
            case COMBO_STEP_SLAM -> ATTACK_SLAM_SLASH;
            default -> ATTACK_LEFT_SLASH;
        };
    }

    public boolean isComboInProgress() {
        return this.isAttacking() && this.getAttackType() == ATTACK_COMBO;
    }

    public boolean advanceAttackAnimation() {
        ++this.attackAnimationTicks;

        if (this.attackAnimationTicks < this.getAttackAnimationDurationTicks()) {
            return false;
        }

        if (this.getAttackType() == ATTACK_COMBO && this.getComboStep() < COMBO_STEP_SLAM) {
            this.setComboStep(this.getComboStep() + 1);
            this.attackAnimationTicks = 0;
            this.attackDamageApplied = false;
            return false;
        }

        this.setIsAttacking(false);
        return true;
    }

    public void setAttackType(int attackType) {
        this.entityData.set(ATTACK_TYPE, Mth.clamp(attackType, ATTACK_RIGHT_SLASH, ATTACK_COMBO));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(CUSTOM_DYING, false);
        this.entityData.define(IS_ATTACKING, false);
        this.entityData.define(ATTACK_TYPE, ATTACK_RIGHT_SLASH);
        this.entityData.define(COMBO_STEP, COMBO_STEP_LEFT);
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
        tag.putInt("AttackType", this.getAttackType());
        tag.putInt("ComboStep", this.getComboStep());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Variant", Tag.TAG_INT)) {
            this.setVariant(tag.getInt("Variant"));
        }
        if (tag.contains("AttackType", Tag.TAG_INT)) {
            this.setAttackType(tag.getInt("AttackType"));
        }
        if (tag.contains("ComboStep", Tag.TAG_INT)) {
            this.setComboStep(tag.getInt("ComboStep"));
        }
    }

    public boolean isCustomDying() {
        return this.entityData.get(CUSTOM_DYING);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ShardLizardSlasherAttackGoal(this, 1.5f, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 60f)
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

   public void tick() {
       super.tick();

//        LivingEntity target = this.getTarget();
//        if (target instanceof Player player && isWearingLizardScaleFullSet(player)
//                && this.getTarget() != player && this.getLastHurtByMob() != player) {
//            this.setTarget(null);
//        }

       if (this.level().isClientSide) {
           setUpAnimationStates();
       }

       if (this.level().isClientSide || this.isCustomDying()) {
           return;
       }
   }

    private void setUpAnimationStates() {
        if (this.isCustomDying()) {
            this.idleAnimationState.stop();
            this.rightSlashAnimationState.stop();
            this.leftSlashAnimationState.stop();
            this.slamSlashAnimationState.stop();
            if (!this.deathAnimationState.isStarted()) {
                this.deathAnimationState.start(this.tickCount);
            }
            return;
        }

        this.deathAnimationState.stop();

        if (this.isAttacking()) {
            this.idleAnimationState.stop();

            switch (this.getActiveAttackType()) {
                case ATTACK_LEFT_SLASH:
                    this.rightSlashAnimationState.stop();
                    this.slamSlashAnimationState.stop();
                    if (!this.leftSlashAnimationState.isStarted()) {
                        this.leftSlashAnimationState.start(this.tickCount);
                    }
                    break;
                case ATTACK_SLAM_SLASH:
                    this.rightSlashAnimationState.stop();
                    this.leftSlashAnimationState.stop();
                    if (!this.slamSlashAnimationState.isStarted()) {
                        this.slamSlashAnimationState.start(this.tickCount);
                    }
                    break;
                case ATTACK_RIGHT_SLASH:
                default:
                    this.leftSlashAnimationState.stop();
                    this.slamSlashAnimationState.stop();
                    if (!this.rightSlashAnimationState.isStarted()) {
                        this.rightSlashAnimationState.start(this.tickCount);
                    }
                    break;
            }
            return;
        }

        this.rightSlashAnimationState.stop();
        this.leftSlashAnimationState.stop();
        this.slamSlashAnimationState.stop();
        this.attackAnimationState.stop();

        if (this.getDeltaMovement().horizontalDistanceSqr() > 0.0001D) {
            this.idleAnimationState.stop();
            return;
        }

        if (!this.idleAnimationState.isStarted()) {
            this.idleAnimationState.start(this.tickCount);
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
            this.setHealth(0.0F);
            return;
        }
        super.die(pDamageSource);
    }

    @Override
    protected void tickDeath() {
        if (this.isCustomDying()) {
            ++this.deathTime;
            ++this.customDeathAnimationTicks;

            if (this.customDeathAnimationTicks >= CUSTOM_DEATH_ANIMATION_TICKS && !this.level().isClientSide()) {
                this.dropAllDeathLoot(this.damageSources().generic());
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
        LOGGER.atInfo().log("getDefaultLootTable() entered!");
        return new ResourceLocation(Neo_Prism.MOD_ID, "entities/shard_lizards/slasher");
    }

    @Override
    public boolean isPreventingPlayerRest(Player player) {
        return false;
    }
}