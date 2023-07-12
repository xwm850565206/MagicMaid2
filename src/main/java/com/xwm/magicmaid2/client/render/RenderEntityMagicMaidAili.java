package com.xwm.magicmaid2.client.render;

import com.xwm.magicmaid.object.item.equipment.EquipmentAttribute;
import com.xwm.magicmaid.registry.MagicEquipmentRegistry;
import com.xwm.magicmaid2.client.model.entity.ModelAili;
import com.xwm.magicmaid2.client.render.layer.LayerWeapon;
import com.xwm.magicmaid2.common.entity.maid.EntityMaidBase;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@SideOnly(Side.CLIENT)
public class RenderEntityMagicMaidAili extends RenderBase
{
    public RenderEntityMagicMaidAili(RenderManager renderManager) {
        super(renderManager, new ModelAili());
        this.shadowSize = 0.7F; //change 0.7 to the desired shadow size.
        this.addLayer(new LayerWeapon(this) {

            private Sphere sphere = new Sphere();
            @Override
            public void render(EntityMaidBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
                GeoModelProvider mainModelProvider = getEntityModel();
                GeoBone anchor = mainModelProvider.getModel(mainModelProvider.getModelLocation(entitylivingbaseIn)).getBone("right_arm_bone").get();

                EquipmentAttribute attribute = MagicEquipmentRegistry.getAttribute(entitylivingbaseIn.getWeaponType());
                if (attribute == MagicEquipmentRegistry.NONE)
                    return;
                Item weapon = attribute.getEquipment();
                if (weapon == ItemInit.UNREAL_BALL)
                {
                    GlStateManager.pushMatrix();

                    GlStateManager.disableTexture2D();
                    GlStateManager.translate(0.5*Math.sin(Math.toRadians(entitylivingbaseIn.ticksExisted * 5)),  entitylivingbaseIn.height / 2f + 0.05, 0.5*Math.cos(Math.toRadians(entitylivingbaseIn.ticksExisted * 5)));
                    GlStateManager.rotate(netHeadYaw, 0, 1, 0);

                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_DST_ALPHA, 1, 0);

                    GlStateManager.color(0.35f, 0.67f, 0.35f,0.6f);
                    sphere.draw(0.1f, 20, 20);

                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                }
                else
                {
                    GeoModel model = null;
                    model = modelProvider.getModel(modelProvider.getModelLocation(weapon));
                    Minecraft.getMinecraft().getTextureManager().bindTexture(modelProvider.getTextureLocation(weapon));

                    GlStateManager.enableBlend();
                    GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GlStateManager.pushMatrix();
                    IGeoRenderer.MATRIX_STACK.push();
                    IGeoRenderer.MATRIX_STACK.translate(anchor);
                    IGeoRenderer.MATRIX_STACK.moveToPivot(anchor);
                    IGeoRenderer.MATRIX_STACK.rotate(anchor);
                    IGeoRenderer.MATRIX_STACK.scale(anchor);
                    IGeoRenderer.MATRIX_STACK.moveBackFromPivot(anchor);

                    this.renderer.render(model, entitylivingbaseIn, partialTicks, 1, 1, 1, (float) Math.abs(Math.sin(Math.toRadians(entitylivingbaseIn.ticksExisted))));
                    IGeoRenderer.MATRIX_STACK.pop();

                    GlStateManager.popMatrix();
                    GlStateManager.disableBlend();
                }
            }
        });
    }
}
