package net.tobias.neo_prism.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.tobias.neo_prism.Neo_Prism;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_RED_TOOL = tag("needs_red_tool");
        public static final TagKey<Block> NEEDS_ORANGE_TOOL = tag("needs_orange_tool");
        public static final TagKey<Block> NEEDS_YELLOW_TOOL = tag("needs_yellow_tool");
        public static final TagKey<Block> NEEDS_GREEN_TOOL = tag("needs_green_tool");
        public static final TagKey<Block> NEEDS_BLUE_TOOL = tag("needs_blue_tool");
        public static final TagKey<Block> NEEDS_CYAN_TOOL = tag("needs_cyan_tool");
        public static final TagKey<Block> NEEDS_PURPLE_TOOL = tag("needs_purple_tool");
        public static final TagKey<Block> NEEDS_PINK_TOOL = tag("needs_pink_tool");
        public static final TagKey<Block> NEEDS_WHITE_TOOL = tag("needs_white_tool");
        public static final TagKey<Block> NEEDS_PRISMATIC_TOOL = tag("needs_prismatic_tool");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(Neo_Prism.MOD_ID, name));
        }
    }

    public static class Items {
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(Neo_Prism.MOD_ID, name));
        }
    }
}