package net.tobias.neo_prism.entity.ai.shard_lizards.goals;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.tobias.neo_prism.entity.entities.ShardLizardSlasher;

public class ShardLizardSlasherAttackGoal extends MeleeAttackGoal {
    private final ShardLizardSlasher entity;
    private int attacksDelay = 40;
    private int ticksUntilNextAttack = 45;
    private boolean shouldCountTillNextAttack = false;

    public ShardLizardSlasherAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        entity = ((ShardLizardSlasher) mob);
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
        if (!entity.isComboInProgress()) {
            entity.setIsAttacking(false);
        }
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

            if (entity.isAttacking()) {
                entity.tryForcedComboOnParalyzedTarget();
            }
            boolean attackFinished = entity.advanceAttackAnimation();

            if (!entity.hasAttackDamageApplied() && entity.attackAnimationTicks >= entity.getAttackDamageTick()) {
                mob.getLookControl().setLookAt(enemy.getX(), enemy.getEyeY(), enemy.getZ());
                performAttack(enemy);
                entity.setAttackDamageApplied(true);
            }

            if (attackFinished) {
                resetAttackCooldown();
            }
            return;
        }

        if (entity.isComboInProgress()) {
            boolean attackFinished = entity.advanceAttackAnimation();
            if (attackFinished) {
                resetAttackCooldown();
            }
            shouldCountTillNextAttack = false;
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

    protected void performAttack(LivingEntity enemy) {
        mob.swing(InteractionHand.MAIN_HAND);
        hurtWithoutKnockback(enemy, entity.getCurrentAttackDamage());
    }

    private void hurtWithoutKnockback(LivingEntity target, float damage) {
        net.minecraft.world.phys.Vec3 oldVelocity = target.getDeltaMovement();
        boolean didDamage = target.hurt(mob.damageSources().mobAttack(mob), damage);
        if (didDamage) {
            target.setDeltaMovement(oldVelocity.x, oldVelocity.y, oldVelocity.z);
            target.hurtMarked = true;
        }
    }
}