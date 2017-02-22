package com.jj.jjmod.tileentities;

import com.jj.jjmod.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

/** TileEntity for carcass blocks. */
public class TECarcass extends TileEntity {
    
    /** Length of an age stage in ticks. */
    private int stageSize;
    /** Birth time of this carcass. */
    private long birthTime;
    
    public void setData(long birthTime, int stageSize) {
        
        this.birthTime = birthTime;
        this.stageSize = stageSize;
    }
    
    /** @return The birth time of this carcass. */
    public long getBirthTime() {
        
        return this.birthTime;
    }
    
    /** @return The age stage size of this carcass. */
    public int getStageSize() {
        
        return this.stageSize;
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {

        super.writeToNBT(nbt);
        nbt.setLong("birthTime", this.birthTime);
        nbt.setInteger("stageSize", this.stageSize);
        return nbt;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt) {

        super.readFromNBT(nbt);
        this.birthTime = nbt.getLong("birthTime");
        this.stageSize = nbt.getInteger("stageSize");
    }
}
