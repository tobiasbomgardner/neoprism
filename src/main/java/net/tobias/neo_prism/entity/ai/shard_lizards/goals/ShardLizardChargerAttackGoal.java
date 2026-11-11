package net.tobias.neo_prism.entity.ai.shard_lizards.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.tobias.neo_prism.entity.entities.ShardLizardCharger;
import org.joml.Vector3f;

import java.util.EnumSet;

public class ShardLizardChargerAttackGoal extends Goal {
    private static final double BITE_RANGE = 1.0D;
    private static final double CHARGE_START_RANGE = 12.0D;
    private static final double RETREAT_RANGE = 10.0D;
    private static final float BITE_DAMAGE = 3.0F;
    private static final float DASH_DAMAGE = 7.0F;
    private static final int ATTACK_INTERVAL = 30;
    private static final int DASH_COOLDOWN = 80;
    private static final int PREPARE_TICKS = 60;
    private static final int POST_PREPARE_DELAY = 5;
    private static final int DASH_DURATION = 80;
    private static final int POST_DASH_RECOVERY_TICKS = 20;

    private final ShardLizardCharger entity;
    private LivingEntity target;
    private Vec3 chargeTarget;
    private Vec3 dashTarget;
    private boolean preparingCharge;
    private int attackCooldown;
    private int prepareTicks;
    private int dashTicks;
    private int postDashRecoveryTicks;

    public ShardLizardChargerAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        entity = (ShardLizardCharger) mob;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = entity.getTarget();
        return target != null && target.isAlive() && !entity.isCustomDying();
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void start() {
        target = entity.getTarget();
        attackCooldown = 0;
        prepareTicks = 0;
        dashTicks = 0;
        postDashRecoveryTicks = 0;
        chargeTarget = null;
        dashTarget = null;
        preparingCharge = false;
        entity.setIsAttacking(false);
        entity.cancelDash();
    }

    @Override
    public void stop() {
        entity.setIsAttacking(false);
        entity.cancelDash();
        chargeTarget = null;
        dashTarget = null;
        preparingCharge = false;
        prepareTicks = 0;
        dashTicks = 0;
        postDashRecoveryTicks = 0;
        entity.getNavigation().stop();
    }

    @Override
    public void tick() {
        target = entity.getTarget();
        if (target == null || !target.isAlive()) {
            return;
        }

        if (attackCooldown > 0) {
            --attackCooldown;
        }

        if (entity.getDashCooldown() > 0) {
            entity.setDashCooldown(entity.getDashCooldown() - 1);
        }

        if (entity.isDashing()) {
            handleDash();
            return;
        }

        if (postDashRecoveryTicks > 0) {
            --postDashRecoveryTicks;
            entity.getNavigation().stop();
            entity.setDeltaMovement(0.0D, entity.getDeltaMovement().y * 0.1D, 0.0D);
            return;
        }

        double distance = entity.distanceTo(target);
        double distSq = entity.distanceToSqr(target);

        if (isTargetWithinStrikeRange(target, BITE_RANGE)) {
            attackCooldown = Math.max(attackCooldown, 0);
            if (attackCooldown <= 0) {
                performBite();
            }
            return;
        }

        if (preparingCharge) {
            if (chargeTarget == null) {
                preparingCharge = false;
                return;
            }

            if (prepareTicks < PREPARE_TICKS) {
                chargeTarget = chooseChargeTarget();
            }

            entity.getNavigation().stop();
            entity.setDeltaMovement(0.0D, entity.getDeltaMovement().y * 0.1D, 0.0D);
            entity.getLookControl().setLookAt(chargeTarget.x, chargeTarget.y, chargeTarget.z);
            spawnTrail(chargeTarget);
            prepareTicks++;

            if (prepareTicks >= PREPARE_TICKS && prepareTicks < PREPARE_TICKS + POST_PREPARE_DELAY) {
                return;
            }

            if (prepareTicks >= PREPARE_TICKS + POST_PREPARE_DELAY) {
                beginDash();
            }
            return;
        }

        if (entity.getDashCooldown() > 0) {
            if (distance <= RETREAT_RANGE) {
                runAwayFromTarget();
            } else {
                entity.getNavigation().stop();
                entity.setDeltaMovement(0.0D, entity.getDeltaMovement().y * 0.1D, 0.0D);
            }
            return;
        }

        if (entity.getDashCooldown() <= 0 && distance <= CHARGE_START_RANGE) {
            prepareCharge();
            return;
        }

        if (distance <= RETREAT_RANGE) {
            runAwayFromTarget();
            return;
        }

        entity.getNavigation().moveTo(target, 1.1D);
    }

