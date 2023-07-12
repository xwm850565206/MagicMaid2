package com.xwm.magicmaid2.client.model.javamodel;// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMaier extends ModelBase {
	private final ModelRenderer main;
	private final ModelRenderer head_bone;
	private final ModelRenderer hair;
	private final ModelRenderer bone40;
	private final ModelRenderer bone39;
	private final ModelRenderer hat;
	private final ModelRenderer hat2;
	private final ModelRenderer bone156;
	private final ModelRenderer bone155;
	private final ModelRenderer bone153;
	private final ModelRenderer bone154;
	private final ModelRenderer bone157;
	private final ModelRenderer bone158;
	private final ModelRenderer bone159;
	private final ModelRenderer bone160;
	private final ModelRenderer bone161;
	private final ModelRenderer bone162;
	private final ModelRenderer bone163;
	private final ModelRenderer bone164;
	private final ModelRenderer bone165;
	private final ModelRenderer bone166;
	private final ModelRenderer bone167;
	private final ModelRenderer bone168;
	private final ModelRenderer bone169;
	private final ModelRenderer bone170;
	private final ModelRenderer bone171;
	private final ModelRenderer bone172;
	private final ModelRenderer bone173;
	private final ModelRenderer bone174;
	private final ModelRenderer bone175;
	private final ModelRenderer bone176;
	private final ModelRenderer bone177;
	private final ModelRenderer bone178;
	private final ModelRenderer bone179;
	private final ModelRenderer bone180;
	private final ModelRenderer bone181;
	private final ModelRenderer bone182;
	private final ModelRenderer bone183;
	private final ModelRenderer bone184;
	private final ModelRenderer bone41;
	private final ModelRenderer bone42;
	private final ModelRenderer bone43;
	private final ModelRenderer bone44;
	private final ModelRenderer bone45;
	private final ModelRenderer bone46;
	private final ModelRenderer bone47;
	private final ModelRenderer bone48;
	private final ModelRenderer bone49;
	private final ModelRenderer bone50;
	private final ModelRenderer bone51;
	private final ModelRenderer bone52;
	private final ModelRenderer bone53;
	private final ModelRenderer bone54;
	private final ModelRenderer bone55;
	private final ModelRenderer bone56;
	private final ModelRenderer body;
	private final ModelRenderer bone66;
	private final ModelRenderer bone67;
	private final ModelRenderer bone68;
	private final ModelRenderer bone69;
	private final ModelRenderer bone70;
	private final ModelRenderer bone71;
	private final ModelRenderer bone73;
	private final ModelRenderer bone74;
	private final ModelRenderer bone75;
	private final ModelRenderer cape;
	private final ModelRenderer capeInner;
	private final ModelRenderer cape7;
	private final ModelRenderer cape8;
	private final ModelRenderer cape9;
	private final ModelRenderer cape10;
	private final ModelRenderer capeOutter;
	private final ModelRenderer cape2;
	private final ModelRenderer cape3;
	private final ModelRenderer cape4;
	private final ModelRenderer cape5;
	private final ModelRenderer armLeft;
	private final ModelRenderer right_arm_bone;
	private final ModelRenderer legLeft;
	private final ModelRenderer legRight;

	public ModelMaier() {
		textureWidth = 128;
		textureHeight = 128;

		main = new ModelRenderer(this);
		main.setRotationPoint(0.0F, 24.0F, 0.0F);
		

		head_bone = new ModelRenderer(this);
		head_bone.setRotationPoint(0.0F, -18.0F, 0.0F);
		main.addChild(head_bone);
		head_bone.cubeList.add(new ModelBox(head_bone, 45, 0, -4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F, false));
		head_bone.cubeList.add(new ModelBox(head_bone, 38, 43, -4.0F, -8.0F, -4.0F, 8, 9, 8, 0.2F, false));
		head_bone.cubeList.add(new ModelBox(head_bone, 0, 96, -8.0F, -11.9F, -8.3F, 16, 16, 16, -4.2F, false));

		hair = new ModelRenderer(this);
		hair.setRotationPoint(0.0F, 0.0F, 0.0F);
		head_bone.addChild(hair);
		

		bone40 = new ModelRenderer(this);
		bone40.setRotationPoint(-3.5F, -0.1F, -3.5F);
		hair.addChild(bone40);
		setRotationAngle(bone40, -0.0873F, 0.0F, 0.0873F);
		bone40.cubeList.add(new ModelBox(bone40, 0, 33, -0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F, false));

		bone39 = new ModelRenderer(this);
		bone39.setRotationPoint(3.5F, -0.1F, -3.5F);
		hair.addChild(bone39);
		setRotationAngle(bone39, -0.0873F, 0.0F, -0.0873F);
		bone39.cubeList.add(new ModelBox(bone39, 0, 15, -0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F, false));

		hat = new ModelRenderer(this);
		hat.setRotationPoint(0.0F, -6.7F, 0.0F);
		head_bone.addChild(hat);
		hat.cubeList.add(new ModelBox(hat, 0, 0, -7.5F, 0.0F, -7.5F, 15, 0, 15, 0.0F, false));
		hat.cubeList.add(new ModelBox(hat, 38, 35, -4.0F, -2.75F, -4.0F, 8, 0, 8, 0.0F, false));

		hat2 = new ModelRenderer(this);
		hat2.setRotationPoint(0.0F, -2.1F, 0.0F);
		hat.addChild(hat2);
		hat2.cubeList.add(new ModelBox(hat2, 7, 0, -1.0F, -2.3895F, -1.0F, 2, 0, 2, 0.45F, false));

		bone156 = new ModelRenderer(this);
		bone156.setRotationPoint(0.0F, 0.0F, 0.0F);
		hat2.addChild(bone156);
		bone156.cubeList.add(new ModelBox(bone156, 70, 56, -1.5F, -0.5F, 1.6213F, 3, 1, 2, 0.0F, false));

		bone155 = new ModelRenderer(this);
		bone155.setRotationPoint(0.0F, 0.0F, 2.4107F);
		bone156.addChild(bone155);
		setRotationAngle(bone155, 0.1309F, 0.0F, 0.0F);
		

		bone153 = new ModelRenderer(this);
		bone153.setRotationPoint(0.0F, 0.054F, 0.0F);
		bone155.addChild(bone153);
		setRotationAngle(bone153, 0.0F, 0.0F, 0.054F);
		bone153.cubeList.add(new ModelBox(bone153, 0, 60, -1.0F, -3.0F, -1.0F, 1, 3, 1, 0.0F, false));

		bone154 = new ModelRenderer(this);
		bone154.setRotationPoint(0.0F, 0.054F, 0.0F);
		bone155.addChild(bone154);
		setRotationAngle(bone154, 0.0F, 0.0F, -0.054F);
		bone154.cubeList.add(new ModelBox(bone154, 0, 60, 0.0F, -3.0F, -1.0F, 1, 3, 1, 0.0F, false));

		bone157 = new ModelRenderer(this);
		bone157.setRotationPoint(0.0F, 0.0F, 0.0F);
		hat2.addChild(bone157);
		setRotationAngle(bone157, 0.0F, -0.7854F, 0.0F);
		bone157.cubeList.add(new ModelBox(bone157, 70, 25, -1.5F, -0.5F, 1.6213F, 3, 1, 2, 0.0F, false));

		bone158 = new ModelRenderer(this);
		bone158.setRotationPoint(0.0F, 0.0F, 2.4107F);
		bone157.addChild(bone158);
		setRotationAngle(bone158, 0.1309F, 0.0F, 0.0F);
		

		bone159 = new ModelRenderer(this);
		bone159.setRotationPoint(0.0F, 0.054F, 0.0F);
		bone158.addChild(bone159);
		setRotationAngle(bone159, 0.0F, 0.0F, 0.054F);
		bone159.cubeList.add(new ModelBox(bone159, 0, 60, -1.0F, -3.0F, -1.0F, 1, 3, 1, 0.0F, false));

		bone160 = new ModelRenderer(this);
		bone160.setRotationPoint(0.0F, 0.054F, 0.0F);
		bone158.addChild(bone160);
		setRotationAngle(bone160, 0.0F, 0.0F, -0.054F);
		bone160.cubeList.add(new ModelBox(bone160, 0, 60, 0.0F, -3.0F, -1.0F, 1, 3, 1, 0.0F, false));

		bone161 = new ModelRenderer(this);
		bone161.setRotationPoint(0.0F, 0.0F, 0.0F);
		hat2.addChild(bone161);
		setRotationAngle(bone161, 0.0F, -1.5708F, 0.0F);
		bone161.cubeList.add(new ModelBox(bone161, 26, 64, -1.5F, -0.5F, 1.6213F, 3, 1, 2, 0.0F, false));

		bone162 = new ModelRenderer(this);
		bone162.setRotationPoint(0.0F, 0.0F, 2.4107F);
		bone161.addChild(bone162);
		setRotationAngle(bone162, 0.1309F, 0.0F, 0.0F);
		

		bone163 = new ModelRenderer(this);
		bone163.setRotationPoint(0.0F, 0.054F, 0.0F);
		bone162.addChild(bone163);
		setRotationAngle(bone163, 0.0F, 0.0F, 0.054F);
		bone163.cubeList.add(new ModelBox(bone163, 0, 60, -1.0F, -3.0F, -1.0F, 1, 3, 1, 0.0F, false));

		bone164 = new ModelRenderer(this);
		bone164.setRotationPoint(0.0F, 0.054F, 0.0F);
		bone162.addChild(bone164);
		setRotationAngle(bone164, 0.0F, 0.0F, -0.054F);
		bone164.cubeList.add(new ModelBox(bone164, 0, 60, 0.0F, -3.0F, -1.0F, 1, 3, 1, 0.0F, false));

		bone165 = new ModelRenderer(this);
		bone165.setRotationPoint(0.0F, 0.0F, 0.0F);
		hat2.addChild(bone165);
		setRotationAngle(bone165, 0.0F, -2.3562F, 0.0F);
		bone165.cubeList.add(new ModelBox(bone165, 18, 63, -1.5F, -0.5F, 1.6213F, 3, 1, 2, 0.0F, false));

		bone166 = new ModelRenderer(this);
		bone166.setRotationPoint(0.0F, 0.0F, 2.4107F);
		bone165.addChild(bone166);
		setRotationAngle(bone166, 0.1309F, 0.0F, 0.0F);
		

		bone167 = new ModelRenderer(this);
		bone167.setRotationPoint(0.0F, 0.054F, 0.0F);
		bone166.addChild(bone167);
		setRotationAngle(bone167, 0.0F, 0.0F, 0.054F);
		bone167.cubeList.add(new ModelBox(bone167, 0, 60, -1.0F, -3.0F, -1.0F, 1, 3, 1, 0.0F, false));

		bone168 = new ModelRenderer(this);
		bone168.setRotationPoint(0.0F, 0.054F, 0.0F);
		bone166.addChild(bone168);
		setRotationAngle(bone168, 0.0F, 0.0F, -0.054F);
		bone168.cubeList.add(new ModelBox(bone168, 0, 60, 0.0F, -3.0F, -1.0F, 1, 3, 1, 0.0F, false));

		bone169 = new ModelRenderer(this);
		bone169.setRotationPoint(0.0F, 0.0F, 0.0F);
		hat2.addChild(bone169);
		setRotationAngle(bone169, 0.0F, 3.1416F, 0.0F);
		bone169.cubeList.add(new ModelBox(bone169, 52, 62, -1.5F, -0.5F, 1.6213F, 3, 1, 2, 0.0F, false));

		bone170 = new ModelRenderer(this);
		bone170.setRotationPoint(0.0F, 0.0F, 2.4107F);
		bone169.addChild(bone170);
		setRotationAngle(bone170, 0.1309F, 0.0F, 0.0F);
		

		bone171 = new ModelRenderer(this);
		bone171.setRotationPoint(0.0F, 0.054F, 0.0F);
		bone170.addChild(bone171);
		setRotationAngle(bone171, 0.0F, 0.0F, 0.054F);
		bone171.cubeList.add(new ModelBox(bone171, 0, 60, -1.0F, -3.0F, -1.0F, 1, 3, 1, 0.0F, false));

		bone172 = new ModelRenderer(this);
		bone172.setRotationPoint(0.0F, 0.054F, 0.0F);
		bone170.addChild(bone172);
		setRotationAngle(bone172, 0.0F, 0.0F, -0.054F);
		bone172.cubeList.add(new ModelBox(bone172, 0, 60, 0.0F, -3.0F, -1.0F, 1, 3, 1, 0.0F, false));

		bone173 = new ModelRenderer(this);
		bone173.setRotationPoint(0.0F, 0.0F, 0.0F);
		hat2.addChild(bone173);
		setRotationAngle(bone173, 0.0F, 2.3562F, 0.0F);
		bone173.cubeList.add(new ModelBox(bone173, 62, 48, -1.5F, -0.5F, 1.6213F, 3, 1, 2, 0.0F, false));

		bone174 = new ModelRenderer(this);
		bone174.setRotationPoint(0.0F, 0.0F, 2.4107F);
		bone173.addChild(bone174);
		setRotationAngle(bone174, 0.1309F, 0.0F, 0.0F);
		

		bone175 = new ModelRenderer(this);
		bone175.setRotationPoint(0.0F, 0.054F, 0.0F);
		bone174.addChild(bone175);
		setRotationAngle(bone175, 0.0F, 0.0F, 0.054F);
		bone175.cubeList.add(new ModelBox(bone175, 0, 60, -1.0F, -3.0F, -1.0F, 1, 3, 1, 0.0F, false));

		bone176 = new ModelRenderer(this);
		bone176.setRotationPoint(0.0F, 0.054F, 0.0F);
		bone174.addChild(bone176);
		setRotationAngle(bone176, 0.0F, 0.0F, -0.054F);
		bone176.cubeList.add(new ModelBox(bone176, 0, 60, 0.0F, -3.0F, -1.0F, 1, 3, 1, 0.0F, false));

		bone177 = new ModelRenderer(this);
		bone177.setRotationPoint(0.0F, 0.0F, 0.0F);
		hat2.addChild(bone177);
		setRotationAngle(bone177, 0.0F, 1.5708F, 0.0F);
		bone177.cubeList.add(new ModelBox(bone177, 60, 25, -1.5F, -0.5F, 1.6213F, 3, 1, 2, 0.0F, false));

		bone178 = new ModelRenderer(this);
		bone178.setRotationPoint(0.0F, 0.0F, 2.4107F);
		bone177.addChild(bone178);
		setRotationAngle(bone178, 0.1309F, 0.0F, 0.0F);
		

		bone179 = new ModelRenderer(this);
		bone179.setRotationPoint(0.0F, 0.054F, 0.0F);
		bone178.addChild(bone179);
		setRotationAngle(bone179, 0.0F, 0.0F, 0.054F);
		bone179.cubeList.add(new ModelBox(bone179, 0, 60, -1.0F, -3.0F, -1.0F, 1, 3, 1, 0.0F, false));

		bone180 = new ModelRenderer(this);
		bone180.setRotationPoint(0.0F, 0.054F, 0.0F);
		bone178.addChild(bone180);
		setRotationAngle(bone180, 0.0F, 0.0F, -0.054F);
		bone180.cubeList.add(new ModelBox(bone180, 0, 60, 0.0F, -3.0F, -1.0F, 1, 3, 1, 0.0F, false));

		bone181 = new ModelRenderer(this);
		bone181.setRotationPoint(0.0F, 0.0F, 0.0F);
		hat2.addChild(bone181);
		setRotationAngle(bone181, 0.0F, 0.7854F, 0.0F);
		bone181.cubeList.add(new ModelBox(bone181, 40, 17, -1.5F, -0.5F, 1.6213F, 3, 1, 2, 0.0F, false));

		bone182 = new ModelRenderer(this);
		bone182.setRotationPoint(0.0F, 0.0F, 2.4107F);
		bone181.addChild(bone182);
		setRotationAngle(bone182, 0.1309F, 0.0F, 0.0F);
		

		bone183 = new ModelRenderer(this);
		bone183.setRotationPoint(0.0F, 0.054F, 0.0F);
		bone182.addChild(bone183);
		setRotationAngle(bone183, 0.0F, 0.0F, 0.054F);
		bone183.cubeList.add(new ModelBox(bone183, 0, 60, -1.0F, -3.0F, -1.0F, 1, 3, 1, 0.0F, false));

		bone184 = new ModelRenderer(this);
		bone184.setRotationPoint(0.0F, 0.054F, 0.0F);
		bone182.addChild(bone184);
		setRotationAngle(bone184, 0.0F, 0.0F, -0.054F);
		bone184.cubeList.add(new ModelBox(bone184, 0, 60, 0.0F, -3.0F, -1.0F, 1, 3, 1, 0.0F, false));

		bone41 = new ModelRenderer(this);
		bone41.setRotationPoint(0.0F, 0.0F, 0.0F);
		hat.addChild(bone41);
		

		bone42 = new ModelRenderer(this);
		bone42.setRotationPoint(0.0F, 0.0F, 4.4848F);
		bone41.addChild(bone42);
		setRotationAngle(bone42, 0.1773F, 0.0F, 0.0F);
		bone42.cubeList.add(new ModelBox(bone42, 18, 60, -3.5F, -2.8264F, 0.0002F, 7, 3, 0, 0.0F, false));

		bone43 = new ModelRenderer(this);
		bone43.setRotationPoint(-3.5F, 0.1736F, 0.0F);
		bone42.addChild(bone43);
		setRotationAngle(bone43, 0.0F, 0.0F, 0.1745F);
		bone43.cubeList.add(new ModelBox(bone43, 4, 36, -1.0F, -3.0F, 0.0F, 1, 3, 0, 0.0F, false));

		bone44 = new ModelRenderer(this);
		bone44.setRotationPoint(3.5F, 0.1736F, 0.0F);
		bone42.addChild(bone44);
		setRotationAngle(bone44, 0.0F, 0.0F, -0.1745F);
		bone44.cubeList.add(new ModelBox(bone44, 4, 33, 0.0F, -3.0F, 0.0F, 1, 3, 0, 0.0F, false));

		bone45 = new ModelRenderer(this);
		bone45.setRotationPoint(0.0F, 0.0F, 0.0F);
		hat.addChild(bone45);
		setRotationAngle(bone45, 0.0F, -1.5708F, 0.0F);
		

		bone46 = new ModelRenderer(this);
		bone46.setRotationPoint(0.0F, 0.0F, 4.4848F);
		bone45.addChild(bone46);
		setRotationAngle(bone46, 0.1773F, 0.0F, 0.0F);
		bone46.cubeList.add(new ModelBox(bone46, 52, 60, -3.5F, -2.8264F, 0.0002F, 7, 3, 0, 0.0F, false));

		bone47 = new ModelRenderer(this);
		bone47.setRotationPoint(-3.5F, 0.1736F, 0.0F);
		bone46.addChild(bone47);
		setRotationAngle(bone47, 0.0F, 0.0F, 0.1745F);
		bone47.cubeList.add(new ModelBox(bone47, 4, 18, -1.0F, -3.0F, 0.0F, 1, 3, 0, 0.0F, false));

		bone48 = new ModelRenderer(this);
		bone48.setRotationPoint(3.5F, 0.1736F, 0.0F);
		bone46.addChild(bone48);
		setRotationAngle(bone48, 0.0F, 0.0F, -0.1745F);
		bone48.cubeList.add(new ModelBox(bone48, 4, 15, 0.0F, -3.0F, 0.0F, 1, 3, 0, 0.0F, false));

		bone49 = new ModelRenderer(this);
		bone49.setRotationPoint(0.0F, 0.0F, 0.0F);
		hat.addChild(bone49);
		setRotationAngle(bone49, 0.0F, -3.1416F, 0.0F);
		

		bone50 = new ModelRenderer(this);
		bone50.setRotationPoint(0.0F, 0.0F, 4.4848F);
		bone49.addChild(bone50);
		setRotationAngle(bone50, 0.1773F, 0.0F, 0.0F);
		bone50.cubeList.add(new ModelBox(bone50, 46, 25, -3.5F, -2.8264F, 0.0002F, 7, 3, 0, 0.0F, false));

		bone51 = new ModelRenderer(this);
		bone51.setRotationPoint(-3.5F, 0.1736F, 0.0F);
		bone50.addChild(bone51);
		setRotationAngle(bone51, 0.0F, 0.0F, 0.1745F);
		bone51.cubeList.add(new ModelBox(bone51, 12, 8, -1.0F, -3.0F, 0.0F, 1, 3, 0, 0.0F, false));

		bone52 = new ModelRenderer(this);
		bone52.setRotationPoint(3.5F, 0.1736F, 0.0F);
		bone50.addChild(bone52);
		setRotationAngle(bone52, 0.0F, 0.0F, -0.1745F);
		bone52.cubeList.add(new ModelBox(bone52, 12, 5, 0.0F, -3.0F, 0.0F, 1, 3, 0, 0.0F, false));

		bone53 = new ModelRenderer(this);
		bone53.setRotationPoint(0.0F, 0.0F, 0.0F);
		hat.addChild(bone53);
		setRotationAngle(bone53, 0.0F, -4.7124F, 0.0F);
		

		bone54 = new ModelRenderer(this);
		bone54.setRotationPoint(0.0F, 0.0F, 4.4848F);
		bone53.addChild(bone54);
		setRotationAngle(bone54, 0.1773F, 0.0F, 0.0F);
		bone54.cubeList.add(new ModelBox(bone54, 0, 12, -3.5F, -2.8264F, 0.0002F, 7, 3, 0, 0.0F, false));

		bone55 = new ModelRenderer(this);
		bone55.setRotationPoint(-3.5F, 0.1736F, 0.0F);
		bone54.addChild(bone55);
		setRotationAngle(bone55, 0.0F, 0.0F, 0.1745F);
		bone55.cubeList.add(new ModelBox(bone55, 12, 2, -1.0F, -3.0F, 0.0F, 1, 3, 0, 0.0F, false));

		bone56 = new ModelRenderer(this);
		bone56.setRotationPoint(3.5F, 0.1736F, 0.0F);
		bone54.addChild(bone56);
		setRotationAngle(bone56, 0.0F, 0.0F, -0.1745F);
		bone56.cubeList.add(new ModelBox(bone56, 0, 0, 0.0F, -3.0F, 0.0F, 1, 3, 0, 0.0F, false));

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, -10.5F, 0.0F);
		main.addChild(body);
		body.cubeList.add(new ModelBox(body, 54, 69, -3.0F, -7.499F, -3.0F, 6, 9, 6, -0.1F, false));
		body.cubeList.add(new ModelBox(body, 0, 98, -3.0F, -7.499F, -3.0F, 6, 9, 6, 0.0F, false));

		bone66 = new ModelRenderer(this);
		bone66.setRotationPoint(0.85F, -1.249F, 0.5F);
		body.addChild(bone66);
		setRotationAngle(bone66, 0.0F, 0.0F, 0.1745F);
		bone66.cubeList.add(new ModelBox(bone66, 39, 86, -4.0F, -0.5F, -3.501F, 4, 9, 6, 0.0F, false));

		bone67 = new ModelRenderer(this);
		bone67.setRotationPoint(-0.6709F, 5.3034F, 6.25F);
		bone66.addChild(bone67);
		bone67.cubeList.add(new ModelBox(bone67, 96, 1, -0.25F, -2.3F, -10.0F, 1, 1, 1, -0.2F, false));

		bone68 = new ModelRenderer(this);
		bone68.setRotationPoint(-0.6709F, 5.3034F, 6.25F);
		bone66.addChild(bone68);
		bone68.cubeList.add(new ModelBox(bone68, 99, 0, -0.25F, -0.6F, -10.0F, 1, 1, 1, -0.2F, false));

		bone69 = new ModelRenderer(this);
		bone69.setRotationPoint(-0.85F, -1.249F, 0.5F);
		body.addChild(bone69);
		setRotationAngle(bone69, 0.0F, 0.0F, -0.1745F);
		bone69.cubeList.add(new ModelBox(bone69, 19, 86, 0.0F, -0.5F, -3.502F, 4, 9, 6, 0.0F, false));

		bone70 = new ModelRenderer(this);
		bone70.setRotationPoint(0.1F, -2.45F, 0.0F);
		body.addChild(bone70);
		setRotationAngle(bone70, 0.0F, 0.0F, -0.2618F);
		bone70.cubeList.add(new ModelBox(bone70, 70, 49, -3.5F, -0.5F, -3.5F, 7, 1, 7, 0.0F, false));
		bone70.cubeList.add(new ModelBox(bone70, 90, 44, -2.5F, -1.0F, -4.0F, 2, 2, 2, -0.3F, false));

		bone71 = new ModelRenderer(this);
		bone71.setRotationPoint(0.7F, -5.0F, 6.75F);
		body.addChild(bone71);
		bone71.cubeList.add(new ModelBox(bone71, 96, 1, -1.0F, 0.8F, -10.0F, 1, 1, 1, -0.2F, false));

		bone73 = new ModelRenderer(this);
		bone73.setRotationPoint(0.7F, -1.8F, 6.75F);
		body.addChild(bone73);
		bone73.cubeList.add(new ModelBox(bone73, 96, 1, -1.0F, -1.0F, -10.0F, 1, 1, 1, -0.2F, false));

		bone74 = new ModelRenderer(this);
		bone74.setRotationPoint(0.7F, -0.5F, 6.75F);
		body.addChild(bone74);
		bone74.cubeList.add(new ModelBox(bone74, 96, 1, -1.1F, -1.0F, -10.0F, 1, 1, 1, -0.2F, false));

		bone75 = new ModelRenderer(this);
		bone75.setRotationPoint(0.7F, 1.1F, 6.75F);
		body.addChild(bone75);
		bone75.cubeList.add(new ModelBox(bone75, 96, 1, -1.2F, -1.0F, -10.0F, 1, 1, 1, -0.2F, false));

		cape = new ModelRenderer(this);
		cape.setRotationPoint(0.0F, -1.1F, 0.2F);
		body.addChild(cape);
		

		capeInner = new ModelRenderer(this);
		capeInner.setRotationPoint(0.0F, 11.35F, -0.2F);
		cape.addChild(capeInner);
		capeInner.cubeList.add(new ModelBox(capeInner, 28, 60, -4.0F, -24.6F, -3.8F, 8, 5, 8, 0.45F, false));

		cape7 = new ModelRenderer(this);
		cape7.setRotationPoint(0.0F, -19.0F, -2.7F);
		capeInner.addChild(cape7);
		setRotationAngle(cape7, 0.0873F, 0.0F, 0.0F);
		cape7.cubeList.add(new ModelBox(cape7, 0, 59, -5.5F, -0.0822F, 0.208F, 11, 2, 7, -0.06F, false));

		cape8 = new ModelRenderer(this);
		cape8.setRotationPoint(0.0F, 2.0F, 1.0F);
		cape7.addChild(cape8);
		setRotationAngle(cape8, 0.0873F, 0.0F, 0.0F);
		cape8.cubeList.add(new ModelBox(cape8, 46, 35, -6.5F, -0.0637F, 0.2143F, 13, 2, 6, -0.06F, false));

		cape9 = new ModelRenderer(this);
		cape9.setRotationPoint(0.0F, 2.0F, 1.0F);
		cape8.addChild(cape9);
		setRotationAngle(cape9, 0.0873F, 0.0F, 0.0F);
		cape9.cubeList.add(new ModelBox(cape9, 41, 28, -7.5F, -0.0448F, 0.2191F, 15, 2, 5, -0.06F, false));

		cape10 = new ModelRenderer(this);
		cape10.setRotationPoint(0.0F, 2.0F, 0.0F);
		cape9.addChild(cape10);
		setRotationAngle(cape10, 0.0873F, 0.0F, 0.0F);
		cape10.cubeList.add(new ModelBox(cape10, 0, 15, -8.5F, -0.1195F, -0.7437F, 17, 12, 6, -0.06F, false));

		capeOutter = new ModelRenderer(this);
		capeOutter.setRotationPoint(0.0F, 11.25F, 0.0F);
		cape.addChild(capeOutter);
		capeOutter.cubeList.add(new ModelBox(capeOutter, 0, 68, -4.0F, -24.5F, -4.0F, 8, 5, 8, 0.5F, false));

		cape2 = new ModelRenderer(this);
		cape2.setRotationPoint(0.0F, -19.0F, -2.7F);
		capeOutter.addChild(cape2);
		setRotationAngle(cape2, 0.0873F, 0.0F, 0.0F);
		cape2.cubeList.add(new ModelBox(cape2, 60, 60, -5.5F, 0.0F, 0.0F, 11, 2, 7, 0.01F, false));

		cape3 = new ModelRenderer(this);
		cape3.setRotationPoint(0.0F, 2.0F, 1.0F);
		cape2.addChild(cape3);
		setRotationAngle(cape3, 0.0873F, 0.0F, 0.0F);
		cape3.cubeList.add(new ModelBox(cape3, 0, 51, -6.5F, 0.0F, 0.0F, 13, 2, 6, 0.01F, false));

		cape4 = new ModelRenderer(this);
		cape4.setRotationPoint(0.0F, 2.0F, 1.0F);
		cape3.addChild(cape4);
		setRotationAngle(cape4, 0.0873F, 0.0F, 0.0F);
		cape4.cubeList.add(new ModelBox(cape4, 46, 16, -7.5F, 0.0F, 0.0F, 15, 2, 5, 0.01F, false));

		cape5 = new ModelRenderer(this);
		cape5.setRotationPoint(0.0F, 2.0F, 0.0F);
		cape4.addChild(cape5);
		setRotationAngle(cape5, 0.0873F, 0.0F, 0.0F);
		cape5.cubeList.add(new ModelBox(cape5, 0, 33, -8.5F, -0.094F, -0.9658F, 17, 12, 6, 0.01F, false));

		armLeft = new ModelRenderer(this);
		armLeft.setRotationPoint(3.0F, -17.5F, 0.0F);
		main.addChild(armLeft);
		setRotationAngle(armLeft, 0.0F, 0.0F, -0.3491F);
		armLeft.cubeList.add(new ModelBox(armLeft, 74, 89, 0.0F, 1.0F, -1.0F, 2, 8, 2, 0.0F, false));
		armLeft.cubeList.add(new ModelBox(armLeft, 78, 78, -0.5F, 0.0F, -1.5F, 3, 8, 3, -0.2F, false));

		right_arm_bone = new ModelRenderer(this);
		right_arm_bone.setRotationPoint(-3.0F, -17.5F, 0.0F);
		main.addChild(right_arm_bone);
		setRotationAngle(right_arm_bone, 0.0F, 0.0F, 0.3491F);
		right_arm_bone.cubeList.add(new ModelBox(right_arm_bone, 74, 89, -2.0F, 1.0F, -1.0F, 2, 8, 2, 0.0F, false));
		right_arm_bone.cubeList.add(new ModelBox(right_arm_bone, 78, 78, -2.5F, 0.0F, -1.5F, 3, 8, 3, -0.2F, false));

		legLeft = new ModelRenderer(this);
		legLeft.setRotationPoint(2.0F, -9.0F, 0.0F);
		main.addChild(legLeft);
		legLeft.cubeList.add(new ModelBox(legLeft, 0, 0, -1.501F, 0.0F, -1.5F, 3, 9, 3, 0.0F, false));

		legRight = new ModelRenderer(this);
		legRight.setRotationPoint(-2.0F, -9.0F, 0.0F);
		main.addChild(legRight);
		legRight.cubeList.add(new ModelBox(legRight, 0, 0, -1.499F, 0.0F, -1.5F, 3, 9, 3, 0.0F, false));
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