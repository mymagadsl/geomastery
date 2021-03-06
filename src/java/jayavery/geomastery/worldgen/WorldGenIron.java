/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.worldgen;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

/** WorldGenerator for Iron Ore. */
public class WorldGenIron extends WorldGenStone {

    public WorldGenIron(World world, Random rand) {

        super(world, rand, Blocks.IRON_ORE.getDefaultState(), 15, 50, 20, 1);
    }

    @Override
    protected int getVeinSize() {

        int rand1 = this.rand.nextInt(3);
        int rand2 = this.rand.nextInt(3);
        int rand3 = this.rand.nextInt(3);

        return rand1 + rand2 + rand3 + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        return true;
    }
}
