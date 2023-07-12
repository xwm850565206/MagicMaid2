package com.xwm.magicmaid2.client.render.item;

import com.xwm.magicmaid2.client.model.weapon.ModelWeapon;
import com.xwm.magicmaid2.common.item.equipment.ItemAnimatableWeapon;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.example.item.JackInTheBoxItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;


@SideOnly(Side.CLIENT)
public class ItemAnimatableWeaponRenderer extends GeoItemRenderer<ItemAnimatableWeapon>
{
    public ItemAnimatableWeaponRenderer() {
        super(new ModelWeapon());
    }
}
