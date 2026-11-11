package net.tobias.neo_prism.entity.ai.shard_lizards.goals;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.tobias.neo_prism.effects.ModEffects;
import net.tobias.neo_prism.entity.entities.ShardLizardThrasher;

public class ShardLizardThrasherAttackGoal extends MeleeAttackGoal {
    private final ShardLizardThrasher entity;
    private int attacksDelay = 40;
    private int ticksUntilNextAttack = 45;
    private boolean shouldCountTillNextAttack = false;

    public ShardLizardThrasherAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
        entity = ((ShardLizardThrasher) mob);
    }

    @Override
    public void start() {
        super.start();
        attacksDelay = 40;
        ticksUntilNextAttack = 45;
    }

    @Override
    public void tick() {
        super.tick();
        if (shouldCountTillNextAttack) {
            ticksUntilNextAttack = Math.max(ticksUntilNextAttack - 1, 0);
        }
    }

    @Override
    public void stop() {
        entity.setIsAttacking(false);
        super.stop();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        if (isEnemyWithinAttackDistance(enemy, distToEnemySqr)) {
            shouldCountTillNextAttack = true;

            if (!entity.isAttacking()) {
                if (isTimeToStartAttackAnimation()) {
                    entity.startAttackAnimation();
                }
                return;
            }

            float[] damageTicks = entity.getAttackDamageTick();
            int comboDamageTicksReached = 0;

            if (entity.getAttackType() == ShardLizardThrasher.ATTACK_COMBO) {
                for (int i = 0; i < damageTicks.length; i++) {
                    comboDamageTicksReached = i;
                    if (entity.attackAnimationTicks >= damageTicks[i] && damageTicks[i] > entity.getLastComboDamageTicksReached()) {
                        mob.getLookControl().setLookAt(enemy.getX(), enemy.getEyeY(), enemy.getZ());
                        performAttack(enemy, comboDamageTicksReached);
                        entity.setLastComboDamageTicksReached(damageTicks[i]);
                        break;
                    }
                }
            } else {
                for (float tick : damageTicks) {
                    if (!entity.hasAttackDamageApplied() && entity.attackAnimationTicks >= tick) {
                        mob.getLookControl().setLookAt(enemy.getX(), enemy.getEyeY(), enemy.getZ());
                        performAttack(enemy, comboDamageTicksReached);
                        entity.setAttackDamageApplied(true);
                        break;
                    }
                }
            }

            if (!entity.isAttacking()) {
                resetAttackCooldown();
            }
            return;
        }

        resetAttackCooldown();
        shouldCountTillNextAttack = false;
        entity.setIsAttacking(false);
        entity.attackAnimationTicks = 0;
    }

    private boolean isEnemyWithinAttackDistance(LivingEntity enemy, double distToEnemySqr) {
        return distToEnemySqr <= getAttackReachSqr(enemy) + 1.0D;
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

        if (entity.getAttackType() == ShardLizardThrasher.ATTACK_COMBO) {
            enemy.invulnerableTime = 0;
            enemy.hurtTime = 0;
        }

        boolean didDamage = hurtWithoutKnockback(enemy, damageAmount);
        if (!didDamage || enemy.level().isClientSide()) {
            return;
        }

        int attackType = entity.getAttackType();
        if (attackType == ShardLizardThrasher.ATTACK_COMBO) {
            if (comboDamageTicksReached == amount.length - 1) {
                enemy.addEffect(new MobEffectInstance(ModEffects.PARALYSIS.get(), 10 * 20, 0));
            }
        } else if (attackType == ShardLizardThrasher.ATTACK_SLAM) {
            if (mob.getRandom().nextFloat() < 0.15f) {
                enemy.addEffect(new MobEffectInstance(ModEffects.PARALYSIS.get(), 10 * 20, 0));
            }
        } else if (attackType == ShardLizardThrasher.ATTACK_SPIN) {
            if (mob.getRandom().nextFloat() < 0.10f) {
                enemy.addEffect(new MobEffectInstance(ModEffects.PARALYSIS.get(), 10 * 20, 0));
            }
        }
    }

    private boolean hurtWithoutKnockback(LivingEntity target, float damage) {
        net.minecraft.world.phys.Vec3 oldVelocity = target.getDeltaMovement();
        boolean didDamage = target.hurt(mob.damageSources().mobAttack(mob), damage);
        if (didDamage) {
            target.setDeltaMovement(oldVelocity.x, oldVelocity.y, oldVelocity.z);
            target.hurtMarked = true;
        }
        return didDamage;
    }
}