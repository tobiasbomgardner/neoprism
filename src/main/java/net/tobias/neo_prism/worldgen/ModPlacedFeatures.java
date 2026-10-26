package net.tobias.neo_prism.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.tobias.neo_prism.Neo_Prism;
import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> RED_SS_ORE_PLACED_KEY = registerKey("red_ss_ore_placed");
    public static final ResourceKey<PlacedFeature> ORANGE_SS_ORE_PLACED_KEY = registerKey("orange_ss_ore_placed");
    public static final ResourceKey<PlacedFeature> YELLOW_SS_ORE_PLACED_KEY = registerKey("yellow_ss_ore_placed");
    public static final ResourceKey<PlacedFeature> GREEN_SS_ORE_PLACED_KEY = registerKey("green_ss_ore_placed");
    public static final ResourceKey<PlacedFeature> BLUE_SS_ORE_PLACED_KEY = registerKey("blue_ss_ore_placed");
    public static final ResourceKey<PlacedFeature> CYAN_SS_ORE_PLACED_KEY = registerKey("cyan_ss_ore_placed");
    public static final ResourceKey<PlacedFeature> PURPLE_SS_ORE_PLACED_KEY = registerKey("purple_ss_ore_placed");
    public static final ResourceKey<PlacedFeature> PINK_SS_ORE_PLACED_KEY = registerKey("pink_ss_ore_placed");
    public static final ResourceKey<PlacedFeature> WHITE_SS_ORE_PLACED_KEY = registerKey("white_ss_ore_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, RED_SS_ORE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.RED_OVERWOLRD_SS_ORES_KEY),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),
                        VerticalAnchor.absolute(50))));
        register(context, ORANGE_SS_ORE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.ORANGE_OVERWOLRD_SS_ORES_KEY),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),
                        VerticalAnchor.absolute(50))));
        register(context, YELLOW_SS_ORE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.YELLOW_OVERWOLRD_SS_ORES_KEY),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),
                        VerticalAnchor.absolute(50))));
        register(context, GREEN_SS_ORE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.GREEN_OVERWOLRD_SS_ORES_KEY),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),
                        VerticalAnchor.absolute(50))));
        register(context, BLUE_SS_ORE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.BLUE_OVERWOLRD_SS_ORES_KEY),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),
                        VerticalAnchor.absolute(50))));
        register(context, CYAN_SS_ORE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.CYAN_OVERWOLRD_SS_ORES_KEY),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),
                        VerticalAnchor.absolute(50))));
        register(context, PURPLE_SS_ORE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.PURPLE_OVERWOLRD_SS_ORES_KEY),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),
                        VerticalAnchor.absolute(50))));
        register(context, PINK_SS_ORE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.PINK_OVERWOLRD_SS_ORES_KEY),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),
                        VerticalAnchor.absolute(50))));
        register(context, WHITE_SS_ORE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.WHITE_OVERWOLRD_SS_ORES_KEY),
                ModOrePlacement.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64),
                        VerticalAnchor.absolute(50))));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Neo_Prism.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
                                 Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}