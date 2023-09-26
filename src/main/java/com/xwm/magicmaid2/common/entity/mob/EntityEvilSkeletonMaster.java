package com.xwm.magicmaid2.common.entity.mob;

import com.xwm.magicmaid2.common.entity.throwable.EntityEvilSwordPower;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.List;

public class EntityEvilSkeletonMaster extends EntityMob implements IAnimatable
{
    private static final DataParameter<Boolean> ARMS_RAISED = EntityDataManager.<Boolean>createKey(EntityEvilSkeletonMaster.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ATTACK_TYPE = EntityDataManager.<Boolean>createKey(EntityEvilSkeletonMaster.class, DataSerializers.BOOLEAN);


    private AnimationFactory factory = new AnimationFactory(this);

    public EntityEvilSkeletonMaster(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void initEntityAI()
    {
        super.initEntityAI();
        this.tasks.addTask(2, new EntityAIEvilSkeletonAttack(this, 1.0D, false));
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityPigZombie.class}));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
    }

    protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(ARMS_RAISED, Boolean.FALSE);
        this.getDataManager().register(ATTACK_TYPE, Boolean.FALSE);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(15.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(3.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100);
    }

    public void setArmsRaised(boolean armsRaised)
    {
        this.getDataManager().set(ARMS_RAISED, armsRaised);
    }

    public boolean isArmsRaised()
    {
        return this.getDataManager().get(ARMS_RAISED);
    }

    public void setAttackType(boolean attackType)
    {
        this.getDataManager().set(ATTACK_TYPE, attackType);
    }

    public boolean getAttackType()
    {
        return this.getDataManager().get(ATTACK_TYPE);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
//
        if (isArmsRaised()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.evil_skeleton_master.attack", false));
        }
        else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.evil_skeleton_master.walk", true));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.evil_skeleton_master.idle", true));

        }

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }


    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public class EntityAIEvilSkeletonAttack extends EntityAIAttackMelee
    {
        private int swingArmTick = -1;
        public EntityAIEvilSkeletonAttack(EntityEvilSkeletonMaster creature, double speedIn, boolean useLongMemory) {
            super(creature, speedIn, useLongMemory);
        }

        @Override
        public void startExecuting() {
            super.startExecuting();
        }

        @Override
        public void updateTask() {
            EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
            if (entitylivingbase != null && entitylivingbase.isEntityAlive())
            {
                super.updateTask();
            }
            else {
                this.checkAndPerformAttack(entitylivingbase, 0);
            }
        }

        @Override
        public boolean shouldContinueExecuting() {
            boolean flag =  super.shouldContinueExecuting();
            return flag || swingArmTick != -1;
        }

        @Override
        protected void checkAndPerformAttack(@Nullable EntityLivingBase entityLivingBase, double distance) {
            double d0 = 0;
            if (entityLivingBase != null) {
                d0 = this.getAttackReachSqr(entityLivingBase);
                this.attacker.getLookHelper().setLookPositionWithEntity(entityLivingBase, 30, 90);
            }

            if (swingArmTick != -1) {
                swingArmTick--;
            }

            if (swingArmTick != -1 || distance <= d0) {
                this.attacker.getNavigator().setPath(null, 0);
            }


            if (distance <= d0 && swingArmTick == -1 && entityLivingBase != null && entityLivingBase.isEntityAlive())
            {
                ((EntityEvilSkeletonMaster) this.attacker).setArmsRaised(true);
                swingArmTick = 30;
            }


            if (swingArmTick == 5) {

                for (int i = -1; i <= 1; i++)
                {
                    EntityEvilSwordPower ball = new EntityEvilSwordPower(world, this.attacker);
                    ball.setPosition(attacker.posX, attacker.posY + attacker.getEyeHeight(), attacker.posZ);
                    ball.setStrength(2);
                    if (entityLivingBase == null)
                    {
                        Vec3d vec3d = attacker.getLookVec().rotateYaw(0.3f*i);
                        ball.shoot(vec3d.x, vec3d.y, vec3d.z, 1.0f, 0f);
                    }
                    else {
                        Vec3d vec3d = new Vec3d(entityLivingBase.posX - attacker.posX, entityLivingBase.posY + entityLivingBase.height / 2 - ball.posY, entityLivingBase.posZ - attacker.posZ);
                        vec3d = vec3d.rotateYaw(0.3f*i);
                        ball.shoot(vec3d.x, vec3d.y, vec3d.z, 1.0f, 0f);
                    }

                    world.spawnEntity(ball);
                }
                this.attacker.playSound(SoundEvents.ENTITY_SNOWMAN_SHOOT, 1.0F, 1.0F / (attacker.getRNG().nextFloat() * 0.4F + 0.8F));

            }


            if (swingArmTick == 0) {
                ((EntityEvilSkeletonMaster) this.attacker).setArmsRaised(false);
                ((EntityEvilSkeletonMaster) this.attacker).setAttackType(!((EntityEvilSkeletonMaster) this.attacker).getAttackType());

            }
        }

        @Override
        protected double getAttackReachSqr(EntityLivingBase attackTarget) {
            return 10;
        }

        @Override
        public void resetTask() {
            super.resetTask();
            ((EntityEvilSkeletonMaster) this.attacker).setArmsRaised(false);
            ((EntityEvilSkeletonMaster) this.attacker).setAttackType(!((EntityEvilSkeletonMaster) this.attacker).getAttackType());
        }
    }



}
