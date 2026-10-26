package net.tobias.neo_prism;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.tobias.neo_prism.block.ModBlocks;
import net.tobias.neo_prism.item.ModItems;
import net.tobias.neo_prism.item.ModSpawnEggs;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Neo_Prism.MOD_ID);

    public static final RegistryObject<CreativeModeTab> NEO_PRISM_COMPLETE = CREATIVE_MODE_TABS.register("neo_prism_complete",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ALL.get("prismatic_gem").get()))
                    .title(Component.translatable("creativetabs.neo_prism_complete"))
                    .displayItems((pParameters, pOutput) -> {
                        ModBlocks.SORTED_ORDER_COMPETE.forEach(k -> pOutput.accept(ModBlocks.get(k).get()));
                        ModItems.SORTED_ORDER_COMPETE.forEach(k -> pOutput.accept(ModItems.get(k).get()));
                        ModSpawnEggs.SORTED_ORDER_COMPETE.forEach(k -> pOutput.accept(ModSpawnEggs.get(k).get()));
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> NEO_PRISM_WIP = CREATIVE_MODE_TABS.register("neo_prism_wip",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ALL.get("prism_lighter").get()))
                    .title(Component.translatable("creativetabs.neo_prism_wip"))
                    .displayItems((pParameters, pOutput) -> {
                        ModBlocks.SORTED_ORDER_WIP.forEach(k -> pOutput.accept(ModBlocks.get(k).get()));
                        ModItems.SORTED_ORDER_WIP.forEach(k -> pOutput.accept(ModItems.get(k).get()));
                        ModSpawnEggs.SORTED_ORDER_WIP.forEach(k -> pOutput.accept(ModSpawnEggs.get(k).get()));
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}