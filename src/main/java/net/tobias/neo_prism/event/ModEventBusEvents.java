package net.tobias.neo_prism.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.entity.ModEntities;
import net.tobias.neo_prism.entity.entities.ShardLizardCharger;
import net.tobias.neo_prism.entity.entities.ShardLizardScout;
import net.tobias.neo_prism.entity.entities.ShardLizardSlasher;

@Mod.EventBusSubscriber(modid = Neo_Prism.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SHARD_LIZARD_SCOUT.get(), ShardLizardScout.createAttributes().build());
        event.put(ModEntities.SHARD_LIZARD_CHARGER.get(), ShardLizardCharger.createAttributes().build());
        event.put(ModEntities.SHARD_LIZARD_SLASHER.get(), ShardLizardSlasher.createAttributes().build());
    }
}