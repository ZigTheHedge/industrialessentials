package com.cwelth.industrialessentials.inits;

import com.cwelth.industrialessentials.blocks.Anvil;
import com.cwelth.industrialessentials.blocks.CoalGenerator;
import com.cwelth.industrialessentials.blocks.IEBlockOre;
import com.cwelth.industrialessentials.blocks.ModelledBlock;
import com.cwelth.industrialessentials.items.Hammer;
import com.cwelth.industrialessentials.items.IEIngot;
import com.cwelth.industrialessentials.items.IEOreDust;
import com.cwelth.industrialessentials.items.IEPart;
import com.cwelth.industrialessentials.tileentities.AnvilTE;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.cwelth.industrialessentials.IndustrialEssentials.MODID;

public class IEContent {
    private static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<TileEntityType<?>> TILEENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, MODID);

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILEENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //Blocks and BlockItems
    //Ores
    public static final RegistryObject<IEBlockOre> SILVER_ORE = BLOCKS.register("silver_ore", () -> new IEBlockOre(2, 4F));
    public static final RegistryObject<Item> SILVER_ORE_ITEM = ITEMS.register("silver_ore", () -> new BlockItem(SILVER_ORE.get(), new Item.Properties().group(InitCommon.creativeTab)));
    public static final RegistryObject<IEBlockOre> LEAD_ORE = BLOCKS.register("lead_ore", () -> new IEBlockOre(2, 4F));
    public static final RegistryObject<Item> LEAD_ORE_ITEM = ITEMS.register("lead_ore", () -> new BlockItem(LEAD_ORE.get(), new Item.Properties().group(InitCommon.creativeTab)));
    public static final RegistryObject<IEBlockOre> COPPER_ORE = BLOCKS.register("copper_ore", () -> new IEBlockOre(1, 2F));
    public static final RegistryObject<Item> COPPER_ORE_ITEM = ITEMS.register("copper_ore", () -> new BlockItem(COPPER_ORE.get(), new Item.Properties().group(InitCommon.creativeTab)));
    public static final RegistryObject<IEBlockOre> TIN_ORE = BLOCKS.register("tin_ore", () -> new IEBlockOre(1, 2F));
    public static final RegistryObject<Item> TIN_ORE_ITEM = ITEMS.register("tin_ore", () -> new BlockItem(TIN_ORE.get(), new Item.Properties().group(InitCommon.creativeTab)));

    //Machines
    public static final RegistryObject<CoalGenerator> COAL_GENERATOR = BLOCKS.register("coal_generator", CoalGenerator::new);
    public static final RegistryObject<Item> COAL_GENERATOR_ITEM = ITEMS.register("coal_generator", () -> new BlockItem(COAL_GENERATOR.get(), new Item.Properties().group(InitCommon.creativeTab)));
    public static final RegistryObject<Anvil> ANVIL = BLOCKS.register("anvil", Anvil::new);
    public static final RegistryObject<Item> ANVIL_ITEM = ITEMS.register("anvil", () -> new BlockItem(ANVIL.get(), new Item.Properties().group(InitCommon.creativeTab)));

    //TileEntities
    public static final RegistryObject<TileEntityType<AnvilTE>> ANVIL_TE = TILEENTITIES.register("anvilte", () -> TileEntityType.Builder.create(AnvilTE::new, ANVIL.get()).build(null));

    //Items - dusts
    public static final RegistryObject<IEOreDust> IRON_ORE_DUST = ITEMS.register("iron_ore_dust", IEOreDust::new);
    public static final RegistryObject<IEOreDust> GOLD_ORE_DUST = ITEMS.register("gold_ore_dust", IEOreDust::new);
    public static final RegistryObject<IEOreDust> SILVER_ORE_DUST = ITEMS.register("silver_ore_dust", IEOreDust::new);
    public static final RegistryObject<IEOreDust> LEAD_ORE_DUST = ITEMS.register("lead_ore_dust", IEOreDust::new);
    public static final RegistryObject<IEOreDust> COPPER_ORE_DUST = ITEMS.register("copper_ore_dust", IEOreDust::new);
    public static final RegistryObject<IEOreDust> TIN_ORE_DUST = ITEMS.register("tin_ore_dust", IEOreDust::new);
    public static final RegistryObject<IEOreDust> BRONZE_ORE_DUST = ITEMS.register("bronze_ore_dust", IEOreDust::new);
    public static final RegistryObject<IEOreDust> ELECTRUM_ORE_DUST = ITEMS.register("electrum_ore_dust", IEOreDust::new);

    //Items - ingots
    public static final RegistryObject<IEIngot> SILVER_INGOT = ITEMS.register("silver_ingot", IEIngot::new);
    public static final RegistryObject<IEIngot> LEAD_INGOT = ITEMS.register("lead_ingot", IEIngot::new);
    public static final RegistryObject<IEIngot> COPPER_INGOT = ITEMS.register("copper_ingot", IEIngot::new);
    public static final RegistryObject<IEIngot> TIN_INGOT = ITEMS.register("tin_ingot", IEIngot::new);
    public static final RegistryObject<IEIngot> BRONZE_INGOT = ITEMS.register("bronze_ingot", IEIngot::new);
    public static final RegistryObject<IEIngot> ELECTRUM_INGOT = ITEMS.register("electrum_ingot", IEIngot::new);

    //Items - machine parts
    public static final RegistryObject<IEPart> ENERGY_CONNECTOR_PART = ITEMS.register("energy_connector_part", IEPart::new);
    public static final RegistryObject<IEPart> CHASSIS = ITEMS.register("chassis", IEPart::new);
    public static final RegistryObject<IEPart> HEAT_SINK = ITEMS.register("heat_sink", IEPart::new);
    public static final RegistryObject<IEPart> BOILER_PART = ITEMS.register("boiler_part", IEPart::new);
    public static final RegistryObject<IEPart> GENERATOR_PART = ITEMS.register("generator_part", IEPart::new);
    public static final RegistryObject<IEPart> REDSTONE_PART = ITEMS.register("redstone_part", IEPart::new);
    public static final RegistryObject<Hammer> HAMMER_PART = ITEMS.register("hammer_part", () -> new Hammer(ItemTier.IRON));
    public static final RegistryObject<Hammer> HAMMER_DIAMOND_PART = ITEMS.register("hammer_diamond_part", () -> new Hammer(ItemTier.DIAMOND));

}
