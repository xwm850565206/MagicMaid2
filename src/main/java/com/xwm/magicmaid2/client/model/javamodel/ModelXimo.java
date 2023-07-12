package com.xwm.magicmaid2.client.model.javamodel;// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelXimo extends ModelBase {
	private final ModelRenderer main;
	private final ModelRenderer head_bone;
	private final ModelRenderer ahoge;
	private final ModelRenderer hornleft;
	private final ModelRenderer bone7;
	private final ModelRenderer bone4;
	private final ModelRenderer bone5;
	private final ModelRenderer bone6;
	private final ModelRenderer hornright;
	private final ModelRenderer bone17;
	private final ModelRenderer bone27;
	private final ModelRenderer bone28;
	private final ModelRenderer bone29;
	private final ModelRenderer right_arm_bone;
	private final ModelRenderer armLeft;
	private final ModelRenderer body;
	private final ModelRenderer bone2;
	private final ModelRenderer bone3;
	private final ModelRenderer sittingRotationSkirt;
	private final ModelRenderer legLeft;
	private final ModelRenderer legRight;
	private final ModelRenderer wingRight;
	private final ModelRenderer bone8;
	private final ModelRenderer bone12;
	private final ModelRenderer bone9;
	private final ModelRenderer bone10;
	private final ModelRenderer bone13;
	private final ModelRenderer bone14;
	private final ModelRenderer bone15;
	private final ModelRenderer bone16;
	private final ModelRenderer bone11;
	private final ModelRenderer wingLeft;
	private final ModelRenderer bone18;
	private final ModelRenderer bone19;
	private final ModelRenderer bone20;
	private final ModelRenderer bone21;
	private final ModelRenderer bone22;
	private final ModelRenderer bone23;
	private final ModelRenderer bone24;
	private final ModelRenderer bone25;
	private final ModelRenderer bone26;
	private final ModelRenderer tail;
	private final ModelRenderer tailRight;
	private final ModelRenderer bone46;
	private final ModelRenderer bone47;
	private final ModelRenderer bone44;
	private final ModelRenderer bone45;
	private final ModelRenderer bone48;
	private final ModelRenderer bone49;

	public ModelXimo() {
		textureWidth = 128;
		textureHeight = 128;

		main = new ModelRenderer(this);
		main.setRotationPoint(0.0F, 24.0F, 0.0F);
		

		head_bone = new ModelRenderer(this);
		head_bone.setRotationPoint(0.0F, -18.0F, 0.0F);
		main.addChild(head_bone);
		head_bone.cubeList.add(new ModelBox(head_bone, 40, 34, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, false));

		ahoge = new ModelRenderer(this);
		ahoge.setRotationPoint(0.0F, 0.0F, 0.0F);
		head_bone.addChild(ahoge);
		ahoge.cubeList.add(new ModelBox(ahoge, 40, 86, -2.5F, -13.0F, 0.0F, 5, 5, 0, 0.0F, false));

		hornleft = new ModelRenderer(this);
		hornleft.setRotationPoint(3.0F, -7.25F, -1.0F);
		head_bone.addChild(hornleft);
		setRotationAngle(hornleft, 0.5996F, -0.1245F, 0.1796F);
		

		bone7 = new ModelRenderer(this);
		bone7.setRotationPoint(0.0F, -7.0F, 0.0F);
		hornleft.addChild(bone7);
		setRotationAngle(bone7, -0.0873F, 0.0F, 0.0F);
		bone7.cubeList.add(new ModelBox(bone7, 30, 13, -0.5F, 0.0F, -0.5F, 1, 7, 1, 0.0F, false));

		bone4 = new ModelRenderer(this);
		bone4.setRotationPoint(0.0F, -7.0F, 0.0F);
		hornleft.addChild(bone4);
		setRotationAngle(bone4, 0.0873F, 0.0F, 0.0F);
		bone4.cubeList.add(new ModelBox(bone4, 30, 13, -0.5F, 0.0F, -0.5F, 1, 7, 1, 0.0F, false));

		bone5 = new ModelRenderer(this);
		bone5.setRotationPoint(0.0F, -7.0F, 0.0F);
		hornleft.addChild(bone5);
		setRotationAngle(bone5, 0.0F, 0.0F, 0.0873F);
		bone5.cubeList.add(new ModelBox(bone5, 0, 13, -0.5F, 0.0F, -0.5F, 1, 7, 1, 0.0F, false));

		bone6 = new ModelRenderer(this);
		bone6.setRotationPoint(0.0F, -7.0F, 0.0F);
		hornleft.addChild(bone6);
		setRotationAngle(bone6, 0.0F, 0.0F, -0.0873F);
		bone6.cubeList.add(new ModelBox(bone6, 0, 13, -0.5F, 0.0F, -0.5F, 1, 7, 1, 0.0F, false));

		hornright = new ModelRenderer(this);
		hornright.setRotationPoint(-3.0F, -7.25F, -1.0F);
		head_bone.addChild(hornright);
		setRotationAngle(hornright, 0.6037F, 0.0998F, -0.1434F);
		

		bone17 = new ModelRenderer(this);
		bone17.setRotationPoint(0.0F, -7.0F, 0.0F);
		hornright.addChild(bone17);
		setRotationAngle(bone17, -0.0873F, 0.0F, 0.0F);
		bone17.cubeList.add(new ModelBox(bone17, 0, 13, -0.5F, 0.0F, -0.5F, 1, 7, 1, 0.0F, false));

		bone27 = new ModelRenderer(this);
		bone27.setRotationPoint(0.0F, -7.0F, 0.0F);
		hornright.addChild(bone27);
		setRotationAngle(bone27, 0.0873F, 0.0F, 0.0F);
		bone27.cubeList.add(new ModelBox(bone27, 30, 13, -0.5F, 0.0F, -0.5F, 1, 7, 1, 0.0F, false));

		bone28 = new ModelRenderer(this);
		bone28.setRotationPoint(0.0F, -7.0F, 0.0F);
		hornright.addChild(bone28);
		setRotationAngle(bone28, 0.0F, 0.0F, 0.0873F);
		bone28.cubeList.add(new ModelBox(bone28, 0, 13, -0.5F, 0.0F, -0.5F, 1, 7, 1, 0.0F, false));

		bone29 = new ModelRenderer(this);
		bone29.setRotationPoint(0.0F, -7.0F, 0.0F);
		hornright.addChild(bone29);
		setRotationAngle(bone29, 0.0F, 0.0F, -0.0873F);
		bone29.cubeList.add(new ModelBox(bone29, 0, 25, -0.5F, 0.0F, -0.5F, 1, 7, 1, 0.0F, false));

		right_arm_bone = new ModelRenderer(this);
		right_arm_bone.setRotationPoint(-3.0F, -17.5F, 0.0F);
		main.addChild(right_arm_bone);
		setRotationAngle(right_arm_bone, 0.0F, 0.0F, 0.4363F);
		right_arm_bone.cubeList.add(new ModelBox(right_arm_bone, 12, 74, -2.0F, 1.0F, -1.0F, 2, 8, 2, 0.0F, false));
		right_arm_bone.cubeList.add(new ModelBox(right_arm_bone, 20, 74, -2.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F, false));

		armLeft = new ModelRenderer(this);
		armLeft.setRotationPoint(3.0F, -17.5F, 0.0F);
		main.addChild(armLeft);
		setRotationAngle(armLeft, 0.0F, 0.0F, -0.4363F);
		armLeft.cubeList.add(new ModelBox(armLeft, 32, 74, 0.0F, 1.0F, -1.0F, 2, 8, 2, 0.0F, false));
		armLeft.cubeList.add(new ModelBox(armLeft, 40, 74, -0.5F, 0.0F, -1.5F, 3, 3, 3, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, -10.5F, 0.0F);
		main.addChild(body);
		body.cubeList.add(new ModelBox(body, 56, 50, -3.0F, -7.5F, -3.0F, 6, 6, 6, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 56, 53, -3.0F, -1.5F, -3.0F, 6, 3, 6, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 24, 62, -4.0F, 1.5F, -2.0F, 8, 2, 4, -0.1F, false));
		body.cubeList.add(new ModelBox(body, 38, 91, -0.5F, -7.5F, -3.6F, 1, 4, 1, 0.0F, false));

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(bone2);
		setRotationAngle(bone2, 0.0F, 0.0F, -0.4363F);
		bone2.cubeList.add(new ModelBox(bone2, 36, 97, 2.2939F, -6.1023F, -3.6F, 1, 3, 1, 0.0F, false));

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(bone3);
		setRotationAngle(bone3, 0.0F, 0.0F, 0.4363F);
		bone3.cubeList.add(new ModelBox(bone3, 32, 97, -3.2939F, -6.1023F, -3.6F, 1, 3, 1, 0.0F, false));

		sittingRotationSkirt = new ModelRenderer(this);
		sittingRotationSkirt.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(sittingRotationSkirt);
		sittingRotationSkirt.cubeList.add(new ModelBox(sittingRotationSkirt, 0, 34, -5.0F, 1.5F, -5.0F, 10, 4, 10, 0.0F, false));
		sittingRotationSkirt.cubeList.add(new ModelBox(sittingRotationSkirt, 0, 50, -4.0F, -1.5F, -4.0F, 8, 3, 8, 0.0F, false));

		legLeft = new ModelRenderer(this);
		legLeft.setRotationPoint(2.0F, -9.0F, 0.0F);
		main.addChild(legLeft);
		legLeft.cubeList.add(new ModelBox(legLeft, 64, 62, -1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F, false));

		legRight = new ModelRenderer(this);
		legRight.setRotationPoint(-2.0F, -9.0F, 0.0F);
		main.addChild(legRight);
		legRight.cubeList.add(new ModelBox(legRight, 0, 74, -1.5F, 0.0F, -1.5F, 3, 9, 3, 0.0F, false));

		wingRight = new ModelRenderer(this);
		wingRight.setRotationPoint(-1.0F, -16.0F, 4.0F);
		main.addChild(wingRight);
		setRotationAngle(wingRight, -0.1745F, -1.0472F, 0.0F);
		wingRight.cubeList.add(new ModelBox(wingRight, 44, 0, -0.5F, -8.8278F, 0.6562F, 0, 12, 22, 0.0F, false));

		bone8 = new ModelRenderer(this);
		bone8.setRotationPoint(1.0F, 17.0F, -4.0F);
		wingRight.addChild(bone8);
		setRotationAngle(bone8, 0.7854F, 0.0F, 0.0F);
		bone8.cubeList.add(new ModelBox(bone8, 60, 86, -2.0F, -10.2277F, 18.7129F, 1, 1, 4, 0.0F, false));

		bone12 = new ModelRenderer(this);
		bone12.setRotationPoint(1.0F, 17.0F, -4.0F);
		wingRight.addChild(bone12);
		setRotationAngle(bone12, 1.0472F, 0.0F, 0.0F);
		bone12.cubeList.add(new ModelBox(bone12, 50, 86, -2.0F, -5.0359F, 16.7224F, 1, 1, 4, 0.0F, false));

		bone9 = new ModelRenderer(this);
		bone9.setRotationPoint(1.0F, 17.0F, -4.0F);
		wingRight.addChild(bone9);
		setRotationAngle(bone9, 0.5236F, 0.0F, 0.0F);
		bone9.cubeList.add(new ModelBox(bone9, 20, 86, -2.0F, -15.7577F, 19.2919F, 1, 1, 4, 0.0F, false));

		bone10 = new ModelRenderer(this);
		bone10.setRotationPoint(1.0F, 17.0F, -4.0F);
		wingRight.addChild(bone10);
		setRotationAngle(bone10, 0.2618F, 0.0F, 0.0F);
		bone10.cubeList.add(new ModelBox(bone10, 10, 86, -2.0F, -21.2492F, 18.4199F, 1, 1, 4, 0.0F, false));

		bone13 = new ModelRenderer(this);
		bone13.setRotationPoint(1.0F, 17.0F, -4.0F);
		wingRight.addChild(bone13);
		setRotationAngle(bone13, 0.2618F, 0.0F, 0.0F);
		bone13.cubeList.add(new ModelBox(bone13, 42, 94, -2.0F, -20.2139F, 14.5562F, 1, 1, 2, 0.0F, false));

		bone14 = new ModelRenderer(this);
		bone14.setRotationPoint(1.0F, 17.0F, -4.0F);
		wingRight.addChild(bone14);
		setRotationAngle(bone14, -0.2618F, 0.0F, 0.0F);
		bone14.cubeList.add(new ModelBox(bone14, 42, 91, -2.0F, -25.7838F, 4.2311F, 1, 1, 2, 0.0F, false));

		bone15 = new ModelRenderer(this);
		bone15.setRotationPoint(1.0F, 17.0F, -4.0F);
		wingRight.addChild(bone15);
		setRotationAngle(bone15, -0.7854F, 0.0F, 0.0F);
		bone15.cubeList.add(new ModelBox(bone15, 18, 97, -2.0F, -25.445F, -7.4956F, 1, 1, 2, 0.0F, false));

		bone16 = new ModelRenderer(this);
		bone16.setRotationPoint(1.0F, 17.0F, -4.0F);
		wingRight.addChild(bone16);
		setRotationAngle(bone16, -1.309F, 0.0F, 0.0F);
		bone16.cubeList.add(new ModelBox(bone16, 0, 97, -2.0F, -19.2882F, -17.4818F, 1, 1, 2, 0.0F, false));

		bone11 = new ModelRenderer(this);
		bone11.setRotationPoint(1.0F, 17.0F, -4.0F);
		wingRight.addChild(bone11);
		bone11.cubeList.add(new ModelBox(bone11, 0, 62, -2.0F, -26.3278F, 16.1562F, 1, 1, 11, 0.0F, false));

		wingLeft = new ModelRenderer(this);
		wingLeft.setRotationPoint(1.0F, -16.0F, 4.0F);
		main.addChild(wingLeft);
		setRotationAngle(wingLeft, -0.1745F, 1.0472F, 0.0F);
		wingLeft.cubeList.add(new ModelBox(wingLeft, 0, 0, 0.5F, -8.8278F, 0.6562F, 0, 12, 22, 0.0F, false));

		bone18 = new ModelRenderer(this);
		bone18.setRotationPoint(1.0F, 17.0F, -4.0F);
		wingLeft.addChild(bone18);
		setRotationAngle(bone18, 0.7854F, 0.0F, 0.0F);
		bone18.cubeList.add(new ModelBox(bone18, 0, 86, -1.0F, -10.2277F, 18.7129F, 1, 1, 4, 0.0F, false));

		bone19 = new ModelRenderer(this);
		bone19.setRotationPoint(1.0F, 17.0F, -4.0F);
		wingLeft.addChild(bone19);
		setRotationAngle(bone19, 1.0472F, 0.0F, 0.0F);
		bone19.cubeList.add(new ModelBox(bone19, 30, 86, -1.0F, -5.0359F, 16.7224F, 1, 1, 4, 0.0F, false));

		bone20 = new ModelRenderer(this);
		bone20.setRotationPoint(1.0F, 17.0F, -4.0F);
		wingLeft.addChild(bone20);
		setRotationAngle(bone20, 0.5236F, 0.0F, 0.0F);
		bone20.cubeList.add(new ModelBox(bone20, 10, 91, -1.0F, -15.7577F, 19.2919F, 1, 1, 4, 0.0F, false));

		bone21 = new ModelRenderer(this);
		bone21.setRotationPoint(1.0F, 17.0F, -4.0F);
		wingLeft.addChild(bone21);
		setRotationAngle(bone21, 0.2618F, 0.0F, 0.0F);
		bone21.cubeList.add(new ModelBox(bone21, 0, 91, -1.0F, -21.2492F, 18.4199F, 1, 1, 4, 0.0F, false));

		bone22 = new ModelRenderer(this);
		bone22.setRotationPoint(1.0F, 17.0F, -4.0F);
		wingLeft.addChild(bone22);
		setRotationAngle(bone22, 0.2618F, 0.0F, 0.0F);
		bone22.cubeList.add(new ModelBox(bone22, 48, 91, -1.0F, -20.2139F, 14.5562F, 1, 1, 2, 0.0F, false));

		bone23 = new ModelRenderer(this);
		bone23.setRotationPoint(1.0F, 17.0F, -4.0F);
		wingLeft.addChild(bone23);
		setRotationAngle(bone23, -0.2618F, 0.0F, 0.0F);
		bone23.cubeList.add(new ModelBox(bone23, 32, 91, -1.0F, -25.7838F, 4.2311F, 1, 1, 2, 0.0F, false));

		bone24 = new ModelRenderer(this);
		bone24.setRotationPoint(1.0F, 17.0F, -4.0F);
		wingLeft.addChild(bone24);
		setRotationAngle(bone24, -0.7854F, 0.0F, 0.0F);
		bone24.cubeList.add(new ModelBox(bone24, 6, 97, -1.0F, -25.445F, -7.4956F, 1, 1, 2, 0.0F, false));

		bone25 = new ModelRenderer(this);
		bone25.setRotationPoint(1.0F, 17.0F, -4.0F);
		wingLeft.addChild(bone25);
		setRotationAngle(bone25, -1.309F, 0.0F, 0.0F);
		bone25.cubeList.add(new ModelBox(bone25, 12, 97, -1.0F, -19.2882F, -17.4818F, 1, 1, 2, 0.0F, false));

		bone26 = new ModelRenderer(this);
		bone26.setRotationPoint(1.0F, 17.0F, -4.0F);
		wingLeft.addChild(bone26);
		bone26.cubeList.add(new ModelBox(bone26, 32, 50, -1.0F, -26.3278F, 16.1562F, 1, 1, 11, 0.0F, false));

		tail = new ModelRenderer(this);
		tail.setRotationPoint(-0.1F, -9.7F, 1.9F);
		main.addChild(tail);
		

		tailRight = new ModelRenderer(this);
		tailRight.setRotationPoint(0.2F, 0.1F, 0.0F);
		tail.addChild(tailRight);
		setRotationAngle(tailRight, -1.0472F, -0.5236F, -0.3491F);
		

		bone46 = new ModelRenderer(this);
		bone46.setRotationPoint(0.0196F, -0.2402F, -2.7481F);
		tailRight.addChild(bone46);
		setRotationAngle(bone46, 0.5236F, 0.0F, 0.0F);
		bone46.cubeList.add(new ModelBox(bone46, 116, 91, -0.5F, 0.933F, 2.3481F, 1, 1, 3, 0.0F, false));
		bone46.cubeList.add(new ModelBox(bone46, 76, 118, -0.5F, -11.9952F, 9.8122F, 1, 1, 2, 0.0F, false));

		bone47 = new ModelRenderer(this);
		bone47.setRotationPoint(0.0196F, -0.2402F, -2.7481F);
		tailRight.addChild(bone47);
		setRotationAngle(bone47, 1.0472F, 0.0F, 0.0F);
		bone47.cubeList.add(new ModelBox(bone47, 108, 91, -0.5F, 3.3481F, 3.6651F, 1, 1, 3, 0.0F, false));
		bone47.cubeList.add(new ModelBox(bone47, 102, 115, -0.5F, -5.4821F, 12.4952F, 1, 1, 2, 0.0F, false));

		bone44 = new ModelRenderer(this);
		bone44.setRotationPoint(0.0196F, -0.2402F, -2.7481F);
		tailRight.addChild(bone44);
		setRotationAngle(bone44, 1.5708F, 0.0F, 0.0F);
		bone44.cubeList.add(new ModelBox(bone44, 97, 115, -0.5F, 6.0981F, 3.5981F, 1, 1, 3, 0.0F, false));
		bone44.cubeList.add(new ModelBox(bone44, 76, 115, -0.5F, 1.5F, 11.5622F, 1, 1, 2, 0.0F, false));

		bone45 = new ModelRenderer(this);
		bone45.setRotationPoint(0.0196F, -0.2402F, -2.7481F);
		tailRight.addChild(bone45);
		setRotationAngle(bone45, 2.0944F, 0.0F, 0.0F);
		bone45.cubeList.add(new ModelBox(bone45, 76, 107, -0.5F, 8.4461F, 2.1651F, 1, 1, 3, 0.0F, false));
		bone45.cubeList.add(new ModelBox(bone45, 76, 92, -0.5F, 7.0801F, 7.2631F, 1, 1, 2, 0.0F, false));

		bone48 = new ModelRenderer(this);
		bone48.setRotationPoint(0.0196F, -0.2402F, -2.7481F);
		tailRight.addChild(bone48);
		setRotationAngle(bone48, 2.618F, 0.0F, 0.0F);
		bone48.cubeList.add(new ModelBox(bone48, 76, 103, -0.5F, 9.7631F, -0.25F, 1, 1, 3, 0.0F, false));

		bone49 = new ModelRenderer(this);
		bone49.setRotationPoint(0.0615F, -15.7896F, 2.0151F);
		tailRight.addChild(bone49);
		setRotationAngle(bone49, 0.3491F, 0.0F, 0.0F);
		bone49.cubeList.add(new ModelBox(bone49, 96, 91, -2.0F, -0.25F, -1.25F, 4, 0, 4, 0.0F, false));
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