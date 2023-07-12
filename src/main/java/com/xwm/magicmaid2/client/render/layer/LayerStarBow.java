package com.xwm.magicmaid2.client.render.layer;

import com.xwm.magicmaid2.client.model.weapon.ModelStarBow;
import com.xwm.magicmaid2.common.entity.maid.EntityMaidBase;
import com.xwm.magicmaid2.core.enums.EnumState;
import com.xwm.magicmaid2.core.init.ItemInit;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;


@SideOnly(Side.CLIENT)
public class LayerStarBow extends GeoLayerRenderer<EntityMaidBase> {

    private static final ResourceLocation BOW_0 = new ResourceLocation(Reference.MODID, "textures/items/star_bow.png");
    private static final ResourceLocation BOW_1 = new ResourceLocation(Reference.MODID, "textures/items/star_bow_1.png");
    private static final ResourceLocation BOW_2 = new ResourceLocation(Reference.MODID, "textures/items/star_bow_2.png");

    private AnimatedGeoModel starBow = new ModelStarBow();
    private IGeoRenderer renderer;
    private int tick;

    public LayerStarBow(IGeoRenderer<EntityMaidBase> entityRendererIn) {
        super(entityRendererIn);
        this.renderer = entityRendererIn;
        this.tick = 0;
    }

    @Override
    public void render(EntityMaidBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA, 1, 0);
        GlStateManager.translate(0.2,  entitylivingbaseIn.height / 2f + 0.05, -0.5);
        GlStateManager.rotate(netHeadYaw, 0, 1, 0);
//        GlStateManager.rotate(headPitch, 1, 0, 0);
        GlStateManager.scale(1, 1.5, 1);

        if (entitylivingbaseIn.getState() == EnumState.toInt(EnumState.SKILL1))
        {
            tick++;
            if (tick < 10) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(BOW_0);
            }
            else if (tick < 15) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(BOW_1);
            }
            else if (tick < 20) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(BOW_2);
            }
            else {
                Minecraft.getMinecraft().getTextureManager().bindTexture(BOW_0);
            }

            this.renderer.render(starBow.getModel(starBow.getModelLocation(ItemInit.STAR_BOW)), entitylivingbaseIn, partialTicks, 1, 1, 1, 0.8F);

        }
        else
            tick = 0;

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();

    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
