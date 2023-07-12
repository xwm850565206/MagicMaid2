package com.xwm.magicmaid2.core.network.packet.server;

import com.xwm.magicmaid.player.MagicCreatureAttributes;
import com.xwm.magicmaid.player.capability.CapabilityLoader;
import com.xwm.magicmaid.player.capability.ICreatureCapability;
import com.xwm.magicmaid2.client.task.RenderTaiJiTask;
import com.xwm.magicmaid2.client.task.RenderTaskManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

public class SPacketModifier implements IMessage
{
    private int entityID;
    private int dimension;
    private boolean remove;
    private NBTTagCompound modifier;

    private int type;

    public SPacketModifier() {
    }

    public SPacketModifier(int entityID, int dimension, boolean remove, NBTTagCompound modifier, int type) {
        this.entityID = entityID;
        this.dimension = dimension;
        this.remove = remove;
        this.modifier = modifier;
        this.type = type;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        this.entityID = buffer.readInt();
        this.dimension = buffer.readInt();
        this.remove = buffer.readBoolean();
        try {
            this.modifier = buffer.readCompoundTag();
        } catch (IOException e) {
           ;
        }

        this.type = buffer.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        buffer.writeInt(this.entityID);
        buffer.writeInt(this.dimension);
        buffer.writeBoolean(this.remove);
        buffer.writeCompoundTag(this.modifier);
        buffer.writeInt(this.type);
    }

    public static class Handler implements IMessageHandler<SPacketModifier, IMessage>
    {

        @Override
        public IMessage onMessage(SPacketModifier message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT)
                return null;

            Entity entity = Minecraft.getMinecraft().world.getEntityByID(message.entityID);
            if (entity == null)
                return null;
            AttributeModifier attributeModifier = SharedMonsterAttributes.readAttributeModifierFromNBT(message.modifier);
            ICreatureCapability capability = entity.getCapability(CapabilityLoader.CREATURE_CAPABILITY, null);
            if (capability != null && attributeModifier != null) {
                if (message.remove) {
                    capability.getAttributeMap().getAttributeInstance(getAttributeById(message.type)).removeModifier(attributeModifier);
                }
                else {
                    capability.getAttributeMap().getAttributeInstance(getAttributeById(message.type)).applyModifier(attributeModifier);

                }
            }
            return null;
        }

        public IAttribute getAttributeById(int id)
        {
            switch (id) {
                case 0:
                    return MagicCreatureAttributes.NORMAL_DAMAGE_RATE;
                case 1:
                    return MagicCreatureAttributes.SKILL_DAMAGE_RATE;
                case 2:
                    return MagicCreatureAttributes.ENERGY;
                case 3:
                    return MagicCreatureAttributes.PER_ENERGY;
                case 4:
                    return MagicCreatureAttributes.MAX_ENERGY;
                case 5:
                    return MagicCreatureAttributes.SKILL_SPEED;
                case 6:
                    return MagicCreatureAttributes.INJURY_REDUCTION;
                case 7:
                    return MagicCreatureAttributes.IGNORE_REDUCTION;
                default:
                    return MagicCreatureAttributes.NORMAL_DAMAGE_RATE;
            }
        }
    }
}
