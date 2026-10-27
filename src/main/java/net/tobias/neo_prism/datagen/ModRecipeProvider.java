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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALL.get("repstone_pressure_plate").get(), 1)
                .pattern("##")
                .define('#', ModBlocks.ALL.get("repstone").get())
                .unlockedBy(getHasName(ModBlocks.ALL.get("repstone").get()),
                        has(ModBlocks.ALL.get("repstone").get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALL.get("repstone_bricks").get(), 4)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.ALL.get("repstone").get())
                .unlockedBy(getHasName(ModBlocks.ALL.get("repstone").get()),
                        has(ModBlocks.ALL.get("repstone").get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALL.get("repstone_slab").get(), 6)
                .pattern("###")
                .define('#', ModBlocks.ALL.get("repstone").get())
                .unlockedBy(getHasName(ModBlocks.ALL.get("repstone").get()),
                        has(ModBlocks.ALL.get("repstone").get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALL.get("repstone_stairs").get(), 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', ModBlocks.ALL.get("repstone").get())
                .unlockedBy(getHasName(ModBlocks.ALL.get("repstone").get()),
                        has(ModBlocks.ALL.get("repstone").get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALL.get("repstone_wall").get(), 6)
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.ALL.get("repstone").get())
                .unlockedBy(getHasName(ModBlocks.ALL.get("repstone").get()),
                        has(ModBlocks.ALL.get("repstone").get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALL.get("repstone_brick_slab").get(), 6)
                .pattern("###")
                .define('#', ModBlocks.ALL.get("repstone_bricks").get())
                .unlockedBy(getHasName(ModBlocks.ALL.get("repstone_bricks").get()),
                        has(ModBlocks.ALL.get("repstone_bricks").get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALL.get("repstone_brick_stairs").get(), 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', ModBlocks.ALL.get("repstone_bricks").get())
                .unlockedBy(getHasName(ModBlocks.ALL.get("repstone_bricks").get()),
                        has(ModBlocks.ALL.get("repstone_bricks").get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALL.get("repstone_brick_wall").get(), 6)
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.ALL.get("repstone_bricks").get())
                .unlockedBy(getHasName(ModBlocks.ALL.get("repstone_bricks").get()),
                        has(ModBlocks.ALL.get("repstone_bricks").get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALL.get("chiseled_repstone_bricks").get(), 1)
                .pattern("#")
                .pattern("#")
                .define('#', ModBlocks.ALL.get("repstone_brick_slab").get())
                .unlockedBy(getHasName(ModBlocks.ALL.get("repstone_brick_slab").get()),
                        has(ModBlocks.ALL.get("repstone_brick_slab").get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALL.get("cobbled_repstone_slab").get(), 6)
                .pattern("###")
                .define('#', ModBlocks.ALL.get("cobbled_repstone").get())
                .unlockedBy(getHasName(ModBlocks.ALL.get("cobbled_repstone").get()),
                        has(ModBlocks.ALL.get("cobbled_repstone").get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALL.get("cobbled_repstone_stairs").get(), 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', ModBlocks.ALL.get("cobbled_repstone").get())
                .unlockedBy(getHasName(ModBlocks.ALL.get("cobbled_repstone").get()),
                        has(ModBlocks.ALL.get("cobbled_repstone").get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALL.get("cobbled_repstone_wall").get(), 6)
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.ALL.get("cobbled_repstone").get())
                .unlockedBy(getHasName(ModBlocks.ALL.get("cobbled_repstone").get()),
                        has(ModBlocks.ALL.get("cobbled_repstone").get()))
                .save(pWriter);

        anyStonecutter(pWriter, RecipeCategory.MISC, ModBlocks.ALL.get("repstone_slab").get(),
                ModBlocks.ALL.get("repstone").get(), 2);
        anyStonecutter(pWriter, RecipeCategory.MISC, ModBlocks.ALL.get("repstone_stairs").get(),
                ModBlocks.ALL.get("repstone").get(), 1);
        anyStonecutter(pWriter, RecipeCategory.MISC, ModBlocks.ALL.get("repstone_wall").get(),
                ModBlocks.ALL.get("repstone").get(), 1);
        anyStonecutter(pWriter, RecipeCategory.MISC, ModBlocks.ALL.get("chiseled_repstone_bricks").get(),
                ModBlocks.ALL.get("repstone").get(), 1);
        anyStonecutter(pWriter, RecipeCategory.MISC, ModBlocks.ALL.get("repstone_bricks").get(),
                ModBlocks.ALL.get("repstone").get(), 1);
        anyStonecutter(pWriter, RecipeCategory.MISC, ModBlocks.ALL.get("repstone_brick_wall").get(),
                ModBlocks.ALL.get("repstone").get(), 1);
        anyStonecutter(pWriter, RecipeCategory.MISC, ModBlocks.ALL.get("repstone_brick_stairs").get(),
                ModBlocks.ALL.get("repstone").get(), 1);
        anyStonecutter(pWriter, RecipeCategory.MISC, ModBlocks.ALL.get("repstone_brick_slab").get(),
                ModBlocks.ALL.get("repstone").get(), 2);
        anyStonecutter(pWriter, RecipeCategory.MISC, ModBlocks.ALL.get("chiseled_repstone_bricks").get(),
                ModBlocks.ALL.get("repstone_bricks").get(), 1);
        anyStonecutter(pWriter, RecipeCategory.MISC, ModBlocks.ALL.get("repstone_brick_slab").get(),
                ModBlocks.ALL.get("repstone_bricks").get(), 2);
        anyStonecutter(pWriter, RecipeCategory.MISC, ModBlocks.ALL.get("repstone_brick_stairs").get(),
                ModBlocks.ALL.get("repstone_bricks").get(), 1);
        anyStonecutter(pWriter, RecipeCategory.MISC, ModBlocks.ALL.get("repstone_brick_wall").get(),
                ModBlocks.ALL.get("repstone_bricks").get(), 1);
        anyStonecutter(pWriter, RecipeCategory.MISC, ModBlocks.ALL.get("cobbled_repstone_slab").get(),
                ModBlocks.ALL.get("cobbled_repstone").get(), 2);
        anyStonecutter(pWriter, RecipeCategory.MISC, ModBlocks.ALL.get("cobbled_repstone_stairs").get(),
                ModBlocks.ALL.get("cobbled_repstone").get(), 1);
        anyStonecutter(pWriter, RecipeCategory.MISC, ModBlocks.ALL.get("cobbled_repstone_wall").get(),
                ModBlocks.ALL.get("cobbled_repstone").get(), 1);
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

    protected static void anyStonecutter(Consumer<FinishedRecipe> finishedRecipeConsumer, RecipeCategory category,
                                                    ItemLike result, ItemLike material, int resultCount) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(material), category, result, resultCount)
                .unlockedBy(getHasName(material), has(material))
                .save(finishedRecipeConsumer, getConversionRecipeName(result, material) + "_stonecutting");
    }
}