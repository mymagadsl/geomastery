/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.worldgen;

import java.util.Random;
import jayavery.geomastery.main.GeoBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

/** WorldGenerator for chickpea crops. */
public class WorldGenChickpea extends WorldGenCrop {

    public WorldGenChickpea(World world, Random rand) {
        
        super(world, rand, GeoBlocks.CHICKPEA.getFullgrown(), 20, 4);
    }
}
