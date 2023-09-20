package com.xwm.magicmaid2.common.event;

import com.google.common.collect.Lists;
import com.xwm.magicmaid.entity.mob.basic.interfaces.IEntityAvoidThingCreature;
import com.xwm.magicmaid.entity.mob.basic.interfaces.IEntityBossCreature;
import com.xwm.magicmaid.init.DimensionInit;
import com.xwm.magicmaid.manager.IMagicBossManager;
import com.xwm.magicmaid.manager.IMagicCreatureManagerImpl;
import com.xwm.magicmaid.network.NetworkLoader;
import com.xwm.magicmaid.network.entity.SPacketEntityData;
import com.xwm.magicmaid.store.WorldDifficultyData;
import com.xwm.magicmaid.world.dimension.DimensionChurch;
import com.xwm.magicmaid2.common.storage.PlayerStorage;
import com.xwm.magicmaid2.common.tileentity.*;
import com.xwm.magicmaid2.core.init.BlockInit;
import com.xwm.magicmaid2.core.init.EntityInit;
import com.xwm.magicmaid2.core.init.ItemInit;
import com.xwm.magicmaid2.core.init.PotionInit;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class EventLoader {

    private static Random RANDOM = new Random();

    /**
     * 注册实体
     * @param event
     */
    @SubscribeEvent
    public static void onEntityRegister(RegistryEvent.Register<EntityEntry> event)
    {
        EntityInit.registerEntities(event);
    }

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
        GameRegistry.registerTileEntity(TileEntityForestPortal.class, new ResourceLocation(Reference.MODID, "forest_portal"));
        GameRegistry.registerTileEntity(TileEntityCorpse.class, new ResourceLocation(Reference.MODID, "corpse"));
        GameRegistry.registerTileEntity(TileEntitySymbol.class, new ResourceLocation(Reference.MODID, "symbol"));
        GameRegistry.registerTileEntity(TileEntityMagicCircleLevel1.class, new ResourceLocation(Reference.MODID, "magic_circle_level_1"));
        GameRegistry.registerTileEntity(TileEntityDedicationDead.class, new ResourceLocation(Reference.MODID, "dedication_dead"));
        GameRegistry.registerTileEntity(TileEntityCandleStick.class, new ResourceLocation(Reference.MODID, "candle_stck"));
        GameRegistry.registerTileEntity(TileEntityEvilSkeleton.class, new ResourceLocation(Reference.MODID, "evil_skeleton"));
        GameRegistry.registerTileEntity(TileEntityHolyCross.class, new ResourceLocation(Reference.MODID, "holy_cross_maid2"));

    }


    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        World world = event.world;
        if (world.provider.getDimension() == DimensionInit.DIMENSION_CHURCH && world.provider instanceof DimensionChurch)
        {
            IMagicBossManager fightManager = ((DimensionChurch) world.provider).getFightManager();
            if (fightManager != null && !fightManager.getBossFirstKilled() && fightManager.getBossKilled())
            {
                fightManager.setBossFirstKilled(true);
                ((DimensionChurch) world.provider).generateNextWorldPortal(Blocks.GOLD_BLOCK.getDefaultState(), BlockInit.BLOCK_FOREST_PORTAL.getDefaultState());
            }
        }
    }

    @SubscribeEvent
    public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event)
    {
        EntityLivingBase entityLivingBase = event.getEntityLiving();

        if (entityLivingBase.getActivePotionEffect(PotionInit.POTION_CRASY) != null)
        {
            if (entityLivingBase.getEntityData().hasKey("crasy_rabbit") && entityLivingBase.getEntityData().getInteger("crasy_rabbit") != -1)
            {
                int id = entityLivingBase.getEntityData().getInteger("crasy_rabbit");
                Entity entity = entityLivingBase.getEntityWorld().getEntityByID(id);
                if (entity != null && entity.isEntityAlive() && entity instanceof EntityRabbit)
                {
                    entityLivingBase.addPotionEffect(new PotionEffect(PotionInit.POTION_CRASY, 120, 1));
                }
                else
                {
                    entityLivingBase.getEntityData().setInteger("crasy_rabbit", -1);
                    if (!entityLivingBase.world.isRemote)
                        entityLivingBase.addPotionEffect(new PotionEffect(PotionInit.POTION_CRASY, 0, 9));
                }
            }
        }

        if (entityLivingBase instanceof EntityPlayerMP) // update client side
        {
            if (entityLivingBase.getEntityData().hasKey("crasy_rabbit")){
                SPacketEntityData packet = new SPacketEntityData(entityLivingBase.getEntityId(), entityLivingBase.dimension, 1, entityLivingBase.getEntityData().getInteger("crasy_rabbit") + "", "crasy_rabbit");
                NetworkLoader.instance.sendTo(packet, (EntityPlayerMP) entityLivingBase);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event)
    {
        EntityLivingBase entityLivingBase = event.getEntityLiving();
        if (entityLivingBase.getActivePotionEffect(PotionInit.POTION_CRASY) != null)
        {
            entityLivingBase.isDead = false;
            entityLivingBase.setHealth(0.5f);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onEntityDamaged(LivingDamageEvent event)
    {
        EntityLivingBase entityLivingBase = event.getEntityLiving();
        if (!entityLivingBase.world.isRemote && (entityLivingBase).getActivePotionEffect(PotionInit.POTION_DISTORT) != null)
        {
            if (RANDOM.nextFloat() < 0.1 + 0.1 * WorldDifficultyData.get(entityLivingBase.world).getWorldDifficulty())
            {
                if (entityLivingBase instanceof EntityPlayer) {
                    ((EntityPlayer) entityLivingBase).setHealth(0);
                    entityLivingBase.sendMessage(new TextComponentString(entityLivingBase.getName() + " 因为肉身撕裂而亡"));
                    ((EntityPlayer) entityLivingBase).inventory.dropAllItems();
                }
                else if (entityLivingBase instanceof IEntityAvoidThingCreature)
                {
                    if (!(entityLivingBase instanceof IEntityBossCreature)) // boss 不能被肉身撕裂
                        IMagicCreatureManagerImpl.getInstance().setDead((IEntityAvoidThingCreature) entityLivingBase);
                }
                else {
                    entityLivingBase.setDead();
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRightItem(PlayerInteractEvent.RightClickItem event)
    {
        EntityPlayer entityPlayer = event.getEntityPlayer();
        if (!entityPlayer.world.isRemote && entityPlayer.getActivePotionEffect(PotionInit.POTION_HEAVEN) != null)
        {
            entityPlayer.setHealth(0.5f);
            entityPlayer.sendMessage(new TextComponentString("天堂之力，恶魔堕入地狱!"));
            FMLCommonHandler.instance().getMinecraftServerInstance().commandManager.executeCommand(entityPlayer, "forge setdimension " + entityPlayer.getUniqueID() + " -1");
        }
        else if (!entityPlayer.world.isRemote && event.getItemStack().getItem() == com.xwm.magicmaid.init.ItemInit.ITEM_EVIL)
        {
            entityPlayer.addPotionEffect(new PotionEffect(PotionInit.POTION_EVIL, 4800, 1));
            if (!entityPlayer.isCreative())
                event.getItemStack().shrink(1);
        }
    }

    @SubscribeEvent
    public static void onPlayerDieEvent(LivingHurtEvent event)
    {
        EntityLivingBase entityLivingBase = event.getEntityLiving();
        if (entityLivingBase instanceof EntityPlayer)
        {
            if (entityLivingBase.getEntityWorld().isRemote)
                return;

            if (entityLivingBase.getHealth() <= event.getAmount()){
                List<ItemStack> stacks = Lists.newArrayList(entityLivingBase.getArmorInventoryList());
                if (stacks.get(1).getItem() == ItemInit.EVIL_LEGGINGS){
                    entityLivingBase.setHealth(entityLivingBase.getMaxHealth());
                    entityLivingBase.sendMessage(new TextComponentString(TextFormatting.YELLOW + "伤害免疫，邪恶护膝碎裂"));
                    event.setAmount(0);
                    stacks.get(1).shrink(1);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        if (event.isWasDeath())
        {
            EntityPlayer player = event.getOriginal();
            EntityPlayer newPlayer = event.getEntityPlayer();
            NBTTagCompound compound = PlayerStorage.get(player.world).getPlayerData(player.getUniqueID());
            if (compound != null) {
                compound.setTag("Inventory", newPlayer.inventory.writeToNBT(new NBTTagList())); // 背包不能还原 防止玩家刷物品
                newPlayer.readFromNBT(compound);
                newPlayer.getEntityData().setBoolean("jealous_love", true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerReSpawn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent event)
    {
        EntityPlayer player = event.player;
        if (player.world.isRemote)
            return;

        if (player.getEntityData().hasKey("jealous_love"))
        {
            NBTTagCompound compound = PlayerStorage.get(player.world).getPlayerData(player.getUniqueID());
            if (compound == null)
                return;

            NBTTagList nbttaglist = compound.getTagList("Pos", 6);
            double posX = nbttaglist.getDoubleAt(0);
            double posY = nbttaglist.getDoubleAt(1);
            double posZ = nbttaglist.getDoubleAt(2);
            int dimension =  PlayerStorage.get(player.world).getPlayerDimension(player.getUniqueID());
            if (dimension == player.dimension)
                return;
            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
            String rawCommand = "forge setdimension " + player.getName() + " " + dimension + " " + posX + " " + posY + " " + posZ;
            server.commandManager.executeCommand(server, rawCommand);
            player.readFromNBT(compound);
            player.getEntityData().removeTag("jealous_love");

        }
    }
}
