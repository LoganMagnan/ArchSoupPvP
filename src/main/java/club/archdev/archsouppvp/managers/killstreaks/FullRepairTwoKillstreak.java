package club.archdev.archsouppvp.managers.killstreaks;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Repairable;

import java.util.Arrays;

public class FullRepairTwoKillstreak extends Killstreak {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public FullRepairTwoKillstreak() {
        super("Full repair two", 25, new ItemBuilder(Material.DIAMOND).name("&bFull repair two").lore(Arrays.asList(
                "&7&oFully repair your armor and receive 50 coins",
                "&d25 kills needed"
        )).build());
    }

    public KillstreaksCallable getKillstreaksCallable() {
        return (player, playerData) -> {
            this.main.getServer().broadcastMessage(Utils.translate("&a" + player.getName() + " &eis on a &d" + this.getKillstreak() + " &ekillstreak"));

            for (ItemStack itemStack : player.getInventory().getArmorContents()) {
                if (itemStack != null && itemStack.getItemMeta() instanceof Repairable) {
                    itemStack.setDurability((short) 0);
                }
            }

            playerData.setCoins(playerData.getCoins() + 50);

            player.sendMessage(Utils.translate("&8[&d&lKillstreak&8] &e+50 coins"));
        };
    }
}
