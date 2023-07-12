package com.xwm.magicmaid2.common.item.equipment;

import com.xwm.magicmaid2.common.entity.throwable.EntityStarArrow;
import com.xwm.magicmaid2.common.item.equipment.ItemAnimatableWeapon;
import com.xwm.magicmaid2.core.init.EquipmentInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemStarArrow extends ItemAnimatableWeapon {

    public ItemStarArrow(String name) {
        super(name);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

//        EntityStarArrow arrow = new EntityStarArrow(worldIn);
//        arrow.setType(6);
//        arrow.setPosition(playerIn.posX, playerIn.posY + 2, playerIn.posZ);
//        arrow.shoot(playerIn.posX, playerIn.posY, playerIn.posZ, 1, 1);
//        if (!worldIn.isRemote)
//            worldIn.spawnEntity(arrow);

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.YELLOW + "星空之箭，传说卡莉尔取一片星辰余晖凝练，又以天堂不朽树的一小节断枝为箭骨制作而成，其内有无穷的奥妙。");
        tooltip.add(TextFormatting.YELLOW + "在拥有[水晶弓]之后，可以使用水晶弓发射，不消耗箭矢");
    }
}
