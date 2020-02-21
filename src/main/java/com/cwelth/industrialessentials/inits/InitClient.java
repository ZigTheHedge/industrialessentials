package com.cwelth.industrialessentials.inits;

import com.cwelth.industrialessentials.IndustrialEssentials;
import com.cwelth.industrialessentials.tileentities.containers.client.CoalGeneratorScreen;
import com.cwelth.industrialessentials.tileentities.renderers.AnvilTESR;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.cwelth.industrialessentials.tileentities.renderers.UIUtils.UI_ATLAS;

@Mod.EventBusSubscriber(modid = IndustrialEssentials.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitClient {
    public static void init(final FMLClientSetupEvent event) {
        AnvilTESR.register();
        ScreenManager.registerFactory(IEContent.COAL_GENERATOR_CONTAINER.get(), CoalGeneratorScreen::new);
        //RenderTypeLookup.setRenderLayer(IEContent.COAL_GENERATOR.get(), RenderType.getCutoutMipped());
    }

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (!event.getMap().getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)) {
            return;
        }

        event.addSprite(UI_ATLAS);
    }
}
