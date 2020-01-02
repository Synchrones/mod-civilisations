package fr.salsa.CVLST.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityTest extends EntityCow {

    public EntityTest(World worldIn) {
        super(worldIn);
    }
    @Override
    public EntityCow createChild(EntityAgeable ageable){
        return new EntityCow(world);
    }

    @Override
    protected SoundEvent getAmbientSound(){
        return super.getAmbientSound();
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return super.getHurtSound(source);
    }
    @Override
    protected SoundEvent getDeathSound(){
        return super.getDeathSound();
    }
}
