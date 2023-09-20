package com.xwm.magicmaid2.core.network.packet.server;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class SPacketParticle6 implements IMessage
{
    private boolean messageValid;

    public double x, y, z;

    public EnumParticleTypes particleTypes;
    public double xSpeed = 0;
    public double ySpeed = 0;
    public double zSpeed = 0;

    public SPacketParticle6(){
        this.messageValid = false;
    }

    public SPacketParticle6(double x, double y, double z, double sx, double sy, double sz, EnumParticleTypes particleTypes)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xSpeed = sx;
        this.ySpeed = sy;
        this.zSpeed = sz;
        this.particleTypes = particleTypes;
        this.messageValid = true;
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        try
        {
            this.x = buf.readDouble();
            this.y = buf.readDouble();
            this.z = buf.readDouble();
            this.xSpeed = buf.readDouble();
            this.ySpeed = buf.readDouble();
            this.zSpeed = buf.readDouble();
            this.particleTypes = EnumParticleTypes.getParticleFromId(buf.readInt());
        }
        catch (IndexOutOfBoundsException e)
        {
            return;
        }
        this.messageValid = true;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        if(!this.messageValid)
            return;

        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeDouble(xSpeed);
        buf.writeDouble(ySpeed);
        buf.writeDouble(zSpeed);
        buf.writeInt(particleTypes.getParticleID());
    }

    public static class Handler implements IMessageHandler<SPacketParticle6, IMessage>
    {
        @Override
        public IMessage onMessage(SPacketParticle6 message, MessageContext ctx)
        {
            if (message.messageValid && ctx.side != Side.CLIENT)
                return null;

            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));

            return null;
        }

        private void processMessage(SPacketParticle6 message, MessageContext ctx){

            Minecraft.getMinecraft().world.spawnParticle(message.particleTypes, message.x, message.y, message.z, message.xSpeed, message.ySpeed, message.zSpeed);
        }
    }
}