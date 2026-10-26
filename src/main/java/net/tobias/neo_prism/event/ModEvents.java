package net.tobias.neo_prism.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.block.ModBlocks;
import net.tobias.neo_prism.item.ModItems;

import java.util.List;

@Mod.EventBusSubscriber(modid = Neo_Prism.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.FARMER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModItems.ALL.get("red_bread").get(), 5),
                    20, 1, 0.02f));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModItems.ALL.get("orange_bread").get(), 5),
                    20, 1, 0.02f));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModItems.ALL.get("yellow_bread").get(), 5),
                    20, 1, 0.02f));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModItems.ALL.get("green_bread").get(), 5),
                    20, 1, 0.02f));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModItems.ALL.get("blue_bread").get(), 5),
                    20, 1, 0.02f));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModItems.ALL.get("cyan_bread").get(), 5),
                    20, 1, 0.02f));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModItems.ALL.get("purple_bread").get(), 5),
                    20, 1, 0.02f));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModItems.ALL.get("pink_bread").get(), 5),
                    20, 1, 0.02f));
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModItems.ALL.get("white_bread").get(), 5),
                    20, 1, 0.02f));

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.ALL.get("red_wheat").get(), 3),
                    15, 3, 0.03f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.ALL.get("orange_wheat").get(), 3),
                    15, 3, 0.03f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.ALL.get("yellow_wheat").get(), 3),
                    15, 3, 0.03f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.ALL.get("green_wheat").get(), 3),
                    15, 3, 0.03f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.ALL.get("blue_wheat").get(), 3),
                    15, 3, 0.03f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.ALL.get("cyan_wheat").get(), 3),
                    15, 3, 0.03f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.ALL.get("purple_wheat").get(), 3),
                    15, 3, 0.03f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.ALL.get("pink_wheat").get(), 3),
                    15, 3, 0.03f));
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.ALL.get("white_wheat").get(), 3),
                    15, 3, 0.03f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 7),
                    new ItemStack(ModBlocks.ALL.get("red_hay_bale").get(), 1),
                    10, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 7),
                    new ItemStack(ModBlocks.ALL.get("orange_hay_bale").get(), 1),
                    10, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 7),
                    new ItemStack(ModBlocks.ALL.get("yellow_hay_bale").get(), 1),
                    10, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 7),
                    new ItemStack(ModBlocks.ALL.get("green_hay_bale").get(), 1),
                    10, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 7),
                    new ItemStack(ModBlocks.ALL.get("blue_hay_bale").get(), 1),
                    10, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 7),
                    new ItemStack(ModBlocks.ALL.get("cyan_hay_bale").get(), 1),
                    10, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 7),
                    new ItemStack(ModBlocks.ALL.get("purple_hay_bale").get(), 1),
                    10, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 7),
                    new ItemStack(ModBlocks.ALL.get("pink_hay_bale").get(), 1),
                    10, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 7),
                    new ItemStack(ModBlocks.ALL.get("white_hay_bale").get(), 1),
                    10, 10, 0.05f));

        }
        if (event.getType() == VillagerProfession.ARMORER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("red_helmet").get(), 1),
                    5, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("red_boots").get(), 1),
                    5, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("orange_helmet").get(), 1),
                    5, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("orange_boots").get(), 1),
                    5, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("yellow_helmet").get(), 1),
                    5, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("yellow_boots").get(), 1),
                    5, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("green_helmet").get(), 1),
                    5, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("green_boots").get(), 1),
                    5, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("blue_helmet").get(), 1),
                    5, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("blue_boots").get(), 1),
                    5, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("cyan_helmet").get(), 1),
                    5, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("cyan_boots").get(), 1),
                    5, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("purple_helmet").get(), 1),
                    5, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("purple_boots").get(), 1),
                    5, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("pink_helmet").get(), 1),
                    5, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("pink_boots").get(), 1),
                    5, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("white_helmet").get(), 1),
                    5, 10, 0.05f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("white_boots").get(), 1),
                    5, 10, 0.05f));

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("red_leggings").get(), 1),
                    3, 15, 0.07f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("red_chestplate").get(), 1),
                    3, 15, 0.07f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("orange_leggings").get(), 1),
                    3, 15, 0.07f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("orange_chestplate").get(), 1),
                    3, 15, 0.07f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("yellow_leggings").get(), 1),
                    3, 15, 0.07f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("yellow_chestplate").get(), 1),
                    3, 15, 0.07f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("green_leggings").get(), 1),
                    3, 15, 0.07f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("green_chestplate").get(), 1),
                    3, 15, 0.07f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("blue_leggings").get(), 1),
                    3, 15, 0.07f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("blue_chestplate").get(), 1),
                    3, 15, 0.07f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("cyan_leggings").get(), 1),
                    3, 15, 0.07f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("cyan_chestplate").get(), 1),
                    3, 15, 0.07f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("purple_leggings").get(), 1),
                    3, 15, 0.07f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("purple_chestplate").get(), 1),
                    3, 15, 0.07f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("pink_leggings").get(), 1),
                    3, 15, 0.07f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("pink_chestplate").get(), 1),
                    3, 15, 0.07f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("white_leggings").get(), 1),
                    3, 15, 0.07f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("white_chestplate").get(), 1),
                    3, 15, 0.07f));
        }
        if (event.getType() == VillagerProfession.BUTCHER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

//            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
//                    new ItemStack(Items.EMERALD, 1),
//                    new ItemStack(ModItems.ALL.get("ITEM/BLOCK_NAME").get(), 5),
//                    20, 1, 0.02f));
        }
        if (event.getType() == VillagerProfession.CARTOGRAPHER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModItems.ALL.get("ITEM/BLOCK_NAME").get(), 5),
                    20, 1, 0.02f));
        }
        if (event.getType() == VillagerProfession.CLERIC) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

