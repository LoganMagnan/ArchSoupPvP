package club.archdev.archsouppvp.managers.killstreaks;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class GoldenApplesKillstreak extends Killstreak {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public GoldenApplesKillstreak() {
        super("Golden apples", 10, new ItemBuilder(Material.GOLDEN_APPLE).name("&bGolden apples").lore(Arrays.asList(
                "&7&oGives you 8 golden apples",
                "&d10 kills needed"
        )).build());
    }

    public KillstreaksCallable getKillstreaksCallable() {
        return (player, playerData) -> {
            this.main.getServer().broadcastMessage(Utils.translate("&a" + player.getName() + " &eis on a &d" + this.getKillstreak() + " &ekillstreak"));

            if (player.getInventory().firstEmpty() == -1) {
                player.sendMessage(Utils.translate("&cYour inventory is full"));

                return;
            }

            player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 8));
        };
    }
}
