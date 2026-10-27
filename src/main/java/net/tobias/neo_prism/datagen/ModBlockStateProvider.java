package net.tobias.neo_prism.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.block.ModBlocks;
import net.tobias.neo_prism.item.ModItems;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Neo_Prism.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // example use: blockWithItem(ModBlocks.ALL.get("red_hay_bale"));
        blockWithItem(ModBlocks.ALL.get("repstone"));
        blockWithItem(ModBlocks.ALL.get("cobbled_repstone"));
        blockWithItem(ModBlocks.ALL.get("repstone_bricks"));
        blockWithItem(ModBlocks.ALL.get("cracked_repstone_bricks"));
        blockWithItem(ModBlocks.ALL.get("chiseled_repstone_bricks"));

        stairsBlock(((StairBlock) ModBlocks.ALL.get("repstone_stairs").get()), blockTexture(ModBlocks.ALL.get("repstone").get()));
        stairsBlock(((StairBlock) ModBlocks.ALL.get("cobbled_repstone_stairs").get()), blockTexture(ModBlocks.ALL.get("cobbled_repstone").get()));
        stairsBlock(((StairBlock) ModBlocks.ALL.get("repstone_brick_stairs").get()), blockTexture(ModBlocks.ALL.get("repstone_bricks").get()));

        slabBlock(((SlabBlock) ModBlocks.ALL.get("repstone_slab").get()), blockTexture(ModBlocks.ALL.get("repstone").get()),
                blockTexture(ModBlocks.ALL.get("repstone").get()));
        slabBlock(((SlabBlock) ModBlocks.ALL.get("cobbled_repstone_slab").get()), blockTexture(ModBlocks.ALL.get("cobbled_repstone").get()),
                blockTexture(ModBlocks.ALL.get("repstone").get()));
        slabBlock(((SlabBlock) ModBlocks.ALL.get("repstone_brick_slab").get()), blockTexture(ModBlocks.ALL.get("repstone_bricks").get()),
                blockTexture(ModBlocks.ALL.get("repstone").get()));

        buttonBlock(((ButtonBlock) ModBlocks.ALL.get("repstone_button").get()), blockTexture(ModBlocks.ALL.get("repstone").get()));

        pressurePlateBlock(((PressurePlateBlock) ModBlocks.ALL.get("repstone_pressure_plate").get()), blockTexture(ModBlocks.ALL.get("repstone").get()));

        wallBlock(((WallBlock) ModBlocks.ALL.get("repstone_wall").get()), blockTexture(ModBlocks.ALL.get("repstone").get()));
        wallBlock(((WallBlock) ModBlocks.ALL.get("cobbled_repstone_wall").get()), blockTexture(ModBlocks.ALL.get("cobbled_repstone").get()));
        wallBlock(((WallBlock) ModBlocks.ALL.get("repstone_brick_wall").get()), blockTexture(ModBlocks.ALL.get("repstone_bricks").get()));

        doorBlockWithRenderType(((DoorBlock) ModBlocks.ALL.get("repstone_door").get()), modLoc("block/repstone_door_bottem"), modLoc("block/repstone_door_top"), "cutout");
        trapdoorBlockWithRenderType(((TrapDoorBlock) ModBlocks.ALL.get("repstone_trapdoor").get()), modLoc("block/repstone_trapdoor"), true, "cutout");
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}