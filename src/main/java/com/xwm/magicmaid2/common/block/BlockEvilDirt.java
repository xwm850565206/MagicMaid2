package com.xwm.magicmaid2.common.block;

import com.xwm.magicmaid.object.block.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockEvilDirt extends BlockBase {
    public BlockEvilDirt(String name, Material material) {
        super(name, material);
        this.setSoundType(SoundType.GROUND);
    }
}
