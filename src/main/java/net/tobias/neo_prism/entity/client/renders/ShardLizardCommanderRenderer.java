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
import net.tobias.neo_prism.entity.client.models.ShardLizardCommanderModel;
import net.tobias.neo_prism.entity.entities.ShardLizardCommander;

public class ShardLizardCommanderRenderer extends MobRenderer<ShardLizardCommander, ShardLizardCommanderModel<ShardLizardCommander>> {
    private static final ResourceLocation[] BASE = new ResourceLocation[] {
            rl("textures/entity/shard_lizards/commander/main/red.png"),
            rl("textures/entity/shard_lizards/commander/main/orange.png"),
            rl("textures/entity/shard_lizards/commander/main/yellow.png"),
            rl("textures/entity/shard_lizards/commander/main/green.png"),
            rl("textures/entity/shard_lizards/commander/main/blue.png"),
            rl("textures/entity/shard_lizards/commander/main/cyan.png"),
            rl("textures/entity/shard_lizards/commander/main/purple.png"),
            rl("textures/entity/shard_lizards/commander/main/pink.png"),
            rl("textures/entity/shard_lizards/commander/main/white.png")
    };
    private static final ResourceLocation[] EMISSIVE = new ResourceLocation[] {
            rl("textures/entity/shard_lizards/commander/emissive/red.png"),
            rl("textures/entity/shard_lizards/commander/emissive/orange.png"),
            rl("textures/entity/shard_lizards/commander/emissive/yellow.png"),
            rl("textures/entity/shard_lizards/commander/emissive/green.png"),
            rl("textures/entity/shard_lizards/commander/emissive/blue.png"),
            rl("textures/entity/shard_lizards/commander/emissive/cyan.png"),
            rl("textures/entity/shard_lizards/commander/emissive/purple.png"),
            rl("textures/entity/shard_lizards/commander/emissive/pink.png"),
            rl("textures/entity/shard_lizards/commander/emissive/white.png")
    };

    public ShardLizardCommanderRenderer(EntityRendererProvider.Context context) {
        super(context, new ShardLizardCommanderModel<>(context.bakeLayer(ModModelLayers.SHARD_LIZARD_COMMANDER_LAYER)), 1f);
        addLayer(new FireSpiterEmissiveLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ShardLizardCommander entity) {
        return BASE[textureIndex(entity)];
    }

    @Override
    public void render(ShardLizardCommander entity, float entityYaw, float partialTicks, PoseStack matrixStack,
                       MultiBufferSource source, int packedLight) {

        super.render(entity, entityYaw, partialTicks, matrixStack, source, packedLight);
    }

    private static ResourceLocation rl(String path) {
        return new ResourceLocation(Neo_Prism.MOD_ID, path);
    }

    public static int textureIndex(ShardLizardCommander entity) {
        return Mth.clamp(entity.getVariant(), 0, BASE.length - 1);
    }

    @Override
    protected float getFlipDegrees(ShardLizardCommander entity) {
        return entity.isCustomDying() ? 0.0f : super.getFlipDegrees(entity);
    }

    public static class FireSpiterEmissiveLayer extends RenderLayer<ShardLizardCommander, ShardLizardCommanderModel<ShardLizardCommander>> {
        public FireSpiterEmissiveLayer(RenderLayerParent<ShardLizardCommander, ShardLizardCommanderModel<ShardLizardCommander>> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ShardLizardCommander entity,
                           float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
            VertexConsumer vc = buffer.getBuffer(RenderType.eyes(EMISSIVE[textureIndex(entity)]));
            getParentModel().renderToBuffer(poseStack, vc, 15728640, LivingEntityRenderer.getOverlayCoords(entity, 0.0f), 1, 1, 1, 1);
        }
    }
}