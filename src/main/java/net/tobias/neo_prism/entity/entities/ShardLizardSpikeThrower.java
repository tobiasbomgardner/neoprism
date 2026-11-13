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
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.entity.entities.projectiles.ShardLizardTailSpike;

public class ShardLizardSpikeThrower extends Monster implements RangedAttackMob {
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(ShardLizardSpikeThrower.class, EntityDataSerializers.INT);
    private static final int VARIANT_COUNT = 9;
    private static final EntityDataAccessor<Boolean> CUSTOM_DYING =
            SynchedEntityData.defineId(ShardLizardSpikeThrower.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_ATTACKING =
            SynchedEntityData.defineId(ShardLizardSpikeThrower.class, EntityDataSerializers.BOOLEAN);
    private static final int CUSTOM_DEATH_ANIMATION_TICKS = 45;
    private static final int THROW_SPIKE_ANIMATION_TICKS = 40;
    private static final int SPIKE_FIRE_TICK = 5;
    private static final int EXTRA_ATTACK_DELAY_TICKS = 20;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();
    public final AnimationState fireSpikeAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private int customDeathAnimationTicks = 0;
    private int attackAnimationTicks = 0;
    private boolean spikeFiredThisAttack = false;
    private LivingEntity queuedSpikeTarget = null;

    public ShardLizardSpikeThrower(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
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
        if (tag.contains("Variant", Tag.TAG_INT)) setVariant(tag.getInt("Variant"));
    }

    public boolean isCustomDying() {
        return entityData.get(CUSTOM_DYING);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        if (target == null || isCustomDying()) return;

        if (isAttacking()) {
            queuedSpikeTarget = target;
            return;
        }

        setIsAttacking(true);
        attackAnimationTicks = THROW_SPIKE_ANIMATION_TICKS;
        spikeFiredThisAttack = false;
        queuedSpikeTarget = target;
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0d, THROW_SPIKE_ANIMATION_TICKS + EXTRA_ATTACK_DELAY_TICKS, 16.0f));
        goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        goalSelector.addGoal(3, new RandomLookAroundGoal(this));

        targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 60f)
                .add(Attributes.ARMOR, 3f)
                .add(Attributes.ARMOR_TOUGHNESS, 1f)
                .add(Attributes.ATTACK_DAMAGE, 5f)
                .add(Attributes.ATTACK_SPEED, 2f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.0f)
                .add(Attributes.FOLLOW_RANGE, 50f)
                .add(Attributes.JUMP_STRENGTH, 3f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 3f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f);
    }

    public void tick() {
        super.tick();

        if (level().isClientSide) setUpAnimationStates();

        if (level().isClientSide || isCustomDying()) return;

        if (isAttacking() && attackAnimationTicks > 0) {
            attackAnimationTicks--;

            if (!spikeFiredThisAttack && attackAnimationTicks <= THROW_SPIKE_ANIMATION_TICKS - SPIKE_FIRE_TICK) {
                if (queuedSpikeTarget != null && queuedSpikeTarget.isAlive()) {
                    Level level = level();
                    Vec3 from = getEyePosition();
                    Vec3 to = queuedSpikeTarget.getEyePosition();
                    Vec3 dir = to.subtract(from).normalize();

                    ShardLizardTailSpike spike = new ShardLizardTailSpike(level, this);
                    spike.setVariant(0);
                    spike.shoot(dir.x, dir.y, dir.z, 2.0f, 1.0f);
                    level.addFreshEntity(spike);
                }

                spikeFiredThisAttack = true;
            }

            if (attackAnimationTicks <= 0) {
                queuedSpikeTarget = null;
                spikeFiredThisAttack = false;
                setIsAttacking(false);
            }
        }
    }

    private void setUpAnimationStates() {
        if (isCustomDying()) {
            idleAnimationState.stop();
            fireSpikeAnimationState.stop();
            if (!deathAnimationState.isStarted()) {
                deathAnimationState.start(tickCount);
            }
            return;
        }

        deathAnimationState.stop();

        if (isAttacking()) {
            idleAnimationState.stop();
            if (!fireSpikeAnimationState.isStarted()) {
                fireSpikeAnimationState.start(tickCount);
            }
            return;
        }

        fireSpikeAnimationState.stop();
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
        if (getPose() == Pose.STANDING) f = Math.min(partialTick * 6f, 1f); else f = 0;
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
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.TURTLE_HURT;
    }

    @Override
    public ResourceLocation getDefaultLootTable() {
        return new ResourceLocation(Neo_Prism.MOD_ID, "entities/shard_lizards/spike_thrower");
    }

    @Override
    public boolean isPreventingPlayerRest(Player player) {
        return false;
    }
}