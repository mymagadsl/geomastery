package jayavery.geomastery.entities.projectile;

import jayavery.geomastery.main.GeoItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySpearBronze extends EntityProjectile {
    
    /** Damager done by this spear. */
    private static final double DAMAGE = 9.33;
    
    /** Durability of this spear's item. */
    private int durability = 0;

    public EntitySpearBronze(World world) {

        super(world, DAMAGE);
    }

    public EntitySpearBronze(World world, EntityLivingBase thrower,
            int durability) {

        super(world, thrower, DAMAGE);
        this.durability = durability;
    }

    public EntitySpearBronze(World world, double x, double y, double z) {

        super(world, x, y, z, DAMAGE);
    }

    @Override
    public ItemStack getArrowStack() {

        if (this.durability >= GeoItems.spearBronze.getMaxDamage()) {
            
            return ItemStack.EMPTY;
            
        } else {

            return new ItemStack(GeoItems.spearBronze, 1, this.durability);
        }
    }
    
    @Override
    public void onHit(RayTraceResult raytrace) {
        
       super.onHit(raytrace);
       
       if (this.isDead) {

           EntitySpearBronze replace = new EntitySpearBronze(this.world,
                   this.posX, this.posY, this.posZ);
           replace.durability = this.durability;
           replace.pickupStatus = PickupStatus.ALLOWED;
           this.world.spawnEntity(replace);
       }
    }
}