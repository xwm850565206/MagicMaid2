package com.xwm.magicmaid2.client.render;

import com.xwm.magicmaid.util.Reference;
import com.xwm.magicmaid2.common.entity.maid.EntityMaidBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.RenderHurtColor;
import software.bernie.shadowed.eliotlash.mclib.utils.Interpolations;

import java.awt.*;
import java.util.Collections;


@SideOnly(Side.CLIENT)
public class RenderBase extends GeoEntityRenderer<EntityMaidBase>
{
    protected RenderBase(RenderManager renderManager, AnimatedGeoModel modelProvider) {
        super(renderManager, modelProvider);
    }

    /** gecklib casue some stupid things, fix it **/
    protected void adjustDoRender(EntityMaidBase entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        if (entity.isInvisible())
            return;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        // TODO: entity.isPassenger() looks redundant here
        boolean shouldSit = /* entity.isPassenger() && */ (entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit());
        EntityModelData entityModelData = new EntityModelData();
        entityModelData.isSitting = shouldSit;
        entityModelData.isChild = entity.isChild();

        float f = Interpolations.lerpYaw(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
        float f1 = Interpolations.lerpYaw(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
        float netHeadYaw = f1 - f;
        if (shouldSit && entity.getRidingEntity() instanceof EntityLivingBase)
        {
            EntityLivingBase livingentity = (EntityLivingBase) entity.getRidingEntity();
            f = Interpolations.lerpYaw(livingentity.prevRenderYawOffset, livingentity.renderYawOffset, partialTicks);
            netHeadYaw = f1 - f;
            float f3 = MathHelper.wrapDegrees(netHeadYaw);
            if (f3 < -85.0F)
            {
                f3 = -85.0F;
            }

            if (f3 >= 85.0F)
            {
                f3 = 85.0F;
            }

            f = f1 - f3;
            if (f3 * f3 > 2500.0F)
            {
                f += f3 * 0.2F;
            }

            netHeadYaw = f1 - f;
        }

        float headPitch = Interpolations.lerp(entity.prevRotationPitch, entity.rotationPitch, partialTicks);
		/* TODO: vanilla mobs can't sleep in beds in 1.12.2 and below
		if (entity.getPose() == Pose.SLEEPING)
		{
			Direction direction = entity.getBedDirection();
			if (direction != null)
			{
				float f4 = entity.getEyeHeight(Pose.STANDING) - 0.1F;
				stack.translate((double) ((float) (-direction.getXOffset()) * f4), 0.0D, (double) ((float) (-direction.getZOffset()) * f4));
			}
		} */
        float f7 = this.handleRotationFloat(entity, partialTicks);
        this.applyRotations(entity, f7, f, partialTicks);

        float limbSwingAmount = 0.0F;
        float limbSwing = 0.0F;
        if (!shouldSit && entity.isEntityAlive())
        {
            limbSwingAmount = Interpolations.lerp(entity.prevLimbSwingAmount, entity.limbSwingAmount, partialTicks);
            limbSwing = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);
            if (entity.isChild())
            {
                limbSwing *= 3.0F;
            }

            if (limbSwingAmount > 1.0F)
            {
                limbSwingAmount = 1.0F;
            }
        }
        entityModelData.headPitch = -headPitch;
        entityModelData.netHeadYaw = -netHeadYaw;

        AnimationEvent predicate = new AnimationEvent(entity, limbSwing, limbSwingAmount, partialTicks, !(limbSwingAmount > -0.15F && limbSwingAmount < 0.15F), Collections.singletonList(entityModelData));
        GeoModel model = getGeoModelProvider().getModel(getGeoModelProvider().getModelLocation(entity));
        if (getGeoModelProvider() instanceof IAnimatableModel)
        {
            ((IAnimatableModel<EntityMaidBase>) getGeoModelProvider()).setLivingAnimations(entity, this.getUniqueID(entity), predicate);
        }

        // head rotation
        GeoBone head = model.getBone("head_bone").get();
//        head.setRotationX(headPitch * 0.017453292F); why gecklib cal this so stupid
        head.setRotationY(netHeadYaw * 0.017453292F);

        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0.01f, 0);
        Minecraft.getMinecraft().renderEngine.bindTexture(getEntityTexture(entity));
        Color renderColor = getRenderColor(entity, partialTicks);

        boolean flag = this.setDoRenderBrightness(entity, partialTicks);

        render(model, entity, partialTicks, (float) renderColor.getRed() / 255f, (float) renderColor.getBlue() / 255f, (float) renderColor.getGreen() / 255f, (float) renderColor.getAlpha() / 255);
        if (flag)
        {
            RenderHurtColor.unset();
        }

        for (GeoLayerRenderer<EntityMaidBase> layerRenderer : this.layerRenderers)
        {
            layerRenderer.doRenderLayer(entity, limbSwing, limbSwingAmount, partialTicks, f7, netHeadYaw, headPitch, 1 / 16F);
        }

        GlStateManager.popMatrix();
        GlStateManager.popMatrix();

        // super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    public void doRender(EntityMaidBase entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (entity.getEntityData().hasKey(Reference.EFFECT_ABSORB))
            return;

        adjustDoRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected float getDeathMaxRotation(EntityMaidBase entityLivingBaseIn) {
        if (entityLivingBaseIn.getEntityData().hasKey(Reference.EFFECT_ABSORB))
            return 0.0F;
        else
            return super.getDeathMaxRotation(entityLivingBaseIn);
    }
}
