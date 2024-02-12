package net.duckxyz.xyzscythes.events;

import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.duckxyz.xyzscythes.XYZScythes;
import net.duckxyz.xyzscythes.particles.ModParticles;
import net.duckxyz.xyzscythes.particles.custom.BloodParticles;

@Mod.EventBusSubscriber(modid = XYZScythes.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.BLOOD_PARTICLE.get(), BloodParticles.Provider::new);
    }
}
