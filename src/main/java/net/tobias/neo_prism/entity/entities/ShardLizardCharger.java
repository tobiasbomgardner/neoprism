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
import net.tobias.neo_prism.entity.ai.shard_lizards.goals.ShardLizardChargerBiteAttackGoal;

public class ShardLizardCharger extends Monster {
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(ShardLizardScout.class, EntityDataSerializers.INT);
    private static final int VARIANT_COUNT = 9;
    private static final EntityDataAccessor<Boolean> CUSTOM_DYING =
            SynchedEntityData.defineId(ShardLizardScout.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_ATTACKING =
            SynchedEntityData.defineId(ShardLizardScout.class, EntityDataSerializers.BOOLEAN);
    private static final int CUSTOM_DEATH_ANIMATION_TICKS = 25;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private int customDeathAnimationTicks = 0;
    public int attackAnimationTicks = 0;
    private int dashCooldown = 0;
    private int dashTicks = 0;
    private boolean isDashing = false;

    public ShardLizardCharger(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 15;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ShardLizardChargerBiteAttackGoal(this, 1.5f, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 25, true, false, livingEntity -> true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 60f)
                .add(Attributes.ARMOR, 6f)
                .add(Attributes.ARMOR_TOUGHNESS, 2.5f)
                .add(Attributes.ATTACK_DAMAGE, 7f)
                .add(Attributes.ATTACK_SPEED, 2f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1f)
                .add(Attributes.FOLLOW_RANGE, 70f)
                .add(Attributes.JUMP_STRENGTH, 3f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 2f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f);
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

    private void setUpAnimationStates() {
        if (this.isCustomDying()) {
            this.idleAnimationState.stop();
            if (!this.deathAnimationState.isStarted()) {
                this.deathAnimationState.start(this.tickCount);
            }
            return;
        }

        this.deathAnimationState.stop();

        if (this.isAttacking()) {
            this.idleAnimationState.stop();
            if (!this.attackAnimationState.isStarted()) {
                this.attackAnimationState.start(this.tickCount);
            }
            return;
        }

        this.attackAnimationState.stop();

        if (this.getDeltaMovement().horizontalDistanceSqr() > 0.0001D) {
            this.idleAnimationState.stop();
            return;
        }

        if (!this.idleAnimationState.isStarted()) {
            this.idleAnimationState.start(this.tickCount);
        }
    }

    public void tick() {
        super.tick();

        if (this.dashCooldown > 0) {
            --this.dashCooldown;
        }

        if (this.isDashing) {
            ++this.dashTicks;
        } else {
            this.dashTicks = 0;
        }

        if (this.level().isClientSide) {
            setUpAnimationStates();
        }
    }

    public boolean isDashing() {
        return this.isDashing;
    }

    public void startDash(Vec3 direction) {
        this.isDashing = true;
        this.dashTicks = 0;
        this.setDeltaMovement(direction.normalize().scale(2.6D));
        this.setIsAttacking(true);
    }

    public void cancelDash() {
        this.isDashing = false;
        this.dashTicks = 0;
        this.dashCooldown = 40;
        this.setIsAttacking(false);
        this.setDeltaMovement(0.0D, 0.0D, 0.0D);
    }

    public int getDashCooldown() {
        return this.dashCooldown;
    }

    public void setDashCooldown(int dashCooldown) {
        this.dashCooldown = dashCooldown;
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
        return new ResourceLocation(Neo_Prism.MOD_ID, "entities/shard_lizards/charger");
    }

    @Override
    public boolean isPreventingPlayerRest(Player player) {
        return false;
    }
}