package one.callum.xyzscythes.entities.projectiles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import one.callum.xyzscythes.entities.ModEntities;

import java.util.List;
import java.util.stream.Collectors;

public class BloodWave extends Projectile {
    private int life = 0;
    private List<MobEffect> effects;

    public BloodWave(Level pLevel) {
        this(pLevel, null);
    }

    public BloodWave(Level pLevel, LivingEntity pEntity) {
        super(ModEntities.BLOOD_WAVE.get(), pLevel);
        this.setOwner(pEntity);
        this.effects = pEntity.getActiveEffects().stream()
                .map(e -> e.getEffect()).collect(Collectors.toList());
    }

    public BloodWave(EntityType<BloodWave> bloodWaveEntityType, Level level) {
        super(bloodWaveEntityType, level);
    }

    @Override
    public void tick() {
//        if (life > 10) this.remove(RemovalReason.DISCARDED);
//        else life++;
//
//        Vec3 vec3 = this.getDeltaMovement();
//        double d0 = this.getX() + vec3.x;
//        double d1 = this.getY() + vec3.y;
//        double d2 = this.getZ() + vec3.z;
//        this.setPos(d0, d1, d2);
//        ProjectileUtil.rotateTowardsMovement(this, 0.2F);

        this.level().addParticle(ParticleTypes.CHERRY_LEAVES, this.getX(), this.getY(), this.getZ(), 0, 0, 0);

//        AABB aabb = new AABB(this.blockPosition().above()).inflate(1d, 1d, 1d);
//        this.level().getEntities(this, aabb).forEach(e -> {
//            e.hurt(this.damageSources().sonicBoom(this.getOwner()), 10f);
//        });

        super.tick();
    }

    @Override
    protected void defineSynchedData() {

    }
}
