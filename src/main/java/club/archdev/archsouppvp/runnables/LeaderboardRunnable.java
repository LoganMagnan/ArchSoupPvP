package club.archdev.archsouppvp.runnables;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LeaderboardRunnable extends BukkitRunnable {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    @Override
    public void run() {
        for (Player player : this.main.getServer().getOnlinePlayers()) {
            PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

            if (playerData == null) {
                return;
            }

            this.main.getPlayerDataManager().savePlayerData(playerData);
            this.main.getPlayerDataManager().loadPlayerData(playerData);
        }

        this.main.getServer().broadcastMessage(Utils.translate("&8[&d&lKitPvP&8] &7&oUpdating leaderboards..."));
    }
}
