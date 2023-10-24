package com.xwm.magicmaid2.core.network.packet.server;

import com.xwm.magicmaid2.client.task.RenderBeamSegmentTask;
import com.xwm.magicmaid2.client.task.RenderIceRegion;
import com.xwm.magicmaid2.client.task.RenderTaskManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;


public class SPacketAddRenderTaskIceRegion implements IMessage
{
    private int ownerId;
    private int maxAge;

    private int power;
    private double posX;
    private double posY;
    private double posZ;

    public SPacketAddRenderTaskIceRegion() {
    }

    public SPacketAddRenderTaskIceRegion(int ownerId, int maxAge, int power, double posX, double posY, double posZ) {
        this.ownerId = ownerId;
        this.maxAge = maxAge;
        this.power = power;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        ownerId = buf.readInt();
        maxAge = buf.readInt();
        power = buf.readInt();
        posX = buf.readDouble();
        posY = buf.readDouble();
        posZ = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(ownerId);
        buf.writeInt(maxAge);
        buf.writeInt(power);
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }

    public static class Handler implements IMessageHandler<SPacketAddRenderTaskIceRegion, IMessage>
    {

        @Override
        public IMessage onMessage(SPacketAddRenderTaskIceRegion message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT)
                return null;

            Entity entity = Minecraft.getMinecraft().world.getEntityByID(message.ownerId);
            RenderTaskManager.renderTasks.add(new RenderIceRegion(entity, message.maxAge, message.power, message.posX, message.posY, message.posZ));

            return null;
        }
    }
}
