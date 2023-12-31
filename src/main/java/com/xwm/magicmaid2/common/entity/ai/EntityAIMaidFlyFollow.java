package com.xwm.magicmaid2.common.entity.ai;

import com.xwm.magicmaid.entity.mob.basic.AbstractEntityMagicCreature;
import com.xwm.magicmaid.entity.mob.basic.interfaces.IEntityMultiModeCreature;
import com.xwm.magicmaid.entity.mob.basic.interfaces.IEntityTameableCreature;
import com.xwm.magicmaid.enumstorage.EnumMode;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFollowOwnerFlying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class EntityAIMaidFlyFollow extends EntityAIBase
{
    private final AbstractEntityMagicCreature tameable;
    private EntityLivingBase owner;
    World world;
    private final double followSpeed;
    private final PathNavigate petPathfinder;
    private int timeToRecalcPath;
    float maxDist;
    float minDist;
    private float oldWaterCost;

    private BlockPos destRecord;

    public EntityAIMaidFlyFollow(AbstractEntityMagicCreature tameableIn, double followSpeedIn, float minDistIn, float maxDistIn)
    {
        this.tameable = tameableIn;
        this.world = tameableIn.world;
        this.followSpeed = followSpeedIn;
        this.petPathfinder = tameableIn.getNavigator();
        this.minDist = minDistIn;
        this.maxDist = maxDistIn;
        this.destRecord = null;
        this.setMutexBits(3);

        if (!(tameableIn.getNavigator() instanceof PathNavigateGround) && !(tameableIn.getNavigator() instanceof PathNavigateFlying))
        {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }

        if (!(this.tameable instanceof IEntityTameableCreature))
            throw new IllegalArgumentException("need IEntityTameableCreature");
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!((IEntityTameableCreature)this.tameable).hasOwner())
            return false;

        EntityLivingBase entityLivingBase = (EntityLivingBase) FMLCommonHandler.instance()
                .getMinecraftServerInstance().getEntityFromUuid(((IEntityTameableCreature)this.tameable).getOwnerID());

        if (entityLivingBase == null)
        {
            return false;
        }
        else if (entityLivingBase instanceof EntityPlayer && ((EntityPlayer)entityLivingBase).isSpectator())
        {
            return false;
        }
        else if (((IEntityTameableCreature)this.tameable).isSitting())
        {
            return false;
        }
        else if (this.tameable.getDistanceSq(entityLivingBase) < (double)(this.minDist * this.minDist))
        {
            return false;
        }
        else if (this.tameable.isPerformAttack())
        {
            return false;
        }
        else if (this.tameable instanceof IEntityMultiModeCreature && ((IEntityMultiModeCreature) this.tameable).getMode() == EnumMode.toInt(EnumMode.SITTING))
        {
            return false;
        }
        else
        {
            this.owner = entityLivingBase;
            this.destRecord = this.owner.getPosition();
            return true;
        }
    }

    public boolean shouldContinueExecuting()
    {
        return !this.petPathfinder.noPath() && this.tameable.getDistanceSq(this.owner) > (double)(this.maxDist * this.maxDist) && !((IEntityTameableCreature)this.tameable).isSitting();
    }

    public void startExecuting()
    {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.tameable.getPathPriority(PathNodeType.WATER);
        this.tameable.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    public void resetTask()
    {
        this.owner = null;
        this.petPathfinder.clearPath();
        this.tameable.setPathPriority(PathNodeType.WATER, this.oldWaterCost);

        if (this.destRecord != null && this.tameable.getDistanceSq(this.destRecord) > minDist*minDist)
        {
            this.tameable.setPosition(this.destRecord.getX(), this.destRecord.getY(), this.destRecord.getZ());
        }
        this.destRecord = null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        this.tameable.getLookHelper().setLookPositionWithEntity(this.owner, 90.0F, (float)this.tameable.getVerticalFaceSpeed());
        this.destRecord = this.owner.getPosition();
        if (!((IEntityTameableCreature)this.tameable).isSitting())
        {
            if (--this.timeToRecalcPath <= 0)
            {
                this.timeToRecalcPath = 10;

                if (!this.petPathfinder.tryMoveToEntityLiving(this.owner, this.followSpeed))
                {
                    if (!this.tameable.getLeashed() && !this.tameable.isRiding())
                    {
                        if (this.tameable.getDistanceSq(this.owner) >= 144.0D)
                        {
                            int i = MathHelper.floor(this.owner.posX) - 2;
                            int j = MathHelper.floor(this.owner.posZ) - 2;
                            int k = MathHelper.floor(this.owner.getEntityBoundingBox().minY);

                            for (int l = 0; l <= 4; ++l)
                            {
                                for (int i1 = 0; i1 <= 4; ++i1)
                                {
                                    if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.isTeleportFriendlyBlock(i, j, k, l, i1))
                                    {
                                        this.tameable.setLocationAndAngles((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), this.tameable.rotationYaw, this.tameable.rotationPitch);
                                        this.petPathfinder.clearPath();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    protected boolean isTeleportFriendlyBlock(int x, int p_192381_2_, int y, int p_192381_4_, int p_192381_5_)
    {
        return this.world.isAirBlock(new BlockPos(x + p_192381_4_, y, p_192381_2_ + p_192381_5_)) && this.world.isAirBlock(new BlockPos(x + p_192381_4_, y + 1, p_192381_2_ + p_192381_5_));
    }
}
