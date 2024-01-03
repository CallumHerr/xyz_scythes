package one.callum.xyzscythes.entities.projectiles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import one.callum.xyzscythes.entities.ModEntities;

public class SonicBlast extends Projectile {
    private int life = 0;

    public SonicBlast(Level pLevel) {
        this(pLevel, null);
    }

    public SonicBlast(Level pLevel, LivingEntity pEntity) {
        super(ModEntities.SONIC_BLAST.get(), pLevel);
        this.setOwner(pEntity);
    }

    public SonicBlast(EntityType<SonicBlast> sonicWaveEntityType, Level level) {
        super(sonicWaveEntityType, level);
    }

    @Override
    public void tick() {
        if (life > 10) this.remove(RemovalReason.DISCARDED);
        else life++;

        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.setPos(d0, d1, d2);
        ProjectileUtil.rotateTowardsMovement(this, 0.2F);

        this.level().addParticle(ParticleTypes.SONIC_BOOM, this.getX(), this.getY(), this.getZ(), 0, 0, 0);

        AABB aabb = new AABB(this.blockPosition().above()).inflate(1d, 1d, 1d);
        this.level().getEntities(this, aabb).forEach(e -> {
            e.hurt(this.damageSources().sonicBoom(this.getOwner()), 10f);
        });

        super.tick();
    }

    @Override
    protected void defineSynchedData() {

    }
}
