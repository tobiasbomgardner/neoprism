package net.tobias.neo_prism.entity.client.renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.entity.client.ModModelLayers;
import net.tobias.neo_prism.entity.client.models.ShardLizardThrasherModel;
import net.tobias.neo_prism.entity.entities.ShardLizardThrasher;

public class ShardLizardThrasherRenderer extends MobRenderer<ShardLizardThrasher, ShardLizardThrasherModel<ShardLizardThrasher>> {
    private static final ResourceLocation[] BASE = new ResourceLocation[] {
            rl("textures/entity/shard_lizards/thrasher/red_thrasher.png"),
            rl("textures/entity/shard_lizards/thrasher/orange_thrasher.png"),
            rl("textures/entity/shard_lizards/thrasher/yellow_thrasher.png"),
            rl("textures/entity/shard_lizards/thrasher/green_thrasher.png"),
            rl("textures/entity/shard_lizards/thrasher/blue_thrasher.png"),
            rl("textures/entity/shard_lizards/thrasher/cyan_thrasher.png"),
            rl("textures/entity/shard_lizards/thrasher/purple_thrasher.png"),
            rl("textures/entity/shard_lizards/thrasher/pink_thrasher.png"),
            rl("textures/entity/shard_lizards/thrasher/white_thrasher.png")
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

    public ShardLizardThrasherRenderer(EntityRendererProvider.Context context) {
        super(context, new ShardLizardThrasherModel<>(context.bakeLayer(ModModelLayers.SHARD_LIZARD_THRASHER_LAYER)), 1f);
        this.addLayer(new ThrasherEmissiveLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ShardLizardThrasher entity) {
        return BASE[textureIndex(entity)];
    }

    @Override
    public void render(ShardLizardThrasher entity, float entityYaw, float partialTicks, PoseStack matrixStack,
                       MultiBufferSource source, int packedLight) {
        matrixStack.scale(0.5f, 0.5f, 0.5f);

        super.render(entity, entityYaw, partialTicks, matrixStack, source, packedLight);
    }

    private static ResourceLocation rl(String path) {
        return new ResourceLocation(Neo_Prism.MOD_ID, path);
    }

    public static int textureIndex(ShardLizardThrasher entity) {
        return Mth.clamp(entity.getVariant(), 0, BASE.length - 1);
    }

    @Override
    protected float getFlipDegrees(ShardLizardThrasher entity) {
        return entity.isCustomDying() ? 0.0f : super.getFlipDegrees(entity);
    }

    public static class ThrasherEmissiveLayer extends RenderLayer<ShardLizardThrasher, ShardLizardThrasherModel<ShardLizardThrasher>> {
        public ThrasherEmissiveLayer(RenderLayerParent<ShardLizardThrasher, ShardLizardThrasherModel<ShardLizardThrasher>> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ShardLizardThrasher entity,
                           float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
            VertexConsumer vc = buffer.getBuffer(RenderType.eyes(EMISSIVE[textureIndex(entity)]));
            this.getParentModel().renderToBuffer(poseStack, vc, 15728640, LivingEntityRenderer.getOverlayCoords(entity, 0.0f), 1, 1, 1, 1);
        }
    }
}