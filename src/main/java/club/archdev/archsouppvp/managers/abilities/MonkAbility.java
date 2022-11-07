package club.archdev.archsouppvp.managers.abilities;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.playerdata.PlayerState;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import org.bukkit.Material;

import java.util.Arrays;

public class MonkAbility extends Ability {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public MonkAbility() {
        super("Monk", new ItemBuilder(Material.BLAZE_ROD).name("&bMonk").lore(Arrays.asList(
                "&7&oHit a player to switch items",
                "&7&ofrom their hotbar with",
                "&7&oitems from their inventory"
        )).build());
    }

    @Override
    public AbilityCallable getAbilityCallable() {
        return player -> {
            PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

            if (playerData.getPlayerState() == PlayerState.SPAWN) {
                return;
            }

            if (this.main.getCooldownManager().isOnCooldown(player)) {
                player.sendMessage(Utils.translate("&cYou have to wait &c&l" + this.main.getCooldownManager().getCooldownTime(player) + " seconds &cto use the ability again"));

                return;
            }

            this.main.getCooldownManager().setCooldownSet(player, true, this.getCooldown());
            this.main.getCooldownManager().setCooldownTime(player, this.getCooldown());
        };
    }

    @Override
    public int getCooldown() {
        return 25;
    }
}
