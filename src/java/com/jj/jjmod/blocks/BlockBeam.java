package com.jj.jjmod.blocks;

import java.util.List;
import javax.annotation.Nullable;
import com.jj.jjmod.blocks.BlockBeam.EnumAxis;
import com.jj.jjmod.init.ModBlocks;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.tileentities.TEBeam;
import com.jj.jjmod.tileentities.TEBeam.EnumFloor;
import com.jj.jjmod.tileentities.TEBeam.EnumPartBeam;
import com.jj.jjmod.utilities.BlockMaterial;
import com.jj.jjmod.utilities.IBuildingBlock;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Beam block. */
public class BlockBeam extends BlockComplexAbstract {

    public static final PropertyEnum<EnumAxis> AXIS =
            PropertyEnum.<EnumAxis>create("axis", EnumAxis.class);
    public static final PropertyEnum<EnumFloor> FLOOR =
            PropertyEnum.<TEBeam.EnumFloor>
            create("floor", TEBeam.EnumFloor.class);
    public static final PropertyBool NORTH_SIDE =
            PropertyBool.create("northside");
    public static final PropertyBool EAST_SIDE =
            PropertyBool.create("eastside");
    public static final PropertyBool SOUTH_SIDE =
            PropertyBool.create("southside");
    public static final PropertyBool WEST_SIDE =
            PropertyBool.create("westside");
    public static final PropertyBool NORTH_END =
            PropertyBool.create("northend");
    public static final PropertyBool EAST_END = PropertyBool.create("eastend");
    public static final PropertyBool SOUTH_END =
            PropertyBool.create("southend");
    public static final PropertyBool WEST_END = PropertyBool.create("westend");
        
    public BlockBeam() {
        
        super("beam", BlockMaterial.WOOD_FURNITURE, 2F, ToolType.AXE);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        
        return new TEBeam();
    }
    
    @Override
    public boolean hasTileEntity() {
        
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos,
            Block block, BlockPos unused) {
        
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (!(tileEntity instanceof TEBeam)) {
            
            return;
        }
        
        TEBeam tileBeam = (TEBeam) tileEntity;
        EnumPartBeam part = tileBeam.getPart();
        EnumFacing facing = tileBeam.getFacing();
        
        boolean destroy = false;
        
        switch (part) {
            
            case FRONT: {
                
                BlockPos posFront = pos.offset(facing);
                IBlockState stateFront = world.getBlockState(posFront);
                Block blockFront = stateFront.getBlock();
                boolean validFront = ModBlocks.HEAVY.contains(blockFront);
                
                if (blockFront instanceof IBuildingBlock) {
                    
                    IBuildingBlock buildingFront = (IBuildingBlock) blockFront;
                    validFront = buildingFront.supportsBeam();
                }
                
                BlockPos posBack = pos.offset(facing.getOpposite());
                IBlockState stateBack = world.getBlockState(posBack);
                Block blockBack = stateBack.getBlock();
                TileEntity tileBack = world.getTileEntity(posBack);
                boolean validBack = (blockBack == this) &&
                        (tileBack instanceof TEBeam) &&
                        (((TEBeam) tileBack).getFacing() == facing);  
                
                if (!validBack || !validFront) {
                    
                    destroy = true;
                }
                
                break;
            }
            
            case MIDDLE: {
                
                BlockPos posFront = pos.offset(facing);
                IBlockState stateFront = world.getBlockState(posFront);
                Block blockFront = stateFront.getBlock();
                TileEntity tileFront = world.getTileEntity(posFront);
                boolean validFront = (blockFront == this) &&
                        (tileFront instanceof TEBeam) &&
                        (((TEBeam) tileFront).getFacing() == facing);   
                
                BlockPos posBack = pos.offset(facing.getOpposite());
                IBlockState stateBack = world.getBlockState(posBack);
                Block blockBack = stateBack.getBlock();
                TileEntity tileBack = world.getTileEntity(posBack);
                boolean validBack = (blockBack == this) &&
                        (tileBack instanceof TEBeam) &&
                        (((TEBeam) tileBack).getFacing() == facing);   
                
                if (!validBack || !validFront) {
                    
                    destroy = true;
                }
                
                break;
            }
            
            case BACK: {
                
                BlockPos posFront = pos.offset(facing);
                IBlockState stateFront = world.getBlockState(posFront);
                Block blockFront = stateFront.getBlock();
                TileEntity tileFront = world.getTileEntity(posFront);
                boolean validFront = (blockFront == this) &&
                        (tileFront instanceof TEBeam) &&
                        (((TEBeam) tileFront).getFacing() == facing);  
                
                BlockPos posBack = pos.offset(facing.getOpposite());
                IBlockState stateBack = world.getBlockState(posBack);
                Block blockBack = stateBack.getBlock();
                boolean validBack = ModBlocks.HEAVY.contains(blockBack);
                
                if (blockBack instanceof IBuildingBlock) {
                    
                    IBuildingBlock buildingBack = (IBuildingBlock) blockBack;
                    validBack = buildingBack.supportsBeam();
                }
                
                if (!validBack || !validFront) {
                    
                    destroy = true;
                }
                
                break;
            }
        }
        
        if (destroy) {
            
            world.setBlockToAir(pos);
            
            if (part.shouldDrop()) {

                spawnAsEntity(world, pos, new ItemStack(tileBeam.getItem()));
            }
        }
    }
    
