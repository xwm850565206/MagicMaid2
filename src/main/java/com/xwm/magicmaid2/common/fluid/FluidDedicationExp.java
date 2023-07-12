package com.xwm.magicmaid2.common.fluid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidDedicationExp extends Fluid
{
    public FluidDedicationExp(String fluidName, ResourceLocation still, ResourceLocation flowing) {
        super(fluidName, still, flowing);
        this.setUnlocalizedName(fluidName);
    }
}
