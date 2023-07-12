package com.xwm.magicmaid2.common.entity.ai;

import com.xwm.magicmaid.manager.MagicEquipmentUtils;
import com.xwm.magicmaid.registry.MagicEquipmentRegistry;
import com.xwm.magicmaid2.core.init.EquipmentInit;
import com.xwm.magicmaid2.core.network.NetworkLoader;
import com.xwm.magicmaid.registry.MagicRenderRegistry;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidXimo;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidXimoBoss;
import com.xwm.magicmaid2.core.enums.EnumState;
import com.xwm.magicmaid2.core.network.packet.server.SPacketAddRenderTaskDrived;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityAICreateDrivedRegion extends EntityAIBase
{
    private World world;
    private EntityMagicMaidXimo taskOwner;
    private int tick;
    private int performTick;
    private Random random;

    private AxisAlignedBB bb;

    private int seeTime = 0;

    private int areaId;

    public EntityAICreateDrivedRegion(World world, EntityMagicMaidXimo taskOwner) {
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
            if (!taskOwner.inventory.func_70301_a(0).isEmpty() && MagicEquipmentRegistry.getAttribute(taskOwner.getWeaponType()) == EquipmentInit.EAGER)
            {
                return !taskOwner.isDesire();
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
        boolean flag = shouldExecute();
        return  (performTick > 0 && performTick <= 340) || flag;
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

        if (flag && Math.sqrt(d) < 20)
        {
            seeTime++;
//            this.taskOwner.getNavigator().clearPath();
        }
        else {
            if (performTick == 0) { // 开始放技能了就得放完
                if (Math.sqrt(d) >= 20 || Math.abs(taskOwner.posY - taskOwner.getAttackTarget().posY) < 5) {
                    double d0 = this.taskOwner.getAttackTarget().posX;
                    double d1 = this.taskOwner.getAttackTarget().posY + 5;
                    double d2 = this.taskOwner.getAttackTarget().posZ;
                    this.taskOwner.getMoveHelper().setMoveTo(d0, d1, d2, 1.5D);
                }
                seeTime = 0;
            }
        }

        if ((seeTime+performTick) % 40 == 0)
        {
            AxisAlignedBB tbb = taskOwner.getEntityBoundingBox().grow(2);
            List<EntityLivingBase> entityLivingBases = world.getEntitiesWithinAABB(EntityLivingBase.class, tbb);
            for (EntityLivingBase entityLivingBase : entityLivingBases) {
                if (MagicEquipmentUtils.checkEnemy(taskOwner, entityLivingBase)){ // 有敌人靠近 躲避
                    double d0 = this.taskOwner.posX + random.nextInt(8) - 4;
                    double d1 = this.taskOwner.posY + random.nextInt(6) - 2;
                    double d2 = this.taskOwner.posZ + random.nextInt(8) - 4;
                    this.taskOwner.getMoveHelper().setMoveTo(d0, d1, d2, 3.0D);
                    break;
                }
            }
        }


        if (seeTime >= 20) {
            if (taskOwner.isSkillReady(EnumState.SKILL2)) {
                performTick++;
            }
            this.taskOwner.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);
        }

        if (performTick == 20) {
            taskOwner.setState(EnumState.toInt(EnumState.SKILL2));
            if (this.taskOwner instanceof EntityMagicMaidXimoBoss) {
                bb = this.taskOwner.getEntityBoundingBox().grow(10+this.taskOwner.getRank()*5).grow(0, 100, 0);
                areaId = MagicRenderRegistry.allocateArea();
                ((EntityMagicMaidXimoBoss) this.taskOwner).createWarningArea(areaId, bb);
            }
        }

        if (performTick == 40) {
            bb = this.taskOwner.getEntityBoundingBox().grow(10+this.taskOwner.getRank()*5).grow(0, 100, 0);
            if (this.taskOwner instanceof EntityMagicMaidXimoBoss)
                ((EntityMagicMaidXimoBoss) this.taskOwner).removeWarningArea(areaId);
            double ty = taskOwner.posY;
            while (ty > 0) {
                if (world.getBlockState(new BlockPos(taskOwner.posX, ty, taskOwner.posZ)).getBlock() == Blocks.AIR)
                    ty--;
                else
                    break;
            }
            taskOwner.setDesire(true);
            taskOwner.setState(EnumState.toInt(EnumState.STANDARD));
            SPacketAddRenderTaskDrived packet = new SPacketAddRenderTaskDrived(taskOwner.getEntityId(), 1200, taskOwner.posX, ty+1, taskOwner.posZ, 20 + 10*taskOwner.getLevel());
            NetworkLoader.instance.sendToAll(packet);
        }

        if (performTick >= 40 && performTick < 340)
        {
           List<EntityLivingBase> mobs = world.getEntitiesWithinAABB(EntityLivingBase.class, bb);

            if (performTick % 20 == 0)
                this.taskOwner.playSound(SoundEvents.ENTITY_ENDERDRAGON_SHOOT, 5, 1);

            for (EntityLivingBase mob : mobs) {

                if (mob instanceof EntityPlayerMP) {
                    if (((EntityPlayerMP) mob).isCreative())
                        continue;
                }

                if (MagicEquipmentUtils.checkEnemy(this.taskOwner, mob)) {
                    if (mob instanceof EntityCreature) {
                        EntityLivingBase tg = mobs.get(random.nextInt(mobs.size()));
                        if (tg != mob && tg != taskOwner)
                            ((EntityCreature) mob).setAttackTarget(tg);
                        try {
                            mob.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(new AttributeModifier("ximo_drived", 1, 2)); // 攻击力翻倍
                        } catch (NullPointerException e) {
                            ;
                        }
                    }

                    if (mob instanceof EntityPlayerMP) {
                        List<EntityLiving> entityLivings = world.getEntitiesWithinAABB(EntityLiving.class, mob.getEntityBoundingBox().grow(1));
                        if (!entityLivings.isEmpty()) {
                            ((EntityPlayerMP) mob).attackTargetEntityWithCurrentItem(entityLivings.get(0));
                        }
                    }
                }
            }

            if (performTick % 40 == 0) {
                EntityLivingBase entityLivingBase = this.taskOwner.getAttackTarget();
                if (entityLivingBase != null) {
                    double x = entityLivingBase.posX + random.nextFloat() * 2 - 1;
                    double y = entityLivingBase.posY + random.nextFloat() * 2;
                    double z = entityLivingBase.posZ + random.nextFloat() * 2 - 1;
                    world.playEvent(2006, new BlockPos(x, y, z), 0);
                    try {
                        entityLivingBase.attackEntityFrom(DamageSource.causeMobDamage(this.taskOwner).setMagicDamage().setDamageBypassesArmor(), 4 * this.taskOwner.getRank() + 2);
                    } catch (Exception e) {
                        ;
                    }
                }
            }
        }


        if (performTick == 340) {
            taskOwner.resetSkillCount(EnumState.SKILL2, 100 - taskOwner.getRank() * 10);
            taskOwner.setState(EnumState.toInt(EnumState.STANDARD));
        }
    }
}
