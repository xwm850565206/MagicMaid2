package com.xwm.magicmaid2.common.entity;

import com.google.common.collect.Lists;
import com.xwm.magicmaid.manager.IMagicCreatureManagerImpl;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntitySoul extends EntityLivingBase
{
    private EntityLivingBase soulTarget;
    private EntityLivingBase performer;
    private int damage;

    public EntitySoul(World worldIn) {
        super(worldIn);
        this.setNoGravity(true); // 没有引力
    }

    public void init(EntityLivingBase performer, EntityLivingBase soulTarget, int damage)
    {
        this.setPerformer(performer);
        this.setSoulTarget(soulTarget);
        this.setDamage(damage);
        if (soulTarget != null)
            this.setSize(soulTarget.width, soulTarget.height);
    }

    public EntityLivingBase getSoulTarget() {
        return soulTarget;
    }

    public void setSoulTarget(EntityLivingBase soulTarget) {
        this.soulTarget = soulTarget;
    }

    public EntityLivingBase getPerformer() {
        return performer;
    }

    public void setPerformer(EntityLivingBase performer) {
        this.performer = performer;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.soulTarget = (EntityLivingBase) world.getEntityByID(compound.getInteger("soul_id"));
        this.performer = (EntityLivingBase) world.getEntityByID(compound.getInteger("performer_id"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if (performer != null)
            compound.setInteger("performer_id", performer.getEntityId());
        if (soulTarget != null)
            compound.setInteger("soul_id", soulTarget.getEntityId());
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (soulTarget == null && getEntityData().hasKey("soul_id"))
            soulTarget = (EntityLivingBase) world.getEntityByID(getEntityData().getInteger("soul_id"));

        if (soulTarget == null && this.ticksExisted > 20)
            this.setDead();
        else if (soulTarget != null) {
            if (!soulTarget.isEntityAlive() || this.ticksExisted > 80)
                this.setDead();

            if (world instanceof WorldServer && soulTarget != null)
            {
                for (int i = 0; i < 10; i++)
                {
                    float r = rand.nextFloat();
                    ((WorldServer)world).spawnParticle(EnumParticleTypes.PORTAL, posX + r * (soulTarget.posX - posX), posY  + r * (soulTarget.posY - posY), posZ + r * (soulTarget.posZ - posZ), 1, 0, 0, 0, 0f);
                }
            }

            if (!isEntityAlive())
            {
                if (this.damage > 0)
                {
                    if (world instanceof WorldServer)
                    {
                        for (int i = 0; i < this.ticksExisted; i++)
                        {
                            float r = 1;
                            ((WorldServer)world).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + r * rand.nextFloat() - r/2, posY + r* rand.nextFloat(), posZ + r * rand.nextFloat() - r/2, 1, 0, 0, 0, 0.6f);
                        }
                    }
                }

                if (soulTarget != null && soulTarget.isEntityAlive() && !world.isRemote)
                {
                    IMagicCreatureManagerImpl.getInstance().attackEntityFrom(soulTarget, DamageSource.causeMobDamage(performer).setDamageBypassesArmor(), damage * (this.ticksExisted / 20.0f));
                }
            }
        }

    }



    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
//        super.attackEntityFrom(source, amount);
        this.damage = 0;
        this.setDead();

        if (world instanceof WorldServer)
        {
            for (int i = 0; i < this.ticksExisted; i++)
            {
                float r = 1;
                ((WorldServer)world).spawnParticle(EnumParticleTypes.CRIT_MAGIC, posX + r * rand.nextFloat() - r/2, posY + r* rand.nextFloat(), posZ + r* rand.nextFloat() - r/2, 1, 0, 0, 0, 0.3f);
            }
        }

        return true;
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        if (this.soulTarget != null)
            return soulTarget.getArmorInventoryList();
        else
            return Lists.newArrayList();
    }

    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
        if (this.soulTarget != null)
            return soulTarget.getItemStackFromSlot(slotIn);
        else
            return ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
        if (this.soulTarget != null)
            soulTarget.setItemStackToSlot(slotIn, stack);
    }

    @Override
    public EnumHandSide getPrimaryHand() {
        if (this.soulTarget != null)
            return soulTarget.getPrimaryHand();
        else
            return EnumHandSide.RIGHT;
    }
}
