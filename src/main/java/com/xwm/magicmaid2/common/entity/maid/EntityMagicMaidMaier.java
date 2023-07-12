package com.xwm.magicmaid2.common.entity.maid;

import com.xwm.magicmaid.manager.IProcessManagerImpl;
import com.xwm.magicmaid.manager.ISkillManagerImpl;
import com.xwm.magicmaid.object.item.ItemSkillBook;
import com.xwm.magicmaid.object.item.equipment.ItemEquipment;
import com.xwm.magicmaid.player.capability.CapabilityLoader;
import com.xwm.magicmaid.player.capability.ISkillCapability;
import com.xwm.magicmaid.player.skill.IPerformSkill;
import com.xwm.magicmaid.player.skill.ISkill;
import com.xwm.magicmaid.registry.MagicEquipmentRegistry;
import com.xwm.magicmaid.registry.MagicSkillRegistry;
import com.xwm.magicmaid.util.process.ProcessTask;
import com.xwm.magicmaid2.core.init.LootTableInit;
import com.xwm.magicmaid2.core.registry.SkillRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityMagicMaidMaier extends EntityMaidBase implements IAnimatable, IRangedAttackMob
{
    private AnimationFactory factory = new AnimationFactory(this);

    public EntityMagicMaidMaier(World worldIn)
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
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
        this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
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
        return LootTableInit.HOLY_FRUIT_AILI;
    }

    @Override
    public boolean canEquip(ItemEquipment itemEquipment) {
        return false;
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (player.getEntityWorld().isRemote)
            return true;
        else {
            if (hand == EnumHand.MAIN_HAND) {
                TalkTask talkTask = new TalkTask(1, player, 310);
                IProcessManagerImpl.getInstance().addTask(talkTask);
            }
            return true;
        }
    }

    private static class TalkTask extends ProcessTask
    {

        public TalkTask(int priority, Entity taskOwner, int maxAge) {
            super(priority, taskOwner, maxAge);
        }

        @Override
        public void update() {
            if (!this.taskOwner.getEntityWorld().isRemote) {
                if (this.age == 0) {
                    this.taskOwner.sendMessage(new TextComponentString("[神秘人]: 你看着不像教堂的人，是从外面来的吗？前面就是教堂的禁地了，禁地里刚刚出现了及其强大的能量碰撞，造成了一个极其诡异的能量场，不能再靠近了。"));
                }

                if (this.age == 100) {
                    this.taskOwner.sendMessage(new TextComponentString("[神秘人]: 我叫梅尔，禁地里发生的事情牵扯到世界的存亡，我不能和你明说，但是如果你是好人的话，我想请你帮个忙。"));
                }

                if (this.age == 200) {
                    this.taskOwner.sendMessage(new TextComponentString("[梅尔]: 我需要在这里控制能量场不继续扩大，但是想要解决问题我需要进入能量场中。能量场由一股纯粹的恶组成，想要撕开裂缝需要几件物品，[烛台]，[圣十字]，[独角仙]，[傲慢]，这些物品教堂里没有，得麻烦你到外面收集了。"));
                }

                if (this.age == 300) {
                    this.taskOwner.sendMessage(new TextComponentString("[梅尔]: 外面已经完全被污染，你需要十分小心，我传授你两个技能，它们会帮助你快速来回。"));
                    this.taskOwner.sendMessage(new TextComponentString(TextFormatting.YELLOW + "获得技能[荒芜大陆传送]和[迷雾森林传送]"));

                    ISkillCapability skillCapability = (ISkillCapability) taskOwner.getCapability(CapabilityLoader.SKILL_CAPABILITY, null);

                    if (skillCapability != null) {
                        ISkill skill = MagicSkillRegistry.getSkill(SkillRegistry.PERFORM_TO_NORMAL_LAND.getName());
                        skillCapability.setPerformSkill(skill.getName(), (IPerformSkill) skill);
                        ISkill skill1 = MagicSkillRegistry.getSkill(SkillRegistry.PERFORM_TO_RUIN_FOREST.getName());
                        skillCapability.setPerformSkill(skill1.getName(), (IPerformSkill) skill1);
                        ISkillManagerImpl.getInstance().updateToClient((EntityPlayer) taskOwner);
                    }
                }
            }

            age++;
        }
    }
}
