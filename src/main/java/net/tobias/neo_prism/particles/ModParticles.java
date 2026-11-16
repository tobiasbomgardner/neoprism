package net.tobias.neo_prism.particles;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tobias.neo_prism.Neo_Prism;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES;
    public static final RegistryObject<SimpleParticleType> FLAME;

    static {
        PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Neo_Prism.MOD_ID);
        FLAME = PARTICLES.register("flame", () -> new SimpleParticleType(true));
    }

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }
}