package net.duckxyz.xyzscythes.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.duckxyz.xyzscythes.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(), p -> {
            p.broadcastBreakEvent(pContext.getPlayer().getUsedItemHand());
        });

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.xyz_scythes.iron_right"));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
