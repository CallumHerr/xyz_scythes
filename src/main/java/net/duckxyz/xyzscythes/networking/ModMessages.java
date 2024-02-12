package net.duckxyz.xyzscythes.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.duckxyz.xyzscythes.XYZScythes;
import net.duckxyz.xyzscythes.networking.packets.CreateNetherPortalPacket;
import net.duckxyz.xyzscythes.networking.packets.EndTeleportPacket;
import net.duckxyz.xyzscythes.networking.packets.PlayerToSpawn;

public class ModMessages {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(XYZScythes.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(CreateNetherPortalPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(CreateNetherPortalPacket::new)
                .encoder(CreateNetherPortalPacket::write)
                .consumerMainThread(CreateNetherPortalPacket::handler)
                .add();

        net.messageBuilder(EndTeleportPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(EndTeleportPacket::new)
                .encoder(EndTeleportPacket::write)
                .consumerMainThread(EndTeleportPacket::handler)
                .add();

        net.messageBuilder(PlayerToSpawn.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerToSpawn::new)
                .encoder(PlayerToSpawn::write)
                .consumerMainThread(PlayerToSpawn::handler)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
