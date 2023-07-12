package com.xwm.magicmaid2.common.entity.maid;

import com.xwm.magicmaid.entity.ai.EntityAIMaidAttackRanged;
import com.xwm.magicmaid.entity.ai.EntityAIMaidFollow;
import com.xwm.magicmaid.entity.ai.EntityAINearestAttackableTargetAvoidOwner;
import com.xwm.magicmaid.entity.mob.basic.interfaces.IEntityTameableCreature;
import com.xwm.magicmaid.enumstorage.EnumMode;
import com.xwm.magicmaid.manager.MagicEquipmentUtils;
import com.xwm.magicmaid.object.item.equipment.EquipmentAttribute;
import com.xwm.magicmaid.object.item.equipment.ItemEquipment;
import com.xwm.magicmaid.registry.MagicEquipmentRegistry;
import com.xwm.magicmaid2.common.entity.ai.EntityAICreateDrivedRegion;
import com.xwm.magicmaid2.common.entity.ai.EntityAIMaidFlyFollow;
import com.xwm.magicmaid2.common.entity.ai.EntityAISuppleEnergy;
import com.xwm.magicmaid2.core.enums.EnumState;
import com.xwm.magicmaid2.core.init.EquipmentInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import com.xwm.magicmaid2.core.init.LootTableInit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class EntityMagicMaidXimo extends EntityMaidBase implements IAnimatable, IRangedAttackMob, EntityFlying
{
    private static final DataParameter<Boolean> DESIRE = EntityDataManager.<Boolean>createKey(EntityMagicMaidXimo.class, DataSerializers.BOOLEAN);

    private AnimationFactory factory = new AnimationFactory(this);

    public EntityMagicMaidXimo(World worldIn)
    {
        super(worldIn);
        this.ignoreFrustumCheck = true;
        this.setNoGravity(true);
        this.moveHelper = new EntityFlyHelper(this);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(DESIRE, Boolean.TRUE);
    }

    public void initEntityAI(){
        super.initEntityAI();
//        this.tasks.addTask(5, new AIRandomFly(this));
        for (Iterator<EntityAITasks.EntityAITaskEntry> it = this.tasks.taskEntries.iterator(); it.hasNext(); ) { // 步行跟随任务删除
            EntityAITasks.EntityAITaskEntry entry = it.next();
            if (entry.action instanceof EntityAIMaidFollow) {
                it.remove();
                break;
            }
        }
        this.tasks.addTask(1, new EntityAIMaidFlyFollow(this, 3f, 10, 5));
        this.tasks.addTask(3, new EntityAISuppleEnergy(world,this));
        this.tasks.addTask(3, new EntityAICreateDrivedRegion(world,this));
        this.tasks.addTask(4, new EntityAIMaidAttackRanged(this, 1.5D, 40, 20));

//        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityMob.class, 4.0F, 1.2D, 1.4D));
//        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
//        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTargetAvoidOwner(this, EntityLivingBase.class, true, new EnemySelect(this)));


    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(3.0);
//        this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.FLYING_SPEED)
        this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
        this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (this.getMode() == EnumMode.toInt(EnumMode.SITTING) && this.hasOwner())
        {
            EntityPlayer player = this.world.getPlayerEntityByUUID(this.getOwnerID());
            if (player != null && player.getFoodStats().needFood())
            {
                player.getFoodStats().addExhaustion(4); // 服侍状态恢复主人饱食度
            }
        }
    }

    @Override
    protected PathNavigate createNavigator(World worldIn)
    {
        PathNavigateFlying pathnavigateflying = new PathNavigateFlying(this, worldIn);
        pathnavigateflying.setCanOpenDoors(false);
        pathnavigateflying.setCanFloat(true);
        pathnavigateflying.setCanEnterDoors(true);
        return pathnavigateflying;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source instanceof EntityDamageSourceIndirect) {
            for (int i = 0; i < 8; ++i) {
                if (this.teleportAvoid(posX, posY, posZ)) {
                    return true;
                }
            }
            return false;
        }
        return super.attackEntityFrom(source, amount);
    }

    protected boolean teleportAvoid(double t0, double t1, double t2)
    {
        double d0 = t0 + (this.rand.nextDouble() - 0.5D) * 8.0D;
        double d1 = t1 + (double)(this.rand.nextInt(4) - 2);
        double d2 = t2 + (this.rand.nextDouble() - 0.5D) * 8.0D;
        return this.attemptTeleport(d0, d1, d2);
    }

    protected  <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (EnumState.valueOf(this.getState()) == EnumState.SKILL1) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ximo.skill1", true));
        }
        else if (EnumState.valueOf(this.getState()) == EnumState.SKILL2) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ximo.skill2", false));
        }
        else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ximo.walk", true));
        }
        else if (this.getAttackTarget() == null)
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ximo.idle", true));

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        target.attackEntityFrom(DamageSource.causeMobDamage(this), this.getAttackDamage(MagicEquipmentRegistry.NONE));
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {

    }

    @Override
    protected ResourceLocation getLootTable()
    {
        if (getTrueHealth() > 0) return null;
        return LootTableInit.HOLY_FRUIT_XIMO;
    }

    @Override
    public void getEquipment(ItemEquipment equipment) {
        super.getEquipment(equipment);
        if (equipment != null) {
            EquipmentAttribute attribute = equipment.getEquipmentAttribute();
            if (attribute.getType() != EquipmentAttribute.EquipmentType.NONE) {
                if (attribute.getType() == EquipmentAttribute.EquipmentType.WEAPON) {
                    ;
                } else if (attribute == EquipmentInit.XIMO_CORE) {
                    this.setMaxHealthbarnum(40);
                    if (this.isFirstGetArmor()) {
                        this.setHealthbarnum(40);
                        this.setFirstGetArmor(false);
                    }
                }
            }
        }
    }

    @Override
    public void loseEquipment(ItemEquipment equipment) {
        super.loseEquipment(equipment);
        if (equipment != null) {
            if (!this.world.isRemote) {
                EquipmentAttribute attribute = equipment.getEquipmentAttribute();
                if (attribute == MagicEquipmentRegistry.NONE)
                    return;
                else if (attribute.getType() == EquipmentAttribute.EquipmentType.WEAPON) {
                    ;
                } else if (attribute == EquipmentInit.XIMO_CORE){
                    this.setArmorType(MagicEquipmentRegistry.NONE.getName());
                    this.setMaxHealthbarnum(10);
                }
            }
        }
    }

    @Override
    public boolean canEquip(ItemEquipment itemEquipment) {
        return itemEquipment == ItemInit.EAGER || itemEquipment == ItemInit.XIMO_CORE;

    }


    public boolean isDesire() {
        return this.getDataManager().get(DESIRE);
    }

    public void setDesire(boolean isDesire) {
        this.getDataManager().set(DESIRE, isDesire);
    }

    public void fall(float distance, float damageMultiplier)
    {
    }

    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
    {
    }

    public void travel(float strafe, float vertical, float forward)
    {
//        super.travel(strafe, vertical, forward);
        if (this.isInWater())
        {
            this.moveRelative(strafe, vertical, forward, 0.02F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.800000011920929D;
            this.motionY *= 0.800000011920929D;
            this.motionZ *= 0.800000011920929D;
        }
        else if (this.isInLava())
        {
            this.moveRelative(strafe, vertical, forward, 0.02F);
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5D;
            this.motionY *= 0.5D;
            this.motionZ *= 0.5D;
        }
        else
        {
            float f = 0.91F;

            if (this.onGround)
            {
                BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
                IBlockState underState = this.world.getBlockState(underPos);
                f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.91F;
            }

            float f1 = 0.16277136F / (f * f * f);
            this.moveRelative(strafe, vertical, forward, this.onGround ? 0.1F * f1 : 0.02F);
            f = 0.91F;

            if (this.onGround)
            {
                BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
                IBlockState underState = this.world.getBlockState(underPos);
                f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.91F;
            }

            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)f;
            this.motionY *= (double)f;
            this.motionZ *= (double)f;
        }

        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d1 = this.posX - this.prevPosX;
        double d0 = this.posZ - this.prevPosZ;
        float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;

        if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }

        this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }

    /**
     * Returns true if this entity should move as if it were on a ladder (either because it's actually on a ladder, or
     * for AI reasons)
     */
    public boolean isOnLadder()
    {
        return false;
    }

    static class AIRandomFly extends EntityAIBase
     {
        private final EntityMagicMaidXimo parentEntity;

        public AIRandomFly(EntityMagicMaidXimo ximo)
        {
            this.parentEntity = ximo;
            this.setMutexBits(1);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();

            if (!entitymovehelper.isUpdating())
            {
                return true;
            }
            else
            {
                double d0 = entitymovehelper.getX() - this.parentEntity.posX;
                double d1 = entitymovehelper.getY() - this.parentEntity.posY;
                double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting()
        {
            return false;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            Random random = this.parentEntity.getRNG();
            double d0 = this.parentEntity.posX + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = this.parentEntity.posY + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d2 = this.parentEntity.posZ + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);

            if (d1 > parentEntity.getEntityWorld().getSeaLevel() + 30)
                d1 = this.parentEntity.posY + (double) ((random.nextFloat() * 0.5F - 1.0F) * 4.0F);

            this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
        }
    }

}
