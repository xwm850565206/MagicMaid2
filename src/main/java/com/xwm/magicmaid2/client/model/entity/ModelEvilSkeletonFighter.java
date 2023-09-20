package com.xwm.magicmaid2.client.model.entity;

import com.xwm.magicmaid2.common.entity.mob.EntityEvilSkeleton;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelEvilSkeletonFighter extends AnimatedGeoModel<EntityEvilSkeleton> {

    @Override
    public ResourceLocation getModelLocation(EntityEvilSkeleton entity) {
        return new ResourceLocation(Reference.MODID, "geo/models/evil_skeleton_fighter.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityEvilSkeleton entity) {
        return new ResourceLocation(Reference.MODID, "textures/entities/evil_skeleton.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityEvilSkeleton entity) {
        return new ResourceLocation(Reference.MODID, "animations/evil_skeleton_fighter.animation.json");
    }
}