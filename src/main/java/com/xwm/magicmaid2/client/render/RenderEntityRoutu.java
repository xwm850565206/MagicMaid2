package com.xwm.magicmaid2.client.render;

import com.xwm.magicmaid2.client.model.entity.ModelRoutu;
import com.xwm.magicmaid2.common.entity.mob.EntityRoutu;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderEntityRoutu extends GeoEntityRenderer<EntityRoutu> {
    protected RenderEntityRoutu(RenderManager renderManager) {
        super(renderManager, new ModelRoutu());
    }

    @Override
    protected float getDeathMaxRotation(EntityRoutu entityLivingBaseIn) {
        return 0F;
    }
}
