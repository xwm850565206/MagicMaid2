package com.xwm.magicmaid2.common.potion;

import com.xwm.magicmaid.init.ItemInit;
import com.xwm.magicmaid.player.MagicCreatureAttributes;
import com.xwm.magicmaid.player.capability.CapabilityLoader;
import com.xwm.magicmaid.player.capability.ICreatureCapability;
import com.xwm.magicmaid.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.Sys;

import java.util.UUID;

public class PotionEvil extends Potion
{
//    private static UUID UNIQUEID = UUID.randomUUID();

    public PotionEvil() {
        super(true, 0x000000);
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
        super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, amplifier);

//        ICreatureCapability capability = entityLivingBaseIn.getCapability(CapabilityLoader.CREATURE_CAPABILITY, null);
//        if (capability != null)
//        {
//            capability.getAttributeMap().getAttributeInstance(MagicCreatureAttributes.NORMAL_DAMAGE_RATE).applyModifier(
//                    new AttributeModifier(UNIQUEID, getName(), 0.5 * (1 + amplifier), 0)
//            );
//
//            capability.getAttributeMap().getAttributeInstance(MagicCreatureAttributes.PER_ENERGY).applyModifier(
//                    new AttributeModifier(UNIQUEID, getName(),  -1, 0)
//            );
//
//            System.out.println(capability.getAttributeMap().getAttributeInstance(MagicCreatureAttributes.PER_ENERGY).getAttributeValue());
//        }
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
        super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);

//        ICreatureCapability capability = entityLivingBaseIn.getCapability(CapabilityLoader.CREATURE_CAPABILITY, null);
//        if (capability != null)
//        {
//            capability.getAttributeMap().getAttributeInstance(MagicCreatureAttributes.NORMAL_DAMAGE_RATE).removeModifier(UNIQUEID);
//            capability.getAttributeMap().getAttributeInstance(MagicCreatureAttributes.SKILL_SPEED).removeModifier(UNIQUEID);
//        }
    }

    public boolean hasStatusIcon()
    {
        return true;
    }

    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
    {
        try {
            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend(); //Forge: Make sure blend is enabled else tabs show a white border.
            GlStateManager.color(1, 1, 1, 1);
            RenderHelper.enableGUIStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(ItemInit.ITEM_EVIL), x + 6, y + 8);
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        } catch (NullPointerException e){
            ;
        }
    }

    public boolean shouldRenderHUD(PotionEffect effect)
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc, float alpha) {
        try {
            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend(); //Forge: Make sure blend is enabled else tabs show a white border.
            GlStateManager.color(1, 1, 1, 1);
            RenderHelper.enableGUIStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(new ItemStack(ItemInit.ITEM_EVIL), x + 4, y + 4);
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        } catch (NullPointerException e){
            ;
        }
    }
}
