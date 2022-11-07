package club.archdev.archsouppvp.utils.menu.refill;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class RefillMenu implements InventoryHolder {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    private Inventory inventory;

    public void openMenu(Player player) {
        PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

        this.inventory = this.main.getServer().createInventory(this, 54, Utils.translate("&eRefill " + /*(playerData.isPotions() ? "potions" :*/ "soups") + "...")/*)*/;

        for (int i = 0; i < 54; i++) {
            this.inventory.setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
        }

        this.main.getCooldownManager().setRefillTime(player, 60);
        this.main.getCooldownManager().setRefillSet(player, true);

        player.openInventory(this.inventory);
    }
}
