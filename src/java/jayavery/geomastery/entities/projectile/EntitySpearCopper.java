/*******************************************************************************
 * Copyright (C) 2017 Jay Avery
 * 
 * This file is part of Geomastery. Geomastery is free software: distributed
 * under the GNU Affero General Public License (<http://www.gnu.org/licenses/>).
 ******************************************************************************/
package jayavery.geomastery.entities.projectile;

import jayavery.geomastery.main.GeoItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/** Copper spear projectile entity. */
public class EntitySpearCopper extends EntityProjectile {
    
    /** Damage done by this spear. */
    private static final double DAMAGE = 8D;
    
    /** Durability of this spear's item. */
    private int durability = 0;

    public EntitySpearCopper(World world) {

        super(world, DAMAGE);
    }

    public EntitySpearCopper(World world, EntityLivingBase thrower,
            int durability) {

        super(world, thrower, DAMAGE);
        this.durability = durability;
    }

    public EntitySpearCopper(World world, double x, double y, double z) {

        super(world, x, y, z, DAMAGE);
    }

    @Override
    public ItemStack getArrowStack() {

        if (this.durability + 1 >= GeoItems.SPEAR_COPPER.getMaxDamage()) {
            
            return null;
            
        } else {
            
            return new ItemStack(GeoItems.SPEAR_COPPER, 1, this.durability);
        }
    }
    
    @Override
    public void onHit(RayTraceResult raytraceResultIn) {
        
       super.onHit(raytraceResultIn);
       
       if (!this.world.isRemote && this.isDead) {

           EntitySpearCopper replace = new EntitySpearCopper(this.world,
                   this.posX, this.posY, this.posZ);
           replace.durability = this.durability;
           replace.pickupStatus = PickupStatus.ALLOWED;
           this.world.spawnEntity(replace);
       }
    }
}
