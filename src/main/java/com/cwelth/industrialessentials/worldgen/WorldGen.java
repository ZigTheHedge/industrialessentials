package com.cwelth.industrialessentials.worldgen;

import com.cwelth.industrialessentials.inits.Configs;
import com.cwelth.industrialessentials.inits.IEContent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class WorldGen {
    public static void initGen()
    {
        for(Biome biome : ForgeRegistries.BIOMES)
        {
            if(Configs.silverProbability.get() > 0)
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                        new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                IEContent.SILVER_ORE.get().getDefaultState(),
                                Configs.silverVeinSize.get())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(Configs.silverProbability.get(), Configs.silverMinY.get(), Configs.silverMinY.get(), Configs.silverMaxY.get()))));
            if(Configs.leadProbability.get() > 0)
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                        new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                IEContent.LEAD_ORE.get().getDefaultState(),
                                Configs.leadVeinSize.get())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(Configs.leadProbability.get(), Configs.leadMinY.get(), Configs.leadMinY.get(), Configs.leadMaxY.get()))));
            if(Configs.copperProbability.get() > 0)
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                        new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                IEContent.COPPER_ORE.get().getDefaultState(),
                                Configs.copperVeinSize.get())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(Configs.copperProbability.get(), Configs.copperMinY.get(), Configs.copperMinY.get(), Configs.copperMaxY.get()))));
            if(Configs.tinProbability.get() > 0)
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(
                        new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                IEContent.TIN_ORE.get().getDefaultState(),
                                Configs.tinVeinSize.get())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(Configs.tinProbability.get(), Configs.tinMinY.get(), Configs.tinMinY.get(), Configs.tinMaxY.get()))));
        }
    }
}
