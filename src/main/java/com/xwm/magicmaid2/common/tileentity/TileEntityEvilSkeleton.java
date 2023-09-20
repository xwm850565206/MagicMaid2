package com.xwm.magicmaid2.common.tileentity;

import com.xwm.magicmaid2.common.entity.mob.EntityEvilSkeleton;
import com.xwm.magicmaid2.core.init.BlockInit;
import com.xwm.magicmaid2.core.network.NetworkLoader;
import com.xwm.magicmaid2.core.network.packet.client.CPacketSpawnEntity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;

public class TileEntityEvilSkeleton extends TileEntity implements IAnimatable, ITickable
{
    private final AnimationFactory factory = new AnimationFactory(this);
    private int deadTick = -1;

    private Random random = new Random();

    private <E extends TileEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (this.hasWorld())
        {
            if (this.getWorld().getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 6, false) != null)
            {
                if (deadTick == -1)
                    deadTick = 0;
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.skeleton.awake_evil", false));
            }
        }

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory()
    {
        return factory;
    }


    @Override
    public void update()
    {
        if (deadTick != -1)
            deadTick++;

        if (deadTick >= 40)
        {
            for (int i = 0; i < 20; i++)
            {
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + random.nextFloat() - 0.5f, pos.getY() + random.nextFloat() - 0.5f, pos.getZ() + random.nextFloat() - 0.5f,
                        random.nextFloat() - 0.5f,  + random.nextFloat(), random.nextFloat() - 0.5f);
            }
        }

        if (deadTick >= 80)
        {
            boolean flag = true;
            int R = 3;
            for (int i = -R; i < R && flag; i++)
                for (int j = -R; j < R && flag; j++)
                {
                    if (world.getBlockState(pos.add(i, 0, j)).getBlock() == BlockInit.BLOC_HOLY_CROSS)
                        flag = false;
                }
            CPacketSpawnEntity packet = new CPacketSpawnEntity(getPos(), world.provider.getDimension(), flag);
            NetworkLoader.instance.sendToServer(packet);
            this.deadTick = -1;
        }

    }
}
