package com.cwelth.industrialessentials.inits;

import com.cwelth.industrialessentials.IndustrialEssentials;
import com.cwelth.industrialessentials.tileentities.renderers.AnvilTESR;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = IndustrialEssentials.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitClient {
    public static void init(final FMLClientSetupEvent event) {
        AnvilTESR.register();
        //RenderTypeLookup.setRenderLayer(IEContent.COAL_GENERATOR.get(), RenderType.getCutoutMipped());
    }
}
