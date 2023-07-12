package com.xwm.magicmaid2.client.render;

import com.xwm.magicmaid2.client.render.util.LightningBoltRenderer;
import com.xwm.magicmaid2.common.entity.throwable.EntityEvilDeathBall;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

import javax.annotation.Nullable;
import java.util.Random;


@SideOnly(Side.CLIENT)
public class RenderEntityEvilBall extends Render<EntityEvilDeathBall>
{
    private Sphere sphere = new Sphere();
    private Random random = new Random();


    protected RenderEntityEvilBall(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityEvilDeathBall entity, double x, double y, double z, float entityYaw, float partialTicks) {
//        super.doRender(entity, x, y, z, entityYaw, partialTicks);

        GlStateManager.disableLighting();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA, 1, 0);

        float radiu = Math.min(entity.ticksExisted / 20.0f + 0.1f, 3f);
//        float radiu = 0.25f;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.color(1, 1, 1,1);
        sphere.draw(radiu/5, 50, 50);

        LightningBoltRenderer lightningBoltRenderer = entity.getLightningBoltRenderer();
        if (lightningBoltRenderer == null || lightningBoltRenderer.isDone())
        {
            lightningBoltRenderer = LightningBoltRenderer.generateLightingBolt(
                    new Vector3f(random.nextFloat()*0.2f - 0.1f, random.nextFloat()*0.2f - 0.1f, random.nextFloat()*0.2f - 0.1f),
                    new Vector3f(random.nextFloat()*0.2f - 0.1f, random.nextFloat()*0.2f - 0.1f, random.nextFloat()*0.2f - 0.1f),
                    200);
            entity.setLightningBoltRenderer(lightningBoltRenderer);
        }

        GlStateManager.pushMatrix();
        GlStateManager.color(1, 1, 1,1);

        lightningBoltRenderer.render();
        lightningBoltRenderer.update(40);

        GlStateManager.popMatrix();

        GlStateManager.color(160.0f/255, 32.0f/255, 240.0f/255, 0.65f);
        sphere.draw(radiu, 100, 100);

        GlStateManager.popMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();

    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityEvilDeathBall entity) {
        return null;
    }
}
