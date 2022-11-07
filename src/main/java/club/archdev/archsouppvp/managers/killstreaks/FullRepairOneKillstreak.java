package club.archdev.archsouppvp.managers.killstreaks;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Repairable;

import java.util.Arrays;

public class FullRepairOneKillstreak extends Killstreak {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public FullRepairOneKillstreak() {
        super("Full repair one", 5, new ItemBuilder(Material.IRON_INGOT).name("&bFull repair one").lore(Arrays.asList(
                "&7&oFully repair your armor",
                "&d5 kills needed"
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
        };
    }
}
