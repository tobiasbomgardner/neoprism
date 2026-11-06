package net.tobias.neo_prism.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tobias.neo_prism.Neo_Prism;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS;
    public static final RegistryObject<MobEffect> PARALYSIS;

    static {
        EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Neo_Prism.MOD_ID);
        PARALYSIS = EFFECTS.register("paralysis", ParalysisEffect::new);
    }

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}