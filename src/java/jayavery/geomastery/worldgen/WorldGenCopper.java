/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.world.World;

/** WorldGenerator for copper ore. */
public class WorldGenCopper extends WorldGenStone {

    public WorldGenCopper(World world, Random rand) {

        super(world, rand, GeoBlocks.ORE_COPPER.getDefaultState(),
                40, 120, 20, 1);
    }

    @Override
    protected int getVeinSize() {

        int rand1 = this.rand.nextInt(5);
        int rand2 = this.rand.nextInt(5);

        return rand1 + rand2 + 1;
    }

    @Override
    protected boolean shouldGenBlock() {

        return true;
    }
}
