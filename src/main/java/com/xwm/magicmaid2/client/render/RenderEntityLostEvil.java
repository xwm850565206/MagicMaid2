package com.xwm.magicmaid2.client.render;

import com.xwm.magicmaid2.client.model.entity.ModelLostEvil;
import com.xwm.magicmaid2.client.model.entity.ModelLostHeart;
import com.xwm.magicmaid2.common.entity.mob.EntityLostEvil;
import com.xwm.magicmaid2.common.entity.mob.EntityLostHeart;
import net.minecraft.client.renderer.entity.RenderManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class RenderEntityLostEvil extends GeoEntityRenderer<EntityLostEvil> {
    protected RenderEntityLostEvil(RenderManager renderManager) {
        super(renderManager, new ModelLostEvil());
    }
}
