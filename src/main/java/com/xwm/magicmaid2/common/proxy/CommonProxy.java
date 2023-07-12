package com.xwm.magicmaid2.common.proxy;

import com.xwm.magicmaid.manager.IMagicBossManagerImpl;
import com.xwm.magicmaid.object.item.ItemSkillBook;
import com.xwm.magicmaid.registry.MagicSkillRegistry;
import com.xwm.magicmaid2.common.world.gen.StructureRuinForestPieces;
import com.xwm.magicmaid2.core.init.BiomeInit;
import com.xwm.magicmaid2.core.init.DimensionInit;
import com.xwm.magicmaid2.core.init.FluidInit;
import com.xwm.magicmaid2.core.init.PotionInit;
import com.xwm.magicmaid2.core.network.NetworkLoader;
import com.xwm.magicmaid2.core.registry.SkillRegistry;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        SkillRegistry.registerAll();
        FluidInit.registerFluids();
        PotionInit.registerPotions();
        BiomeInit.registerBiomes();
        DimensionInit.registerDimensions();
        NetworkLoader.registerPackets(event);
    }

    public void init(FMLInitializationEvent event){
        StructureRuinForestPieces.registerPieces();
    }

    public void postInit(FMLPostInitializationEvent event){
        IMagicBossManagerImpl.whiteDomain.add(Reference.MODID);
    }

    public void registerItemRenderer(Item item, int meta, String id) {

    }

    public void registerOBJRenderer(Item item, int meta, String id) {

    }

    public void serverStarting(FMLServerStartingEvent event)
    {

    }

    public void changeSkillSwitch(){

    }

    public ItemStack getWrittenBook(String name)
    {
        return ItemStack.EMPTY;
    }
}
