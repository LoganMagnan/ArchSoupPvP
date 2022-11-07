package club.archdev.archsouppvp.listeners;

import club.archdev.archsouppvp.ArchSoupPvP;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class AbilityListener implements Listener {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    @EventHandler
    public void onAbilityUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getItem() == null) {
            return;
        }

        if (event.getAction().name().startsWith("RIGHT_")) {
            if (event.getItem().isSimilar(this.main.getAbilityManager().getAbilityByName("Shuriken").getMaterial())) {
                this.main.getAbilityManager().getAbilityByName("Shuriken").getAbilityCallable().executeAs(player);
            } else if (event.getItem().isSimilar(this.main.getAbilityManager().getAbilityByName("Astronaut").getMaterial())) {
                this.main.getAbilityManager().getAbilityByName("Astronaut").getAbilityCallable().executeAs(player);
            } else if (event.getItem().isSimilar(this.main.getAbilityManager().getAbilityByName("Eskimo").getMaterial())) {
                this.main.getAbilityManager().getAbilityByName("Eskimo").getAbilityCallable().executeAs(player);
            } else if (event.getItem().isSimilar(this.main.getAbilityManager().getAbilityByName("Kangaroo").getMaterial())) {
                this.main.getAbilityManager().getAbilityByName("Kangaroo").getAbilityCallable().executeAs(player);
            } else if (event.getItem().isSimilar(this.main.getAbilityManager().getAbilityByName("Monk").getMaterial())) {
                this.main.getAbilityManager().getAbilityByName("Monk").getAbilityCallable().executeAs(player);
            } else if (event.getItem().isSimilar(this.main.getAbilityManager().getAbilityByName("Phantom").getMaterial())) {
                this.main.getAbilityManager().getAbilityByName("Phantom").getAbilityCallable().executeAs(player);
            } else if (event.getItem().isSimilar(this.main.getAbilityManager().getAbilityByName("Stomper").getMaterial())) {
                this.main.getAbilityManager().getAbilityByName("Stomper").getAbilityCallable().executeAs(player);
            } else if (event.getItem().isSimilar(this.main.getAbilityManager().getAbilityByName("Switcher").getMaterial())) {
                this.main.getAbilityManager().getAbilityByName("Switcher").getAbilityCallable().executeAs(player);
            } else if (event.getItem().isSimilar(this.main.getAbilityManager().getAbilityByName("Zeus").getMaterial())) {
                this.main.getAbilityManager().getAbilityByName("Zeus").getAbilityCallable().executeAs(player);
            }
        }
    }
}
