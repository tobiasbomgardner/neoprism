package net.tobias.neo_prism.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.tobias.neo_prism.block.ModBlocks;
import net.tobias.neo_prism.datagen.loot.ModBlockLootTables;

import java.util.List;
import java.util.Set;

public class ModLootTableProvider extends BlockLootSubProvider {
    protected ModLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)
        ));
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.ALL.get("repstone_stairs").get());
        this.dropSelf(ModBlocks.ALL.get("repstone_button").get());
        this.dropSelf(ModBlocks.ALL.get("repstone_pressure_plate").get());
        this.dropSelf(ModBlocks.ALL.get("repstone_trapdoor").get());
        this.dropSelf(ModBlocks.ALL.get("repstone_wall").get());

        this.dropSelf(ModBlocks.ALL.get("cobbled_repstone").get());
        this.dropSelf(ModBlocks.ALL.get("cobbled_repstone_stairs").get());
        this.dropSelf(ModBlocks.ALL.get("cobbled_repstone_button").get());
        this.dropSelf(ModBlocks.ALL.get("cobbled_repstone_pressure_plate").get());
        this.dropSelf(ModBlocks.ALL.get("cobbled_repstone_trapdoor").get());
        this.dropSelf(ModBlocks.ALL.get("cobbled_repstone_wall").get());

        this.dropSelf(ModBlocks.ALL.get("repstone_bricks").get());
        this.dropSelf(ModBlocks.ALL.get("repstone_brick_stairs").get());
        this.dropSelf(ModBlocks.ALL.get("repstone_brick_wall").get());

        this.dropSelf(ModBlocks.ALL.get("cracked_repstone_bricks").get());
        this.dropSelf(ModBlocks.ALL.get("chiseled_repstone_bricks").get());

        this.add(ModBlocks.ALL.get("repstone_slab").get(),
                block -> createSlabItemTable(ModBlocks.ALL.get("repstone_slab").get()));
        this.add(ModBlocks.ALL.get("cobbled_repstone_slab").get(),
                block -> createSlabItemTable(ModBlocks.ALL.get("cobbled_repstone_slab").get()));
        this.add(ModBlocks.ALL.get("repstone_brick_slab").get(),
                block -> createSlabItemTable(ModBlocks.ALL.get("repstone_brick_slab").get()));

        this.add(ModBlocks.ALL.get("repstone_door").get(), block -> createDoorTable(ModBlocks.ALL.get("repstone_door").get()));
    }
}