//            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
//                    new ItemStack(Items.EMERALD, 1),
//                    new ItemStack(ModItems.ALL.get("ITEM/BLOCK_NAME").get(), 5),
//                    20, 1, 0.02f));
        }
        if (event.getType() == VillagerProfession.FISHERMAN) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

//            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
//                    new ItemStack(Items.EMERALD, 1),
//                    new ItemStack(ModItems.ALL.get("ITEM/BLOCK_NAME").get(), 5),
//                    20, 1, 0.02f));
        }
        if (event.getType() == VillagerProfession.FLETCHER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

//            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
//                    new ItemStack(Items.EMERALD, 1),
//                    new ItemStack(ModItems.ALL.get("ITEM/BLOCK_NAME").get(), 5),
//                    20, 1, 0.02f));
        }
        if (event.getType() == VillagerProfession.LEATHERWORKER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

//            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
//                    new ItemStack(Items.EMERALD, 1),
//                    new ItemStack(ModItems.ALL.get("ITEM/BLOCK_NAME").get(), 5),
//                    20, 1, 0.02f));
        }
        if (event.getType() == VillagerProfession.LIBRARIAN) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

//            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
//                    new ItemStack(Items.EMERALD, 1),
//                    new ItemStack(ModItems.ALL.get("ITEM/BLOCK_NAME").get(), 5),
//                    20, 1, 0.02f));
        }
        if (event.getType() == VillagerProfession.MASON) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModBlocks.ALL.get("red_gem_block").get(), 1),
                    1, 20, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModBlocks.ALL.get("orange_gem_block").get(), 1),
                    1, 20, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModBlocks.ALL.get("yellow_gem_block").get(), 1),
                    1, 20, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModBlocks.ALL.get("green_gem_block").get(), 1),
                    1, 20, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModBlocks.ALL.get("blue_gem_block").get(), 1),
                    1, 20, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModBlocks.ALL.get("cyan_gem_block").get(), 1),
                    1, 20, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModBlocks.ALL.get("purple_gem_block").get(), 1),
                    1, 20, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModBlocks.ALL.get("pink_gem_block").get(), 1),
                    1, 20, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModBlocks.ALL.get("white_gem_block").get(), 1),
                    1, 20, 0.05f));
        }
        if (event.getType() == VillagerProfession.SHEPHERD) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

