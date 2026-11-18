package net.tobias.neo_prism.entity.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.tobias.neo_prism.entity.animations.ShardLizardCommanderAnimations;
import net.tobias.neo_prism.entity.entities.ShardLizardCommander;

public class ShardLizardCommanderModel<T extends ShardLizardCommander> extends HierarchicalModel<T> {
	private final ModelPart shardLizardCommander;
	private final ModelPart head;

	public ShardLizardCommanderModel(ModelPart root) {
		shardLizardCommander = root.getChild("shardLizardCommander");
		final ModelPart whole_body = shardLizardCommander.getChild("whole_body");
		final ModelPart legs = whole_body.getChild("legs");
		final ModelPart left_leg = legs.getChild("left_leg");
		final ModelPart right_leg = legs.getChild("right_leg");
		final ModelPart body = whole_body.getChild("body");
		final ModelPart bottom_body = body.getChild("bottom_body");
		final ModelPart bottom_body_shards = bottom_body.getChild("bottom_body_shards");
		final ModelPart top_body = bottom_body.getChild("top_body");
		final ModelPart top_body_shards = top_body.getChild("top_body_shards");
		final ModelPart arms = top_body.getChild("arms");
		final ModelPart left_arm = arms.getChild("left_arm");
		final ModelPart top_left_arm = left_arm.getChild("top_left_arm");
		final ModelPart bottom_left_arm = top_left_arm.getChild("bottom_left_arm");
		final ModelPart crystal_left = top_left_arm.getChild("crystal_left");
		final ModelPart right_arm = arms.getChild("right_arm");
		final ModelPart top_right_arm = right_arm.getChild("top_right_arm");
		final ModelPart bottom_right_arm = top_right_arm.getChild("bottom_right_arm");
		final ModelPart flap = bottom_right_arm.getChild("flap");
		final ModelPart broken_prismatic_eye = bottom_right_arm.getChild("broken_prismatic_eye");
		final ModelPart crystal_right = top_right_arm.getChild("crystal_right");
		head = top_body.getChild("head");
		final ModelPart jaw = head.getChild("jaw");
		final ModelPart head_shards = head.getChild("head_shards");
		final ModelPart tail = bottom_body.getChild("tail");
		final ModelPart p1 = tail.getChild("p1");
		final ModelPart p2 = p1.getChild("p2");
		final ModelPart p3 = p2.getChild("p3");
		final ModelPart p4 = p3.getChild("p4");
		final ModelPart p5 = p4.getChild("p5");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition shardLizardCommander = partdefinition.addOrReplaceChild("shardLizardCommander", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition whole_body = shardLizardCommander.addOrReplaceChild("whole_body", CubeListBuilder.create(), PartPose.offset(0.0F, -22.7958F, -0.3792F));

		PartDefinition legs = whole_body.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 22.7958F, 0.3792F));

