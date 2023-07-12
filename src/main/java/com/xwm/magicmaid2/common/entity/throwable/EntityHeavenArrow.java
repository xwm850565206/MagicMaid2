package com.xwm.magicmaid2.common.entity.throwable;

import com.xwm.magicmaid.manager.IMagicCreatureManagerImpl;
import com.xwm.magicmaid.manager.MagicEquipmentUtils;
import com.xwm.magicmaid2.core.init.PotionInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityHeavenArrow extends EntityArrow
{
    private static final DataParameter<Integer> RANK = EntityDataManager.<Integer>createKey(EntityStarArrow.class, DataSerializers.VARINT);

    public EntityHeavenArrow(World worldIn) {
        super(worldIn);
        this.pickupStatus = PickupStatus.DISALLOWED;
    }

    public EntityHeavenArrow(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.pickupStatus = PickupStatus.DISALLOWED;
    }

    public EntityHeavenArrow(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
        this.pickupStatus = PickupStatus.DISALLOWED;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(RANK, 0); // 0~2 from shooter
    }

    public int getRank()
    {
        return this.dataManager.get(RANK);
    }

    public void setRank(int rank)
    {
        this.dataManager.set(RANK, rank);
    }

    public void onUpdate()
    {
        super.onUpdate();

        if (this.world.isRemote && !this.inGround)
        {
            this.world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }

        if (ticksExisted > 200)
            this.setDead();
    }

    @Nullable
    @Override
    protected Entity findEntityOnPath(Vec3d start, Vec3d end) {
        Entity entity = super.findEntityOnPath(start, end);
        if (entity instanceof EntityLivingBase && shootingEntity instanceof EntityLivingBase)
            if (MagicEquipmentUtils.checkEnemy((EntityLivingBase) shootingEntity, (EntityLivingBase) entity))
                return entity;
        return null;
    }


    protected void arrowHit(EntityLivingBase living) {
        if (!MagicEquipmentUtils.checkEnemy((EntityLivingBase) shootingEntity, living))
            return;

        float damage = 4 + 2 * getRank();
        boolean flag = true;
        if (living instanceof EntityPlayerMP) {
            if (living.getHealth() <= damage) {
                living.addPotionEffect(new PotionEffect(PotionInit.POTION_HEAVEN, 200, 0));
                flag = false;
            } else if (living.getHealth() > 4 * damage) {
                ((EntityPlayer) living).inventory.dropAllItems();
                living.setHealth(living.getHealth() / 2);
            }
        }
        if (flag)
            IMagicCreatureManagerImpl.getInstance().attackEntityFrom(living, DamageSource.causeThrownDamage(this, shootingEntity).setDamageBypassesArmor(), damage);
        }


    @Override
    protected ItemStack getArrowStack() {
        return ItemStack.EMPTY;
    }

    public void spawnItemStack(World worldIn, double x, double y, double z, ItemStack stack)
    {
        float f = rand.nextFloat() * 0.8F + 0.1F;
        float f1 = rand.nextFloat() * 0.8F + 0.1F;
        float f2 = rand.nextFloat() * 0.8F + 0.1F;

        while (!stack.isEmpty())
        {
            EntityItem entityitem = new EntityItem(worldIn, x + (double)f, y + (double)f1, z + (double)f2, stack.splitStack(rand.nextInt(21) + 10));
            entityitem.setPickupDelay(10 * getRank() * 2);
            float f3 = 0.05F;
            entityitem.motionX = rand.nextGaussian() * 0.05000000074505806D;
            entityitem.motionY = rand.nextGaussian() * 0.05000000074505806D + 0.20000000298023224D;
            entityitem.motionZ = rand.nextGaussian() * 0.05000000074505806D;
            worldIn.spawnEntity(entityitem);
        }
    }
}
