// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.12.2 or 1.15.2 (same format for both) for entity models animated with GeckoLib
// Paste this class into your mod and follow the documentation for GeckoLib to use animations. You can find the documentation here: https://github.com/bernie-g/geckolib
// Blockbench plugin created by Gecko
package com.xwm.magicmaid2.client.model.entity;

import com.xwm.magicmaid2.common.entity.mob.EntityRoutu;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@SideOnly(Side.CLIENT)
public class ModelRoutu extends AnimatedGeoModel<EntityRoutu> {

	@Override
	public ResourceLocation getModelLocation(EntityRoutu object) {
		return new ResourceLocation(Reference.MODID, "geo/models/routu/routu" + (object.getRoutuType()+1) +  ".geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(EntityRoutu object) {
		return new ResourceLocation(Reference.MODID, "textures/entities/routu.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(EntityRoutu animatable) {
		return null;
	}
}