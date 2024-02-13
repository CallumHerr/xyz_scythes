package net.duckxyz.xyzscythes.networking.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.network.CustomPayloadEvent;

import java.util.function.Supplier;

public class EndTeleportPacket {
    public EndTeleportPacket() {

    }

    public EndTeleportPacket(FriendlyByteBuf friendlyByteBuf) {
    }

    public boolean handler(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            Level level = player.level();

            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200));

            ResourceKey dimension = level.dimension() == Level.END ? Level.OVERWORLD : Level.END;
            ServerLevel serverlevel = ((ServerLevel) level).getServer().getLevel(dimension);
            if (serverlevel == null) {
                return;
            }

            player.changeDimension(serverlevel);
        });
        return true;
    }

    public void write(FriendlyByteBuf friendlyByteBuf) {
    }
}
