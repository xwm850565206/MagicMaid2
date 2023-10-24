package com.xwm.magicmaid2.client.render.layer;

import com.xwm.magicmaid2.client.model.javamodel.ModelIce;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidNeva;
import com.xwm.magicmaid2.common.entity.maid.EntityMaidBase;
import com.xwm.magicmaid2.common.entity.mob.EntityEvilSkeleton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class LayerNovaIce extends GeoLayerRenderer<EntityMaidBase>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("magic_maid2:textures/entities/ice.png");

    private ModelBase ICE = new ModelIce();

    public LayerNovaIce(IGeoRenderer<EntityMaidBase> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(EntityMaidBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        GeoModelProvider mainModelProvider = getEntityModel();
        GeoBone anchor = mainModelProvider.getModel(mainModelProvider.getModelLocation(entitylivingbaseIn)).getBone("wing_bone").get();
        apply(anchor);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 1, 1);

        int charge = ((EntityMagicMaidNeva)entitylivingbaseIn).getCharge();
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
        float perAngle = 60;
        for (int i = 0; i < charge; i++)
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate(MathHelper.cos((float) Math.toRadians(i*perAngle)), MathHelper.sin((float) Math.toRadians(i*perAngle)), 0);
            renderIce(90, 0, true);
            GlStateManager.popMatrix();
        }
//        for (int i = charge; i < 6; i++)
//        {
//            GlStateManager.pushMatrix();
//            GlStateManager.translate(MathHelper.cos((float) Math.toRadians(i*perAngle)), MathHelper.sin((float) Math.toRadians(i*perAngle)), 0);
//            renderIce(90, 0, true);
//            GlStateManager.popMatrix();
//        }
        GlStateManager.popMatrix();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

    public void renderIce(float yaw, float pitch, boolean isAlpha)
    {
        if (isAlpha)
        {
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA, 1, 0);
        }
        else {
            GlStateManager.disableAlpha();
            GlStateManager.color(1, 1, 1, 1);
            GlStateManager.scale(2, 2, 2);
        }
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        this.ICE.render(null, 0.0F, 0.0F, 0.0F, yaw, pitch, 0.0625F);

        if (isAlpha) {
            GlStateManager.disableBlend();
            GlStateManager.enableAlpha();
        }
    }

    public void apply(GeoBone anchor)
    {
        GlStateManager.translate(anchor.getPositionX() / 16, anchor.getPositionY() / 16, anchor.getPositionZ() / 16);
        GlStateManager.translate(anchor.rotationPointX / 16, anchor.rotationPointY / 16, anchor.rotationPointZ / 16);
        GlStateManager.rotate((float) (anchor.getRotationZ() / Math.PI * 180), 0, 0, 1);
        GlStateManager.rotate((float) (anchor.getRotationY() / Math.PI * 180), 0, 1, 0);
        GlStateManager.rotate((float) (anchor.getRotationX() / Math.PI * 180), 1, 0, 0);
        GlStateManager.scale(anchor.getScaleX(), anchor.getScaleY(), anchor.getScaleZ());
        GlStateManager.translate(-anchor.rotationPointX / 16, -anchor.rotationPointY / 16, -anchor.rotationPointZ / 16);
    }
}
