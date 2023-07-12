package com.xwm.magicmaid2.client.render.tileentity;

import com.xwm.magicmaid2.client.model.entity.ModelCorpse;
import com.xwm.magicmaid2.common.block.BlockDedicationDead;
import com.xwm.magicmaid2.common.tileentity.TileEntityCorpse;
import com.xwm.magicmaid2.common.tileentity.TileEntityDedicationDead;
import com.xwm.magicmaid2.core.init.BlockInit;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class TileEntityDedicationDeadRenderer extends TileEntitySpecialRenderer<TileEntityDedicationDead>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/skeleton/skeleton.png");
    private ModelSkeleton model;
    private ModelPlayer small;
    private ModelPlayer normal;

    public TileEntityDedicationDeadRenderer() {
        super();
        this.model = new ModelSkeleton(){
            @Override
            public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
                this.bipedHead.render(scale);
                this.bipedBody.render(scale);
                this.bipedRightArm.render(scale);
                this.bipedLeftArm.render(scale);
                this.bipedRightLeg.render(scale);
                this.bipedLeftLeg.render(scale);
                this.bipedHeadwear.render(scale);
            }
        };

        this.model.bipedLeftLeg.rotateAngleX = -90*((float)Math.PI) / 180.0f;
        this.model.bipedRightLeg.rotateAngleX = -90*((float)Math.PI) / 180.0f;
        this.model.bipedLeftArm.rotateAngleX = -30*((float)Math.PI) / 180.0f;
        this.model.bipedRightArm.rotateAngleX = -30*((float)Math.PI) / 180.0f;

        this.small = new ModelPlayer(0.5f, true){
            @Override
            public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
                this.bipedHead.render(scale);
                this.bipedBody.render(scale);
                this.bipedRightArm.render(scale);
                this.bipedLeftArm.render(scale);
                this.bipedRightLeg.render(scale);
                this.bipedLeftLeg.render(scale);
                this.bipedHeadwear.render(scale);
                this.bipedLeftLegwear.render(scale);
                this.bipedRightLegwear.render(scale);
                this.bipedLeftArmwear.render(scale);
                this.bipedRightArmwear.render(scale);
                this.bipedBodyWear.render(scale);
            }
        };
        this.normal = new ModelPlayer(0.5f, false){
            @Override
            public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
                this.bipedHead.render(scale);
                this.bipedBody.render(scale);
                this.bipedRightArm.render(scale);
                this.bipedLeftArm.render(scale);
                this.bipedRightLeg.render(scale);
                this.bipedLeftLeg.render(scale);
                this.bipedHeadwear.render(scale);
                this.bipedLeftLegwear.render(scale);
                this.bipedRightLegwear.render(scale);
                this.bipedLeftArmwear.render(scale);
                this.bipedRightArmwear.render(scale);
                this.bipedBodyWear.render(scale);
            }
        };

        this.small.bipedLeftLeg.rotateAngleX = -90*((float)Math.PI) / 180.0f;
        this.small.bipedRightLeg.rotateAngleX = -90*((float)Math.PI) / 180.0f;
        this.small.bipedLeftArm.rotateAngleX = -30*((float)Math.PI) / 180.0f;
        this.small.bipedRightArm.rotateAngleX = -30*((float)Math.PI) / 180.0f;
        this.normal.bipedLeftLeg.rotateAngleX = -90*((float)Math.PI) / 180.0f;
        this.normal.bipedRightLeg.rotateAngleX = -90*((float)Math.PI) / 180.0f;
        this.normal.bipedLeftArm.rotateAngleX = -30*((float)Math.PI) / 180.0f;
        this.normal.bipedRightArm.rotateAngleX = -30*((float)Math.PI) / 180.0f;

        ModelPlayer.copyModelAngles(small.bipedLeftLeg, small.bipedLeftLegwear);
        ModelPlayer.copyModelAngles(small.bipedRightLeg, small.bipedRightLegwear);
        ModelPlayer.copyModelAngles(small.bipedLeftArm, small.bipedLeftArmwear);
        ModelPlayer.copyModelAngles(small.bipedRightArm, small.bipedRightArmwear);
        ModelPlayer.copyModelAngles(small.bipedBody, small.bipedBodyWear);

        ModelPlayer.copyModelAngles(normal.bipedLeftLeg, normal.bipedLeftLegwear);
        ModelPlayer.copyModelAngles(normal.bipedRightLeg, normal.bipedRightLegwear);
        ModelPlayer.copyModelAngles(normal.bipedLeftArm, normal.bipedLeftArmwear);
        ModelPlayer.copyModelAngles(normal.bipedRightArm, normal.bipedRightArmwear);
        ModelPlayer.copyModelAngles(normal.bipedBody, normal.bipedBodyWear);
    }

    @Override
    public void render(TileEntityDedicationDead te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);

        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.translate(x, y, z);
        GlStateManager.translate(0.5, 0.5, 0.5);
        GlStateManager.translate(0, 0.4, 0);
        GlStateManager.scale(1, -1, 1);
        GlStateManager.scale(0.1, 0.1, 0.1);
        GlStateManager.scale(0.6, 0.6, 0.6);
        switch (BlockInit.BLOCK_DEDICATION_DEAD.getMetaFromState(te.getWorld().getBlockState(te.getPos()))-2)
        {
            case 0:break;
            case 1:
                GlStateManager.rotate(180, 0, 1, 0);
                break;
            case 2:
                GlStateManager.rotate(90, 0, 1, 0);
                break;
            case 3:
                GlStateManager.rotate(-90, 0, 1, 0);
                break;
        }


        GlStateManager.color(1, 1, 1, 1);
        this.bindTexture(TEXTURE);
        model.render(null, 0, 0, 0, 0, 0, 1);


        GlStateManager.color(1, 1, 1, 0.4f);
        this.bindTexture(Minecraft.getMinecraft().player.getLocationSkin());
        if (Minecraft.getMinecraft().player.getSkinType().equals("default"))
        {
            normal.render(null, 0, 0, 0, 0, 0, 1);
        }
        else {
            small.render(null, 0, 0, 0, 0, 0, 1);
        }


        GlStateManager.enableCull();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();

    }
}
