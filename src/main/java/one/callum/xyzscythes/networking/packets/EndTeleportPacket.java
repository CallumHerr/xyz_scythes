package one.callum.xyzscythes.networking.packets;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EndTeleportPacket {
    public EndTeleportPacket() {

    }

    public EndTeleportPacket(FriendlyByteBuf friendlyByteBuf) {
    }

    public boolean handler(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
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
