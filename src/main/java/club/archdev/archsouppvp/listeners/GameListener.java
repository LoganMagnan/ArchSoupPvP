package club.archdev.archsouppvp.listeners;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.playerdata.PlayerData;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class GameListener implements Listener {

    private ArchSoupPvP plugin = ArchSoupPvP.getInstance();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        Location from = event.getFrom();
        Location to = event.getTo();

        PlayerData playerData = this.plugin.getPlayerDataManager().getPlayerData(player.getUniqueId());

        if (player == null) {
            return;
        }

        if (!this.plugin.getSpawnManager().getCuboid().isInside((player)) && (from.getBlockX() != to.getBlockX() || from.getBlockZ() != to.getBlockZ()) && player.getInventory().contains(this.plugin.getHotbarManager().getSpawnItems().get(0).getItemStack())) {
            if (playerData.getCurrentKitName() == null) {
                this.plugin.getKitManager().getKitByName("Default").useKit(player);
            } else {
                this.plugin.getKitManager().getKitByName(playerData.getCurrentKitName()).useKit(player);
            }
        }
    }
}
