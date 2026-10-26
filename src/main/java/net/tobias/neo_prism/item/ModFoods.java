package net.tobias.neo_prism.item;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties PRISM_COLOR_BREAD =
            new FoodProperties.Builder().nutrition(6).saturationMod(0.7f).build();
    public static final FoodProperties RAW_LIZARD_MEAT =
            new FoodProperties.Builder().nutrition(7).saturationMod(0.75f).build();
    public static final FoodProperties COOKED_LIZARD_MEAT =
            new FoodProperties.Builder().nutrition(9).saturationMod(1.0f).build();
}