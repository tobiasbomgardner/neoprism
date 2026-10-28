package net.tobias.neo_prism.extra.durability_tooltip;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextColor;

public enum TooltipColor {
    BASE,
    GOLD,
    OTHER;

    public TextColor getColorForDurability(ChatFormatting baseColor, int durability, int maxDurability) {
        switch (this) {
            case GOLD -> {
                return TextColor.fromRgb(0xFFD700);
            }
            case OTHER -> {
                if (maxDurability <= 0) {
                    TextColor fallbackColor = TextColor.fromLegacyFormat(baseColor);
                    return fallbackColor != null ? fallbackColor : TextColor.fromRgb(0xFFFFFF);
                }

                float durabilityRatio = (float) durability / (float) maxDurability;
                durabilityRatio = Math.max(0.0F, Math.min(1.0F, durabilityRatio));

                int red = Math.round(255.0F * (1.0F - durabilityRatio));
                int green = Math.round(255.0F * durabilityRatio);
                int rgb = (red << 16) | (green << 8);
                return TextColor.fromRgb(rgb);
            }
            default -> {
                TextColor fallbackColor = TextColor.fromLegacyFormat(baseColor);
                return fallbackColor != null ? fallbackColor : TextColor.fromRgb(0xFFFFFF);
            }
        }
    }
}