package com.xwm.magicmaid2.common.entity.throwable;

import com.xwm.magicmaid.manager.IMagicCreatureManagerImpl;
import com.xwm.magicmaid.manager.MagicEquipmentUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public class EntityIce extends EntityThrowable
{

    private int strength = 1;

    public EntityIce(World worldIn) {
        super(worldIn);
        this.setNoGravity(true);
        this.setSize(0.25F, 0.15F);
    }

    public EntityIce(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.setNoGravity(true);
        this.setSize(0.25F, 0.15F);
    }

    public EntityIce(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
        this.setNoGravity(true);
        this.setSize(0.25F, 0.15F);
    }


    @Override
    public void onUpdate() {
        super.onUpdate();
        if (ticksExisted > 500)
            explode();
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if(!world.isRemote) {
            if (result.entityHit instanceof EntityLivingBase) {
                if (!MagicEquipmentUtils.checkEnemy(this.thrower, (EntityLivingBase) result.entityHit)) {
                    ;
                } else {
                    explode();
                }
            } else {
                explode();
            }
        }
    }

    public void explode()
    {
        List<EntityLivingBase> entitylivingBases = world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(1f));
        for (EntityLivingBase entityLivingBase : entitylivingBases)
        {
            if (MagicEquipmentUtils.checkEnemy(this.thrower, entityLivingBase))
            {
                try {
                    IMagicCreatureManagerImpl.getInstance().attackEntityFrom(entityLivingBase, DamageSource.causeMobDamage(thrower).setMagicDamage(), getStrength()*5);
                    entityLivingBase.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 5));
                    entityLivingBase.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 5));
                } catch (Exception e)
                {
                    ;
                }
            }

        }

        createExplosion(this.posX, this.posY, this.posZ);
        this.setDead();
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void createExplosion(double x, double y, double z)
    {
        BlockPos pos = new BlockPos(x, y, z);
        world.playEvent(2001, pos, Block.getStateId(Blocks.ICE.getDefaultState()));
    }
}
