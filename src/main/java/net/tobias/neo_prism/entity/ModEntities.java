package net.tobias.neo_prism.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.entity.entities.*;
import net.tobias.neo_prism.entity.entities.projectiles.ShardLizardTailSpike;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Neo_Prism.MOD_ID);

    public static final RegistryObject<EntityType<ShardLizardScout>> SHARD_LIZARD_SCOUT =
            ENTITY_TYPES.register("shard_lizard_scout", () -> EntityType.Builder.of(ShardLizardScout::new, MobCategory.MONSTER)
                    .sized(2.0f, 1.0f).build("shard_lizard_scout"));
    public static final RegistryObject<EntityType<ShardLizardCharger>> SHARD_LIZARD_CHARGER =
            ENTITY_TYPES.register("shard_lizard_charger", () -> EntityType.Builder.of(ShardLizardCharger::new, MobCategory.MONSTER)
                    .sized(2.0f, 1.2f).build("shard_lizard_charger"));
    public static final RegistryObject<EntityType<ShardLizardSlasher>> SHARD_LIZARD_SLASHER =
            ENTITY_TYPES.register("shard_lizard_slasher", () -> EntityType.Builder.of(ShardLizardSlasher::new, MobCategory.MONSTER)
                    .sized(2.0f, 1.2f).build("shard_lizard_slasher"));
    public static final RegistryObject<EntityType<ShardLizardThrasher>> SHARD_LIZARD_THRASHER =
            ENTITY_TYPES.register("shard_lizard_thrasher", () -> EntityType.Builder.of(ShardLizardThrasher::new, MobCategory.MONSTER)
                    .sized(2.0f, 1.2f).build("shard_lizard_thrasher"));
    public static final RegistryObject<EntityType<ShardLizardSpikeThrower>> SHARD_LIZARD_SPIKE_THROWER =
            ENTITY_TYPES.register("shard_lizard_spike_thrower", () -> EntityType.Builder.of(ShardLizardSpikeThrower::new, MobCategory.MONSTER)
                    .sized(2.0f, 1.2f).build("shard_lizard_spike_thrower"));
    public static final RegistryObject<EntityType<ShardLizardTailSpike>> SHARD_LIZARD_TAIL_SPIKE =
            ENTITY_TYPES.register("shard_lizard_tail_spike", () -> EntityType.Builder.<ShardLizardTailSpike>of(ShardLizardTailSpike::new, MobCategory.MISC)
                    .sized(0.35f, 0.35f).build("shard_lizard_tail_spike"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}