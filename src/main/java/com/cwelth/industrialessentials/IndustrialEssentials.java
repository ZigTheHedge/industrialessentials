package com.cwelth.industrialessentials;

import com.cwelth.industrialessentials.inits.Configs;
import com.cwelth.industrialessentials.inits.IEContent;
import com.cwelth.industrialessentials.inits.InitClient;
import com.cwelth.industrialessentials.inits.InitCommon;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(IndustrialEssentials.MODID)
public class IndustrialEssentials {
    public static final String MODID = "industrialessentials";

    public static final Logger LOGGER = LogManager.getLogger();

    public IndustrialEssentials()
    {
        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configs.COMMON_CONFIG);
        Configs.loadConfig(Configs.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("industrialessentials-common.toml"));
        FMLJavaModLoadingContext.get().getModEventBus().addListener(InitCommon::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(InitClient::init);

        IEContent.init();

    }

}
