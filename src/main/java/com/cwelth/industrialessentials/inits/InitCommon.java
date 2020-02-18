package com.cwelth.industrialessentials.inits;

import com.cwelth.industrialessentials.IndustrialEssentials;
import com.cwelth.industrialessentials.items.Hammer;
import com.cwelth.industrialessentials.networking.AnvilHoverSync;
import com.cwelth.industrialessentials.networking.NetworkingSetup;
import com.cwelth.industrialessentials.recipes.AnvilRecipes;
import com.cwelth.industrialessentials.tileentities.AnvilTE;
import com.cwelth.industrialessentials.worldgen.WorldGen;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.DrawHighlightEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = IndustrialEssentials.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InitCommon {
    public static AnvilRecipes anvilRecipes = new AnvilRecipes();
    public static AnvilTE lastTE = null;

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

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void drawProgressBar(DrawHighlightEvent event)
    {
        Minecraft mc = Minecraft.getInstance();
        BlockRayTraceResult result = (BlockRayTraceResult) event.getTarget();
        if(mc.world.getBlockState(result.getPos()).getBlock() == IEContent.ANVIL.get())
        {
            if(lastTE == null) {
                AnvilTE te = (AnvilTE) mc.world.getTileEntity(result.getPos());
                if (te != null)
                {
                    lastTE = te;
                    te.clientOnLookAt = true;
                    NetworkingSetup.INSTANCE.sendToServer(new AnvilHoverSync(true, te.getPos(), te.getWorld().getDimension().getType()));
                }
            } else
            {
                AnvilTE te = (AnvilTE) mc.world.getTileEntity(result.getPos());
                if (te != null)
                {
                    if(lastTE.getPos() != te.getPos())
                    {
                        NetworkingSetup.INSTANCE.sendToServer(new AnvilHoverSync(false, lastTE.getPos(), lastTE.getWorld().getDimension().getType()));
                        lastTE.clientOnLookAt = false;
                        lastTE = te;
                        te.clientOnLookAt = true;
                        NetworkingSetup.INSTANCE.sendToServer(new AnvilHoverSync(true, te.getPos(), te.getWorld().getDimension().getType()));
                    }
                }
            }
        } else
        {
            if(lastTE != null)
            {
                NetworkingSetup.INSTANCE.sendToServer(new AnvilHoverSync(false, lastTE.getPos(), lastTE.getWorld().getDimension().getType()));
                lastTE.clientOnLookAt = false;
                lastTE = null;
            }
        }
    }

}
