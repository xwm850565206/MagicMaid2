package com.xwm.magicmaid2.client.model.weapon;

import com.xwm.magicmaid2.common.item.equipment.ItemAnimatableWeapon;
import com.xwm.magicmaid2.core.init.ItemInit;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.model.AnimatedGeoModel;


@SideOnly(Side.CLIENT)
public class ModelWeaponHand extends AnimatedGeoModel<ItemAnimatableWeapon>
{
    @Override
    public ResourceLocation getModelLocation(ItemAnimatableWeapon object) {
        if (object == ItemInit.DARK_CLAW)
            return new ResourceLocation(Reference.MODID, "geo/models/dark_claw_hand.geo.json");
        else if (object == ItemInit.DARK_SOUL)
            return new ResourceLocation(Reference.MODID, "geo/models/dark_soul_hand.geo.json");
        else if (object == ItemInit.UNREAL_RING)
            return new ResourceLocation(Reference.MODID, "geo/models/unreal_ring_hand.geo.json");
        else
            return null;
    }

    @Override
    public ResourceLocation getTextureLocation(ItemAnimatableWeapon object) {
        if (object == ItemInit.DARK_CLAW)
            return new ResourceLocation(Reference.MODID, "textures/items/dark_claw.png");
        else if (object == ItemInit.DARK_SOUL)
            return new ResourceLocation(Reference.MODID, "textures/items/dark_soul.png");
        else if (object == ItemInit.UNREAL_RING)
            return new ResourceLocation(Reference.MODID, "textures/items/unreal_ring.png");
        else
            return null;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ItemAnimatableWeapon animatable) {
        return null;
    }
}