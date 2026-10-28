package net.tobias.neo_prism.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.util.ModTags;

import java.util.List;

public class ModToolTiers {
    public static final Tier PRISMATIC = TierSortingRegistry.registerTier(new ForgeTier(5, 2500, 10.0f,
            5.0f, 20, ModTags.Blocks.NEEDS_PRISMATIC_TOOL,
            () -> Ingredient.of(ModItems.MODDED_TIER_MATERIALS.get("prismatic_gem").get())), new ResourceLocation(Neo_Prism.MOD_ID, "prismatic"),
            List.of(Tiers.NETHERITE), List.of());
}