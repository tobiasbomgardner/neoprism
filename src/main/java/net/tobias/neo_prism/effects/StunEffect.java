package net.tobias.neo_prism.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class StunEffect extends MobEffect {
    public StunEffect() {
        super(MobEffectCategory.HARMFUL, 16747520);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "57F1BADC-F545-4D89-B218-751C2FF8053D", (double) -0.5F, AttributeModifier.Operation.ADDITION);
    }

    public boolean getDuration(int duration, int amplifier) {
        return duration > 0;
    }
}