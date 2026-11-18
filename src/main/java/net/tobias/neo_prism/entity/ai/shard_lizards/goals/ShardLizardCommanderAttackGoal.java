package net.tobias.neo_prism.entity.ai.shard_lizards.goals;

import com.mojang.logging.LogUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.tobias.neo_prism.effects.ModEffects;
import net.tobias.neo_prism.entity.entities.ShardLizardCommander;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class ShardLizardCommanderAttackGoal extends MeleeAttackGoal {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final ShardLizardCommander entity;
    private final Set<Entity> roarPushedTargets = new HashSet<>();
    private boolean vengeanceLiftActive = false;
    private int vengeanceLiftTicksRemaining = 0;
    private boolean vengeanceHovering = false;
    private int attacksDelay = 40;
    private int ticksUntilNextAttack = 45;
    private boolean shouldCountTillNextAttack = false;

    public ShardLizardCommanderAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
        entity = ((ShardLizardCommander) mob);
    }

    @Override
    public void start() {
        super.start();
        attacksDelay = 40;
        ticksUntilNextAttack = 40;
    }

    @Override
    public void stop() {
        roarPushedTargets.clear();
        resetVengeanceLiftState();
        entity.setIsAttacking(false);
        super.stop();
    }

    @Override
    public void tick() {
        super.tick();
        if (shouldCountTillNextAttack) {
            ticksUntilNextAttack = Math.max(ticksUntilNextAttack - 1, 0);
        }
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        float[] damageTicks = entity.getAttackDamageTick();
        int comboDamageTicksReached = 0;

        if (entity.getVengeanceMeterValue() != 7 && isEnemyWithinAttackDistance(enemy, distToEnemySqr)) {
            shouldCountTillNextAttack = true;

            if (!entity.isAttacking()) {
                if (isTimeToStartAttackAnimation()) {
                    entity.startAttackAnimation();
                }
                return;
            }

            if (entity.getAttackType() == ShardLizardCommander.DOUBLE_WHIP ||
                    entity.getAttackType() == ShardLizardCommander.HEAVY_SLASHES ||
                    entity.getAttackType() == ShardLizardCommander.HEAVY_SLASHES_W_SLAM ||
                    entity.getAttackType() == ShardLizardCommander.THREE_STRIKES_COMBO ||
                    entity.getAttackType() == ShardLizardCommander.DOUBLE_WHIP_HEAVY_SLASHES_W_SLAM &&
                    entity.getAttackType() != ShardLizardCommander.STUN_ROAR &&
                    entity.getAttackType() != ShardLizardCommander.SUMMON &&
                    entity.getAttackType() != ShardLizardCommander.SPIT_FIRE) {
                for (int i = 0; i < damageTicks.length; i++) {
                    comboDamageTicksReached = i;
                    if (entity.attackAnimTicks >= damageTicks[i] && damageTicks[i] > entity.getLastComboDamageTicksReached()) {
                        mob.getLookControl().setLookAt(enemy.getX(), enemy.getEyeY(), enemy.getZ());
                        performAttack(enemy, comboDamageTicksReached);
                        entity.setLastComboDamageTicksReached(damageTicks[i]);
                        break;
                    }
                }
            } else if (
                    entity.getAttackType() != ShardLizardCommander.STUN_ROAR &&
                    entity.getAttackType() != ShardLizardCommander.SUMMON &&
                    entity.getAttackType() != ShardLizardCommander.SPIT_FIRE) {
                for (float tick : damageTicks) {
                    if (!entity.hasAttackDamageApplied() && entity.attackAnimTicks >= tick) {
                        mob.getLookControl().setLookAt(enemy.getX(), enemy.getEyeY(), enemy.getZ());
                        performAttack(enemy, comboDamageTicksReached);
                        entity.setAttackDamageApplied(true);
                        break;
                    }
                }
            } else {
                if (entity.getAttackType() == ShardLizardCommander.STUN_ROAR) {
                    if (entity.level().isClientSide) return;

                    if (entity.attackAnimTicks >= 16) {
                        for (Entity target : entity.level().getEntities(entity, entity.getBoundingBox().inflate(5.0d))) {
                            if (target == entity || !target.isAlive()) continue;

                            Vec3 awayFromCommander = entity.position().subtract(target.position());
                            double awayLengthSq = awayFromCommander.lengthSqr();
                            if (awayLengthSq < 1.0e-6d) continue;

                            Vec3 push = awayFromCommander.normalize().scale(0.20d);
                            Vec3 currentVelocity = target.getDeltaMovement();
                            target.setDeltaMovement(
                                    currentVelocity.x - push.x,
                                    Math.max(currentVelocity.y, 0.0d) + 0.10d,
                                    currentVelocity.z - push.z
                            );
                            target.hurtMarked = true;
                            roarPushedTargets.add(target);
                        }
                    }

                    if (entity.attackAnimTicks >= 30) {
                        for (Entity target : roarPushedTargets) {
                            if (target == null || !target.isAlive()) continue;
                            if (target instanceof LivingEntity livingTarget) {
                                livingTarget.addEffect(
                                        new MobEffectInstance(
                                                ModEffects.PARALYSIS.get(),
                                                10 * 20, 0,
                                                false, true
                                        )
                                );
                            }
                        }
                        roarPushedTargets.clear();
                    }
                }
                if (entity.getAttackType() == ShardLizardCommander.SUMMON) {}
                if (entity.getAttackType() == ShardLizardCommander.SPIT_FIRE) {}
            }

            if (!entity.isAttacking()) {
                roarPushedTargets.clear();
                resetAttackCooldown();
            }
            return;
        } else if (entity.getVengeanceMeterValue() == 7) {
            LOGGER.atInfo().log("VENGEANCE ACTIVE: VM == " + entity.getVengeanceMeterValue());
            shouldCountTillNextAttack = true;
            if (!entity.isAttacking()) {
                LOGGER.atInfo().log("Entity not attacking!");
                entity.startAttackAnimation(ShardLizardCommander.VENGEANCE);
                return;
            }

            if (vengeanceLiftActive && vengeanceLiftTicksRemaining > 0) {
                LOGGER.atInfo().log("vengeanceLiftActive && vengeanceLiftTicksRemaining > 0 passed! ( vLA == " + vengeanceLiftActive + ", vLTR == " + vengeanceLiftTicksRemaining + ")");
                entity.setDeltaMovement(0.0d, 0.12d, 0.0d);
                LOGGER.atInfo().log("Movement set! (0.0d, 0.12d, 0.0d)");
                entity.hurtMarked = true;
                vengeanceLiftTicksRemaining--;
            }

            float[] effectTicks = entity.getEffectTicks();
            for (int i = 0; i < effectTicks.length; i++) {
                if (i == 0) {
                    LOGGER.atInfo().log("i == 0!");
                    if (entity.attackAnimTicks >= effectTicks[i] && effectTicks[i] > entity.getLastEffectTicksReached()) {
                        LOGGER.atInfo().log("(i == 0) entity.attackAnimTicks >= effectTicks[i] && effectTicks[i] > entity.getLastEffectTicksReached() passed! ( e.aAT == " + entity.attackAnimTicks + ", eT[i] == " + effectTicks[i] + ", e.gLETR() == " + entity.getLastEffectTicksReached() + ")");
                        vengeanceLiftActive = true;
                        vengeanceLiftTicksRemaining = 45;
                        if (vengeanceHovering) {
                            LOGGER.atInfo().log("entity is vengeanceHovering! Resetting...");
                            entity.setNoGravity(false);
                            vengeanceHovering = false;
                            LOGGER.atInfo().log("...Reset!");
                        }
                        entity.setLastEffectTicksReached(effectTicks[i]);
                        LOGGER.atInfo().log("lastEffectTicksReached == " + effectTicks[i] + "!");
                        break;
                    }
                    LOGGER.atInfo().log("(i == 0) entity.attackAnimTicks >= effectTicks[i] && effectTicks[i] > entity.getLastEffectTicksReached() FAILED! ( e.aAT == " + entity.attackAnimTicks + ", eT[i] == " + effectTicks[i] + ", e.gLETR() == " + entity.getLastEffectTicksReached() + ")");
                } else if (i == 1) {
                    LOGGER.atInfo().log("i == 1!");
                    if (entity.attackAnimTicks >= effectTicks[i] && effectTicks[i] > entity.getLastEffectTicksReached()) {
                        LOGGER.atInfo().log("(i == 1) entity.attackAnimTicks >= effectTicks[i] && effectTicks[i] > entity.getLastEffectTicksReached() passed! ( e.aAT == " + entity.attackAnimTicks + ", eT[i] == " + effectTicks[i] + ", e.gLETR() == " + entity.getLastEffectTicksReached() + ")");
                        vengeanceLiftActive = false;
                        vengeanceLiftTicksRemaining = 0;
                        entity.setDeltaMovement(0.0d, 0.0d, 0.0d);
                        LOGGER.atInfo().log("deltaMovement == 0.0d, 0.0d, 0.0d.");
                        entity.setNoGravity(true);
                        vengeanceHovering = true;
                        entity.setInvisible(true);
                        LOGGER.atInfo().log("entity is Invisible!");
                        LivingEntity target = entity.getTarget();
                        if (target != null) {
                            LOGGER.atInfo().log("target != null (target == " + target.getDisplayName() + ")");
                            target.addEffect(new MobEffectInstance(ModEffects.PARALYSIS.get(), -1, 0,
                                    false, false, true));
                            target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, -1, 0,
                                    false, false, true));
                            LOGGER.atInfo().log("added effects to target.");
                            double targetX = target.getX();
                            double targetY = target.getY();
                            double targetZ = target.getZ();
                            entity.teleportTo(targetX, targetY, targetZ);
                            LOGGER.atInfo().log("teleported entity to target.");
                        } else LOGGER.atInfo().log("target is NULL!!!");
                        entity.setLastEffectTicksReached(effectTicks[i]);
                        LOGGER.atInfo().log("lastEffectTicksReached == " + effectTicks[i] + "!");
                        break;
                    }
                    LOGGER.atInfo().log("(i == 1) entity.attackAnimTicks >= effectTicks[i] && effectTicks[i] > entity.getLastEffectTicksReached() FAILED! ( e.aAT == " + entity.attackAnimTicks + ", eT[i] == " + effectTicks[i] + ", e.gLETR() == " + entity.getLastEffectTicksReached() + ")");
                } else if (i == 2) {
                    LOGGER.atInfo().log("i == 2!");
                    if (entity.attackAnimTicks >= effectTicks[i] && effectTicks[i] > entity.getLastEffectTicksReached()) {
                        LOGGER.atInfo().log("(i == 2) entity.attackAnimTicks >= effectTicks[i] && effectTicks[i] > entity.getLastEffectTicksReached() passed! ( e.aAT == " + entity.attackAnimTicks + ", eT[i] == " + effectTicks[i] + ", e.gLETR() == " + entity.getLastEffectTicksReached() + ")");
                        entity.setNoGravity(false);
                        vengeanceHovering = false;
                        entity.setInvisible(false);
                        LOGGER.atInfo().log("entity != isInvisible");
                        for (int d = 0; d < damageTicks.length; d++) {
                            comboDamageTicksReached = d;
                            if (entity.attackAnimTicks >= damageTicks[d] && damageTicks[d] > entity.getLastComboDamageTicksReached()) {
                                LOGGER.atInfo().log("entity.attackAnimTicks >= damageTicks[d] && damageTicks[d] > entity.getLastComboDamageTicksReached() passed! (e.aAT == " + entity.attackAnimTicks + ", dT[d] == " + damageTicks[d] + ", e.gLCDTR() == " + entity.getLastComboDamageTicksReached() + ")");
                                mob.getLookControl().setLookAt(enemy.getX(), enemy.getEyeY(), enemy.getZ());
                                performAttack(enemy, comboDamageTicksReached);
                                LOGGER.atInfo().log("performAttack(enemy, comboDamageTicksReached) has been called");
                                entity.setLastComboDamageTicksReached(damageTicks[d]);
                                LOGGER.atInfo().log("lastComboDamageTicksReached == " + damageTicks[d] + "!");
                                entity.setLastEffectTicksReached(effectTicks[i]);
                                LOGGER.atInfo().log("lastEffectTicksReached == " + effectTicks[i] + "!");
                                break;
                            }
                        }
                        LivingEntity target = entity.getTarget();
                        if (target != null) {
                            target.removeEffect(MobEffects.BLINDNESS);
                            target.removeEffect(ModEffects.PARALYSIS.get());
                            LOGGER.atInfo().log("removed given effects (BLINDNESS, PARALYSIS) from target.");
                        } else LOGGER.atInfo().log("target == NULL!!!");
                    }
                    LOGGER.atInfo().log("(i == 3) entity.attackAnimTicks >= effectTicks[i] && effectTicks[i] > entity.getLastEffectTicksReached() FAILED! ( e.aAT == " + entity.attackAnimTicks + ", eT[i] == " + effectTicks[i] + ", e.gLETR() == " + entity.getLastEffectTicksReached() + ")");
                    entity.resetVengeanceMeter(0);
                    LOGGER.atInfo().log("vengeanceMeter is now at 0");
                }
            }
        }

        if (entity.isAttacking() && entity.getAttackType() == ShardLizardCommander.SPIT_FIRE) {
            return;
        }

        roarPushedTargets.clear();
        resetAttackCooldown();
        shouldCountTillNextAttack = false;
        resetVengeanceLiftState();
        entity.setIsAttacking(false);
        entity.attackAnimTicks = 0;
    }

    private void resetVengeanceLiftState() {
        vengeanceLiftActive = false;
        vengeanceLiftTicksRemaining = 0;
        if (vengeanceHovering) {
            entity.setNoGravity(false);
            vengeanceHovering = false;
        }
    }

    private boolean isEnemyWithinAttackDistance(LivingEntity enemy, double distToEnemySqr) {
        return distToEnemySqr <= getAttackReachSqr(enemy) + 1.0D;
    }

    @Override
    protected double getAttackReachSqr(LivingEntity attackTarget) {
        return super.getAttackReachSqr(attackTarget) + 1.5d;
    }

    @Override
    protected void resetAttackCooldown() {
        ticksUntilNextAttack = adjustedTickDelay(attacksDelay * 2);
    }

    @Override
    protected boolean isTimeToAttack() {
        return ticksUntilNextAttack <= 0;
    }

    protected boolean isTimeToStartAttackAnimation() {
        return ticksUntilNextAttack <= attacksDelay;
    }

    protected int getTicksUntilNextAttack() {
        return ticksUntilNextAttack;
    }

    protected void performAttack(LivingEntity enemy, int comboDamageTicksReached) {
        mob.swing(InteractionHand.MAIN_HAND);
        float[] amount = entity.getCurrentAttackDamage();
        float damageAmount = amount[comboDamageTicksReached];

        if (entity.getAttackType() == ShardLizardCommander.DOUBLE_WHIP ||
                entity.getAttackType() == ShardLizardCommander.HEAVY_SLASHES ||
                entity.getAttackType() == ShardLizardCommander.HEAVY_SLASHES_W_SLAM ||
                entity.getAttackType() == ShardLizardCommander.THREE_STRIKES_COMBO ||
                entity.getAttackType() == ShardLizardCommander.DOUBLE_WHIP_HEAVY_SLASHES_W_SLAM) {
            enemy.invulnerableTime = 0;
            enemy.hurtTime = 0;
        }

        boolean didDamage = hurtWithoutKnockback(enemy, damageAmount);
        if (!didDamage || enemy.level().isClientSide()) {
            return;
        }

        int attackType = entity.getAttackType();
        if (attackType == ShardLizardCommander.STUN_SLAM) {
            enemy.addEffect(new MobEffectInstance(ModEffects.PARALYSIS.get(), 5 * 20, 0,
                    false, true
            ));
        }
    }

    private boolean hurtWithoutKnockback(LivingEntity target, float damage) {
        Vec3 oldVelocity = target.getDeltaMovement();
        boolean didDamage = target.hurt(mob.damageSources().mobAttack(mob), damage);
        if (didDamage) {
            target.setDeltaMovement(oldVelocity.x, oldVelocity.y, oldVelocity.z);
            target.hurtMarked = true;
            if (entity.vengeanceMeter >= 1) {
                entity.vengeanceMeter--;
            }
        }
        return didDamage;
    }
}