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

public class ShardLizardChargerBiteAttackGoal extends Goal {
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

    public ShardLizardChargerBiteAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        this.entity = (ShardLizardCharger) mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.entity.getTarget();
        return target != null && target.isAlive() && !this.entity.isCustomDying();
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse();
    }

    @Override
    public void start() {
        this.target = this.entity.getTarget();
        this.attackCooldown = 0;
        this.prepareTicks = 0;
        this.dashTicks = 0;
        this.postDashRecoveryTicks = 0;
        this.chargeTarget = null;
        this.dashTarget = null;
        this.preparingCharge = false;
        this.entity.setIsAttacking(false);
        this.entity.cancelDash();
    }

    @Override
    public void stop() {
        this.entity.setIsAttacking(false);
        this.entity.cancelDash();
        this.chargeTarget = null;
        this.dashTarget = null;
        this.preparingCharge = false;
        this.prepareTicks = 0;
        this.dashTicks = 0;
        this.postDashRecoveryTicks = 0;
        this.entity.getNavigation().stop();
    }

    @Override
    public void tick() {
        this.target = this.entity.getTarget();
        if (this.target == null || !this.target.isAlive()) {
            return;
        }

        if (this.attackCooldown > 0) {
            --this.attackCooldown;
        }

        if (this.entity.getDashCooldown() > 0) {
            this.entity.setDashCooldown(this.entity.getDashCooldown() - 1);
        }

        if (this.entity.isDashing()) {
            this.handleDash();
            return;
        }

        if (this.postDashRecoveryTicks > 0) {
            --this.postDashRecoveryTicks;
            this.entity.getNavigation().stop();
            this.entity.setDeltaMovement(0.0D, this.entity.getDeltaMovement().y * 0.1D, 0.0D);
            return;
        }

        double distance = this.entity.distanceTo(this.target);
        double distSq = this.entity.distanceToSqr(this.target);

        if (distSq <= BITE_RANGE * BITE_RANGE) {
            this.attackCooldown = Math.max(this.attackCooldown, 0);
            if (this.attackCooldown <= 0) {
                this.performBite();
            }
            return;
        }

        if (this.preparingCharge) {
            if (this.chargeTarget == null) {
                this.preparingCharge = false;
                return;
            }

            if (this.prepareTicks < PREPARE_TICKS) {
                this.chargeTarget = this.chooseChargeTarget();
            }

            this.entity.getNavigation().stop();
            this.entity.setDeltaMovement(0.0D, this.entity.getDeltaMovement().y * 0.1D, 0.0D);
            this.entity.getLookControl().setLookAt(this.chargeTarget.x, this.chargeTarget.y, this.chargeTarget.z);
            this.spawnTrail(this.chargeTarget);
            this.prepareTicks++;

            if (this.prepareTicks >= PREPARE_TICKS && this.prepareTicks < PREPARE_TICKS + POST_PREPARE_DELAY) {
                return;
            }

            if (this.prepareTicks >= PREPARE_TICKS + POST_PREPARE_DELAY) {
                this.beginDash();
            }
            return;
        }

        if (this.entity.getDashCooldown() > 0) {
            if (distance <= RETREAT_RANGE) {
                this.runAwayFromTarget();
            } else {
                this.entity.getNavigation().stop();
                this.entity.setDeltaMovement(0.0D, this.entity.getDeltaMovement().y * 0.1D, 0.0D);
            }
            return;
        }

        if (this.entity.getDashCooldown() <= 0 && distance <= CHARGE_START_RANGE) {
            this.prepareCharge();
            return;
        }

        if (distance <= RETREAT_RANGE) {
            this.runAwayFromTarget();
            return;
        }

        this.entity.getNavigation().moveTo(this.target, 1.1D);
    }

    private void decideNearRangeBehavior() {
        if (this.target == null) {
            return;
        }

        double distance = this.entity.distanceTo(this.target);
        if (distance > RETREAT_RANGE) {
            this.prepareCharge();
            return;
        }

        Vec3 retreatDirection = this.entity.position().subtract(this.target.position()).normalize();
        Vec3 safeDirection = this.findSafeDirection(retreatDirection);
        Vec3 retreatPoint = this.target.position().add(safeDirection.scale(RETREAT_RANGE));
        this.entity.getNavigation().moveTo(retreatPoint.x, retreatPoint.y, retreatPoint.z, 1.25D);
    }

    private void decideChargeBehavior() {
        if (this.target == null) {
            return;
        }

        double distance = this.entity.distanceTo(this.target);
        if (distance <= BITE_RANGE) {
            this.performBite();
            return;
        }

        if (distance <= RETREAT_RANGE) {
            this.runAwayFromTarget();
            return;
        }

        this.prepareCharge();
    }

    private void prepareCharge() {
        if (this.target == null) {
            return;
        }

        this.chargeTarget = this.chooseChargeTarget();
        this.preparingCharge = true;
        this.prepareTicks = 0;
        this.entity.getNavigation().stop();
        this.entity.setDeltaMovement(0.0D, this.entity.getDeltaMovement().y, 0.0D);
        this.spawnTrail(this.chargeTarget);
    }

    private void beginDash() {
        if (this.chargeTarget == null) {
            return;
        }

        this.dashTarget = this.chargeTarget;
        Vec3 direction = this.dashTarget.subtract(this.entity.getBoundingBox().getCenter());
        if (direction.lengthSqr() <= 0.01D) {
            this.chargeTarget = null;
            this.dashTarget = null;
            this.preparingCharge = false;
            this.prepareTicks = 0;
            return;
        }

        this.entity.startDash(direction);
        this.entity.setDashCooldown(DASH_COOLDOWN);
        this.entity.setIsAttacking(true);
        this.dashTicks = 0;
        this.prepareTicks = 0;
        this.preparingCharge = false;
        this.chargeTarget = null;
    }

    private void handleDash() {
        if (this.target == null || this.dashTarget == null) {
            return;
        }

        this.dashTicks++;

        Vec3 dashVector = this.dashTarget.subtract(this.entity.position());
        if (dashVector.lengthSqr() > 0.25D) {
            this.entity.setDeltaMovement(dashVector.normalize().scale(2.6D));
        }

        if (this.entity.distanceToSqr(this.dashTarget) <= 1.5D || this.entity.horizontalCollision || this.dashTicks >= DASH_DURATION) {
            this.entity.setDeltaMovement(0.0D, this.entity.getDeltaMovement().y * 0.1D, 0.0D);
            this.entity.cancelDash();
            this.entity.setIsAttacking(false);
            if (this.entity.distanceToSqr(this.target) <= (BITE_RANGE + 0.5D) * (BITE_RANGE + 0.5D)) {
                this.applyDashDamage(this.target);
            }
            this.attackCooldown = ATTACK_INTERVAL;
            this.entity.setDashCooldown(DASH_COOLDOWN);
            this.postDashRecoveryTicks = POST_DASH_RECOVERY_TICKS;
            this.chargeTarget = null;
            this.dashTarget = null;
            this.preparingCharge = false;
            this.prepareTicks = 0;
            this.dashTicks = 0;
            return;
        }
    }

    private void runAwayFromTarget() {
        if (this.target == null) {
            return;
        }

        Vec3 away = this.entity.position().subtract(this.target.position()).normalize();
        Vec3 safeDirection = this.findSafeDirection(away);
        Vec3 destination = this.entity.position().add(safeDirection.scale(4.0D));
        this.entity.getNavigation().moveTo(destination.x, destination.y, destination.z, 1.3D);
    }

    private Vec3 chooseChargeTarget() {
        if (this.target == null) {
            return this.entity.getBoundingBox().getCenter();
        }

        Vec3 targetFeet = new Vec3(this.target.getX(), this.target.getY(), this.target.getZ());
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
            if (this.isSafePosition(candidate)) {
                return candidate;
            }
        }

        return targetFeet;
    }

    private void performBite() {
        if (this.target == null) {
            return;
        }

        if (this.entity.distanceToSqr(this.target) > BITE_RANGE * BITE_RANGE) {
            return;
        }

        this.entity.getLookControl().setLookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
        this.entity.setIsAttacking(true);
        this.entity.swing(InteractionHand.MAIN_HAND);
        this.applyBiteDamage(this.target);
        this.entity.setIsAttacking(false);
        this.attackCooldown = ATTACK_INTERVAL;
        this.entity.setDashCooldown(35);
    }

    private void applyBiteDamage(LivingEntity target) {
        target.hurt(this.entity.damageSources().mobAttack(this.entity), BITE_DAMAGE);
    }

    private void applyDashDamage(LivingEntity target) {
        target.hurt(this.entity.damageSources().mobAttack(this.entity), DASH_DAMAGE);
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
            if (this.isPathSafe(this.entity.position(), normalized, 3.0D)) {
                return normalized;
            }
        }

        return preferred.normalize();
    }

    private boolean isPathSafe(Vec3 start, Vec3 direction, double maxDistance) {
        for (double distance = 0.5D; distance <= maxDistance; distance += 0.5D) {
            Vec3 sample = start.add(direction.scale(distance));
            if (!this.isSafePosition(sample)) {
                return false;
            }
        }
        return true;
    }

    private boolean isSafePosition(Vec3 position) {
        for (double yOffset = -1.0D; yOffset <= 1.0D; yOffset += 1.0D) {
            BlockPos pos = BlockPos.containing(position.x, position.y + yOffset, position.z);
            BlockState state = this.entity.level().getBlockState(pos);
            FluidState fluid = this.entity.level().getFluidState(pos);
            if (!state.isAir() || !fluid.isEmpty()) {
                return false;
            }
        }

        BlockPos groundPos = BlockPos.containing(position.x, position.y - 1.0D, position.z);
        if (!this.entity.level().getBlockState(groundPos).isAir()) {
            return true;
        }

        return false;
    }

    private void spawnTrail(Vec3 target) {
        if (!(this.entity.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        Vec3 start = this.entity.getBoundingBox().getCenter();
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