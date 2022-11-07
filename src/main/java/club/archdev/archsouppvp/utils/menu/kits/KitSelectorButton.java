package club.archdev.archsouppvp.utils.menu.kits;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.kits.Kit;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import club.archdev.archsouppvp.utils.menu.Button;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class KitSelectorButton extends Button {

    private PlayerData playerData;
    private Kit kit;

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<String>();
        lore.add(Utils.translate(Utils.scoreboardBar));
        lore.addAll(Arrays.asList(kit.getLore()));
        lore.add(Utils.translate(Utils.scoreboardBar));

        if (!this.playerData.getUnlockedKits().contains(kit.getName())) {
            lore.add("&ePrice: " + kit.getPrice());

            if (this.playerData.getCoins() < this.kit.getPrice()) {
                lore.add("&cNot enough coins");
            } else {
                lore.add("&aClick here to buy");
            }
        } else {
            lore.add("&eClick to use &d" + this.kit.getName());
        }

        return new ItemBuilder(kit.getMaterial()).name(Utils.translate("&a" + kit.getName())).lore(Utils.translate(lore)).build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (!this.playerData.getUnlockedKits().contains(kit.getName())) {
            if (this.playerData.getCoins() < kit.getPrice()) {
                player.sendMessage(Utils.translate("&cNot enough coins"));
            } else {
                this.playerData.getUnlockedKits().add(kit.getName());
                this.playerData.setCoins(this.playerData.getCoins() - kit.getPrice());

                player.sendMessage(Utils.translate("&eYou have bought the &d" + kit.getName() + " &ekit"));
            }

            return;
        }

        this.playerData.setCurrentKitName(kit.getName());

        player.closeInventory();
        player.sendMessage(Utils.translate("&eYou have selected the &d" + kit.getName() + " &ekit"));
    }
}
