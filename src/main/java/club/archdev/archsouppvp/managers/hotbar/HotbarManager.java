package club.archdev.archsouppvp.managers.hotbar;

import club.archdev.archsouppvp.managers.hotbar.impl.HotbarItem;
import club.archdev.archsouppvp.utils.ItemUtil;
import club.archdev.archsouppvp.utils.Utils;
import lombok.Getter;
import org.bukkit.Material;
import java.util.ArrayList;
import java.util.List;

@Getter
public class HotbarManager {

    private List<HotbarItem> spawnItems = new ArrayList<>();

    public HotbarManager() {
        this.loadSpawnItems();
    }

    private void loadSpawnItems() {
        spawnItems.add(new HotbarItem(
                ItemUtil.createUnbreakableItem(
                        Material.DIAMOND_SWORD, Utils.translate("&e&l» &dKit selector &e&l«"), 1, (short) 0
                ), 0, true, "KIT_SELECTOR_MENU")
        );

        spawnItems.add(new HotbarItem(
                ItemUtil.createUnbreakableItem(
                        Material.WATCH, Utils.translate("&e&l» &dSelect previous kit &e&l«"), 1, (short) 0
                ), 1, true, "SELECT_PREVIOUS_KIT")
        );

        spawnItems.add(new HotbarItem(
                ItemUtil.createUnbreakableItem(
                        Material.CHEST, Utils.translate("&e&l» &dPerks &e&l«"), 1, (short) 0
                ), 2, true, "PERKS_MENU")
        );

        spawnItems.add(new HotbarItem(
                ItemUtil.createUnbreakableItem(
                        Material.NETHER_STAR, Utils.translate("&e&l» &dStatistics &e&l«"), 1, (short) 0
                ), 4, true, "STATISTICS_MENU")
        );

        spawnItems.add(new HotbarItem(
                ItemUtil.createUnbreakableItem(
                        Material.DIAMOND, Utils.translate("&e&l» &dCosmetics &e&l«"), 1, (short) 0
                ), 7, true, "COSMETICS_MENU")
        );

        spawnItems.add(new HotbarItem(
                ItemUtil.createUnbreakableItem(
                        Material.REDSTONE_COMPARATOR, Utils.translate("&e&l» &dSettings &e&l«"), 1, (short) 0
                ), 8, true, "OPEN_SETTINGS_SELECTOR_MENU")
        );
    }
}
