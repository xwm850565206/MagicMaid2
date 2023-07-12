package com.xwm.magicmaid2.common.item.equipment;

import com.xwm.magicmaid.manager.IMagicCreatureManagerImpl;
import com.xwm.magicmaid.manager.IProcessManagerImpl;
import com.xwm.magicmaid.manager.MagicEquipmentUtils;
import com.xwm.magicmaid.util.process.ProcessTask;
import com.xwm.magicmaid2.core.init.PotionInit;
import com.xwm.magicmaid2.core.network.NetworkLoader;
import com.xwm.magicmaid2.core.network.packet.server.SPacketAddRenderTaskBeam;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemDarkClaw extends ItemAnimatableWeapon
{
    public ItemDarkClaw(String name) {
        super(name);
    }

    protected  <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event)
    {
        //Not setting an animation here as that's handled in onItemRightClick
//        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.draw_claw.idle", true));
        return PlayState.CONTINUE;

    }


    @Override
    public boolean isChargeable() {
        return true;
    }

    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {
        this.onUsing(stack, player, count);
        super.onUsingTick(stack, player, count);
    }


    @Override
    public void onUsing(ItemStack stack, EntityLivingBase player, int count) {
//        super.onUsing(stack, player, count);

        if (count > 1) {
            AnimationController controller = GeckoLibUtil.getControllerForStack(this.factory, stack, "controller");
            if (controller.getAnimationState() == AnimationState.Stopped) {
                // If you don't do this, the popup animation will only play once because the animation will be cached.
                controller.markNeedsReload();
                //Set the animation to open the jackinthebox which will start playing music and eventually do the actual animation. Also sets it to not loop
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.dark_claw.idle", false));
            }
        }

        if (count == 1) {
            onUse(player.world, player, EnumHand.MAIN_HAND, null);
        }
    }


    @Override
    public void onUse(World world, EntityLivingBase entityLivingBase, EnumHand enumHand, @Nullable List<EntityLivingBase> list) {
        super.onUse(world, entityLivingBase, enumHand, list);
        IProcessManagerImpl.getInstance().addTask(new PlayerUseDarkClaw(1, entityLivingBase));
        entityLivingBase.motionX += 2*Math.cos(Math.toRadians(entityLivingBase.rotationYaw + 90));
        entityLivingBase.motionZ += 2*Math.sin(Math.toRadians(entityLivingBase.rotationYaw + 90));
        entityLivingBase.motionY += 0.5;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.YELLOW + "传闻卡修可以引爆附近的邪念，届时邪念冲天，百里无声。");
        tooltip.add(TextFormatting.YELLOW + "右键蓄力后，会向前跃起，触及地面时会引爆附件的邪念对周围生物造成伤害，并附加[扭曲]效果。");
        tooltip.add(TextFormatting.YELLOW + "升级会提高引爆次数和引爆伤害。");
    }


    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 30;
    }

    public class PlayerUseDarkClaw extends ProcessTask
    {
        private World world;
        private EntityPlayer player;
        public PlayerUseDarkClaw(int priority, Entity taskOwner) {
            super(priority, taskOwner, 30);
            this.world = taskOwner.getEntityWorld();
            this.player = (EntityPlayer) taskOwner;
        }

        @Override
        public void update() {

            if (!world.isRemote && age >= getMaxAge() / 2 && taskOwner.onGround) {

                double cx = taskOwner.posX;
                double cy = taskOwner.posY;
                double cz = taskOwner.posZ;
                IProcessManagerImpl.getInstance().addTask(new PlayerDarkClawExplorsion(1, player, cx, cy, cz, 10*(getLevel(player.getHeldItemMainhand())+3)));
                age = getMaxAge() - 1; // 提前结束
                if (!player.isCreative())
                    player.getHeldItemMainhand().damageItem(1, player);

            }

            age++;
        }
    }

    @Override
    public int getBaseDamage() {
        return 2;
    }

    @Override
    public List<Integer> getAttackDamage() {
        List<Integer> attackList = new ArrayList();

        for(int i = 0; i <= 7; ++i) {
            attackList.add(i);
        }

        return attackList;
    }

    public class PlayerDarkClawExplorsion extends ProcessTask {
        double cx;
        double cy;
        double cz;
        World world;
        EntityPlayer player;

        public PlayerDarkClawExplorsion(int priority, Entity taskOwner, double cx, double cy, double cz, int maxAge) {
            super(priority, taskOwner, maxAge);
            this.world = taskOwner.getEntityWorld();
            this.player = (EntityPlayer) taskOwner;
            this.cx = cx;
            this.cy = cy;
            this.cz = cz;
        }

        @Override
        public void update() {
            if (age % 10 == 0)
            {
                int i = age / 10;
                int m = 4 + i * 2;
                double pi = Math.PI * 2 / m;
                double r = 2.0 + i;
                for (int j = 0; j < m; j++) {
                    SPacketAddRenderTaskBeam packet = new SPacketAddRenderTaskBeam(taskOwner.getEntityId(), 20, cx + r * Math.sin(j * pi), cy, cz + r * Math.cos(j * pi));
                    NetworkLoader.instance.sendToDimension(packet, taskOwner.dimension);
                }

                List<EntityLivingBase> entityLivings = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(cx, cy, cz, cx, cy + 10, cz).grow(r));
                for (EntityLivingBase entityLiving : entityLivings) {
                    if (MagicEquipmentUtils.checkEnemy((EntityLivingBase) taskOwner, entityLiving)) {
                        entityLiving.motionY += 0.6799;
                        IMagicCreatureManagerImpl.getInstance().attackEntityFrom(entityLiving, DamageSource.causePlayerDamage((EntityPlayer) taskOwner).setMagicDamage(), MagicEquipmentUtils.getAttackDamage(player, player.getHeldItem(EnumHand.MAIN_HAND), getEquipmentAttribute()));
                        entityLiving.addPotionEffect(new PotionEffect(PotionInit.POTION_DISTORT, 300, 0));
                    }
                }
            }

            age++;
        }
    }
}