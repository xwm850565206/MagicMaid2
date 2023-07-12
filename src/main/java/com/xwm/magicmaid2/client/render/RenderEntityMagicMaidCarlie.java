package com.xwm.magicmaid2.client.render;

import com.xwm.magicmaid2.client.model.entity.ModelCarlie;
import com.xwm.magicmaid2.client.render.layer.LayerStarBow;
import com.xwm.magicmaid2.common.entity.maid.EntityMaidBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;


@SideOnly(Side.CLIENT)
public class RenderEntityMagicMaidCarlie extends RenderBase
{
    private LayerRenderer<EntityMaidBase> layerRenderer = new LayerStarBow(this);

    public RenderEntityMagicMaidCarlie(RenderManager renderManager) {
        super(renderManager, new ModelCarlie());
        this.shadowSize = 0.7F * 0.85F;

        this.addLayer((GeoLayerRenderer<EntityMaidBase>) layerRenderer);
    }

    @Override
    public void doRender(EntityMaidBase entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (entity.isInvisible())
            return;

        float scale = 0.8f;

        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.translate(x / scale - x, y / scale - y, z / scale - z);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);

        GlStateManager.popMatrix();
    }
}
