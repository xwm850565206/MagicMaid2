package com.xwm.magicmaid2.client.event;

import com.xwm.magicmaid.util.interfaces.IHasModel;
import com.xwm.magicmaid2.client.render.RenderHandler;
import com.xwm.magicmaid2.client.task.RenderTaskManager;
import com.xwm.magicmaid2.common.block.BlockFluidDedicationLife;
import com.xwm.magicmaid2.core.init.BlockInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class EventLoader {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event)
    {
        for (Block block : BlockInit.BLOCKS)
        {
            if (block instanceof IHasModel)
            {
                ((IHasModel)block).registerModels();
            }
        }

        for (Item item : ItemInit.ITEMS)
        {
            if (item instanceof IHasModel)
            {
                ((IHasModel)item).registerModels();
            }
        }

        RenderHandler.registerCustomMeshesAndStates();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onFogColorRender(EntityViewRenderEvent.FogColors event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;

        if (player.getEntityData().hasKey("crasy_rabbit") && player.getEntityData().getInteger("crasy_rabbit") != -1){
            event.setBlue(0.8f);
            event.setGreen(0.8f);
            event.setRed(0.8f);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onFirstPersonRender(EntityViewRenderEvent.RenderFogEvent event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;

        if (player.getEntityData().hasKey("crasy_rabbit") && player.getEntityData().getInteger("crasy_rabbit") != -1) {
            GlStateManager.setFog(GlStateManager.FogMode.EXP);
            GlStateManager.setFogStart(0.0F);
            GlStateManager.setFogEnd(0.8F);
            GlStateManager.setFogDensity(0.08F);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onEntityRenderPre(RenderLivingEvent.Pre event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;

        // crazy attack
        if (player.getEntityData().hasKey("crasy_rabbit") && player.getEntityData().getInteger("crasy_rabbit") != -1)
        {
            int id = player.getEntityData().getInteger("crasy_rabbit");
            Entity rabbit = player.world.getEntityByID(id);
            if (rabbit != null && rabbit.isEntityAlive() & rabbit instanceof EntityRabbit)
            {
                EntityLivingBase entityLivingBase = event.getEntity();
                if (!entityLivingBase.equals(rabbit))
                {
                    mc.getRenderManager().renderEntity(rabbit, event.getX(), event.getY(), event.getZ(), 0, 0, false);
                    event.setCanceled(true);
                }

            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent.Pre event)
    {
        EntityPlayer player = event.getEntityPlayer();
        if (!player.getEntityData().hasKey("dedication_life") || !player.getEntityData().getBoolean("dedication_life"))
            return;

        if (!player.isInWater())
            return;

        if (player instanceof EntityPlayerSP)
        {
            if (player.getEntityData().hasKey("dedication_life_type"))
            {
                int type = player.getEntityData().getInteger("dedication_life_type");
                if(type == 1)
                {
                    GlStateManager.disableTexture2D();
                    GlStateManager.disableLighting();
                    GlStateManager.color(1, 1, 1);
                }
                else if (type == 2)
                {
                    GlStateManager.disableTexture2D();
                    GlStateManager.disableLighting();
                    GlStateManager.color(0, 0, 0);
                }
                else if (type == 0){
                    GlStateManager.disableTexture2D();
                    GlStateManager.disableLighting();
                    GlStateManager.color(0.5f, 0.5f, 0.5f, 0.2f);
                }
            }

        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent.Post event)
    {

        EntityPlayer player = event.getEntityPlayer();
        if (!player.getEntityData().hasKey("dedication_life") || !player.getEntityData().getBoolean("dedication_life") || !player.getEntityData().hasKey("dedication_life_type"))
            return;
        if (!player.isInWater())
            return;
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
    }

    //渲染待渲染队列的特效
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRenderTick(RenderWorldLastEvent event)
    {
        RenderTaskManager.processRenderTask(event);
    }

}
