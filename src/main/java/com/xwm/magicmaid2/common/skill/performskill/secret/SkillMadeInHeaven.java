package com.xwm.magicmaid2.common.skill.performskill.secret;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.xwm.magicmaid.event.SkillPerformEvent;
import com.xwm.magicmaid.player.skill.IPerformSkill;
import com.xwm.magicmaid.player.skill.perfomskill.PerformSkillBase;
import com.xwm.magicmaid.player.skill.perfomskill.secret.PerformSkillSecretBase;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SkillMadeInHeaven extends PerformSkillSecretBase
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/skill_icon.png");

    protected int updateLCG = (new Random()).nextInt();

    @Override
    public int getPerformEnergy() {
        return 400+100*level;
    }

    @Override
    public int getColdTime() {
        return 1200;
    }

    @Override
    public int getRequirePoint() {
        return level < getMaxLevel() ? 1000*level + 500 : -1;
    }

    @Override
    public String getDetailDescription() {
        return "加速周围时间\n升级会提升加速时间的强度和加速的范围";
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

        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(0, 0, 40, 0, 40, 40);
        GlStateManager.popMatrix();
    }

    @Override
    public void perform(EntityLivingBase playerIn, World worldIn, BlockPos posIn) {

        if (curColdTime > 0) return;
        if (MinecraftForge.EVENT_BUS.post(new SkillPerformEvent<IPerformSkill>(this, playerIn, posIn))) return;
        if (!consumEnergy(playerIn, worldIn, posIn)) return;

        float radius = 4 + level*2;
        int rate = (int) (100 + level*2000);

        updateEntities(worldIn, rate, posIn, radius);
        if(!worldIn.isRemote)
            updateBlocks((WorldServer) worldIn, rate, posIn, radius);

        if (!worldIn.isRemote)
            playerIn.sendMessage(new TextComponentString("人终是要上天堂的，最后再说一遍，时间要开始加速了!"));
        curColdTime = getColdTime();
    }

    @Override
    public void update() {
        this.curColdTime = Math.max(0, this.curColdTime - 1);
    }

    @Override
    public String getName() {
        return super.getName() + ".made_in_heaven";
    }

    public void updateEntities(World world, int rate, BlockPos pos, float radius)
    {
        world.profiler.startSection("entities");
        world.profiler.startSection("global");

        for (int i = 0; i < world.weatherEffects.size(); ++i)
        {
            Entity entity = world.weatherEffects.get(i);

            try
            {
                if(entity.updateBlocked) continue;
                for (int _i = 0; _i < rate; _i++) {
                    ++entity.ticksExisted;
                    entity.onUpdate();
                }
            }
            catch (Throwable throwable2)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable2, "Ticking entity");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being ticked");

                if (entity == null)
                {
                    crashreportcategory.addCrashSection("Entity", "~~NULL~~");
                }
                else
                {
                    entity.addEntityCrashInfo(crashreportcategory);
                }

                if (net.minecraftforge.common.ForgeModContainer.removeErroringEntities)
                {
                    net.minecraftforge.fml.common.FMLLog.log.fatal("{}", crashreport.getCompleteReport());
                    world.removeEntity(entity);
                }
                else
                    throw new ReportedException(crashreport);
            }

            if (entity.isDead)
            {
                world.weatherEffects.remove(i--);
            }
        }

        world.profiler.endStartSection("regular");
        for (int i1 = 0; i1 < world.loadedEntityList.size(); ++i1)
        {
            Entity entity2 = world.loadedEntityList.get(i1);
            if (entity2.getDistanceSq(pos) > radius*radius)
                continue;

            Entity entity3 = entity2.getRidingEntity();
            if (entity3 != null)
            {
                if (!entity3.isDead && entity3.isPassenger(entity2))
                {
                    continue;
                }

                entity2.dismountRidingEntity();
            }

            world.profiler.startSection("tick");

            if (!entity2.isDead && !(entity2 instanceof EntityPlayerMP))
            {
                try
                {
                    net.minecraftforge.server.timings.TimeTracker.ENTITY_UPDATE.trackStart(entity2);
                    for (int _i = 0; _i < rate; _i++) {
                        world.updateEntity(entity2);
                    }
                    net.minecraftforge.server.timings.TimeTracker.ENTITY_UPDATE.trackEnd(entity2);

                }
                catch (Throwable throwable1)
                {
                    CrashReport crashreport1 = CrashReport.makeCrashReport(throwable1, "Ticking entity");
                    CrashReportCategory crashreportcategory1 = crashreport1.makeCategory("Entity being ticked");
                    entity2.addEntityCrashInfo(crashreportcategory1);
                    if (net.minecraftforge.common.ForgeModContainer.removeErroringEntities)
                    {
                        net.minecraftforge.fml.common.FMLLog.log.fatal("{}", crashreport1.getCompleteReport());
                        world.removeEntity(entity2);
                    }
                    else
                        throw new ReportedException(crashreport1);
                }
            }
            world.profiler.endSection();
            world.profiler.startSection("remove");

            if (entity2.isDead)
            {
                int l1 = entity2.chunkCoordX;
                int i2 = entity2.chunkCoordZ;

                if (entity2.addedToChunk && world.isChunkGeneratedAt(l1, i2))
                {
                    world.getChunkFromChunkCoords(l1, i2).removeEntity(entity2);
                }

                world.loadedEntityList.remove(i1--);
                world.onEntityRemoved(entity2);
            }

            world.profiler.endSection();

        }
        world.profiler.endStartSection("blockEntities");
        Iterator<TileEntity> iterator = world.tickableTileEntities.iterator();

        while (iterator.hasNext())
        {
            TileEntity tileentity = iterator.next();

            if (!tileentity.isInvalid() && tileentity.hasWorld())
            {
                BlockPos blockpos = tileentity.getPos();
                if (blockpos.distanceSq(pos) > radius*radius)
                    continue;

                if (world.isBlockLoaded(blockpos, false) && world.getWorldBorder().contains(blockpos)) //Forge: Fix TE's getting an extra tick on the client side....
                {
                    try
                    {
                        world.profiler.func_194340_a(() ->
                        {
                            return String.valueOf((Object)TileEntity.getKey(tileentity.getClass()));
                        });
                        net.minecraftforge.server.timings.TimeTracker.TILE_ENTITY_UPDATE.trackStart(tileentity);
                        for (int _i = 0; _i < rate; _i++) {
                            ((ITickable)tileentity).update();
                        }
                        net.minecraftforge.server.timings.TimeTracker.TILE_ENTITY_UPDATE.trackEnd(tileentity);
                        world.profiler.endSection();
                    }
                    catch (Throwable throwable)
                    {
                        CrashReport crashreport2 = CrashReport.makeCrashReport(throwable, "Ticking block entity");
                        CrashReportCategory crashreportcategory2 = crashreport2.makeCategory("Block entity being ticked");
                        tileentity.addInfoToCrashReport(crashreportcategory2);
                        if (net.minecraftforge.common.ForgeModContainer.removeErroringTileEntities)
                        {
                            net.minecraftforge.fml.common.FMLLog.log.fatal("{}", crashreport2.getCompleteReport());
                            tileentity.invalidate();
                            world.removeTileEntity(tileentity.getPos());
                        }
                        else
                            throw new ReportedException(crashreport2);
                    }
                }
            }

            if (tileentity.isInvalid())
            {
                iterator.remove();
                world.loadedTileEntityList.remove(tileentity);

                if (world.isBlockLoaded(tileentity.getPos()))
                {
                    //Forge: Bugfix: If we set the tile entity it immediately sets it in the chunk, so we could be desyned
                    Chunk chunk = world.getChunkFromBlockCoords(tileentity.getPos());
                    if (chunk.getTileEntity(tileentity.getPos(), net.minecraft.world.chunk.Chunk.EnumCreateEntityType.CHECK) == tileentity)
                        chunk.removeTileEntity(tileentity.getPos());
                }
            }
        }
