package com.jj.jjmod.blocks;

import java.util.Random;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.main.GuiHandler.GuiList;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.ToolType;
import com.jj.jjmod.main.Main;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCraftingTextiles extends BlockComplexAbstract {

    public static final PropertyEnum<EnumPartTextiles> PART = PropertyEnum
            .<EnumPartTextiles>create("part", EnumPartTextiles.class);
    public static final PropertyDirection FACING =
            PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockCraftingTextiles() {

        super("crafting_textiles", BlockMaterial.WOOD_FURNITURE, 5F, ToolType.NONE);
    }

    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {

        EnumFacing facing = state.getValue(FACING);
        EnumPartTextiles part = state.getValue(PART);

        if (part == EnumPartTextiles.FRONT) {

            boolean brokenBack = world
                    .getBlockState(pos.offset(facing)).getBlock() != this;

            if (brokenBack) {

                world.setBlockToAir(pos);
            }
            
        } else {

            boolean brokenFront = world
                    .getBlockState(pos.offset(facing.getOpposite()))
                    .getBlock() != this;

            if (brokenFront) {

                world.setBlockToAir(pos);
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        return state.getValue(PART).IS_FLAT ? FLAT_BOUNDS : FULL_BLOCK_AABB;
    }

    @Override
    public BlockStateContainer createBlockState() {

        return new BlockStateContainer(this, new IProperty[] {PART, FACING});
    }

    @Override
    public IBlockState getActualState(IBlockState state,
            IBlockAccess world, BlockPos pos) {

        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        int i = 0;

        i = i | state.getValue(FACING).getHorizontalIndex();

        if (state.getValue(PART) == EnumPartTextiles.BACK) {

            i |= 8;
        }

        return i;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        EnumFacing facing = EnumFacing.getHorizontal(meta);
        IBlockState state = this.getDefaultState();

        if ((meta & 8) > 0) {

            state = state.withProperty(PART, EnumPartTextiles.FRONT);
            state = state.withProperty(FACING, facing);

        } else {

            state = state.withProperty(PART, EnumPartTextiles.BACK);
            state = state.withProperty(FACING, facing);
        }

        return state;
    }

    @Override
    public void activate(EntityPlayer player, World world,
            int x, int y, int z) {

        player.openGui(Main.instance, GuiList.TEXTILES.ordinal(),
                world, x, y, z);
    }

    public static enum EnumPartTextiles implements IStringSerializable {

        FRONT("front", true),
        BACK("back", false);

        private final String NAME;
        private final boolean IS_FLAT;

        private EnumPartTextiles(String name, boolean isFlat) {

            this.NAME = name;
            this.IS_FLAT = isFlat;
        }

        public String toString() {

            return this.NAME;
        }

        public String getName() {

            return this.NAME;
        }
    }
}
