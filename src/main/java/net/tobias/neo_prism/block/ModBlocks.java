package net.tobias.neo_prism.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.PrismColor;
import net.tobias.neo_prism.item.ModItems;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Neo_Prism.MOD_ID);

    private static final List<String> PARTS_COMPLETE = List.of(
            "hay_bale","shattered_shard_ore","shattered_shard_deepslate_ore","gem_block"
    );
    private static final List<String> PARTS_WIP = null;
    private static final Map<String, Block> BLOCK_MAP = new HashMap<>();
    private static final Map<String, String> BLOCK_XP = new HashMap<>();

    private static void setupBlocks(){
        BLOCK_MAP.put("hay_bale", Blocks.HAY_BLOCK);
        BLOCK_MAP.put("gem_block", Blocks.DIAMOND_BLOCK);
        BLOCK_MAP.put("shattered_shard_ore", Blocks.DIAMOND_ORE);
        BLOCK_XP.put("shattered_shard_ore", "4,8");
        BLOCK_MAP.put("shattered_shard_deepslate_ore", Blocks.DEEPSLATE_DIAMOND_ORE);
        BLOCK_XP.put("shattered_shard_deepslate_ore", "4,8");
    }

    public static final Map<String, RegistryObject<Block>> ALL = new HashMap<>();
    public static final List<String> SORTED_ORDER_COMPETE = new ArrayList<>();
    public static final List<String> SORTED_ORDER_WIP = new ArrayList<>();

    private static RegistryObject<Block> registerSans_XP_COMPLETE(String id, Supplier<? extends Block> baseBlock) {
        RegistryObject<Block> obj = registerBlock(id, () -> new Block(BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops()));
        ALL.put(id, obj);
        SORTED_ORDER_COMPETE.add(id);
        return obj;
    }
    private static RegistryObject<Block> registerSans_XP_WIP(String id, Supplier<? extends Block> baseBlock) {
        RegistryObject<Block> obj = registerBlock(id, () -> new Block(BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops()));
        ALL.put(id, obj);
        SORTED_ORDER_WIP.add(id);
        return obj;
    }
    private static RegistryObject<Block> registerPro_XP_COMPLETE(String id, Supplier<? extends Block> baseBlock, int minInclusive, int maxInclusive) {
        RegistryObject<Block> obj = registerBlock(id, () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops(), UniformInt.of(minInclusive, maxInclusive)));
        ALL.put(id, obj);
        SORTED_ORDER_COMPETE.add(id);
        return obj;
    }
    private static RegistryObject<Block> registerPro_XP_WIP(String id, Supplier<? extends Block> baseBlock, int minInclusive, int maxInclusive) {
        RegistryObject<Block> obj = registerBlock(id, () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops(), UniformInt.of(minInclusive, maxInclusive)));
        ALL.put(id, obj);
        SORTED_ORDER_WIP.add(id);
        return obj;
    }

    static {
        setupBlocks();
        if (PARTS_COMPLETE != null) {
            for (PrismColor c : PrismColor.values()) {
                String prefix = c.name().toLowerCase();
                for (String part : PARTS_COMPLETE) {
                    Block baseBlock = BLOCK_MAP.get(part);
                    if (baseBlock == null) {
                        throw new IllegalStateException("No base block mapping for complete part: " + part);
                    }
                    if (BLOCK_XP.containsKey(part)) {
                        String[] splitValues = BLOCK_XP.get(part).split(",");
                        if (splitValues.length < 2) {
                            throw new IllegalStateException("Invalid XP config for part: " + part);
                        }
                        int xpMin = Integer.parseInt(splitValues[0].trim());
                        int xpMax = Integer.parseInt(splitValues[1].trim());
                        registerPro_XP_COMPLETE(prefix + "_" + part, () -> baseBlock, xpMin, xpMax);
                    } else {
                        registerSans_XP_COMPLETE(prefix + "_" + part, () -> baseBlock);
                    }
                }
            }
        }
        if (PARTS_WIP != null) {
            for (PrismColor c : PrismColor.values()) {
                String prefix = c.name().toLowerCase();
                for (String part : PARTS_WIP) {
                    Block baseBlock = BLOCK_MAP.get(part);
                    if (baseBlock == null) {
                        throw new IllegalStateException("No base block mapping for WIP part: " + part);
                    }
                    if (BLOCK_XP.containsKey(part)) {
                        String[] splitValues = BLOCK_XP.get(part).split(",");
                        if (splitValues.length < 2) {
                            throw new IllegalStateException("Invalid XP config for part: " + part);
                        }
                        int xpMin = Integer.parseInt(splitValues[0].trim());
                        int xpMax = Integer.parseInt(splitValues[1].trim());
                        registerPro_XP_WIP(prefix + "_" + part, () -> baseBlock, xpMin, xpMax);
                    } else {
                        registerSans_XP_WIP(prefix + "_" + part, () -> baseBlock);
                    }
                }
            }
        }
        RegistryObject<Block> obj = registerBlock("prism_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.REINFORCED_DEEPSLATE).sound(SoundType.AMETHYST)));
        ALL.put("prism_block", obj);
        SORTED_ORDER_WIP.add("prism_block");
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static RegistryObject<Block> get(String id) {
        return ALL.get(id);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}