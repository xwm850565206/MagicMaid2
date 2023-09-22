package com.xwm.magicmaid2.core.network.packet.client;

import com.xwm.magicmaid2.common.block.BlockEvilSkeleton;
import com.xwm.magicmaid2.common.entity.mob.EntityEvilSkeleton;
import io.netty.buffer.ByteBuf;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;
import java.util.UUID;

public class CPacketSpawnEntity implements IMessage {
    public BlockPos pos;
    public int dimension;
    public boolean spawn;

    public CPacketSpawnEntity() {
    }

    public CPacketSpawnEntity(BlockPos pos, int dimension, boolean spawn) {
        this.pos = pos;
        this.dimension = dimension;
        this.spawn = spawn;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        this.pos = packetBuffer.readBlockPos();
        this.dimension = packetBuffer.readInt();
        this.spawn = packetBuffer.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packetBuffer = new PacketBuffer(buf);
        packetBuffer.writeBlockPos(this.pos);
        packetBuffer.writeInt(this.dimension);
        packetBuffer.writeBoolean(this.spawn);
    }

    public static class Handler implements IMessageHandler<CPacketSpawnEntity, IMessage> {

        @Override
        public IMessage onMessage(CPacketSpawnEntity message, MessageContext ctx) {
            if (ctx.side != Side.SERVER)
                return null;

            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension);
            if (message.spawn)
            {
                EntityEvilSkeleton skeleton = new EntityEvilSkeleton(world);

                if (world.getBlockState(message.pos).canEntitySpawn(skeleton))
                    skeleton.setPosition(message.pos.getX(), message.pos.getY(), message.pos.getZ());
                else {
                    for (int x = -1; x <= 1; x++)
                        for (int z = -1; z <= 1; z++)
                        {
                            if (world.getBlockState(message.pos.add(x, 0, z)).canEntitySpawn(skeleton))
                                skeleton.setPosition(message.pos.getX() + x, message.pos.getY(), message.pos.getZ() + z);
                        }
                }

                world.spawnEntity(skeleton);
            }

            world.destroyBlock(message.pos, false);
            return null;
        }
    }
}