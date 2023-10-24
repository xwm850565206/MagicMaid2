package com.xwm.magicmaid2.client.model.javamodel;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;


public class ModelIce extends ModelBase {
	private final ModelRenderer ice4;
	private final ModelRenderer piece18;
	private final ModelRenderer bone146;
	private final ModelRenderer bone147;
	private final ModelRenderer bone148;
	private final ModelRenderer piece19;
	private final ModelRenderer bone149;
	private final ModelRenderer bone150;
	private final ModelRenderer bone151;
	private final ModelRenderer piece20;
	private final ModelRenderer bone152;
	private final ModelRenderer bone153;
	private final ModelRenderer bone154;
	private final ModelRenderer piece21;
	private final ModelRenderer bone155;
	private final ModelRenderer bone156;
	private final ModelRenderer bone157;
	private final ModelRenderer piece22;
	private final ModelRenderer bone158;
	private final ModelRenderer bone159;
	private final ModelRenderer bone160;
	private final ModelRenderer piece23;
	private final ModelRenderer bone161;
	private final ModelRenderer bone162;
	private final ModelRenderer bone163;
	private final ModelRenderer piece24;
	private final ModelRenderer bone164;
	private final ModelRenderer bone165;
	private final ModelRenderer bone166;
	private final ModelRenderer piece33;
	private final ModelRenderer bone167;
	private final ModelRenderer bone168;
	private final ModelRenderer bone169;

