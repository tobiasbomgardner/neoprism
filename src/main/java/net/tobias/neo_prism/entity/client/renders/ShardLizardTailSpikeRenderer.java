package net.tobias.neo_prism.entity.client.renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.entity.client.ModModelLayers;
import net.tobias.neo_prism.entity.client.models.ShardLizardTailSpikeModel;
import net.tobias.neo_prism.entity.entities.projectiles.ShardLizardTailSpike;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class ShardLizardTailSpikeRenderer extends EntityRenderer<ShardLizardTailSpike> {
    private static final ResourceLocation[] BASE = new ResourceLocation[] {
            rl("textures/entity/projectiles/tail_spike_paralysis.png"),
            rl("textures/entity/projectiles/tail_spike.png")
    };

    private final ShardLizardTailSpikeModel<ShardLizardTailSpike> model;

    public ShardLizardTailSpikeRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new ShardLizardTailSpikeModel<>(context.bakeLayer(ModModelLayers.SHARD_LIZARD_TAIL_SPIKE_LAYER));
    }

    public void render(ShardLizardTailSpike entity, float entityYaw, float partialTicks, PoseStack matrixStack,
                       MultiBufferSource buffer, int packedLight) {
        matrixStack.pushPose();
        matrixStack.mulPose(Axis.YP.rotationDegrees(-(Mth.lerp(-partialTicks, -entity.yRotO, -entity.getYRot()) - 90.0F)));
        matrixStack.mulPose(Axis.ZP.rotationDegrees(-(Mth.lerp(-partialTicks, -entity.xRotO, -entity.getXRot()))));

        matrixStack.mulPose(Axis.XP.rotationDegrees(-45.0F));
        matrixStack.translate(0.0F, 0.0F, 0.0F);

        for (int j = 0; j < 4; ++j) {
            matrixStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        }

        VertexConsumer vc = buffer.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity)));
        model.renderToBuffer(matrixStack, vc, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStack.popPose();
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ShardLizardTailSpike entity) {
        return BASE[Mth.clamp(entity.getVariant(), 0, BASE.length - 1)];
    }

    private static ResourceLocation rl(String path) {
        return new ResourceLocation(Neo_Prism.MOD_ID, path);
    }
}