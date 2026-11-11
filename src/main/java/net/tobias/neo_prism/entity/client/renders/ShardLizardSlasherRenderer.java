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
import net.tobias.neo_prism.entity.client.models.ShardLizardChargerModel;
import net.tobias.neo_prism.entity.client.models.ShardLizardSlasherModel;
import net.tobias.neo_prism.entity.entities.ShardLizardCharger;
import net.tobias.neo_prism.entity.entities.ShardLizardSlasher;

public class ShardLizardSlasherRenderer extends MobRenderer<ShardLizardSlasher, ShardLizardSlasherModel<ShardLizardSlasher>> {
    private static final ResourceLocation[] BASE = new ResourceLocation[] {
            rl("textures/entity/shard_lizards/slasher/red_slasher.png"),
            rl("textures/entity/shard_lizards/slasher/orange_slasher.png"),
            rl("textures/entity/shard_lizards/slasher/yellow_slasher.png"),
            rl("textures/entity/shard_lizards/slasher/green_slasher.png"),
            rl("textures/entity/shard_lizards/slasher/blue_slasher.png"),
            rl("textures/entity/shard_lizards/slasher/cyan_slasher.png"),
            rl("textures/entity/shard_lizards/slasher/purple_slasher.png"),
            rl("textures/entity/shard_lizards/slasher/pink_slasher.png"),
            rl("textures/entity/shard_lizards/slasher/white_slasher.png")
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
    public ShardLizardSlasherRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ShardLizardSlasherModel<>(pContext.bakeLayer(ModModelLayers.SHARD_LIZARD_SLASHER_LAYER)), 1f);
        this.addLayer(new ShardLizardSlasherRenderer.SlasherEmissiveLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ShardLizardSlasher entity) {
        return BASE[textureIndex(entity)];
    }

    @Override
    public void render(ShardLizardSlasher pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.scale(0.5f, 0.5f, 0.5f);

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    private static ResourceLocation rl(String path) {
        return new ResourceLocation(Neo_Prism.MOD_ID, path);
    }

    public static int textureIndex(ShardLizardSlasher e) {
        return Mth.clamp(e.getVariant(), 0, BASE.length - 1);
    }

    @Override
    protected float getFlipDegrees(ShardLizardSlasher entity) {
        return entity.isCustomDying() ? 0.0f : super.getFlipDegrees(entity);
    }

    public static class SlasherEmissiveLayer extends RenderLayer<ShardLizardSlasher, ShardLizardSlasherModel<ShardLizardSlasher>> {
        public SlasherEmissiveLayer(RenderLayerParent<ShardLizardSlasher, ShardLizardSlasherModel<ShardLizardSlasher>> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ShardLizardSlasher entity,
                           float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
            VertexConsumer vc = buffer.getBuffer(RenderType.eyes(EMISSIVE[textureIndex(entity)]));
            this.getParentModel().renderToBuffer(poseStack, vc, 15728640, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1, 1, 1, 1);
        }
    }
}