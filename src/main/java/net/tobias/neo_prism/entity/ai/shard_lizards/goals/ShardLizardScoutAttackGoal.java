package net.tobias.neo_prism.entity.ai.shard_lizards.goals;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.phys.Vec3;
import net.tobias.neo_prism.entity.entities.ShardLizardScout;

public class ShardLizardScoutAttackGoal extends MeleeAttackGoal {
    private final ShardLizardScout entity;
    private int attackDelay = 25;
    private int ticksUntilNextAttack = 10;
    private boolean shouldCountTillNextAttack = false;

    public ShardLizardScoutAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
        entity = ((ShardLizardScout) mob);
    }

    @Override
    public void start() {
        super.start();
        attackDelay = 25;
        ticksUntilNextAttack = 10;
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

            if (isTimeToStartAttackAnimation()) {
                entity.setIsAttacking(true);
            }

            if (isTimeToAttack()) {
                mob.getLookControl().setLookAt(enemy.getX(), enemy.getEyeY(), enemy.getZ());
                performAttack(enemy);
            }
        } else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setIsAttacking(false);
            entity.attackAnimationTicks = 0;
        }
    }

    private boolean isEnemyWithinAttackDistance(LivingEntity enemy, double distToEnemySqr) {
        return distToEnemySqr <= getAttackReachSqr(enemy) + 1.0D;
    }

    @Override
    protected void resetAttackCooldown() {
        ticksUntilNextAttack = adjustedTickDelay(attackDelay * 2);
    }

    @Override
    protected boolean isTimeToAttack() {
        return ticksUntilNextAttack <= 0;
    }

    protected boolean isTimeToStartAttackAnimation() {
        return ticksUntilNextAttack <= attackDelay;
    }

    protected int getTicksUntilNextAttack() {
        return ticksUntilNextAttack;
    }

    protected void performAttack(LivingEntity enemy) {
        resetAttackCooldown();
        mob.swing(InteractionHand.MAIN_HAND);
        hurtWithoutKnockback(enemy, 3.0f);
    }

    private void hurtWithoutKnockback(LivingEntity target, float damage) {
        Vec3 oldVelocity = target.getDeltaMovement();
        boolean didDamage = target.hurt(mob.damageSources().mobAttack(mob), damage);
        if (didDamage) {
            target.setDeltaMovement(oldVelocity.x, oldVelocity.y, oldVelocity.z);
            target.hurtMarked = true;
        }
    }
}