package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import net.minecraft.world.World;

/** WorldGenerator for wheat crops. */
public class WorldGenWheat extends WorldGenCrop {

    public WorldGenWheat(World world, Random rand) {
        
        super(world, rand, ModBlocks.wheat.getFullgrown(), 10, 7);
    }
}
