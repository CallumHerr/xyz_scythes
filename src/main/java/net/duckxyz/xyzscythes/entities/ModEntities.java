package net.duckxyz.xyzscythes.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.duckxyz.xyzscythes.XYZScythes;
import net.duckxyz.xyzscythes.entities.projectiles.BloodWave;
import net.duckxyz.xyzscythes.entities.projectiles.SonicBlast;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, XYZScythes.MODID);

    public static final RegistryObject<EntityType<SonicBlast>> SONIC_BLAST =
            ENTITY_TYPES.register("sonic_blast",
                    () -> EntityType.Builder.<SonicBlast>of(SonicBlast::new, MobCategory.MISC)
                            .sized(1f, 1f).build("sonic_blast"));

    public static final RegistryObject<EntityType<BloodWave>> BLOOD_WAVE =
            ENTITY_TYPES.register("blood_wave",
                    () -> EntityType.Builder.<BloodWave>of(BloodWave::new, MobCategory.MISC)
                            .sized(5f, 1f).build("blood_wave"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
