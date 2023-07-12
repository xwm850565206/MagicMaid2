package com.xwm.magicmaid2.common.item.equipment;

import com.xwm.magicmaid2.common.entity.throwable.EntityHeavenArrow;
import com.xwm.magicmaid2.core.init.EquipmentInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.List;

public class ItemHeavenArrow extends ItemAnimatableWeapon
{
    public ItemHeavenArrow(String name) {
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

//        EntityHeavenArrow arrow = new EntityHeavenArrow(worldIn);
//        arrow.setRank(2);
//        arrow.setPosition(playerIn.posX, playerIn.posY + 2, playerIn.posZ);
//        arrow.shoot(playerIn.posX, playerIn.posY, playerIn.posZ, 1, 0);
//        if (!worldIn.isRemote)
//            worldIn.spawnEntity(arrow);

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.YELLOW + "天神卡莉尔以其绝对的意志凝聚的箭，锐不可当，可以审判一切，镇压一切，箭离弦，万物洗礼，诸恶不行。");
        tooltip.add(TextFormatting.YELLOW + "在拥有[水晶弓]之后，可以使用水晶弓发射，不消耗箭矢");
    }
}
