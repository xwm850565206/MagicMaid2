package com.xwm.magicmaid2.core.network.packet.server;

import com.xwm.magicmaid2.client.task.RenderDrivedRing;
import com.xwm.magicmaid2.client.task.RenderTaiJiTask;
import com.xwm.magicmaid2.client.task.RenderTaskManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;


public class SPacketAddRenderTaskDrived implements IMessage
{
    private int ownerId;
    private int maxAge;
    private double posX;
    private double posY;
    private double posZ;

    private double radius;

    public SPacketAddRenderTaskDrived() {
    }

    public SPacketAddRenderTaskDrived(int ownerId, int maxAge, double posX, double posY, double posZ, double radius) {
        this.ownerId = ownerId;
        this.maxAge = maxAge;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.radius = radius;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        ownerId = buf.readInt();
        maxAge = buf.readInt();
        posX = buf.readDouble();
        posY = buf.readDouble();
        posZ = buf.readDouble();
        radius = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(ownerId);
        buf.writeInt(maxAge);
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
        buf.writeDouble(radius);
    }

    public static class Handler implements IMessageHandler<SPacketAddRenderTaskDrived, IMessage>
    {

        @Override
        public IMessage onMessage(SPacketAddRenderTaskDrived message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT)
                return null;

            Entity entity = Minecraft.getMinecraft().world.getEntityByID(message.ownerId);
            RenderTaskManager.renderTasks.add(new RenderDrivedRing(entity, message.maxAge, message.posX, message.posY, message.posZ, message.radius));

            return null;
        }
    }
}
