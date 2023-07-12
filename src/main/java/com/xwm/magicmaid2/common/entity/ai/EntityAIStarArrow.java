package com.xwm.magicmaid2.common.entity.ai;

import com.xwm.magicmaid.object.item.equipment.EquipmentAttribute;
import com.xwm.magicmaid.registry.MagicEquipmentRegistry;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidCarlie;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidCassiu;
import com.xwm.magicmaid2.common.entity.throwable.EntityEvilDeathBall;
import com.xwm.magicmaid2.common.entity.throwable.EntityHeavenArrow;
import com.xwm.magicmaid2.common.entity.throwable.EntityRing;
import com.xwm.magicmaid2.common.entity.throwable.EntityStarArrow;
import com.xwm.magicmaid2.core.enums.EnumState;
import com.xwm.magicmaid2.core.init.EquipmentInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class EntityAIStarArrow extends EntityAIBase
{
    public int COLD_TIME = 30;
    private World world;
    private EntityMagicMaidCarlie taskOwner;
    private int tick;
    private int performTick;
    private Random random;

    public EntityAIStarArrow(World world, EntityMagicMaidCarlie taskOwner) {
        this.world = world;
        this.taskOwner = taskOwner;
        this.random = new Random();
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {

        if (taskOwner != null && taskOwner.getAttackTarget() != null &&
                (MagicEquipmentRegistry.getAttribute(taskOwner.getWeaponType()) == EquipmentInit.STAR_ARROW || MagicEquipmentRegistry.getAttribute(taskOwner.getWeaponType()) == EquipmentInit.HEAVEN_ARROW))
        {
            return true;
        }

        return false;
    }

    public void startExecuting()
    {
        performTick = 0;
        tick = 0;
    }

    public boolean shouldContinueExecuting()
    {
        return shouldExecute() || !this.taskOwner.getNavigator().noPath() || performTick >= 20;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        taskOwner.setState(EnumState.toInt(EnumState.STANDARD));
        taskOwner.setIsPerformAttack(false);
        tick = 0;
        performTick = 0;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        EntityLivingBase target = taskOwner.getAttackTarget();

        if (target == null)
            return;


        double d = this.taskOwner.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ);
        boolean flag = this.taskOwner.getEntitySenses().canSee(target);

        if (flag && d < taskOwner.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue()*4)
        {
            tick++;
            this.taskOwner.getNavigator().clearPath();
        }
        else {
            this.taskOwner.getNavigator().tryMoveToEntityLiving(target, 1.0f);
            tick = 0;
        }

        if (tick >= 20) {

            if (taskOwner.isSkillReady(EnumState.SKILL1)) {
                performTick++;
            }
            if (performTick < 20) {
                this.taskOwner.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);
            }

            if (performTick == 1) {
                taskOwner.setState(EnumState.toInt(EnumState.SKILL1));
                this.taskOwner.setIsPerformAttack(true);
            }
        }
        else {
            performTick = 0;
        }


        if (performTick == 20 || performTick == 25 || performTick == 30)
        {
            this.taskOwner.faceEntity(target, 30.0F, 30.0F);
            EquipmentAttribute equipmentAttribute = MagicEquipmentRegistry.getAttribute(taskOwner.getWeaponType());
            EntityArrow arrow;
            if (equipmentAttribute.equals(EquipmentInit.STAR_ARROW))
            {
                arrow = new EntityStarArrow(world, taskOwner);
                ((EntityStarArrow) arrow).setType(random.nextInt(7));
                ((EntityStarArrow) arrow).setRank(taskOwner.getRank());
            }
            else
            {
                arrow = new EntityHeavenArrow(world, taskOwner);
                ((EntityHeavenArrow)arrow).setRank(taskOwner.getRank());
            }

            arrow.setLocationAndAngles(taskOwner.posX, taskOwner.posY + taskOwner.getEyeHeight(), taskOwner.posZ, taskOwner.rotationYaw, taskOwner.rotationPitch);

            double d0 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D;
            double d1 = target.posX - taskOwner.posX;
            double d2 = d0 - arrow.posY;
            double d3 = target.posZ - taskOwner.posZ;
            float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
            arrow.shoot(d1, d2 + (double)f, d3, 2.0F, 0F);
            world.spawnEntity(arrow);
        }

        if (performTick >= 30) {
            performTick = 0;
            taskOwner.setState(EnumState.toInt(EnumState.STANDARD));
            this.taskOwner.setIsPerformAttack(false);
        }
    }
}
