package com.xwm.magicmaid2.common.item;

import com.xwm.magicmaid.entity.mob.maid.EntityMagicMaid;
import com.xwm.magicmaid.manager.IMagicBossManager;
import com.xwm.magicmaid.network.NetworkLoader;
import com.xwm.magicmaid.network.SPacketSound;
import com.xwm.magicmaid.object.item.ItemBase;
import com.xwm.magicmaid.object.item.interfaces.ICanGetSkillPoint;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidXimoBoss;
import com.xwm.magicmaid2.common.world.dimension.DimensionNormalLand;
import com.xwm.magicmaid2.common.world.dimension.DimensionRuinForest;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.List;

public class ItemSageBone extends ItemBase implements ICanGetSkillPoint {

    public ItemSageBone(String name) {
        super(name);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(TextFormatting.YELLOW + "高贵的遗骨，不为外欲扰动，在欲念横流的大陆显得格格不入，是汐墨最爱的养料");
        tooltip.add(TextFormatting.YELLOW + "右键使用，可以召唤汐墨Boss");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.isRemote)
            return super.onItemRightClick(worldIn, playerIn, handIn);
        else {
            if (!(worldIn.provider instanceof DimensionNormalLand)) {
                playerIn.sendMessage(new TextComponentString("贤者之骨只有在荒芜之地才能使用"));
                return ActionResult.newResult(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
            }

            //如果boss存在，就不该再生成boss了
            IMagicBossManager fightManager = ((DimensionNormalLand) worldIn.provider).getFightManager();
            if (fightManager.getBossAlive()) {
                playerIn.sendMessage(new TextComponentString("请击杀boss后再使用贤者之骨"));
                return ActionResult.newResult(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
            }

            EntityMagicMaid bossMaid = new EntityMagicMaidXimoBoss(worldIn);
            BlockPos pos = new BlockPos(playerIn.posX + itemRand.nextInt(10) - 5, playerIn.posY, playerIn.posZ + itemRand.nextInt(10) - 5);
            bossMaid.setPosition(pos.getX(), pos.getY() + 10 + itemRand.nextInt(20), pos.getZ());
            bossMaid.setAttackTarget(playerIn);
            worldIn.spawnEntity(bossMaid);
            fightManager.init(bossMaid);

            SPacketSound packet = new SPacketSound(0, pos);
            NetworkRegistry.TargetPoint target = new NetworkRegistry.TargetPoint(worldIn.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 40.0D);
            NetworkLoader.instance.sendToAllAround(packet, target);
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
        return 20;
    }
}
