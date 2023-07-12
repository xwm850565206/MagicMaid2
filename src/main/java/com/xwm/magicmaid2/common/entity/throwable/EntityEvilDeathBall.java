package com.xwm.magicmaid2.common.entity.throwable;

import com.xwm.magicmaid.manager.IMagicCreatureManagerImpl;
import com.xwm.magicmaid.manager.MagicEquipmentUtils;
import com.xwm.magicmaid2.client.render.util.LightningBoltRenderer;
import com.xwm.magicmaid2.core.init.PotionInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityEvilDeathBall extends EntityThrowable
{
    @SideOnly(Side.CLIENT)
    private LightningBoltRenderer lightningBoltRenderer;

    private int strength = 1;
    private int deadTick = 60;

    public EntityEvilDeathBall(World worldIn) {
        super(worldIn);
        this.setNoGravity(true);
    }

    public EntityEvilDeathBall(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.setNoGravity(true);
    }

    public EntityEvilDeathBall(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
        this.setNoGravity(true);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        float radiu = Math.min(this.ticksExisted / 20.0f + 0.4f, 3f);
        this.setSize(radiu, radiu);

        if (!world.isRemote) {
            List<EntityLivingBase> entitylivingBases = world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(radiu * 2));
            for (EntityLivingBase entityLivingBase : entitylivingBases) {
                if (MagicEquipmentUtils.checkEnemy(thrower, entityLivingBase))
                    entityLivingBase.setPosition(posX, posY, posZ);
            }
        }

        if (ticksExisted > deadTick)
        {
            explode();
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {

    }

    public void explode()
    {
        List<EntityLivingBase> entitylivingBases = world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(strength*2));
        for (EntityLivingBase entityLivingBase : entitylivingBases)
        {
            if (entityLivingBase != thrower)
            {
                try {
                    IMagicCreatureManagerImpl.getInstance().attackEntityFrom(entityLivingBase, DamageSource.causeMobDamage(thrower).setMagicDamage(), strength*5);
                    entityLivingBase.addPotionEffect(new PotionEffect(PotionInit.POTION_DISTORT, 100, getStrength()));
                } catch (Exception e)
                {
                    ;
                }
            }

        }

        this.world.createExplosion(this, this.posX, this.posY + (double)(this.height / 16.0F), this.posZ, strength, true);
        this.setDead();
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDeadTick() {
        return deadTick;
    }

    public void setDeadTick(int deadTick) {
        this.deadTick = deadTick;
    }

    @SideOnly(Side.CLIENT)
    public LightningBoltRenderer getLightningBoltRenderer() {
        return lightningBoltRenderer;
    }

    @SideOnly(Side.CLIENT)
    public void setLightningBoltRenderer(LightningBoltRenderer lightningBoltRenderer) {
        this.lightningBoltRenderer = lightningBoltRenderer;
    }
}
