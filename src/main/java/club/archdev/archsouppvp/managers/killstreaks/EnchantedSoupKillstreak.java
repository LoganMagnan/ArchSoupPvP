package club.archdev.archsouppvp.managers.killstreaks;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class EnchantedSoupKillstreak extends Killstreak {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public EnchantedSoupKillstreak() {
        super("Enchanted soup", 20, new ItemBuilder(Material.MUSHROOM_SOUP).name("&bEnchanted soup").lore(Arrays.asList(
                "&7&oGives 2 enchanted soup (Instant maximum health)",
                "&d20 kills needed"
        )).build());
    }

    public KillstreaksCallable getKillstreaksCallable() {
        return (player, playerData) -> {
            this.main.getServer().broadcastMessage(Utils.translate("&a" + player.getName() + " &eis on a &d" + this.getKillstreak() + " &ekillstreak"));

            if (player.getInventory().firstEmpty() == -1) {
                player.sendMessage(Utils.translate("&cYour inventory is full"));

                return;
            }

            ItemStack itemStack = new ItemBuilder(Material.MUSHROOM_SOUP).name("&bEnchanted soup").lore(Arrays.asList(
                    "&7&oA normal soup, but",
                    "&7&oit heals you to full health"
            )).enchantment(Enchantment.DURABILITY).amount(2).build();

            player.getInventory().addItem(itemStack);
        };
    }
}
