package club.archdev.archsouppvp.runnables;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;

public class SpawnRunnable extends BukkitRunnable {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    @Override
    public void run() {
        for (Map.Entry<UUID, Integer> mapInformation : this.main.getSpawnManager().getPlayers().entrySet()) {
            Player player = this.main.getServer().getPlayer(mapInformation.getKey());

            if (player != null) {
                if (this.main.getCombatManager().isInCombat(player.getUniqueId())) {
                    this.main.getSpawnManager().getPlayers().remove(mapInformation.getKey());

                    player.sendMessage(Utils.translate("&cYou can not go to spawn while you are in combat"));
                } else {
                    if (mapInformation.getValue() <= 0) {
                        this.main.getSpawnManager().getPlayers().remove(player.getUniqueId());
                        this.main.getPlayerDataManager().sendToSpawnAndResetPlayer(player);

                        player.sendMessage(Utils.translate("&aTeleporting to spawn..."));
                    } else {
                        this.main.getSpawnManager().getPlayers().put(mapInformation.getKey(), mapInformation.getValue() - 1);

                        player.sendMessage(Utils.translate("&aTeleporting to spawn in " + mapInformation.getValue() + "..."));
                    }
                }
            } else {
                this.main.getSpawnManager().getPlayers().remove(mapInformation.getKey());
            }
        }
    }
}
