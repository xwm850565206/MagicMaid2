package com.xwm.magicmaid2.client.render.item;

import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidXimo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import static com.xwm.magicmaid2.client.render.RenderEntityHeavenArrow.sphere;


@SideOnly(Side.CLIENT)
public class ItemEagerRenderer extends TileEntityItemStackRenderer
{
    @Override
    public void renderByItem(ItemStack itemStackIn) {
        GlStateManager.pushMatrix();
        GlStateManager.color(1, 1, 1, 0.5f);
        ItemStack stack = new ItemStack(Items.SKULL, 1, 0);
        super.renderByItem(stack);
        GlStateManager.popMatrix();
    }

    public void renderByItem(EntityMagicMaidXimo ximo) {

        GlStateManager.pushMatrix();
        GlStateManager.rotate(Minecraft.getSystemTime()/10 % 360, 0, 1, 0);
        GlStateManager.translate(-1, 0.5, -1);
        GlStateManager.scale(0.5, 0.5, 0.5);
        if (ximo.isDesire()) {
            ItemStack stack = new ItemStack(Items.SKULL, 1, 0);
            super.renderByItem(stack);
        }
        else {
            ItemStack stack = new ItemStack(Items.SKULL, 1, 3);
            super.renderByItem(stack);
        }
        GlStateManager.popMatrix();
    }
}
