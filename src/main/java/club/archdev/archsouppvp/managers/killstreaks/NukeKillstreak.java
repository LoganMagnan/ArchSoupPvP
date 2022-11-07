package club.archdev.archsouppvp.managers.killstreaks;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.runnables.NukeRunnable;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NukeKillstreak extends Killstreak {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public NukeKillstreak() {
        super("Nuke", 50, new ItemBuilder(Material.TNT).name("&bNuke").lore(Arrays.asList(
                "&7&oDecimates all enemies in a radius of 25 blocks",
                "&d50 kills needed"
        )).build());
    }

    public KillstreaksCallable getKillstreaksCallable() {
        return (player, playerData) -> {
            this.main.getServer().broadcastMessage(Utils.translate("&a" + player.getName() + " &eis on a &d" + this.getKillstreak() + " &ekillstreak"));

            List<Entity> nearbyEntities = new ArrayList<Entity>(player.getNearbyEntities(25, 25, 25));

            new NukeRunnable(player, nearbyEntities).runTaskTimerAsynchronously(this.main, 0, 20);
        };
    }
}
