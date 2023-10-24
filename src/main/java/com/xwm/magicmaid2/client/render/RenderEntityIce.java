package com.xwm.magicmaid2.client.render;

import com.xwm.magicmaid2.client.model.javamodel.ModelIce;
import com.xwm.magicmaid2.common.entity.throwable.EntityEvilSwordPower;
import com.xwm.magicmaid2.common.entity.throwable.EntityIce;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderWitherSkull;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

import javax.annotation.Nullable;
import java.util.Random;


@SideOnly(Side.CLIENT)
public class RenderEntityIce extends Render<EntityIce>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("magic_maid2:textures/entities/ice.png");

    private ModelBase ICE = new ModelIce();

    protected RenderEntityIce(RenderManager renderManager) {
        super(renderManager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityIce entity) {
        return TEXTURE;
    }


    public void doRender(EntityIce entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA, 1, 0);
        GlStateManager.pushMatrix();
        float f = entity.prevRotationYaw +  (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;
        float f1 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        GlStateManager.translate((float)x, (float)y, (float)z);
        float f2 = 0.0625F;
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
//        GlStateManager.rotate(90, 0, 1 ,0);
        GlStateManager.enableAlpha();
        this.bindEntityTexture(entity);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        this.ICE.render(entity, 0.0F, 0.0F, 0.0F, f, f1, f2);

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.disableBlend();

    }
}