    @Override
    public boolean removedByPlayer(IBlockState state, World world,
            BlockPos pos, EntityPlayer player, boolean willHarvest) {
        
        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (!(tileEntity instanceof TEBeam)) {
            
            world.setBlockToAir(pos);
            return true;
        }
        
        TEBeam tileBeam = (TEBeam) tileEntity;
        EnumFloor floor = tileBeam.getFloor();
        
        // Drops floor and leaves beam behind
        switch (floor) {
            
            case NONE : {
             
                world.setBlockToAir(pos);
                
                if (tileBeam.getPart().shouldDrop()) {

                    spawnAsEntity(world, pos, new ItemStack(tileBeam.getItem()));
                }
                
                return true;
            }
            
            case POLE : {

                spawnAsEntity(world, pos, new ItemStack(EnumFloor.POLE.getItem()));
                tileBeam.removeFloor();
                return false;
            }
            
            case WOOD : {

                spawnAsEntity(world, pos, new ItemStack(EnumFloor.WOOD.getItem()));
                tileBeam.removeFloor();
                return false;
            }
        }
        
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world,
            BlockPos pos) {

        state = this.getActualState(state, world, pos);
        EnumFloor floor = state.getValue(FLOOR);
        
        if (floor != EnumFloor.NONE) {
            
            return TOP_HALF;
        }
        
        return BEAM[state.getValue(AXIS).ordinal()];
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list, @Nullable Entity entity, boolean unused) {
        
        state = this.getActualState(state, world, pos);
        
        addCollisionBoxToList(pos, entityBox, list, BEAM[state.getValue(AXIS).ordinal()]);
        
        if (state.getValue(FLOOR) != EnumFloor.NONE) {
            
            addCollisionBoxToList(pos, entityBox, list, BEAM_FLOOR);
        }
    }

    @Override
    public BlockStateContainer createBlockState() {

        return new BlockStateContainer(this, new IProperty[] {AXIS, FLOOR,
                NORTH_SIDE, EAST_SIDE, SOUTH_SIDE, WEST_SIDE, NORTH_END,
                EAST_END, SOUTH_END, WEST_END});
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world,
            BlockPos pos) {

        TileEntity tileEntity = world.getTileEntity(pos);
        
        if (tileEntity instanceof TEBeam) {
            
            TEBeam tileBeam = (TEBeam) tileEntity;
            
            state = tileBeam.getFloor() == null ? state : state.withProperty(FLOOR, tileBeam.getFloor());
            state = tileBeam.getFacing() == null ? state : state.withProperty(AXIS,
                    EnumAxis.get(tileBeam.getFacing()));
            state = state.withProperty(NORTH_SIDE,
                    tileBeam.hasSideConnection(EnumFacing.NORTH)); 
            state = state.withProperty(EAST_SIDE,
                    tileBeam.hasSideConnection(EnumFacing.EAST));
            state = state.withProperty(SOUTH_SIDE,
                    tileBeam.hasSideConnection(EnumFacing.SOUTH));
            state = state.withProperty(WEST_SIDE,
                    tileBeam.hasSideConnection(EnumFacing.WEST));
            state = state.withProperty(NORTH_END,
                    tileBeam.hasEndConnection(EnumFacing.NORTH));
            state = state.withProperty(EAST_END,
                    tileBeam.hasEndConnection(EnumFacing.EAST));
            state = state.withProperty(SOUTH_END,
                    tileBeam.hasEndConnection(EnumFacing.SOUTH));
            state = state.withProperty(WEST_END,
                    tileBeam.hasEndConnection(EnumFacing.WEST));
        }
        
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        return this.getDefaultState();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos,
            IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing side, float x, float y, float z) {
        
        return false;
    }
    
    @Override
    public void activate(EntityPlayer player, World world, int x, int y,
            int z) {}
    
    /** Enum defining which axis the Beam structure is aligned on. */
    public enum EnumAxis implements IStringSerializable {
        
        NS("ns"), EW("ew");
        
        private String name;
        
        private EnumAxis(String name) {
            
            this.name = name;
        }
        
        @Override
        public String getName() {
            
            return this.name;
        }
        
        /** @return The EnumAxis associated with the given direction. */
        public static EnumAxis get(EnumFacing facing) {
            
            if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
                
                return NS;
                
            } else {
                
                return EW;
            }
        }
        
        /** @return Whether the given direction is
         * aligned with this EnumAxis. */
        public boolean matches(EnumFacing facing) {
            
            switch (this) {
                
                case NS: {
                    
                    return facing == EnumFacing.NORTH ||
                            facing == EnumFacing.SOUTH;
                }
                
                case EW: {
                    
                    return facing == EnumFacing.EAST ||
                            facing == EnumFacing.WEST;
                }
                
                default: {
                    
                    return false;
                }
            }
        }
    }
}
