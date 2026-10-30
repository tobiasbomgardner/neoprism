package net.tobias.neo_prism.entity.entities.projectiles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tobias.neo_prism.effects.ModEffects;
import net.tobias.neo_prism.entity.ModEntities;
import net.tobias.neo_prism.item.ModItems;

public class StunDart extends AbstractDart {
    private int duration = 200;

    public StunDart(EntityType<? extends StunDart> entityType, Level level) {
        super(entityType, level);
    }

    public StunDart(Level level, LivingEntity shooter) {
        super(ModEntities.STUN_DART.get(), shooter, level);
    }

    public StunDart(Level level, double x, double y, double z) {
        super(ModEntities.STUN_DART.get(), x, y, z, level);
    }



    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        if (this.level().isClientSide && !this.inGround) {
            this.level().addParticle(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }

    }

    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.getItem("stun_dart"));
    }

    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        MobEffectInstance mobeffectinstance = new MobEffectInstance(ModEffects.STUN.get(), this.duration, 0);
        living.addEffect(mobeffectinstance, this.getEffectSource());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Duration")) {
            this.duration = compound.getInt("Duration");
        }

    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Duration", this.duration);
    }
}