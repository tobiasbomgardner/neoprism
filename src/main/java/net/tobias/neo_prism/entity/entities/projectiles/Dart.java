package net.tobias.neo_prism.entity.entities.projectiles;

import com.google.common.collect.Sets;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.tobias.neo_prism.entity.ModEntities;
import net.tobias.neo_prism.item.ModItems;

import java.util.Collection;
import java.util.Set;

public class Dart extends AbstractDart {
    private static final int EXPOSED_POTION_DECAY_TIME = 600;
    private static final int NO_EFFECT_COLOR = -1;
    private static final EntityDataAccessor<Integer> ID_EFFECT_COLOR = SynchedEntityData.defineId(Dart.class, EntityDataSerializers.INT);
    private static final byte EVENT_POTION_PUFF = 0;
    private Potion potion = Potions.EMPTY;
    private final Set<MobEffectInstance> effects = Sets.newHashSet();
    private boolean fixedColor;

    public Dart(EntityType<? extends Dart> entityType, Level level) {
        super(entityType, level);
    }

    public Dart(Level level, double x, double y, double z) {
        super(ModEntities.DART.get(), x, y, z, level);
    }

    public Dart(Level level, LivingEntity shooter) {
        super(ModEntities.DART.get(), shooter, level);
    }

    public void setEffectsFromItem(ItemStack item) {
        if (item.is(ModItems.getItem("dart"))) {
            this.potion = Potions.EMPTY;
            this.effects.clear();
            this.entityData.set(ID_EFFECT_COLOR, -1);
        }
    }

    public static int getCustomColor(ItemStack item) {
        CompoundTag compoundTag = item.getTag();
        return compoundTag != null && compoundTag.contains("CustomPotionColor", 99) ? compoundTag.getInt("CustomPotionColor") : -1;
    }

    private void updateColor() {
        this.fixedColor = false;
        if (this.potion == Potions.EMPTY && this.effects.isEmpty()) {
            this.entityData.set(ID_EFFECT_COLOR, -1);
        } else {
            this.entityData.set(ID_EFFECT_COLOR, PotionUtils.getColor(PotionUtils.getAllEffects(this.potion, this.effects)));
        }
    }

    public void addEffect(MobEffectInstance effect) {
        this.effects.add(effect);
        this.entityData.set(ID_EFFECT_COLOR, PotionUtils.getColor(PotionUtils.getAllEffects(this.potion, this.effects)));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_EFFECT_COLOR, -1);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (this.inGround) {
                if (this.inGroundTime % 5 == 0) {
                    this.makeParticle(1);
                }
            } else {
                this.makeParticle(2);
            }
        } else if (this.inGround && this.inGroundTime != 0 && !this.effects.isEmpty() && this.inGroundTime >= 600) {
            this.level().broadcastEntityEvent(this, (byte) 0);
            this.potion = Potions.EMPTY;
            this.effects.clear();
            this.entityData.set(ID_EFFECT_COLOR, -1);
        }
    }

    private void makeParticle(int particleAmount) {
        int i = this.getColor();
        if (i != -1 && particleAmount > 0) {
            double d0 = (double) (i >> 16 & 255) / 255.0D;
            double d1 = (double) (i >> 8 & 255) / 255.0D;
            double d2 = (double) (i >> 0 & 255) / 255.0D;

            for (int j = 0; j < particleAmount; ++j) {
                this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d0, d1, d2);
            }
        }
    }

    public int getColor() {
        return this.entityData.get(ID_EFFECT_COLOR);
    }

    private void setFixedColor(int fixedColor) {
        this.fixedColor = true;
        this.entityData.set(ID_EFFECT_COLOR, fixedColor);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.potion != Potions.EMPTY) {
            compound.putString("Potion", BuiltInRegistries.POTION.getKey(this.potion).toString());
        }

        if (this.fixedColor) {
            compound.putInt("Color", this.getColor());
        }

        if (!this.effects.isEmpty()) {
            ListTag listTag = new ListTag();

            for (MobEffectInstance effect : this.effects) {
                listTag.add(effect.save(new CompoundTag()));
            }

            compound.put("CustomPotionEffects", listTag);
        }
    }

    /**
     *  (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Potion", 8)) {
            this.potion = PotionUtils.getPotion(compound);
        }

        for(MobEffectInstance mobeffectinstance : PotionUtils.getCustomEffects(compound)) {
            this.addEffect(mobeffectinstance);
        }

        if (compound.contains("Color", 99)) {
            this.setFixedColor(compound.getInt("Color"));
        } else {
            this.updateColor();
        }
    }

    protected void doPostHurtEffects(LivingEntity livingEntity) {
        super.doPostHurtEffects(livingEntity);
        Entity entity = this.getEffectSource();

        for (MobEffectInstance effect : this.potion.getEffects()) {
            livingEntity.addEffect(new MobEffectInstance(effect.getEffect(), Math.max(effect.mapDuration((duration) -> {
                return duration / 8;
            }), 1), effect.getAmplifier(), effect.isAmbient(), effect.isVisible()), entity);
        }

        if (!this.effects.isEmpty()) {
            for (MobEffectInstance effect : this.effects) {
                livingEntity.addEffect(effect, entity);
            }
        }
    }

    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.getItem("dart"));
    }

    /**
     * Handles an entity event received from a {@link net.minecraft.network.protocol.game.ClientboundEntityEventPacket}.
     */
    public void handleEntityEvent(byte id) {
        if (id == 0) {
            int i = this.getColor();
            if (i != -1) {
                double d0 = (double) (i >> 16 & 255) / 255.0D;
                double d1 = (double) (i >> 8 & 255) / 255.0D;
                double d2 = (double) (i >> 0 & 255) / 255.0D;

                for(int j = 0; j < 20; ++j) {
                    this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d0, d1, d2);
                }
            }
        } else {
            super.handleEntityEvent(id);
        }

    }
}