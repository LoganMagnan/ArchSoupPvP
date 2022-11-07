package club.archdev.archsouppvp.utils.menu.statistics;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import club.archdev.archsouppvp.utils.menu.Button;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class StatisticsButton extends Button {

    private UUID uuid;

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<String>();

        Player target = ArchSoupPvP.getInstance().getServer().getPlayer(this.uuid);

        PlayerData targetData = ArchSoupPvP.getInstance().getPlayerDataManager().getPlayerData(target.getUniqueId());

        lore.add(Utils.scoreboardBar);
        lore.add("  &8* &fKills: &d" + targetData.getKills());
        lore.add("  &8* &fDeaths: &d" + targetData.getDeaths());
        lore.add("  &8* &fKDR: &d" + targetData.getKills() / targetData.getDeaths());
        lore.add("  &8* &fCurrent killstreak: &d" + targetData.getCurrentKillstreak());
        lore.add("  &8* &fHighest killstreak: &d" + targetData.getHighestKillstreak());
        lore.add("  &8* &fCurrent kit: &d" + (targetData.getCurrentKitName() == null ? targetData.getCurrentKitName() : "None"));
        lore.add(Utils.scoreboardBar);

        return new ItemBuilder(Material.SKULL_ITEM).name(Utils.translate("&d&l" + target.getName() + "'s statistics")).lore(Utils.translate(lore)).durability(3).owner(target.getName()).build();
    }
}
