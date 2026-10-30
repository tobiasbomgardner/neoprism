package net.tobias.neo_prism.damagesources;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.tobias.neo_prism.Neo_Prism;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> DART;

    static {
        DART = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Neo_Prism.MOD_ID, "dart"));
    }
}