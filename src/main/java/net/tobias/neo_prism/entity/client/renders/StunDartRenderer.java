package net.tobias.neo_prism.entity.client.renders;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.entity.entities.projectiles.StunDart;

@OnlyIn(Dist.CLIENT)
public class StunDartRenderer extends DartRender<StunDart> {
    public static final ResourceLocation STUN_DART_LOCATION = ResourceLocation.fromNamespaceAndPath(Neo_Prism.MOD_ID, "textures/entity/projectiles/stun_dart.png");

    public StunDartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(StunDart entity) {
        return STUN_DART_LOCATION;
    }
}