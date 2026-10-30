package net.tobias.neo_prism.item.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.tobias.neo_prism.entity.entities.projectiles.AbstractDart;
import net.tobias.neo_prism.entity.entities.projectiles.StunDart;

public class StunDartItem extends Item {
    public StunDartItem(Properties pProperties) {
        super(pProperties);
    }

    public AbstractDart createDart(Level level, ItemStack item, LivingEntity shooter) {
        return new StunDart(level, shooter);
    }
}