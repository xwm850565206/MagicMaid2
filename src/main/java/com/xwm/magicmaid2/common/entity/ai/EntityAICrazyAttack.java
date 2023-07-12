package com.xwm.magicmaid2.common.entity.ai;

import com.xwm.magicmaid.registry.MagicEquipmentRegistry;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidAili;
import com.xwm.magicmaid2.core.enums.EnumState;
import com.xwm.magicmaid2.core.init.EquipmentInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import com.xwm.magicmaid2.core.init.PotionInit;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import java.util.Random;

public class EntityAICrazyAttack extends EntityAIBase
{
    public int COLD_TIME = 150;
    private World world;
    private EntityMagicMaidAili taskOwner;
    private int tick;
    private int performTick;
    private Random random;

    public EntityAICrazyAttack(World world, EntityMagicMaidAili taskOwner) {
        this.world = world;
        this.taskOwner = taskOwner;
        this.random = new Random();
        this.COLD_TIME = 300 - taskOwner.getRank() * 50;
    }

    @Override
    public boolean shouldExecute() {

        double range = taskOwner.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
        if (taskOwner != null && taskOwner.getAttackTarget() != null && taskOwner.getDistanceSq(taskOwner.getAttackTarget()) < range)
        {
            if (!taskOwner.inventory.func_70301_a(0).isEmpty() && MagicEquipmentRegistry.getAttribute(taskOwner.getWeaponType()) == EquipmentInit.UNREAL_BALL)
                return true;
        }

        return false;
    }

    public void startExecuting()
    {
        taskOwner.setState(EnumState.toInt(EnumState.SKILL1));
        performTick = 0;
    }

    public boolean shouldContinueExecuting()
    {
        double range = taskOwner.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
        if (taskOwner!= null && taskOwner.getAttackTarget() != null && performTick < 40 && taskOwner.getDistanceSq(taskOwner.getAttackTarget()) < range)
            if (!taskOwner.inventory.func_70301_a(0).isEmpty() && taskOwner.inventory.func_70301_a(0).getItem() == ItemInit.UNREAL_BALL)
                return true;
        return false;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        taskOwner.resetSkillCount(EnumState.SKILL2, 30);
        taskOwner.setState(EnumState.toInt(EnumState.STANDARD));
        tick = 0;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        performTick++;
        if (performTick == 40)
        {
            EntityLivingBase target = this.taskOwner.getAttackTarget();
            if (target instanceof EntityPlayer)
            {
                EntityRabbit rabbit = new EntityRabbit(world);
                rabbit.setRabbitType(random.nextInt(99));
                rabbit.setPosition(target.posX + random.nextInt(6) - 3, target.posY, target.posZ + random.nextInt(6) - 3);
                if (!world.isRemote) {
                    world.spawnEntity(rabbit);
                    target.getEntityData().setInteger("crasy_rabbit", rabbit.getEntityId());
                }
                target.addPotionEffect(new PotionEffect(PotionInit.POTION_CRASY, 120, 1));
            }

            for (int i = 0; i < 3; i++)
            {
                EntityRabbit rabbit = new EntityRabbit(world);
                rabbit.targetTasks.addTask(2, new EntityAINearestAttackableTarget(rabbit, EntityLiving.class, true));;
                rabbit.setPosition(target.posX + random.nextInt(6) - 3, target.posY, target.posZ + random.nextInt(6) - 3);
                rabbit.setRabbitType(99);
                rabbit.setAttackTarget(target);
                if (!world.isRemote) {
                    world.spawnEntity(rabbit);
                }
            }
        }
    }
}
