package one.callum.xyzscythes.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.SweepingEdgeEnchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import one.callum.xyzscythes.util.ModTags;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BasicScythe extends ScytheItem {
    public BasicScythe(int pAttackDamageModifier, float pAttackSpeedModifier, int durability) {
        super(Tiers.IRON, pAttackDamageModifier, pAttackSpeedModifier,
                new Properties().durability(durability));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (level.isClientSide) return super.useOn(pContext);
        BlockPos pPos = pContext.getClickedPos();

        if (!level.getBlockState(pContext.getClickedPos()).is(ModTags.BLOCKS.foliage)) return super.useOn(pContext);

        int yOffset = 0;
        if (level.getBlockState(pPos).is(BlockTags.DIRT)) yOffset = 1;

        for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <=3; z++) {
                BlockPos pos = pPos.offset(x, yOffset, z);
                if (level.getBlockState(pos).is(ModTags.BLOCKS.foliage)) {
                    level.destroyBlock(pos, true);
                }
            }
        }

        return InteractionResult.SUCCESS;
    }
}
