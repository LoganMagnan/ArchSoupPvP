package club.archdev.archsouppvp.utils.menu.statistics;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import club.archdev.archsouppvp.utils.menu.Button;
import club.archdev.archsouppvp.utils.menu.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class StatisticsMenu extends Menu {

    private UUID uuid;

    @Override
    public String getTitle(Player player) {
        return Utils.translate("&d&l" + ArchSoupPvP.getInstance().getServer().getOfflinePlayer(this.uuid).getName() + "'s statistics");
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<Integer, Button>();
        buttons.put(12, new StatisticsButton(this.uuid));
        buttons.put(14, new StatisticsUnlockedKitsButton(this.uuid));

        this.fillEmptySlots(buttons, new ItemBuilder(Material.STAINED_GLASS_PANE).name(Utils.translate("")).durability(7).build());

        return buttons;
    }
}
