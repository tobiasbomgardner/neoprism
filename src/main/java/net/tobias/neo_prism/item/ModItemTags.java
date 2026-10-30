package net.tobias.neo_prism.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> DARTS = bind("darts");

    private ModItemTags() {}

    private static TagKey<Item> bind(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(name));
    }

    public static TagKey<Item> create(final ResourceLocation name) {
        return TagKey.create(Registries.ITEM, name);
    }
}