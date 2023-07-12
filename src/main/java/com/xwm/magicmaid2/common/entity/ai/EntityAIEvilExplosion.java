package com.xwm.magicmaid2.common.entity.ai;

import com.xwm.magicmaid.manager.IMagicCreatureManagerImpl;
import com.xwm.magicmaid.manager.MagicEquipmentUtils;
import com.xwm.magicmaid.registry.MagicEquipmentRegistry;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidCassiu;
import com.xwm.magicmaid2.core.enums.EnumState;
import com.xwm.magicmaid2.core.init.EquipmentInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import com.xwm.magicmaid2.core.init.PotionInit;
import com.xwm.magicmaid2.core.network.NetworkLoader;
import com.xwm.magicmaid2.core.network.packet.server.SPacketAddRenderTaskBeam;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class EntityAIEvilExplosion extends EntityAIBase
{
    private World world;
    private EntityMagicMaidCassiu taskOwner;
    private int performTick;
    private Random random;
    private double cx;
    private double cy;
    private double cz;

    public EntityAIEvilExplosion(World world, EntityMagicMaidCassiu taskOwner) {
        this.world = world;
        this.taskOwner = taskOwner;
        this.random = new Random();
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        double range = taskOwner.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
        if (taskOwner != null && taskOwner.getAttackTarget() != null && taskOwner.getDistanceSq(taskOwner.getAttackTarget()) < range && taskOwner.isSkillReady(EnumState.SKILL1))
        {
            if (!taskOwner.inventory.func_70301_a(0).isEmpty() && MagicEquipmentRegistry.getAttribute(taskOwner.getWeaponType()) == EquipmentInit.DARK_CLAW)
            {
                return true;
            }
        }

        return false;
    }

    public void startExecuting()
    {
        taskOwner.setState(EnumState.toInt(EnumState.SKILL1));
        taskOwner.setIsPerformAttack(true);
        performTick = 0;
    }

    public boolean shouldContinueExecuting()
    {
        double range = taskOwner.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue() * 2;
        if ((taskOwner != null && taskOwner.getAttackTarget() == null  && performTick > 15 && performTick < 60)
                || (taskOwner != null && taskOwner.getAttackTarget() != null && taskOwner.getDistanceSq(taskOwner.getAttackTarget()) < range && performTick < 60)) {
            if (!taskOwner.inventory.func_70301_a(0).isEmpty() && taskOwner.inventory.func_70301_a(0).getItem() == ItemInit.DARK_CLAW) {
                return true;
            }
        }

        return false;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        taskOwner.resetSkillCount(EnumState.SKILL2, 30);
        taskOwner.setState(EnumState.toInt(EnumState.STANDARD));
        taskOwner.setIsPerformAttack(false);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        performTick++;

        if (performTick == 10)
        {
            this.taskOwner.motionY += 1.33;
        }

        EntityLivingBase target = this.taskOwner.getAttackTarget();
        if (target == null)
            return;

        this.taskOwner.faceEntity(target, 30.0F, 30.0F);
        if (performTick == 15)
        {
            this.taskOwner.setPosition(target.posX, target.posY, target.posZ);
            AxisAlignedBB bb = target.getEntityBoundingBox();
            cx = (bb.maxX + bb.minX) / 2.0;
            cy = bb.minY;
            cz = (bb.maxZ + bb.minZ) / 2.0;
        }

        if (performTick >= 20 && performTick % 10 == 0)
        {
            int i = (performTick-20) / 10;

            int m = 4 + i * 2;
            double pi = Math.PI * 2 / m;
            double r = 2.0 + i;
            for (int j = 0; j < m; j++)
            {
                SPacketAddRenderTaskBeam packet = new SPacketAddRenderTaskBeam(taskOwner.getEntityId(),  20, cx + r * Math.sin(j * pi), cy, cz+ r * Math.cos(j * pi));
                NetworkLoader.instance.sendToDimension(packet, taskOwner.dimension);
            }

            List<EntityLivingBase> entityLivings = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(cx, cy, cz, cx, cy+10, cz).grow(r));
            for (EntityLivingBase entityLiving : entityLivings)
            {
                if (MagicEquipmentUtils.checkEnemy(taskOwner, entityLiving))
                {
                    entityLiving.motionY += 0.6799;
                    IMagicCreatureManagerImpl.getInstance().attackEntityFrom(entityLiving, DamageSource.causeMobDamage(taskOwner).setMagicDamage(), 2+2 * taskOwner.getRank());
                    entityLiving.addPotionEffect(new PotionEffect(PotionInit.POTION_DISTORT, 300,  taskOwner.getRank()));
                }
            }

        }
    }
}
