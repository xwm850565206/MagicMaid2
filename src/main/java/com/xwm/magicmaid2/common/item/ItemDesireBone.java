package com.xwm.magicmaid2.common.item;

import com.xwm.magicmaid.entity.mob.maid.EntityMagicMaid;
import com.xwm.magicmaid.manager.IMagicBossManager;
import com.xwm.magicmaid.network.NetworkLoader;
import com.xwm.magicmaid.network.SPacketSound;
import com.xwm.magicmaid.object.item.ItemBase;
import com.xwm.magicmaid.object.item.interfaces.ICanGetSkillPoint;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidXimoBoss;
import com.xwm.magicmaid2.common.world.dimension.DimensionNormalLand;
import com.xwm.magicmaid2.core.init.ItemInit;
import com.xwm.magicmaid2.core.init.PotionInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.List;

public class ItemDesireBone extends ItemBase implements ICanGetSkillPoint {

    public ItemDesireBone(String name) {
        super(name);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.YELLOW + "欲望使人逐渐失去理智，彻底沦陷在欲望中后，身体会被欲者接管并发生变化。");
        tooltip.add(TextFormatting.YELLOW + "这种躯体会变得十分具有攻击性，且躯体更加强大。");
        tooltip.add("");
        tooltip.add("右键使用获得饥渴buff");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.isRemote)
            return super.onItemRightClick(worldIn, playerIn, handIn);
        else {
            playerIn.addPotionEffect(new PotionEffect(PotionInit.POTION_DESIRE, 600, 2));
            ItemStack stack = playerIn.getHeldItem(handIn);
            if (!playerIn.isCreative())
                stack.shrink(1);
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        }
    }

    @Override
    public void doRegister() {
        ItemInit.ITEMS.add(this);
    }

    @Override
    public int getSkillPoint(ItemStack itemStack, EntityPlayer entityPlayer) {
        return 5;
    }
}
