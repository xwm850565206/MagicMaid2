package com.xwm.magicmaid2.common.skill.performskill.unreachable;

import com.xwm.magicmaid.event.SkillPerformEvent;
import com.xwm.magicmaid.manager.IProcessManagerImpl;
import com.xwm.magicmaid.manager.MagicEquipmentUtils;
import com.xwm.magicmaid.player.skill.IPerformSkill;
import com.xwm.magicmaid.player.skill.perfomskill.unreachable.PerformSkillUnreachableBase;
import com.xwm.magicmaid.util.process.ProcessTask;
import com.xwm.magicmaid2.client.task.RenderTaiJiTask;
import com.xwm.magicmaid2.client.task.RenderTaskManager;
import com.xwm.magicmaid2.core.network.NetworkLoader;
import com.xwm.magicmaid2.core.network.packet.server.SPacketAddRenderTaskTaiJi;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

public class SkillTaiJi extends PerformSkillUnreachableBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/skill_icon.png");


    @Override
    public int getPerformEnergy() {
        return 200*level;
    }

    @Override
    public int getColdTime() {
        return 8*20*(level) + 60*level;
    }

    @Override
    public void perform(EntityLivingBase playerIn,World worldIn, BlockPos posIn) {
        if (curColdTime > 0) return;
        if (MinecraftForge.EVENT_BUS.post(new SkillPerformEvent<IPerformSkill>(this, playerIn, posIn))) return;
        if (!consumEnergy(playerIn, worldIn, posIn)) return;

        playerIn.swingArm(EnumHand.MAIN_HAND);
        IProcessManagerImpl.getInstance().addTask(new ProcessSkillTaskTaiJi(1, playerIn, level, 20*8*(level)));

        if (!worldIn.isRemote) {
            playerIn.sendMessage(new TextComponentString("阴阳调和，构建阵图!"));
            SPacketAddRenderTaskTaiJi packet = new SPacketAddRenderTaskTaiJi(playerIn.getEntityId(), 8*20*(level)*3, playerIn.posX, playerIn.posY, playerIn.posZ, 6*level+6);
            NetworkLoader.instance.sendToDimension(packet, worldIn.provider.getDimension());
        }

        curColdTime = getColdTime();
    }

    @Override
    public int getRequirePoint() {
        return level*1000;
    }

    @Override
    public String getDetailDescription() {
        return "初步窥探[调和]，领悟出的力量。\n可以极大的减缓施法范围内实体的速度和转动。\n升级可以延长影响时间和影响范围。";
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

        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(0, 0, 40, 80, 40, 40);
        GlStateManager.popMatrix();
    }

    @Override
    public String getName() {
        return super.getName() + ".taiji";
    }


    @Override
    public void update() {
        this.curColdTime = Math.max(0, this.curColdTime - 1);
    }

    private static class ProcessSkillTaskTaiJi extends ProcessTask {

        private World world;
        private EntityLivingBase player;
        private int level;

        private BlockPos pos;

        public ProcessSkillTaskTaiJi(int priority, EntityLivingBase taskOwner, int level, int maxAge) {
            super(priority, taskOwner, maxAge);
            this.world = taskOwner.getEntityWorld();
            this.player = taskOwner;
            this.level = level;
            this.pos = player.getPosition();
        }

        @Override
        public void update() {

            AxisAlignedBB bb = new AxisAlignedBB(pos).grow(6*level+6, 128, 6*level+6);
            List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, bb);
            for (Entity entity : entities)
            {
                if (entity instanceof EntityLivingBase)
                {
                    if (MagicEquipmentUtils.checkEnemy(player, (EntityLivingBase) entity)) {
                        entity.motionX = 0;
                        entity.motionY = 0;
                        entity.motionZ = 0;
                        entity.rotationYaw = 0;
                        entity.rotationPitch = 0;
                    }
                }
               else {
                    entity.motionX = 0;
                    entity.motionY = 0;
                    entity.motionZ = 0;
                    entity.rotationYaw = 0;
                    entity.rotationPitch = 0;
                }
            }


            age++;
        }
    }
}
