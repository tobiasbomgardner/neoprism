package net.tobias.neo_prism.item.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.tobias.neo_prism.entity.entities.projectiles.AbstractDart;
import net.tobias.neo_prism.entity.entities.projectiles.Dart;

public class DartItem extends Item {
    public DartItem(Properties pProperties) {
        super(pProperties);
    }

    public AbstractDart createDart(Level level, ItemStack item, LivingEntity shooter) {
        Dart dart = new Dart(level, shooter);
        dart.setEffectsFromItem(item);
        return dart;
    }

    public boolean isInfinite(ItemStack item, ItemStack dartGun, Player player) {
        int enchant = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, dartGun);
        return enchant > 0 && this.getClass() == DartItem.class;
    }
}