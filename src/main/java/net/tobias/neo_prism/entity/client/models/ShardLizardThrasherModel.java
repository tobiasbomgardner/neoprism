package net.tobias.neo_prism.entity.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.tobias.neo_prism.entity.animations.ShardLizardThrasherAnimations;
import net.tobias.neo_prism.entity.entities.ShardLizardThrasher;

public class ShardLizardThrasherModel<T extends ShardLizardThrasher> extends HierarchicalModel<T> {
	private final ModelPart shard_lizard_thrasher;
	private final ModelPart head;

	public ShardLizardThrasherModel(ModelPart root) {
		shard_lizard_thrasher = root.getChild("shard_lizard_thrasher");
		final ModelPart body = shard_lizard_thrasher.getChild("body");
		final ModelPart all_but_legs = body.getChild("all_but_legs");
		final ModelPart body_neck = all_but_legs.getChild("body_neck");
		final ModelPart actual_body = body_neck.getChild("actual_body");
		final ModelPart shards = actual_body.getChild("shards");
		final ModelPart neck = body_neck.getChild("neck");
		head = neck.getChild("head");
		final ModelPart jaw = head.getChild("jaw");
		final ModelPart tail = all_but_legs.getChild("tail");
		final ModelPart p1 = tail.getChild("p1");
		final ModelPart p2 = p1.getChild("p2");
		final ModelPart p3 = p2.getChild("p3");
		final ModelPart p4 = p3.getChild("p4");
		final ModelPart p5 = p4.getChild("p5");
		final ModelPart p6 = p5.getChild("p6");
		final ModelPart p7 = p6.getChild("p7");
		final ModelPart p8 = p7.getChild("p8");
		final ModelPart p9 = p8.getChild("p9");
		final ModelPart back_left_leg = body.getChild("back_left_leg");
		final ModelPart bll_start = back_left_leg.getChild("bll_start");
		final ModelPart bll_mid = bll_start.getChild("bll_mid");
		final ModelPart bll_end = bll_mid.getChild("bll_end");
		final ModelPart bll_foot = bll_end.getChild("bll_foot");
		final ModelPart bll_toe1 = bll_foot.getChild("bll_toe1");
		final ModelPart back4 = bll_toe1.getChild("back4");
		final ModelPart front4 = back4.getChild("front4");
		final ModelPart bll_toe2 = bll_foot.getChild("bll_toe2");
		final ModelPart bll_toe3 = bll_foot.getChild("bll_toe3");
		final ModelPart bll_toe4 = bll_foot.getChild("bll_toe4");
		final ModelPart bll_toe5 = bll_foot.getChild("bll_toe5");
		final ModelPart back_right_leg = body.getChild("back_right_leg");
		final ModelPart brl_start = back_right_leg.getChild("brl_start");
		final ModelPart brl_mid = brl_start.getChild("brl_mid");
		final ModelPart brl_end = brl_mid.getChild("brl_end");
		final ModelPart brl_foot = brl_end.getChild("brl_foot");
		final ModelPart brl_toe1 = brl_foot.getChild("brl_toe1");
		final ModelPart back3 = brl_toe1.getChild("back3");
		final ModelPart front3 = back3.getChild("front3");
		final ModelPart brl_toe2 = brl_foot.getChild("brl_toe2");
		final ModelPart brl_toe3 = brl_foot.getChild("brl_toe3");
		final ModelPart brl_toe4 = brl_foot.getChild("brl_toe4");
		final ModelPart brl_toe5 = brl_foot.getChild("brl_toe5");
		final ModelPart front_left_leg = body.getChild("front_left_leg");
		final ModelPart fll_start = front_left_leg.getChild("fll_start");
		final ModelPart fll_mid = fll_start.getChild("fll_mid");
		final ModelPart fll_end = fll_mid.getChild("fll_end");
		final ModelPart fll_foot = fll_end.getChild("fll_foot");
		final ModelPart fll_toe1 = fll_foot.getChild("fll_toe1");
		final ModelPart back2 = fll_toe1.getChild("back2");
		final ModelPart front2 = back2.getChild("front2");
		final ModelPart fll_toe2 = fll_foot.getChild("fll_toe2");
		final ModelPart fll_toe3 = fll_foot.getChild("fll_toe3");
		final ModelPart fll_toe4 = fll_foot.getChild("fll_toe4");
		final ModelPart fll_toe5 = fll_foot.getChild("fll_toe5");
		final ModelPart front_right_leg = body.getChild("front_right_leg");
		final ModelPart frl_start = front_right_leg.getChild("frl_start");
		final ModelPart frl_mid = frl_start.getChild("frl_mid");
		final ModelPart frl_end = frl_mid.getChild("frl_end");
		final ModelPart frl_foot = frl_end.getChild("frl_foot");
		final ModelPart frl_toe1 = frl_foot.getChild("frl_toe1");
		final ModelPart back = frl_toe1.getChild("back");
		final ModelPart front = back.getChild("front");
		final ModelPart frl_toe2 = frl_foot.getChild("frl_toe2");
		final ModelPart frl_toe3 = frl_foot.getChild("frl_toe3");
		final ModelPart frl_toe4 = frl_foot.getChild("frl_toe4");
		final ModelPart frl_toe5 = frl_foot.getChild("frl_toe5");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition shard_lizard_thrasher = partdefinition.addOrReplaceChild("shard_lizard_thrasher", CubeListBuilder.create(), PartPose.offset(0.0F, 24.1F, 0.0F));

		PartDefinition body = shard_lizard_thrasher.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition all_but_legs = body.addOrReplaceChild("all_but_legs", CubeListBuilder.create(), PartPose.offset(0.0F, -6.8F, 18.5F));

		PartDefinition body_neck = all_but_legs.addOrReplaceChild("body_neck", CubeListBuilder.create(), PartPose.offset(0.7F, -9.0F, 0.9F));

		PartDefinition actual_body = body_neck.addOrReplaceChild("actual_body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.6F, -5.0F, -43.6F, 10.0F, 10.0F, 43.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1F, -0.2F, 0.2F));

		PartDefinition shards = actual_body.addOrReplaceChild("shards", CubeListBuilder.create(), PartPose.offset(-0.6F, 0.2F, -19.6F));

		PartDefinition shard_r1 = shards.addOrReplaceChild("shard_r1", CubeListBuilder.create().texOffs(40, 111).addBox(0.0F, -6.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -2.2F, -23.0F, -0.7418F, 0.0F, 0.0F));

		PartDefinition shard_r2 = shards.addOrReplaceChild("shard_r2", CubeListBuilder.create().texOffs(32, 111).addBox(0.0F, -6.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -2.2F, 14.0F, -0.7418F, 0.0F, 0.0F));

		PartDefinition shard_r3 = shards.addOrReplaceChild("shard_r3", CubeListBuilder.create().texOffs(24, 111).addBox(0.0F, -6.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -2.2F, -7.0F, -0.7418F, 0.0F, 0.0F));

		PartDefinition shard_r4 = shards.addOrReplaceChild("shard_r4", CubeListBuilder.create().texOffs(16, 111).addBox(0.0F, -6.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -2.2F, 10.0F, -0.7418F, 0.0F, 0.0F));

		PartDefinition shard_r5 = shards.addOrReplaceChild("shard_r5", CubeListBuilder.create().texOffs(8, 111).addBox(0.0F, -6.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -2.2F, -18.0F, -0.7418F, 0.0F, 0.0F));

		PartDefinition shard_r6 = shards.addOrReplaceChild("shard_r6", CubeListBuilder.create().texOffs(0, 111).addBox(0.0F, -6.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -2.2F, 0.0F, -0.7418F, 0.0F, 0.0F));

		PartDefinition neck = body_neck.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(80, 53).addBox(-3.9F, -4.0F, -9.0F, 8.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.2F, -0.2F, -38.4F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 53).addBox(-3.0F, -3.0F, -14.0F, 6.0F, 4.0F, 14.0F, new CubeDeformation(0.001F))
		.texOffs(104, 105).addBox(-3.0F, 1.0F, -6.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.002F)), PartPose.offset(0.1F, 0.0F, -7.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(92, 94).addBox(-3.0F, -1.0F, -9.8F, 6.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -4.2F));

		PartDefinition tail = all_but_legs.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(1.0F, -1.7F, -0.1F));

