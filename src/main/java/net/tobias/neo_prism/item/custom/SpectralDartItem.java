package net.tobias.neo_prism.item.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tobias.neo_prism.entity.entities.projectiles.AbstractDart;
import net.tobias.neo_prism.entity.entities.projectiles.SpectralDart;

public class SpectralDartItem extends DartItem {
    public SpectralDartItem(Properties properties) {
        super(properties);
    }

    public AbstractDart createDart(Level level, ItemStack stack, LivingEntity shooter) {
        return new SpectralDart(level, shooter);
    }
}