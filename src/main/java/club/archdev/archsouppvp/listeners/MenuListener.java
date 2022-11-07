package club.archdev.archsouppvp.listeners;

import club.archdev.archsouppvp.utils.menusystem.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (holder instanceof Menu) {
            event.setCancelled(true);

            if (event.getCurrentItem() == null) {
                return;
            }

            Menu menu = (Menu) holder;
            menu.handleMenu(event);
        }
    }
}
