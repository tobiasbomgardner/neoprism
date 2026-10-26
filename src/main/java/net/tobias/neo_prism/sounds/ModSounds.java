package net.tobias.neo_prism.sounds;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tobias.neo_prism.Neo_Prism;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Neo_Prism.MOD_ID);

    public static final RegistryObject<SoundEvent> SHARD_LIZARD_SCOUT_AMBIENT = registerSoundEvents("shard_lizard_scout_ambient");
    public static final RegistryObject<SoundEvent> SHARD_LIZARD_SCOUT_HURT = registerSoundEvents("shard_lizard_scout_hurt");
    public static final RegistryObject<SoundEvent> SHARD_LIZARD_SCOUT_DEATH = registerSoundEvents("shard_lizard_scout_death");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Neo_Prism.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}