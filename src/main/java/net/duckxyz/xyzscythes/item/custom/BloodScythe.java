package net.duckxyz.xyzscythes.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.duckxyz.xyzscythes.entities.projectiles.BloodWave;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BloodScythe extends ScytheItem {
    private double pullStrength = 0.5d;

    public BloodScythe(int pAttackDamageModifier, float pAttackSpeedModifier, int durability) {
        super(Tiers.NETHERITE, pAttackDamageModifier, pAttackSpeedModifier,
                new Properties().durability(durability));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        if (!pPlayer.isCrouching() && !pLevel.isClientSide()) {
            BloodWave bloodWave = new BloodWave(pLevel, pPlayer);
            bloodWave.moveTo(pPlayer.getX(), pPlayer.getY() + 1.3d, pPlayer.getZ());
            bloodWave.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F,
                    1.5F, 1.0F);
            pLevel.addFreshEntity(bloodWave);

            pPlayer.removeAllEffects();
            pPlayer.hurt(pPlayer.damageSources().magic(), 1f);

        } else if (!pLevel.isClientSide()) {
            AABB aabb = pPlayer.getBoundingBox().inflate(15d);
            for (LivingEntity entity : pLevel.getEntitiesOfClass(LivingEntity.class, aabb)) {
                if (entity.is(pPlayer)) continue;
                entity.hurtMarked = true;
                entity.knockback(pullStrength,
                        entity.getX()-pPlayer.getX(),
                        entity.getZ()-pPlayer.getZ());
            }

            pPlayer.getItemInHand(pUsedHand).hurtAndBreak(1, pPlayer, p -> {
                p.broadcastBreakEvent(pPlayer.getUsedItemHand());
            });
        }

        pPlayer.getCooldowns().addCooldown(this, 100);

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.xyz_scythes.blood_right"));
        pTooltipComponents.add(Component.translatable("tooltip.xyz_scythes.blood_shift"));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