		PartDefinition p1 = tail.addOrReplaceChild("p1", CubeListBuilder.create().texOffs(80, 70).addBox(-4.5F, -5.0F, 0.6F, 9.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, -1.6F, -0.2618F, 0.0F, 0.0F));

		PartDefinition p2 = p1.addOrReplaceChild("p2", CubeListBuilder.create().texOffs(0, 83).addBox(-4.0F, -4.5F, 0.6F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 6.0F, 0.0524F, 0.0F, 0.0F));

		PartDefinition p3 = p2.addOrReplaceChild("p3", CubeListBuilder.create().texOffs(32, 98).addBox(-3.5F, -4.0F, 0.6F, 7.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 6.0F, 0.0698F, 0.0F, 0.0F));

		PartDefinition p4 = p3.addOrReplaceChild("p4", CubeListBuilder.create().texOffs(58, 103).addBox(-3.0F, -3.5F, 0.6F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.8F, 0.0873F, 0.0F, 0.0F));

		PartDefinition p5 = p4.addOrReplaceChild("p5", CubeListBuilder.create().texOffs(82, 105).addBox(-2.5F, -3.0F, 0.6F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.7F, -0.0524F, 0.0F, 0.0F));

		PartDefinition p6 = p5.addOrReplaceChild("p6", CubeListBuilder.create().texOffs(106, 0).addBox(-2.0F, -2.5174F, 0.4008F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 6.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition p7 = p6.addOrReplaceChild("p7", CubeListBuilder.create().texOffs(106, 10).addBox(-1.5F, -1.9686F, 0.3016F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.0174F, 5.8008F, -0.1047F, 0.0F, 0.0F));

		PartDefinition p8 = p7.addOrReplaceChild("p8", CubeListBuilder.create().texOffs(106, 19).addBox(-1.0F, -1.6584F, 0.3097F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0314F, 6.0016F, 0.1222F, 0.0F, 0.0F));

		PartDefinition p9 = p8.addOrReplaceChild("p9", CubeListBuilder.create().texOffs(106, 27).addBox(-0.5F, -0.8954F, 0.1037F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.1584F, 6.2097F, -0.0698F, 0.0F, 0.0F));

		PartDefinition back_left_leg = body.addOrReplaceChild("back_left_leg", CubeListBuilder.create(), PartPose.offset(-1.986F, -16.3531F, 15.9836F));

		PartDefinition bll_start = back_left_leg.addOrReplaceChild("bll_start", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition start_r1 = bll_start.addOrReplaceChild("start_r1", CubeListBuilder.create().texOffs(40, 53).addBox(-13.0F, -5.0F, -4.0F, 14.0F, 6.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-0.014F, 2.3531F, 0.8164F, -0.0757F, -0.2011F, -0.543F));

		PartDefinition bll_mid = bll_start.addOrReplaceChild("bll_mid", CubeListBuilder.create(), PartPose.offset(-10.4F, 6.4F, -2.7F));

		PartDefinition mid_r1 = bll_mid.addOrReplaceChild("mid_r1", CubeListBuilder.create().texOffs(7, 32).addBox(-12.2F, -2.6F, -3.1F, 12.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1766F, -0.1098F, 0.4F, 0.0F, 0.0F, -0.6981F));

		PartDefinition bll_end = bll_mid.addOrReplaceChild("bll_end", CubeListBuilder.create(), PartPose.offset(-6.0953F, 5.6469F, -1.2774F));

		PartDefinition end_r1 = bll_end.addOrReplaceChild("end_r1", CubeListBuilder.create().texOffs(80, 85).addBox(-10.0F, -4.0F, -4.0F, 11.0F, 4.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(1.4732F, 2.2062F, 1.1345F, -0.778F, 1.2759F, -0.8939F));

		PartDefinition bll_foot = bll_end.addOrReplaceChild("bll_foot", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0895F, 3.4937F, 8.1248F, 0.0F, 0.0349F, 0.0F));

		PartDefinition foot_r1 = bll_foot.addOrReplaceChild("foot_r1", CubeListBuilder.create().texOffs(106, 38).addBox(-4.0F, -3.0F, 0.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7253F, 1.8125F, -1.254F, 0.0F, -0.5236F, 0.0F));

		PartDefinition bll_toe1 = bll_foot.addOrReplaceChild("bll_toe1", CubeListBuilder.create(), PartPose.offset(-0.8756F, -0.0625F, 0.2134F));

		PartDefinition back4 = bll_toe1.addOrReplaceChild("back4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition toe1_back_r1 = back4.addOrReplaceChild("toe1_back_r1", CubeListBuilder.create().texOffs(110, 82).addBox(-2.0F, -1.5F, -1.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.8899F, 0.875F, 1.1873F, 0.0F, 0.4363F, 0.0F));

		PartDefinition front4 = back4.addOrReplaceChild("front4", CubeListBuilder.create(), PartPose.offset(-2.4456F, 0.125F, 1.0546F));

		PartDefinition toe1_claw_r1 = front4.addOrReplaceChild("toe1_claw_r1", CubeListBuilder.create().texOffs(28, 109).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.655F, -0.25F, 2.6083F, 0.0F, 0.7418F, 0.0F));

		PartDefinition toe1_front_r1 = front4.addOrReplaceChild("toe1_front_r1", CubeListBuilder.create().texOffs(48, 111).addBox(-1.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.7136F, -0.25F, 1.7294F, 0.0F, 0.7418F, 0.0F));

		PartDefinition bll_toe2 = bll_foot.addOrReplaceChild("bll_toe2", CubeListBuilder.create(), PartPose.offset(-1.8303F, 0.0625F, -0.6431F));

		PartDefinition toe2_claw_r1 = bll_toe2.addOrReplaceChild("toe2_claw_r1", CubeListBuilder.create().texOffs(30, 107).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.758F, 0.25F, -5.1132F, 0.0F, -1.9199F, 0.0F));

		PartDefinition toe2_r1 = bll_toe2.addOrReplaceChild("toe2_r1", CubeListBuilder.create().texOffs(28, 85).addBox(-2.5F, -0.5F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.842F, -0.25F, -2.4868F, 0.0F, -1.9199F, 0.0F));

		PartDefinition bll_toe3 = bll_foot.addOrReplaceChild("bll_toe3", CubeListBuilder.create(), PartPose.offset(-3.9236F, 0.0625F, -2.7356F));

		PartDefinition toe3_claw_r1 = bll_toe3.addOrReplaceChild("toe3_claw_r1", CubeListBuilder.create().texOffs(74, 89).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.9699F, 0.25F, -5.8468F, 0.0F, -1.2654F, 0.0F));

		PartDefinition toe3_r1 = bll_toe3.addOrReplaceChild("toe3_r1", CubeListBuilder.create().texOffs(106, 48).addBox(-3.0F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0301F, -0.25F, -2.7532F, 0.0F, -1.2654F, 0.0F));

		PartDefinition bll_toe4 = bll_foot.addOrReplaceChild("bll_toe4", CubeListBuilder.create(), PartPose.offset(-4.2823F, 0.0625F, -1.8998F));

		PartDefinition toe4_claw_r1 = bll_toe4.addOrReplaceChild("toe4_claw_r1", CubeListBuilder.create().texOffs(76, 89).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.2923F, 0.25F, 2.4865F, 0.0F, 0.3491F, 0.0F));

