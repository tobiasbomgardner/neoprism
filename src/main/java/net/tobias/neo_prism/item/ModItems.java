package net.tobias.neo_prism.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.PrismColor;
import net.tobias.neo_prism.item.custom.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Neo_Prism.MOD_ID);

    private static final List<String> PARTS_COMPLETE = List.of(
            "wheat","bread","shattered_shard","shard","gem",
            "helmet","chestplate","leggings","boots","sword","pickaxe","axe","shovel","hoe"
    );
    private static final List<String> PRISMATIC_PARTS_COMPLETE = List.of(
            "gem","upgrade_template","helmet","chestplate","leggings","boots",
            "sword","pickaxe","axe","shovel","hoe"
    );

    private static final List<String> PARTS_WIP = null;
    private static final List<String> PRISMATIC_PARTS_WIP = null;

    private static final List<String> NONCOLOR_ITEMS_COMPLETE = null;
    private static final List<String> NONCOLOR_ITEMS_WIP = List.of(
            "raw_lizard_meat","cooked_lizard_meat","lizard_scale","lizard_scale_helmet",
            "lizard_scale_chestplate","lizard_scale_leggings","lizard_scale_boots","lizard_tail_thwacker",
            "lizard_teeth","lizard_claw","lizard_tail"
    );


    private static final Map<String, FoodProperties> FOOD_TYPES = new HashMap<>();
    private static final Map<String, Tier> MODDED_TIERS = new HashMap<>();
    private static final Map<String, Tier> MINECRAFT_TIERS = new HashMap<>();
    private static final Map<String, ModArmorMaterials> ARMOR_MATERIALS = new HashMap<>();
    private static final Map<String, ArmorItem.Type> ARMOR_TYPES = new HashMap<>();
    protected static final Map<String, RegistryObject<Item>> ARMOR_ITEMS = new HashMap<>();
    protected static final Map<String, RegistryObject<Item>> MODDED_TIER_MATERIALS = new HashMap<>();
    private static final Map<String, Integer> MAX_STACK_SIZE = new HashMap<>();
    private static void setUpMaps() {
        FOOD_TYPES.put("bread", ModFoods.PRISM_COLOR_BREAD);
        FOOD_TYPES.put("raw_lizard_meat", ModFoods.RAW_LIZARD_MEAT);
        FOOD_TYPES.put("cooked_lizard_meat", ModFoods.COOKED_LIZARD_MEAT);

        MODDED_TIERS.put("sword", ModToolTiers.PRISMATIC);
        MODDED_TIERS.put("pickaxe", ModToolTiers.PRISMATIC);
        MODDED_TIERS.put("axe", ModToolTiers.PRISMATIC);
        MODDED_TIERS.put("shovel", ModToolTiers.PRISMATIC);
        MODDED_TIERS.put("hoe", ModToolTiers.PRISMATIC);
        MODDED_TIERS.put("lizard_tail_thwacker", ModToolTiers.LIZARD);

        MINECRAFT_TIERS.put("sword", Tiers.DIAMOND);
        MINECRAFT_TIERS.put("pickaxe", Tiers.DIAMOND);
        MINECRAFT_TIERS.put("axe", Tiers.DIAMOND);
        MINECRAFT_TIERS.put("shovel", Tiers.DIAMOND);
        MINECRAFT_TIERS.put("hoe", Tiers.DIAMOND);

        ARMOR_TYPES.put("helmet", ArmorItem.Type.HELMET);
        ARMOR_TYPES.put("chestplate", ArmorItem.Type.CHESTPLATE);
        ARMOR_TYPES.put("leggings", ArmorItem.Type.LEGGINGS);
        ARMOR_TYPES.put("boots", ArmorItem.Type.BOOTS);
        ARMOR_TYPES.put("lizard_scale_helmet", ArmorItem.Type.HELMET);
        ARMOR_TYPES.put("lizard_scale_chestplate", ArmorItem.Type.CHESTPLATE);
        ARMOR_TYPES.put("lizard_scale_leggings", ArmorItem.Type.LEGGINGS);
        ARMOR_TYPES.put("lizard_scale_boots", ArmorItem.Type.BOOTS);

        ARMOR_MATERIALS.put("red_helmet", ModArmorMaterials.RED);
        ARMOR_MATERIALS.put("red_chestplate", ModArmorMaterials.RED);
        ARMOR_MATERIALS.put("red_leggings", ModArmorMaterials.RED);
        ARMOR_MATERIALS.put("red_boots", ModArmorMaterials.RED);
        ARMOR_MATERIALS.put("orange_helmet", ModArmorMaterials.ORANGE);
        ARMOR_MATERIALS.put("orange_chestplate", ModArmorMaterials.ORANGE);
        ARMOR_MATERIALS.put("orange_leggings", ModArmorMaterials.ORANGE);
        ARMOR_MATERIALS.put("orange_boots", ModArmorMaterials.ORANGE);
        ARMOR_MATERIALS.put("yellow_helmet", ModArmorMaterials.YELLOW);
        ARMOR_MATERIALS.put("yellow_chestplate", ModArmorMaterials.YELLOW);
        ARMOR_MATERIALS.put("yellow_leggings", ModArmorMaterials.YELLOW);
        ARMOR_MATERIALS.put("yellow_boots", ModArmorMaterials.YELLOW);
        ARMOR_MATERIALS.put("green_helmet", ModArmorMaterials.GREEN);
        ARMOR_MATERIALS.put("green_chestplate", ModArmorMaterials.GREEN);
        ARMOR_MATERIALS.put("green_leggings", ModArmorMaterials.GREEN);
        ARMOR_MATERIALS.put("green_boots", ModArmorMaterials.GREEN);
        ARMOR_MATERIALS.put("blue_helmet", ModArmorMaterials.BLUE);
        ARMOR_MATERIALS.put("blue_chestplate", ModArmorMaterials.BLUE);
        ARMOR_MATERIALS.put("blue_leggings", ModArmorMaterials.BLUE);
        ARMOR_MATERIALS.put("blue_boots", ModArmorMaterials.BLUE);
        ARMOR_MATERIALS.put("cyan_helmet", ModArmorMaterials.CYAN);
        ARMOR_MATERIALS.put("cyan_chestplate", ModArmorMaterials.CYAN);
        ARMOR_MATERIALS.put("cyan_leggings", ModArmorMaterials.CYAN);
        ARMOR_MATERIALS.put("cyan_boots", ModArmorMaterials.CYAN);
        ARMOR_MATERIALS.put("purple_helmet", ModArmorMaterials.PURPLE);
        ARMOR_MATERIALS.put("purple_chestplate", ModArmorMaterials.PURPLE);
        ARMOR_MATERIALS.put("purple_leggings", ModArmorMaterials.PURPLE);
        ARMOR_MATERIALS.put("purple_boots", ModArmorMaterials.PURPLE);
        ARMOR_MATERIALS.put("pink_helmet", ModArmorMaterials.PINK);
        ARMOR_MATERIALS.put("pink_chestplate", ModArmorMaterials.PINK);
        ARMOR_MATERIALS.put("pink_leggings", ModArmorMaterials.PINK);
        ARMOR_MATERIALS.put("pink_boots", ModArmorMaterials.PINK);
        ARMOR_MATERIALS.put("white_helmet", ModArmorMaterials.WHITE);
        ARMOR_MATERIALS.put("white_chestplate", ModArmorMaterials.WHITE);
        ARMOR_MATERIALS.put("white_leggings", ModArmorMaterials.WHITE);
        ARMOR_MATERIALS.put("white_boots", ModArmorMaterials.WHITE);
        ARMOR_MATERIALS.put("prismatic_helmet", ModArmorMaterials.PRISMATIC);
        ARMOR_MATERIALS.put("prismatic_chestplate", ModArmorMaterials.PRISMATIC);
        ARMOR_MATERIALS.put("prismatic_leggings", ModArmorMaterials.PRISMATIC);
        ARMOR_MATERIALS.put("prismatic_boots", ModArmorMaterials.PRISMATIC);
        ARMOR_MATERIALS.put("lizard_scale_helmet", ModArmorMaterials.LIZARD_SCALE);
        ARMOR_MATERIALS.put("lizard_scale_chestplate", ModArmorMaterials.LIZARD_SCALE);
        ARMOR_MATERIALS.put("lizard_scale_leggings", ModArmorMaterials.LIZARD_SCALE);
        ARMOR_MATERIALS.put("lizard_scale_boots", ModArmorMaterials.LIZARD_SCALE);

        MAX_STACK_SIZE.put("bread", 64);
        MAX_STACK_SIZE.put("raw_lizard_meat", 64);
        MAX_STACK_SIZE.put("cooked_lizard_meat", 64);
        MAX_STACK_SIZE.put("lizard_scale", 64);
        MAX_STACK_SIZE.put("lizard_claw", 64);
        MAX_STACK_SIZE.put("lizard_teeth", 64);
        MAX_STACK_SIZE.put("lizard_tail", 64);
        MAX_STACK_SIZE.put("gem", 64);
        MAX_STACK_SIZE.put("shattered_shard", 64);
        MAX_STACK_SIZE.put("shard", 64);
        MAX_STACK_SIZE.put("wheat", 64);
        MAX_STACK_SIZE.put("upgrade_template", 64);
        MAX_STACK_SIZE.put("dart", 64);
        MAX_STACK_SIZE.put("stun_dart", 64);
        MAX_STACK_SIZE.put("dart_gun", 1);
        MAX_STACK_SIZE.put("helmet", 1);
        MAX_STACK_SIZE.put("chestplate", 1);
        MAX_STACK_SIZE.put("leggings", 1);
        MAX_STACK_SIZE.put("boots", 1);
        MAX_STACK_SIZE.put("sword", 1);
        MAX_STACK_SIZE.put("pickaxe", 1);
        MAX_STACK_SIZE.put("axe", 1);
        MAX_STACK_SIZE.put("shovel", 1);
        MAX_STACK_SIZE.put("hoe", 1);
        MAX_STACK_SIZE.put("lizard_scale_helmet", 1);
        MAX_STACK_SIZE.put("lizard_scale_chestplate", 1);
        MAX_STACK_SIZE.put("lizard_scale_leggings", 1);
        MAX_STACK_SIZE.put("lizard_scale_boots", 1);
        MAX_STACK_SIZE.put("lizard_tail_thwacker", 1);
    }


    public static final Map<String, RegistryObject<Item>> ALL = new HashMap<>();
    public static final List<String> SORTED_ORDER_COMPLETE = new ArrayList<>();
    public static final List<String> SORTED_ORDER_WIP = new ArrayList<>();

    private static void registerCOMPLETE(String id, int maxStackSize) {
        RegistryObject<Item> obj = ITEMS.register(id, () -> new Item(new Item.Properties().stacksTo(maxStackSize)));
        ALL.put(id, obj);
        SORTED_ORDER_COMPLETE.add(id);
        String[] itemType = id.split("_");
        if (id.equals("lizard_scale") || itemType[1].equals("shard") || id.equals("prismatic_gem")) {
            ARMOR_ITEMS.put(id, obj);
            if (id.equals("prismatic_gem")) {
                MODDED_TIER_MATERIALS.put(id, obj);
            }
        }
        if (id.equals("lizard_tail")) {
            MODDED_TIER_MATERIALS.put(id, obj);
        }
    }
    private static void registerWIP(String id, int maxStackSize) {
        RegistryObject<Item> obj = ITEMS.register(id, () -> new Item(new Item.Properties().stacksTo(maxStackSize)));
        ALL.put(id, obj);
        SORTED_ORDER_WIP.add(id);
        String[] itemType = id.split("_");
//        if (itemType.length == 2) {
            if (id.equals("lizard_scale") || itemType[1].equals("shard") || id.equals("prismatic_gem")) {
                ARMOR_ITEMS.put(id, obj);
                if (id.equals("prismatic_gem")) {
                    MODDED_TIER_MATERIALS.put(id, obj);
                }
            }
//        }
        if (id.equals("lizard_tail")) {
            MODDED_TIER_MATERIALS.put(id, obj);
        }
    }
    private static void registerFoodCOMPLETE(String id, FoodProperties foodType, int maxStackSize) {
        RegistryObject<Item> obj = ITEMS.register(id, () -> new Item(new Item.Properties().food(foodType).stacksTo(maxStackSize)));
        ALL.put(id, obj);
        SORTED_ORDER_COMPLETE.add(id);
    }
    private static void registerFoodWIP(String id, FoodProperties foodType, int maxStackSize) {
        RegistryObject<Item> obj = ITEMS.register(id, () -> new Item(new Item.Properties().food(foodType).stacksTo(maxStackSize)));
        ALL.put(id, obj);
        SORTED_ORDER_WIP.add(id);
    }
    private static void registerSwordCOMPLETE(String id, Tier minecraftTier, Tier moddedTier, int attackDamageModifier, float attackSpeedModifier, boolean isFireResistant) {
        RegistryObject<Item> obj;
        if (minecraftTier == null & moddedTier != null) {
            if (isFireResistant) {
                obj = ITEMS.register(id, () -> new SwordItem(moddedTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties().fireResistant()));
            } else {
                obj = ITEMS.register(id, () -> new SwordItem(moddedTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties()));
            }
            ALL.put(id, obj);
            SORTED_ORDER_COMPLETE.add(id);
        } else if (minecraftTier != null & moddedTier == null) {
            if (isFireResistant) {
                obj = ITEMS.register(id, () -> new SwordItem(minecraftTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties().fireResistant()));
            } else {
                obj = ITEMS.register(id, () -> new SwordItem(minecraftTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties()));
            }
            ALL.put(id, obj);
            SORTED_ORDER_COMPLETE.add(id);
        }
    }
    private static void registerSwordWIP(String id, Tier minecraftTier, Tier moddedTier, int attackDamageModifier, float attackSpeedModifier, boolean isFireResistant) {
        RegistryObject<Item> obj;
        if (minecraftTier == null & moddedTier != null) {
            if (isFireResistant) {
                obj = ITEMS.register(id, () -> new SwordItem(moddedTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties().fireResistant()));
            } else {
                obj = ITEMS.register(id, () -> new SwordItem(moddedTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties()));
            }
            ALL.put(id, obj);
            SORTED_ORDER_WIP.add(id);
        } else if (minecraftTier != null & moddedTier == null) {
            if (isFireResistant) {
                obj = ITEMS.register(id, () -> new SwordItem(minecraftTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties().fireResistant()));
            } else {
                obj = ITEMS.register(id, () -> new SwordItem(minecraftTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties()));
            }
            ALL.put(id, obj);
            SORTED_ORDER_WIP.add(id);
        }
    }
    private static void registerPickaxeCOMPLETE(String id, Tier minecraftTier, Tier moddedTier, int attackDamageModifier, float attackSpeedModifier, boolean isFireResistant) {
        RegistryObject<Item> obj;
        if (minecraftTier == null & moddedTier != null) {
            if (isFireResistant) {
                obj = ITEMS.register(id, () -> new PickaxeItem(moddedTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties().fireResistant()));
            } else {
                obj = ITEMS.register(id, () -> new PickaxeItem(moddedTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties()));
            }
            ALL.put(id, obj);
            SORTED_ORDER_COMPLETE.add(id);
        } else if (minecraftTier != null & moddedTier == null) {
            if (isFireResistant) {
                obj = ITEMS.register(id, () -> new PickaxeItem(minecraftTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties().fireResistant()));
            } else {
                obj = ITEMS.register(id, () -> new PickaxeItem(minecraftTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties()));
            }
            ALL.put(id, obj);
            SORTED_ORDER_COMPLETE.add(id);
        }
    }
    private static void registerPickaxeWIP(String id, Tier minecraftTier, Tier moddedTier, int attackDamageModifier, float attackSpeedModifier, boolean isFireResistant) {
        RegistryObject<Item> obj;
        if (minecraftTier == null & moddedTier != null) {
            if (isFireResistant) {
                obj = ITEMS.register(id, () -> new PickaxeItem(moddedTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties().fireResistant()));
            } else {
                obj = ITEMS.register(id, () -> new PickaxeItem(moddedTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties()));
            }
            ALL.put(id, obj);
            SORTED_ORDER_WIP.add(id);
        } else if (minecraftTier != null & moddedTier == null) {
            if (isFireResistant) {
                obj = ITEMS.register(id, () -> new PickaxeItem(minecraftTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties().fireResistant()));
            } else {
                obj = ITEMS.register(id, () -> new PickaxeItem(minecraftTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties()));
            }
            ALL.put(id, obj);
            SORTED_ORDER_WIP.add(id);
        }
    }
    private static void registerAxeCOMPLETE(String id, Tier minecraftTier, Tier moddedTier, float attackDamageModifier, float attackSpeedModifier, boolean isFireResistant) {
        RegistryObject<Item> obj;
        if (minecraftTier == null & moddedTier != null) {
            if (isFireResistant) {
                obj = ITEMS.register(id, () -> new AxeItem(moddedTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties().fireResistant()));
            } else {
                obj = ITEMS.register(id, () -> new AxeItem(moddedTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties()));
            }
            ALL.put(id, obj);
            SORTED_ORDER_COMPLETE.add(id);
        } else if (minecraftTier != null & moddedTier == null) {
            if (isFireResistant) {
                obj = ITEMS.register(id, () -> new AxeItem(minecraftTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties().fireResistant()));
            } else {
                obj = ITEMS.register(id, () -> new AxeItem(minecraftTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties()));
            }
            ALL.put(id, obj);
            SORTED_ORDER_COMPLETE.add(id);
        }
    }
    private static void registerAxeWIP(String id, Tier minecraftTier, Tier moddedTier, float attackDamageModifier, float attackSpeedModifier, boolean isFireResistant) {
        RegistryObject<Item> obj;
        if (minecraftTier == null & moddedTier != null) {
            if (isFireResistant) {
                obj = ITEMS.register(id, () -> new AxeItem(moddedTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties().fireResistant()));
            } else {
                obj = ITEMS.register(id, () -> new AxeItem(moddedTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties()));
            }
            ALL.put(id, obj);
            SORTED_ORDER_WIP.add(id);
        } else if (minecraftTier != null & moddedTier == null) {
            if (isFireResistant) {
                obj = ITEMS.register(id, () -> new AxeItem(minecraftTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties().fireResistant()));
            } else {
                obj = ITEMS.register(id, () -> new AxeItem(minecraftTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties()));
            }
            ALL.put(id, obj);
            SORTED_ORDER_WIP.add(id);
        }
    }
    private static void registerShovelCOMPLETE(String id, Tier minecraftTier, Tier moddedTier, float attackDamageModifier, float attackSpeedModifier, boolean isFireResistant) {
        RegistryObject<Item> obj;
        if (minecraftTier == null & moddedTier != null) {
            if (isFireResistant) {
                obj = ITEMS.register(id, () -> new ShovelItem(moddedTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties().fireResistant()));
            } else {
                obj = ITEMS.register(id, () -> new ShovelItem(moddedTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties()));
            }
            ALL.put(id, obj);
            SORTED_ORDER_COMPLETE.add(id);
        } else if (minecraftTier != null & moddedTier == null) {
            if (isFireResistant) {
                obj = ITEMS.register(id, () -> new ShovelItem(minecraftTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties().fireResistant()));
            } else {
                obj = ITEMS.register(id, () -> new ShovelItem(minecraftTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties()));
            }
            ALL.put(id, obj);
            SORTED_ORDER_COMPLETE.add(id);
        }
    }
    private static void registerShovelWIP(String id, Tier minecraftTier, Tier moddedTier, float attackDamageModifier, float attackSpeedModifier, boolean isFireResistant) {
        RegistryObject<Item> obj;
        if (minecraftTier == null & moddedTier != null) {
            if (isFireResistant) {
                obj = ITEMS.register(id, () -> new ShovelItem(moddedTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties().fireResistant()));
            } else {
                obj = ITEMS.register(id, () -> new ShovelItem(moddedTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties()));
            }
            ALL.put(id, obj);
            SORTED_ORDER_WIP.add(id);
        } else if (minecraftTier != null & moddedTier == null) {
            if (isFireResistant) {
                obj = ITEMS.register(id, () -> new ShovelItem(minecraftTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties().fireResistant()));
            } else {
                obj = ITEMS.register(id, () -> new ShovelItem(minecraftTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties()));
            }
            ALL.put(id, obj);
            SORTED_ORDER_WIP.add(id);
        }
    }
    private static void registerHoeCOMPLETE(String id, Tier minecraftTier, Tier moddedTier, int attackDamageModifier, float attackSpeedModifier, boolean isFireResistant) {
        RegistryObject<Item> obj;
        if (minecraftTier == null & moddedTier != null) {
            if (isFireResistant) {
                obj = ITEMS.register(id, () -> new HoeItem(moddedTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties().fireResistant()));
            } else {
                obj = ITEMS.register(id, () -> new HoeItem(moddedTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties()));
            }
            ALL.put(id, obj);
            SORTED_ORDER_COMPLETE.add(id);
        } else if (minecraftTier != null & moddedTier == null) {
            if (isFireResistant) {
                obj = ITEMS.register(id, () -> new HoeItem(minecraftTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties().fireResistant()));
            } else {
                obj = ITEMS.register(id, () -> new HoeItem(minecraftTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties()));
            }
            ALL.put(id, obj);
            SORTED_ORDER_COMPLETE.add(id);
        }
    }
    private static void registerHoeWIP(String id, Tier minecraftTier, Tier moddedTier, int attackDamageModifier, float attackSpeedModifier, boolean isFireResistant) {
        RegistryObject<Item> obj;
        if (minecraftTier == null & moddedTier != null) {
            if (isFireResistant) {
                obj = ITEMS.register(id, () -> new HoeItem(moddedTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties().fireResistant()));
            } else {
                obj = ITEMS.register(id, () -> new HoeItem(moddedTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties()));
            }
            ALL.put(id, obj);
            SORTED_ORDER_WIP.add(id);
        } else if (minecraftTier != null & moddedTier == null) {
            if (isFireResistant) {
                obj = ITEMS.register(id, () -> new HoeItem(minecraftTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties().fireResistant()));
            } else {
                obj = ITEMS.register(id, () -> new HoeItem(minecraftTier, attackDamageModifier,
                        attackSpeedModifier, new Item.Properties()));
            }
            ALL.put(id, obj);
            SORTED_ORDER_WIP.add(id);
        }
    }
    private static void registerArmorCOMPLETE(String id, ModArmorMaterials armorMaterial, ArmorItem.Type armorType) {
        RegistryObject<Item> obj = ITEMS.register(id, () -> new ArmorItem(armorMaterial, armorType, new Item.Properties()));
        ALL.put(id, obj);
        SORTED_ORDER_COMPLETE.add(id);
    }
    private static void registerArmorWIP(String id, ModArmorMaterials armorMaterial, ArmorItem.Type armorType) {
        RegistryObject<Item> obj = ITEMS.register(id, () -> new ArmorItem(armorMaterial, armorType, new Item.Properties()));
        ALL.put(id, obj);
        SORTED_ORDER_WIP.add(id);
    }

    static {
        setUpMaps();
        if (PARTS_COMPLETE != null) {
            for (PrismColor c : PrismColor.values()) {
                String prefix = c.name().toLowerCase();
                for (String part : PARTS_COMPLETE) {
                    String id = prefix + "_" + part;
                    int maxStackSize = MAX_STACK_SIZE.get(part);
                    FoodProperties foodType = FOOD_TYPES.get(part);
                    if (foodType != null) {
                        registerFoodCOMPLETE(id, foodType, maxStackSize);
                    } else {
                        Tier tierType = MINECRAFT_TIERS.get(part);
                        if (tierType != null) {
                            switch (part) {
                                case "sword":
                                    registerSwordCOMPLETE(id, tierType, null, 3, -2.4F, false);
                                    break;
                                case "pickaxe":
                                    registerPickaxeCOMPLETE(id, tierType, null, 1, -2.8F, false);
                                    break;
                                case "axe":
                                    registerAxeCOMPLETE(id, tierType, null, 5.0F, -3.0F, false);
                                    break;
                                case "shovel":
                                    registerShovelCOMPLETE(id, tierType, null, 1.5F, -3.0F, false);
                                    break;
                                case "hoe":
                                    registerHoeCOMPLETE(id, tierType, null, -3, -0.0F, false);
                                    break;
                                default:
                                    throw new IllegalToolException("'" + id + "' is not a valid tool or armor!");
                            }
                        } else {
                            tierType = MODDED_TIERS.get(part);
                            if (tierType != null) {
                                switch (part) {
                                    case "sword":
                                        registerSwordCOMPLETE(id, null, tierType, 5, -2.3F, true);
                                        break;
                                    case "pickaxe":
                                        registerPickaxeCOMPLETE(id, null, tierType, 3, -2.7F, true);
                                        break;
                                    case "axe":
                                        registerAxeCOMPLETE(id, null, tierType, 6.1F, -2.9F, true);
                                        break;
                                    case "shovel":
                                        registerShovelCOMPLETE(id, null, tierType, 2.6F, -2.9F, true);
                                        break;
                                    case "hoe":
                                        registerHoeCOMPLETE(id, null, tierType, -5, -0.0F, true);
                                        break;
                                    default:
                                        throw new IllegalToolException("'" + id + "' is not a valid tool or armor!");
                                }
                            } else {
                                ModArmorMaterials armorMaterial = ARMOR_MATERIALS.get(id);
                                ArmorItem.Type armorType = ARMOR_TYPES.get(part);
                                if (armorMaterial != null && armorType != null) {
                                    registerArmorCOMPLETE(id, armorMaterial, armorType);
                                } else {
                                    registerCOMPLETE(id, maxStackSize);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (PRISMATIC_PARTS_COMPLETE != null) {
            for (String part : PRISMATIC_PARTS_COMPLETE) {
                String id = "prismatic_" + part;
                int maxStackSize = MAX_STACK_SIZE.get(part);
                Tier tierType = MODDED_TIERS.get(part);
                if (tierType != null) {
                    switch (part) {
                        case "sword":
                            registerSwordCOMPLETE(id, null, tierType, 5, -2.3F, true);
                            break;
                        case "pickaxe":
                            registerPickaxeCOMPLETE(id, null, tierType, 3, -2.7F, true);
                            break;
                        case "axe":
                            registerAxeCOMPLETE(id, null, tierType, 6.1F, -2.9F, true);
                            break;
                        case "shovel":
                            registerShovelCOMPLETE(id, null, tierType, 2.6F, -2.9F, true);
                            break;
                        case "hoe":
                            registerHoeCOMPLETE(id, null, tierType, -5, -0.0F, true);
                            break;
                        default:
                            throw new IllegalToolException("'" + id + "' is not a valid tool or armor!");
                    }
                } else {
                    ModArmorMaterials armorMaterial = ARMOR_MATERIALS.get(id);
                    ArmorItem.Type armorType = ARMOR_TYPES.get(part);
                    if (armorMaterial != null && armorType != null) {
                        registerArmorCOMPLETE(id, armorMaterial, armorType);
                    } else {
                        registerCOMPLETE(id, maxStackSize);
                    }
                }
            }
        }
        if (NONCOLOR_ITEMS_COMPLETE != null) {
            for (String id : NONCOLOR_ITEMS_COMPLETE) {
                int maxStackSize = MAX_STACK_SIZE.get(id);
                FoodProperties foodType = FOOD_TYPES.get(id);
                if (foodType != null) {
                    registerFoodCOMPLETE(id, foodType, maxStackSize);
                } else {
                    ModArmorMaterials armorMaterial = ARMOR_MATERIALS.get(id);
                    ArmorItem.Type armorType = ARMOR_TYPES.get(id);
                    if (armorMaterial != null && armorType != null) {
                        registerArmorCOMPLETE(id, armorMaterial, armorType);
                    } else {
                        Tier tier = MODDED_TIERS.get(id);
                        if (tier != null) {
                            registerSwordCOMPLETE(id, null, tier, 5, -3.5f, false);
                        } else {
                            registerCOMPLETE(id, maxStackSize);
                        }
                    }
                }
            }
        }



        if (PARTS_WIP != null) {
            for (PrismColor c : PrismColor.values()) {
                String prefix = c.name().toLowerCase();
                for (String part : PARTS_WIP) {
                    String id = prefix + "_" + part;
                    int maxStackSize = MAX_STACK_SIZE.get(part);
                    FoodProperties foodType = FOOD_TYPES.get(part);
                    if (foodType != null) {
                        registerFoodWIP(id, foodType, maxStackSize);
                    } else {
                        Tier tierType = MINECRAFT_TIERS.get(part);
                        if (tierType != null) {
                            switch (part) {
                                case "sword":
                                    registerSwordWIP(id, tierType, null, 3, -2.4F, false);
                                    break;
                                case "pickaxe":
                                    registerPickaxeWIP(id, tierType, null, 1, -2.8F, false);
                                    break;
                                case "axe":
                                    registerAxeWIP(id, tierType, null, 5.0F, -3.0F, false);
                                    break;
                                case "shovel":
                                    registerShovelWIP(id, tierType, null, 1.5F, -3.0F, false);
                                    break;
                                case "hoe":
                                    registerHoeWIP(id, tierType, null, -3, -0.0F, false);
                                    break;
                                default:
                                    throw new IllegalToolException("'" + id + "' is not a valid tool or armor!");
                            }
                        } else {
                            tierType = MODDED_TIERS.get(part);
                            if (tierType != null) {
                                switch (part) {
                                    case "sword":
                                        registerSwordWIP(id, null, tierType, 5, -2.3F, true);
                                        break;
                                    case "pickaxe":
                                        registerPickaxeWIP(id, null, tierType, 3, -2.7F, true);
                                        break;
                                    case "axe":
                                        registerAxeWIP(id, null, tierType, 6.1F, -2.9F, true);
                                        break;
                                    case "shovel":
                                        registerShovelWIP(id, null, tierType, 2.6F, -2.9F, true);
                                        break;
                                    case "hoe":
                                        registerHoeWIP(id, null, tierType, -5, -0.0F, true);
                                        break;
                                    default:
                                        throw new IllegalToolException("'" + id + "' is not a valid tool or armor!");
                                }
                            } else {
                                ModArmorMaterials armorMaterial = ARMOR_MATERIALS.get(id);
                                ArmorItem.Type armorType = ARMOR_TYPES.get(part);
                                if (armorMaterial != null && armorType != null) {
                                    registerArmorWIP(id, armorMaterial, armorType);
                                } else {
                                    registerWIP(id, maxStackSize);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (PRISMATIC_PARTS_WIP != null) {
            for (String part : PRISMATIC_PARTS_WIP) {
                String id = "prismatic_" + part;
                int maxStackSize = MAX_STACK_SIZE.get(part);
                Tier tierType = MODDED_TIERS.get(part);
                if (tierType != null) {
                    switch (part) {
                        case "sword":
                            registerSwordWIP(id, null, tierType, 5, -2.3F, true);
                            break;
                        case "pickaxe":
                            registerPickaxeWIP(id, null, tierType, 3, -2.7F, true);
                            break;
                        case "axe":
                            registerAxeWIP(id, null, tierType, 6.1F, -2.9F, true);
                            break;
                        case "shovel":
                            registerShovelWIP(id, null, tierType, 2.6F, -2.9F, true);
                            break;
                        case "hoe":
                            registerHoeWIP(id, null, tierType, -5, -0.0F, true);
                            break;
                        default:
                            throw new IllegalToolException("'" + id + "' is not a valid tool or armor!");
                    }
                } else {
                    ModArmorMaterials armorMaterial = ARMOR_MATERIALS.get(id);
                    ArmorItem.Type armorType = ARMOR_TYPES.get(part);
                    if (armorMaterial != null && armorType != null) {
                        registerArmorWIP(id, armorMaterial, armorType);
                    } else {
                        registerWIP(id, maxStackSize);
                    }
                }
            }
        }
        if (NONCOLOR_ITEMS_WIP != null) {
            for (String id : NONCOLOR_ITEMS_WIP) {
                int maxStackSize = MAX_STACK_SIZE.get(id);
                FoodProperties foodType = FOOD_TYPES.get(id);
                if (foodType != null) {
                    registerFoodWIP(id, foodType, maxStackSize);
                } else {
                    ModArmorMaterials armorMaterial = ARMOR_MATERIALS.get(id);
                    ArmorItem.Type armorType = ARMOR_TYPES.get(id);
                    if (armorMaterial != null && armorType != null) {
                        registerArmorWIP(id, armorMaterial, armorType);
                    } else {
                        Tier tier = MODDED_TIERS.get(id);
                        if (tier != null) {
                            registerSwordWIP(id, null, tier, 5, -3.5f, false);
                        } else {
                            registerWIP(id, maxStackSize);
                        }
                    }
                }
            }
        }
        registerPrismLighter();
    }

    public static RegistryObject<Item> get(String id) {
        return ALL.get(id);
    }
    public static List<Item> getShards() {
        return List.of(ALL.get("red_shard").get(),
                ALL.get("orange_shard").get(),
                ALL.get("yellow_shard").get(),
                ALL.get("green_shard").get(),
                ALL.get("blue_shard").get(),
                ALL.get("cyan_shard").get(),
                ALL.get("purple_shard").get(),
                ALL.get("pink_shard").get(),
                ALL.get("white_shard").get()
        );
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    private static void registerPrismLighter() {
        RegistryObject<Item> obj = ITEMS.register("prism_lighter", () -> new PrismLighterItem(new Item.Properties().durability(100)));
        ALL.put("prism_lighter", obj);
        SORTED_ORDER_WIP.add("prism_lighter");
    }
}