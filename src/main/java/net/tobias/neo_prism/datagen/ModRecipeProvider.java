package net.tobias.neo_prism.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.block.ModBlocks;
import net.tobias.neo_prism.item.ModItems;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        //oreBlasting(pWriter, PRISM_SMELTABLES, RecipeCategory.MISC, ModItems.ALL.get("").get(), 0.25f, 100, "prism");
        //oreSmelting(pWriter, PRISM_SMELTABLES, RecipeCategory.MISC, ModItems.ALL.get("").get(), 0.25f, 200, "prism");

//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALL.get("red_hay_bale").get())
//                .pattern("###")
//                .pattern("###")
//                .pattern("###")
//                .define('#', ModItems.ALL.get("red_wheat").get())
//                .unlockedBy(getHasName(ModItems.ALL.get("red_wheat").get()),
//                        has(ModItems.ALL.get("red_wheat").get()))
//                .save(pWriter);


//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ALL.get("red_wheat").get(), 9)
//                .requires(ModBlocks.ALL.get("red_hay_bale").get())
//                .unlockedBy(getHasName(ModBlocks.ALL.get("red_hay_bale").get()),
//                        has(ModBlocks.ALL.get("red_hay_bale").get()))
//                .save(pWriter);

//        anySmithing(pWriter, Items.NETHERITE_SWORD, RecipeCategory.MISC,
//                ModItems.ALL.get("prismatic_sword").get(), ModItems.ALL.get("prismatic_upgrade_template").get(),
//                ModItems.ALL.get("prismatic_gem").get());
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients,
                                      RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme,
                                      String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients,
                                      RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime,
                                      String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer,
                                     RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer,
                                     List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                     float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience,
                    pCookingTime, pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike),
                    has(itemlike)).save(pFinishedRecipeConsumer, Neo_Prism.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }

    protected static void anySmithing(Consumer<FinishedRecipe> finishedRecipeConsumer, Item baseItem,
                                   RecipeCategory category, Item resultItem, Item templateItem, Item additionItem) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(templateItem),
                        Ingredient.of(baseItem), Ingredient.of(additionItem), category, resultItem)
                .unlocks("has_netherite_ingot", has(additionItem)).save(finishedRecipeConsumer,
                        getItemName(resultItem) + "_smithing");
    }
}