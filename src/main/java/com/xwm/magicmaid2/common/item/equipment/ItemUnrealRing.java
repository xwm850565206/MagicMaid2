package com.xwm.magicmaid2.common.item.equipment;

import com.xwm.magicmaid.manager.MagicEquipmentUtils;
import com.xwm.magicmaid.network.NetworkLoader;
import com.xwm.magicmaid.network.entity.SPacketEntityData;
import com.xwm.magicmaid.object.item.ItemSkillBook;
import com.xwm.magicmaid2.client.task.RenderDrivedRing;
import com.xwm.magicmaid2.client.task.RenderTaiJiTask;
import com.xwm.magicmaid2.client.task.RenderTaskManager;
import com.xwm.magicmaid2.common.entity.EntitySoul;
import com.xwm.magicmaid2.core.registry.SkillRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ItemUnrealRing extends ItemAnimatableWeapon
{
    private Random random = new Random();
    public ItemUnrealRing(String name) {
        super(name);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.YELLOW + "狐族至宝，可以引导灵魂之力，连天神都对其敬畏有加。");
        tooltip.add(TextFormatting.YELLOW + "右键会抽取最近敌人的灵魂，抽出的灵魂数量和施法范围会随着武器等级的提升而提升");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.isRemote) {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }

        AxisAlignedBB bb = playerIn.getEntityBoundingBox();
        int radius = getLevel(playerIn.getHeldItem(handIn)) + 1;
        List<EntityLivingBase> entityLivings = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, bb.grow(radius, 1, radius));
        entityLivings.removeIf((entityLiving -> !MagicEquipmentUtils.checkEnemy(playerIn, entityLiving)));
        onUse(worldIn, playerIn, handIn, entityLivings);
        return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public void onUse(World world, EntityLivingBase entityLivingBase, EnumHand enumHand, @Nullable List<EntityLivingBase> list) {
        super.onUse(world, entityLivingBase, enumHand, list);

        if (list == null || list.isEmpty())
            return;

        ItemStack stack = entityLivingBase.getHeldItem(enumHand);
        list.sort(((o1, o2) -> {
                if (entityLivingBase.getDistanceSq(o1) == entityLivingBase.getDistanceSq(o2))
                    return 0;
                else return entityLivingBase.getDistanceSq(o1) < entityLivingBase.getDistanceSq(o2) ? -1 : 1;
            }
        ));

        EntityLivingBase entityLivingBase1 = list.get(0);
        for (int i = 0; i < 1 + getLevel(stack); i++)
        {
            EntitySoul soul = new EntitySoul(world);
            soul.setPosition(entityLivingBase.posX + random.nextInt(2) - 4, entityLivingBase.posY + random.nextInt(3), entityLivingBase.posZ +  random.nextInt(2) - 4);
            soul.init(entityLivingBase, entityLivingBase1, 5);
            if (!world.isRemote) {
                world.spawnEntity(soul);
                SPacketEntityData packet = new SPacketEntityData(soul.getEntityId(), entityLivingBase.dimension, 1, entityLivingBase1.getEntityId() + "", "soul_id");
                NetworkLoader.instance.sendToAll(packet);
            }
        }

        if (entityLivingBase instanceof EntityPlayer)
            if (((EntityPlayer) entityLivingBase).isCreative())
                return;
        stack.damageItem(1, entityLivingBase);
    }
}