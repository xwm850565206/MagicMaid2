package com.xwm.magicmaid2.client.model.weapon;

import com.xwm.magicmaid2.common.item.equipment.ItemAnimatableArmor;
import com.xwm.magicmaid2.common.item.equipment.ItemAnimatableWeapon;
import com.xwm.magicmaid2.core.init.ItemInit;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.model.AnimatedGeoModel;


@SideOnly(Side.CLIENT)
public class ModelArmor extends AnimatedGeoModel<ItemAnimatableArmor>
{
    @Override
    public ResourceLocation getModelLocation(ItemAnimatableArmor object) {
        if (object == ItemInit.STAR_BOW)
            return new ResourceLocation(Reference.MODID, "geo/models/starbow.geo.json");
        else
            return null;
    }

    @Override
    public ResourceLocation getTextureLocation(ItemAnimatableArmor object) {
        if (object == ItemInit.STAR_BOW)
            return new ResourceLocation(Reference.MODID, "textures/items/star_bow.png");
        else
            return null;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ItemAnimatableArmor animatable) {
        return null;
    }
}