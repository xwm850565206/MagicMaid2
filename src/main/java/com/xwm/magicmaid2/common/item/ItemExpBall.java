package com.xwm.magicmaid2.common.item;

import com.xwm.magicmaid.creativetab.CreativeTabMaid;
import com.xwm.magicmaid.entity.mob.basic.interfaces.IEntityRankCreature;
import com.xwm.magicmaid.object.item.ItemBase;
import com.xwm.magicmaid2.core.init.ItemInit;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemExpBall extends ItemBase
{
    public static final String TAG_KEY = Reference.MODID + "_exp_ball";
    public static final String KEY = "count";

    public ItemExpBall(String name) {
        super(name);
    }

    @Override
    public void doRegister() {
        ItemInit.ITEMS.add(this);
    }


    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab == CreativeTabMaid.CREATIVE_TAB_MAID) {
            ItemStack stack = new ItemStack(ItemInit.EXP_BALL);
            setExp(stack, 100);
            items.add(stack);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(TextFormatting.YELLOW + "邪恶的魔法师们擅长抽取他人的力量为己所用");
        tooltip.add(TextFormatting.YELLOW + "右键需要经验的生物可以将球体里的经验转移到其身上");
        tooltip.add(TextFormatting.GREEN+"经验值: " +  getExp(stack));
    }

    public int getExp(ItemStack stack)
    {
        if (stack.getItem() == ItemInit.EXP_BALL)
        {
            if (stack.getSubCompound(TAG_KEY) == null)
                return 0;
            else {
                NBTTagCompound compound = stack.getOrCreateSubCompound(TAG_KEY);
                int exp = compound.getInteger(KEY);
                return exp;
            }
        }
        else {
            return 0;
        }
    }

    public void setExp(ItemStack stack, int exp)
    {
        NBTTagCompound compound = stack.getOrCreateSubCompound(TAG_KEY);
        compound.setInteger(KEY, exp);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if (target instanceof IEntityRankCreature)
        {
            if (((IEntityRankCreature) target).getRank() >= 2)
                return false;
            if (((IEntityRankCreature) target).getExp() >= 100)
                return false;

            int exp = this.getExp(stack);
            int minus = 100 - ((IEntityRankCreature) target).getExp();
            if (exp > minus) {
                ((IEntityRankCreature) target).setExp(minus + ((IEntityRankCreature) target).getExp());
                this.setExp(stack, exp-minus);
            }
            else {
                ((IEntityRankCreature) target).setExp(exp + ((IEntityRankCreature) target).getExp());
                if (!playerIn.isCreative())
                    stack.shrink(1);
            }

            return true;
        }
        else
            return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }
}
