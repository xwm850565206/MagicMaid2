// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.12.2 or 1.15.2 (same format for both) for entity models animated with GeckoLib
// Paste this class into your mod and follow the documentation for GeckoLib to use animations. You can find the documentation here: https://github.com/bernie-g/geckolib
// Blockbench plugin created by Gecko
package com.xwm.magicmaid2.client.model.entity;

import com.xwm.magicmaid2.common.entity.mob.EntityEvilSkeletonMaster;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@SideOnly(Side.CLIENT)
public class ModelEvilSkeletonMaster extends AnimatedGeoModel<EntityEvilSkeletonMaster> {

	@Override
	public ResourceLocation getModelLocation(EntityEvilSkeletonMaster object) {
		return new ResourceLocation(Reference.MODID, "geo/models/evil_skeleton_master.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(EntityEvilSkeletonMaster object) {
		return new ResourceLocation(Reference.MODID, "textures/entities/evil_skeleton_master.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(EntityEvilSkeletonMaster animatable) {
		return new ResourceLocation(Reference.MODID, "animations/evil_skeleton_master.animation.json");
	}
}