package com.jj.jjmod.capabilities;

import com.jj.jjmod.init.ModCapabilities;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ProviderCapDecay implements ICapabilitySerializable {

    private final ICapDecay instance;
    
    public ProviderCapDecay(ICapDecay instance) {
        
        this.instance = instance;
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        
        return capability == ModCapabilities.CAP_DECAY;
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        
        if (capability == ModCapabilities.CAP_DECAY) {
            
            return (T) this.instance;
        }
        
        return null;
    }
    
    @Override
    public NBTBase serializeNBT() {
        
        return this.instance.serializeNBT();
    }
    
    @Override
    public void deserializeNBT(NBTBase nbt) {
        
        this.instance.deserializeNBT((NBTTagCompound) nbt);
    }
}
