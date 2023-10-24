package com.xwm.magicmaid2.client.render;

import com.xwm.magicmaid.object.item.equipment.EquipmentAttribute;
import com.xwm.magicmaid.registry.MagicEquipmentRegistry;
import com.xwm.magicmaid2.client.model.entity.ModelNeva;
import com.xwm.magicmaid2.client.render.item.ItemEagerRenderer;
import com.xwm.magicmaid2.client.render.layer.LayerNovaIce;
import com.xwm.magicmaid2.client.render.layer.LayerWeapon;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidXimo;
import com.xwm.magicmaid2.common.entity.maid.EntityMaidBase;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEntityMagicMaidNeva extends RenderBase {
    public RenderEntityMagicMaidNeva(RenderManager renderManager) {
        super(renderManager, new ModelNeva());
        this.shadowSize = 0.7F * 0.8F;
        this.addLayer(new LayerWeapon(this) {

            private ItemEagerRenderer eagerRenderer = new ItemEagerRenderer();
            @Override
            public void render(EntityMaidBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
                EquipmentAttribute attribute = MagicEquipmentRegistry.getAttribute(entitylivingbaseIn.getWeaponType());
                if (attribute == MagicEquipmentRegistry.NONE)
                    return;
                Item weapon = attribute.getEquipment();
                if (weapon == ItemInit.EAGER)
                {
                    eagerRenderer.renderByItem((EntityMagicMaidXimo) entitylivingbaseIn);
                }
            }
        });
        this.addLayer(new LayerNovaIce(this));
    }

    @Override
    public void doRender(EntityMaidBase entity, double x, double y, double z, float entityYaw, float partialTicks) {

        if (entity.isInvisible())
            return;

        float scale = 0.8f;

        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.translate(x / scale - x, y / scale - y, z / scale - z);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        GlStateManager.popMatrix();
    }
}
