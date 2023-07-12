package com.xwm.magicmaid2.client.render;

import com.xwm.magicmaid2.client.render.util.LightningBoltRenderer;
import com.xwm.magicmaid2.common.entity.throwable.EntityEvilSwordPower;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

import javax.annotation.Nullable;
import java.util.Random;


@SideOnly(Side.CLIENT)
public class RenderEntityEvilSwordPower extends Render<EntityEvilSwordPower>
{
    private Sphere sphere = new Sphere();
    private Random random = new Random();


    protected RenderEntityEvilSwordPower(RenderManager renderManager) {
        super(renderManager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityEvilSwordPower entity) {
        return null;
    }

    @Override
    public void doRender(EntityEvilSwordPower entity, double x, double y, double z, float entityYaw, float partialTicks) {
//        super.doRender(entity, x, y, z, entityYaw, partialTicks);


        GlStateManager.disableLighting();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();

        float radiu = 0.2f;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y+0.1f, z);
        GlStateManager.color(0, 0, 0,0.7f);
        sphere.draw(radiu/3, 20, 20);
        GlStateManager.color(40.0f/255, 40.0f/255, 40.0f/255, 0.7f);
        sphere.draw(radiu*2/3, 20, 20);
        GlStateManager.color(55.0f/255, 55.0f/255, 55.0f/255, 0.7f);
        sphere.draw(radiu, 40, 40);

        GlStateManager.popMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();

    }
}
