package club.archdev.archsouppvp.utils.menu.statistics;

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
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class StatisticsUnlockedKitsButton extends Button {

    private UUID uuid;

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<String>();

        Player target = ArchSoupPvP.getInstance().getServer().getPlayer(this.uuid);

        PlayerData targetData = ArchSoupPvP.getInstance().getPlayerDataManager().getPlayerData(target.getUniqueId());

        lore.add(Utils.scoreboardBar);

        ArchSoupPvP.getInstance().getKitManager().getKits().stream().sorted(Comparator.comparingInt(Kit :: getPrice)).forEach(kit -> lore.add("  &8* &f" + kit.getName() + " &d>> " + (targetData.getUnlockedKits().contains(kit.getName()) ? " &a✓" : "&c✘")));

        lore.add(Utils.scoreboardBar);

        return new ItemBuilder(Material.DIAMOND_SWORD).name(Utils.translate("&d&l" + target.getName() + "'s kits")).lore(Utils.translate(lore)).build();
    }
}
