package com.xwm.magicmaid2.client.render.layer;

import com.xwm.magicmaid.init.ItemInit;
import com.xwm.magicmaid2.client.model.weapon.ModelScythe;
import com.xwm.magicmaid2.common.entity.mob.EntityEvilSkeletonMaster;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

import java.nio.FloatBuffer;


@SideOnly(Side.CLIENT)
public class LayerWeaponEvilSkeletonMaster extends GeoLayerRenderer<EntityEvilSkeletonMaster> {

    protected AnimatedGeoModel modelProvider = new ModelScythe();
    protected IGeoRenderer renderer;
    protected int tick;
    protected ItemStack itemStack = new ItemStack(ItemInit.ITEM_THE_GOSPELS);
    protected FloatBuffer MODELVIEW = GLAllocation.createDirectFloatBuffer(16);

    public LayerWeaponEvilSkeletonMaster(IGeoRenderer<EntityEvilSkeletonMaster> entityRendererIn) {
        super(entityRendererIn);
        this.renderer = entityRendererIn;

    }

    @Override
    public void render(EntityEvilSkeletonMaster entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        GeoModelProvider mainModelProvider = getEntityModel();
        GeoBone anchor = mainModelProvider.getModel(mainModelProvider.getModelLocation(entitylivingbaseIn)).getBone("left_arm").get();


        GlStateManager.pushMatrix();

        apply(mainModelProvider.getModel(mainModelProvider.getModelLocation(entitylivingbaseIn)).getBone("upper").get());
        apply(anchor);
        GlStateManager.translate(-3/16.0, 14/16.0, -7/16.0);
        GlStateManager.rotate(90, 1, 0, 0);


        Minecraft.getMinecraft().getItemRenderer().renderItem(entitylivingbaseIn, itemStack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND);

        GlStateManager.popMatrix();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
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

    public void apply2(GeoBone anchor)
    {
        IGeoRenderer.MATRIX_STACK.push();
        IGeoRenderer.MATRIX_STACK.translate(anchor);
        IGeoRenderer.MATRIX_STACK.moveToPivot(anchor);
        IGeoRenderer.MATRIX_STACK.rotate(anchor);
        IGeoRenderer.MATRIX_STACK.scale(anchor);
        IGeoRenderer.MATRIX_STACK.moveBackFromPivot(anchor);
    }
}
