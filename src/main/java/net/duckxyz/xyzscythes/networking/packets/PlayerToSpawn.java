package net.duckxyz.xyzscythes.networking.packets;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.network.CustomPayloadEvent;

import java.util.function.Supplier;

public class PlayerToSpawn {
    public PlayerToSpawn() {

    }

    public PlayerToSpawn(FriendlyByteBuf buf) {}

    public void write(FriendlyByteBuf buf) {}

    public boolean handler(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            BlockPos pos = player.getRespawnPosition();

            BlockState state = player.level().getBlockState(pos);
            while (!state.isValidSpawn(player.level(), pos, EntityType.PLAYER) && pos.getY() < 320) {
                pos = pos.above();
            }
            if (pos.getY() >= 320) {
                player.sendSystemMessage(Component.translatable("chat.xyz_scythes.spawn_blocked"));
                return;
            }

            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200));
            player.teleportToWithTicket(pos.getX(), pos.getY(), pos.getZ());
        });
        return true;
    }

}
