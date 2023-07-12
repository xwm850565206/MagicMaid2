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
public class ModelAili extends ModelBase {
	private final ModelRenderer main;
	private final ModelRenderer head_bone;
	private final ModelRenderer face_bone;
	private final ModelRenderer face_hair_bone;
	private final ModelRenderer inside_face_bone;
	private final ModelRenderer hair_bone;
	private final ModelRenderer left_h;
	private final ModelRenderer right_h;
	private final ModelRenderer left_ear_bone;
	private final ModelRenderer right_ear_bone;
	private final ModelRenderer headwear_bone;
	private final ModelRenderer body_bone;
	private final ModelRenderer right_arm_bone;
	private final ModelRenderer left_arm_bone;
	private final ModelRenderer inside_body_bone;
	private final ModelRenderer dress_bone;
	private final ModelRenderer bottom_bone;
	private final ModelRenderer right_leg_bone;
	private final ModelRenderer left_leg_bone;
	private final ModelRenderer tail_bone;

	public ModelAili() {
		textureWidth = 64;
		textureHeight = 64;

		main = new ModelRenderer(this);
		main.setRotationPoint(0.25F, 24.0F, 0.5F);
		

		head_bone = new ModelRenderer(this);
		head_bone.setRotationPoint(0.0F, -17.0F, -1.0F);
		main.addChild(head_bone);
		

		face_bone = new ModelRenderer(this);
		face_bone.setRotationPoint(-0.5F, 2.75F, 0.125F);
		head_bone.addChild(face_bone);
		

		face_hair_bone = new ModelRenderer(this);
		face_hair_bone.setRotationPoint(0.0F, 0.0F, 0.0F);
		face_bone.addChild(face_hair_bone);
		face_hair_bone.cubeList.add(new ModelBox(face_hair_bone, 40, 40, -1.75F, -4.5F, -1.875F, 4, 4, 4, 1.0F, false));

		inside_face_bone = new ModelRenderer(this);
		inside_face_bone.setRotationPoint(0.0F, 0.0F, 0.0F);
		face_bone.addChild(inside_face_bone);
		inside_face_bone.cubeList.add(new ModelBox(inside_face_bone, 0, 23, -1.25F, -3.75F, -1.375F, 3, 3, 3, 1.0F, false));

		hair_bone = new ModelRenderer(this);
		hair_bone.setRotationPoint(-0.25F, -2.25F, 3.5F);
		head_bone.addChild(hair_bone);
		

		left_h = new ModelRenderer(this);
		left_h.setRotationPoint(3.0F, 1.0F, -1.0F);
		hair_bone.addChild(left_h);
		setRotationAngle(left_h, -1.0908F, 0.5672F, 0.0F);
		left_h.cubeList.add(new ModelBox(left_h, 24, 12, -1.4929F, -1.0926F, -0.4558F, 2, 2, 12, 0.0F, false));

		right_h = new ModelRenderer(this);
		right_h.setRotationPoint(-2.25F, 1.0F, -0.25F);
		hair_bone.addChild(right_h);
		setRotationAngle(right_h, -1.0908F, -0.5672F, 0.0F);
		right_h.cubeList.add(new ModelBox(right_h, 0, 23, -1.2162F, -0.8717F, -0.3321F, 2, 2, 12, 0.0F, false));

		left_ear_bone = new ModelRenderer(this);
		left_ear_bone.setRotationPoint(3.0F, -2.75F, -0.5F);
		head_bone.addChild(left_ear_bone);
		setRotationAngle(left_ear_bone, 0.0F, -0.4363F, 0.5672F);
		left_ear_bone.cubeList.add(new ModelBox(left_ear_bone, 18, 37, -1.8783F, -3.7892F, -0.0568F, 4, 5, 1, 0.0F, false));

		right_ear_bone = new ModelRenderer(this);
		right_ear_bone.setRotationPoint(-3.0F, -2.75F, -1.0F);
		head_bone.addChild(right_ear_bone);
		setRotationAngle(right_ear_bone, 0.0F, 0.4363F, -0.5672F);
		right_ear_bone.cubeList.add(new ModelBox(right_ear_bone, 0, 29, -2.52F, -3.7688F, 0.3778F, 4, 5, 1, 0.0F, false));

		headwear_bone = new ModelRenderer(this);
		headwear_bone.setRotationPoint(-0.25F, -2.75F, -2.0F);
		head_bone.addChild(headwear_bone);
		headwear_bone.cubeList.add(new ModelBox(headwear_bone, 29, 26, -3.0F, -1.0F, 0.0F, 6, 1, 1, 0.0F, false));
		headwear_bone.cubeList.add(new ModelBox(headwear_bone, 27, 12, -1.5F, -2.0F, 0.0F, 3, 1, 1, 0.0F, false));

		body_bone = new ModelRenderer(this);
		body_bone.setRotationPoint(-0.4167F, -5.6667F, -0.5F);
		main.addChild(body_bone);
		

		right_arm_bone = new ModelRenderer(this);
		right_arm_bone.setRotationPoint(-2.5139F, -7.7045F, -0.25F);
		body_bone.addChild(right_arm_bone);
		setRotationAngle(right_arm_bone, 0.0F, 0.0F, 0.1745F);
		right_arm_bone.cubeList.add(new ModelBox(right_arm_bone, 0, 12, -1.3687F, -0.481F, -1.25F, 2, 7, 2, 0.0F, false));

		left_arm_bone = new ModelRenderer(this);
		left_arm_bone.setRotationPoint(2.2654F, -7.5836F, -0.25F);
		body_bone.addChild(left_arm_bone);
		setRotationAngle(left_arm_bone, 0.0F, 0.0F, -0.1745F);
		left_arm_bone.cubeList.add(new ModelBox(left_arm_bone, 0, 0, -0.0605F, -0.3434F, -1.25F, 2, 7, 2, 0.0F, false));

		inside_body_bone = new ModelRenderer(this);
		inside_body_bone.setRotationPoint(-0.3333F, -6.8333F, -0.75F);
		body_bone.addChild(inside_body_bone);
		inside_body_bone.cubeList.add(new ModelBox(inside_body_bone, 16, 23, -0.5F, -0.5F, -0.5F, 2, 2, 2, 1.0F, false));

		dress_bone = new ModelRenderer(this);
		dress_bone.setRotationPoint(-0.8333F, -2.3333F, -0.25F);
		body_bone.addChild(dress_bone);
		dress_bone.cubeList.add(new ModelBox(dress_bone, 28, 28, -3.0F, 1.0F, -4.0F, 8, 1, 8, 0.0F, false));
		dress_bone.cubeList.add(new ModelBox(dress_bone, 30, 0, -2.5F, 0.0F, -3.5F, 7, 1, 7, 0.0F, false));
		dress_bone.cubeList.add(new ModelBox(dress_bone, 0, 37, -2.0F, -1.0F, -3.0F, 6, 1, 6, 0.0F, false));
		dress_bone.cubeList.add(new ModelBox(dress_bone, 24, 38, -1.5F, -2.0F, -2.5F, 5, 1, 5, 0.0F, false));
		dress_bone.cubeList.add(new ModelBox(dress_bone, 0, 0, -3.5F, 2.0F, -4.5F, 9, 2, 9, 0.0F, false));

		bottom_bone = new ModelRenderer(this);
		bottom_bone.setRotationPoint(-0.0833F, -0.3333F, 0.0F);
		body_bone.addChild(bottom_bone);
		

		right_leg_bone = new ModelRenderer(this);
		right_leg_bone.setRotationPoint(-2.0F, 0.0F, 0.0F);
		bottom_bone.addChild(right_leg_bone);
		right_leg_bone.cubeList.add(new ModelBox(right_leg_bone, 0, 58, -0.25F, 2.0F, -1.0F, 2, 4, 2, 0.0F, false));

		left_leg_bone = new ModelRenderer(this);
		left_leg_bone.setRotationPoint(2.25F, 0.0F, 0.0F);
		bottom_bone.addChild(left_leg_bone);
		left_leg_bone.cubeList.add(new ModelBox(left_leg_bone, 0, 58, -1.5F, 2.0F, -1.0F, 2, 4, 2, 0.0F, false));

		tail_bone = new ModelRenderer(this);
		tail_bone.setRotationPoint(0.0F, -2.0F, 4.0F);
		bottom_bone.addChild(tail_bone);
		setRotationAngle(tail_bone, -0.3927F, 0.0F, 0.0F);
		tail_bone.cubeList.add(new ModelBox(tail_bone, 44, 55, -1.5F, -0.4693F, -2.6955F, 3, 2, 7, 0.0F, false));
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