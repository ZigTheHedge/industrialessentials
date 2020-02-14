package com.cwelth.industrialessentials.inits;

import com.cwelth.industrialessentials.IndustrialEssentials;
import com.cwelth.industrialessentials.items.Hammer;
import com.cwelth.industrialessentials.networking.NetworkingSetup;
import com.cwelth.industrialessentials.recipes.AnvilRecipes;
import com.cwelth.industrialessentials.worldgen.WorldGen;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = IndustrialEssentials.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InitCommon {
    public static AnvilRecipes anvilRecipes = new AnvilRecipes();

    public static final ItemGroup creativeTab = new ItemGroup("industrialessentials") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(IEContent.COAL_GENERATOR.get());
        }
    };

    public static void init(final FMLCommonSetupEvent event) {
        initCapabilities();
        WorldGen.initGen();
        anvilRecipes.init();
        NetworkingSetup.registerMessages();
    }

    public static void initCapabilities()
    {
    }

}
