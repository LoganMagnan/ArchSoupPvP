package club.archdev.archsouppvp.managers.killstreaks;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class SnowBombsKillstreak extends Killstreak {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public SnowBombsKillstreak() {
        super("Snow bombs", 45, new ItemBuilder(Material.SNOW_BALL).name("&bSnow bombs").lore(Arrays.asList(
                "&7&oGives 3 snow bombs",
                "&d45 kills needed"
        )).build());
    }

    public KillstreaksCallable getKillstreaksCallable() {
        return (player, playerData) -> {
            this.main.getServer().broadcastMessage(Utils.translate("&a" + player.getName() + " &eis on a &d" + this.getKillstreak() + " &ekillstreak"));

            if (player.getInventory().firstEmpty() == -1) {
                player.sendMessage(Utils.translate("&cYour inventory is full"));

                return;
            }

            ItemStack itemStack = (new ItemBuilder(Material.SNOW_BALL)).name("&bSnow bombs").lore(Arrays.asList(
                    "&7&oThis Bomb will explode",
                    "&7&oon impact"
            )).amount(3).enchantment(Enchantment.DURABILITY).build();

            player.getInventory().addItem(itemStack);
        };
    }
}
