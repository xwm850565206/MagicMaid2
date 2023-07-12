package com.xwm.magicmaid2.common.item.equipment;

import com.xwm.magicmaid.manager.IProcessManagerImpl;
import com.xwm.magicmaid2.common.entity.throwable.EntityEvilDeathBall;
import com.xwm.magicmaid2.common.item.equipment.ItemAnimatableWeapon;
import com.xwm.magicmaid2.core.init.EquipmentInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDarkSoul extends ItemAnimatableWeapon
{
    public ItemDarkSoul(String name) {
        super(name);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.YELLOW + "传闻卡修在千年之前的一场战斗中，将附近所有死去的生灵之魂聚集在一起，并以某种邪恶的手段将其凝聚成晶。");
        tooltip.add(TextFormatting.YELLOW + "右键蓄力后释放暗灭法球，蓄力时间随着武器等级提升而缩减");
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
    public void onUse(World world, EntityLivingBase entityLivingBase, EnumHand enumHand, @Nullable List<EntityLivingBase> list) {
        super.onUse(world, entityLivingBase, enumHand, list);

        if (world.isRemote)
            return;
        ItemStack stack = entityLivingBase.getHeldItem(enumHand);
        EntityEvilDeathBall ball = new EntityEvilDeathBall(world, entityLivingBase);
        ball.posX = entityLivingBase.posX;
        ball.posY = entityLivingBase.posY + entityLivingBase.height / 2.0;
        ball.posZ = entityLivingBase.posZ;

        double d0 = Math.cos(Math.toRadians(entityLivingBase.rotationYaw + 90));
        double d1 = -Math.sin(Math.toRadians(entityLivingBase.rotationPitch));
        double d2 = Math.sin(Math.toRadians(entityLivingBase.rotationYaw + 90));
        ball.shoot(d0, d1, d2, 0.8F, 0F);
        world.spawnEntity(ball);
        if (entityLivingBase instanceof EntityPlayer)
            if (((EntityPlayer) entityLivingBase).isCreative())
                return;
        stack.damageItem(1, entityLivingBase);
    }

    @Override
    public void onUsing(ItemStack stack, EntityLivingBase player, int count) {
        if (count == 1) {
            onUse(player.world, player, EnumHand.MAIN_HAND, null);
        }
    }


    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 100 - 10 * getLevel(stack);
    }


}