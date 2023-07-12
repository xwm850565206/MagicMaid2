package com.xwm.magicmaid2.client.render.layer;

import com.xwm.magicmaid.object.item.equipment.EquipmentAttribute;
import com.xwm.magicmaid.registry.MagicEquipmentRegistry;
import com.xwm.magicmaid2.client.model.weapon.ModelWeaponHand;
import com.xwm.magicmaid2.common.entity.maid.EntityMaidBase;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;


@SideOnly(Side.CLIENT)
public class LayerWeapon extends GeoLayerRenderer<EntityMaidBase> {

    protected AnimatedGeoModel modelProvider = new ModelWeaponHand();
    protected IGeoRenderer renderer;
    protected int tick;

    public LayerWeapon(IGeoRenderer<EntityMaidBase> entityRendererIn) {
        super(entityRendererIn);
        this.renderer = entityRendererIn;

    }

    @Override
    public void render(EntityMaidBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        GeoModelProvider mainModelProvider = getEntityModel();
        GeoBone anchor = mainModelProvider.getModel(mainModelProvider.getModelLocation(entitylivingbaseIn)).getBone("right_arm_bone").get();

        EquipmentAttribute attribute = MagicEquipmentRegistry.getAttribute(entitylivingbaseIn.getWeaponType());
        if (attribute == MagicEquipmentRegistry.NONE)
            return;
        Item weapon = attribute.getEquipment();
        GeoModel model = null;
        model = modelProvider.getModel(modelProvider.getModelLocation(weapon));
        Minecraft.getMinecraft().getTextureManager().bindTexture(modelProvider.getTextureLocation(weapon));

        GlStateManager.pushMatrix();
        IGeoRenderer.MATRIX_STACK.push();
        IGeoRenderer.MATRIX_STACK.translate(anchor);
        IGeoRenderer.MATRIX_STACK.moveToPivot(anchor);
        IGeoRenderer.MATRIX_STACK.rotate(anchor);
        IGeoRenderer.MATRIX_STACK.scale(anchor);
        IGeoRenderer.MATRIX_STACK.moveBackFromPivot(anchor);

        this.renderer.render(model, entitylivingbaseIn, partialTicks, 1, 1, 1, 1);
        IGeoRenderer.MATRIX_STACK.pop();

        GlStateManager.popMatrix();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
