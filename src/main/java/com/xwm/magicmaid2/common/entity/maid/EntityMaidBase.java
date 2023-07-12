package com.xwm.magicmaid2.common.entity.maid;

import com.xwm.magicmaid.entity.mob.maid.EntityMagicMaid;
import com.xwm.magicmaid.entity.mob.weapon.EntityMaidWeapon;
import com.xwm.magicmaid.network.NetworkLoader;
import com.xwm.magicmaid.network.entity.SPacketEntityData;
import com.xwm.magicmaid.object.item.equipment.EquipmentAttribute;
import com.xwm.magicmaid.object.item.equipment.ItemEquipment;
import com.xwm.magicmaid.registry.MagicEquipmentRegistry;
import com.xwm.magicmaid2.core.enums.EnumState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;

import java.util.HashMap;
import java.util.Map;

public abstract class EntityMaidBase extends EntityMagicMaid implements IAnimatable
{
    protected Map<EnumState, Integer> SKILL_COUNT = new HashMap<>();

    public EntityMaidBase(World worldIn) {
        super(worldIn);
        SKILL_COUNT.put(EnumState.SKILL1, 0);
        SKILL_COUNT.put(EnumState.SKILL2, 0);
        weapon = new EntityMaidWeapon(worldIn); // just to avoid maid to find her weapon every time, because maid2 has no weapon entity
    }

    @Override
    public AxisAlignedBB getUsingArea(ItemStack itemStack, EntityLivingBase entityLivingBase, AxisAlignedBB axisAlignedBB) {
        return new AxisAlignedBB(0 , 0, 0, 0, 0, 0);
    }

    public boolean isSkillReady(EnumState state)
    {
        return SKILL_COUNT.getOrDefault(state, 1) <= 0;
    }

    public void descreasingSkillCount(EnumState state)
    {
        SKILL_COUNT.put(state, Math.max(0, SKILL_COUNT.getOrDefault(state, 1) - 1));
    }

    public void resetSkillCount(EnumState state, int count)
    {
        SKILL_COUNT.put(state, count);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        SKILL_COUNT.forEach((state, count) -> {
            descreasingSkillCount(state);
        });

        if (getAttackTarget() == this) {
            this.setAttackTarget(null);
        }
    }

    @Override
    public void onDeathUpdate() {
        super.onDeathUpdate();
    }

}
