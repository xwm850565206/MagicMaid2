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
public class ModelCassiu extends ModelBase {
	private final ModelRenderer main;
	private final ModelRenderer surround;
	private final ModelRenderer s1;
	private final ModelRenderer s2;
	private final ModelRenderer s3;
	private final ModelRenderer head_bone;
	private final ModelRenderer face_bone;
	private final ModelRenderer hair_bone;
	private final ModelRenderer inside_face_bone;
	private final ModelRenderer body_bone;
	private final ModelRenderer right_arm_bone;
	private final ModelRenderer right_arm_wrapper;
	private final ModelRenderer left_arm_bone;
	private final ModelRenderer left_arm_wrapper;
	private final ModelRenderer inside_body_bone;
	private final ModelRenderer inside_body_wrapper;
	private final ModelRenderer bottom_bone;
	private final ModelRenderer right_leg_bone;
	private final ModelRenderer right_leg_wrapper;
	private final ModelRenderer left_leg_bone;
	private final ModelRenderer left_leg_wrapper;

	public ModelCassiu() {
		textureWidth = 128;
		textureHeight = 128;

		main = new ModelRenderer(this);
		main.setRotationPoint(0.0F, 26.0F, 0.0F);
		

		surround = new ModelRenderer(this);
		surround.setRotationPoint(0.0F, 0.0F, 0.0F);
		main.addChild(surround);
		

		s1 = new ModelRenderer(this);
		s1.setRotationPoint(-6.0F, -12.75F, -2.0F);
		surround.addChild(s1);
		s1.cubeList.add(new ModelBox(s1, 10, 60, -8.0F, -11.25F, -4.0F, 4, 4, 4, 0.0F, false));
		s1.cubeList.add(new ModelBox(s1, 0, 16, -7.0F, -13.25F, -3.0F, 2, 2, 2, 0.0F, false));

		s2 = new ModelRenderer(this);
		s2.setRotationPoint(6.0F, -12.75F, -2.0F);
		surround.addChild(s2);
		s2.cubeList.add(new ModelBox(s2, 10, 60, 4.0F, -11.25F, -4.0F, 4, 4, 4, 0.0F, false));
		s2.cubeList.add(new ModelBox(s2, 0, 16, 5.0F, -13.25F, -3.0F, 2, 2, 2, 0.0F, false));

		s3 = new ModelRenderer(this);
		s3.setRotationPoint(0.0F, -12.75F, 5.0F);
		surround.addChild(s3);
		s3.cubeList.add(new ModelBox(s3, 10, 60, -2.0F, -11.25F, 3.0F, 4, 4, 4, 0.0F, false));
		s3.cubeList.add(new ModelBox(s3, 0, 16, -1.0F, -13.25F, 4.0F, 2, 2, 2, 0.0F, false));

		head_bone = new ModelRenderer(this);
		head_bone.setRotationPoint(0.25F, -21.0F, -0.5F);
		main.addChild(head_bone);
		

		face_bone = new ModelRenderer(this);
		face_bone.setRotationPoint(-0.5F, 2.75F, 0.125F);
		head_bone.addChild(face_bone);
		

		hair_bone = new ModelRenderer(this);
		hair_bone.setRotationPoint(0.0F, 0.0F, 0.0F);
		face_bone.addChild(hair_bone);
		hair_bone.cubeList.add(new ModelBox(hair_bone, 0, 0, -3.5F, -15.75F, -3.625F, 8, 8, 8, 0.25F, false));

		inside_face_bone = new ModelRenderer(this);
		inside_face_bone.setRotationPoint(0.25F, -4.0F, 0.0F);
		face_bone.addChild(inside_face_bone);
		inside_face_bone.cubeList.add(new ModelBox(inside_face_bone, 0, 16, -4.0F, -11.75F, -3.625F, 8, 8, 8, 0.0F, false));

		body_bone = new ModelRenderer(this);
		body_bone.setRotationPoint(-0.1667F, -5.6667F, 0.0F);
		main.addChild(body_bone);
		

		right_arm_bone = new ModelRenderer(this);
		right_arm_bone.setRotationPoint(-3.8333F, -19.8333F, 0.0F);
		body_bone.addChild(right_arm_bone);
		setRotationAngle(right_arm_bone, 0.0F, 0.0F, 0.2182F);
		right_arm_bone.cubeList.add(new ModelBox(right_arm_bone, 56, 0, -3.0F, -0.5F, -2.0F, 3, 12, 4, 0.0F, false));

		right_arm_wrapper = new ModelRenderer(this);
		right_arm_wrapper.setRotationPoint(0.0F, 0.0F, 0.0F);
		right_arm_bone.addChild(right_arm_wrapper);
		right_arm_wrapper.cubeList.add(new ModelBox(right_arm_wrapper, 52, 52, -3.5F, -0.5F, -2.0F, 3, 12, 4, 0.25F, false));

		left_arm_bone = new ModelRenderer(this);
		left_arm_bone.setRotationPoint(4.1667F, -20.8333F, 0.0F);
		body_bone.addChild(left_arm_bone);
		setRotationAngle(left_arm_bone, 0.0F, 0.0F, -0.2618F);
		left_arm_bone.cubeList.add(new ModelBox(left_arm_bone, 52, 28, 0.0F, 0.5F, -2.0F, 3, 12, 4, 0.0F, false));

		left_arm_wrapper = new ModelRenderer(this);
		left_arm_wrapper.setRotationPoint(0.0F, 0.0F, 0.0F);
		left_arm_bone.addChild(left_arm_wrapper);
		left_arm_wrapper.cubeList.add(new ModelBox(left_arm_wrapper, 0, 48, 0.25F, 0.5F, -2.0F, 3, 12, 4, 0.25F, false));

		inside_body_bone = new ModelRenderer(this);
		inside_body_bone.setRotationPoint(0.1667F, -14.3333F, -0.75F);
		body_bone.addChild(inside_body_bone);
		inside_body_bone.cubeList.add(new ModelBox(inside_body_bone, 0, 32, -4.0F, -6.0F, -1.25F, 8, 12, 4, 0.0F, false));

		inside_body_wrapper = new ModelRenderer(this);
		inside_body_wrapper.setRotationPoint(0.0F, 0.0F, 0.0F);
		inside_body_bone.addChild(inside_body_wrapper);
		inside_body_wrapper.cubeList.add(new ModelBox(inside_body_wrapper, 28, 28, -4.0F, -5.75F, -1.25F, 8, 12, 4, 0.25F, false));

		bottom_bone = new ModelRenderer(this);
		bottom_bone.setRotationPoint(-0.0833F, -6.3333F, 0.0F);
		body_bone.addChild(bottom_bone);
		

		right_leg_bone = new ModelRenderer(this);
		right_leg_bone.setRotationPoint(-1.75F, -2.0F, 0.0F);
		bottom_bone.addChild(right_leg_bone);
		right_leg_bone.cubeList.add(new ModelBox(right_leg_bone, 44, 12, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));

		right_leg_wrapper = new ModelRenderer(this);
		right_leg_wrapper.setRotationPoint(0.0F, 0.0F, 0.0F);
		right_leg_bone.addChild(right_leg_wrapper);
		right_leg_wrapper.cubeList.add(new ModelBox(right_leg_wrapper, 20, 44, -2.25F, 0.25F, -2.0F, 4, 12, 4, 0.25F, false));

		left_leg_bone = new ModelRenderer(this);
		left_leg_bone.setRotationPoint(2.25F, -2.0F, 0.0F);
		bottom_bone.addChild(left_leg_bone);
		left_leg_bone.cubeList.add(new ModelBox(left_leg_bone, 36, 44, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));

		left_leg_wrapper = new ModelRenderer(this);
		left_leg_wrapper.setRotationPoint(0.0F, 0.0F, 0.0F);
		left_leg_bone.addChild(left_leg_wrapper);
		left_leg_wrapper.cubeList.add(new ModelBox(left_leg_wrapper, 32, 0, -1.75F, 0.25F, -2.0F, 4, 12, 4, 0.25F, false));
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