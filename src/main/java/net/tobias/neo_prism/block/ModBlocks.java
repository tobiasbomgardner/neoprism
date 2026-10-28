package net.tobias.neo_prism.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
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
    private static final List<String> NON_COLOR_COMPLETE = null;
    private static final List<String> NON_COLOR_WIP = List.of(
            "repstone_stairs","repstone_slab","repstone_wall","repstone_pressure_plate",
            "repstone_button","repstone_door","repstone_trapdoor","cobbled_repstone","cobbled_repstone_stairs",
            "cobbled_repstone_slab","cobbled_repstone_wall","repstone_bricks","repstone_brick_stairs","repstone_brick_slab",
            "repstone_brick_wall","chiseled_repstone_bricks","cracked_repstone_bricks"
    );
    private static final Map<String, Block> BLOCK_MAP = new HashMap<>();
    private static final Map<String, String> BLOCK_XP = new HashMap<>();
    private static final Map<String, BlockSetType> BLOCK_SET_TYPES = new HashMap<>();
    private static final Map<String, PressurePlateBlock.Sensitivity> BLOCK_SENSITIVITY_TYPES = new HashMap<>();
    private static final Map<String, Integer> BLOCKS_WITH_INTS = new HashMap<>();
    private static final Map<String, Boolean> BLOCKS_WITH_BOOLEANS = new HashMap<>();
    private static void setupBlocks() {
        BLOCK_MAP.put("hay_bale", Blocks.HAY_BLOCK);
        BLOCK_MAP.put("gem_block", Blocks.DIAMOND_BLOCK);
        BLOCK_MAP.put("shattered_shard_ore", Blocks.DIAMOND_ORE);
        BLOCK_MAP.put("shattered_shard_deepslate_ore", Blocks.DEEPSLATE_DIAMOND_ORE);

        BLOCK_MAP.put("repstone_stairs", Blocks.STONE_STAIRS);
        BLOCK_MAP.put("repstone_slab", Blocks.STONE_SLAB);
        BLOCK_MAP.put("repstone_button", Blocks.STONE_BUTTON);
        BLOCK_MAP.put("repstone_pressure_plate", Blocks.STONE);
        BLOCK_MAP.put("repstone_wall", Blocks.STONE);
        BLOCK_MAP.put("repstone_door", Blocks.STONE);
        BLOCK_MAP.put("repstone_trapdoor", Blocks.STONE);

        BLOCK_MAP.put("cobbled_repstone_stairs", Blocks.COBBLESTONE_STAIRS);
        BLOCK_MAP.put("cobbled_repstone_slab", Blocks.COBBLESTONE_SLAB);
        BLOCK_MAP.put("cobbled_repstone_wall", Blocks.COBBLESTONE);

        BLOCK_MAP.put("cracked_repstone_bricks", Blocks.CRACKED_STONE_BRICKS);
        BLOCK_MAP.put("chiseled_repstone_bricks", Blocks.CHISELED_STONE_BRICKS);
        BLOCK_MAP.put("repstone_brick_stairs", Blocks.STONE_BRICK_STAIRS);
        BLOCK_MAP.put("repstone_brick_slab", Blocks.STONE_BRICK_SLAB);
        BLOCK_MAP.put("repstone_brick_wall", Blocks.STONE_BRICKS);

        BLOCK_SET_TYPES.put("repstone_button", BlockSetType.IRON);
        BLOCK_SET_TYPES.put("repstone_pressure_plate", BlockSetType.IRON);
        BLOCK_SET_TYPES.put("repstone_door", BlockSetType.STONE);
        BLOCK_SET_TYPES.put("repstone_trapdoor", BlockSetType.STONE);

        BLOCK_SENSITIVITY_TYPES.put("repstone_pressure_plate", PressurePlateBlock.Sensitivity.EVERYTHING);

        BLOCKS_WITH_INTS.put("repstone_button", 30);
        BLOCKS_WITH_BOOLEANS.put("repstone_button", true);

        BLOCK_XP.put("shattered_shard_ore", "4,8");
        BLOCK_XP.put("shattered_shard_deepslate_ore", "4,8");
    }

    public static final Map<String, RegistryObject<Block>> ALL = new HashMap<>();
    public static final List<String> SORTED_ORDER_COMPLETE = new ArrayList<>();
    public static final List<String> SORTED_ORDER_WIP = new ArrayList<>();

    private static void registerSans_XP_COMPLETE(String id, Supplier<? extends Block> baseBlock) {
        RegistryObject<Block> obj = registerBlock(id, () -> new Block(BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops()));
        ALL.put(id, obj);
        SORTED_ORDER_COMPLETE.add(id);
    }
    private static void registerSans_XP_WIP(String id, Supplier<? extends Block> baseBlock) {
        RegistryObject<Block> obj = registerBlock(id, () -> new Block(BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops()));
        ALL.put(id, obj);
        SORTED_ORDER_WIP.add(id);
    }
    private static void registerPro_XP_COMPLETE(String id, Supplier<? extends Block> baseBlock, int minInclusive, int maxInclusive) {
        RegistryObject<Block> obj = registerBlock(id, () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops(), UniformInt.of(minInclusive, maxInclusive)));
        ALL.put(id, obj);
        SORTED_ORDER_COMPLETE.add(id);
    }
    private static void registerPro_XP_WIP(String id, Supplier<? extends Block> baseBlock, int minInclusive, int maxInclusive) {
        RegistryObject<Block> obj = registerBlock(id, () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops(), UniformInt.of(minInclusive, maxInclusive)));
        ALL.put(id, obj);
        SORTED_ORDER_WIP.add(id);
    }
    private static void registerRotatedPillarBlockCOMPLETE(String id, Supplier<? extends Block> baseBlock) {
        RegistryObject<Block> obj = registerBlock(id, () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops()));
        ALL.put(id, obj);
        SORTED_ORDER_COMPLETE.add(id);
    }
    private static void registerRotatedPillarBlockWIP(String id, Supplier<? extends Block> baseBlock) {
        RegistryObject<Block> obj = registerBlock(id, () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops()));
        ALL.put(id, obj);
        SORTED_ORDER_WIP.add(id);
    }
    private static void registerStairsCOMPLETE(String id, Supplier<? extends Block> baseBlock, Supplier<? extends Block> ingredientBlock) {
        RegistryObject<Block> obj = registerBlock(id, () -> new StairBlock(() -> ingredientBlock.get().defaultBlockState(),
                BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops()));
        ALL.put(id, obj);
        SORTED_ORDER_COMPLETE.add(id);
    }
    private static void registerStairsWIP(String id, Supplier<? extends Block> baseBlock, Supplier<? extends Block> ingredientBlock) {
        RegistryObject<Block> obj = registerBlock(id, () -> new StairBlock(() -> ingredientBlock.get().defaultBlockState(),
                BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops()));
        ALL.put(id, obj);
        SORTED_ORDER_WIP.add(id);
    }
    private static void registerSlabCOMPLETE(String id, Supplier<? extends Block> baseBlock) {
        RegistryObject<Block> obj = registerBlock(id, () -> new SlabBlock(BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops()));
        ALL.put(id, obj);
        SORTED_ORDER_COMPLETE.add(id);
    }
    private static void registerSlabWIP(String id, Supplier<? extends Block> baseBlock) {
        RegistryObject<Block> obj = registerBlock(id, () -> new SlabBlock(BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops()));
        ALL.put(id, obj);
        SORTED_ORDER_WIP.add(id);
    }
    private static void registerButtonCOMPLETE(String id, Supplier<? extends Block> baseBlock, BlockSetType blockSetType, int ticksToStayPressed, boolean arrowsCanPress) {
        RegistryObject<Block> obj = registerBlock(id, () -> new ButtonBlock(
                BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops(), blockSetType,
                ticksToStayPressed, arrowsCanPress));
        ALL.put(id, obj);
        SORTED_ORDER_COMPLETE.add(id);
    }
    private static void registerButtonWIP(String id, Supplier<? extends Block> baseBlock, BlockSetType blockSetType, int ticksToStayPressed, boolean arrowsCanPress) {
        RegistryObject<Block> obj = registerBlock(id, () -> new ButtonBlock(
                BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops(), blockSetType,
                ticksToStayPressed, arrowsCanPress));
        ALL.put(id, obj);
        SORTED_ORDER_WIP.add(id);
    }
    private static void registerPressurePlateCOMPLETE(String id, PressurePlateBlock.Sensitivity sensitivity, Supplier<? extends Block> baseBlock, BlockSetType blockSetType) {
        RegistryObject<Block> obj = registerBlock(id, () -> new PressurePlateBlock(sensitivity,
                BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops(), blockSetType));
        ALL.put(id, obj);
        SORTED_ORDER_COMPLETE.add(id);
    }
    private static void registerPressurePlateWIP(String id, PressurePlateBlock.Sensitivity sensitivity, Supplier<? extends Block> baseBlock, BlockSetType blockSetType) {
        RegistryObject<Block> obj = registerBlock(id, () -> new PressurePlateBlock(sensitivity,
                BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops(), blockSetType));
        ALL.put(id, obj);
        SORTED_ORDER_WIP.add(id);
    }
    private static void registerWallCOMPLETE(String id, Supplier<? extends Block> baseBlock) {
        RegistryObject<Block> obj = registerBlock(id, () -> new WallBlock(BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops()));
        ALL.put(id, obj);
        SORTED_ORDER_COMPLETE.add(id);
    }
    private static void registerWallWIP(String id, Supplier<? extends Block> baseBlock) {
        RegistryObject<Block> obj = registerBlock(id, () -> new WallBlock(BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops()));
        ALL.put(id, obj);
        SORTED_ORDER_WIP.add(id);
    }
    private static void registerDoorCOMPLETE(String id, Supplier<? extends Block> baseBlock, BlockSetType blockSetType) {
        RegistryObject<Block> obj = registerBlock(id, () -> new DoorBlock(BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops().noOcclusion(),
                blockSetType));
        ALL.put(id, obj);
        SORTED_ORDER_COMPLETE.add(id);
    }
    private static void registerDoorWIP(String id, Supplier<? extends Block> baseBlock, BlockSetType blockSetType) {
        RegistryObject<Block> obj = registerBlock(id, () -> new DoorBlock(BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops().noOcclusion(),
                blockSetType));
        ALL.put(id, obj);
        SORTED_ORDER_WIP.add(id);
    }
    private static void registerTrapDoorCOMPLETE(String id, Supplier<? extends Block> baseBlock, BlockSetType blockSetType) {
        RegistryObject<Block> obj = registerBlock(id, () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops().noOcclusion(),
                blockSetType));
        ALL.put(id, obj);
        SORTED_ORDER_COMPLETE.add(id);
    }
    private static void registerTrapDoorWIP(String id, Supplier<? extends Block> baseBlock, BlockSetType blockSetType) {
        RegistryObject<Block> obj = registerBlock(id, () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(baseBlock.get()).requiresCorrectToolForDrops().noOcclusion(),
                blockSetType));
        ALL.put(id, obj);
        SORTED_ORDER_WIP.add(id);
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
                        if (!part.equals("hay_bale")) {
                            registerSans_XP_COMPLETE(prefix + "_" + part, () -> baseBlock);
                        } else {
                            registerRotatedPillarBlockCOMPLETE(prefix + "_" + part, () -> baseBlock);
                        }
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
                        if (!part.equals("hay_bale")) {
                            registerSans_XP_WIP(prefix + "_" + part, () -> baseBlock);
                        } else {
                            registerRotatedPillarBlockWIP(prefix + "_" + part, () -> baseBlock);
                        }
                    }
                }
            }
        }


        RegistryObject<Block> REPSTONE = registerBlock("repstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
        ALL.put("repstone", REPSTONE);
        SORTED_ORDER_WIP.add("repstone");
        RegistryObject<Block> COBBLED_REPSTONE = registerBlock("cobbled_repstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
        RegistryObject<Block> REPSTONE_BRICKS = registerBlock("repstone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
        if (NON_COLOR_COMPLETE != null) {
            for (String id : NON_COLOR_COMPLETE) {
                Block baseBlock = BLOCK_MAP.get(id);
                if (baseBlock == null && !id.equals("cobbled_repstone") && !id.equals("repstone_bricks")) {
                    throw new IllegalStateException("No base block mapping for WIP part: " + id);
                }
                String[] parts = id.split("_");
                if (parts.length > 3) {
                    throw new IllegalArgumentException("Block ID is too long! Max: 3");
                } else {
                    boolean isFullBlock = true;
                    if (parts.length == 2) {
                        if (id.equals("cobbled_repstone")) {
                            ALL.put(id, COBBLED_REPSTONE);
                            SORTED_ORDER_WIP.add(id);
                            isFullBlock = false;
                        }
                        if (id.equals("repstone_bricks")) {
                            ALL.put(id, REPSTONE_BRICKS);
                            SORTED_ORDER_WIP.add(id);
                            isFullBlock = false;
                        }
                        if (parts[1].equals("slab")) {
                            registerSlabCOMPLETE(id, () -> baseBlock);
                            isFullBlock = false;
                        }
                        if (parts[1].equals("wall")) {
                            registerWallCOMPLETE(id, () -> baseBlock);
                            isFullBlock = false;
                        }
                        if (parts[1].equals("stairs")) {
                            if (id.equals("repstone_stairs")) {
                                registerStairsCOMPLETE(id, () -> baseBlock, REPSTONE);
                                isFullBlock = false;
                            }
                        }
                        if (parts[1].equals("button")) {
                            BlockSetType blockSetType = BLOCK_SET_TYPES.get(id);
                            int ticksToStayPressed = BLOCKS_WITH_INTS.get(id);
                            boolean arrowsCanPress = BLOCKS_WITH_BOOLEANS.get(id);
                            registerButtonCOMPLETE(id, () -> baseBlock, blockSetType, ticksToStayPressed, arrowsCanPress);
                            isFullBlock = false;
                        }
                        if (parts[1].equals("pressure")) {
                            PressurePlateBlock.Sensitivity sensitivity = BLOCK_SENSITIVITY_TYPES.get(id);
                            BlockSetType blockSetType = BLOCK_SET_TYPES.get(id);
                            registerPressurePlateCOMPLETE(id, sensitivity, () -> baseBlock, blockSetType);
                            isFullBlock = false;
                        }
                        if (parts[1].equals("door")) {
                            BlockSetType blockSetType = BLOCK_SET_TYPES.get(id);
                            registerDoorCOMPLETE(id, () -> baseBlock, blockSetType);
                            isFullBlock = false;
                        }
                        if (parts[1].equals("trapdoor")) {
                            BlockSetType blockSetType = BLOCK_SET_TYPES.get(id);
                            registerTrapDoorCOMPLETE(id, () -> baseBlock, blockSetType);
                            isFullBlock = false;
                        }
                        if (isFullBlock) {
                            registerSans_XP_COMPLETE(id, () -> baseBlock);
                        }
                    } else if (parts.length == 3) {
                        if (parts[1].equals("slab") || parts[2].equals("slab")) {
                            registerSlabCOMPLETE(id, () -> baseBlock);
                            isFullBlock = false;
                        }
                        if (parts[1].equals("wall") || parts[2].equals("wall")) {
                            registerWallCOMPLETE(id, () -> baseBlock);
                            isFullBlock = false;
                        }
                        if (parts[1].equals("stairs") || parts[2].equals("stairs")) {
                            if (id.equals("cobbled_repstone_stairs")) {
                                registerStairsCOMPLETE(id, () -> baseBlock, COBBLED_REPSTONE);
                                isFullBlock = false;
                            } else {
                                registerStairsCOMPLETE(id, () -> baseBlock, REPSTONE_BRICKS);
                                isFullBlock = false;
                            }
                        }
                        if (parts[1].equals("pressure") || parts[2].equals("plate")) {
                            PressurePlateBlock.Sensitivity sensitivity = BLOCK_SENSITIVITY_TYPES.get(id);
                            BlockSetType blockSetType = BLOCK_SET_TYPES.get(id);
                            registerPressurePlateCOMPLETE(id, sensitivity, () -> baseBlock, blockSetType);
                            isFullBlock = false;
                        }
                        if (isFullBlock) {
                            registerSans_XP_COMPLETE(id, () -> baseBlock);
                        }
                    }
                }
            }
        }
        if (NON_COLOR_WIP != null) {
            for (String id : NON_COLOR_WIP) {
                Block baseBlock = BLOCK_MAP.get(id);
                if (baseBlock == null && !id.equals("cobbled_repstone") && !id.equals("repstone_bricks")) {
                    throw new IllegalStateException("No base block mapping for WIP part: " + id);
                }
                String[] parts = id.split("_");
                if (parts.length > 3) {
                    throw new IllegalArgumentException("Block ID is too long! Max: 3");
                } else {
                    boolean isFullBlock = true;
                    if (parts.length == 2) {
                        if (id.equals("cobbled_repstone")) {
                            ALL.put(id, COBBLED_REPSTONE);
                            SORTED_ORDER_WIP.add(id);
                            isFullBlock = false;
                        }
                        if (id.equals("repstone_bricks")) {
                            ALL.put(id, REPSTONE_BRICKS);
                            SORTED_ORDER_WIP.add(id);
                            isFullBlock = false;
                        }
                        if (parts[1].equals("slab")) {
                            registerSlabWIP(id, () -> baseBlock);
                            isFullBlock = false;
                        }
                        if (parts[1].equals("wall")) {
                            registerWallWIP(id, () -> baseBlock);
                            isFullBlock = false;
                        }
                        if (parts[1].equals("stairs")) {
                            if (id.equals("repstone_stairs")) {
                                registerStairsWIP(id, () -> baseBlock, REPSTONE);
                                isFullBlock = false;
                            }
                        }
                        if (parts[1].equals("button")) {
                            BlockSetType blockSetType = BLOCK_SET_TYPES.get(id);
                            int ticksToStayPressed = BLOCKS_WITH_INTS.get(id);
                            boolean arrowsCanPress = BLOCKS_WITH_BOOLEANS.get(id);
                            registerButtonWIP(id, () -> baseBlock, blockSetType, ticksToStayPressed, arrowsCanPress);
                            isFullBlock = false;
                        }
                        if (parts[1].equals("pressure")) {
                            PressurePlateBlock.Sensitivity sensitivity = BLOCK_SENSITIVITY_TYPES.get(id);
                            BlockSetType blockSetType = BLOCK_SET_TYPES.get(id);
                            registerPressurePlateWIP(id, sensitivity, () -> baseBlock, blockSetType);
                            isFullBlock = false;
                        }
                        if (parts[1].equals("door")) {
                            BlockSetType blockSetType = BLOCK_SET_TYPES.get(id);
                            registerDoorWIP(id, () -> baseBlock, blockSetType);
                            isFullBlock = false;
                        }
                        if (parts[1].equals("trapdoor")) {
                            BlockSetType blockSetType = BLOCK_SET_TYPES.get(id);
                            registerTrapDoorWIP(id, () -> baseBlock, blockSetType);
                            isFullBlock = false;
                        }
                        if (isFullBlock) {
                            registerSans_XP_WIP(id, () -> baseBlock);
                        }
                    } else if (parts.length == 3) {
                        if (parts[1].equals("slab") || parts[2].equals("slab")) {
                            registerSlabWIP(id, () -> baseBlock);
                            isFullBlock = false;
                        }
                        if (parts[1].equals("wall") || parts[2].equals("wall")) {
                            registerWallWIP(id, () -> baseBlock);
                            isFullBlock = false;
                        }
                        if (parts[1].equals("stairs") || parts[2].equals("stairs")) {
                            if (id.equals("cobbled_repstone_stairs")) {
                                registerStairsWIP(id, () -> baseBlock, COBBLED_REPSTONE);
                                isFullBlock = false;
                            } else {
                                registerStairsWIP(id, () -> baseBlock, REPSTONE_BRICKS);
                                isFullBlock = false;
                            }
                        }
                        if (parts[1].equals("pressure") || parts[2].equals("plate")) {
                            PressurePlateBlock.Sensitivity sensitivity = BLOCK_SENSITIVITY_TYPES.get(id);
                            BlockSetType blockSetType = BLOCK_SET_TYPES.get(id);
                            registerPressurePlateWIP(id, sensitivity, () -> baseBlock, blockSetType);
                            isFullBlock = false;
                        }
                        if (isFullBlock) {
                            registerSans_XP_WIP(id, () -> baseBlock);
                        }
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