		PartDefinition toe4_r1 = bll_toe4.addOrReplaceChild("toe4_r1", CubeListBuilder.create().texOffs(106, 50).addBox(-3.0F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.3077F, -0.25F, 1.3135F, 0.0F, 0.3491F, 0.0F));

		PartDefinition bll_toe5 = bll_foot.addOrReplaceChild("bll_toe5", CubeListBuilder.create(), PartPose.offset(-4.1638F, 0.0625F, -2.2968F));

		PartDefinition toe5_claw_r1 = bll_toe5.addOrReplaceChild("toe5_claw_r1", CubeListBuilder.create().texOffs(78, 89).addBox(-2.6856F, -1.0F, -0.2044F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0619F, 0.25F, -1.8629F, 0.0F, -0.5236F, 0.0F));

		PartDefinition toe5_r1 = bll_toe5.addOrReplaceChild("toe5_r1", CubeListBuilder.create().texOffs(0, 107).addBox(-3.0F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9475F, -0.25F, -1.4673F, 0.0F, -0.5236F, 0.0F));

		PartDefinition back_right_leg = body.addOrReplaceChild("back_right_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(4.1674F, -16.7181F, 15.8982F, 0.0F, 0.0F, -3.1416F));

		PartDefinition brl_start = back_right_leg.addOrReplaceChild("brl_start", CubeListBuilder.create(), PartPose.offset(0.0F, -0.1F, -0.4F));

		PartDefinition start_r2 = brl_start.addOrReplaceChild("start_r2", CubeListBuilder.create().texOffs(40, 77).addBox(-14.1497F, -2.7522F, -2.8392F, 14.0F, 6.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.1683F, -0.1081F, 0.1106F, 0.0757F, -0.2011F, 0.543F));

		PartDefinition brl_mid = brl_start.addOrReplaceChild("brl_mid", CubeListBuilder.create(), PartPose.offset(-10.2F, -6.2F, -2.4F));

		PartDefinition mid_r2 = brl_mid.addOrReplaceChild("mid_r2", CubeListBuilder.create().texOffs(7, 21).addBox(-12.0F, -2.0F, -3.0F, 12.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, -0.5F, 0.0F, 0.0F, 0.0F, 0.6981F));

		PartDefinition brl_end = brl_mid.addOrReplaceChild("brl_end", CubeListBuilder.create(), PartPose.offset(-6.3154F, -6.5486F, -1.6627F));

		PartDefinition end_r2 = brl_end.addOrReplaceChild("end_r2", CubeListBuilder.create().texOffs(28, 89).addBox(-10.0F, -4.0F, -4.0F, 11.0F, 4.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.8134F, 1.8972F, 1.9052F, 0.778F, 1.2759F, 0.8939F));

		PartDefinition brl_foot = brl_end.addOrReplaceChild("brl_foot", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.5296F, -3.3972F, 7.7541F, 0.0F, 0.1222F, 0.0F));

