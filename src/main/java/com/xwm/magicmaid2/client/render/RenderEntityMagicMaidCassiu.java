package com.xwm.magicmaid2.client.render;

import com.xwm.magicmaid2.client.model.entity.ModelCassiu;
import com.xwm.magicmaid2.client.render.layer.LayerWeapon;
import com.xwm.magicmaid2.common.entity.maid.EntityMaidBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEntityMagicMaidCassiu extends RenderBase {
    public RenderEntityMagicMaidCassiu(RenderManager renderManager) {
        super(renderManager, new ModelCassiu());
        this.shadowSize = 0.7F * 0.8F;
        this.addLayer(new LayerWeapon(this));
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
