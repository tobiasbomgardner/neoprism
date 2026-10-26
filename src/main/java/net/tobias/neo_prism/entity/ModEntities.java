package net.tobias.neo_prism.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.entity.custom.ShardLizardScoutEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Neo_Prism.MOD_ID);

    public static final RegistryObject<EntityType<? extends ShardLizardScoutEntity>> SHARD_LIZARD_SCOUT =
            ENTITY_TYPES.register("shard_lizard_scout", () -> EntityType.Builder.of(ShardLizardScoutEntity::new, MobCategory.MONSTER)
                    .sized(2.0f, 1.0f).build("shard_lizard_scout"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}