		PartDefinition foot_r2 = brl_foot.addOrReplaceChild("foot_r2", CubeListBuilder.create().texOffs(106, 34).addBox(-4.0F, -2.9306F, 0.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4449F, 2.1944F, -1.0126F, 0.0F, -0.5236F, 0.0F));

		PartDefinition brl_toe1 = brl_foot.addOrReplaceChild("brl_toe1", CubeListBuilder.create(), PartPose.offset(-0.4772F, 0.0278F, 0.6063F));

		PartDefinition back3 = brl_toe1.addOrReplaceChild("back3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -0.1F));

		PartDefinition toe1_back_r2 = back3.addOrReplaceChild("toe1_back_r2", CubeListBuilder.create().texOffs(82, 103).addBox(-2.0F, -0.9306F, -1.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.008F, 1.1667F, 1.1357F, 0.0F, 0.4363F, 0.0F));

		PartDefinition front3 = back3.addOrReplaceChild("front3", CubeListBuilder.create(), PartPose.offset(-2.6F, 0.0F, 1.5F));

		PartDefinition toe1_claw_r2 = front3.addOrReplaceChild("toe1_claw_r2", CubeListBuilder.create().texOffs(30, 109).addBox(-0.5F, -0.9306F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.6187F, 0.1667F, 2.1114F, 0.0F, 0.7418F, 0.0F));

		PartDefinition toe1_front_r2 = front3.addOrReplaceChild("toe1_front_r2", CubeListBuilder.create().texOffs(112, 84).addBox(-1.0F, 0.0694F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6774F, 0.1667F, 1.2325F, 0.0F, 0.7418F, 0.0F));

		PartDefinition brl_toe2 = brl_foot.addOrReplaceChild("brl_toe2", CubeListBuilder.create(), PartPose.offset(-1.55F, -0.0556F, -0.2017F));

		PartDefinition toe2_claw_r2 = brl_toe2.addOrReplaceChild("toe2_claw_r2", CubeListBuilder.create().texOffs(56, 111).addBox(-0.5F, -1.4306F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.758F, 0.75F, -5.3132F, 0.0F, -1.9199F, 0.0F));

		PartDefinition toe2_r2 = brl_toe2.addOrReplaceChild("toe2_r2", CubeListBuilder.create().texOffs(28, 83).addBox(-2.5F, 0.0694F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.842F, 0.25F, -2.6868F, 0.0F, -1.9199F, 0.0F));

		PartDefinition brl_toe3 = brl_foot.addOrReplaceChild("brl_toe3", CubeListBuilder.create(), PartPose.offset(-3.5433F, -0.0556F, -2.1942F));

		PartDefinition toe3_claw_r2 = brl_toe3.addOrReplaceChild("toe3_claw_r2", CubeListBuilder.create().texOffs(74, 91).addBox(-0.5F, -1.4306F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0699F, 0.75F, -6.1468F, 0.0F, -1.2654F, 0.0F));

		PartDefinition toe3_r2 = brl_toe3.addOrReplaceChild("toe3_r2", CubeListBuilder.create().texOffs(14, 107).addBox(-3.0F, 0.0694F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.1301F, 0.25F, -3.0532F, 0.0F, -1.2654F, 0.0F));

		PartDefinition brl_toe4 = brl_foot.addOrReplaceChild("brl_toe4", CubeListBuilder.create(), PartPose.offset(-3.902F, -0.0556F, -1.4584F));

		PartDefinition toe4_claw_r2 = brl_toe4.addOrReplaceChild("toe4_claw_r2", CubeListBuilder.create().texOffs(76, 91).addBox(-0.5F, -1.4306F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.3923F, 0.75F, 2.2865F, 0.0F, 0.3491F, 0.0F));

		PartDefinition toe4_r2 = brl_toe4.addOrReplaceChild("toe4_r2", CubeListBuilder.create().texOffs(110, 76).addBox(-3.0F, 0.0694F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.4077F, 0.25F, 1.1135F, 0.0F, 0.3491F, 0.0F));

		PartDefinition brl_toe5 = brl_foot.addOrReplaceChild("brl_toe5", CubeListBuilder.create(), PartPose.offset(-3.6835F, -0.0556F, -1.9554F));

		PartDefinition toe5_claw_r2 = brl_toe5.addOrReplaceChild("toe5_claw_r2", CubeListBuilder.create().texOffs(78, 91).addBox(-2.6856F, -1.4306F, -0.2044F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.2619F, 0.75F, -1.9629F, 0.0F, -0.5236F, 0.0F));

		PartDefinition toe5_r2 = brl_toe5.addOrReplaceChild("toe5_r2", CubeListBuilder.create().texOffs(110, 78).addBox(-3.0F, 0.0694F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.1475F, 0.25F, -1.5673F, 0.0F, -0.5236F, 0.0F));

		PartDefinition front_left_leg = body.addOrReplaceChild("front_left_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.7229F, -16.3181F, -19.1837F, 3.1416F, 0.0F, 0.0F));

		PartDefinition fll_start = front_left_leg.addOrReplaceChild("fll_start", CubeListBuilder.create(), PartPose.offset(0.5F, -0.1071F, 1.0812F));

		PartDefinition start_r3 = fll_start.addOrReplaceChild("start_r3", CubeListBuilder.create().texOffs(40, 65).addBox(-14.0917F, -3.0591F, -3.0728F, 14.0F, 6.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-0.25F, 0.0F, 0.1F, -0.0382F, -0.3397F, 0.5784F));

		PartDefinition fll_mid = fll_start.addOrReplaceChild("fll_mid", CubeListBuilder.create(), PartPose.offset(-9.0F, -5.4F, -4.3F));

		PartDefinition mid_r3 = fll_mid.addOrReplaceChild("mid_r3", CubeListBuilder.create().texOffs(7, 21).addBox(-12.139F, -2.531F, -3.5217F, 12.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0077F, 0.1395F, 0.8F, 0.0F, -0.1309F, 0.6981F));

		PartDefinition fll_end = fll_mid.addOrReplaceChild("fll_end", CubeListBuilder.create(), PartPose.offset(-6.233F, -5.2415F, -1.3014F));

		PartDefinition end_r3 = fll_end.addOrReplaceChild("end_r3", CubeListBuilder.create().texOffs(60, 94).addBox(-10.0F, -4.0F, -4.0F, 11.0F, 4.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.8478F, 1.5972F, 1.6202F, 0.937F, 1.1497F, 1.1617F));

		PartDefinition fll_foot = fll_end.addOrReplaceChild("fll_foot", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.064F, -4.6972F, 8.7391F, 3.1416F, 1.2305F, 3.1416F));

		PartDefinition foot_r3 = fll_foot.addOrReplaceChild("foot_r3", CubeListBuilder.create().texOffs(106, 42).addBox(-2.5F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.8689F, 0.1944F, -1.9478F, 0.0F, -0.5236F, 0.0F));

		PartDefinition fll_toe1 = fll_foot.addOrReplaceChild("fll_toe1", CubeListBuilder.create(), PartPose.offset(-1.1888F, 0.0278F, -0.2316F));

		PartDefinition back2 = fll_toe1.addOrReplaceChild("back2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition toe1_back_r3 = back2.addOrReplaceChild("toe1_back_r3", CubeListBuilder.create().texOffs(112, 86).addBox(-2.0F, -1.5F, -1.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.9213F, 1.1667F, 0.8224F, 0.0F, 0.4363F, 0.0F));

		PartDefinition front2 = back2.addOrReplaceChild("front2", CubeListBuilder.create(), PartPose.offset(-2.3855F, 0.0F, 1.5197F));

		PartDefinition toe1_claw_r3 = front2.addOrReplaceChild("toe1_claw_r3", CubeListBuilder.create().texOffs(48, 113).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.7465F, 0.1667F, 1.7784F, 0.0F, 0.7418F, 0.0F));

		PartDefinition toe1_front_r3 = front2.addOrReplaceChild("toe1_front_r3", CubeListBuilder.create().texOffs(112, 88).addBox(-1.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.8052F, 0.1667F, 0.8995F, 0.0F, 0.7418F, 0.0F));

		PartDefinition fll_toe2 = fll_foot.addOrReplaceChild("fll_toe2", CubeListBuilder.create(), PartPose.offset(-2.1961F, -0.0556F, -1.1415F));

		PartDefinition toe2_claw_r3 = fll_toe2.addOrReplaceChild("toe2_claw_r3", CubeListBuilder.create().texOffs(50, 113).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.7792F, 0.75F, -5.4247F, 0.0F, -1.9199F, 0.0F));

