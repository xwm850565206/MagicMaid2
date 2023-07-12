package com.xwm.magicmaid2.client.model.javamodel;// Made with Blockbench 3.9.3
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelCarlie extends ModelBase {
	private final ModelRenderer main;
	private final ModelRenderer head_bone;
	private final ModelRenderer face_bone;
	private final ModelRenderer hair_bone;
	private final ModelRenderer inside_face_bone;
	private final ModelRenderer right_ear_bone;
	private final ModelRenderer left_ear_bone;
	private final ModelRenderer body_bone;
	private final ModelRenderer right_arm_bone;
	private final ModelRenderer right_arm_wrapper;
	private final ModelRenderer left_arm_bone;
	private final ModelRenderer left_arm_wrapper;
	private final ModelRenderer inside_body_bone;
	private final ModelRenderer inside_body_wrapper;
	private final ModelRenderer cloak_bone;
	private final ModelRenderer cloak_main_bone;
	private final ModelRenderer bone1;
	private final ModelRenderer bone2;
	private final ModelRenderer bone3;
	private final ModelRenderer bone4;
	private final ModelRenderer bone5;
	private final ModelRenderer bottom_bone;
	private final ModelRenderer right_leg_bone;
	private final ModelRenderer right_leg_wrapper;
	private final ModelRenderer left_leg_bone;
	private final ModelRenderer left_leg_wrapper;

	public ModelCarlie() {
		textureWidth = 128;
		textureHeight = 128;

		main = new ModelRenderer(this);
		main.setRotationPoint(0.0F, 26.0F, 0.0F);
		

		head_bone = new ModelRenderer(this);
		head_bone.setRotationPoint(0.25F, -21.0F, 0.25F);
		main.addChild(head_bone);
		

		face_bone = new ModelRenderer(this);
		face_bone.setRotationPoint(-0.5F, 2.75F, 0.125F);
		head_bone.addChild(face_bone);
		

		hair_bone = new ModelRenderer(this);
		hair_bone.setRotationPoint(-0.2F, 0.0F, 0.0F);
		face_bone.addChild(hair_bone);
		hair_bone.cubeList.add(new ModelBox(hair_bone, 32, 0, -3.55F, -15.75F, -3.625F, 8, 8, 8, 0.25F, false));

		inside_face_bone = new ModelRenderer(this);
		inside_face_bone.setRotationPoint(0.25F, -4.0F, 0.0F);
		face_bone.addChild(inside_face_bone);
		inside_face_bone.cubeList.add(new ModelBox(inside_face_bone, 0, 0, -4.0F, -11.75F, -3.625F, 8, 8, 8, 0.0F, false));

		right_ear_bone = new ModelRenderer(this);
		right_ear_bone.setRotationPoint(-4.0F, -12.0F, 0.5F);
		face_bone.addChild(right_ear_bone);
		right_ear_bone.cubeList.add(new ModelBox(right_ear_bone, 0, 69, -3.0F, -1.0F, -1.0F, 3, 1, 2, 0.0F, false));
		right_ear_bone.cubeList.add(new ModelBox(right_ear_bone, 0, 73, -2.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F, false));

		left_ear_bone = new ModelRenderer(this);
		left_ear_bone.setRotationPoint(4.5F, -12.0F, 0.0F);
		face_bone.addChild(left_ear_bone);
		left_ear_bone.cubeList.add(new ModelBox(left_ear_bone, 0, 69, 0.0F, -1.0F, -0.5F, 3, 1, 2, 0.0F, false));
		left_ear_bone.cubeList.add(new ModelBox(left_ear_bone, 0, 73, 0.0F, 0.0F, -0.5F, 2, 1, 2, 0.0F, false));

		body_bone = new ModelRenderer(this);
		body_bone.setRotationPoint(-0.1667F, -5.6667F, 0.0F);
		main.addChild(body_bone);
		

		right_arm_bone = new ModelRenderer(this);
		right_arm_bone.setRotationPoint(-3.8333F, -19.8333F, 0.0F);
		body_bone.addChild(right_arm_bone);
		right_arm_bone.cubeList.add(new ModelBox(right_arm_bone, 40, 16, -3.0F, -0.5F, -2.0F, 3, 12, 4, 0.0F, false));

		right_arm_wrapper = new ModelRenderer(this);
		right_arm_wrapper.setRotationPoint(0.0F, 0.0F, 0.0F);
		right_arm_bone.addChild(right_arm_wrapper);
		right_arm_wrapper.cubeList.add(new ModelBox(right_arm_wrapper, 40, 32, -3.0F, -0.5F, -2.0F, 3, 12, 4, 0.25F, false));

		left_arm_bone = new ModelRenderer(this);
		left_arm_bone.setRotationPoint(4.1667F, -20.8333F, 0.0F);
		body_bone.addChild(left_arm_bone);
		left_arm_bone.cubeList.add(new ModelBox(left_arm_bone, 32, 48, 0.0F, 0.5F, -2.0F, 3, 12, 4, 0.0F, false));

		left_arm_wrapper = new ModelRenderer(this);
		left_arm_wrapper.setRotationPoint(0.0F, 0.0F, 0.0F);
		left_arm_bone.addChild(left_arm_wrapper);
		left_arm_wrapper.cubeList.add(new ModelBox(left_arm_wrapper, 40, 32, 0.0F, 0.5F, -2.0F, 3, 12, 4, 0.25F, false));

		inside_body_bone = new ModelRenderer(this);
		inside_body_bone.setRotationPoint(0.1667F, -14.3333F, -0.75F);
		body_bone.addChild(inside_body_bone);
		inside_body_bone.cubeList.add(new ModelBox(inside_body_bone, 16, 16, -4.0F, -6.0F, -1.25F, 8, 12, 4, 0.0F, false));

		inside_body_wrapper = new ModelRenderer(this);
		inside_body_wrapper.setRotationPoint(0.0F, 0.0F, 0.0F);
		inside_body_bone.addChild(inside_body_wrapper);
		inside_body_wrapper.cubeList.add(new ModelBox(inside_body_wrapper, 16, 32, -4.0F, -6.0F, -1.25F, 8, 12, 4, 0.25F, false));

		cloak_bone = new ModelRenderer(this);
		cloak_bone.setRotationPoint(0.0F, -5.0F, 2.75F);
		inside_body_bone.addChild(cloak_bone);
		

		cloak_main_bone = new ModelRenderer(this);
		cloak_main_bone.setRotationPoint(0.5F, -1.0F, 0.8F);
		cloak_bone.addChild(cloak_main_bone);
		cloak_main_bone.cubeList.add(new ModelBox(cloak_main_bone, 69, 1, -4.0F, 0.0F, -0.5F, 7, 3, 1, 0.0F, false));

		bone1 = new ModelRenderer(this);
		bone1.setRotationPoint(0.0F, 3.0F, 0.0F);
		cloak_main_bone.addChild(bone1);
		bone1.cubeList.add(new ModelBox(bone1, 67, 81, -6.0F, 0.0F, -0.5F, 11, 4, 1, 0.0F, false));

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0F, 4.0F, 0.0F);
		bone1.addChild(bone2);
		bone2.cubeList.add(new ModelBox(bone2, 69, 68, -7.0F, 0.0F, -0.5F, 13, 5, 1, 0.0F, false));

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(0.0F, 5.0F, 0.0F);
		bone2.addChild(bone3);
		bone3.cubeList.add(new ModelBox(bone3, 67, 27, -6.0F, 0.0F, -0.5F, 11, 5, 1, 0.0F, false));

		bone4 = new ModelRenderer(this);
		bone4.setRotationPoint(0.0F, 5.0F, 0.0F);
		bone3.addChild(bone4);
		bone4.cubeList.add(new ModelBox(bone4, 69, 44, -5.0F, 0.0F, -0.5F, 9, 4, 1, 0.0F, false));

		bone5 = new ModelRenderer(this);
		bone5.setRotationPoint(0.0F, 4.0F, 0.0F);
		bone4.addChild(bone5);
		bone5.cubeList.add(new ModelBox(bone5, 85, 94, -1.5F, 0.0F, -0.5F, 2, 3, 1, 0.0F, false));
		bone5.cubeList.add(new ModelBox(bone5, 62, 93, 0.5F, 0.0F, -0.5F, 2, 2, 1, 0.0F, false));
		bone5.cubeList.add(new ModelBox(bone5, 93, 83, -3.5F, 0.0F, -0.5F, 2, 2, 1, 0.0F, false));

		bottom_bone = new ModelRenderer(this);
		bottom_bone.setRotationPoint(-0.0833F, -6.3333F, 0.0F);
		body_bone.addChild(bottom_bone);
		

		right_leg_bone = new ModelRenderer(this);
		right_leg_bone.setRotationPoint(-1.75F, -2.0F, 0.0F);
		bottom_bone.addChild(right_leg_bone);
		right_leg_bone.cubeList.add(new ModelBox(right_leg_bone, 16, 48, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, true));

		right_leg_wrapper = new ModelRenderer(this);
		right_leg_wrapper.setRotationPoint(0.0F, 0.0F, 0.0F);
		right_leg_bone.addChild(right_leg_wrapper);
		right_leg_wrapper.cubeList.add(new ModelBox(right_leg_wrapper, 0, 48, -2.0F, 0.25F, -2.0F, 4, 12, 4, 0.25F, false));

		left_leg_bone = new ModelRenderer(this);
		left_leg_bone.setRotationPoint(2.25F, -2.0F, 0.0F);
		bottom_bone.addChild(left_leg_bone);
		left_leg_bone.cubeList.add(new ModelBox(left_leg_bone, 16, 48, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));

		left_leg_wrapper = new ModelRenderer(this);
		left_leg_wrapper.setRotationPoint(0.0F, 0.0F, 0.0F);
		left_leg_bone.addChild(left_leg_wrapper);
		left_leg_wrapper.cubeList.add(new ModelBox(left_leg_wrapper, 0, 32, -2.0F, 0.25F, -2.0F, 4, 12, 4, 0.25F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		main.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}