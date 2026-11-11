package net.tobias.neo_prism.entity.client.renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.entity.client.ModModelLayers;
import net.tobias.neo_prism.entity.client.models.ShardLizardScoutModel;
import net.tobias.neo_prism.entity.entities.ShardLizardScout;

public class ShardLizardScoutRenderer extends MobRenderer<ShardLizardScout, ShardLizardScoutModel<ShardLizardScout>> {
    private static final ResourceLocation[] BASE = new ResourceLocation[] {
            rl("textures/entity/shard_lizards/scout/red_scout.png"),
            rl("textures/entity/shard_lizards/scout/orange_scout.png"),
            rl("textures/entity/shard_lizards/scout/yellow_scout.png"),
            rl("textures/entity/shard_lizards/scout/green_scout.png"),
            rl("textures/entity/shard_lizards/scout/blue_scout.png"),
            rl("textures/entity/shard_lizards/scout/cyan_scout.png"),
            rl("textures/entity/shard_lizards/scout/purple_scout.png"),
            rl("textures/entity/shard_lizards/scout/pink_scout.png"),
            rl("textures/entity/shard_lizards/scout/white_scout.png")
    };

    public static final ResourceLocation[] EMISSIVE = new ResourceLocation[] {
            rl("textures/entity/shard_lizards/emissive/red_emissive.png"),
            rl("textures/entity/shard_lizards/emissive/orange_emissive.png"),
            rl("textures/entity/shard_lizards/emissive/yellow_emissive.png"),
            rl("textures/entity/shard_lizards/emissive/green_emissive.png"),
            rl("textures/entity/shard_lizards/emissive/blue_emissive.png"),
            rl("textures/entity/shard_lizards/emissive/cyan_emissive.png"),
            rl("textures/entity/shard_lizards/emissive/purple_emissive.png"),
            rl("textures/entity/shard_lizards/emissive/pink_emissive.png"),
            rl("textures/entity/shard_lizards/emissive/white_emissive.png")
    };

    public ShardLizardScoutRenderer(EntityRendererProvider.Context context) {
        super(context, new ShardLizardScoutModel<>(context.bakeLayer(ModModelLayers.SHARD_LIZARD_SCOUT_LAYER)), 1f);
        this.addLayer(new ScoutEmissiveLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ShardLizardScout entity) {
        return BASE[textureIndex(entity)];
    }

    @Override
    public void render(ShardLizardScout entity, float entityYaw, float partialTicks, PoseStack matrixStack,
                       MultiBufferSource buffer, int packedLight) {
        matrixStack.scale(0.5f, 0.5f, 0.5f);

        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    private static ResourceLocation rl(String path) {
        return new ResourceLocation(Neo_Prism.MOD_ID, path);
    }

    public static int textureIndex(ShardLizardScout entity) {
        return Mth.clamp(entity.getVariant(), 0, BASE.length - 1);
    }

    @Override
    protected float getFlipDegrees(ShardLizardScout entity) {
        return entity.isCustomDying() ? 0.0f : super.getFlipDegrees(entity);
    }

    public static class ScoutEmissiveLayer extends RenderLayer<ShardLizardScout, ShardLizardScoutModel<ShardLizardScout>> {
        public ScoutEmissiveLayer(RenderLayerParent<ShardLizardScout, ShardLizardScoutModel<ShardLizardScout>> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ShardLizardScout entity,
                           float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
            VertexConsumer vc = buffer.getBuffer(RenderType.eyes(EMISSIVE[textureIndex(entity)]));
            this.getParentModel().renderToBuffer(poseStack, vc, 15728640, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1, 1, 1, 1);
        }
    }
}