//            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
//                    new ItemStack(Items.EMERALD, 1),
//                    new ItemStack(ModItems.ALL.get("ITEM/BLOCK_NAME").get(), 5),
//                    20, 1, 0.02f));
        }
        if (event.getType() == VillagerProfession.TOOLSMITH) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModItems.ALL.get("red_pickaxe").get(), 1),
                    2, 20, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModItems.ALL.get("orange_pickaxe").get(), 1),
                    2, 20, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModItems.ALL.get("yellow_pickaxe").get(), 1),
                    2, 20, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModItems.ALL.get("green_pickaxe").get(), 1),
                    2, 20, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModItems.ALL.get("blue_pickaxe").get(), 1),
                    2, 20, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModItems.ALL.get("cyan_pickaxe").get(), 1),
                    2, 20, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModItems.ALL.get("purple_pickaxe").get(), 1),
                    2, 20, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModItems.ALL.get("pink_pickaxe").get(), 1),
                    2, 20, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    new ItemStack(ModItems.ALL.get("white_pickaxe").get(), 1),
                    2, 20, 0.05f));

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 25),
                    new ItemStack(ModItems.ALL.get("red_axe").get(), 1),
                    4, 15, 0.04f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 25),
                    new ItemStack(ModItems.ALL.get("orange_axe").get(), 1),
                    4, 15, 0.04f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 25),
                    new ItemStack(ModItems.ALL.get("yellow_axe").get(), 1),
                    4, 15, 0.04f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 25),
                    new ItemStack(ModItems.ALL.get("green_axe").get(), 1),
                    4, 15, 0.04f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 25),
                    new ItemStack(ModItems.ALL.get("blue_axe").get(), 1),
                    4, 15, 0.04f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 25),
                    new ItemStack(ModItems.ALL.get("cyan_axe").get(), 1),
                    4, 15, 0.04f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 25),
                    new ItemStack(ModItems.ALL.get("purple_axe").get(), 1),
                    4, 15, 0.04f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 25),
                    new ItemStack(ModItems.ALL.get("pink_axe").get(), 1),
                    4, 15, 0.04f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 25),
                    new ItemStack(ModItems.ALL.get("white_axe").get(), 1),
                    4, 15, 0.04f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(ModItems.ALL.get("red_shovel").get(), 1),
                    6, 10, 0.03f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(ModItems.ALL.get("orange_shovel").get(), 1),
                    6, 10, 0.03f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(ModItems.ALL.get("yellow_shovel").get(), 1),
                    6, 10, 0.03f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(ModItems.ALL.get("green_shovel").get(), 1),
                    6, 10, 0.03f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(ModItems.ALL.get("blue_shovel").get(), 1),
                    6, 10, 0.03f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(ModItems.ALL.get("cyan_shovel").get(), 1),
                    6, 10, 0.03f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(ModItems.ALL.get("purple_shovel").get(), 1),
                    6, 10, 0.03f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(ModItems.ALL.get("pink_shovel").get(), 1),
                    6, 10, 0.03f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),
                    new ItemStack(ModItems.ALL.get("white_shovel").get(), 1),
                    6, 10, 0.03f));

            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(ModItems.ALL.get("red_hoe").get(), 1),
                    8, 5, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(ModItems.ALL.get("orange_hoe").get(), 1),
                    8, 5, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(ModItems.ALL.get("yellow_hoe").get(), 1),
                    8, 5, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(ModItems.ALL.get("green_hoe").get(), 1),
                    8, 5, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(ModItems.ALL.get("blue_hoe").get(), 1),
                    8, 5, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(ModItems.ALL.get("cyan_hoe").get(), 1),
                    8, 5, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(ModItems.ALL.get("purple_hoe").get(), 1),
                    8, 5, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(ModItems.ALL.get("pink_hoe").get(), 1),
                    8, 5, 0.02f));
            trades.get(4).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(ModItems.ALL.get("white_hoe").get(), 1),
                    8, 5, 0.02f));
        }
        if (event.getType() == VillagerProfession.WEAPONSMITH) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("red_sword").get(), 1),
                    5, 10, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("orange_sword").get(), 1),
                    5, 10, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("yellow_sword").get(), 1),
                    5, 10, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("green_sword").get(), 1),
                    5, 10, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("blue_sword").get(), 1),
                    5, 10, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("cyan_sword").get(), 1),
                    5, 10, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("purple_sword").get(), 1),
                    5, 10, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("pink_sword").get(), 1),
                    5, 10, 0.05f));
            trades.get(5).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(ModItems.ALL.get("white_sword").get(), 1),
                    5, 10, 0.05f));
        }
        if (event.getType() == VillagerProfession.NITWIT) {}
        if (event.getType() == VillagerProfession.NONE) {}
    }

    @SubscribeEvent
    public static void addCustomWanderingTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 10),
                new ItemStack(ModItems.ALL.get("red_shattered_shard").get(), 1),
                5, 10, 0.03f));
        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 10),
                new ItemStack(ModItems.ALL.get("orange_shattered_shard").get(), 1),
                5, 10, 0.03f));
        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 10),
                new ItemStack(ModItems.ALL.get("yellow_shattered_shard").get(), 1),
                5, 10, 0.03f));
        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 10),
                new ItemStack(ModItems.ALL.get("green_shattered_shard").get(), 1),
                5, 10, 0.03f));
        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 10),
                new ItemStack(ModItems.ALL.get("blue_shattered_shard").get(), 1),
                5, 10, 0.03f));
        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 10),
                new ItemStack(ModItems.ALL.get("cyan_shattered_shard").get(), 1),
                5, 10, 0.03f));
        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 10),
                new ItemStack(ModItems.ALL.get("purple_shattered_shard").get(), 1),
                5, 10, 0.03f));
        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 10),
                new ItemStack(ModItems.ALL.get("pink_shattered_shard").get(), 1),
                5, 10, 0.03f));
        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 10),
                new ItemStack(ModItems.ALL.get("white_shattered_shard").get(), 1),
                5, 10, 0.03f));

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                new ItemStack(ModItems.ALL.get("red_gem").get(), 1),
                1, 20, 0.05f));
        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                new ItemStack(ModItems.ALL.get("orange_gem").get(), 1),
                1, 20, 0.05f));
        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                new ItemStack(ModItems.ALL.get("yellow_gem").get(), 1),
                1, 20, 0.05f));
        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                new ItemStack(ModItems.ALL.get("green_gem").get(), 1),
                1, 20, 0.05f));
        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                new ItemStack(ModItems.ALL.get("blue_gem").get(), 1),
                1, 20, 0.05f));
        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                new ItemStack(ModItems.ALL.get("cyan_gem").get(), 1),
                1, 20, 0.05f));
        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                new ItemStack(ModItems.ALL.get("purple_gem").get(), 1),
                1, 20, 0.05f));
        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                new ItemStack(ModItems.ALL.get("pink_gem").get(), 1),
                1, 20, 0.05f));
        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                new ItemStack(ModItems.ALL.get("white_gem").get(), 1),
                1, 20, 0.05f));

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.NETHERITE_INGOT, 5),
                new ItemStack(ModItems.ALL.get("prismatic_gem").get(), 1),
                1, 20, 1f));
    }
}