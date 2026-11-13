package net.tobias.neo_prism.entity.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.tobias.neo_prism.entity.entities.projectiles.ShardLizardTailSpike;

public class ShardLizardTailSpikeModel<T extends ShardLizardTailSpike> extends HierarchicalModel<T> {
	private final ModelPart shard_lizard_tail_spike;

	public ShardLizardTailSpikeModel(ModelPart root) {
		shard_lizard_tail_spike = root.getChild("shard_lizard_tail_spike");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition shard_lizard_tail_spike = partdefinition.addOrReplaceChild("shard_lizard_tail_spike", CubeListBuilder.create().texOffs(4, 0).addBox(-0.5F, 2.0676F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.rotation(0.0f, 0.0f, 0.0f));

		PartDefinition spike_r1 = shard_lizard_tail_spike.addOrReplaceChild("spike_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -6.0F, -1.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.8676F, 1.5F, 0.1745F, 0.0F, 0.0F));

		PartDefinition spike_r2 = shard_lizard_tail_spike.addOrReplaceChild("spike_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -6.0F, 0.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.8676F, -1.5F, -0.1745F, 0.0F, 0.0F));

		PartDefinition spike_r3 = shard_lizard_tail_spike.addOrReplaceChild("spike_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -6.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 2.8676F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition spike_r4 = shard_lizard_tail_spike.addOrReplaceChild("spike_r4", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -6.0F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 2.8676F, 0.0F, 0.0F, 0.0F, 0.1745F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		shard_lizard_tail_spike.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return shard_lizard_tail_spike;
	}
}