    private void decideNearRangeBehavior() {
        if (target == null) {
            return;
        }

        double distance = entity.distanceTo(target);
        if (distance > RETREAT_RANGE) {
            prepareCharge();
            return;
        }

        Vec3 retreatDirection = entity.position().subtract(target.position()).normalize();
        Vec3 safeDirection = findSafeDirection(retreatDirection);
        Vec3 retreatPoint = target.position().add(safeDirection.scale(RETREAT_RANGE));
        entity.getNavigation().moveTo(retreatPoint.x, retreatPoint.y, retreatPoint.z, 1.25D);
    }

    private void decideChargeBehavior() {
        if (target == null) {
            return;
        }

        double distance = entity.distanceTo(target);
        if (distance <= BITE_RANGE) {
            performBite();
            return;
        }

        if (distance <= RETREAT_RANGE) {
            runAwayFromTarget();
            return;
        }

        prepareCharge();
    }

    private void prepareCharge() {
        if (target == null) {
            return;
        }

        chargeTarget = chooseChargeTarget();
        preparingCharge = true;
        prepareTicks = 0;
        entity.getNavigation().stop();
        entity.setDeltaMovement(0.0D, entity.getDeltaMovement().y, 0.0D);
        spawnTrail(chargeTarget);
    }

    private void beginDash() {
        if (chargeTarget == null) {
            return;
        }

        dashTarget = chargeTarget;
        Vec3 direction = dashTarget.subtract(entity.getBoundingBox().getCenter());
        if (direction.lengthSqr() <= 0.01D) {
            chargeTarget = null;
            dashTarget = null;
            preparingCharge = false;
            prepareTicks = 0;
            return;
        }

        entity.startDash(direction);
        entity.setDashCooldown(DASH_COOLDOWN);
        entity.setIsAttacking(true);
        dashTicks = 0;
        prepareTicks = 0;
        preparingCharge = false;
        chargeTarget = null;
    }

    private void handleDash() {
        if (target == null || dashTarget == null) {
            return;
        }

        dashTicks++;

        Vec3 dashVector = dashTarget.subtract(entity.position());
        if (dashVector.lengthSqr() > 0.25D) {
            entity.setDeltaMovement(dashVector.normalize().scale(2.6D));
        }

        if (entity.distanceToSqr(dashTarget) <= 1.5D || entity.horizontalCollision || dashTicks >= DASH_DURATION) {
            entity.setDeltaMovement(0.0D, entity.getDeltaMovement().y * 0.1D, 0.0D);
            entity.cancelDash();
            entity.setIsAttacking(false);
            if (isTargetWithinStrikeRange(target, BITE_RANGE + 0.5D)) {
                applyDashDamage(target);
            }
            attackCooldown = ATTACK_INTERVAL;
            entity.setDashCooldown(DASH_COOLDOWN);
            postDashRecoveryTicks = POST_DASH_RECOVERY_TICKS;
            chargeTarget = null;
            dashTarget = null;
            preparingCharge = false;
            prepareTicks = 0;
            dashTicks = 0;
            return;
        }
    }

    private void runAwayFromTarget() {
        if (target == null) {
            return;
        }

        Vec3 away = entity.position().subtract(target.position()).normalize();
        Vec3 safeDirection = findSafeDirection(away);
        Vec3 destination = entity.position().add(safeDirection.scale(4.0D));
        entity.getNavigation().moveTo(destination.x, destination.y, destination.z, 1.3D);
    }

