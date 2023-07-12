package com.xwm.magicmaid2.common.world.dimension;

import com.xwm.magicmaid.entity.mob.basic.AbstractEntityMagicCreature;
import com.xwm.magicmaid.manager.IMagicBossManagerImpl;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidXimoBoss;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;

public class ITemporaryMagicBossManagerImpl extends IMagicBossManagerImpl {

    private int tickCount = 0;
    private EntityMagicMaidXimoBoss ximo = null;
    private static final int MAX_TICK_COUNT = 20*600;
    public ITemporaryMagicBossManagerImpl(WorldServer worldIn, NBTTagCompound compound) {
        super(worldIn, compound);
    }

    @Override
    public void tick() {
        super.tick();

        if (ximo != null && getBossAlive()) // 汐墨存在
            tickCount++;
        else
            tickCount = 0;

        if (tickCount > 0 && tickCount % 1200 == 0) {
            for (EntityPlayer player : playerList) {
                if (player.dimension == ximo.dimension)
                    player.sendMessage(new TextComponentString("距离boss消失还有" + 100 * (1 - tickCount*1.0f/MAX_TICK_COUNT) + "%"));
            }
        }


        if (tickCount > MAX_TICK_COUNT && ximo != null && getBossAlive())
        {
            this.setBossAlive(false);
            this.setBoss(null);

            for (EntityPlayer player : playerList) {
                if (player.dimension == ximo.dimension)
                    player.sendMessage(new TextComponentString("未在时间内消灭boss，boss自动消失"));
            }
            ximo.setPosition(ximo.posX, 255, ximo.posZ);
            ximo.killSelf();
        }



    }

    @Override
    public void init(AbstractEntityMagicCreature boss) {
        super.init(boss);
        if (boss instanceof EntityMagicMaidXimoBoss)
            this.ximo = (EntityMagicMaidXimoBoss) boss;
    }
}
