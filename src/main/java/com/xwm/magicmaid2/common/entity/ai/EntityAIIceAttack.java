package com.xwm.magicmaid2.common.entity.ai;

import com.xwm.magicmaid.enumstorage.EnumMode;
import com.xwm.magicmaid.manager.IProcessManagerImpl;
import com.xwm.magicmaid.manager.MagicEquipmentUtils;
import com.xwm.magicmaid.registry.MagicEquipmentRegistry;
import com.xwm.magicmaid.util.process.ProcessTask;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidNeva;
import com.xwm.magicmaid2.common.entity.throwable.EntityIce;
import com.xwm.magicmaid2.core.enums.EnumState;
import com.xwm.magicmaid2.core.init.EquipmentInit;
import com.xwm.magicmaid2.core.network.NetworkLoader;
import com.xwm.magicmaid2.core.network.packet.server.SPacketAddRenderTaskIceRegion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class EntityAIIceAttack extends EntityAIBase
{
    /** The entity the AI instance has been applied to */
    private final EntityMagicMaidNeva entityHost;
    private EntityLivingBase attackTarget;
    /**
     * A decrementing tick that spawns a ranged attack once this value reaches 0. It is then set back to the
     * maxRangedAttackTime.
     */
    private int rangedAttackTime;
    private final double entityMoveSpeed;
    private int seeTime;
    private final int attackIntervalMin;
    /** The maximum time the AI has to wait before peforming another ranged attack. */
    private final int maxRangedAttackTime;
    private final float attackRadius;
    private final float maxAttackDistance;

    private int performTime;

    public EntityAIIceAttack(EntityMagicMaidNeva attacker, double movespeed, int maxAttackTime, float maxAttackDistanceIn)
    {
        this(attacker, movespeed, maxAttackTime, maxAttackTime, maxAttackDistanceIn);
    }

    public EntityAIIceAttack(EntityMagicMaidNeva attacker, double movespeed, int p_i1650_4_, int maxAttackTime, float maxAttackDistanceIn)
    {
        this.rangedAttackTime = -1;
        this.entityHost = attacker;
        this.entityMoveSpeed = movespeed;
        this.attackIntervalMin = p_i1650_4_;
        this.maxRangedAttackTime = maxAttackTime;
        this.attackRadius = maxAttackDistanceIn;
        this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
        this.setMutexBits(3);

    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = this.entityHost.getAttackTarget();

        if (entitylivingbase == null)
        {
            return false;
        }
        else
        {
            if (!entityHost.inventory.func_70301_a(0).isEmpty() && MagicEquipmentRegistry.getAttribute(entityHost.getWeaponType()) == EquipmentInit.HEAVY_COLD){
                this.attackTarget = entitylivingbase;
                return true;
            }

            return false;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return this.shouldExecute() || !this.entityHost.getNavigator().noPath() || this.entityHost.getState() == EnumState.toInt(EnumState.SKILL2);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        this.attackTarget = null;
        this.seeTime = 0;
        this.rangedAttackTime = 0;
        this.entityHost.setState(EnumState.toInt(EnumState.STANDARD));
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        double d0 = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.getEntityBoundingBox().minY, this.attackTarget.posZ);
        boolean flag = this.entityHost.getEntitySenses().canSee(this.attackTarget);

        if (flag)
        {
            ++this.seeTime;
        }
        else
        {
            this.seeTime = 0;
        }

        if (d0 <= (double)this.maxAttackDistance && this.seeTime >= 20)
        {
            this.entityHost.getNavigator().clearPath();
        }
        else if (performTime <= 0 && this.entityHost.getDistanceSq(this.attackTarget) > 10)
        {
            this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
        }

        this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);

        if (performTime > 0)
        {
            if (entityHost.getState() == EnumState.toInt(EnumState.SKILL2))
            {
                performTime--;
                if (performTime == 0) {
                    SPacketAddRenderTaskIceRegion packet = new SPacketAddRenderTaskIceRegion(entityHost.getEntityId(),  200, (1+entityHost.getRank()) * 2, entityHost.posX, entityHost.posY, entityHost.posZ);
                    NetworkLoader.instance.sendToDimension(packet, entityHost.dimension);
                    ProcessTaskIceRegion task = new ProcessTaskIceRegion(1, this.entityHost, 100);
                    IProcessManagerImpl.getInstance().addTask(task);
                    this.entityHost.setCharge(0);
                    this.entityHost.setState(EnumState.toInt(EnumState.STANDARD));
                    this.entityHost.heal((1+entityHost.getRank()) * 10);
                }
            }
            else {
                if (performTime <= 10) {
                    EntityLivingBase target = entityHost.getAttackTarget();
                    for (int i = 0; i <= 3; i++) {
                        EntityIce ball = new EntityIce(entityHost.world, entityHost);
                        ball.setPosition(entityHost.posX + entityHost.width / 2.0f + entityHost.width * MathHelper.cos((float) (i * 2 * Math.PI / 3.0f + entityHost.getRNG().nextInt(120) * 2 * Math.PI / 120.0f)), entityHost.posY + entityHost.getEyeHeight(), entityHost.posZ + entityHost.width / 2.0f + entityHost.width * MathHelper.sin((float) (i * 2 * Math.PI / 3.0f + entityHost.getRNG().nextInt(120) * 2 * Math.PI / 120.0f)));
                        if (target == null) {
                            Vec3d vec3d = entityHost.getLookVec();
                            ball.shoot(vec3d.x, vec3d.y, vec3d.z, 0.5f, 0.1f);
                        } else {
                            Vec3d vec3d = new Vec3d(target.posX - entityHost.posX, target.posY + target.height / 2 - ball.posY, target.posZ - entityHost.posZ);
                            ball.shoot(vec3d.x, vec3d.y, vec3d.z, 0.5f, 0.1f);
                        }

                        if (!entityHost.world.isRemote)
                            entityHost.world.spawnEntity(ball);
                    }
                    entityHost.playSound(SoundEvents.ENTITY_SNOWMAN_SHOOT, 1.0F, 1.0F / (entityHost.getRNG().nextFloat() * 0.4F + 0.8F));
                }
                performTime--;
                if (performTime == 0)
                    this.entityHost.setState(EnumState.toInt(EnumState.STANDARD));
            }
        }
        else {
            if (this.rangedAttackTime > 0)
                this.rangedAttackTime--;

            if (this.rangedAttackTime == 0)
            {
                if (!flag)
                {
                    return;
                }

                if (entityHost.getCharge() == 6) {
                    this.entityHost.setState(EnumState.toInt(EnumState.SKILL2));
                    this.performTime = 24;
                }
                else {
                    entityHost.setCharge(Math.min(entityHost.getCharge()+1, 6));
                    this.performTime = 20;
                    this.entityHost.setState(EnumState.toInt(EnumState.SKILL1));
                }
                this.rangedAttackTime = this.attackIntervalMin;
            }


            if (this.rangedAttackTime < 0)
            {
                this.rangedAttackTime = this.attackIntervalMin;
            }
        }

    }

    public static class ProcessTaskIceRegion extends ProcessTask
    {
        private EntityMagicMaidNeva neva;
        private AxisAlignedBB center;
        public ProcessTaskIceRegion(int priority, EntityMagicMaidNeva taskOwner, int maxAge) {
            super(priority, taskOwner, maxAge);
            this.neva = taskOwner;
            this.center = taskOwner.getEntityBoundingBox();
        }

        @Override
        public void update() {
            float radius = (1+neva.getRank()) * 2 * 10 * (getAge() * 1.0f / getMaxAge());
            List<Entity> entities = taskOwner.getEntityWorld().getEntitiesWithinAABB(Entity.class, center.grow(radius));

            for (Entity entity : entities)
            {
                if (entity instanceof EntityIce && ((EntityIce) entity).getThrower() == taskOwner) // 不会定住自己的冰块
                    continue;
                entity.motionX = 0;
                entity.motionY = 0;
                entity.motionZ = 0;
                if (entity instanceof EntityLiving)
                {
                    if (MagicEquipmentUtils.checkEnemy(neva, (EntityLivingBase) entity))
                    {
                        entity.attackEntityFrom(DamageSource.causeMobDamage(neva).setDamageBypassesArmor().setMagicDamage(), (1+neva.getRank())*5);
                    }
                }
            }

            age++;
        }
    }
}