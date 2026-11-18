package net.tobias.neo_prism.entity.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.entity.ai.shard_lizards.goals.ShardLizardCommanderAttackGoal;

public class ShardLizardCommander extends Monster {
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(ShardLizardCommander.class, EntityDataSerializers.INT);
    private static final int VARIANT_COUNT = 9;
    private static final EntityDataAccessor<Boolean> CUSTOM_DYING =
            SynchedEntityData.defineId(ShardLizardCommander.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_ATTACKING =
            SynchedEntityData.defineId(ShardLizardCommander.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ATTACK_TYPE =
            SynchedEntityData.defineId(ShardLizardCommander.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> VENGEANCE_METER_VALUE =
            SynchedEntityData.defineId(ShardLizardCommander.class, EntityDataSerializers.INT);
    public static final int STUN_ROAR = 0;
    public static final int SUMMON = 1;
    public static final int SPIT_FIRE = 2;
    public static final int BITE = 3;
    public static final int STUN_SLAM = 4;
    public static final int WHIP = 5;
    public static final int HEAVY_SLASH = 6;
    public static final int DOUBLE_WHIP = 7;
    public static final int HEAVY_SLASHES = 8;
    public static final int HEAVY_SLASHES_W_SLAM = 9;
    public static final int THREE_STRIKES_COMBO = 10;
    public static final int DOUBLE_WHIP_HEAVY_SLASHES_W_SLAM = 11;
    public static final int VENGEANCE = 12;
    private static final int DEATH_ANIM_TICKS = 80;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();
    public final AnimationState stunRoarAnimationState = new AnimationState();
    public final AnimationState summonAnimationState = new AnimationState();
    public final AnimationState spitFireAnimationState = new AnimationState();
    public final AnimationState biteAnimationState = new AnimationState();
    public final AnimationState stunSlamAnimationState = new AnimationState();
    public final AnimationState whipAnimationState = new AnimationState();
    public final AnimationState heavySlashAnimationState = new AnimationState();
    public final AnimationState doubleWhipAnimationState = new AnimationState();
    public final AnimationState heavySlashesAnimationState = new AnimationState();
    public final AnimationState heavySlashesWSlamAnimationState = new AnimationState();
    public final AnimationState threeStrikesComboAnimationState = new AnimationState();
    public final AnimationState doubleWhipHeavySlashesWSlamAnimationState = new AnimationState();
    public final AnimationState vengeanceAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private int deathAnimTicks = 0;
    public int attackAnimTicks = 0;
    private boolean attackDamageApplied = false;
    private float lastComboDamageTicksReached = -1.0f;
    private float lastEffectTicksReached = -1.0f;
    public int vengeanceMeter = 0;

    public ShardLizardCommander(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        xpReward = 50;
    }

    public void setIsAttacking(boolean isAttacking) {
        boolean wasAttacking = entityData.get(IS_ATTACKING);
        if (wasAttacking != isAttacking) {
            entityData.set(IS_ATTACKING, isAttacking);
        }
        attackAnimTicks = 0;
        attackDamageApplied = false;
        lastComboDamageTicksReached = -1.0f;
        lastEffectTicksReached = - 1.0f;
    }

    public void startAttackAnimation() {
        if (getVengeanceMeterValue() >= 7) {
            setAttackType(VENGEANCE);
        } else {
            setAttackType(selectAttackType());
        }
        attackAnimTicks = 0;
        attackDamageApplied = false;
        lastComboDamageTicksReached = -1.0f;
        lastEffectTicksReached = -1.0f;
        setIsAttacking(true);
    }
    public void startAttackAnimation(int forceSet) {
        setAttackType(forceSet);
        attackAnimTicks = 0;
        attackDamageApplied = false;
        lastComboDamageTicksReached = -1.0f;
        lastEffectTicksReached = -1.0f;
        setIsAttacking(true);
    }

    private int selectAttackType() {
        boolean canUseSpitFire = getHealth() <= getMaxHealth() * 0.5f;
        float roll = getRandom().nextFloat();

        if (roll < 0.05f) {
            return DOUBLE_WHIP_HEAVY_SLASHES_W_SLAM;
        }
        if (roll < 0.22f) {
            int[] secondRarest = {
                    THREE_STRIKES_COMBO,
                    HEAVY_SLASHES_W_SLAM,
                    STUN_SLAM,
                    STUN_ROAR,
                    SPIT_FIRE
            };
            if (!canUseSpitFire) {
                int[] filtered = {THREE_STRIKES_COMBO, HEAVY_SLASHES_W_SLAM, STUN_SLAM, STUN_ROAR};
                return filtered[getRandom().nextInt(filtered.length)];
            }
            return secondRarest[getRandom().nextInt(secondRarest.length)];
        }
        if (roll < 0.42f) {
            int[] thirdRarest = {HEAVY_SLASHES, DOUBLE_WHIP};
            return thirdRarest[getRandom().nextInt(thirdRarest.length)];
        }

        int[] common = {BITE, WHIP, HEAVY_SLASH};
        return common[getRandom().nextInt(common.length)];
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

    public float getLastEffectTicksReached() {
        return lastEffectTicksReached;
    }

    public void setLastEffectTicksReached(float lastEffectTicksReached) {
        this.lastEffectTicksReached = lastEffectTicksReached;
    }

    public float[] getAttackDamageTick() {
        return switch (getAttackType()) {
            case BITE -> new float[] {13.0f};
            case WHIP -> new float[] {19.0f};
            case STUN_SLAM -> new float[] {12.0f};
            case HEAVY_SLASH -> new float[] {24.0f};
            case DOUBLE_WHIP -> new float[] {23.0f, 28.0f};
            case HEAVY_SLASHES -> new float[] {24.0f, 30.0f};
            case HEAVY_SLASHES_W_SLAM -> new float[] {24.0f, 30.0f, 40.0f};
            case THREE_STRIKES_COMBO -> new float[] {24.0f, 29.0f, 39.0f};
            case DOUBLE_WHIP_HEAVY_SLASHES_W_SLAM -> new float[] {24.0f, 29.0f, 39.0f, 44.0f, 55.0f};
            case VENGEANCE -> new float[] {247.6f, 252.6f, 257.6f, 262.6f, 266.6f, 271.6f, 275.0f, 280.0f, 283.4f, 288.4f, 328.4f};
            default -> new float[] {};
        };
    }

    public float getAttackAnimationDurationTicks() {
        return switch (getAttackType()) {
            case STUN_ROAR -> 40.0f;
            case SPIT_FIRE -> 175.0f;
            case BITE, WHIP -> 20.0f;
            case STUN_SLAM -> 35.3f;
            case HEAVY_SLASH -> 50.0f;
            case DOUBLE_WHIP -> 30.0f;
            case HEAVY_SLASHES -> 60.0f;
            case HEAVY_SLASHES_W_SLAM -> 55.0f;
            case THREE_STRIKES_COMBO -> 65.0f;
            case DOUBLE_WHIP_HEAVY_SLASHES_W_SLAM -> 70.0f;
            case VENGEANCE -> 355.0f;
            default -> 0.0f;
        };
    }

    public float[] getCurrentAttackDamage() {
        return switch (getAttackType()) {
            case BITE, WHIP -> new float[] {5.0f};
            case STUN_SLAM, HEAVY_SLASH -> new float[] {7.0f};
            case DOUBLE_WHIP -> new float[] {3.0f, 5.0f};
            case HEAVY_SLASHES -> new float[] {5.0f, 5.0f};
            case HEAVY_SLASHES_W_SLAM -> new float[] {5.0f, 5.0f, 7.0f};
            case THREE_STRIKES_COMBO -> new float[] {3.0f, 5.0f, 5.0f};
            case DOUBLE_WHIP_HEAVY_SLASHES_W_SLAM -> new float[] {3.0f, 5.0f, 5.0f, 5.0f, 7.0f};
            case VENGEANCE -> new float[] {4.0f, 4.0f, 4.0f, 4.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 7.0f};
            default -> new float[] {};
        };
    }

    public float[] getEffectTicks() {
        if (getAttackType() == VENGEANCE) {
            return new float[] {175.0f, 220.0f, 230.0f};
            //175.0f start lift, 220.0f stop lift and make invisible, 230.0f tp.
        } else {
            return new float[] {};
        }
    }

    public boolean isAttacking() {
        return entityData.get(IS_ATTACKING);
    }

    public int getAttackType() {
        return entityData.get(ATTACK_TYPE);
    }

    public void setAttackType(int attackType) {
        entityData.set(ATTACK_TYPE, Mth.clamp(attackType, STUN_ROAR, VENGEANCE));
        if (level() instanceof ServerLevel serverLevel) {
            String attackName = switch (getAttackType()) {
                case 0 -> "Stun Roar";
                case 1 -> "Summon";
                case 2 -> "Spit Fire";
                case 3 -> "Bite";
                case 4 -> "Stun Slam";
                case 5 -> "Whip";
                case 6 -> "Heavy Slash";
                case 7 -> "Double Whip";
                case 8 -> "Heavy Slashes";
                case 9 -> "Heavy Slashes with Slam";
                case 10 -> "Three Strikes Combo";
                case 11 -> "Double Whip and Heavy Slashes with Slam";
                case 12 -> "VENGEANCE";
                default -> "Unknown";
            };
            for (Player player : serverLevel.players()) {
                player.displayClientMessage(Component.literal(attackName), true);
            }
        }
    }

    public void resetVengeanceMeter(int startPoint) {
        vengeanceMeter = startPoint;
    }

    public int getVengeanceMeterValue() {
        return vengeanceMeter;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(VARIANT, 0);
        entityData.define(CUSTOM_DYING, false);
        entityData.define(IS_ATTACKING, false);
        entityData.define(ATTACK_TYPE, BITE);
        entityData.define(VENGEANCE_METER_VALUE, 0);
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
    public boolean hurt(DamageSource source, float amount) {
        int vengeanceMeterMultiplier = 0;
        if (source.getDirectEntity() instanceof Projectile) {
            amount *= 0.5f;
            vengeanceMeterMultiplier = 1;
        }

        if (source.getDirectEntity() instanceof Projectile && isAttacking() && (getAttackType() == STUN_ROAR || getAttackType() == SPIT_FIRE || getAttackType() == SUMMON)) {
            return false;
        }

        boolean damaged = super.hurt(source, amount);
        if (damaged) {
            invulnerableTime = Math.max(invulnerableTime, 20);
            hurtTime = Math.max(hurtTime, 15);
            vengeanceMeter++;
            if (source.getDirectEntity() instanceof Projectile) {
                vengeanceMeter = vengeanceMeter + vengeanceMeterMultiplier;
            }
        }
        return damaged;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", getVariant());
        tag.putInt("AttackType", getAttackType());
        tag.putInt("VengeanceMeterValue", getVengeanceMeterValue());
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
        if (tag.contains("VengeanceMeterValue", Tag.TAG_INT)) {
            resetVengeanceMeter(tag.getInt("VengeanceMeterValue"));
        }
    }

    public boolean isCustomDying() {
        return entityData.get(CUSTOM_DYING);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new ShardLizardCommanderAttackGoal(this, 1.5f, true));
        goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        goalSelector.addGoal(3, new RandomLookAroundGoal(this));

        targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 200f)
                .add(Attributes.ARMOR, 20f)
                .add(Attributes.ARMOR_TOUGHNESS, 10f)
                .add(Attributes.ATTACK_DAMAGE, 7f)
                .add(Attributes.ATTACK_SPEED, 1f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.001f)
                .add(Attributes.FOLLOW_RANGE, 50f)
                .add(Attributes.JUMP_STRENGTH, 3f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10f)
                .add(Attributes.MOVEMENT_SPEED, 0.1f);
    }

    public boolean advanceAttackAnimation() {
        if (!isAttacking()) {
            return false;
        }

        ++attackAnimTicks;

        if (attackAnimTicks < getAttackAnimationDurationTicks()) {
            return false;
        }

        setIsAttacking(false);
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (isAttacking() && (getAttackType() == STUN_ROAR || getAttackType() == SPIT_FIRE || getAttackType() == SUMMON)) {
            getNavigation().stop();
            setDeltaMovement(getDeltaMovement().x * 0.1D, getDeltaMovement().y, getDeltaMovement().z * 0.1D);
        }

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
            stunRoarAnimationState.stop();
            spitFireAnimationState.stop();
            biteAnimationState.stop();
            stunSlamAnimationState.stop();
            whipAnimationState.stop();
            heavySlashAnimationState.stop();
            doubleWhipAnimationState.stop();
            heavySlashesAnimationState.stop();
            heavySlashesWSlamAnimationState.stop();
            threeStrikesComboAnimationState.stop();
            doubleWhipHeavySlashesWSlamAnimationState.stop();
            vengeanceAnimationState.stop();
            if (!deathAnimationState.isStarted()) {
                deathAnimationState.start(tickCount);
            }
            return;
        }

        deathAnimationState.stop();

        if (isAttacking()) {
            idleAnimationState.stop();

            switch (getAttackType()) {
                case STUN_ROAR:
                    spitFireAnimationState.stop();
                    biteAnimationState.stop();
                    stunSlamAnimationState.stop();
                    whipAnimationState.stop();
                    heavySlashAnimationState.stop();
                    doubleWhipAnimationState.stop();
                    heavySlashesAnimationState.stop();
                    heavySlashesWSlamAnimationState.stop();
                    threeStrikesComboAnimationState.stop();
                    doubleWhipHeavySlashesWSlamAnimationState.stop();
                    vengeanceAnimationState.stop();
                    if (!stunRoarAnimationState.isStarted()) {
                        stunRoarAnimationState.start(tickCount);
                    }
                    break;
                case SPIT_FIRE:
                    stunRoarAnimationState.stop();
                    biteAnimationState.stop();
                    stunSlamAnimationState.stop();
                    whipAnimationState.stop();
                    heavySlashAnimationState.stop();
                    doubleWhipAnimationState.stop();
                    heavySlashesAnimationState.stop();
                    heavySlashesWSlamAnimationState.stop();
                    threeStrikesComboAnimationState.stop();
                    doubleWhipHeavySlashesWSlamAnimationState.stop();
                    vengeanceAnimationState.stop();
                    if (!spitFireAnimationState.isStarted()) {
                        spitFireAnimationState.start(tickCount);
                    }
                    break;
                case STUN_SLAM:
                    stunRoarAnimationState.stop();
                    spitFireAnimationState.stop();
                    biteAnimationState.stop();
                    whipAnimationState.stop();
                    heavySlashAnimationState.stop();
                    doubleWhipAnimationState.stop();
                    heavySlashesAnimationState.stop();
                    heavySlashesWSlamAnimationState.stop();
                    threeStrikesComboAnimationState.stop();
                    doubleWhipHeavySlashesWSlamAnimationState.stop();
                    vengeanceAnimationState.stop();
                    if (!stunSlamAnimationState.isStarted()) {
                        stunSlamAnimationState.start(tickCount);
                    }
                    break;
                case WHIP:
                    stunRoarAnimationState.stop();
                    spitFireAnimationState.stop();
                    biteAnimationState.stop();
                    stunSlamAnimationState.stop();
                    heavySlashAnimationState.stop();
                    doubleWhipAnimationState.stop();
                    heavySlashesAnimationState.stop();
                    heavySlashesWSlamAnimationState.stop();
                    threeStrikesComboAnimationState.stop();
                    doubleWhipHeavySlashesWSlamAnimationState.stop();
                    vengeanceAnimationState.stop();
                    if (!whipAnimationState.isStarted()) {
                        whipAnimationState.start(tickCount);
                    }
                    break;
                case HEAVY_SLASH:
                    stunRoarAnimationState.stop();
                    spitFireAnimationState.stop();
                    biteAnimationState.stop();
                    stunSlamAnimationState.stop();
                    whipAnimationState.stop();
                    doubleWhipAnimationState.stop();
                    heavySlashesAnimationState.stop();
                    heavySlashesWSlamAnimationState.stop();
                    threeStrikesComboAnimationState.stop();
                    doubleWhipHeavySlashesWSlamAnimationState.stop();
                    vengeanceAnimationState.stop();
                    if (!heavySlashAnimationState.isStarted()) {
                        heavySlashAnimationState.start(tickCount);
                    }
                    break;
                case DOUBLE_WHIP:
                    stunRoarAnimationState.stop();
                    spitFireAnimationState.stop();
                    biteAnimationState.stop();
                    stunSlamAnimationState.stop();
                    whipAnimationState.stop();
                    heavySlashAnimationState.stop();
                    heavySlashesAnimationState.stop();
                    heavySlashesWSlamAnimationState.stop();
                    threeStrikesComboAnimationState.stop();
                    doubleWhipHeavySlashesWSlamAnimationState.stop();
                    vengeanceAnimationState.stop();
                    if (!doubleWhipAnimationState.isStarted()) {
                        doubleWhipAnimationState.start(tickCount);
                    }
                    break;
                case HEAVY_SLASHES:
                    stunRoarAnimationState.stop();
                    spitFireAnimationState.stop();
                    biteAnimationState.stop();
                    stunSlamAnimationState.stop();
                    whipAnimationState.stop();
                    heavySlashAnimationState.stop();
                    doubleWhipAnimationState.stop();
                    heavySlashesWSlamAnimationState.stop();
                    threeStrikesComboAnimationState.stop();
                    doubleWhipHeavySlashesWSlamAnimationState.stop();
                    vengeanceAnimationState.stop();
                    if (!heavySlashesAnimationState.isStarted()) {
                        heavySlashesAnimationState.start(tickCount);
                    }
                    break;
                case HEAVY_SLASHES_W_SLAM:
                    stunRoarAnimationState.stop();
                    spitFireAnimationState.stop();
                    biteAnimationState.stop();
                    stunSlamAnimationState.stop();
                    whipAnimationState.stop();
                    heavySlashAnimationState.stop();
                    doubleWhipAnimationState.stop();
                    heavySlashesAnimationState.stop();
                    threeStrikesComboAnimationState.stop();
                    doubleWhipHeavySlashesWSlamAnimationState.stop();
                    vengeanceAnimationState.stop();
                    if (!heavySlashesWSlamAnimationState.isStarted()) {
                        heavySlashesWSlamAnimationState.start(tickCount);
                    }
                    break;
                case THREE_STRIKES_COMBO:
                    stunRoarAnimationState.stop();
                    spitFireAnimationState.stop();
                    biteAnimationState.stop();
                    stunSlamAnimationState.stop();
                    whipAnimationState.stop();
                    heavySlashAnimationState.stop();
                    doubleWhipAnimationState.stop();
                    heavySlashesAnimationState.stop();
                    heavySlashesWSlamAnimationState.stop();
                    doubleWhipHeavySlashesWSlamAnimationState.stop();
                    vengeanceAnimationState.stop();
                    if (!threeStrikesComboAnimationState.isStarted()) {
                        threeStrikesComboAnimationState.start(tickCount);
                    }
                    break;
                case DOUBLE_WHIP_HEAVY_SLASHES_W_SLAM:
                    stunRoarAnimationState.stop();
                    spitFireAnimationState.stop();
                    biteAnimationState.stop();
                    stunSlamAnimationState.stop();
                    whipAnimationState.stop();
                    heavySlashAnimationState.stop();
                    doubleWhipAnimationState.stop();
                    heavySlashesAnimationState.stop();
                    heavySlashesWSlamAnimationState.stop();
                    threeStrikesComboAnimationState.stop();
                    vengeanceAnimationState.stop();
                    if (!doubleWhipHeavySlashesWSlamAnimationState.isStarted()) {
                        doubleWhipHeavySlashesWSlamAnimationState.start(tickCount);
                    }
                    break;
                case VENGEANCE:
                    stunRoarAnimationState.stop();
                    spitFireAnimationState.stop();
                    biteAnimationState.stop();
                    stunSlamAnimationState.stop();
                    whipAnimationState.stop();
                    heavySlashAnimationState.stop();
                    doubleWhipAnimationState.stop();
                    heavySlashesAnimationState.stop();
                    heavySlashesWSlamAnimationState.stop();
                    threeStrikesComboAnimationState.stop();
                    doubleWhipHeavySlashesWSlamAnimationState.stop();
                    if (!vengeanceAnimationState.isStarted()) {
                        vengeanceAnimationState.start(tickCount);
                    }
                    break;
                case BITE:
                default:
                    stunRoarAnimationState.stop();
                    spitFireAnimationState.stop();
                    stunSlamAnimationState.stop();
                    whipAnimationState.stop();
                    heavySlashAnimationState.stop();
                    doubleWhipAnimationState.stop();
                    heavySlashesAnimationState.stop();
                    heavySlashesWSlamAnimationState.stop();
                    threeStrikesComboAnimationState.stop();
                    doubleWhipHeavySlashesWSlamAnimationState.stop();
                    vengeanceAnimationState.stop();
                    if (!biteAnimationState.isStarted()) {
                        biteAnimationState.start(tickCount);
                    }
                    break;
            }
            return;
        }

        stunRoarAnimationState.stop();
        spitFireAnimationState.stop();
        biteAnimationState.stop();
        stunSlamAnimationState.stop();
        whipAnimationState.stop();
        heavySlashAnimationState.stop();
        doubleWhipAnimationState.stop();
        heavySlashesAnimationState.stop();
        heavySlashesWSlamAnimationState.stop();
        threeStrikesComboAnimationState.stop();
        doubleWhipHeavySlashesWSlamAnimationState.stop();
        vengeanceAnimationState.stop();
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
            deathAnimTicks = 0;
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
            ++deathAnimTicks;

            if (deathAnimTicks >= DEATH_ANIM_TICKS && !level().isClientSide()) {
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
        return new ResourceLocation(Neo_Prism.MOD_ID, "entities/shard_lizards/commander");
    }

    @Override
    public boolean isPreventingPlayerRest(Player player) {
        return false;
    }
}