package club.archdev.archsouppvp.runnables;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class NukeRunnable extends BukkitRunnable {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    private Player player;

    private List<Entity> nearbyEntities;

    private int timer;

    public Player getPlayer() {
        return this.player;
    }

    public List<Entity> getNearbyEntities() {
        return this.nearbyEntities;
    }

    public NukeRunnable(Player player, List<Entity> nearbyEntities) {
        this.player = player;
        this.nearbyEntities = nearbyEntities;
        this.timer = 11;
    }

    public int getTimer() {
        return this.timer;
    }

    public void run() {
        if (this.player == null || !this.player.isOnline()) {
            cancel();
            return;
        }
        if (this.timer <= 1) {
            for (Entity entity : this.nearbyEntities) {
                if (!(entity instanceof Player))
                    continue;
                Player playerEntity = (Player) entity;
                if (this.main.getSpawnManager().getCuboid().isInside(playerEntity))
                    continue;
                ((Player)entity).damage(99999.0D, (Entity)this.player);
            }

            Bukkit.broadcastMessage(Utils.translate("&8[&4&lNuke&8] &eThe nuke took out &d" + this.nearbyEntities.size() + " &eplayers"));

            cancel();

            return;
        }
        this.timer--;
        Bukkit.broadcastMessage(Utils.translate("&8[&4&lNuke&8] &eDropping in &d" + this.timer));
    }
}