//        BlockCrops

//        if (!world.isRemote) {
//            world.profiler.endStartSection("blocks");
//            BlockPos.getAllInBox(pos.add(-radius, -radius / 2, -radius), pos.add(radius, radius / 2, radius)).forEach((blockPos) -> {
//                IBlockState iblockstate = world.getBlockState(blockPos);
//                Block block = iblockstate.getBlock();
//                world.profiler.startSection("randomTick");
//                if (block.getTickRandomly()) {
//                    for (int _i = 0; _i < rate; _i++) {
//                        block.randomTick(world, blockPos, iblockstate, world.rand);
//                    }
//                }
//                world.profiler.endSection();
//                world.notifyBlockUpdate(blockPos, iblockstate, iblockstate, 3);
//            });
//        }

        world.profiler.endSection();
        world.profiler.endSection();
        world.updateEntities();
    }

    public void updateBlocks(WorldServer world, int rate, BlockPos pos, float radius)
    {
        int i = world.getGameRules().getInt("randomTickSpeed") * rate;
        boolean flag = world.isRaining();
        boolean flag1 = world.isThundering();
        world.profiler.startSection("pollingChunks");

        for (Iterator<Chunk> iterator = world.getPersistentChunkIterable(Lists.newArrayList(world.getPlayerChunkMap().getChunkIterator()).iterator()); iterator.hasNext(); world.profiler.endSection()) {
            world.profiler.startSection("getChunk");
            Chunk chunk = iterator.next();
            int j = chunk.x * 16;
            int k = chunk.z * 16;
            world.profiler.endStartSection("checkNextLight");
            chunk.enqueueRelightChecks();
            world.profiler.endStartSection("tickChunk");
            chunk.onTick(false);
            world.profiler.endStartSection("tickBlocks");
            BlockPos.getAllInBox(pos.add(-radius, -radius / 2, -radius), pos.add(radius, radius / 2, radius)).forEach((blockPos) -> {

                IBlockState iblockstate = world.getBlockState(blockPos);
                Block block = iblockstate.getBlock();
                if (block instanceof BlockCrops) {
                    for (int _i = 0; _i < rate/100; _i++) {
                        world.profiler.startSection("randomTick");
                        world.immediateBlockTick(blockPos, iblockstate, world.rand);
//                        block.randomTick(world, blockPos, iblockstate, world.rand);
                        world.profiler.endSection();
                    }
                    world.notifyBlockUpdate(blockPos, iblockstate, iblockstate, 3);
                }
            });

//            world.profiler.endSection();
//
            if (i > 0)
            {
                for (ExtendedBlockStorage extendedblockstorage : chunk.getBlockStorageArray())
                {
                    if (extendedblockstorage != Chunk.NULL_BLOCK_STORAGE && extendedblockstorage.needsRandomTick())
                    {
                        for (int i1 = 0; i1 < i; ++i1)
                        {
                            this.updateLCG = this.updateLCG * 3 + 1013904223;
                            int j1 = this.updateLCG >> 2;
                            int k1 = j1 & 15;
                            int l1 = j1 >> 8 & 15;
                            int i2 = j1 >> 16 & 15;
                            if (pos.distanceSq(k1 + j, i2 + extendedblockstorage.getYLocation(), l1 + k) > radius*radius)
                                continue;

                            IBlockState iblockstate = extendedblockstorage.get(k1, i2, l1);
                            Block block = iblockstate.getBlock();
                            world.profiler.startSection("randomTick");

                            if (block.getTickRandomly())
                            {
                                block.randomTick(world, new BlockPos(k1 + j, i2 + extendedblockstorage.getYLocation(), l1 + k), iblockstate, world.rand);
                                world.notifyBlockUpdate(new BlockPos(k1 + j, i2 + extendedblockstorage.getYLocation(), l1 + k), iblockstate, iblockstate, 3);
                            }

                            world.profiler.endSection();
                        }
                    }
                }
            }
//        }
        }

        world.profiler.endSection();
    }

    protected BlockPos adjustPosToNearbyEntity(World world, BlockPos pos)
    {
        BlockPos blockpos = world.getPrecipitationHeight(pos);
        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(blockpos, new BlockPos(blockpos.getX(), world.getHeight(), blockpos.getZ()))).grow(3.0D);
        List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb, new com.google.common.base.Predicate<EntityLivingBase>()
        {
            public boolean apply(@Nullable EntityLivingBase p_apply_1_)
            {
                return p_apply_1_ != null && p_apply_1_.isEntityAlive() && world.canSeeSky(p_apply_1_.getPosition());
            }
        });

        if (!list.isEmpty())
        {
            return ((EntityLivingBase)list.get(world.rand.nextInt(list.size()))).getPosition();
        }
        else
        {
            if (blockpos.getY() == -1)
            {
                blockpos = blockpos.up(2);
            }

            return blockpos;
        }
    }
}
