package net.tobias.neo_prism.entity.client.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.StuckInBodyLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tobias.neo_prism.entity.entities.projectiles.Dart;

@OnlyIn(Dist.CLIENT)
public class DartLayer<T extends LivingEntity, M extends PlayerModel<T>> extends StuckInBodyLayer<T, M> {
    private final EntityRenderDispatcher dispatcher;

    public DartLayer(EntityRendererProvider.Context context, LivingEntityRenderer<T, M> renderer) {
        super(renderer);
        this.dispatcher = context.getEntityRenderDispatcher();
    }

    @Override
    protected int numStuck(T entity) {
        return entity.getArrowCount();
    }

    @Override
    protected void renderStuckItem(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Entity entity, float x, float y, float z, float partialTick) {
        float f = Mth.sqrt(x * x + z * z);
        Dart dart = new Dart(entity.level(), entity.getX(), entity.getY(), entity.getZ());
        dart.setYRot((float) (Math.atan2((double) x, (double) z) * (double) (180F / (float) Math.PI)));
        dart.setXRot((float) (Math.atan2((double) y, (double) f) * (double) (180F / (float) Math.PI)));
        dart.yRotO = dart.getYRot();
        dart.xRotO = dart.getXRot();
        this.dispatcher.render(dart, 0.0D, 0.0D, 0.0D, 0.0F, partialTick, poseStack, buffer, packedLight);
    }
}