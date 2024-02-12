package net.duckxyz.xyzscythes;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.duckxyz.xyzscythes.entities.ModEntities;
import net.duckxyz.xyzscythes.entities.client.renderers.BloodWaveRenderer;
import net.duckxyz.xyzscythes.entities.client.renderers.SonicWaveRenderer;
import net.duckxyz.xyzscythes.item.ModItems;
import net.duckxyz.xyzscythes.networking.ModMessages;
import net.duckxyz.xyzscythes.particles.ModParticles;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(XYZScythes.MODID)
public class XYZScythes {
    public static final String MODID = "xyz_scythes";

    public XYZScythes() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModEntities.register(modEventBus);
        ModParticles.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::commonSetup);

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.IRON_SCYTHE);
            event.accept(ModItems.NETHER_SCYTHE);
            event.accept(ModItems.SCULK_SCYTHE);
            event.accept(ModItems.BLOOD_SCYTHE);
            event.accept(ModItems.ENDER_SCYTHE);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModMessages.register();
        });
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntities.SONIC_BLAST.get(), SonicWaveRenderer::new);
            EntityRenderers.register(ModEntities.BLOOD_WAVE.get(), BloodWaveRenderer::new);
        }
    }
}
