package com.cwelth.industrialessentials.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class IEBlockOre extends Block {
    public IEBlockOre(int harvestLevel, float hardness) {
        super(Properties.create(Material.ROCK)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(harvestLevel)
                .hardnessAndResistance(hardness)
        );
    }
}
