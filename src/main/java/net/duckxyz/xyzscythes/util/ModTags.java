package net.duckxyz.xyzscythes.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.duckxyz.xyzscythes.XYZScythes;

public class ModTags {
    public static class BLOCKS {
        public static final TagKey<Block> foliage = BlockTags.create(
                new ResourceLocation(XYZScythes.MODID, "foliage"));
    }

    public static class ITEMS {
        public static final TagKey<Item> scythes = ItemTags.create(
                new ResourceLocation(XYZScythes.MODID, "scythes"));
    }
}
