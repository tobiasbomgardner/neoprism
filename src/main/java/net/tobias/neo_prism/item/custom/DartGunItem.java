package net.tobias.neo_prism.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.tobias.neo_prism.entity.entities.projectiles.AbstractDart;
import net.tobias.neo_prism.item.ModItemTags;
import net.tobias.neo_prism.item.ModItems;

import java.util.function.Predicate;

public class DartGunItem extends ProjectileWeaponItem implements Vanishable {
    public static final int MAX_DRAW_DURATION = 20;
    public static final int DEFAULT_RANGE = 15;
    public static final Predicate<ItemStack> DARTS_ONLY = (item) -> {
        return item.is(ModItemTags.DARTS);
    };

    public DartGunItem(Item.Properties properties) {
        super(properties);
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
            ItemStack itemstack = player.getProjectile(stack);

            int i = this.getUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, level, player, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(ModItems.getItem("dart"));
                }

                float f = getPowerForTime(i);
                if (!((double)f < 0.1D)) {
                    boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((DartItem)itemstack.getItem()).isInfinite(itemstack, stack, player));
                    if (!level.isClientSide) {
                        DartItem dartItem = (DartItem)(itemstack.getItem() instanceof DartItem ? itemstack.getItem() : ModItems.getItem("dart"));
                        AbstractDart abstractDart = dartItem.createDart(level, itemstack, player);
                        abstractDart = customDart(abstractDart);
                        abstractDart.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 1.0F);
                        if (f == 1.0F) {
                            abstractDart.setCritDart(true);
                        }

                        int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
                        if (j > 0) {
                            abstractDart.setBaseDamage(abstractDart.getBaseDamage() + (double)j * 0.5D + 0.5D);
                        }

                        int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
                        if (k > 0) {
                            abstractDart.setKnockback(k);
                        }

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
                            abstractDart.setSecondsOnFire(100);
                        }

                        stack.hurtAndBreak(1, player, (player1) -> {
                            player1.broadcastBreakEvent(player.getUsedItemHand());
                        });
                        if (flag1 || player.getAbilities().instabuild && (itemstack.is(ModItems.getItem("spectral_dart")) || itemstack.is(ModItems.getItem("tipped_dart")))) {
                            abstractDart.pickup = AbstractDart.Pickup.CREATIVE_ONLY;
                        }

                        level.addFreshEntity(abstractDart);
                    }

                    level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.getInventory().removeItem(itemstack);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    /**
     * Gets the velocity of the arrow entity from the dart gun's charge
     */
    public static float getPowerForTime(int charge) {
        float f = (float)charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    /**
     * Returns the action that specifies what animation to play when the item is being used.
     */
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     */
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        boolean flag = !player.getProjectile(itemstack).isEmpty();

        InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, level, player, hand, flag);
        if (ret != null) return ret;

        if (!player.getAbilities().instabuild && !flag) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    /**
     * Get the predicate to match ammunition when searching the player's inventory, not their main/offhand
     */
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return DARTS_ONLY;
    }

    public AbstractDart customDart(AbstractDart dart) {
        return dart;
    }

    public int getDefaultProjectileRange() {
        return 15;
    }
}