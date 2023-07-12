package com.xwm.magicmaid2.client.model.weapon;

import com.xwm.magicmaid2.common.item.equipment.ItemStarBow;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.model.AnimatedGeoModel;


@SideOnly(Side.CLIENT)
public class ModelStarBow extends AnimatedGeoModel<ItemStarBow>
{
    @Override
    public ResourceLocation getModelLocation(ItemStarBow object) {
        return new ResourceLocation(Reference.MODID, "geo/models/starbow.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ItemStarBow object) {
        return new ResourceLocation(Reference.MODID, "textures/items/star_bow.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ItemStarBow animatable) {
        return null;
    }
}
