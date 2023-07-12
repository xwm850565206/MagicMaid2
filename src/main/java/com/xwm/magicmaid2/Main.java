package com.xwm.magicmaid2;

import com.xwm.magicmaid2.common.proxy.CommonProxy;
import com.xwm.magicmaid2.core.registry.MagicFormulaRegistry;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, dependencies = "required-after:magic_maid;required-after:geckolib3")
public class Main
{
    public static Logger logger;

    @Mod.Instance(Reference.MODID)
    public static Main instance;

    @SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
    public static CommonProxy proxy;

//
    static {
        FluidRegistry.enableUniversalBucket();
    }


    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event);
        GeckoLib.initialize();
        GeckoLibMod.DISABLE_IN_DEV = true;
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
        MagicFormulaRegistry.registerAllFormula();
    }

    @EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }


    public Logger getLogger() {
        return logger;
    }
}
