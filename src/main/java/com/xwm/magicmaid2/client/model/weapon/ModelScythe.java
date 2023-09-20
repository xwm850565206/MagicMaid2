package com.xwm.magicmaid2.client.model.weapon;

import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelScythe extends AnimatedGeoModel
{
    @Override
    public ResourceLocation getModelLocation(Object object) {
        return new ResourceLocation(Reference.MODID, "geo/models/scythe.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Object object) {
        return new ResourceLocation(Reference.MODID, "textures/items/scythe.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Object animatable) {
        return null;
    }
}
