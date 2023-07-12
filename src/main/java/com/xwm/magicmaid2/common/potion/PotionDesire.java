package com.xwm.magicmaid2.common.potion;

import com.google.common.collect.Maps;
import com.xwm.magicmaid.init.ItemInit;
import com.xwm.magicmaid.manager.IMagicCreatureManagerImpl;
import com.xwm.magicmaid.network.entity.SPacketCapabilityUpdate;
import com.xwm.magicmaid.player.MagicCreatureAttributes;
import com.xwm.magicmaid.player.capability.CapabilityLoader;
import com.xwm.magicmaid.player.capability.ICreatureCapability;
import com.xwm.magicmaid2.core.network.NetworkLoader;
import com.xwm.magicmaid2.core.network.packet.server.SPacketModifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.Sys;

import java.util.Map;
import java.util.UUID;

public class PotionDesire extends Potion
{

    private final Map<IAttribute, AttributeModifier> attributeModifierMap = Maps.<IAttribute, AttributeModifier>newHashMap();
    public PotionDesire() {
        super(false, 0x000000);
    }

    public Potion registerPotionAttributeModifier(IAttribute attribute, String uniqueId, double ammount, int operation)
    {
        super.registerPotionAttributeModifier(attribute, uniqueId, ammount, operation);
        AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(uniqueId), this.getName(), ammount, operation);
        this.attributeModifierMap.put(attribute, attributemodifier);
        return this;
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
        ICreatureCapability capability = entityLivingBaseIn.getCapability(CapabilityLoader.CREATURE_CAPABILITY, null);
        if (capability != null)
        {
            for (Map.Entry<IAttribute, AttributeModifier> entry : this.attributeModifierMap.entrySet()) {
                IAttributeInstance iattributeinstance = capability.getAttributeMap().getAttributeInstance(entry.getKey());

                if (iattributeinstance != null) {
                    AttributeModifier attributemodifier = this.attributeModifierMap.get(entry.getKey());
                    iattributeinstance.removeModifier(attributemodifier);
                    iattributeinstance.applyModifier(new AttributeModifier(attributemodifier.getID(), this.getName() + " " + amplifier, this.getAttributeModifierAmount(amplifier, attributemodifier), attributemodifier.getOperation()));
                }
            }

        }

        if (entityLivingBaseIn instanceof EntityPlayer)
            IMagicCreatureManagerImpl.getInstance().updateToClient((EntityPlayer) entityLivingBaseIn);
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
        ICreatureCapability capability = entityLivingBaseIn.getCapability(CapabilityLoader.CREATURE_CAPABILITY, null);
        if (capability != null) {
            for (Map.Entry<IAttribute, AttributeModifier> entry : this.attributeModifierMap.entrySet()) {
                IAttributeInstance iattributeinstance = capability.getAttributeMap().getAttributeInstance(entry.getKey());

                if (iattributeinstance != null) {
                    iattributeinstance.removeModifier(entry.getValue());

//                    SPacketModifier packet = new SPacketModifier(entityLivingBaseIn.getEntityId(), entityLivingBaseIn.dimension, true, SharedMonsterAttributes.writeAttributeModifierToNBT(entry.getValue()), 3);
//                    NetworkLoader.instance.sendToDimension(packet, entityLivingBaseIn.dimension);
                }
            }
        }


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
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
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
