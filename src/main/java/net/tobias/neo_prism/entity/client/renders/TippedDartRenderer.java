package net.tobias.neo_prism.entity.client.renders;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.entity.entities.projectiles.Dart;

@OnlyIn(Dist.CLIENT)
public class TippedDartRenderer extends DartRenderer<Dart> {
    public static final ResourceLocation NORMAL_DART_LOCATION = ResourceLocation.fromNamespaceAndPath(Neo_Prism.MOD_ID, "textures/entity/projectiles/dart.png");
    public static final ResourceLocation TIPPED_DART_LOCATION = ResourceLocation.fromNamespaceAndPath(Neo_Prism.MOD_ID, "textures/entity/projectiles/tipped_dart.png");

    public TippedDartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Dart entity) {
        return entity.getColor() > 0 ? TIPPED_DART_LOCATION : NORMAL_DART_LOCATION;
    }
}