package club.archdev.archsouppvp.managers.killstreaks;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ChickenStrikeKillstreak extends Killstreak {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public ChickenStrikeKillstreak() {
        super("Chicken strike", 35, new ItemBuilder(Material.EGG).name("&bChicken strike").lore(Arrays.asList(
                "&7&oCall in a strike of eggs that explode",
                "&d35 kills needed"
        )).build());
    }

    public KillstreaksCallable getKillstreaksCallable() {
        return (player, playerData) -> {
            this.main.getServer().broadcastMessage(Utils.translate("&a" + player.getName() + " &eis on a &d" + this.getKillstreak() + " &ekillstreak"));

            if (player.getInventory().firstEmpty() == -1) {
                player.sendMessage(Utils.translate("&cYour inventory is full"));

                return;
            }

            ItemStack itemStack = new ItemBuilder(Material.EGG).name("&bChicken strike").lore(Arrays.asList(
                    "&7&oCall in a strike of eggs",
                    "&7&othat come raining down and explode"
            )).enchantment(Enchantment.DURABILITY).build();

            player.getInventory().addItem(itemStack);
        };
    }
}
