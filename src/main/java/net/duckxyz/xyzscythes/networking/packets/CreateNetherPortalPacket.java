package net.duckxyz.xyzscythes.networking.packets;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class CreateNetherPortalPacket {
    private BlockPos pos;
    private boolean xFacing;
    public CreateNetherPortalPacket(BlockPos pos, boolean xFacing) {
        this.pos = pos;
        this.xFacing = xFacing;
    }

    public CreateNetherPortalPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.xFacing = buf.readBoolean();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
        buf.writeBoolean(this.xFacing);
    }

    public boolean handler(CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = (ServerLevel) player.level();
            BlockState portal = Blocks.NETHER_PORTAL.defaultBlockState()
                    .setValue(NetherPortalBlock.AXIS, this.xFacing ?
                            Direction.Axis.Z : Direction.Axis.X);

            level.setBlock(this.pos, portal, 18);
            if (level.getBlockState(this.pos.above()).is(Blocks.AIR)) {
                level.setBlock(this.pos.above(), portal, 18);
            }
        });
        return true;
    }

}
