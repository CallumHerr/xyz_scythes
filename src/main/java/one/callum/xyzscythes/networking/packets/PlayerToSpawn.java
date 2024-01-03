package one.callum.xyzscythes.networking.packets;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerToSpawn {
    public PlayerToSpawn() {

    }

    public PlayerToSpawn(FriendlyByteBuf buf) {}

    public void write(FriendlyByteBuf buf) {}

    public boolean handler(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            BlockPos pos = player.getRespawnPosition();

            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200));
            player.teleportToWithTicket(pos.getX(), pos.getY(), pos.getZ());
        });
        return true;
    }

}
