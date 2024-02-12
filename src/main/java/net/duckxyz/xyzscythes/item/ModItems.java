package net.duckxyz.xyzscythes.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.duckxyz.xyzscythes.XYZScythes;
import net.duckxyz.xyzscythes.item.custom.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, XYZScythes.MODID);

    public static final RegistryObject<Item> IRON_SCYTHE =
            ITEMS.register("iron_scythe",
                    () -> new BasicScythe(4, -2.8f, 250));

    public static final RegistryObject<Item> NETHER_SCYTHE =
            ITEMS.register("nether_scythe",
                    () -> new NetherScythe(4, -2.8f, 250));

    public static final RegistryObject<Item> SCULK_SCYTHE =
            ITEMS.register("sculk_scythe",
                    () -> new SculkScythe(4, -2.8f, 250));

    public static final RegistryObject<Item> BLOOD_SCYTHE =
            ITEMS.register("blood_scythe",
                    () -> new BloodScythe(4,-2.7f, 250));

    public static final RegistryObject<Item> ENDER_SCYTHE =
            ITEMS.register("ender_scythe",
                    () -> new EnderScythe(4, -2.7f, 250));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
