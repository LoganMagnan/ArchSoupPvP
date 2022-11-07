package club.archdev.archsouppvp.managers.abilities;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.playerdata.PlayerState;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import org.bukkit.Material;

import java.util.Arrays;

public class KangarooAbility extends Ability {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public KangarooAbility() {
        super("Kangaroo", new ItemBuilder(Material.FIREWORK).name("&bKangaroo").lore(Arrays.asList(
                "&7&oRight click to jump",
                "&7&oto the direction",
                "&7&oyou're facing"
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

            player.getLocation().add(0, 10, 0);

            this.main.getCombatManager().cancelNextFallDamage(player, 100);
            this.main.getCooldownManager().setCooldownSet(player, true, this.getCooldown());
            this.main.getCooldownManager().setCooldownTime(player, this.getCooldown());
        };
    }

    @Override
    public int getCooldown() {
        return 10;
    }
}
