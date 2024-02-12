package net.duckxyz.xyzscythes.item.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.duckxyz.xyzscythes.screen.ScythePortalMenu;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnderScythe extends ScytheItem {
    public EnderScythe(int pAttackDamageModifier, float pAttackSpeedModifier, int durability) {
        super(Tiers.NETHERITE, pAttackDamageModifier, pAttackSpeedModifier, new Properties().durability(durability));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pPlayer.isCrouching() && pLevel.isClientSide()) {
            pPlayer.getCooldowns().addCooldown(this, 600);
            
            Minecraft.getInstance().setScreen(new ScythePortalMenu());

            pPlayer.getItemInHand(pUsedHand).hurtAndBreak(25, pPlayer, p -> {
                p.broadcastBreakEvent(pPlayer.getUsedItemHand());
            });
        }

        if (!pLevel.isClientSide && !pPlayer.isCrouching()) {
            pPlayer.getCooldowns().addCooldown(this, 20);
            ThrownEnderpearl thrownenderpearl = new ThrownEnderpearl(pLevel, pPlayer);
            thrownenderpearl.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
            pLevel.addFreshEntity(thrownenderpearl);

            pPlayer.getItemInHand(pUsedHand).hurtAndBreak(1, pPlayer, p -> {
                p.broadcastBreakEvent(pPlayer.getUsedItemHand());
            });
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.xyz_scythes.ender_right"));
        pTooltipComponents.add(Component.translatable("tooltip.xyz_scythes.ender_shift"));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}

