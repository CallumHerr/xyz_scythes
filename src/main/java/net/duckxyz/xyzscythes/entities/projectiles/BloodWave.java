package net.duckxyz.xyzscythes.entities.projectiles;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.duckxyz.xyzscythes.entities.ModEntities;
import net.duckxyz.xyzscythes.particles.ModParticles;

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
        if (life > 10) this.remove(RemovalReason.DISCARDED);
        else life++;

        //Movement
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.setPos(d0, d1, d2);
        ProjectileUtil.rotateTowardsMovement(this, 0.2F);

        spawnParticles();

        //Damage
        for (LivingEntity entity : level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox())) {
            if (this.getOwner() != null && entity.is(this.getOwner())) continue;
            entity.hurt(damageSources().magic(), 5f);

            if (effects == null || effects.size() < 1) return;
            for (MobEffect effect : effects) {
                entity.addEffect(new MobEffectInstance(effect, 100));
            }
        }

        super.tick();
    }

    private void spawnParticles() {
        float yRot = Math.abs(this.getYRot());
        //Particles along the x axis
        if (yRot < 22.5f || yRot > 157.5f) {
            double z;
            if (yRot < 22.5f) z = this.getBoundingBox().maxZ;
            else z = this.getBoundingBox().minZ;

            this.level().addParticle(ModParticles.BLOOD_PARTICLE.get(),
                    this.getX(), this.getY(), z, 0, 0, 0);

            for (int i = 1; i < 3; i++) {
                this.level().addParticle(ModParticles.BLOOD_PARTICLE.get(),
                        this.getX() - i, this.getY(), z, 0, 0, 0);
                this.level().addParticle(ModParticles.BLOOD_PARTICLE.get(),
                        this.getX() + i, this.getY(), z, 0, 0, 0);
            }
            //Diagonal particles
        } else if (yRot < 67.5f || yRot > 123.5f) {
            this.level().addParticle(ModParticles.BLOOD_PARTICLE.get(),
                    this.getX(), this.getY(), this.getZ(), 0, 0, 0);

            if (this.getYRot() < 90 && this.getYRot() > 0 || this.getYRot() < -90) {
                for (int i = 1; i < 3; i++) {
                    this.level().addParticle(ModParticles.BLOOD_PARTICLE.get(),
                            this.getX() - i, this.getY(), this.getZ() - i, 0, 0 ,0);
                    this.level().addParticle(ModParticles.BLOOD_PARTICLE.get(),
                            this.getX() + i, this.getY(), this.getZ() + i, 0, 0 ,0);
                }
            } else {
                for (int i = 1; i < 3; i++) {
                    this.level().addParticle(ModParticles.BLOOD_PARTICLE.get(),
                            this.getX() + i, this.getY(), this.getZ() - i, 0, 0 ,0);
                    this.level().addParticle(ModParticles.BLOOD_PARTICLE.get(),
                            this.getX() - i, this.getY(), this.getZ() + i, 0, 0 ,0);
                }
            }

            //Particles along z axis
        } else {
            double x;
            if (this.getYRot() < 0) x = this.getBoundingBox().minX;
            else x = this.getBoundingBox().maxX;

            this.level().addParticle(ModParticles.BLOOD_PARTICLE.get(),
                    x, this.getY(), this.getZ(), 0, 0, 0);

            for (int i = 1; i < 3; i++) {
                this.level().addParticle(ModParticles.BLOOD_PARTICLE.get(),
                        x, this.getY(), this.getZ() + i, 0, 0, 0);
                this.level().addParticle(ModParticles.BLOOD_PARTICLE.get(),
                        x, this.getY(), this.getZ() - i, 0, 0, 0);
            }
        }
    }

    @Override
    protected void defineSynchedData() {
    }
}