	public ModelIce() {
		textureWidth = 32;
		textureHeight = 32;

		ice4 = new ModelRenderer(this);
		ice4.setRotationPoint(-0.5263F, -0.9846F, -0.2854F);
		setRotationAngle(ice4, -1.5708F, 0.0F, 0.0F);
		ice4.cubeList.add(new ModelBox(ice4, 15, 0, -0.5F, 4.8014F, -0.4995F, 1, 0, 1, 0.0F, false));
		ice4.cubeList.add(new ModelBox(ice4, 0, 15, -0.5F, -4.8014F, -0.5005F, 1, 0, 1, 0.0F, false));

		piece18 = new ModelRenderer(this);
		piece18.setRotationPoint(4.5263F, -11.2854F, -12.0154F);
		ice4.addChild(piece18);


		bone146 = new ModelRenderer(this);
		bone146.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece18.addChild(bone146);
		setRotationAngle(bone146, -0.2007F, 0.0F, 0.0F);
		bone146.cubeList.add(new ModelBox(bone146, 14, 10, -5.0263F, 8.2676F, 15.4706F, 1, 5, 0, 0.0F, false));

		bone147 = new ModelRenderer(this);
		bone147.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece18.addChild(bone147);
		setRotationAngle(bone147, -0.2007F, 0.0F, 0.2007F);
		bone147.cubeList.add(new ModelBox(bone147, 14, 5, -1.7385F, 8.7378F, 15.5662F, 1, 5, 0, 0.0F, false));

		bone148 = new ModelRenderer(this);
		bone148.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece18.addChild(bone148);
		setRotationAngle(bone148, -0.2007F, 0.0F, -0.2007F);
		bone148.cubeList.add(new ModelBox(bone148, 14, 0, -8.1324F, 6.9692F, 15.2064F, 1, 5, 0, 0.0F, false));

		piece19 = new ModelRenderer(this);
		piece19.setRotationPoint(4.5263F, -11.2854F, -12.0154F);
		ice4.addChild(piece19);
		setRotationAngle(piece19, 0.0F, -1.5708F, 0.0F);


		bone149 = new ModelRenderer(this);
		bone149.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece19.addChild(bone149);
		setRotationAngle(bone149, -0.2007F, 0.0F, 0.0F);
		bone149.cubeList.add(new ModelBox(bone149, 12, 10, 11.5359F, 9.7648F, 8.1128F, 1, 5, 0, 0.0F, false));

		bone150 = new ModelRenderer(this);
		bone150.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece19.addChild(bone150);
		setRotationAngle(bone150, -0.2007F, 0.0F, 0.2007F);
		bone150.cubeList.add(new ModelBox(bone150, 12, 5, 14.4912F, 6.9992F, 7.5491F, 1, 5, 0, 0.0F, false));

		bone151 = new ModelRenderer(this);
		bone151.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece19.addChild(bone151);
		setRotationAngle(bone151, -0.2007F, 0.0F, -0.2007F);
		bone151.cubeList.add(new ModelBox(bone151, 12, 0, 8.0972F, 11.702F, 8.5069F, 1, 5, 0, 0.0F, false));

		piece20 = new ModelRenderer(this);
		piece20.setRotationPoint(4.5263F, -11.2854F, -12.0154F);
		ice4.addChild(piece20);
		setRotationAngle(piece20, 0.0F, 3.1416F, 0.0F);


		bone152 = new ModelRenderer(this);
		bone152.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece20.addChild(bone152);
		setRotationAngle(bone152, -0.2007F, 0.0F, 0.0F);
		bone152.cubeList.add(new ModelBox(bone152, 10, 10, 4.0272F, 13.0669F, -8.1169F, 1, 5, 0, 0.0F, false));

		bone153 = new ModelRenderer(this);
		bone153.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece20.addChild(bone153);
		setRotationAngle(bone153, -0.2007F, 0.0F, 0.2007F);
		bone153.cubeList.add(new ModelBox(bone153, 8, 10, 7.1334F, 11.7683F, -8.3811F, 1, 5, 0, 0.0F, false));

		bone154 = new ModelRenderer(this);
		bone154.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece20.addChild(bone154);
		setRotationAngle(bone154, -0.2007F, 0.0F, -0.2007F);
		bone154.cubeList.add(new ModelBox(bone154, 6, 10, 0.7393F, 13.5372F, -8.0212F, 1, 5, 0, 0.0F, false));

		piece21 = new ModelRenderer(this);
		piece21.setRotationPoint(4.5263F, -11.2854F, -12.0154F);
		ice4.addChild(piece21);
		setRotationAngle(piece21, 0.0F, 1.5708F, 0.0F);


		bone155 = new ModelRenderer(this);
		bone155.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece21.addChild(bone155);
		setRotationAngle(bone155, -0.2007F, 0.0F, 0.0F);
		bone155.cubeList.add(new ModelBox(bone155, 10, 5, -12.5359F, 11.5699F, -0.7589F, 1, 5, 0, 0.0F, false));

		bone156 = new ModelRenderer(this);
		bone156.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece21.addChild(bone156);
		setRotationAngle(bone156, -0.2007F, 0.0F, 0.2007F);
		bone156.cubeList.add(new ModelBox(bone156, 4, 10, -9.0972F, 13.5072F, -0.3658F, 1, 5, 0, 0.0F, false));

		bone157 = new ModelRenderer(this);
		bone157.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece21.addChild(bone157);
		setRotationAngle(bone157, -0.2007F, 0.0F, -0.2007F);
		bone157.cubeList.add(new ModelBox(bone157, 2, 10, -15.4912F, 8.8044F, -1.3226F, 1, 5, 0, 0.0F, false));

		piece22 = new ModelRenderer(this);
		piece22.setRotationPoint(4.5263F, -15.2854F, -12.0154F);
		ice4.addChild(piece22);


		bone158 = new ModelRenderer(this);
		bone158.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece22.addChild(bone158);
		setRotationAngle(bone158, -0.2007F, 0.0F, 0.0F);
		bone158.cubeList.add(new ModelBox(bone158, 10, 0, -5.0272F, 7.9695F, 13.413F, 1, 5, 0, 0.0F, false));

		bone159 = new ModelRenderer(this);
		bone159.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece22.addChild(bone159);
		setRotationAngle(bone159, -0.2007F, 0.0F, 0.2007F);
		bone159.cubeList.add(new ModelBox(bone159, 0, 10, -2.8362F, 8.7454F, 13.5709F, 1, 5, 0, 0.0F, false));

		bone160 = new ModelRenderer(this);
		bone160.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece22.addChild(bone160);
		setRotationAngle(bone160, -0.2007F, 0.0F, -0.2007F);
		bone160.cubeList.add(new ModelBox(bone160, 8, 5, -7.0365F, 6.9765F, 13.211F, 1, 5, 0, 0.0F, false));

		piece23 = new ModelRenderer(this);
		piece23.setRotationPoint(4.5263F, -18.2854F, -9.0154F);
		ice4.addChild(piece23);
		setRotationAngle(piece23, 0.0F, 3.1416F, 0.0F);


		bone161 = new ModelRenderer(this);
		bone161.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece23.addChild(bone161);
		setRotationAngle(bone161, -0.2007F, 0.0F, 0.0F);
		bone161.cubeList.add(new ModelBox(bone161, 8, 0, 4.0263F, 15.1093F, -6.6358F, 1, 5, 0, 0.0F, false));

		bone162 = new ModelRenderer(this);
		bone162.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece23.addChild(bone162);
		setRotationAngle(bone162, -0.2007F, 0.0F, 0.2007F);
		bone162.cubeList.add(new ModelBox(bone162, 6, 5, 6.6335F, 14.0575F, -6.8498F, 1, 5, 0, 0.0F, false));

		bone163 = new ModelRenderer(this);
		bone163.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece23.addChild(bone163);
		setRotationAngle(bone163, -0.2007F, 0.0F, -0.2007F);
		bone163.cubeList.add(new ModelBox(bone163, 6, 0, 1.2373F, 15.8265F, -6.4909F, 1, 5, 0, 0.0F, false));

		piece24 = new ModelRenderer(this);
		piece24.setRotationPoint(4.5263F, -18.2854F, -12.0154F);
		ice4.addChild(piece24);
		setRotationAngle(piece24, 0.0F, -1.5708F, 0.0F);


		bone164 = new ModelRenderer(this);
		bone164.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece24.addChild(bone164);
		setRotationAngle(bone164, -0.2007F, 0.0F, 0.0F);
		bone164.cubeList.add(new ModelBox(bone164, 4, 5, 11.5359F, 12.4059F, 6.6541F, 1, 5, 0, 0.0F, false));

		bone165 = new ModelRenderer(this);
		bone165.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece24.addChild(bone165);
		setRotationAngle(bone165, -0.2007F, 0.0F, 0.2007F);
		bone165.cubeList.add(new ModelBox(bone165, 2, 5, 13.9924F, 9.8869F, 6.1416F, 1, 5, 0, 0.0F, false));

		bone166 = new ModelRenderer(this);
		bone166.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece24.addChild(bone166);
		setRotationAngle(bone166, -0.2007F, 0.0F, -0.2007F);
		bone166.cubeList.add(new ModelBox(bone166, 0, 5, 8.596F, 14.5897F, 7.0984F, 1, 5, 0, 0.0F, false));

		piece33 = new ModelRenderer(this);
		piece33.setRotationPoint(-1.4737F, -18.2854F, -12.0154F);
		ice4.addChild(piece33);
		setRotationAngle(piece33, 0.0F, 1.5708F, 0.0F);


		bone167 = new ModelRenderer(this);
		bone167.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece33.addChild(bone167);
		setRotationAngle(bone167, -0.2007F, 0.0F, 0.0F);
		bone167.cubeList.add(new ModelBox(bone167, 4, 0, -12.5358F, 13.0145F, 3.6619F, 1, 5, 0, 0.0F, false));

		bone168 = new ModelRenderer(this);
		bone168.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece33.addChild(bone168);
		setRotationAngle(bone168, -0.2007F, 0.0F, 0.2007F);
		bone168.cubeList.add(new ModelBox(bone168, 2, 0, -9.5961F, 15.1983F, 4.1062F, 1, 5, 0, 0.0F, false));

		bone169 = new ModelRenderer(this);
		bone169.setRotationPoint(0.0F, 0.0F, 0.0F);
		piece33.addChild(bone169);
		setRotationAngle(bone169, -0.2007F, 0.0F, -0.2007F);
		bone169.cubeList.add(new ModelBox(bone169, 0, 0, -14.9924F, 10.4955F, 3.1494F, 1, 5, 0, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		ice4.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}