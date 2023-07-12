package com.xwm.magicmaid2.common.entity.throwable;

import com.xwm.magicmaid.manager.IMagicCreatureManagerImpl;
import com.xwm.magicmaid.manager.MagicEquipmentUtils;
import com.xwm.magicmaid2.client.render.util.LightningBoltRenderer;
import com.xwm.magicmaid2.core.init.PotionInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityEvilSwordPower extends EntityThrowable
{
    private int strength = 1;
    private float radius = 0.2f;

    public EntityEvilSwordPower(World worldIn) {
        super(worldIn);
        this.setNoGravity(true);
        this.setSize(radius, radius);
    }

    public EntityEvilSwordPower(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.setNoGravity(true);
        this.setSize(radius, radius);
    }

    public EntityEvilSwordPower(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
        this.setNoGravity(true);
        this.setSize(radius, radius);
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
                    IMagicCreatureManagerImpl.getInstance().attackEntityFrom(entityLivingBase, DamageSource.causeMobDamage(thrower).setMagicDamage(), 5);
                    entityLivingBase.addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 2));
                } catch (Exception e)
                {
                    ;
                }
            }

        }

        this.world.createExplosion(this, this.posX, this.posY + (double)(this.height / 16.0F), this.posZ, 0, true);
        this.setDead();
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
