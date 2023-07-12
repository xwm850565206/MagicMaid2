// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.12.2 or 1.15.2 (same format for both) for entity models animated with GeckoLib
// Paste this class into your mod and follow the documentation for GeckoLib to use animations. You can find the documentation here: https://github.com/bernie-g/geckolib
// Blockbench plugin created by Gecko
package com.xwm.magicmaid2.client.model.entity;

import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidAili;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidXimo;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@SideOnly(Side.CLIENT)
public class ModelXimo extends AnimatedGeoModel<EntityMagicMaidXimo> {

	@Override
	public ResourceLocation getModelLocation(EntityMagicMaidXimo object) {
		return new ResourceLocation(Reference.MODID, "geo/models/ximo1.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(EntityMagicMaidXimo object) {
		return new ResourceLocation(Reference.MODID, "textures/entities/ximo.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(EntityMagicMaidXimo animatable) {
		return new ResourceLocation(Reference.MODID, "animations/ximo.animation.json");
	}
}