		PartDefinition left_leg = legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(72, 53).addBox(-3.0F, -2.0F, -2.9F, 6.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -14.0F, -0.1F));

		PartDefinition right_leg = legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(72, 53).addBox(-3.0F, -2.0F, -2.9F, 6.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -14.0F, -0.1F));

		PartDefinition body = whole_body.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 9.6042F, 0.8208F, 0.3491F, 0.0F, 0.0F));

		PartDefinition bottom_body = body.addOrReplaceChild("bottom_body", CubeListBuilder.create().texOffs(0, 42).addBox(-8.0F, -11.9283F, -4.242F, 16.0F, 12.0F, 8.0F, new CubeDeformation(-0.02F)), PartPose.offsetAndRotation(0.0F, -0.3648F, 0.0624F, -0.1745F, 0.0F, 0.0F));

		PartDefinition bottom_body_shards = bottom_body.addOrReplaceChild("bottom_body_shards", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 12.0F));

		PartDefinition shard_r1 = bottom_body_shards.addOrReplaceChild("shard_r1", CubeListBuilder.create().texOffs(84, 93).addBox(-1.0F, -9.0F, -3.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -9.8833F, -9.2167F, -1.3963F, 0.0F, 0.0F));

		PartDefinition top_body = bottom_body.addOrReplaceChild("top_body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -10.7F, -0.2F, 0.1745F, 0.0F, 0.0F));

		PartDefinition top_body_r1 = top_body.addOrReplaceChild("top_body_r1", CubeListBuilder.create().texOffs(0, 21).addBox(-7.0F, -13.0F, -1.0417F, 16.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.01F, 0.394F, -2.7992F, 0.1309F, 0.0F, 0.0F));

		PartDefinition top_body_shards = top_body.addOrReplaceChild("top_body_shards", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition shard_r2 = top_body_shards.addOrReplaceChild("shard_r2", CubeListBuilder.create().texOffs(68, 93).addBox(-1.0F, -9.0F, -3.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -3.7833F, 3.6833F, -1.3963F, 0.0F, 0.0F));

		PartDefinition shard_r3 = top_body_shards.addOrReplaceChild("shard_r3", CubeListBuilder.create().texOffs(52, 93).addBox(-1.0F, -9.0F, -3.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -10.7833F, 2.2833F, -1.3963F, 0.0F, 0.0F));

		PartDefinition arms = top_body.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0.0F, -10.5083F, -1.2417F));

		PartDefinition left_arm = arms.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition top_left_arm = left_arm.addOrReplaceChild("top_left_arm", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition top_left_arm_r1 = top_left_arm.addOrReplaceChild("top_left_arm_r1", CubeListBuilder.create().texOffs(24, 62).addBox(-6.0F, -2.0F, -3.0F, 6.0F, 14.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0503F, -0.1091F, 0.1273F, -0.3491F, 0.0F, 0.0F));

		PartDefinition bottom_left_arm = top_left_arm.addOrReplaceChild("bottom_left_arm", CubeListBuilder.create(), PartPose.offset(-2.9F, 11.1F, -4.0622F));

		PartDefinition bottom_left_arm_r1 = bottom_left_arm.addOrReplaceChild("bottom_left_arm_r1", CubeListBuilder.create().texOffs(72, 53).addBox(-3.11F, 0.7859F, -3.9915F, 5.0F, 14.0F, 6.0F, new CubeDeformation(-0.02F))
		.texOffs(72, 53).addBox(1.85F, 0.7859F, -3.9915F, 1.04F, 14.0F, 6.0F, new CubeDeformation(-0.02F)), PartPose.offsetAndRotation(-0.0403F, -2.434F, 1.0491F, 0.1745F, 0.0F, 0.0F));

		PartDefinition crystal_left = top_left_arm.addOrReplaceChild("crystal_left", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition crystal_bottom_r1 = crystal_left.addOrReplaceChild("crystal_bottom_r1", CubeListBuilder.create().texOffs(0, 82).addBox(-5.0F, -2.0F, -1.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-1.0F, -1.6F, -1.4F, -0.3491F, 0.0F, 0.0F));

		PartDefinition back_right_r1 = crystal_left.addOrReplaceChild("back_right_r1", CubeListBuilder.create().texOffs(102, 35).addBox(-1.0F, -5.0263F, -0.8812F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.1F, -2.2F, 3.0F, -0.5948F, 0.1008F, 0.2108F));

		PartDefinition back_left_r1 = crystal_left.addOrReplaceChild("back_left_r1", CubeListBuilder.create().texOffs(96, 55).addBox(-1.0F, -5.3082F, -0.9838F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.8F, -1.9F, 2.9F, -0.5704F, -0.1929F, -0.2403F));

		PartDefinition front_right_r1 = crystal_left.addOrReplaceChild("front_right_r1", CubeListBuilder.create().texOffs(94, 28).addBox(-1.0F, -5.2142F, -0.9496F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.1F, -3.0F, -0.6F, -0.0713F, 0.1972F, 0.1733F));

		PartDefinition back_mid_r1 = crystal_left.addOrReplaceChild("back_mid_r1", CubeListBuilder.create().texOffs(96, 62).addBox(-1.0F, -5.5901F, -1.0864F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -1.7F, 2.4F, -0.7418F, 0.0F, 0.0F));

		PartDefinition front_mid_r1 = crystal_left.addOrReplaceChild("front_mid_r1", CubeListBuilder.create().texOffs(96, 48).addBox(-1.0F, -5.2142F, -0.9496F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -3.0F, -0.6F, 0.0436F, 0.0F, 0.0F));

		PartDefinition mid_right_r1 = crystal_left.addOrReplaceChild("mid_right_r1", CubeListBuilder.create().texOffs(96, 76).addBox(-1.0F, -5.3082F, -0.9838F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.2F, -2.6F, 1.2F, -0.2915F, 0.1183F, 0.3097F));

		PartDefinition mid_mid_r1 = crystal_left.addOrReplaceChild("mid_mid_r1", CubeListBuilder.create().texOffs(96, 83).addBox(-0.9F, -5.4021F, -1.018F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.1F, -2.5F, 1.2F, -0.3491F, 0.0F, 0.0F));

		PartDefinition mid_left_r1 = crystal_left.addOrReplaceChild("mid_left_r1", CubeListBuilder.create().texOffs(96, 69).addBox(-1.2F, -5.3424F, -0.8898F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, -2.6F, 1.1F, -0.2935F, -0.1186F, -0.4396F));

		PartDefinition front_left_r1 = crystal_left.addOrReplaceChild("front_left_r1", CubeListBuilder.create().texOffs(94, 21).addBox(-1.0F, -5.3082F, -0.9838F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.8F, -2.9F, -0.7F, -0.047F, 0.0167F, -0.3051F));

		PartDefinition right_arm = arms.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(6.0F, 1.0F, -0.3F, 0.0F, 0.0F, -0.3054F));

		PartDefinition top_right_arm = right_arm.addOrReplaceChild("top_right_arm", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition top_right_arm_r1 = top_right_arm.addOrReplaceChild("top_right_arm_r1", CubeListBuilder.create().texOffs(48, 70).addBox(0.0F, -2.0F, -3.0F, 6.0F, 17.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0265F, -0.1172F, 0.1273F, -0.3491F, 0.0F, 0.0F));

		PartDefinition bottom_right_arm = top_right_arm.addOrReplaceChild("bottom_right_arm", CubeListBuilder.create(), PartPose.offset(3.0F, 11.8568F, -4.3069F));

		PartDefinition bottom_right_arm_r1 = bottom_right_arm.addOrReplaceChild("bottom_right_arm_r1", CubeListBuilder.create().texOffs(24, 106).addBox(-4.1557F, -0.1674F, -3.9435F, 8.0F, 14.0F, 8.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.1822F, -0.557F, 0.2112F, -0.3491F, 0.0F, 0.0F));

		PartDefinition flap = bottom_right_arm.addOrReplaceChild("flap", CubeListBuilder.create(), PartPose.offset(-3.6F, 12.2F, -3.8F));

		PartDefinition flap_r1 = flap.addOrReplaceChild("flap_r1", CubeListBuilder.create().texOffs(24, 119).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(3.61F, 0.1946F, -0.635F, -0.3491F, 0.0F, 0.0F));

		PartDefinition broken_prismatic_eye = bottom_right_arm.addOrReplaceChild("broken_prismatic_eye", CubeListBuilder.create(), PartPose.offset(0.0265F, 6.7591F, -2.3432F));

		PartDefinition broken_prismatic_eye_r1 = broken_prismatic_eye.addOrReplaceChild("broken_prismatic_eye_r1", CubeListBuilder.create().texOffs(58, 109).addBox(-3.5F, -5.5F, -4.5F, 8.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.171F, 0.4698F, -0.3491F, 0.0F, 0.0F));

		PartDefinition crystal_right = top_right_arm.addOrReplaceChild("crystal_right", CubeListBuilder.create(), PartPose.offset(6.0F, 0.0F, -0.1F));

		PartDefinition crystal_bottom_r2 = crystal_right.addOrReplaceChild("crystal_bottom_r2", CubeListBuilder.create().texOffs(24, 82).addBox(-5.0F, -2.0F, -1.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-1.0F, -1.6F, -1.4F, -0.3491F, 0.0F, 0.0F));

		PartDefinition back_right_r2 = crystal_right.addOrReplaceChild("back_right_r2", CubeListBuilder.create().texOffs(104, 42).addBox(-1.0F, -5.0263F, -0.8812F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -2.2F, 3.0F, -0.5948F, 0.1008F, 0.2108F));

		PartDefinition back_left_r2 = crystal_right.addOrReplaceChild("back_left_r2", CubeListBuilder.create().texOffs(102, 28).addBox(-1.0F, -5.3082F, -0.9838F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.8F, -1.9F, 2.9F, -0.5704F, -0.1929F, -0.2403F));

		PartDefinition front_right_r2 = crystal_right.addOrReplaceChild("front_right_r2", CubeListBuilder.create().texOffs(24, 102).addBox(-1.0F, -5.2142F, -0.9496F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.1F, -3.0F, -0.6F, -0.0713F, 0.1972F, 0.1733F));

		PartDefinition back_mid_r2 = crystal_right.addOrReplaceChild("back_mid_r2", CubeListBuilder.create().texOffs(102, 21).addBox(-1.0F, -5.5901F, -1.0864F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -1.7F, 2.6F, -0.7418F, 0.0F, 0.0F));

		PartDefinition front_mid_r2 = crystal_right.addOrReplaceChild("front_mid_r2", CubeListBuilder.create().texOffs(16, 102).addBox(-1.0F, -5.2142F, -0.9496F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -3.0F, -0.6F, 0.0436F, 0.0F, 0.0F));

		PartDefinition mid_right_r2 = crystal_right.addOrReplaceChild("mid_right_r2", CubeListBuilder.create().texOffs(100, 97).addBox(-1.0F, -5.3082F, -0.9838F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.1F, -2.6F, 1.2F, -0.2915F, 0.1183F, 0.3097F));

		PartDefinition mid_mid_r2 = crystal_right.addOrReplaceChild("mid_mid_r2", CubeListBuilder.create().texOffs(100, 90).addBox(-0.9F, -5.4021F, -1.018F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.1F, -2.5F, 1.2F, -0.3491F, 0.0F, 0.0F));

		PartDefinition mid_left_r2 = crystal_right.addOrReplaceChild("mid_left_r2", CubeListBuilder.create().texOffs(8, 99).addBox(-1.2F, -5.3424F, -0.8898F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, -2.6F, 1.1F, -0.2935F, -0.1186F, -0.4396F));

		PartDefinition front_left_r2 = crystal_right.addOrReplaceChild("front_left_r2", CubeListBuilder.create().texOffs(0, 99).addBox(-1.0F, -5.3082F, -0.9838F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.7F, -2.9F, -0.7F, -0.047F, 0.0167F, -0.3051F));

		PartDefinition head = top_body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.01F, -8.2811F, -12.0463F, 10.0F, 6.0F, 15.0F, new CubeDeformation(0.01F))
		.texOffs(80, 14).addBox(-5.01F, -2.2811F, -1.0463F, 10.0F, 3.0F, 4.0F, new CubeDeformation(0.01F))
		.texOffs(50, 15).addBox(-4.92F, -2.2811F, -11.0463F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(54, 15).addBox(4.9F, -2.2811F, -11.0463F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.7833F, -2.7167F, -0.3491F, 0.0F, 0.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.125F, -1.025F, 0.8727F, 0.0F, 0.0F));

		PartDefinition jaw_r1 = jaw.addOrReplaceChild("jaw_r1", CubeListBuilder.create().texOffs(48, 21).addBox(-5.0F, -2.0F, -11.1F, 10.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.01F, -0.04F, 0.1701F, -0.8727F, 0.0F, 0.0F));

		PartDefinition head_shards = head.addOrReplaceChild("head_shards", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition shard_r4 = head_shards.addOrReplaceChild("shard_r4", CubeListBuilder.create().texOffs(36, 93).addBox(-1.0F, -9.0F, -3.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -6.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition shard_r5 = head_shards.addOrReplaceChild("shard_r5", CubeListBuilder.create().texOffs(20, 89).addBox(-1.0F, -9.0F, -3.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -6.0F, -8.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition tail = bottom_body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -3.6F, 3.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition p1 = tail.addOrReplaceChild("p1", CubeListBuilder.create().texOffs(48, 37).addBox(-4.0F, -4.5083F, -1.0417F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.48F, 0.0F, 0.0F));

		PartDefinition p2 = p1.addOrReplaceChild("p2", CubeListBuilder.create().texOffs(50, 0).addBox(-3.5F, -4.0F, -2.0F, 7.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition p3 = p2.addOrReplaceChild("p3", CubeListBuilder.create().texOffs(80, 0).addBox(-3.1F, -3.3F, -2.0F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1F, -0.2F, 6.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition p4 = p3.addOrReplaceChild("p4", CubeListBuilder.create().texOffs(80, 37).addBox(-2.5F, -2.5F, -2.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, -0.3F, 6.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition p5 = p4.addOrReplaceChild("p5", CubeListBuilder.create().texOffs(0, 89).addBox(-2.2F, -2.0F, -2.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, 0.0F, 4.0F, -0.2618F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		root().getAllParts().forEach(ModelPart::resetPose);

		if (entity.isCustomDying()) {
			animate(entity.deathAnimationState, ShardLizardCommanderAnimations.DEATH, ageInTicks, 0.5f);
			return;
		}

		if (entity.isAttacking()) {
			switch (entity.getAttackType()) {
				case ShardLizardCommander.STUN_ROAR:
					animate(entity.stunRoarAnimationState, ShardLizardCommanderAnimations.STUN_ROAR, ageInTicks, 1.0f);
					break;
				case ShardLizardCommander.SPIT_FIRE:
					animate(entity.spitFireAnimationState, ShardLizardCommanderAnimations.SPIT_FIRE, ageInTicks, 1.0f);
					break;
				case ShardLizardCommander.STUN_SLAM:
					animate(entity.stunSlamAnimationState, ShardLizardCommanderAnimations.STUN_SLAM, ageInTicks, 1.0f);
					break;
				case ShardLizardCommander.WHIP:
					animate(entity.whipAnimationState, ShardLizardCommanderAnimations.WHIP, ageInTicks, 1.0f);
					break;
				case ShardLizardCommander.HEAVY_SLASH:
					animate(entity.heavySlashAnimationState, ShardLizardCommanderAnimations.HEAVY_SLASH, ageInTicks, 1.0f);
					break;
				case ShardLizardCommander.DOUBLE_WHIP:
					animate(entity.doubleWhipAnimationState, ShardLizardCommanderAnimations.DOUBLE_WHIP, ageInTicks, 1.0f);
					break;
				case ShardLizardCommander.HEAVY_SLASHES:
					animate(entity.heavySlashesAnimationState, ShardLizardCommanderAnimations.HEAVY_SLASHES, ageInTicks, 1.0f);
					break;
				case ShardLizardCommander.HEAVY_SLASHES_W_SLAM:
					animate(entity.heavySlashesWSlamAnimationState, ShardLizardCommanderAnimations.HEAVY_SLASHES_W_SLAM, ageInTicks, 1.0f);
					break;
				case ShardLizardCommander.THREE_STRIKES_COMBO:
					animate(entity.threeStrikesComboAnimationState, ShardLizardCommanderAnimations.THREE_STRIKES_COMBO, ageInTicks, 1.0f);
					break;
				case ShardLizardCommander.SUMMON:
					animate(entity.summonAnimationState, ShardLizardCommanderAnimations.SUMMON, ageInTicks, 1.0f);
					break;
				case ShardLizardCommander.DOUBLE_WHIP_HEAVY_SLASHES_W_SLAM:
					animate(entity.doubleWhipHeavySlashesWSlamAnimationState, ShardLizardCommanderAnimations.DOUBLE_WHIP_HEAVY_SLASHES_W_SLAM, ageInTicks, 1.0f);
					break;
				case ShardLizardCommander.VENGEANCE:
					animate(entity.vengeanceAnimationState, ShardLizardCommanderAnimations.VENGEANCE, ageInTicks, 1.0f);
					break;
				case ShardLizardCommander.BITE:
				default:
					animate(entity.biteAnimationState, ShardLizardCommanderAnimations.BITE, ageInTicks, 1.0f);
					break;
			}
			return;
		}

		applyHeadRotation(netHeadYaw, headPitch, ageInTicks);
		boolean isMoving = limbSwingAmount > 0.01f || entity.getDeltaMovement().horizontalDistanceSqr() > 0.0001d;
		if (isMoving) {
			animateWalk(ShardLizardCommanderAnimations.WALK, limbSwing, limbSwingAmount, 9f, 2.5f);
			entity.idleAnimationState.stop();
			return;
		}

		animate(entity.idleAnimationState, ShardLizardCommanderAnimations.IDLE, ageInTicks, 1.0f);
	}

	private void applyHeadRotation(float netheadYaw, float headPitch, float ageInTicks) {
		netheadYaw = Mth.clamp(netheadYaw, -30.0f, 30.0f);
		headPitch = Mth.clamp(headPitch, -25.0f, 45.0f);
		head.yRot = netheadYaw * ((float) Math.PI / 180f);
		head.xRot = headPitch * ((float) Math.PI / 180f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		shardLizardCommander.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return shardLizardCommander;
	}
}