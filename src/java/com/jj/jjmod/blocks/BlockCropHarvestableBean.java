package com.jj.jjmod.blocks;

import com.jj.jjmod.init.ModItems;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomePlains;

public class BlockCropHarvestableBean extends BlockCropHarvestable {
    
    public BlockCropHarvestableBean() {
        
        super("bean", 3,
                () -> ModItems.bean, 4, 0.2F);
        this.growthChance = 0.4F;
    }

    @Override
    public boolean isPermitted(Biome biome) {

        return biome instanceof BiomeForest || biome == Biomes.RIVER ||
                biome instanceof BiomePlains || biome == Biomes.BEACH ||
                biome instanceof BiomeJungle;
    }

}
