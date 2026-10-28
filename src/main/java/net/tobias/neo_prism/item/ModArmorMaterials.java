package net.tobias.neo_prism.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.tobias.neo_prism.Neo_Prism;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    RED("red", 33, new int[]{ 3, 8, 6, 3 }, 25,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 2f, 0f,
            () -> Ingredient.of(ModItems.ARMOR_ITEMS.get("red_shard").get())),
    ORANGE("orange", 33, new int[]{ 3, 8, 6, 3 }, 25,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 2f, 0f,
            () -> Ingredient.of(ModItems.ARMOR_ITEMS.get("orange_shard").get())),
    YELLOW("yellow", 33, new int[]{ 3, 8, 6, 3 }, 25,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 2f, 0f,
            () -> Ingredient.of(ModItems.ARMOR_ITEMS.get("yellow_shard").get())),
    GREEN("green", 33, new int[]{ 3, 8, 6, 3 }, 25,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 2f, 0f,
            () -> Ingredient.of(ModItems.ARMOR_ITEMS.get("green_shard").get())),
    BLUE("blue", 33, new int[]{ 3, 8, 6, 3 }, 25,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 12f, 0f,
            () -> Ingredient.of(ModItems.ARMOR_ITEMS.get("blue_shard").get())),
    CYAN("cyan", 33, new int[]{ 3, 8, 6, 3 }, 25,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 2f, 0f,
            () -> Ingredient.of(ModItems.ARMOR_ITEMS.get("cyan_shard").get())),
    PURPLE("purple", 33, new int[]{ 3, 8, 6, 3 }, 25,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 2f, 0f,
            () -> Ingredient.of(ModItems.ARMOR_ITEMS.get("purple_shard").get())),
    PINK("pink", 33, new int[]{ 3, 8, 6, 3 }, 25,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 2f, 0f,
            () -> Ingredient.of(ModItems.ARMOR_ITEMS.get("pink_shard").get())),
    WHITE("white", 33, new int[]{ 3, 8, 6, 3 }, 25,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 2f, 0f,
            () -> Ingredient.of(ModItems.ARMOR_ITEMS.get("white_shard").get())),
    PRISMATIC("prismatic", 57, new int[]{ 5, 10, 8, 4 }, 25,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 3.5f, 1f,
            () -> Ingredient.of(ModItems.ARMOR_ITEMS.get("prismatic_gem").get()));

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = { 11, 16, 15, 13 };

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts,
                      int enchantmentValue, SoundEvent equipSound, float toughness,
                      float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return BASE_DURABILITY[type.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.protectionAmounts[type.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return Neo_Prism.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}