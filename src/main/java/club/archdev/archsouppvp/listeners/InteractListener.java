package club.archdev.archsouppvp.listeners;

import club.archdev.archsouppvp.managers.hotbar.impl.HotbarItem;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.playerdata.PlayerState;
import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.utils.menu.kits.KitSelectorMenu;
import club.archdev.archsouppvp.utils.menu.settings.SettingsMenu;
import club.archdev.archsouppvp.utils.menu.statistics.StatisticsMenu;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }

        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        ItemStack itemStack = player.getItemInHand();

        if (!event.hasItem() || itemStack == null) {
            return;
        }

        PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

        if (playerData.getPlayerState() == PlayerState.PLAYING) {
            return;
        }

        HotbarItem hotBarItem = HotbarItem.getItemByItemStack(player.getItemInHand());

        if (hotBarItem == null) {
            return;
        }

        if (hotBarItem.getActionType() == null) {
            return;
        }

        switch (playerData.getPlayerState()) {
            case SPAWN:
                event.setCancelled(true);

                switch (hotBarItem.getActionType()) {
                    case KIT_SELECTOR_MENU:
                        new KitSelectorMenu().openMenu(player);

                        break;
                    case SELECT_PREVIOUS_KIT:
                        if (playerData.getCurrentKitName() == null) {
                            this.main.getKitManager().getKitByName("Default").useKit(player);
                        } else {
                            this.main.getKitManager().getKitByName(playerData.getCurrentKitName()).useKit(player);
                        }

                        break;
                    case PERKS_MENU:
                        break;
                    case STATISTICS_MENU:
                        new StatisticsMenu(player.getUniqueId()).openMenu(player);

                        break;
                    case COSMETICS_MENU:
                        break;
                    case OPEN_SETTINGS_SELECTOR_MENU:
                        new SettingsMenu().openMenu(player);

                        break;
                }

                break;
        }
    }
}
