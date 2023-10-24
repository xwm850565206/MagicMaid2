package com.xwm.magicmaid2.core.network;

import com.xwm.magicmaid2.core.network.packet.client.CPacketGiveItem;
import com.xwm.magicmaid2.core.network.packet.client.CPacketSpawnEntity;
import com.xwm.magicmaid2.core.network.packet.server.*;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkLoader
{
    public static SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);

    private static int ID = 0;

    public static void registerPackets(FMLPreInitializationEvent event)
    {
        registerMessage(SPacketAddRenderTaskBeam.Handler.class, SPacketAddRenderTaskBeam.class, Side.CLIENT);
        registerMessage(SPacketAddRenderTaskTaiJi.Handler.class, SPacketAddRenderTaskTaiJi.class, Side.CLIENT);
        registerMessage(SPacketAddRenderTaskDrived.Handler.class, SPacketAddRenderTaskDrived.class, Side.CLIENT);
        registerMessage(SPacketAddRenderTaskIceRegion.Handler.class, SPacketAddRenderTaskIceRegion.class, Side.CLIENT);
        registerMessage(SPacketModifier.Handler.class, SPacketModifier.class, Side.CLIENT);
        registerMessage(SPacketParticle6.Handler.class, SPacketParticle6.class, Side.CLIENT);

        registerMessage(CPacketGiveItem.Handler.class, CPacketGiveItem.class, Side.SERVER);
        registerMessage(CPacketSpawnEntity.Handler.class, CPacketSpawnEntity.class, Side.SERVER);
    }

    private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(
            Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side)
    {
        instance.registerMessage(messageHandler, requestMessageType, ID++, side);
    }
}
