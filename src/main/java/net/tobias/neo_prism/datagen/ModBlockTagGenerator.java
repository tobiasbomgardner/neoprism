package net.tobias.neo_prism.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Neo_Prism.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        /*example use for custom class tags, adding the ORES tag at the end:
        this.tag(ModTags.Blocks.NEEDS_RED_TOOL).add(ModBlocks.ALL.get("BLOCK_NAME").get()).addTag(Tags.Blocks.ORES);


        Examples for Minecraft/Forge tags:
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.ALL.get("BLOCK_NAME").get());
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.ALL.get("BLOCK_NAME").get());
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.ALL.get("BLOCK_NAME").get());
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.ALL.get("BLOCK_NAME").get());
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.ALL.get("BLOCK_NAME").get());
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(ModBlocks.ALL.get("BLOCK_NAME").get());

        Forge specials:
        this.tag(Tags.Blocks.NEEDS_WOOD_TOOL).add(ModBlocks.ALL.get("BLOCK_NAME").get());
        this.tag(Tags.Blocks.NEEDS_GOLD_TOOL).add(ModBlocks.ALL.get("BLOCK_NAME").get());
        this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(ModBlocks.ALL.get("BLOCK_NAME").get());

        This one shall demonstrate many entries:
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.ALL.get("BLOCK_NAME").get(),
                ModBlocks.ALL.get("BLOCK_NAME").get(),
                ModBlocks.ALL.get("BLOCK_NAME").get(),
                ModBlocks.ALL.get("BLOCK_NAME").get()
        );
        */
    }
}