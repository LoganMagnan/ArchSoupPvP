package club.archdev.archsouppvp.utils.menu.settings;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.utils.Utils;
import club.archdev.archsouppvp.utils.config.ConfigCursor;
import club.archdev.archsouppvp.utils.menu.Button;
import club.archdev.archsouppvp.utils.menu.Menu;
import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

public class SettingsMenu extends Menu {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    @Override
    public String getTitle(Player player) {
        return Utils.translate("&bSettings");
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public boolean isUpdateAfterClick() {
        return true;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<Integer, Button>();
        buttons.put(
                12,
                new SettingsButton(
                        "&bToggle Scoreboard",
                        Lists.newArrayList(),
                        Material.valueOf("PAINTING"),
                        0,
                        "toggle-scoreboard"
                )
        );

        buttons.put(
                14,
                new SettingsButton(
                        "&bPlayer Visibility",
                        Lists.newArrayList(),
                        Material.valueOf("EYE_OF_ENDER"),
                        0,
                        "player-visibility"
                )
        );

        return buttons;
    }
}