		PartDefinition toe2_r3 = fll_toe2.addOrReplaceChild("toe2_r3", CubeListBuilder.create().texOffs(28, 87).addBox(-2.5F, -0.5F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.8632F, 0.25F, -2.7983F, 0.0F, -1.9199F, 0.0F));

		PartDefinition fll_toe3 = fll_foot.addOrReplaceChild("fll_toe3", CubeListBuilder.create(), PartPose.offset(-4.0302F, -0.0556F, -2.7485F));

		PartDefinition toe3_claw_r3 = fll_toe3.addOrReplaceChild("toe3_claw_r3", CubeListBuilder.create().texOffs(58, 98).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.2079F, 0.75F, -6.6438F, 0.0F, -1.2654F, 0.0F));

		PartDefinition toe3_r3 = fll_toe3.addOrReplaceChild("toe3_r3", CubeListBuilder.create().texOffs(0, 109).addBox(-3.0F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.2681F, 0.25F, -3.5502F, 0.0F, -1.2654F, 0.0F));

		PartDefinition fll_toe4 = fll_foot.addOrReplaceChild("fll_toe4", CubeListBuilder.create(), PartPose.offset(-4.3732F, -0.0556F, -2.3524F));

		PartDefinition toe4_claw_r3 = fll_toe4.addOrReplaceChild("toe4_claw_r3", CubeListBuilder.create().texOffs(58, 100).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.546F, 0.75F, 2.1293F, 0.0F, 0.3491F, 0.0F));

		PartDefinition toe4_r3 = fll_toe4.addOrReplaceChild("toe4_r3", CubeListBuilder.create().texOffs(14, 109).addBox(-3.0F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5614F, 0.25F, 0.9562F, 0.0F, 0.3491F, 0.0F));

		PartDefinition fll_toe5 = fll_foot.addOrReplaceChild("fll_toe5", CubeListBuilder.create(), PartPose.offset(-4.4597F, -0.0556F, -2.3718F));

		PartDefinition toe5_claw_r3 = fll_toe5.addOrReplaceChild("toe5_claw_r3", CubeListBuilder.create().texOffs(90, 103).addBox(-2.6856F, -2.0F, -0.2044F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.1105F, 0.75F, -2.5977F, 0.0F, -0.5236F, 0.0F));

		PartDefinition toe5_r3 = fll_toe5.addOrReplaceChild("toe5_r3", CubeListBuilder.create().texOffs(110, 70).addBox(-3.0F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9961F, 0.25F, -2.2021F, 0.0F, -0.5236F, 0.0F));

		PartDefinition front_right_leg = body.addOrReplaceChild("front_right_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(3.1368F, -16.7333F, -20.9974F, -3.1416F, 0.0F, -3.1416F));

		PartDefinition frl_start = front_right_leg.addOrReplaceChild("frl_start", CubeListBuilder.create(), PartPose.offset(0.1514F, 0.2333F, -0.7268F));

		PartDefinition start_r4 = frl_start.addOrReplaceChild("start_r4", CubeListBuilder.create().texOffs(0, 71).addBox(-13.2971F, -3.1677F, -3.2555F, 14.0F, 6.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-0.8093F, 0.5F, 0.4391F, -0.0382F, -0.3397F, -0.5784F));

		PartDefinition frl_mid = frl_start.addOrReplaceChild("frl_mid", CubeListBuilder.create(), PartPose.offset(-10.1F, 5.9F, -3.7F));

		PartDefinition mid_r4 = frl_mid.addOrReplaceChild("mid_r4", CubeListBuilder.create().texOffs(7, 32).addBox(-12.2263F, -1.8562F, -2.3F, 12.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3F, -0.4F, -0.9F, 0.0F, 0.0F, -0.6981F));

		PartDefinition frl_end = frl_mid.addOrReplaceChild("frl_end", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.968F, 5.9F, -2.1186F, 0.0F, 0.1309F, 0.0F));

		PartDefinition end_r4 = frl_end.addOrReplaceChild("end_r4", CubeListBuilder.create().texOffs(0, 98).addBox(-10.7093F, -1.9056F, -2.3911F, 11.0F, 4.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.5195F, 0.16F, 0.4694F, -0.8855F, 1.2669F, -0.9956F));

		PartDefinition frl_foot = frl_end.addOrReplaceChild("frl_foot", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.3951F, 3.6444F, 10.4407F, -3.1416F, 1.2305F, -3.1416F));

		PartDefinition foot_r4 = frl_foot.addOrReplaceChild("foot_r4", CubeListBuilder.create().texOffs(60, 89).addBox(-3.9544F, -3.25F, 0.2458F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4793F, 2.2056F, -1.8236F, 0.0F, -0.5236F, 0.0F));

		PartDefinition frl_toe1 = frl_foot.addOrReplaceChild("frl_toe1", CubeListBuilder.create(), PartPose.offset(-0.6415F, 0.3722F, 0.2039F));

		PartDefinition back = frl_toe1.addOrReplaceChild("back", CubeListBuilder.create(), PartPose.offset(0.6265F, -0.1667F, -0.1581F));

		PartDefinition toe1_back_r4 = back.addOrReplaceChild("toe1_back_r4", CubeListBuilder.create().texOffs(112, 90).addBox(-2.1752F, -1.75F, -0.8217F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.5046F, 1.0F, 0.7853F, 0.0F, 0.4363F, 0.0F));

		PartDefinition front = back.addOrReplaceChild("front", CubeListBuilder.create(), PartPose.offset(-2.9961F, 0.0F, 0.3081F));

		PartDefinition toe1_claw_r4 = front.addOrReplaceChild("toe1_claw_r4", CubeListBuilder.create().texOffs(52, 113).addBox(-0.7207F, -0.75F, 0.1174F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.7192F, 0.0F, 2.9528F, 0.0F, 0.7418F, 0.0F));

		PartDefinition toe1_front_r4 = front.addOrReplaceChild("toe1_front_r4", CubeListBuilder.create().texOffs(112, 92).addBox(-1.2207F, -0.75F, -0.3826F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.7778F, 0.0F, 2.0739F, 0.0F, 0.7418F, 0.0F));

		PartDefinition frl_toe2 = frl_foot.addOrReplaceChild("frl_toe2", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.1501F, 0.4556F, -1.1075F, 0.0426F, -0.0002F, 0.0094F));

		PartDefinition toe2_claw_r4 = frl_toe2.addOrReplaceChild("toe2_claw_r4", CubeListBuilder.create().texOffs(54, 113).addBox(-0.2394F, -1.2389F, -0.0036F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3237F, 0.25F, -5.2184F, 0.0F, -1.9199F, 0.0F));

		PartDefinition toe2_r4 = frl_toe2.addOrReplaceChild("toe2_r4", CubeListBuilder.create().texOffs(110, 80).addBox(-2.2394F, -0.7389F, -0.5036F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4078F, -0.25F, -2.592F, 0.0F, -1.9199F, 0.0F));

		PartDefinition frl_toe3 = frl_foot.addOrReplaceChild("frl_toe3", CubeListBuilder.create(), PartPose.offset(-3.3309F, 0.4556F, -2.0744F));

		PartDefinition toe3_claw_r4 = frl_toe3.addOrReplaceChild("toe3_claw_r4", CubeListBuilder.create().texOffs(28, 107).addBox(-0.3003F, -1.25F, 0.1504F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3166F, 0.25F, -7.0776F, 0.0F, -1.2654F, 0.0F));

		PartDefinition toe3_r4 = frl_toe3.addOrReplaceChild("toe3_r4", CubeListBuilder.create().texOffs(110, 72).addBox(-2.8003F, -0.75F, -0.3496F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.3768F, -0.25F, -3.984F, 0.0F, -1.2654F, 0.0F));

		PartDefinition frl_toe4 = frl_foot.addOrReplaceChild("frl_toe4", CubeListBuilder.create(), PartPose.offset(-4.0211F, 0.4556F, -1.9601F));

		PartDefinition toe4_claw_r4 = frl_toe4.addOrReplaceChild("toe4_claw_r4", CubeListBuilder.create().texOffs(104, 113).addBox(-0.659F, -1.25F, 0.1929F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.3075F, 0.25F, 1.9773F, 0.0F, 0.3491F, 0.0F));

		PartDefinition toe4_r4 = frl_toe4.addOrReplaceChild("toe4_r4", CubeListBuilder.create().texOffs(106, 46).addBox(-3.159F, -0.75F, -0.3071F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.3229F, -0.25F, 0.8042F, 0.0F, 0.3491F, 0.0F));

		PartDefinition frl_toe5 = frl_foot.addOrReplaceChild("frl_toe5", CubeListBuilder.create(), PartPose.offset(-3.7826F, 0.4556F, -2.5221F));

		PartDefinition toe5_claw_r4 = frl_toe5.addOrReplaceChild("toe5_claw_r4", CubeListBuilder.create().texOffs(56, 113).addBox(-2.6401F, -1.25F, 0.0414F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.1971F, 0.25F, -2.2072F, 0.0F, -0.5236F, 0.0F));

		PartDefinition toe5_r4 = frl_toe5.addOrReplaceChild("toe5_r4", CubeListBuilder.create().texOffs(110, 74).addBox(-2.9544F, -0.75F, -0.2542F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0827F, -0.25F, -1.8116F, 0.0F, -0.5236F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		root().getAllParts().forEach(ModelPart::resetPose);

		if (entity.isCustomDying()) {
			animate(entity.deathAnimationState, ShardLizardThrasherAnimations.shard_lizard_thrasher_death, ageInTicks, 2f);
			return;
		}

		if (entity.isAttacking()) {
			switch (entity.getAttackType()) {
				case ShardLizardThrasher.ATTACK_SLAM:
					animate(entity.slamAnimationState, ShardLizardThrasherAnimations.shard_lizard_thrasher_tail_slam, ageInTicks, 1.0f);
					break;
				case ShardLizardThrasher.ATTACK_COMBO:
					animate(entity.comboAnimationState, ShardLizardThrasherAnimations.shard_lizard_thrasher_tail_combo, ageInTicks, 1.0f);
					break;
				case ShardLizardThrasher.ATTACK_SPIN:
				default:
					animate(entity.spinAnimationState, ShardLizardThrasherAnimations.shard_lizard_thrasher_tail_spin, ageInTicks, 1.0f);
					break;
			}
			return;
		}

		applyHeadRotation(netHeadYaw, headPitch, ageInTicks);
		boolean isMoving = limbSwingAmount > 0.01f || entity.getDeltaMovement().horizontalDistanceSqr() > 0.0001d;
		if (isMoving) {
			animateWalk(ShardLizardThrasherAnimations.shard_lizard_thrasher_walk, limbSwing, limbSwingAmount, 5f, 2.5f);
			entity.idleAnimationState.stop();
			return;
		}

		animate(entity.idleAnimationState, ShardLizardThrasherAnimations.shard_lizard_thrasher_idle, ageInTicks, 1.0f);
	}

	private void applyHeadRotation(float netheadYaw, float headPitch, float ageInTicks) {
		netheadYaw = Mth.clamp(netheadYaw, -30.0f, 30.0f);
		headPitch = Mth.clamp(headPitch, -25.0f, 45.0f);
		head.yRot = netheadYaw * ((float) Math.PI / 180f);
		head.xRot = headPitch * ((float) Math.PI / 180f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		shard_lizard_thrasher.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return shard_lizard_thrasher;
	}
}