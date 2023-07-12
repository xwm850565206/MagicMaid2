package com.xwm.magicmaid2.common.entity.maid;

import com.xwm.magicmaid.entity.ai.EntityAIMaidAttackMelee;
import com.xwm.magicmaid.entity.ai.EntityAINearestAttackableTargetAvoidOwner;
import com.xwm.magicmaid.enumstorage.EnumMode;
import com.xwm.magicmaid.network.NetworkLoader;
import com.xwm.magicmaid.network.particle.SPacketParticle;
import com.xwm.magicmaid.object.item.equipment.EquipmentAttribute;
import com.xwm.magicmaid.object.item.equipment.ItemEquipment;
import com.xwm.magicmaid.object.item.equipment.ItemWeapon;
import com.xwm.magicmaid.player.MagicCreatureAttributes;
import com.xwm.magicmaid.registry.MagicEquipmentRegistry;
import com.xwm.magicmaid2.common.entity.ai.EntityAIEvilBall;
import com.xwm.magicmaid2.common.entity.ai.EntityAIEvilExplosion;
import com.xwm.magicmaid2.core.enums.EnumState;
import com.xwm.magicmaid2.core.init.EquipmentInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import com.xwm.magicmaid2.core.init.LootTableInit;
import com.xwm.magicmaid2.core.init.PotionInit;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityMagicMaidCassiu extends EntityMaidBase implements IAnimatable
{
    private AnimationFactory factory = new AnimationFactory(this);

    public EntityMagicMaidCassiu(World worldIn) {
        super(worldIn);
        this.ignoreFrustumCheck = true;
    }

    public void initEntityAI(){
        super.initEntityAI();

        this.tasks.addTask(4, new EntityAIMaidAttackMelee(this, 1.25D, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTargetAvoidOwner(this, EntityLivingBase.class, true, new EnemySelect(this)));
        this.tasks.addTask(3, new EntityAIEvilExplosion(world, this));
        this.tasks.addTask(3, new EntityAIEvilBall(world, this));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.setMaxHealthbarnum(20);
        this.setHealthbarnum(20);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
        this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
//        this.getCapability()
//        this.getAttributeMap().getAttributeInstance(MagicCreatureAttributes.INJURY_REDUCTION).setBaseValue(0.5f);
//        this.getAttributeMap().getAttributeInstance(MagicCreatureAttributes.IGNORE_REDUCTION).setBaseValue(0.5f);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        if (EnumState.valueOf(this.getState()) == EnumState.SKILL1) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cassiu.skill1", false));
        }
        else if (EnumState.valueOf(this.getState()) == EnumState.SKILL2) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cassiu.skill2", false));
        }
        else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cassiu.walk", true));
        }
        else if (this.getAttackTarget() == null)
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.cassiu.idle", true));

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

        if (!world.isRemote) {
            if (MagicEquipmentRegistry.getAttribute(getArmorType()) == EquipmentInit.BANSHEE_VEIL) {
                if (this.ticksExisted % 100 == 0) {
                    this.setInvisible(true);
                    if (!world.isRemote) {
                        for (int i = 0; i < 4; i++) {
                            SPacketParticle packet = new SPacketParticle(posX + rand.nextFloat(), posY + height / 2 + rand.nextFloat(), posZ + rand.nextFloat(), EnumParticleTypes.DRAGON_BREATH);
                            NetworkLoader.instance.sendToDimension(packet, dimension);
                        }
                    }
                    this.heal(5);
//                    System.out.println("heal");
                } else if (this.ticksExisted % 50 == 0) {
                    this.setInvisible(false);
                    if (!world.isRemote) {
                        for (int i = 0; i < 4; i++) {
                            SPacketParticle packet = new SPacketParticle(posX + rand.nextFloat(), posY + height / 2 + rand.nextFloat(), posZ + rand.nextFloat(), EnumParticleTypes.DRAGON_BREATH);
                            NetworkLoader.instance.sendToDimension(packet, dimension);
                        }
                    }
                }

                if (EnumMode.valueOf(getMode()) == EnumMode.SERVE && hasOwner()) {
                    EntityLivingBase owner = world.getPlayerEntityByUUID(getOwnerID());
                    if (owner != null)
                        owner.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 100, 0));
                }
            }

            if (this.isPerformAttack())
                this.setInvisible(false);
        }
    }


    public boolean attackEntityFrom(DamageSource source, float amount) {
        // cancel fall damage
        if (source.equals(DamageSource.FALL) || source.isExplosion())
            return false;

        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void getEquipment(ItemEquipment equipment) {
        super.getEquipment(equipment);
        if (equipment != null) {
            EquipmentAttribute attribute = equipment.getEquipmentAttribute();
            if (attribute.getType() != EquipmentAttribute.EquipmentType.NONE) {
                if (attribute.getType() == EquipmentAttribute.EquipmentType.WEAPON) {
                    ;
                } else if (attribute == EquipmentInit.BANSHEE_VEIL) {
                    this.setMaxHealthbarnum(20);
                    if (this.isFirstGetArmor()) {
                        this.setHealthbarnum(80);
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
                if (attribute == MagicEquipmentRegistry.NONE) {
                    return;
                } else if (attribute.getType() == EquipmentAttribute.EquipmentType.WEAPON) {
                    return;
                } else if (attribute == EquipmentInit.BANSHEE_VEIL){
                    this.setMaxHealthbarnum(20);
                }
            }
        }
    }

    @Override
    public boolean canEquip(ItemEquipment itemEquipment) {
        return itemEquipment == ItemInit.DARK_CLAW || itemEquipment == ItemInit.DARK_SOUL || itemEquipment == ItemInit.BANSHEE_VEIL;

    }

    @Override
    protected ResourceLocation getLootTable()
    {
        if (getTrueHealth() > 0) return null;
        return LootTableInit.HOLY_FRUIT_CASSIU;
    }
}
