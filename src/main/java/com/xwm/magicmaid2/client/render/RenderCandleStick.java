package com.xwm.magicmaid2.client.render;

import com.xwm.magicmaid2.client.model.entity.ModelCandleStick;
import com.xwm.magicmaid2.common.tileentity.TileEntityCandleStick;
import software.bernie.example.block.tile.FertilizerTileEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RenderCandleStick extends GeoBlockRenderer<TileEntityCandleStick>
{
    public RenderCandleStick() {
        super(new ModelCandleStick());
    }
}
