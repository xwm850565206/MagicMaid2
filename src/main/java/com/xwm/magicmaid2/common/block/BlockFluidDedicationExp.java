package com.xwm.magicmaid2.common.block;

import com.xwm.magicmaid.particle.EnumCustomParticles;
import com.xwm.magicmaid.particle.ParticleSpawner;
import com.xwm.magicmaid.util.interfaces.IRegistrable;
import com.xwm.magicmaid2.core.init.BlockInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class BlockFluidDedicationExp extends BlockFluidClassic implements IRegistrable
{
    public BlockFluidDedicationExp(String name, Fluid fluid, Material material) {
        super(fluid, material);

        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setTickRandomly(true);
//        this.setTickRate(5);

        doRegister();
    }

    @Override
    public void doRegister() {
        BlockInit.BLOCKS.add(this);
        ItemInit.ITEMS.add((new ItemBlock(this)).setRegistryName(this.getRegistryName()));
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }


    @Override
    public void updateTick(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        super.updateTick(world, pos, state, rand);
        if (!world.isAreaLoaded(pos, 1)) return; // Forge: prevent growing cactus from loading unloaded chunks with block update



        AxisAlignedBB bb = new AxisAlignedBB(pos);
        List<EntityLivingBase> entityLivingBases = world.getEntitiesWithinAABB(EntityLivingBase.class, bb);
        for (EntityLivingBase entityLivingBase : entityLivingBases)
        {
            if (entityLivingBase instanceof EntityPlayer) {
                float experienceLevel = ((EntityPlayer) entityLivingBase).experienceLevel;
                if (experienceLevel > 10) {
                    ((EntityPlayer) entityLivingBase).addExperienceLevel(-10);
                    ItemStack stack1 = new ItemStack(ItemInit.EXP_BALL);
                    ItemInit.EXP_BALL.setExp(stack1, 20);
                    if (!entityLivingBase.getEntityWorld().isRemote)
                    {
                        EntityItem entityItem = new EntityItem(entityLivingBase.getEntityWorld(), entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ, stack1);
                        entityItem.setDefaultPickupDelay();
                        entityLivingBase.getEntityWorld().spawnEntity(entityItem);
                    }
                    entityLivingBase.attackEntityFrom(DamageSource.DROWN, 0.1f);
//                    world.playSound((EntityPlayer) entityLivingBase, pos, SoundEvents.ENTITY_PLAYER_HURT_DROWN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
            else {
                entityLivingBase.attackEntityFrom(DamageSource.MAGIC.setMagicDamage().setDamageBypassesArmor(), 4);
            }
        }

        world.scheduleUpdate(pos, this, this.tickRate(world));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        ParticleSpawner.spawnParticle(EnumCustomParticles.WHISPER,
                pos.getX() + rand.nextDouble(),
                pos.getY() + rand.nextDouble(),
                pos.getZ() + rand.nextDouble(),
                0, 0, 0);
    }
}
