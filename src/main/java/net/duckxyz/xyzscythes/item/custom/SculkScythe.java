package net.duckxyz.xyzscythes.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.duckxyz.xyzscythes.entities.projectiles.SonicBlast;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SculkScythe extends ScytheItem {
    public SculkScythe(int pAttackDamageModifier, float pAttackSpeedModifier, int durability) {
        super(Tiers.NETHERITE, pAttackDamageModifier, pAttackSpeedModifier, new Properties().durability(durability));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide()) return super.use(pLevel, pPlayer, pUsedHand);
        ServerLevel level = (ServerLevel) pLevel;

        if (!pPlayer.isCrouching()) {
            SonicBlast sonicBlast = new SonicBlast(level, pPlayer);
            sonicBlast.moveTo(pPlayer.getX(), pPlayer.getY() + 1.3d, pPlayer.getZ());
            sonicBlast.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F,
                    1.5F, 1.0F);
            level.addFreshEntity(sonicBlast);

            pPlayer.getItemInHand(pUsedHand).hurtAndBreak(5, pPlayer, p -> {
                p.broadcastBreakEvent(pPlayer.getUsedItemHand());
            });

            pPlayer.getCooldowns().addCooldown(this, 100);
        } else {
            AABB aabb = pPlayer.getBoundingBox().inflate(5d);
            for (LivingEntity entity : pLevel.getEntitiesOfClass(LivingEntity.class, aabb)) {
                if (entity.is(pPlayer)) continue;
                entity.hurt(pPlayer.damageSources().magic(), 2f);
                entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100));
            }

            level.playLocalSound(pPlayer.blockPosition(), SoundEvents.SCULK_SHRIEKER_SHRIEK,
                    SoundSource.PLAYERS, 2f, 0.6f, true);

            pPlayer.getItemInHand(pUsedHand).hurtAndBreak(5, pPlayer, p -> {
                p.broadcastBreakEvent(pPlayer.getUsedItemHand());
            });

            pPlayer.getCooldowns().addCooldown(this, 200);
        }

        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.xyz_scythes.sculk_right"));
        pTooltipComponents.add(Component.translatable("tooltip.xyz_scythes.sculk_shift"));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
