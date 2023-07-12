package com.xwm.magicmaid2.common.entity.ai;

import com.xwm.magicmaid.entity.ai.EntityAIMaidAttackRanged;
import net.minecraft.entity.IRangedAttackMob;

public class EntityAIMaidAttackRangedAdjust extends EntityAIMaidAttackRanged
{
    private IRangedAttackMob taskOwner;
    public EntityAIMaidAttackRangedAdjust(IRangedAttackMob attacker, double movespeed, int maxAttackTime, float maxAttackDistanceIn) {
        super(attacker, movespeed, maxAttackTime, maxAttackDistanceIn);
        this.taskOwner = attacker;
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.taskOwner.setSwingingArms(true);
    }

    @Override
    public void resetTask() {
        super.resetTask();
        this.taskOwner.setSwingingArms(false);
    }
}
