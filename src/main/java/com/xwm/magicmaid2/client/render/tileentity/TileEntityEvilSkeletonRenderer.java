package com.xwm.magicmaid2.client.render.tileentity;

import com.xwm.magicmaid2.client.model.entity.ModelEvilSkeleton;
import com.xwm.magicmaid2.common.tileentity.TileEntityEvilSkeleton;

import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class TileEntityEvilSkeletonRenderer extends GeoBlockRenderer<TileEntityEvilSkeleton>
{
    public TileEntityEvilSkeletonRenderer() {
        super(new ModelEvilSkeleton());
    }
}