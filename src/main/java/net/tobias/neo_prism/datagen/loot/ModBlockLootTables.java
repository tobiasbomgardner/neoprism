package net.tobias.neo_prism.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import net.tobias.neo_prism.block.ModBlocks;
import net.tobias.neo_prism.item.ModItems;
import net.tobias.neo_prism.util.ModTags;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        /*Example uses:
        this.dropSelf(ModBlocks.ALL.get("BLOCK_NAME").get());

        Make a loot table for shattered shard ores, but there is no XP
        this.add(ModBlocks.ALL.get("ORE_BLOCK").get(),
                block -> createShatteredShardOreDrops(ModBlocks.ALL.get("ORE_BLOCK").get(),
                        ModItems.ALL.get("ORE_DROP").get(), 2.0f ,3.0f));*/
    }

    protected LootTable.Builder createShatteredShardOreDrops(Block block, Item item, float minDropCount, float maxDropCount) {
        return createSilkTouchDispatchTable(block,
                this.applyExplosionDecay(block,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDropCount, maxDropCount)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}