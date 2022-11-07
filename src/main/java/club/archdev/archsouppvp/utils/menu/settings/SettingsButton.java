package club.archdev.archsouppvp.utils.menu.settings;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.playerdata.PlayerSettings;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import club.archdev.archsouppvp.utils.menu.Button;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class SettingsButton extends Button {

    private String name;
    private List<String> lore;
    private Material material;
    private int durability;
    private String type;

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<String>();

        PlayerData playerData = ArchSoupPvP.getInstance().getPlayerDataManager().getPlayerData(player.getUniqueId());

        PlayerSettings playerSettings = playerData.getPlayerSettings();

        String enabled = Utils.translate("  &a- ");
        String disabled = Utils.translate("  &c- ");
        String unselected = Utils.translate("  &7 ");

        switch (this.type) {
            case "toggle-scoreboard":
                lore.add("&8&m----------------------");
                lore.add("&7If enabled, you will");
                lore.add("&7be able to see the scoreboard");
                lore.add("");
                lore.add((playerSettings.isScoreboardEnabled() ? enabled : unselected) + "&7Show the scoreboard");
                lore.add((!playerSettings.isScoreboardEnabled() ? disabled : unselected) + "&7Hide the scoreboard");

                break;
            case "player-visibility":
                lore.add("&8&m----------------------");
                lore.add("&7If enabled, you will");
                lore.add("&7be able to see the players in the hub");
                lore.add("");
                lore.add((playerSettings.isPlayerVisibilityEnabled() ? enabled : unselected) + "&7Show the players in the hub");
                lore.add((!playerSettings.isPlayerVisibilityEnabled() ? disabled : unselected) + "&7Hide the players in the hub");

                break;
        }

        lore.add(Utils.scoreboardBar);

        ItemStack itemStack = new ItemBuilder(this.material)
                .name(Utils.translate(this.name))
                .lore(Utils.translate(lore))
                .amount(1)
                .durability(this.durability)
                .build();

        return itemStack;
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        PlayerData playerData = ArchSoupPvP.getInstance().getPlayerDataManager().getPlayerData(player.getUniqueId());

        switch (this.type) {
            case "toggle-scoreboard":
                playerData.getPlayerSettings().setScoreboardEnabled(!playerData.getPlayerSettings().isScoreboardEnabled());

                break;
            case "player-visibility":
                playerData.getPlayerSettings().setPlayerVisibilityEnabled(!playerData.getPlayerSettings().isPlayerVisibilityEnabled());

                ArchSoupPvP.getInstance().getPlayerDataManager().updatePlayerView();

                break;
        }

        player.updateInventory();
    }
}
