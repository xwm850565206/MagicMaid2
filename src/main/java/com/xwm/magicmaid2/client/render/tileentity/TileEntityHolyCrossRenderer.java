package com.xwm.magicmaid2.client.render.tileentity;

import com.xwm.magicmaid2.client.model.entity.ModelCorpse;
import com.xwm.magicmaid2.common.tileentity.TileEntityCorpse;
import com.xwm.magicmaid2.common.tileentity.TileEntityHolyCross;
import com.xwm.magicmaid2.core.init.ItemInit;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class TileEntityHolyCrossRenderer extends TileEntitySpecialRenderer<TileEntityHolyCross>
{
    private static final ItemStack CROSS = new ItemStack(ItemInit.HOLY_CROSS);

    public TileEntityHolyCrossRenderer() {
        super();
    }

    @Override
    public void render(TileEntityHolyCross te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);


        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.translate(x, y, z);
        GlStateManager.translate(0.5, 0.6, 0.5);
        GlStateManager.scale(0.7, 0.7, 1);
        Minecraft.getMinecraft().getRenderItem().renderItem(CROSS, ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.enableCull();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();

    }
}
