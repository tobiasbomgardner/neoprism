package net.tobias.neo_prism.extra.durability_tooltip;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tobias.neo_prism.Neo_Prism;

@Mod.EventBusSubscriber(modid = Neo_Prism.MOD_ID, value = Dist.CLIENT)
public class DurabilityTooltipHandler {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (!stack.isDamageableItem()) {
            return;
        }

        int maxDurability = stack.getMaxDamage();
        if (maxDurability <= 0) {
            return;
        }

        int currentDurability = maxDurability - stack.getDamageValue();
        TooltipColor durabilityColor = TooltipColor.OTHER;
        ChatFormatting baseColor = ChatFormatting.GRAY;
        TextColor reactiveColor = durabilityColor.getColorForDurability(baseColor, currentDurability, maxDurability);
        event.getToolTip().add(Component.translatable("tooltip.neo_prism.durability_tooltip",
                Component.literal(String.valueOf(currentDurability)).withStyle(style -> style.withColor(reactiveColor)),
                Component.literal(String.valueOf(maxDurability)).withStyle(style -> style.withColor(reactiveColor)))
                .withStyle(ChatFormatting.GRAY));
    }
}
