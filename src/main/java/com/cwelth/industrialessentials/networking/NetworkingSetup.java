package com.cwelth.industrialessentials.networking;

import com.cwelth.industrialessentials.IndustrialEssentials;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkingSetup {
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(IndustrialEssentials.MODID, "industrialessentials"), () -> "1.0", s -> true, s -> true);
    }
}
