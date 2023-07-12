package com.xwm.magicmaid2.client.task;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderBeamSegmentTask extends TaskBase
{
    public static float[] PURPLE = {160.0f/255, 32.0f/255, 240.0f/255};
    public static final ResourceLocation QUARTZ = new ResourceLocation("minecraft", "textures/blocks/quartz_block_chiseled_top.png");

    private double posX;
    private double posY;
    private double posZ;
    private int yOffset;
    private int height;
    private float[] colors;

    public RenderBeamSegmentTask(@Nullable Entity taskOwner, int maxAge, double posX, double posY, double posZ, int yOffset, int height, float[] colors) {
        super(taskOwner, maxAge);
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yOffset = yOffset;
        this.height = height;
        this.colors = colors;
    }

    public RenderBeamSegmentTask(@Nullable Entity taskOwner, int maxAge, double posX, double posY, double posZ, float[] colors) {
        this(taskOwner, maxAge, posX, posY, posZ, -2, 256, colors);
    }

    public RenderBeamSegmentTask(@Nullable Entity taskOwner, int maxAge, double posX, double posY, double posZ) {
        this(taskOwner, maxAge, posX, posY, posZ, -2, 256, PURPLE);
    }

    @Override
    public void run() {
        double renderPosX = posX - TileEntityRendererDispatcher.staticPlayerX - 0.5;
        double renderPosY = posY - TileEntityRendererDispatcher.staticPlayerY;
        double renderPosZ = posZ - TileEntityRendererDispatcher.staticPlayerZ - 0.5;
        Minecraft.getMinecraft().getTextureManager().bindTexture(QUARTZ);
        TileEntityBeaconRenderer.renderBeamSegment(renderPosX, renderPosY, renderPosZ, 1.0, 1.0, Minecraft.getSystemTime(), this.yOffset, this.height, this.colors);
    }
}
