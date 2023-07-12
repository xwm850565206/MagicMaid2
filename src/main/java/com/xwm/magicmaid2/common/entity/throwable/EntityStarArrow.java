package com.xwm.magicmaid2.common.entity.throwable;

import com.xwm.magicmaid.entity.mob.basic.interfaces.IEntityAvoidThingCreature;
import com.xwm.magicmaid.manager.MagicEquipmentUtils;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidCarlie;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityStarArrow extends EntityArrow
{
    public static final float[][] COLORS = {
        {255, 0, 0}, {255, 97, 0}, {255, 255, 0}, {0, 255, 0}, {0, 255, 255}, {0, 0, 255}, {160, 32, 240}
    };
    private static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>createKey(EntityStarArrow.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> RANK = EntityDataManager.<Integer>createKey(EntityStarArrow.class, DataSerializers.VARINT);

    public EntityStarArrow(World worldIn) {
        super(worldIn);
        this.pickupStatus = PickupStatus.DISALLOWED;
    }

    public EntityStarArrow(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.pickupStatus = PickupStatus.DISALLOWED;
    }

    public EntityStarArrow(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
        this.pickupStatus = PickupStatus.DISALLOWED;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(TYPE, 0); // 0~6 for 7 kind color and effect
        this.dataManager.register(RANK, 0); // 0~2 from shooter
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

    public int getType()
    {
        return this.dataManager.get(TYPE);
    }

    public void setType(int type)
    {
        this.dataManager.set(TYPE, type);
    }

    public int getRank()
    {
        return this.dataManager.get(RANK);
    }

    public void setRank(int rank)
    {
        this.dataManager.set(RANK, rank);
    }

    protected void arrowHit(EntityLivingBase living)
    {
        if (!MagicEquipmentUtils.checkEnemy((EntityLivingBase) shootingEntity, living))
            return;

        living.attackEntityFrom(DamageSource.causeThrownDamage(this, shootingEntity), 3 +  getRank());
        System.out.println(getType());
        try {
            switch (getType())
            {
                case 0:
                    living.setFire(10);
                    break;
                case 1:
                    if (!(living instanceof IEntityAvoidThingCreature))
                        living.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)
                                .applyModifier(new AttributeModifier(SharedMonsterAttributes.MAX_HEALTH.getName(), -living.getMaxHealth() * (0.1 + 0.25 * getRank()), 0));
                    break;
                case 2:
                    living.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
                            .applyModifier(new AttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), -(0.1 + 0.25 * getRank()), 1));
                    break;
                case 3:
                    living.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE)
                            .applyModifier(new AttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), -(0.5 + 0.5 * getRank()), 0));
                    break;
                case 4:
                    if (living instanceof EntityPlayer)
                        ((EntityPlayer) living).getFoodStats().setFoodLevel(0);
                    break;
                case 5:
                    if (living instanceof EntityPlayer)
                    {
                        for (int i = 0; i < ((EntityPlayer) living).inventory.armorInventory.size(); ++i)
                        {
                            ItemStack itemstack = ((EntityPlayer) living).inventory.armorInventory.get(i);

                            if (!itemstack.isEmpty()) {
                                spawnItemStack(living.world, living.posX, living.posY, living.posZ, itemstack);
                            }
                        }
                        ((EntityPlayer) living).inventory.armorInventory.clear();
                    }
                    break;
                case 6:
                    EntityLightningBolt bolt = new EntityLightningBolt(world, living.posX, living.posY, living.posZ, false);
                    world.addWeatherEffect(bolt);
                    if (!world.isRemote)
                        world.spawnEntity(bolt);
                    break;
            }
        } catch (NullPointerException e)
        {
            ;
        }
    }

    @Override
    protected ItemStack getArrowStack() {
        return ItemStack.EMPTY;
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

    public void spawnItemStack(World worldIn, double x, double y, double z, ItemStack stack)
    {
        float f = rand.nextFloat() * 0.8F + 0.1F;
        float f1 = rand.nextFloat() * 0.8F + 0.1F;
        float f2 = rand.nextFloat() * 0.8F + 0.1F;

        while (!stack.isEmpty())
        {
            EntityItem entityitem = new EntityItem(worldIn, x + (double)f, y + (double)f1, z + (double)f2, stack.splitStack(rand.nextInt(21) + 10));
            entityitem.setDefaultPickupDelay();
            float f3 = 0.05F;
            entityitem.motionX = rand.nextGaussian() * 0.05000000074505806D;
            entityitem.motionY = rand.nextGaussian() * 0.05000000074505806D + 0.20000000298023224D;
            entityitem.motionZ = rand.nextGaussian() * 0.05000000074505806D;
            worldIn.spawnEntity(entityitem);
        }
    }
}
