package com.xwm.magicmaid2.common.entity.throwable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityRing extends EntityThrowable
{
    private boolean mainRing = false;

    public EntityRing(World worldIn) {
        super(worldIn);
        this.setNoGravity(true);
        this.setSize(1.0f, 1.0f);
    }

    public EntityRing(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.setNoGravity(true);
        this.setSize(1.0f, 1.0f);
    }

    public EntityRing(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
        this.setNoGravity(true);
        this.setSize(1.0f, 1.0f);
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void onImpact(RayTraceResult result) {

    }

    public void setMainRing()
    {
        this.mainRing = true;
    }

    public boolean isMainRing()
    {
        return this.mainRing;
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();

        if (mainRing && this.ticksExisted > 400)
            setDead();
        else if (ticksExisted > 60)
            setDead();
        else if (mainRing && this.ticksExisted % 10 == 0 && !world.isRemote)
        {
            EntityRing ring = new EntityRing(world, thrower);
            ring.setSize(width, height);
            ring.shoot(this, rotationPitch, rotationYaw, 0, 0.1f, 0);
            world.spawnEntity(ring);
        }

        if (mainRing && ticksExisted % 10 == 0)
        {
            this.setSize(width * 1.25f, height * 1.25f);
        }
    }
}
