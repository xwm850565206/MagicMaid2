package com.xwm.magicmaid2.common.skill.performskill.secret;

import com.xwm.magicmaid.event.SkillPerformEvent;
import com.xwm.magicmaid.player.skill.IPerformSkill;
import com.xwm.magicmaid.player.skill.perfomskill.secret.PerformSkillSecretBase;
import com.xwm.magicmaid2.common.storage.PlayerStorage;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SkillJealous extends PerformSkillSecretBase
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/skill_icon.png");

    @Override
    public String getName() {
        return super.getName() + ".jealous";
    }

    @Override
    public int getPerformEnergy() {
        return 2000 - 500*level;
    }

    @Override
    public int getColdTime() {
        return 3000;
    }

    @Override
    public void perform(EntityLivingBase playerIn, World worldIn, BlockPos posIn) {
        if (curColdTime > 0) return;
        if (MinecraftForge.EVENT_BUS.post(new SkillPerformEvent<IPerformSkill>(this, playerIn, posIn))) return;
        if (!consumEnergy(playerIn, worldIn, posIn)) return;

        if (playerIn instanceof EntityPlayer) {
            PlayerStorage.get(worldIn).setPlayerData((EntityPlayer) playerIn);
            PlayerStorage.get(worldIn).setPlayerDimension((EntityPlayer) playerIn);
        }

        if (!worldIn.isRemote)
            playerIn.sendMessage(new TextComponentString("爱着你!"));

        curColdTime = getColdTime();
    }

    @Override
    public int getRequirePoint() {
        return 3000;
    }

    @Override
    public String getDetailDescription() {
        return "来自魔女疯狂的爱，\n释放后会保存当前状态(背包不会被保存),\n玩家死亡后会回溯到当前状态.\n升级可以降低蓝耗";
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawIcon(float x, float y, float scale) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);

        double scalex = 46.0 / 40.0;
        double scaley = 46.0 / 40.0;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 90);
        GlStateManager.scale(scalex, scaley, 1);
        GlStateManager.scale(scale, scale, 1);

        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(0, 0, 0, 80, 40, 40);
        GlStateManager.popMatrix();
    }

    @Override
    public void update() {
        this.curColdTime = Math.max(0, this.curColdTime - 1);
    }
}
