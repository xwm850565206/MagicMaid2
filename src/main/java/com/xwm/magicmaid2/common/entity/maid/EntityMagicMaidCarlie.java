package com.xwm.magicmaid2.common.entity.maid;

import com.xwm.magicmaid.entity.ai.EntityAIMaidAttackMelee;
import com.xwm.magicmaid.entity.ai.EntityAIMaidAttackRanged;
import com.xwm.magicmaid.enumstorage.EnumMode;
import com.xwm.magicmaid.manager.IMagicCreatureManagerImpl;
import com.xwm.magicmaid.manager.MagicCreatureUtils;
import com.xwm.magicmaid.manager.MagicEquipmentUtils;
import com.xwm.magicmaid.object.item.equipment.EquipmentAttribute;
import com.xwm.magicmaid.object.item.equipment.ItemEquipment;
import com.xwm.magicmaid.object.item.equipment.ItemWeapon;
import com.xwm.magicmaid.registry.MagicEquipmentRegistry;
import com.xwm.magicmaid2.common.entity.ai.EntityAIEvilExplosion;
import com.xwm.magicmaid2.common.entity.ai.EntityAIMaidAttackRangedAdjust;
import com.xwm.magicmaid2.common.entity.ai.EntityAIStarArrow;
import com.xwm.magicmaid2.core.enums.EnumState;
import com.xwm.magicmaid2.core.init.EquipmentInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import com.xwm.magicmaid2.core.init.LootTableInit;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;

public class EntityMagicMaidCarlie extends EntityMaidBase implements IAnimatable, IRangedAttackMob
{
    private AnimationFactory factory = new AnimationFactory(this);

    public EntityMagicMaidCarlie(World worldIn) {
        super(worldIn);
        this.ignoreFrustumCheck = true;
    }

    public void initEntityAI(){
        super.initEntityAI();

        this.tasks.addTask(3, new EntityAIStarArrow(world, this));
        this.tasks.addTask(3, new EntityAIMaidAttackRanged(this, 1.1D, 40, 20));

    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.setMaxHealthbarnum(40);
        this.setHealthbarnum(40);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(80.0D);
        this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        if (EnumState.valueOf(this.getState()) == EnumState.SKILL1 || EnumState.valueOf(this.getState()) == EnumState.SKILL2) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.carlie.skill", false));
        }
        else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.carlie.walk", true));
        }
        else
            return PlayState.STOP;

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
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (MagicEquipmentRegistry.getAttribute(this.getArmorType()) == EquipmentInit.STAR_BOW) // 有护甲，服侍时保护主人
        {
            List<EntityLiving> entityLivings = world.getEntitiesWithinAABB(EntityLiving.class, getEntityBoundingBox().grow(2 + getRank(), 2, 2 + getRank()));
            for (EntityLiving entityLiving : entityLivings)
            {
                if (entityLiving == this)
                    continue;

                if (entityLiving.getAttackTarget() != null && entityLiving.getAttackTarget() == this && MagicEquipmentUtils.checkEnemy(this, entityLiving))
                {
                    IMagicCreatureManagerImpl.getInstance().attackEntityFrom(entityLiving, DamageSource.causeMobDamage(this), getRank());
                }
            }

            if (EnumMode.valueOf(getMode()) == EnumMode.SERVE && hasOwner()) {
                EntityPlayer owner = world.getPlayerEntityByUUID(getOwnerID());
                if (owner != null) {
                    owner.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 100, 0));
                }
            }
        }
    }

    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
//        this,setState();
        EntityArrow entityarrow = this.getArrow(distanceFactor);
        double d0 = target.posX - this.posX;
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 3.0F) - entityarrow.posY;
        double d2 = target.posZ - this.posZ;
        double d3 = (double) MathHelper.sqrt(d0 * d0 + d2 * d2);
        entityarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, 0);
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(entityarrow);
    }

    public void setSwingingArms(boolean swingingArms)
    {
        if (swingingArms)
            this.setState(EnumState.toInt(EnumState.ATTACK));
        else
            this.setState(EnumState.toInt(EnumState.STANDARD));
    }

    protected EntityArrow getArrow(float p_190726_1_)
    {
        EntityTippedArrow entitytippedarrow = new EntityTippedArrow(this.world, this);
        entitytippedarrow.setEnchantmentEffectsFromEntity(this, p_190726_1_);
        return entitytippedarrow;
    }

    @Override
    public void getEquipment(ItemEquipment equipment) {
        super.getEquipment(equipment);
        if (equipment != null) {
            EquipmentAttribute attribute = equipment.getEquipmentAttribute();
            if (attribute.getType() != EquipmentAttribute.EquipmentType.NONE) {
                if (attribute.getType() == EquipmentAttribute.EquipmentType.WEAPON) {
                    ;
                } else if (attribute == EquipmentInit.STAR_BOW) {
                    this.setMaxHealthbarnum(200);
                    if (this.isFirstGetArmor()) {
                        this.setHealthbarnum(200);
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
                } else if (attribute == EquipmentInit.STAR_BOW){
                    this.setArmorType(MagicEquipmentRegistry.NONE.getName());
                    this.setMaxHealthbarnum(40);
                }
            }
        }
    }

    @Override
    public boolean canEquip(ItemEquipment itemEquipment) {
        return itemEquipment == ItemInit.STAR_ARROW || itemEquipment == ItemInit.HEAVEN_ARROW || itemEquipment == ItemInit.STAR_BOW;

    }

    @Override
    protected ResourceLocation getLootTable()
    {
        if (getTrueHealth() > 0) return null;
        return LootTableInit.HOLY_FRUIT_CARLIE;
    }
}
