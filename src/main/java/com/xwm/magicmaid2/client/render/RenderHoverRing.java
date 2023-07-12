package com.xwm.magicmaid2.client.render;

import com.xwm.magicmaid2.common.entity.throwable.EntityRing;
import com.xwm.magicmaid2.common.entity.throwable.EntityStarArrow;
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
// todo error
public class RenderHoverRing extends Render<EntityRing>
{
    protected RenderHoverRing(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityRing entity, double x, double y, double z, float entityYaw, float partialTicks) {

        GlStateManager.disableTexture2D();
//        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA, 1, 0);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(entityYaw, 0, 1, 0);
        GlStateManager.enableRescaleNormal();

        GlStateManager.glLineWidth(6.0f);
        float[] colors = EntityStarArrow.COLORS[5];
        for (int i = 0; i < 3; i++)
            colors[i] /= 255.0f;

        GlStateManager.color(colors[0], colors[1], colors[2], 1F);
        BufferBuilder builder = Tessellator.getInstance().getBuffer();
        builder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        float r = entity.width;
        int n = 12;
        for (int i = 0; i < n; i++)
        {
            float angle = 360.0f / n * i;
            angle = (float) Math.toRadians(angle);
            builder.pos(Math.sin(angle) * r, Math.cos(angle) * r, 0).endVertex();
//            builder.pos(0 + i , 0 + i, 0).color(colors[0], colors[1], colors[2], entity.ticksExisted / 200.0f).endVertex();

//            builder.pos(Math.sin(i * Math.PI / n) * r, Math.cos(i * Math.PI / n) * r, 0).color(colors[0], colors[1], colors[2], entity.ticksExisted / 200.0f).endVertex();
        }

        Tessellator.getInstance().draw();

        GlStateManager.popMatrix();

        GlStateManager.enableTexture2D();
        GlStateManager.disableRescaleNormal();
//        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityRing entity) {
        return null;
    }
}