    private Vec3 chooseChargeTarget() {
        if (target == null) {
            return entity.getBoundingBox().getCenter();
        }

        Vec3 targetFeet = new Vec3(target.getX(), target.getY(), target.getZ());
        Vec3[] candidates = new Vec3[] {
                targetFeet,
                new Vec3(targetFeet.x + 0.5D, targetFeet.y, targetFeet.z),
                new Vec3(targetFeet.x - 0.5D, targetFeet.y, targetFeet.z),
                new Vec3(targetFeet.x, targetFeet.y, targetFeet.z + 0.5D),
                new Vec3(targetFeet.x, targetFeet.y, targetFeet.z - 0.5D),
                new Vec3(targetFeet.x + 0.75D, targetFeet.y, targetFeet.z + 0.75D),
                new Vec3(targetFeet.x - 0.75D, targetFeet.y, targetFeet.z - 0.75D),
                new Vec3(targetFeet.x + 0.75D, targetFeet.y, targetFeet.z - 0.75D),
                new Vec3(targetFeet.x - 0.75D, targetFeet.y, targetFeet.z + 0.75D)
        };

        for (Vec3 candidate : candidates) {
            if (isSafePosition(candidate)) {
                return candidate;
            }
        }

        return targetFeet;
    }

    private void performBite() {
        if (target == null) {
            return;
        }

        if (!isTargetWithinStrikeRange(target, BITE_RANGE)) {
            return;
        }

        entity.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
        entity.setIsAttacking(true);
        entity.swing(InteractionHand.MAIN_HAND);
        applyBiteDamage(target);
        entity.setIsAttacking(false);
        attackCooldown = ATTACK_INTERVAL;
        entity.setDashCooldown(35);
    }

    private void applyBiteDamage(LivingEntity target) {
        hurtWithoutKnockback(target, BITE_DAMAGE);
    }

    private void applyDashDamage(LivingEntity target) {
        hurtWithoutKnockback(target, DASH_DAMAGE);
    }

    private void hurtWithoutKnockback(LivingEntity target, float damage) {
        Vec3 oldVelocity = target.getDeltaMovement();
        boolean didDamage = target.hurt(entity.damageSources().mobAttack(entity), damage);
        if (didDamage) {
            target.setDeltaMovement(oldVelocity.x, oldVelocity.y, oldVelocity.z);
            target.hurtMarked = true;
        }
    }

    private boolean isTargetWithinStrikeRange(LivingEntity target, double baseRange) {
        return entity.distanceToSqr(target) <= (baseRange * baseRange) + 1.0D;
    }

    private Vec3 findSafeDirection(Vec3 preferred) {
        Vec3[] directions = new Vec3[] {
                preferred,
                new Vec3(preferred.z, preferred.y, -preferred.x),
                new Vec3(-preferred.z, preferred.y, preferred.x),
                new Vec3(-preferred.x, preferred.y, -preferred.z),
                new Vec3(preferred.x, preferred.y, 0.0D),
                new Vec3(0.0D, preferred.y, preferred.z)
        };

        for (Vec3 direction : directions) {
            Vec3 normalized = direction.normalize();
            if (isPathSafe(entity.position(), normalized, 3.0D)) {
                return normalized;
            }
        }

        return preferred.normalize();
    }

    private boolean isPathSafe(Vec3 start, Vec3 direction, double maxDistance) {
        for (double distance = 0.5D; distance <= maxDistance; distance += 0.5D) {
            Vec3 sample = start.add(direction.scale(distance));
            if (!isSafePosition(sample)) {
                return false;
            }
        }
        return true;
    }

    private boolean isSafePosition(Vec3 position) {
        for (double yOffset = -1.0D; yOffset <= 1.0D; yOffset += 1.0D) {
            BlockPos pos = BlockPos.containing(position.x, position.y + yOffset, position.z);
            BlockState state = entity.level().getBlockState(pos);
            FluidState fluid = entity.level().getFluidState(pos);
            if (!state.isAir() || !fluid.isEmpty()) {
                return false;
            }
        }

        BlockPos groundPos = BlockPos.containing(position.x, position.y - 1.0D, position.z);
        if (!entity.level().getBlockState(groundPos).isAir()) {
            return true;
        }

        return false;
    }

    private void spawnTrail(Vec3 target) {
        if (!(entity.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        Vec3 start = entity.getBoundingBox().getCenter();
        Vec3 delta = target.subtract(start);
        int steps = Math.max(1, (int) Math.ceil(delta.length() * 3.0D));

        DustParticleOptions options = new DustParticleOptions(new Vector3f(0.3F, 0.3F, 0.3F), 1.0F);
        for (int i = 0; i <= steps; i++) {
            double t = i / (double) steps;
            Vec3 point = start.add(delta.scale(t));
            serverLevel.sendParticles(options, point.x, point.y, point.z, 2, 0.04D, 0.04D, 0.04D, 0.0D);
        }
    }
}