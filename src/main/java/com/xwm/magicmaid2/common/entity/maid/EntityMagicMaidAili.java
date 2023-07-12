package com.xwm.magicmaid2.common.entity.maid;

import com.xwm.magicmaid.entity.ai.EntityAIMaidAttackRanged;
import com.xwm.magicmaid.entity.mob.weapon.EntityMaidWeapon;
import com.xwm.magicmaid.enumstorage.EnumMode;
import com.xwm.magicmaid.object.item.equipment.*;
import com.xwm.magicmaid.player.MagicCreatureAttributes;
import com.xwm.magicmaid.registry.MagicEquipmentRegistry;
import com.xwm.magicmaid.util.handlers.LootTableHandler;
import com.xwm.magicmaid2.common.entity.ai.EntityAICrazyAttack;
import com.xwm.magicmaid2.common.entity.ai.EntityAISoulAttack;
import com.xwm.magicmaid2.common.item.equipment.ItemUnrealBall;
import com.xwm.magicmaid2.core.enums.EnumState;
import com.xwm.magicmaid2.core.init.EquipmentInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import com.xwm.magicmaid2.core.init.LootTableInit;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

public class EntityMagicMaidAili extends EntityMaidBase implements IAnimatable, IRangedAttackMob
{
    private AnimationFactory factory = new AnimationFactory(this);

    public EntityMagicMaidAili(World worldIn)
    {
        super(worldIn);
        this.ignoreFrustumCheck = true;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    public void initEntityAI(){
        super.initEntityAI();

        this.tasks.addTask(2, new EntityAIMaidAttackRanged(this, 1.1D, 40, 10));
        this.targetTasks.addTask(3, new EntityAISoulAttack(world, this));
        this.targetTasks.addTask(3, new EntityAICrazyAttack(world, this));
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
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.aili.skill", false));
        }
        else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.aili.walk", true));
        }
        else if (this.getAttackTarget() == null)
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.aili.idle", true));

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
        if (MagicEquipmentRegistry.getAttribute(this.getArmorType()) == EquipmentInit.FOX_ARMOR) // 有护甲，服侍时保护主人
        {
            List<EntityLiving> entityLivings = world.getEntitiesWithinAABB(EntityLiving.class, getEntityBoundingBox().grow(4 + 2 * getRank(), 2, 4 + 2 * getRank()));
            for (EntityLiving entityLiving : entityLivings)
            {
                if (entityLiving == this)
                    continue;

                if (entityLiving.getAttackTarget() != null && (entityLiving.getAttackTarget() == this || (hasOwner() && EnumMode.valueOf(this.getMode()) == EnumMode.SERVE && entityLiving.getAttackTarget().getUniqueID().equals(this.getOwnerID()))))
                {
                    EntityLiving otherTarget = entityLivings.get(rand.nextInt(entityLivings.size()));
                    if (!(otherTarget == this || (hasOwner() && otherTarget.getUniqueID().equals(this.getOwnerID()))))
                    {
                        entityLiving.setAttackTarget(otherTarget);
                        if (world instanceof WorldServer) {
                            ((WorldServer)world).spawnParticle(EnumParticleTypes.HEART, entityLiving.posX, entityLiving.posY + entityLiving.height, entityLiving.posZ, 4, 0, 0, 0, 1f);
                        }
                    }

                }
            }
        }
    }

    @Override
    public void getEquipment(ItemEquipment equipment) {
        super.getEquipment(equipment);
        if (equipment != null) {
            EquipmentAttribute attribute = equipment.getEquipmentAttribute();
            if (attribute.getType() == EquipmentAttribute.EquipmentType.WEAPON) {
                ;
            }
            else if (attribute == EquipmentInit.FOX_ARMOR) {
                this.setMaxHealthbarnum(40);
                if (this.isFirstGetArmor()) {
                    this.setHealthbarnum(40);
                    this.setFirstGetArmor(false);
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
                }
                else if (attribute == EquipmentInit.FOX_ARMOR) {
                    this.setArmorType(MagicEquipmentRegistry.NONE.getName());
                    this.setMaxHealthbarnum(10);
                }
            }
        }
    }

    @Override
    public boolean canEquip(ItemEquipment itemEquipment) {
        return itemEquipment == ItemInit.UNREAL_RING || itemEquipment == ItemInit.UNREAL_BALL || itemEquipment == ItemInit.FOX_ARMOR;
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        if (getTrueHealth() > 0) return null;
        return LootTableInit.HOLY_FRUIT_AILI;
    }

}
