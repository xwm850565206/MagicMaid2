package com.xwm.magicmaid2.common.item.equipment;

import com.xwm.magicmaid.object.item.equipment.ItemGhostObsessionSword;
import com.xwm.magicmaid.object.item.equipment.ItemObsessionSword;
import com.xwm.magicmaid.util.Reference;
import com.xwm.magicmaid2.common.entity.mob.EntityRoutu;
import com.xwm.magicmaid2.common.entity.throwable.EntityEvilSwordPower;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemEvilGhostObsessionSword extends ItemGhostObsessionSword
{
    private float attackDamage = 1;

    public ItemEvilGhostObsessionSword(String name) {
        super(name);
        setMaxStackSize(1);
        setMaxDamage(200);
    }

    public float getAttackDamage()
    {
        return this.attackDamage;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.YELLOW + "邪恶的能量注入后，手握刀刃就仿佛要失去理智");
        tooltip.add(TextFormatting.YELLOW + "右键可以发射邪恶能量，可以使用邪恶锭修复");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (!worldIn.isRemote)
        {
            EntityEvilSwordPower power = new EntityEvilSwordPower(worldIn, playerIn);
            power.setPosition(playerIn.posX, playerIn.posY, playerIn.posZ);
            power.posY += playerIn.getEyeHeight();
            Vec3d vec3d = playerIn.getLookVec();
            power.shoot(vec3d.x, vec3d.y, vec3d.z, 1.0f, 0f);
            worldIn.spawnEntity(power);
            if (!playerIn.isCreative())
                playerIn.getHeldItem(handIn).damageItem(1, playerIn);

        }
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        if (repair.getItem() == ItemInit.EVIL_INGOT) return true;
        return super.getIsRepairable(toRepair, repair);
    }

    @Override
    public void doRegister() {
        ItemInit.ITEMS.add(this);
    }
}
