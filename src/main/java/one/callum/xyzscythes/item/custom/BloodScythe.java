package one.callum.xyzscythes.item.custom;

import com.mojang.logging.LogUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.enchantment.KnockbackEnchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import one.callum.xyzscythes.entities.projectiles.BloodWave;
import one.callum.xyzscythes.entities.projectiles.SonicBlast;

public class BloodScythe extends ScytheItem {
    private double pullStrength = 0.5d;

    public BloodScythe(int pAttackDamageModifier, float pAttackSpeedModifier, int durability) {
        super(Tiers.NETHERITE, pAttackDamageModifier, pAttackSpeedModifier,
                new Properties().durability(durability));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        if (!pPlayer.isCrouching() && !pLevel.isClientSide()) {
            LogUtils.getLogger().debug("test");
            BloodWave bloodWave = new BloodWave(pLevel, pPlayer);
            bloodWave.moveTo(pPlayer.getX(), pPlayer.getY() + 1.3d, pPlayer.getZ());
            bloodWave.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F,
                    1.5F, 1.0F);
            pLevel.addFreshEntity(bloodWave);

            pPlayer.getCooldowns().addCooldown(this, 100);
        } else if (!pLevel.isClientSide()) {
            AABB aabb = pPlayer.getBoundingBox().inflate(9d);
            pLevel.getEntities(pPlayer, aabb).forEach(e -> {
                if (e instanceof LivingEntity entity) {
//                    Vec3 vec3 = entity.getDeltaMovement();
//                    Vec3 vec31 = (new Vec3(entity.getX()-pPlayer.getX(),
//                            0.0D, entity.getZ()-pPlayer.getZ()))
//                            .normalize().scale(pullStrength);
//                    entity.setDeltaMovement(
//                            vec3.x / 2.0D - vec31.x,
//                            entity.onGround() ?
//                                    Math.min(0.4D, vec3.y / 2.0D + pullStrength) : vec3.y,
//                            vec3.z / 2.0D - vec31.z
//                    );
                    entity.knockback(pullStrength,
                            entity.getX()-pPlayer.getX(),
                            entity.getZ()-pPlayer.getZ());
                }
            });
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
