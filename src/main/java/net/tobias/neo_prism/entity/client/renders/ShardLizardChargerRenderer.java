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
import net.tobias.neo_prism.entity.entities.ShardLizardCharger;

public class ShardLizardChargerRenderer extends MobRenderer<ShardLizardCharger, ShardLizardChargerModel<ShardLizardCharger>> {
    private static final ResourceLocation[] BASE = new ResourceLocation[] {
            rl("textures/entity/shard_lizards/charger/red_charger.png"),
            rl("textures/entity/shard_lizards/charger/orange_charger.png"),
            rl("textures/entity/shard_lizards/charger/yellow_charger.png"),
            rl("textures/entity/shard_lizards/charger/green_charger.png"),
            rl("textures/entity/shard_lizards/charger/blue_charger.png"),
            rl("textures/entity/shard_lizards/charger/cyan_charger.png"),
            rl("textures/entity/shard_lizards/charger/purple_charger.png"),
            rl("textures/entity/shard_lizards/charger/pink_charger.png"),
            rl("textures/entity/shard_lizards/charger/white_charger.png")
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
    public ShardLizardChargerRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ShardLizardChargerModel<>(pContext.bakeLayer(ModModelLayers.SHARD_LIZARD_CHARGER_LAYER)), 1f);
        this.addLayer(new ShardLizardChargerRenderer.ChargerEmissiveLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ShardLizardCharger entity) {
        return BASE[textureIndex(entity)];
    }

    @Override
    public void render(ShardLizardCharger pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.scale(0.5f, 0.5f, 0.5f);

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    private static ResourceLocation rl(String path) {
        return new ResourceLocation(Neo_Prism.MOD_ID, path);
    }

    public static int textureIndex(ShardLizardCharger e) {
        return Mth.clamp(e.getVariant(), 0, BASE.length - 1);
    }

    @Override
    protected float getFlipDegrees(ShardLizardCharger entity) {
        return entity.isCustomDying() ? 0.0f : super.getFlipDegrees(entity);
    }

    public class ChargerEmissiveLayer extends RenderLayer<ShardLizardCharger, ShardLizardChargerModel<ShardLizardCharger>> {
        public ChargerEmissiveLayer(RenderLayerParent<ShardLizardCharger, ShardLizardChargerModel<ShardLizardCharger>> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ShardLizardCharger entity,
                           float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
            VertexConsumer vc = buffer.getBuffer(RenderType.eyes(EMISSIVE[textureIndex(entity)]));
            this.getParentModel().renderToBuffer(poseStack, vc, 15728640, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1, 1, 1, 1);
        }
    }
}