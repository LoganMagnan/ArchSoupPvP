package club.archdev.archsouppvp.utils.menu.killstreaks;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.killstreaks.Killstreak;
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

public class KillstreaksMenu extends Menu {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    @Override
    public String getTitle(Player player) {
        return Utils.translate("&eKillstreaks...");
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<Integer, Button>();

        AtomicInteger atomicInteger = new AtomicInteger(9);

        this.main.getKillstreakManager().getKillstreaks().stream().sorted(Comparator.comparingInt(Killstreak :: getKillstreak)).forEach(killstreak -> {
            if (atomicInteger.get() == 18) {
                atomicInteger.set(22);
            }

            buttons.put(atomicInteger.getAndIncrement(), new KillstreaksButton(killstreak));
        });

        this.fillEmptySlots(buttons, new ItemBuilder(Material.STAINED_GLASS_PANE).name(Utils.translate("")).durability(7).build());

        return buttons;
    }
}
