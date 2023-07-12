package com.xwm.magicmaid2.core.init;

import com.xwm.magicmaid2.common.fluid.FluidDedicationExp;
import com.xwm.magicmaid2.common.fluid.FluidDedicationLife;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.awt.*;

public class FluidInit
{
    public static final Fluid DEDICATION_EXP_FLUID = new FluidDedicationExp("dedication_exp", new ResourceLocation(Reference.MODID, "blocks/dedication_exp_still"), new ResourceLocation(Reference.MODID, "blocks/dedication_exp_flow"));
    public static final Fluid DEDICATION_LIFE_FLUID = new FluidDedicationLife("dedication_life", new ResourceLocation(Reference.MODID, "blocks/dedication_life_still"), new ResourceLocation(Reference.MODID, "blocks/dedication_life_flow"));

    public static void registerFluids()
    {
        registerFluid(DEDICATION_EXP_FLUID);
        registerFluid(DEDICATION_LIFE_FLUID);

        DEDICATION_EXP_FLUID.setBlock(BlockInit.DEDICATION_EXP_BLOCK);
        DEDICATION_EXP_FLUID.setBlock(BlockInit.DEDICATION_LIFE_BLOCK);
    }

    public static void registerFluid(Fluid fluid)
    {
        FluidRegistry.registerFluid(fluid);
        FluidRegistry.addBucketForFluid(fluid);
    }
}
