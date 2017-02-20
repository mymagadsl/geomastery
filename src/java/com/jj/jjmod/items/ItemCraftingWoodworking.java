package com.jj.jjmod.items;

import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.container.ContainerInventory;
import com.jj.jjmod.tileentities.TECraftingWoodworking;
import com.jj.jjmod.tileentities.TECraftingWoodworking.EnumPartWoodworking;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/** Woodworking crafting device item. */
public class ItemCraftingWoodworking extends ItemJj {

    public ItemCraftingWoodworking() {

        super("crafting_woodworking", 1, CreativeTabs.DECORATIONS);
    }

    /** Attempts to build woodworking crafting device structure. */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player,
            World world, BlockPos pos, EnumHand hand, EnumFacing side,
            float x, float y, float z) {
        
        if (world.isRemote) {

            return EnumActionResult.SUCCESS;
        }
        
        ItemStack stack = player.getHeldItem(hand);

        // Calculate positions
        BlockPos posFM = pos.offset(side);
        int intFacing = MathHelper.floor((player.rotationYaw * 4.0F /
                360.0F) + 0.5D) & 3;
        EnumFacing enumFacing = EnumFacing.getHorizontal(intFacing);
        BlockPos posFL = posFM.offset(enumFacing.rotateY().getOpposite());
        BlockPos posBL = posFL.offset(enumFacing);
        BlockPos posBM = posFM.offset(enumFacing);
        BlockPos posBR = posBM.offset(enumFacing.rotateY());
        BlockPos posFR = posBR.offset(enumFacing.getOpposite());

        // Check replaceable
        IBlockState stateFM = world.getBlockState(posFM);
        Block blockFM = stateFM.getBlock();
        boolean replaceableFM = blockFM.isReplaceable(world, posFM);

        IBlockState stateFL = world.getBlockState(posFL);
        Block blockFL = stateFL.getBlock();
        boolean replaceableFL = blockFL.isReplaceable(world, posFL);

        IBlockState stateBL = world.getBlockState(posBL);
        Block blockBL = stateBL.getBlock();
        boolean replaceableBL = blockBL.isReplaceable(world, posBL);

        IBlockState stateBM = world.getBlockState(posBM);
        Block blockBM = stateBM.getBlock();
        boolean replaceableBM = blockBM.isReplaceable(world, posBM);

        IBlockState stateBR = world.getBlockState(posBR);
        Block blockBR = stateBR.getBlock();
        boolean replaceableBR = blockBR.isReplaceable(world, posBR);

        IBlockState stateFR = world.getBlockState(posFR);
        Block blockFR = stateFR.getBlock();
        boolean replaceableFR = blockFR.isReplaceable(world, posFR);

        if (!replaceableFM || !replaceableFL || !replaceableBL ||
                !replaceableBM || !replaceableBR || !replaceableFR) {

            return EnumActionResult.FAIL;
        }

        // Place all
        IBlockState placeState =
                ModBlocks.craftingWoodworking.getDefaultState();

        world.setBlockState(posFM, placeState);
        world.setBlockState(posFL, placeState);
        world.setBlockState(posBL, placeState);
        world.setBlockState(posBM, placeState);
        world.setBlockState(posBR, placeState);
        world.setBlockState(posFR, placeState);

        // Set up tileentities
        ((TECraftingWoodworking) world.getTileEntity(posFM))
                .setState(enumFacing, EnumPartWoodworking.FM);
        ((TECraftingWoodworking) world.getTileEntity(posFL))
                .setState(enumFacing, EnumPartWoodworking.FL);
        ((TECraftingWoodworking) world.getTileEntity(posBL))
                .setState(enumFacing, EnumPartWoodworking.BL);
        ((TECraftingWoodworking) world.getTileEntity(posBM))
                .setState(enumFacing, EnumPartWoodworking.BM);
        ((TECraftingWoodworking) world.getTileEntity(posBR))
                .setState(enumFacing, EnumPartWoodworking.BR);
        ((TECraftingWoodworking) world.getTileEntity(posFR))
                .setState(enumFacing, EnumPartWoodworking.FR);

        // Use item
        world.playSound(null, posFM, SoundType.METAL.getPlaceSound(),
                SoundCategory.BLOCKS,
                SoundType.METAL.getVolume() + 1.0F / 2.0F,
                SoundType.METAL.getPitch() * 0.8F);

        if (!player.capabilities.isCreativeMode) {
            
            stack.shrink(1);
            ContainerInventory.updateHand(player, hand);
        }
        
        return EnumActionResult.SUCCESS;
    }
}