package one.callum.xyzscythes.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import one.callum.xyzscythes.XYZScythes;
import one.callum.xyzscythes.item.custom.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, XYZScythes.MODID);

    public static final RegistryObject<Item> IRON_SCYTHE =
            ITEMS.register("iron_scythe",
                    () -> new BasicScythe(3, -2.4f, 250));

    public static final RegistryObject<Item> NETHER_SCYTHE =
            ITEMS.register("nether_scythe",
                    () -> new NetherScythe(3, -2.4f, 250));

    public static final RegistryObject<Item> SCULK_SCYTHE =
            ITEMS.register("sculk_scythe",
                    () -> new SculkScythe(1, 1, 250));

    public static final RegistryObject<Item> BLOOD_SCYTHE =
            ITEMS.register("blood_scythe",
                    () -> new BloodScythe(1,1, 250));

    public static final RegistryObject<Item> ENDER_SCYTHE =
            ITEMS.register("ender_scythe",
                    () -> new EnderScythe(1, 1, 250));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
