package one.callum.xyzscythes.item.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.SculkShriekerBlock;

public class NetherScythe extends ScytheItem {
    public NetherScythe(int pAttackDamageModifier, float pAttackSpeedModifier, int durability) {
        super(Tiers.DIAMOND, pAttackDamageModifier, pAttackSpeedModifier, new Properties().fireResistant().durability(durability));
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pTarget.setSecondsOnFire(1);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.FIRE_ASPECT) return false;
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide) return super.use(pLevel, pPlayer, pUsedHand);

        Fireball fireball;
        Double offset;
        if (pPlayer.isCrouching()) {
            fireball = new LargeFireball(pLevel, pPlayer, 0, 0, 0, 1);
            pPlayer.getCooldowns().addCooldown(this, 20);
            offset = 1d;
        }
        else {
            fireball = new SmallFireball(pLevel, pPlayer, 0, 0, 0);
            pPlayer.getCooldowns().addCooldown(this, 10);
            offset = 1.3d;
        }

        fireball.moveTo(pPlayer.getX(), pPlayer.getY() + offset, pPlayer.getZ());
        fireball.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0, 3F, 1.0F);
        pLevel.addFreshEntity(fireball);

        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }
}
