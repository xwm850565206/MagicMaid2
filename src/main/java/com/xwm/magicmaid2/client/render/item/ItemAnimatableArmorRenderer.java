package com.xwm.magicmaid2.client.render.item;

import com.xwm.magicmaid2.client.model.weapon.ModelArmor;
import com.xwm.magicmaid2.common.item.equipment.ItemAnimatableArmor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;


@SideOnly(Side.CLIENT)
public class ItemAnimatableArmorRenderer extends GeoItemRenderer<ItemAnimatableArmor>
{
    public ItemAnimatableArmorRenderer() {
        super(new ModelArmor());
    }
}
