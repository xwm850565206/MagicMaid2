package com.xwm.magicmaid2.common.entity.ai;

import com.xwm.magicmaid.registry.MagicEquipmentRegistry;
import com.xwm.magicmaid2.common.entity.throwable.EntityEvilDeathBall;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidCassiu;
import com.xwm.magicmaid2.core.enums.EnumState;
import com.xwm.magicmaid2.core.init.EquipmentInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class EntityAIEvilBall extends EntityAIBase
{
    private World world;
    private EntityMagicMaidCassiu taskOwner;
    private int tick;
    private int performTick;
    private Random random;

    private int seeTime = 0;

    public EntityAIEvilBall(World world, EntityMagicMaidCassiu taskOwner) {
        this.world = world;
        this.taskOwner = taskOwner;
        this.random = new Random();
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {

        double range = taskOwner.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
        if (taskOwner != null && taskOwner.getAttackTarget() != null && taskOwner.getDistanceSq(taskOwner.getAttackTarget()) < range*range)
        {
            if (!taskOwner.inventory.func_70301_a(0).isEmpty() && MagicEquipmentRegistry.getAttribute(taskOwner.getWeaponType()) == EquipmentInit.DARK_SOUL)
            {
                return true;
            }
        }

        return false;
    }

    public void startExecuting()
    {
        taskOwner.setIsPerformAttack(true);
        seeTime = 0;
        performTick = 0;
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
        seeTime = 0;
        tick = 0;
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

        if (flag && Math.sqrt(d) < taskOwner.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue())
        {
            seeTime++;
            this.taskOwner.getNavigator().clearPath();
        }
        else {
            this.taskOwner.getNavigator().tryMoveToEntityLiving(target, 1.0f);
            seeTime = 0;
        }


        if (seeTime >= 20) {
            if (taskOwner.isSkillReady(EnumState.SKILL2)) {
                performTick++;
            }
            if (performTick < 20) {
                this.taskOwner.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);
            }
        }

        if (performTick == 10) {
            taskOwner.setState(EnumState.toInt(EnumState.SKILL2));
        }

        if (performTick == 20 || performTick == 30 || performTick == 40)
        {
            this.taskOwner.faceEntity(target, 30.0F, 30.0F);
            EntityEvilDeathBall ball = new EntityEvilDeathBall(world, taskOwner);
            ball.posX = taskOwner.posX;
            ball.posY = taskOwner.posY + taskOwner.height / 2.0;
            ball.posZ = taskOwner.posZ;

            double d0 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D;
            double d1 = target.posX - taskOwner.posX;
            double d2 = d0 - ball.posY;
            double d3 = target.posZ - taskOwner.posZ;
            float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
            ball.shoot(d1, d2 + (double)f, d3, 0.8F, 0F);
            world.spawnEntity(ball);
        }

        if (performTick == 40) {
            performTick = 0;
            taskOwner.resetSkillCount(EnumState.SKILL2, 30 - taskOwner.getRank() * 15);
            taskOwner.setState(EnumState.toInt(EnumState.STANDARD));
        }

    }
}
