package com.xwm.magicmaid2.client.render.item;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import static com.xwm.magicmaid2.client.render.RenderEntityHeavenArrow.sphere;


@SideOnly(Side.CLIENT)
public class ItemUnrealBallRenderer extends TileEntityItemStackRenderer
{
    @Override
    public void renderByItem(ItemStack itemStackIn) {
        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA, 1, 0);

        GlStateManager.color(0.35f, 0.67f, 0.35f,0.6f);
        GlStateManager.translate(0.5, 0.5, 0.5);
        sphere.draw(0.2f, 20, 20);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
    }
}
