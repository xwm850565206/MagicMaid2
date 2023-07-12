package com.xwm.magicmaid2.common.entity.ai;

import com.xwm.magicmaid.network.NetworkLoader;
import com.xwm.magicmaid.network.entity.SPacketEntityData;
import com.xwm.magicmaid.object.item.equipment.EquipmentAttribute;
import com.xwm.magicmaid.registry.MagicEquipmentRegistry;
import com.xwm.magicmaid2.common.entity.maid.EntityMagicMaidAili;
import com.xwm.magicmaid2.common.entity.EntitySoul;
import com.xwm.magicmaid2.core.enums.EnumState;
import com.xwm.magicmaid2.core.init.EquipmentInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

import java.util.Random;

public class EntityAISoulAttack extends EntityAIBase
{
    public int COLD_TIME = 200;
    private World world;
    private EntityMagicMaidAili taskOwner;
    private int tick;
    private int performTick;
    private Random random;

    public EntityAISoulAttack(World world, EntityMagicMaidAili taskOwner) {
        this.world = world;
        this.taskOwner = taskOwner;
        this.random = new Random();
        this.COLD_TIME = 200 - taskOwner.getRank()*50;
    }

    @Override
    public boolean shouldExecute() {

        if (taskOwner != null && taskOwner.getAttackTarget() != null && taskOwner.isSkillReady(EnumState.SKILL1)) {
            if (!taskOwner.inventory.func_70301_a(0).isEmpty() && MagicEquipmentRegistry.getAttribute(taskOwner.getWeaponType()) == EquipmentInit.UNREAL_RING) {
                return true;
            }
        }
        return false;
    }

    public void startExecuting()
    {
        taskOwner.setState(EnumState.toInt(EnumState.SKILL1));
        performTick = 0;
    }

    public boolean shouldContinueExecuting()
    {
        if (taskOwner!= null && taskOwner.getAttackTarget() != null && performTick < 40)
            if (!taskOwner.inventory.func_70301_a(0).isEmpty() && taskOwner.inventory.func_70301_a(0).getItem() == ItemInit.UNREAL_RING)
                return true;
        return false;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        taskOwner.resetSkillCount(EnumState.SKILL1, 20);
        taskOwner.setState(EnumState.toInt(EnumState.STANDARD));
        tick = 0;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        performTick++;
        if (performTick == 40)
        {
            for (int i = 0; i < 2 * (taskOwner.getRank() + 1); i++)
            {
                EntitySoul soul = new EntitySoul(world);
                soul.setPosition(taskOwner.posX + random.nextInt(2) - 4, taskOwner.posY + random.nextInt(3), taskOwner.posZ +  random.nextInt(2) - 4);
                soul.init(taskOwner, taskOwner.getAttackTarget(), 5);
                if (!world.isRemote) {
                    world.spawnEntity(soul);
                    SPacketEntityData packet = new SPacketEntityData(soul.getEntityId(), taskOwner.dimension, 1, taskOwner.getAttackTarget().getEntityId() + "", "soul_id");
                    NetworkLoader.instance.sendToAll(packet);
                }
            }
        }
    }
}
