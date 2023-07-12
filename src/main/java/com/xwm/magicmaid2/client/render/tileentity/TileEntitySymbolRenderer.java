package com.xwm.magicmaid2.client.render.tileentity;

import com.xwm.magicmaid2.client.model.entity.ModelCorpse;
import com.xwm.magicmaid2.common.tileentity.TileEntityCorpse;
import com.xwm.magicmaid2.common.tileentity.TileEntitySymbol;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityBeaconRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class TileEntitySymbolRenderer extends TileEntitySpecialRenderer<TileEntitySymbol>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entities/corpse.png");
    public TileEntitySymbolRenderer() {
        super();
    }

    @Override
    public void render(TileEntitySymbol te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);

        long time = Minecraft.getSystemTime();
        float radian = (float) (time / 800.0);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5, y + 0.5, z + 0.5);
        GlStateManager.rotate((float) Math.toDegrees(radian), 0, 1, 0);
        GlStateManager.scale(0.75, 0.75, 0.75);
        Minecraft.getMinecraft().getRenderItem().renderItem(te.getSymbol(), ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.popMatrix();

        if (te.isLock()) {
//            double renderPosX = x - TileEntityRendererDispatcher.staticPlayerX - 0.5;
//            double renderPosY = y - TileEntityRendererDispatcher.staticPlayerY;
//            double renderPosZ = z - TileEntityRendererDispatcher.staticPlayerZ - 0.5;
            String name = te.getSymbol().getItem().getRegistryName().getResourcePath().replace("symbol_", "");
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/items/symbol/" + name + ".png"));
            TileEntityBeaconRenderer.renderBeamSegment(x, y, z, partialTicks, 1.0, Minecraft.getSystemTime() / 50.0, 0, 1000, new float[]{1, 1, 1});


        }


    }
}
