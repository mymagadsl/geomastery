package com.jj.jjmod.worldgen;

import java.util.Random;
import com.jj.jjmod.init.ModBlocks;
import net.minecraft.world.World;

/** WorldGenerator for tin ore. */
public class WorldGenTin extends WorldGenStone {

    public WorldGenTin(World world, Random rand) {

        super(world, rand, ModBlocks.oreTin.getDefaultState(), 30, 80, 5, 1);
    }

    @Override
    protected int getVeinSize() {

        int rand1 = this.rand.nextInt(2);
        int rand2 = this.rand.nextInt(2);

        return rand1 + rand2 + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        return true;
    }
}
