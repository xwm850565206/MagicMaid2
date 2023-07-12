package com.xwm.magicmaid2.client.model.entity;// Made with Blockbench 4.1.3
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ModelCorpse extends ModelBase {
	private final ModelRenderer head;
	private final ModelRenderer headwear;
	private final ModelRenderer body;
	private final ModelRenderer left_arm;
	private final ModelRenderer right_arm;
	private final ModelRenderer left_leg;
	private final ModelRenderer right_leg;

	public ModelCorpse() {
		textureWidth = 64;
		textureHeight = 32;

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(head, 0.5236F, 0.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 0, 0, -4.0F, 1.5263F, -9.5F, 8, 8, 8, 0.0F, false));

		headwear = new ModelRenderer(this);
		headwear.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotationAngle(headwear, 0.5236F, 0.0F, 0.0F);
		headwear.cubeList.add(new ModelBox(headwear, 32, 0, -4.0F, 1.5263F, -9.5F, 8, 8, 8, 0.5F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 16, 16, -4.0F, 11.0F, -2.0F, 8, 12, 4, 0.0F, false));

		left_arm = new ModelRenderer(this);
		left_arm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		setRotationAngle(left_arm, -1.0712F, 0.27F, 0.1446F);
		left_arm.cubeList.add(new ModelBox(left_arm, 40, 16, 10.5274F, 2.8442F, 8.7571F, 2, 12, 2, 0.0F, true));

		right_arm = new ModelRenderer(this);
		right_arm.setRotationPoint(5.0F, 2.0F, 0.0F);
		setRotationAngle(right_arm, -1.0809F, -0.1932F, -0.102F);
		right_arm.cubeList.add(new ModelBox(right_arm, 40, 16, -12.0993F, 2.9588F, 8.7571F, 2, 12, 2, 0.0F, false));

		left_leg = new ModelRenderer(this);
		left_leg.setRotationPoint(-2.0F, 12.0F, 0.1F);
		setRotationAngle(left_leg, -1.5708F, -0.3054F, 0.0F);
		left_leg.cubeList.add(new ModelBox(left_leg, 0, 16, 3.0F, 0.0F, 9.9F, 2, 12, 2, 0.0F, true));

		right_leg = new ModelRenderer(this);
		right_leg.setRotationPoint(2.0F, 12.0F, 0.1F);
		setRotationAngle(right_leg, -1.5708F, 0.3054F, 0.0F);
		right_leg.cubeList.add(new ModelBox(right_leg, 0, 16, -5.0F, 0.0F, 9.9F, 2, 12, 2, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		head.render(f5);
		headwear.render(f5);
		body.render(f5);
		left_arm.render(f5);
		right_arm.render(f5);
		left_leg.render(f5);
		right_leg.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}