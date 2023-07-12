package com.xwm.magicmaid2.core.network.packet.client;

import com.xwm.magicmaid2.client.task.RenderBeamSegmentTask;
import com.xwm.magicmaid2.client.task.RenderTaskManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;
import java.util.UUID;

public class CPacketGiveItem implements IMessage
{
    private ItemStack stack;
    private UUID entityId;

    public CPacketGiveItem() {
    }

    public CPacketGiveItem(ItemStack stack, UUID entityId) {
        this.stack = stack;
        this.entityId = entityId;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        try {
            this.stack = packetBuffer.readItemStack();
        } catch (IOException e) {
            ;
        }

        this.entityId = packetBuffer.readUniqueId();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        packetBuffer.writeItemStack(this.stack);
        packetBuffer.writeUniqueId(this.entityId);
    }

    public static class Handler implements IMessageHandler<CPacketGiveItem, IMessage>
    {

        @Override
        public IMessage onMessage(CPacketGiveItem message, MessageContext ctx) {
            if (ctx.side != Side.SERVER)
                return null;

            EntityPlayerMP playerMP = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(message.entityId);
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(playerMP.dimension);
            EntityItem entityItem = new EntityItem(world, playerMP.posX, playerMP.posY, playerMP.posZ);
            entityItem.setItem(message.stack);
            entityItem.setDefaultPickupDelay();
            world.spawnEntity(entityItem);
            return null;
        }
    }
}
