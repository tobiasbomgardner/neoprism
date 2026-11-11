package net.tobias.neo_prism;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tobias.neo_prism.effects.ModEffects;
import net.tobias.neo_prism.entity.ModEntities;
import net.tobias.neo_prism.entity.client.renders.*;
import net.tobias.neo_prism.item.ModItems;
import net.tobias.neo_prism.block.ModBlocks;
import net.tobias.neo_prism.item.ModSpawnEggs;
import net.tobias.neo_prism.sounds.ModSounds;
import org.slf4j.Logger;

@Mod(Neo_Prism.MOD_ID)
public class Neo_Prism {
    public static final String MOD_ID = "neo_prism";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Neo_Prism(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModSpawnEggs.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus);
        ModEffects.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {}

    private void addCreative(BuildCreativeModeTabContentsEvent event) {}

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.SHARD_LIZARD_SCOUT.get(), ShardLizardScoutRenderer::new);
            EntityRenderers.register(ModEntities.SHARD_LIZARD_CHARGER.get(), ShardLizardChargerRenderer::new);
            EntityRenderers.register(ModEntities.SHARD_LIZARD_SLASHER.get(), ShardLizardSlasherRenderer::new);
            EntityRenderers.register(ModEntities.SHARD_LIZARD_THRASHER.get(), ShardLizardThrasherRenderer::new);
        }
    }
}
