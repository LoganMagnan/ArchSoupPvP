package club.archdev.archsouppvp.utils.menu.kits;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.kits.Kit;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import club.archdev.archsouppvp.utils.menu.Button;
import club.archdev.archsouppvp.utils.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class KitSelectorMenu extends Menu {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    @Override
    public String getTitle(Player player) {
        return Utils.translate("&eSelect a kit...");
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<Integer, Button>();

        PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

        AtomicInteger atomicInteger = new AtomicInteger(0);

        this.main.getKitManager().getKits().stream().sorted(Comparator.comparingInt(Kit :: getPrice)).forEach(kit -> buttons.put(atomicInteger.getAndIncrement(), new KitSelectorButton(playerData, kit)));

        this.fillEmptySlots(buttons, new ItemBuilder(Material.STAINED_GLASS_PANE).name(Utils.translate("")).durability(7).build());

        return buttons;
    }
}
