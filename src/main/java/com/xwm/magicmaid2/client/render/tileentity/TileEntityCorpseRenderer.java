package com.xwm.magicmaid2.client.render.tileentity;

import com.xwm.magicmaid2.client.model.entity.ModelCorpse;
import com.xwm.magicmaid2.common.tileentity.TileEntityCorpse;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class TileEntityCorpseRenderer extends TileEntitySpecialRenderer<TileEntityCorpse>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entities/corpse.png");
    private ModelCorpse model;

    public TileEntityCorpseRenderer() {
        super();
        this.model = new ModelCorpse();
    }

    @Override
    public void render(TileEntityCorpse te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);

        EntityPlayerSP playerSP = Minecraft.getMinecraft().player;
        if (playerSP.getEntityData().hasKey(Reference.MODID + "_answer") && playerSP.getEntityData().getBoolean(Reference.MODID + "_answer"))
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate(x+0.5, y, z+0.5);
            GlStateManager.rotate(180, 0, 1, 0);
//            RenderPlayer
            Minecraft.getMinecraft().getRenderManager().renderEntity(playerSP, 0, 0, 0, playerSP.rotationYaw, 1, false);

            GlStateManager.popMatrix();
        }
        else {
            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            GlStateManager.disableCull();
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//             GlStateManager.enableTexture2D();
            GlStateManager.translate(x, y, z);
            GlStateManager.translate(0.5, 0.5, 0.5);
            GlStateManager.translate(0, 2, 0);
            GlStateManager.scale(1, -1, 1);
            GlStateManager.scale(0.1, 0.1, 0.1);
//          GlStateManager.translate(0, 1, 1);
            this.bindTexture(TEXTURE);
            model.render(null, 0, 0, 0, 0, 0, 1);
            GlStateManager.enableCull();
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }
    }
}
