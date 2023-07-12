package com.xwm.magicmaid2.client.task;

import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityEndPortalRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderDrivedRing extends TaskBase
{
    private double posX;
    private double posY;
    private double posZ;

    private double radius;

    public RenderDrivedRing(@Nullable Entity taskOwner, int maxAge, double posX, double posY, double posZ, double radius) {
        super(taskOwner, maxAge);
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.radius = radius;
    }

    public RenderDrivedRing(@Nullable Entity taskOwner, int maxAge, double posX, double posY, double posZ) {
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
//        GlStateManager.rotate(5 + (Minecraft.getSystemTime() / 20.0f % 360), 0, 1, 0);
        GlStateManager.color(1, 1, 1, 1);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        GlStateManager.glLineWidth(2.0f);
        GlStateManager.color(1,182.0f/255.0f,193.0f/255.0f, 1);
        for (int h = 0; h <= 255; h++){
            bufferbuilder.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
            for (int i = 0; i <= 360/12; i++)
            {

                bufferbuilder.pos(this.radius*Math.cos(Math.toRadians(i*12)), 0.01 + h + (Minecraft.getSystemTime()/20)%10/10.0f, this.radius*Math.sin(Math.toRadians(i*12))).endVertex();
            }
            Tessellator.getInstance().draw();
        }

        GlStateManager.glLineWidth(2.0f);
        bufferbuilder.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
        double tmpScale = this.radius*(Minecraft.getSystemTime() / 40.0f % 20 / 10.0f) / Math.PI;
        for (int i = 0; i <= 180; i++)
        {
            double x = Math.toRadians(i-90);
            double fx = (0.64*Math.sqrt(Math.abs(x)) - 0.8 + Math.pow(1.2, Math.abs(x)) * Math.cos(200*x)) * Math.sqrt(Math.cos(x));
            bufferbuilder.pos(x*tmpScale, 0.01, fx*tmpScale).endVertex();
        }
        Tessellator.getInstance().draw();


        GlStateManager.popMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
        GlStateManager.disableRescaleNormal();
    }
}
