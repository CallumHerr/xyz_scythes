package one.callum.xyzscythes.item.custom;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EndGatewayBlock;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraftforge.network.NetworkHooks;
import one.callum.xyzscythes.networking.ModMessages;
import one.callum.xyzscythes.networking.packets.CreateNetherPortalPacket;
import one.callum.xyzscythes.screen.ScythePortalMenu;

public class EnderScythe extends ScytheItem {
    public EnderScythe(int pAttackDamageModifier, float pAttackSpeedModifier, int durability) {
        super(Tiers.NETHERITE, pAttackDamageModifier, pAttackSpeedModifier, new Properties().durability(durability));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pPlayer.isCrouching() && pLevel.isClientSide()) {
            pPlayer.getCooldowns().addCooldown(this, 600);
            
            Minecraft.getInstance().setScreen(new ScythePortalMenu());
        }

        if (!pLevel.isClientSide && !pPlayer.isCrouching()) {
            pPlayer.getCooldowns().addCooldown(this, 20);
            ThrownEnderpearl thrownenderpearl = new ThrownEnderpearl(pLevel, pPlayer);
            thrownenderpearl.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
            pLevel.addFreshEntity(thrownenderpearl);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}

