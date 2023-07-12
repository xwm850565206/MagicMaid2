package com.xwm.magicmaid2.common.entity.ai;

import com.xwm.magicmaid.manager.MagicEquipmentUtils;
import com.xwm.magicmaid.network.NetworkLoader;
import com.xwm.magicmaid.network.particle.SPacketParticle;
import com.xwm.magicmaid.registry.MagicEquipmentRegistry;
import com.xwm.magicmaid.registry.MagicRenderRegistry;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidXimo;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidXimoBoss;
import com.xwm.magicmaid2.core.enums.EnumState;
import com.xwm.magicmaid2.core.init.EquipmentInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class EntityAISuppleEnergy extends EntityAIBase
{
    private World world;
    private EntityMagicMaidXimo taskOwner;
    private int tick;
    private int performTick;
    private Random random;

    private List<EntityLivingBase> targets = new ArrayList<>();
    private List<Boolean> gravity = new ArrayList<>();

    private int seeTime = 0;

    private int areaId;

    public EntityAISuppleEnergy(World world, EntityMagicMaidXimo taskOwner) {
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
                return taskOwner.isDesire();
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
        boolean flag = shouldExecute() || performTick > 0;

        return performTick <= 360 && flag;
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

        if (flag && Math.sqrt(d) < 15+this.taskOwner.getRank()*15)
        {
            seeTime++;
//            this.taskOwner.getNavigator().clearPath();
        }
        else {
            if (performTick == 0) { // 如果技能还没开始 那还可以移动，开始后不能终止
                this.taskOwner.getNavigator().tryMoveToEntityLiving(target, 1.0f);
                seeTime = 0;
            }
        }


        if (seeTime >= 20) {
            if (taskOwner.isSkillReady(EnumState.SKILL1)) {
                performTick++;
            }
            this.taskOwner.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);
        }

        if (performTick == 10) {
            taskOwner.setState(EnumState.toInt(EnumState.SKILL1));
        }

        if (performTick >= 20 && performTick < 60)
        {
            AxisAlignedBB bb = this.taskOwner.getEntityBoundingBox().grow(15+this.taskOwner.getRank()*15);
            if (performTick == 20) {
                if (this.taskOwner instanceof EntityMagicMaidXimoBoss) {
                    areaId = MagicRenderRegistry.allocateArea();
                    ((EntityMagicMaidXimoBoss) this.taskOwner).createWarningArea(areaId, bb);
                }
            }

            List<EntityLivingBase> mobs = world.getEntitiesWithinAABB(EntityLivingBase.class, bb);
            for (EntityLivingBase mob : mobs) {

                if (mob instanceof EntityPlayerMP) {
                    if (((EntityPlayerMP) mob).isCreative())
                        continue;
                }

                if (MagicEquipmentUtils.checkEnemy(this.taskOwner, mob))
                    playConnectParticle(EnumParticleTypes.DRAGON_BREATH, this.taskOwner, mob, performTick-20, 2, 40);
            }
        }

        if (performTick == 60)
        {
            if (this.taskOwner instanceof EntityMagicMaidXimoBoss)
                ((EntityMagicMaidXimoBoss) this.taskOwner).removeWarningArea(areaId);

        }

        if (performTick >= 60 && performTick <= 360)
        {
            AxisAlignedBB bb = this.taskOwner.getEntityBoundingBox().grow(15+this.taskOwner.getRank()*15);
            List<EntityLivingBase> mobs = world.getEntitiesWithinAABB(EntityLivingBase.class, bb);
            for (EntityLivingBase mob : mobs) {

                if (mob instanceof EntityPlayerMP) {
                    if (((EntityPlayerMP) mob).isCreative())
                        continue;
                }

                if (MagicEquipmentUtils.checkEnemy(this.taskOwner, mob)) {
                    for (int i = 0; i < 10; i++)
                        playConnectParticle(EnumParticleTypes.DRAGON_BREATH, this.taskOwner, mob, i, 2, 10);

                    if (performTick > 180 && performTick % 20 == 0) {
                        mob.attackEntityFrom(DamageSource.causeMobDamage(this.taskOwner).setMagicDamage(), this.taskOwner.getRank() * 5 + 5);
                        this.taskOwner.heal(this.taskOwner.getRank() * 5 + 5);
                    }
                    playCageParticle(EnumParticleTypes.DRAGON_BREATH, this.taskOwner, mob, performTick-60, 2, 10);
                    if (mob.posY < taskOwner.posY)
                        mob.motionY = 0.05;
                    else
                        mob.motionY = -0.05;

                    if (mob instanceof EntityPlayerMP)
                    {
                        ((EntityPlayerMP) mob).connection.sendPacket(new SPacketEntityVelocity(mob.getEntityId(), mob.motionX, mob.motionY, mob.motionZ));
                    }
//                    mob.setPosition(mob.posX,  mob.posY+(taskOwner.posY - mob.posY) / 120 * (performTick-60), mob.posZ);
                }
            }
        }

        if (performTick == 360) {
            this.taskOwner.setDesire(false);
            taskOwner.resetSkillCount(EnumState.SKILL1, 30 - taskOwner.getRank() * 15);
            taskOwner.setState(EnumState.toInt(EnumState.STANDARD));
        }
    }

    private void playConnectParticle(EnumParticleTypes type, EntityLivingBase a, EntityLivingBase b, int cnt, int num, double k)
    {
        AxisAlignedBB bb = b.getEntityBoundingBox();
        double d0 = (bb.minX+bb.maxX) / 2.0;
        double d1 = (bb.minY+bb.maxY) / 2.0;
        double d2 = (bb.minZ+bb.maxZ) / 2.0;

        AxisAlignedBB bb1 = a.getEntityBoundingBox();
        double f0 = (bb1.minX+bb1.maxX) / 2.0;
        double f1 = (bb1.minY+bb1.maxY) / 2.0;
        double f2 = (bb1.minZ+bb1.maxZ) / 2.0;

        for (int i = 0; i < num; i++)
        {
            SPacketParticle particlePacket = new SPacketParticle(f0+(d0-f0)/k * cnt, f1 + (d1-f1)/k * cnt, f2 + (d2-f2)/k * cnt, type);
            NetworkRegistry.TargetPoint target = new NetworkRegistry.TargetPoint(world.provider.getDimension(), f0+(d0-f0)/k * cnt, f1 + (d1-f1)/k * cnt, f2 + (d2-f2)/k * cnt, 20.0d);
            NetworkLoader.instance.sendToAllAround(particlePacket, target);
        }
    }

    private void playCageParticle(EnumParticleTypes type, EntityLivingBase a, EntityLivingBase b, int cnt, int num, double k)
    {
        double r = 1.0;
        double radiun = Math.toRadians(20 * cnt);
        AxisAlignedBB bb = b.getEntityBoundingBox();
        double d0 = (bb.minX+bb.maxX) / 2.0  + r*cos(radiun);
        double d1 = (bb.minY+bb.maxY) / 2.0;
        double d2 = (bb.minZ+bb.maxZ) / 2.0 + r*sin(radiun);


        for (int i = 0; i < num; i++)
        {
            SPacketParticle particlePacket = new SPacketParticle(d0, d1, d2, type);
            NetworkRegistry.TargetPoint target = new NetworkRegistry.TargetPoint(world.provider.getDimension(), d0, d1, d2, 20.0d);
            NetworkLoader.instance.sendToAllAround(particlePacket, target);
        }
    }
}
