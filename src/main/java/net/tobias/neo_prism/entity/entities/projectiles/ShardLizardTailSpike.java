package net.tobias.neo_prism.entity.entities.projectiles;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.tobias.neo_prism.effects.ModEffects;
import net.tobias.neo_prism.entity.ModEntities;

public class ShardLizardTailSpike extends AbstractArrow {
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(ShardLizardTailSpike.class, EntityDataSerializers.INT);

    public ShardLizardTailSpike(EntityType<ShardLizardTailSpike> entityType, Level level) {
        super(entityType, level);
    }

    public ShardLizardTailSpike(Level level, LivingEntity shooter) {
        super(ModEntities.SHARD_LIZARD_TAIL_SPIKE.get(), shooter, level);
        this.setOwner(shooter);
        this.pickup = Pickup.DISALLOWED;
        this.setBaseDamage(3.0d);
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void onHitEntity(EntityHitResult hit) {
        super.onHitEntity(hit);

        if (level().isClientSide) return;

        Entity hitEntity = hit.getEntity();
        if (getVariant() == 0 && hitEntity instanceof LivingEntity livingEntity) {
            if (random.nextFloat() < 0.25f) {
                livingEntity.addEffect(new MobEffectInstance(ModEffects.PARALYSIS.get(), 10 * 20, 0));
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        Vec3 motion = getDeltaMovement();
        if (motion.lengthSqr() > 0.0001d) {
            double horizontal = Math.sqrt(motion.x * motion.x + motion.z * motion.z);

            setYRot((float) (Mth.atan2(motion.x, motion.z) * (180d / Math.PI)));
            setYRot((float) (Mth.atan2(motion.y, horizontal) * (180d / Math.PI)));
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(VARIANT, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Variant", Tag.TAG_INT)) {
            setVariant(tag.getInt("Variant"));
        }
    }

    public int getVariant() {
        return entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        entityData.set(VARIANT, Mth.clamp(variant, 0, 1));
    }
}