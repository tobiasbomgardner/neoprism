package net.tobias.neo_prism.entity.client.renders;


import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.entity.entities.projectiles.SpectralDart;

@OnlyIn(Dist.CLIENT)
public class SpectralDartRenderer extends DartRenderer<SpectralDart> {
    public static final ResourceLocation SPECTRAL_DART_LOCATION = ResourceLocation.fromNamespaceAndPath(Neo_Prism.MOD_ID, "textures/entity/projectiles/spectral_dart.png");

    public SpectralDartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(SpectralDart entity) {
        return SPECTRAL_DART_LOCATION;
    }
}