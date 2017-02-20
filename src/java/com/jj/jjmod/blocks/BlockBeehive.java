package com.jj.jjmod.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import com.jj.jjmod.init.ModItems;
import com.jj.jjmod.utilities.IBiomeCheck;
import com.jj.jjmod.utilities.ToolType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeMushroomIsland;
import net.minecraft.world.biome.BiomeOcean;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.biome.BiomeSavanna;
import net.minecraft.world.biome.BiomeSwamp;

public class BlockBeehive extends BlockNew implements IBiomeCheck {
    
    public static final PropertyInteger AGE =
            PropertyInteger.create("age", 0, 3);
    public static final PropertyDirection FACING =
            PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    
    private static final String NAME = "beehive";

    /** Chance of growth per update tick. */
    private float chance = 0.2F;
    
    public BlockBeehive() {
        
        super(Material.PLANTS, NAME, null, 5.0F, null);
        this.setTickRandomly(true);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(AGE, 0).withProperty(FACING, EnumFacing.NORTH));
        
    }
    
    /** @return Whether this Beehive can be planted at this position. */
    public boolean canPlant(World world, BlockPos pos) {
        
        boolean result = false;
        
        for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL) {
            
            IBlockState check = this.getDefaultState()
                    .withProperty(FACING, facing);
            result = result ? result : this.canStay(world, pos, check);
        }
        
        return result;
    }
    
    @Override
    public void updateTick(World world, BlockPos pos,
            IBlockState state, Random rand) {
        
        if (!this.canStay(world, pos, state)) {
            
            world.setBlockToAir(pos);
            return;
        }
        
        if (rand.nextFloat() <= this.chance) {
            
            int oldAge = state.getValue(AGE);
            int newAge = (oldAge + 1) > 3 ? 3 : (oldAge + 1);
            IBlockState newState = state.withProperty(AGE, newAge);
            world.setBlockState(pos, newState);
        }
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world,
            BlockPos pos, Block block, BlockPos unused) {
        
        if (!this.canStay(world, pos, state)) {
            
            world.setBlockToAir(pos);
        }
    }
    
    /** @return Whether this Beehive can stay at this position. */
    public boolean canStay(World world,
            BlockPos pos, IBlockState state) {
        
        Block side = world.getBlockState(pos.offset(state.getValue(FACING)))
                .getBlock();
        Biome biome = world.getBiome(pos);
        return (side instanceof BlockLog || side instanceof BlockWood) &&
                this.isPermitted(biome);
    }
    
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos,
            EnumFacing placeFacing, float x, float y, float z,
            int meta, EntityLivingBase placer) {
        
        IBlockState check = this.getDefaultState();

        if (placeFacing.getAxis().isHorizontal()) {
            
            check = check.withProperty(FACING, placeFacing.getOpposite());
            
            if (this.canStay(world, pos, check)) {
                
                return check;
            }
        }
        
        EnumFacing placerFacing = EnumFacing.fromAngle(placer.rotationYaw);

        if (!placerFacing.getAxis().isHorizontal()) {
            
            int i = MathHelper.floor(placer.rotationYaw *
                    4.0F / 360.0F + 0.5D) & 3;
            placerFacing = EnumFacing.getHorizontal(i);
        }

        check = check.withProperty(FACING, placerFacing);

        if (this.canStay(world, pos, check)) {
            
            return check;
        }
        
        for (EnumFacing checkFacing : EnumFacing.Plane.HORIZONTAL) {
            
            check = check.withProperty(FACING, checkFacing);
            
            if (this.canStay(world, pos, check)) {

                return check;
            }
        }
        
        return check;
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        
        return new BlockStateContainer(this, new IProperty[] {AGE, FACING});
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
        
        IBlockState result = this.getDefaultState();
        result = result.withProperty(AGE, meta % 4);
        result = result.withProperty(FACING,
                EnumFacing.getHorizontal(meta / 4));
        return result;
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        
        int meta = 0;
        meta = meta + (4 * (state.getValue(FACING).getHorizontalIndex()));
        meta = meta + (state.getValue(AGE));
        return meta;
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos,
            IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing side, float x, float y, float z) {
        
        if (!this.canStay(world, pos, state)) {
            
            world.setBlockToAir(pos);
            return false;
        }
        
        int age = state.getValue(AGE);
        
        if (age != 3) {
            
            return false;
            
        } else {
            
            IBlockState newState = state.withProperty(AGE, 0);
            world.setBlockState(pos, newState);
            spawnAsEntity(world, pos, new ItemStack(ModItems.honey, 4));
            spawnAsEntity(world, pos, new ItemStack(ModItems.beeswax));
            
            return true;
        }
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos,
            IBlockState state, int fortune) {
        
        List<ItemStack> items = new ArrayList<ItemStack>();
        
        if (state.getValue(AGE) == 3) {
            
            items.add(new ItemStack(ModItems.beeswax));
            items.add(new ItemStack(ModItems.honey, 4));
        }
        
        return items;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state,
            IBlockAccess world, BlockPos pos) {
        
        return BLIP[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    public boolean isPermitted(Biome biome) {

        return biome instanceof BiomeForest || biome instanceof BiomeOcean ||
                biome == Biomes.RIVER || biome instanceof BiomePlains ||
                biome == Biomes.BEACH || biome instanceof BiomeSwamp ||
                biome instanceof BiomeMushroomIsland ||
                biome instanceof BiomeJungle || biome instanceof BiomeSavanna;
    }
}