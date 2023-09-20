package com.xwm.magicmaid2.client.model.entity;

import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidAili;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelEvilSkeleton  extends AnimatedGeoModel {

    @Override
    public ResourceLocation getModelLocation(Object entity) {
        return new ResourceLocation(Reference.MODID, "geo/models/evil_skeleton.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Object entity) {
        return new ResourceLocation(Reference.MODID, "textures/blocks/evil_skeleton.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Object entity) {
        return new ResourceLocation(Reference.MODID, "animations/evil_skeleton.animation.json");
    }
}