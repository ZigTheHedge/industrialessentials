package com.cwelth.industrialessentials.inits;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class Configs {
    public static final String CATEGORY_MAIN = "general";
    public static final String CATEGORY_WORLDGEN = "worldgen";

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec.IntValue silverMinY;
    public static ForgeConfigSpec.IntValue silverMaxY;
    public static ForgeConfigSpec.IntValue silverVeinSize;
    public static ForgeConfigSpec.IntValue silverProbability;

    public static ForgeConfigSpec.IntValue leadMinY;
    public static ForgeConfigSpec.IntValue leadMaxY;
    public static ForgeConfigSpec.IntValue leadVeinSize;
    public static ForgeConfigSpec.IntValue leadProbability;

    public static ForgeConfigSpec.IntValue copperMinY;
    public static ForgeConfigSpec.IntValue copperMaxY;
    public static ForgeConfigSpec.IntValue copperVeinSize;
    public static ForgeConfigSpec.IntValue copperProbability;

    public static ForgeConfigSpec.IntValue tinMinY;
    public static ForgeConfigSpec.IntValue tinMaxY;
    public static ForgeConfigSpec.IntValue tinVeinSize;
    public static ForgeConfigSpec.IntValue tinProbability;


    public static ForgeConfigSpec COMMON_CONFIG;

    static {
        COMMON_BUILDER.comment("World Gen configuration").push(CATEGORY_WORLDGEN);

        COMMON_BUILDER.comment("Silver Ore").push("silver");
        silverMinY = COMMON_BUILDER.comment("Minimum Spawn Height").defineInRange("silverMinY", 15, 0, 255);
        silverMaxY = COMMON_BUILDER.comment("Maximum Spawn Height").defineInRange("silverMaxY", 45, 0, 255);
        silverVeinSize = COMMON_BUILDER.comment("Vein Size").defineInRange("silverVeinSize", 7, 0, 255);
        silverProbability = COMMON_BUILDER.comment("Probability. Set to 0 to disable generation.").defineInRange("silverProbability", 5, 0, 100);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Lead Ore").push("lead");
        leadMinY = COMMON_BUILDER.comment("Minimum Spawn Height").defineInRange("leadMinY", 25, 0, 255);
        leadMaxY = COMMON_BUILDER.comment("Maximum Spawn Height").defineInRange("leadMaxY", 55, 0, 255);
        leadVeinSize = COMMON_BUILDER.comment("Vein Size").defineInRange("leadVeinSize", 15, 0, 255);
        leadProbability = COMMON_BUILDER.comment("Probability. Set to 0 to disable generation.").defineInRange("leadProbability", 7, 0, 100);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Copper Ore").push("copper");
        copperMinY = COMMON_BUILDER.comment("Minimum Spawn Height").defineInRange("copperMinY", 20, 0, 255);
        copperMaxY = COMMON_BUILDER.comment("Maximum Spawn Height").defineInRange("copperMaxY", 70, 0, 255);
        copperVeinSize = COMMON_BUILDER.comment("Vein Size").defineInRange("copperVeinSize", 15, 0, 255);
        copperProbability = COMMON_BUILDER.comment("Probability. Set to 0 to disable generation.").defineInRange("copperProbability", 10, 0, 100);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Tin Ore").push("tin");
        tinMinY = COMMON_BUILDER.comment("Minimum Spawn Height").defineInRange("tinMinY", 10, 0, 255);
        tinMaxY = COMMON_BUILDER.comment("Maximum Spawn Height").defineInRange("tinMaxY", 60, 0, 255);
        tinVeinSize = COMMON_BUILDER.comment("Vein Size").defineInRange("tinVeinSize", 10, 0, 255);
        tinProbability = COMMON_BUILDER.comment("Probability. Set to 0 to disable generation.").defineInRange("tinProbability", 10, 0, 100);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("General configuration").push(CATEGORY_MAIN);

        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .preserveInsertionOrder()
                .build();

        configData.load();
        spec.setConfig(configData);
    }

}
