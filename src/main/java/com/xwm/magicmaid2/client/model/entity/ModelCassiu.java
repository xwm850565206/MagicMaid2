package com.xwm.magicmaid2.client.model.entity;

import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidCassiu;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.model.AnimatedGeoModel;


@SideOnly(Side.CLIENT)
public class ModelCassiu extends AnimatedGeoModel<EntityMagicMaidCassiu>
{

    @Override
    public ResourceLocation getModelLocation(EntityMagicMaidCassiu object) {
        return new ResourceLocation(Reference.MODID, "geo/models/cassiu.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityMagicMaidCassiu object) {
        return new ResourceLocation(Reference.MODID, "textures/entities/cassiu.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityMagicMaidCassiu animatable) {
        return new ResourceLocation(Reference.MODID, "animations/cassiu.animation.json");
    }
}
