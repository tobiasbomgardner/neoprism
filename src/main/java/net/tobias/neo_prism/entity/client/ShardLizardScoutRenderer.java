package net.tobias.neo_prism.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.tobias.neo_prism.Neo_Prism;
import net.tobias.neo_prism.entity.custom.ShardLizardScoutEntity;

public class ShardLizardScoutRenderer extends MobRenderer<ShardLizardScoutEntity, ShardLizardScoutModel<ShardLizardScoutEntity>> {
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
            rl("textures/entity/shard_lizards/scout/red_emissive.png"),
            rl("textures/entity/shard_lizards/scout/orange_emissive.png"),
            rl("textures/entity/shard_lizards/scout/yellow_emissive.png"),
            rl("textures/entity/shard_lizards/scout/green_emissive.png"),
            rl("textures/entity/shard_lizards/scout/blue_emissive.png"),
            rl("textures/entity/shard_lizards/scout/cyan_emissive.png"),
            rl("textures/entity/shard_lizards/scout/purple_emissive.png"),
            rl("textures/entity/shard_lizards/scout/pink_emissive.png"),
            rl("textures/entity/shard_lizards/scout/white_emissive.png")
    };

    public ShardLizardScoutRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ShardLizardScoutModel<>(pContext.bakeLayer(ModModelLayers.SHARD_LIZARD_SCOUT_LAYER)), 1f);
        this.addLayer(new ScoutEmissiveLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ShardLizardScoutEntity entity) {
        return BASE[textureIndex(entity)];
    }

    @Override
    public void render(ShardLizardScoutEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.scale(0.5f, 0.5f, 0.5f);

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    private static ResourceLocation rl(String path) {
        return new ResourceLocation(Neo_Prism.MOD_ID, path);
    }

    public static int textureIndex(ShardLizardScoutEntity e) {
        return Mth.clamp(e.getVariant(), 0, BASE.length - 1);
    }

    @Override
    protected float getFlipDegrees(ShardLizardScoutEntity entity) {
        return entity.isCustomDying() ? 0.0f : super.getFlipDegrees(entity);
    }

    public class ScoutEmissiveLayer extends RenderLayer<ShardLizardScoutEntity, ShardLizardScoutModel<ShardLizardScoutEntity>> {
        public ScoutEmissiveLayer(RenderLayerParent<ShardLizardScoutEntity, ShardLizardScoutModel<ShardLizardScoutEntity>> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ShardLizardScoutEntity entity,
                           float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
            VertexConsumer vc = buffer.getBuffer(RenderType.eyes(EMISSIVE[textureIndex(entity)]));
            this.getParentModel().renderToBuffer(poseStack, vc, 15728640, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1, 1, 1, 1);
        }
    }
}