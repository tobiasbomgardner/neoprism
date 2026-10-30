package net.tobias.neo_prism.entity.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class StunDartModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart dart;

	public StunDartModel(ModelPart root) {
		dart = root.getChild("Dart");
		final ModelPart feathers = dart.getChild("feathers");
		final ModelPart feather1 = feathers.getChild("feather1");
		final ModelPart feather2 = feathers.getChild("feather2");
		final ModelPart feather3 = feathers.getChild("feather3");
		final ModelPart feather4 = feathers.getChild("feather4");
		final ModelPart stick = dart.getChild("stick");
		final ModelPart tip = dart.getChild("tip");
		final ModelPart end_bar = dart.getChild("end_bar");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Dart = partdefinition.addOrReplaceChild("Dart", CubeListBuilder.create(), PartPose.offsetAndRotation(6.9667F, 18.3F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition feathers = Dart.addOrReplaceChild("feathers", CubeListBuilder.create(), PartPose.offset(-0.6667F, -2.0F, 0.0F));

		PartDefinition feather1 = feathers.addOrReplaceChild("feather1", CubeListBuilder.create().texOffs(8, 0).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(0.0F, -1.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, 0.0F));

		PartDefinition feather2 = feathers.addOrReplaceChild("feather2", CubeListBuilder.create().texOffs(8, 8).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(16, 0).addBox(0.0F, -1.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition feather3 = feathers.addOrReplaceChild("feather3", CubeListBuilder.create().texOffs(12, 0).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(4, 16).addBox(0.0F, -1.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition feather4 = feathers.addOrReplaceChild("feather4", CubeListBuilder.create().texOffs(12, 8).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(16, 4).addBox(0.0F, -1.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition stick = Dart.addOrReplaceChild("stick", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -14.0F, -1.0F, 0.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.3333F, 1.0F, 0.0F));

		PartDefinition e_w_r1 = stick.addOrReplaceChild("e_w_r1", CubeListBuilder.create().texOffs(4, 0).addBox(-1.0F, -14.0F, -1.0F, 0.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 1.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition tip = Dart.addOrReplaceChild("tip", CubeListBuilder.create().texOffs(8, 16).addBox(-1.0F, -16.0F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.3333F, 1.0F, 0.0F));

		PartDefinition n_s_r1 = tip.addOrReplaceChild("n_s_r1", CubeListBuilder.create().texOffs(16, 8).addBox(0.0F, -1.0F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -15.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition end_bar = Dart.addOrReplaceChild("end_bar", CubeListBuilder.create().texOffs(8, 19).addBox(-5.7F, -0.01F, -1.0F, 10.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bar_n_s_r1 = end_bar.addOrReplaceChild("bar_n_s_r1", CubeListBuilder.create().texOffs(8, 17).addBox(-5.0F, 0.0F, -0.3F, 10.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		dart.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return dart;
	}
}