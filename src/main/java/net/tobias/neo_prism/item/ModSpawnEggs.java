package net.tobias.neo_prism.item;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.entity.ModEntities;
import net.tobias.neo_prism.entity.custom.ShardLizardScoutEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModSpawnEggs {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Neo_Prism.MOD_ID);

private static final List<String> EGGS_COMPLETE = null;
    private static final List<String> EGGS_WIP = List.of(
            "shard_lizard_scout"
    );

    private static final Map<String, Integer> BACKGROUND_COLORS = new HashMap<>();
    private static final Map<String, Integer> HIGHLIGHT_COLORS = new HashMap<>();
    private static final Map<String, RegistryObject<EntityType<? extends ShardLizardScoutEntity>>> MOBS = new HashMap<>();
    private static void setUpMapsAndColors() {
        BACKGROUND_COLORS.put("shard_lizard_scout", 0x3d3d43);
        HIGHLIGHT_COLORS.put("shard_lizard_scout", 0xff0000);
        MOBS.put("shard_lizard_scout", ModEntities.SHARD_LIZARD_SCOUT);
    }


    public static final Map<String, RegistryObject<Item>> ALL = new HashMap<>();
    public static final List<String> SORTED_ORDER_COMPETE = new ArrayList<>();
    public static final List<String> SORTED_ORDER_WIP = new ArrayList<>();

    private static void registerCOMPLETE(String id, int backgroundColor, int highlightColor, RegistryObject<EntityType<? extends ShardLizardScoutEntity>> mob) {
        RegistryObject<Item> obj = ITEMS.register(id, () -> new ForgeSpawnEggItem(mob, backgroundColor,
                highlightColor, new Item.Properties()));
        ALL.put(id, obj);
        SORTED_ORDER_COMPETE.add(id);
    }
    private static void registerWIP(String id, int backgroundColor, int highlightColor, RegistryObject<EntityType<? extends ShardLizardScoutEntity>> mob) {
        RegistryObject<Item> obj = ITEMS.register(id, () -> new ForgeSpawnEggItem(mob, backgroundColor,
                highlightColor, new Item.Properties()));
        ALL.put(id, obj);
        SORTED_ORDER_WIP.add(id);
    }


    static {
        setUpMapsAndColors();
        if (EGGS_COMPLETE != null) {
            for (String prefix : EGGS_COMPLETE) {
                int backgroundColor = BACKGROUND_COLORS.get(prefix);
                int highlightColor = HIGHLIGHT_COLORS.get(prefix);
                RegistryObject<EntityType<? extends ShardLizardScoutEntity>> mob = MOBS.get(prefix);
                String id = prefix + "_spawn_egg";
                registerCOMPLETE(id, backgroundColor, highlightColor, mob);
            }
        }
        if (EGGS_WIP != null) {
            for (String prefix : EGGS_WIP) {
                int backgroundColor = BACKGROUND_COLORS.get(prefix);
                int highlightColor = HIGHLIGHT_COLORS.get(prefix);
                RegistryObject<EntityType<? extends ShardLizardScoutEntity>> mob = MOBS.get(prefix);
                String id = prefix + "_spawn_egg";
                registerWIP(id, backgroundColor, highlightColor, mob);
            }
        }
    }

    public static RegistryObject<Item> get(String id) {
        return ALL.get(id);
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

}