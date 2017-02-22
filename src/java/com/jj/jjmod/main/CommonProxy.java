package com.jj.jjmod.main;

import com.jj.jjmod.init.ModBiomes;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.init.ModCaps;
import com.jj.jjmod.init.ModEntities;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.init.ModFluids;
import com.jj.jjmod.init.ModPackets;
import com.jj.jjmod.init.ModRecipes;
import com.jj.jjmod.init.ModTileEntities;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    /** Gui event handler instance. */
    public static final GuiEventHandler GUI =
            new GuiEventHandler();
    /** Block event handler instance. */
    public static final BlockEventHandler BLOCK =
            new BlockEventHandler();
    /** Entity event handler instance. */
    public static final EntityEventHandler ENTITY =
            new EntityEventHandler();
    /** Player event handler instance. */
    public static final PlayerEventHandler PLAYER =
            new PlayerEventHandler();
    /** Capability event handler instance. */
    public static final CapabilityEventHandler CAPABILITY =
            new CapabilityEventHandler();
    /** Worldgenerator instance. */
    public static final IWorldGenerator WORLDGEN = new WorldGenerator();

    public void preInit() {

        ModFluids.preInit();
        ModBlocks.preInit();
        ModItems.preInit();
        ModPackets.preInit();
        ModCaps.preInit();
        ModEntities.preInit();
        
        GameRegistry.registerWorldGenerator(WORLDGEN, 0);
        MinecraftForge.TERRAIN_GEN_BUS.register(WORLDGEN);
        MinecraftForge.EVENT_BUS.register(GUI);
        MinecraftForge.EVENT_BUS.register(BLOCK);
        MinecraftForge.EVENT_BUS.register(ENTITY);
        MinecraftForge.EVENT_BUS.register(PLAYER);
        MinecraftForge.EVENT_BUS.register(CAPABILITY);
    }

    public void init() {

        ModBiomes.init();
        ModRecipes.init();
        ModTileEntities.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance,
                new GuiHandler());
    }

    public void postInit() {

    }
}
