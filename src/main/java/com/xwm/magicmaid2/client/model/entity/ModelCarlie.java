package com.xwm.magicmaid2.client.model.entity;

import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidCarlie;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.model.AnimatedGeoModel;


@SideOnly(Side.CLIENT)
public class ModelCarlie extends AnimatedGeoModel<EntityMagicMaidCarlie>
{
    @Override
    public ResourceLocation getModelLocation(EntityMagicMaidCarlie object) {
        return new ResourceLocation(Reference.MODID, "geo/models/carlie.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityMagicMaidCarlie object) {
        return new ResourceLocation(Reference.MODID, "textures/entities/carlie.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityMagicMaidCarlie animatable) {
        return new ResourceLocation(Reference.MODID, "animations/carlie.animation.json");
    }
}
