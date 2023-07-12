package com.xwm.magicmaid2.common.potion;

import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionCrazy extends Potion
{
    public static final ResourceLocation ICON = new ResourceLocation(Reference.MODID + ":textures/potion_effect.png");

    public PotionCrazy() {
        super(false, 0xff0000);
    }

    public boolean hasStatusIcon()
    {
        return true;
    }

    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
    {
        try {
            GlStateManager.pushMatrix();
            mc.getTextureManager().bindTexture(ICON);
            double f1 = 18.0 / 64.0;
            double f2 = 18.0 / 64.0;
            GlStateManager.translate(x + 6, y + 7, 0);
            GlStateManager.scale(f1, f2, 0);
            mc.currentScreen.drawTexturedModalRect(0, 0, 0, 0, 64, 64);
            GlStateManager.popMatrix();
        } catch (NullPointerException e){
            ;
        }
    }

    public boolean shouldRenderHUD(PotionEffect effect)
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc, float alpha) {
        try {
            GlStateManager.pushMatrix();
            mc.getTextureManager().bindTexture(ICON);
            double f1 = 18.0 / 64.0;
            double f2 = 18.0 / 64.0;
            GlStateManager.translate(x + 3, y + 3, 0);
            GlStateManager.scale(f1, f2, 0);
            mc.ingameGUI.drawTexturedModalRect(0, 0, 0, 0, 64, 64);
            GlStateManager.popMatrix();
        } catch (NullPointerException e){
            ;
        }
    }
}
