package club.archdev.archsouppvp.runnables;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BountyRunnable extends BukkitRunnable {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    private Player bountyPlayer;

    private List<Player> bounties = new ArrayList<Player>();

    @Override
    public void run() {
        List<Player> onlinePlayers = new ArrayList<Player>(this.main.getServer().getOnlinePlayers());

        if (onlinePlayers.size() == 0) {
            return;
        }

        onlinePlayers.forEach(player -> {
            PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

            if (playerData.getBounty() > 0) {
                onlinePlayers.remove(player);
            }
        });

        Player player = onlinePlayers.get(ThreadLocalRandom.current().nextInt(onlinePlayers.size()));

        PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

        int amount = ThreadLocalRandom.current().nextInt(100, 10000);

        playerData.setBounty(amount);

        // this.main.getServer().getScheduler().runTaskTimerAsynchronously(this.main, new BountyNameRunnable(player, playerData), 0, 20);
        this.bounties.add(player);
        this.main.getServer().broadcastMessage(Utils.translate("&8[&6&lBounty&8] &d" + player.getName() + " &ehas been bountied for &d" + amount + " &ecoins"));
    }
}
