package one.callum.xyzscythes.particles;

import com.mojang.serialization.Decoder;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.entity.ExperienceOrbRenderer;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.ExperienceBottleItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import one.callum.xyzscythes.XYZScythes;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, XYZScythes.MODID);

    public static final RegistryObject<SimpleParticleType> BLOOD_PARTICLE =
            PARTICLES.register("blood",
                    () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }
}
