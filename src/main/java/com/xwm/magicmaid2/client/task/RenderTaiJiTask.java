package com.xwm.magicmaid2.client.task;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderTaiJiTask extends TaskBase
{
    private double posX;
    private double posY;
    private double posZ;

    private double radius;

    public RenderTaiJiTask(@Nullable Entity taskOwner, int maxAge, double posX, double posY, double posZ, double radius) {
        super(taskOwner, maxAge);
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.radius = radius;
    }

    public RenderTaiJiTask(@Nullable Entity taskOwner, int maxAge, double posX, double posY, double posZ) {
        this(taskOwner, maxAge, posX, posY, posZ, 2);
    }

    @Override
    public void run() {
        double renderPosX = posX - TileEntityRendererDispatcher.staticPlayerX;
        double renderPosY = posY - TileEntityRendererDispatcher.staticPlayerY;
        double renderPosZ = posZ - TileEntityRendererDispatcher.staticPlayerZ;
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
//        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.translate(renderPosX, renderPosY, renderPosZ);
        GlStateManager.rotate(5 + (Minecraft.getSystemTime() / 20 % 360), 0, 1, 0);
        GlStateManager.color(1, 1, 1, 1);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        // 白色半圆
        bufferbuilder.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION);
        GlStateManager.color(1, 1, 1, 1);
        for (int i = 0; i <= 180; i++)
        {
            bufferbuilder.pos(this.radius*Math.cos(Math.toRadians(i)), 0.01, this.radius*Math.sin(Math.toRadians(i))).endVertex();
        }
        Tessellator.getInstance().draw();

        // 黑色半圆
        bufferbuilder.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION);
        GlStateManager.color(0, 0, 0, 1);
        for (int i = 180; i <= 360; i++)
        {
            bufferbuilder.pos(this.radius*Math.cos(Math.toRadians(i)), 0.01, this.radius*Math.sin(Math.toRadians(i))).endVertex();
        }
        Tessellator.getInstance().draw();

        // 白色外切
        bufferbuilder.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION);
        GlStateManager.color(1, 1, 1, 1);
        for (int i = 90; i <= 180; i++)
        {
            bufferbuilder.pos(Math.toRadians(i)*2 / Math.PI * this.radius - this.radius, 0.011, this.radius*Math.sin(Math.toRadians(i)*2)/2).endVertex();
        }
        Tessellator.getInstance().draw();

        // 黑色外切
        bufferbuilder.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION);
        GlStateManager.color(0, 0, 0, 1);
        for (int i = 0; i <= 90; i++)
        {
            bufferbuilder.pos(Math.toRadians(i)*2 / Math.PI * this.radius - this.radius, 0.011, this.radius*Math.sin(Math.toRadians(i)*2)/2).endVertex();
        }
        Tessellator.getInstance().draw();

        // 白色圆
        bufferbuilder.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION);
        GlStateManager.color(1, 1, 1, 1);
        for (int i = 0; i < 360; i++)
        {
            bufferbuilder.pos(this.radius*Math.cos(Math.toRadians(i))/8 - this.radius/2, 0.012, this.radius*Math.sin(Math.toRadians(i))/8).endVertex();
        }
        Tessellator.getInstance().draw();

        // 黑色圆
        bufferbuilder.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION);
        GlStateManager.color(0, 0, 0, 1);
        for (int i = 0; i < 360; i++)
        {
            bufferbuilder.pos(this.radius*Math.cos(Math.toRadians(i))/8 + this.radius/2, 0.012, this.radius*Math.sin(Math.toRadians(i))/8).endVertex();
        }
        Tessellator.getInstance().draw();

        GlStateManager.popMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
        GlStateManager.disableRescaleNormal();
    }
}
