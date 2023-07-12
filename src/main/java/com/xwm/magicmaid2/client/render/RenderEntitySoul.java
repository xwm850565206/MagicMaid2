package com.xwm.magicmaid2.client.render;

import com.xwm.magicmaid2.common.entity.EntitySoul;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderEntitySoul extends Render<EntitySoul> {

    public RenderEntitySoul(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntitySoul entity, double x, double y, double z, float entityYaw, float partialTicks) {
//        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        if (entity.getSoulTarget() != null)
        {
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA); // todo adjust

            float a = Math.min(1, (1 / 40.0f) * entity.ticksExisted);
            float c = 1 - Math.max(0, (1 / 40.0f) * (entity.ticksExisted - 40));
            GlStateManager.color(c, c, c, a);

            GlStateManager.pushMatrix();
            GlStateManager.translate(x, y, z);
            GlStateManager.scale(-1, 1, -1);
            GlStateManager.translate(-x, -y, -z);

            Minecraft.getMinecraft().getRenderManager().renderEntity(entity.getSoulTarget(), x, y, z, 0, 0, false);

            GlStateManager.popMatrix();
//            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();

            GlStateManager.color(1, 1, 1, 1);
        }

    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntitySoul entity) {
        return null;
    }
}
