package one.callum.xyzscythes.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import one.callum.xyzscythes.XYZScythes;
import one.callum.xyzscythes.entities.projectiles.BloodWave;
import one.callum.xyzscythes.entities.projectiles.SonicBlast;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, XYZScythes.MODID);

    public static final RegistryObject<EntityType<SonicBlast>> SONIC_BLAST =
            ENTITY_TYPES.register("sonic_blast",
                    () -> EntityType.Builder.<SonicBlast>of(SonicBlast::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f).build("sonic_blast"));

    public static final RegistryObject<EntityType<BloodWave>> BLOOD_WAVE =
            ENTITY_TYPES.register("blood_wave",
                    () -> EntityType.Builder.<BloodWave>of(BloodWave::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f).build("blood_wave"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
