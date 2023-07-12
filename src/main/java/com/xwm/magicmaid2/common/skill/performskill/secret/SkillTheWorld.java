package com.xwm.magicmaid2.common.skill.performskill.secret;

import com.xwm.magicmaid.entity.mob.basic.AbstractEntityMagicCreature;
import com.xwm.magicmaid.entity.mob.maid.EntityMagicMaid;
import com.xwm.magicmaid.event.SkillPerformEvent;
import com.xwm.magicmaid.manager.IProcessManagerImpl;
import com.xwm.magicmaid.player.skill.IPerformSkill;
import com.xwm.magicmaid.player.skill.perfomskill.secret.PerformSkillSecretBase;
import com.xwm.magicmaid.util.process.ProcessTask;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class SkillTheWorld extends PerformSkillSecretBase
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/skill_icon.png");

    @Override
    public int getPerformEnergy() {
        if (level == 0)
            return 200;
        else if (level == 1)
            return 400;
        else
            return 800;
    }

    @Override
    public int getColdTime() {
        return 400;
    }

    @Override
    public void perform(EntityLivingBase playerIn, World worldIn, BlockPos posIn) {
        if (curColdTime > 0) return;
        if (MinecraftForge.EVENT_BUS.post(new SkillPerformEvent<IPerformSkill>(this, playerIn, posIn))) return;
        if (!consumEnergy(playerIn, worldIn, posIn)) return;

        playerIn.swingArm(EnumHand.MAIN_HAND);
        IProcessManagerImpl.getInstance().addTask(new ProcessTaskTheWorld(1, playerIn, 20 * (2 + level * 2)));

        curColdTime = getColdTime();
    }

    @Override
    public String getName() {
        return super.getName() + ".the_world";
    }

    @Override
    public int getRequirePoint() {
        return level < getMaxLevel() ? 2000*level + 1000 : -1;
    }

    @Override
    public String getDetailDescription() {
        return "短暂的暂停周围时间\n升级可以提高暂停的时长";
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

        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(0, 0, 40, 40, 40, 40);
        GlStateManager.popMatrix();
    }

    @Override
    public void update() {
        this.curColdTime = Math.max(0, this.curColdTime - 1);
    }

    private static class ProcessTaskTheWorld extends ProcessTask
    {
        public ProcessTaskTheWorld(int priority, Entity taskOwner, int maxAge) {
            super(priority, taskOwner, maxAge);
        }

        @Override
        public void update() {
            if (age == 1) {
                if (!taskOwner.getEntityWorld().isRemote)
                    taskOwner.sendMessage(new TextComponentString("时间停止!"));
                World world = this.taskOwner.getEntityWorld();
                List<EntityLiving> entityLivings = world.getEntities(EntityLiving.class, input -> true);
                for (EntityLiving entityLiving : entityLivings) {
                    entityLiving.setNoAI(true);
                    entityLiving.getEntityData().setBoolean("skill_pause", true);
                    if (entityLiving instanceof AbstractEntityMagicCreature)
                        ((EntityMagicMaid) entityLiving).setItNoAI(true);
                }
            }
            else if (age == getMaxAge()-1) {
                if (!taskOwner.getEntityWorld().isRemote)
                    taskOwner.sendMessage(new TextComponentString("时间恢复!"));
                World world = this.taskOwner.getEntityWorld();
                List<EntityLiving> entityLivings = world.getEntities(EntityLiving.class, input -> {
                    if (input == null)
                        return false;
                    if (input.getEntityData().hasKey("skill_pause") && input.getEntityData().getBoolean("skill_pause"))
                    {
                        return true;
                    }
                    return false;
                });
                for (EntityLiving entityLiving : entityLivings) {
                    entityLiving.setNoAI(false);
                    entityLiving.getEntityData().setBoolean("skill_pause", false);
                    if (entityLiving instanceof AbstractEntityMagicCreature)
                        ((EntityMagicMaid) entityLiving).setItNoAI(false);
                }
            }
            age++;
        }
    }
}
