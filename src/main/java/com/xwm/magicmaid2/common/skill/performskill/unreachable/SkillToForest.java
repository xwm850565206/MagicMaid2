package com.xwm.magicmaid2.common.skill.performskill.unreachable;

import com.xwm.magicmaid.event.SkillPerformEvent;
import com.xwm.magicmaid.player.skill.IPerformSkill;
import com.xwm.magicmaid.player.skill.perfomskill.unreachable.PerformSkillUnreachableBase;
import com.xwm.magicmaid2.core.init.DimensionInit;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SkillToForest extends PerformSkillUnreachableBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/skill_icon.png");


    @Override
    public int getPerformEnergy() {
        return 200;
    }

    @Override
    public int getColdTime() {
        return 60*20;
    }

    @Override
    public void perform(EntityLivingBase playerIn, World worldIn, BlockPos posIn) {
        if (curColdTime > 0) return;
        if (MinecraftForge.EVENT_BUS.post(new SkillPerformEvent<IPerformSkill>(this, playerIn, posIn))) return;
        if (!consumEnergy(playerIn, worldIn, posIn)) return;

        playerIn.swingArm(EnumHand.MAIN_HAND);

        if (!worldIn.isRemote) {
            teleport(playerIn);
        }

        curColdTime = getColdTime();
    }

    public void teleport(EntityLivingBase playerIn) {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        String command = "forge setdimension " + playerIn.getName() + " " + DimensionInit.DIMENSION_RUIN_FOREST + " 131 51 195";
        server.getCommandManager().executeCommand(server, command);
    }

    @Override
    public int getRequirePoint() {
        return 0;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public String getDetailDescription() {
        return "将玩家传送至荒芜大陆，坐标[131，51，195]";
    }

    @Override
    public void drawIcon(float x, float y, float scale) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);

        double scalex = 46.0 / 40.0;
        double scaley = 46.0 / 40.0;

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 90);
        GlStateManager.scale(scalex, scaley, 1);
        GlStateManager.scale(scale, scale, 1);

        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(0, 0, 80, 40, 40, 40);
        GlStateManager.popMatrix();
    }

    @Override
    public String getName() {
        return super.getName() + ".to_ruin_forest";
    }


    @Override
    public void update() {
        this.curColdTime = Math.max(0, this.curColdTime - 1);
    }

}
