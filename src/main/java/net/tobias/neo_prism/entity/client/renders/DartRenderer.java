package net.tobias.neo_prism.entity.client.renders;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.entity.entities.projectiles.Dart;

@OnlyIn(Dist.CLIENT)
public class DartRenderer extends DartRender<Dart> {
    public static final ResourceLocation DART_LOCATION = ResourceLocation.fromNamespaceAndPath(Neo_Prism.MOD_ID, "textures/entity/projectiles/dart.png");

    public DartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Dart entity) {
        return DART_LOCATION;
    }
}
