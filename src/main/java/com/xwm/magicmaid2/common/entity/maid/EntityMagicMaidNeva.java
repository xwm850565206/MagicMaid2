package com.xwm.magicmaid2.common.entity.maid;

import com.xwm.magicmaid.entity.ai.EntityAIMaidAttackRanged;
import com.xwm.magicmaid.enumstorage.EnumMode;
import com.xwm.magicmaid.object.item.equipment.EquipmentAttribute;
import com.xwm.magicmaid.object.item.equipment.ItemEquipment;
import com.xwm.magicmaid.registry.MagicEquipmentRegistry;
import com.xwm.magicmaid2.common.entity.ai.EntityAICrazyAttack;
import com.xwm.magicmaid2.common.entity.ai.EntityAIIceAttack;
import com.xwm.magicmaid2.common.entity.ai.EntityAISoulAttack;
import com.xwm.magicmaid2.common.entity.mob.EntityEvilSkeleton;
import com.xwm.magicmaid2.core.enums.EnumState;
import com.xwm.magicmaid2.core.init.EquipmentInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import com.xwm.magicmaid2.core.init.LootTableInit;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.MobEffects;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import scala.Int;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;

public class EntityMagicMaidNeva extends EntityMaidBase implements IAnimatable, IRangedAttackMob
{
    private AnimationFactory factory = new AnimationFactory(this);
    private static final DataParameter<Integer> CHARGE = EntityDataManager.<Integer>createKey(EntityMagicMaidNeva.class, DataSerializers.VARINT);


    public EntityMagicMaidNeva(World worldIn)
    {
        super(worldIn);
        this.ignoreFrustumCheck = true;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(CHARGE, 0);
    }

    public void initEntityAI(){
        super.initEntityAI();
        this.tasks.addTask(3, new EntityAIIceAttack(this, 1, 80, 5));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
        this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (EnumState.valueOf(this.getState()) == EnumState.SKILL1) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.neva.attack", false));
        }
        else if (EnumState.valueOf(this.getState()) == EnumState.SKILL2) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.neva.ice_explode", false));
        }
        else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.neva.walk", true));
        }
        else
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.neva.idle", true));

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
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (this.getOwnerID() != null && EnumMode.valueOf(this.getMode()) == EnumMode.SERVE)
        {
            AxisAlignedBB bb = this.getEntityBoundingBox().grow(4+4*this.getRank());
            List<EntityLiving> mobs = this.world.getEntitiesWithinAABB(EntityLiving.class, bb);
            for (EntityLiving mob : mobs)
            {
                if (mob.getAttackTarget() != null && (mob.getAttackTarget().getUniqueID().equals(this.getOwnerID()) ||
                        mob.getAttackTarget().getUniqueID().equals(this.getUniqueID()))) // 要主人或者自己都会被施加buff
                {
                    mob.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, getRank()+2));
                    mob.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, getRank()+2));
                }
            }
        }
    }

    @Override
    public void getEquipment(ItemEquipment equipment) {
        super.getEquipment(equipment);
        if (equipment != null) {
            EquipmentAttribute attribute = equipment.getEquipmentAttribute();
            if (attribute.getType() != EquipmentAttribute.EquipmentType.NONE) {
                if (attribute.getType() == EquipmentAttribute.EquipmentType.WEAPON) {
                    ;
                } else if (attribute == EquipmentInit.NEVA_CORE) {
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
                } else if (attribute == EquipmentInit.NEVA_CORE){
                    this.setArmorType(MagicEquipmentRegistry.NONE.getName());
                    this.setMaxHealthbarnum(10);
                }
            }
        }
    }

    @Override
    public boolean canEquip(ItemEquipment itemEquipment) {
        return itemEquipment == ItemInit.HEAVY_COLD || itemEquipment == ItemInit.NEVA_CORE;
    }

    public void setCharge(int charge)
    {
        this.getDataManager().set(CHARGE, charge);
    }

    public int getCharge()
    {
        return this.getDataManager().get(CHARGE);
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        if (getTrueHealth() > 0) return null;
        return LootTableInit.HOLY_FRUIT_NEVA;
    }

}
