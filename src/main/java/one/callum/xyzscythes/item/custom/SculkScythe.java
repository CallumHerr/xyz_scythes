package one.callum.xyzscythes.item.custom;

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
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import one.callum.xyzscythes.entities.projectiles.SonicBlast;

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

            pPlayer.getCooldowns().addCooldown(this, 100);
        } else {
            AABB aabb = pPlayer.getBoundingBox().inflate(5d);
            pLevel.getEntities(pPlayer, aabb).forEach(e -> {
                e.hurt(pPlayer.damageSources().sonicBoom(pPlayer), 5f);
                if (e instanceof LivingEntity entity) {
                    entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100));
                }
            });

            level.playLocalSound(pPlayer.blockPosition(), SoundEvents.SCULK_SHRIEKER_SHRIEK,
                    SoundSource.PLAYERS, 2f, 0.6f, true);

            pPlayer.getCooldowns().addCooldown(this, 200);
        }

        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }
}
