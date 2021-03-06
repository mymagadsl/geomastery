/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.worldgen;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

/** WorldGenerator for Diamond Lode. */
public class WorldGenDiamond extends WorldGenStone {

    public WorldGenDiamond(World world, Random rand) {

        super(world, rand, Blocks.DIAMOND_ORE.getDefaultState(), 0, 15, 1, 1);
    }

    @Override
    protected int getVeinSize() {

        return this.rand.nextInt(79) + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        if (this.rand.nextFloat() < 0.05) {

            return true;
        }

        return false;
    }
}
