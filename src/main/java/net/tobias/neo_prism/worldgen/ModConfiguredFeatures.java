package net.tobias.neo_prism.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.block.ModBlocks;
import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_OVERWOLRD_SS_ORES_KEY = registerKey("red_ss_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_OVERWOLRD_SS_ORES_KEY = registerKey("orange_ss_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_OVERWOLRD_SS_ORES_KEY = registerKey("yellow_ss_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREEN_OVERWOLRD_SS_ORES_KEY = registerKey("green_ss_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_OVERWOLRD_SS_ORES_KEY = registerKey("blue_ss_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CYAN_OVERWOLRD_SS_ORES_KEY = registerKey("cyan_ss_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_OVERWOLRD_SS_ORES_KEY = registerKey("purple_ss_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_OVERWOLRD_SS_ORES_KEY = registerKey("pink_ss_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_OVERWOLRD_SS_ORES_KEY = registerKey("white_ss_ore");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceable = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> redOverworldSSOres = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.ALL.get("red_shattered_shard_ore").get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceable, ModBlocks.ALL.get("red_shattered_shard_deepslate_ore").get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> orangeOverworldSSOres = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.ALL.get("orange_shattered_shard_ore").get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceable, ModBlocks.ALL.get("orange_shattered_shard_deepslate_ore").get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> yellowOverworldSSOres = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.ALL.get("yellow_shattered_shard_ore").get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceable, ModBlocks.ALL.get("yellow_shattered_shard_deepslate_ore").get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> greenOverworldSSOres = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.ALL.get("green_shattered_shard_ore").get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceable, ModBlocks.ALL.get("green_shattered_shard_deepslate_ore").get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> blueOverworldSSOres = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.ALL.get("blue_shattered_shard_ore").get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceable, ModBlocks.ALL.get("blue_shattered_shard_deepslate_ore").get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> cyanOverworldSSOres = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.ALL.get("cyan_shattered_shard_ore").get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceable, ModBlocks.ALL.get("cyan_shattered_shard_deepslate_ore").get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> purpleOverworldSSOres = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.ALL.get("purple_shattered_shard_ore").get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceable, ModBlocks.ALL.get("purple_shattered_shard_deepslate_ore").get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> pinkOverworldSSOres = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.ALL.get("pink_shattered_shard_ore").get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceable, ModBlocks.ALL.get("pink_shattered_shard_deepslate_ore").get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> whiteOverworldSSOres = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.ALL.get("white_shattered_shard_ore").get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceable, ModBlocks.ALL.get("white_shattered_shard_deepslate_ore").get().defaultBlockState())
        );

        register(context, RED_OVERWOLRD_SS_ORES_KEY, Feature.ORE, new OreConfiguration(redOverworldSSOres, 3));
        register(context, ORANGE_OVERWOLRD_SS_ORES_KEY, Feature.ORE, new OreConfiguration(orangeOverworldSSOres, 3));
        register(context, YELLOW_OVERWOLRD_SS_ORES_KEY, Feature.ORE, new OreConfiguration(yellowOverworldSSOres, 3));
        register(context, GREEN_OVERWOLRD_SS_ORES_KEY, Feature.ORE, new OreConfiguration(greenOverworldSSOres, 3));
        register(context, BLUE_OVERWOLRD_SS_ORES_KEY, Feature.ORE, new OreConfiguration(blueOverworldSSOres, 3));
        register(context, CYAN_OVERWOLRD_SS_ORES_KEY, Feature.ORE, new OreConfiguration(cyanOverworldSSOres, 3));
        register(context, PURPLE_OVERWOLRD_SS_ORES_KEY, Feature.ORE, new OreConfiguration(purpleOverworldSSOres, 3));
        register(context, PINK_OVERWOLRD_SS_ORES_KEY, Feature.ORE, new OreConfiguration(pinkOverworldSSOres, 3));
        register(context, WHITE_OVERWOLRD_SS_ORES_KEY, Feature.ORE, new OreConfiguration(whiteOverworldSSOres, 3));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Neo_Prism.MOD_ID, name));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}