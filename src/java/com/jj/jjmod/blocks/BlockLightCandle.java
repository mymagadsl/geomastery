package com.jj.jjmod.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockLightCandle extends BlockLight {

    public BlockLightCandle(String name, float extinguish) {
        
        super(name, 10, extinguish);
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        EnumFacing facing = state.getValue(FACING);
        return facing == EnumFacing.UP ? BlockNew.CENTRE_FOUR :
            BlockNew.BLIP[facing.getHorizontalIndex()];
    }
}
