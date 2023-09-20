package com.xwm.magicmaid2.common.entity.mob;

import com.xwm.magicmaid2.core.network.NetworkLoader;
import com.xwm.magicmaid2.core.network.packet.server.SPacketParticle6;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;

public class EntityEvilSkeleton extends EntityMob implements IAnimatable
{
    private static final DataParameter<Boolean> ARMS_RAISED = EntityDataManager.<Boolean>createKey(EntityEvilSkeleton.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ATTACK_TYPE = EntityDataManager.<Boolean>createKey(EntityEvilSkeleton.class, DataSerializers.BOOLEAN);


    private AnimationFactory factory = new AnimationFactory(this);

    public EntityEvilSkeleton(World worldIn) {
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
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300);
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

        if (isArmsRaised()) {
            if (getAttackType())
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.evil_skeleton.attack_2", false));
            else
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.evil_skeleton.attack", false));

        }
        else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.evil_skeleton.walk", true));
        }
        else {
            return PlayState.STOP;
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
        public EntityAIEvilSkeletonAttack(EntityEvilSkeleton creature, double speedIn, boolean useLongMemory) {
            super(creature, speedIn, useLongMemory);
        }

        @Override
        public void startExecuting() {
            super.startExecuting();
        }

        @Override
        protected void checkAndPerformAttack(EntityLivingBase entityLivingBase, double distance) {
            double d0 = this.getAttackReachSqr(entityLivingBase);

            if (swingArmTick != -1) {
                swingArmTick--;
                this.attacker.getNavigator().setPath(null, 0);
            }

            if (getAttackType()) {
                if (distance <= d0) {
                    if (swingArmTick == -1) {
                        ((EntityEvilSkeleton) this.attacker).setArmsRaised(true);
                        swingArmTick = 9;
                    }
                }

                if (swingArmTick == 7 || swingArmTick == 2) {
                    AxisAlignedBB attackBB = this.attacker.getEntityBoundingBox().offset(this.attacker.getLookVec().scale(2));
                    List<EntityLivingBase> entityLivingBases = world.getEntitiesWithinAABB(EntityLivingBase.class, attackBB.grow(3));

                    for (EntityLivingBase entityLivingBase1 : entityLivingBases) {
                        if (entityLivingBase1 != this.attacker)
                            this.attacker.attackEntityAsMob(entityLivingBase);
                    }
                }

                if (swingArmTick <= 0) {
                    ((EntityEvilSkeleton) this.attacker).setArmsRaised(false);
                    ((EntityEvilSkeleton) this.attacker).setAttackType(false);
                }
            } else {
                if (distance <= d0) {
                    if (swingArmTick == -1) {
                        ((EntityEvilSkeleton) this.attacker).setArmsRaised(true);
                        swingArmTick = 12;
                    }
                }


                if (swingArmTick == 4) {
                    AxisAlignedBB attackBB = this.attacker.getEntityBoundingBox().offset(this.attacker.getLookVec().scale(2));

                    for (int i = 0; i < 10; i++) {
                        SPacketParticle6 packet = new SPacketParticle6((attackBB.minX + attackBB.maxX) / 2.0, attackBB.minY, (attackBB.minZ + attackBB.maxZ) / 2.0, rand.nextFloat() - 0.5, rand.nextFloat(), rand.nextFloat() - 0.5, EnumParticleTypes.DRAGON_BREATH);
                        NetworkLoader.instance.sendToDimension(packet, this.attacker.dimension);
                    }
                }

                if (swingArmTick == 2) {
                    AxisAlignedBB attackBB = this.attacker.getEntityBoundingBox().offset(this.attacker.getLookVec().scale(2));
                    List<EntityLivingBase> entityLivingBases = world.getEntitiesWithinAABB(EntityLivingBase.class, attackBB.grow(3));

                    for (EntityLivingBase entityLivingBase1 : entityLivingBases) {
                        if (entityLivingBase1 != this.attacker)
                            this.attacker.attackEntityAsMob(entityLivingBase);
                    }
                }

                if (swingArmTick <= 0) {
                    ((EntityEvilSkeleton) this.attacker).setArmsRaised(false);
                    ((EntityEvilSkeleton) this.attacker).setAttackType(true);
                }
            }
        }
    }

}
