package net.duckxyz.xyzscythes.screen;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.duckxyz.xyzscythes.networking.ModMessages;
import net.duckxyz.xyzscythes.networking.packets.CreateNetherPortalPacket;
import net.duckxyz.xyzscythes.networking.packets.EndTeleportPacket;
import net.duckxyz.xyzscythes.networking.packets.PlayerToSpawn;

public class ScythePortalMenu extends Screen {
    private static final Component SPAWN = Component.translatable("menu.xyz_scythes.spawn");
    private static final Component TELEPORTED = Component.translatable("chat.xyz_scythes.teleported");

    public ScythePortalMenu() {
        super(Component.translatable("menu.xyz_scythes.teleport_title"));
    }

    @Override
    protected void init() {
        this.createMenu();

        this.addRenderableWidget(new StringWidget(0, 20, this.width, 9, this.title, this.font));
    }

    private void createMenu() {
        Player player = this.minecraft.player;
        Level level = this.minecraft.level;
        GridLayout grid = new GridLayout();
        grid.defaultCellSetting().padding(4, 4, 4, 0);
        GridLayout.RowHelper rowHelper = grid.createRowHelper(1);

        Component NETHER = Component.translatable(level.dimension() != Level.NETHER ?
                "menu.xyz_scythes.nether" : "menu.xyz_scythes.respawn_anchor");
        rowHelper.addChild(Button.builder(NETHER, (button) -> {
            if (level.dimension() == Level.NETHER) {
                ModMessages.sendToServer(new PlayerToSpawn());
                this.minecraft.setScreen(null);
                player.sendSystemMessage(TELEPORTED);
                return;
            }

            Vec3 vec3 = player.getLookAngle();
            BlockPos playerPos = player.blockPosition();
            BlockPos pos;
            boolean xFacing;

            if (Math.abs(vec3.x) > Math.abs(vec3.z)) {
                xFacing = true;
                if (vec3.x < 0) pos =  playerPos.offset(-2, 0, 0);
                else pos = playerPos.offset(2, 0, 0);
            } else {
                xFacing = false;
                if (vec3.z < 0) pos = playerPos.offset(0, 0, -2);
                else pos = playerPos.offset(0, 0, 2);
            }

            if (!this.minecraft.level.getBlockState(pos).is(Blocks.AIR)) {
                player.sendSystemMessage(Component.translatable("chat.xyz_scythes.portal_blocked")
                        .append("[" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + "]"));
            } else {
                ModMessages.sendToServer(new CreateNetherPortalPacket(pos, xFacing));
                player.sendSystemMessage(Component.translatable("chat.xyz_scythes.portal_opened")
                        .append("[" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + "]"));
            }
            this.minecraft.setScreen(null);
        }).width(204).build());

        Component END = Component.translatable(level.dimension() != Level.END ?
                "menu.xyz_scythes.end" : "menu.xyz_scythes.overworld");

        rowHelper.addChild(Button.builder(END, (pButton) -> {
            ModMessages.sendToServer(new EndTeleportPacket());
            this.minecraft.setScreen(null);
            player.sendSystemMessage(TELEPORTED);
        }).width(204).build());

        if (level.dimension() == Level.OVERWORLD) rowHelper.addChild(Button.builder(SPAWN, (pButton) -> {
            ModMessages.sendToServer(new PlayerToSpawn());
            this.minecraft.setScreen(null);
            player.sendSystemMessage(TELEPORTED);
        }).width(204).build());

        grid.arrangeElements();
        FrameLayout.alignInRectangle(grid, 0, 0, this.width, this.height, 0.5F, 0.25F);
        grid.visitWidgets(this::addRenderableWidget);
    }
}
