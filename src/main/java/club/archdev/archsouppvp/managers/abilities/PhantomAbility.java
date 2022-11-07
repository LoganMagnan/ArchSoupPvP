package club.archdev.archsouppvp.managers.abilities;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.playerdata.PlayerState;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import org.bukkit.Material;

import java.util.Arrays;

public class PhantomAbility extends Ability {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public PhantomAbility() {
        super("Phantom", new ItemBuilder(Material.FEATHER).name("&bFly").lore(Arrays.asList(
                "&7&oRight click to fly for",
                "&7&o15 seconds"
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

            player.setAllowFlight(true);
            player.setFlying(true);

            this.main.getCooldownManager().setCooldownSet(player, true, this.getCooldown());
            this.main.getCooldownManager().setCooldownTime(player, this.getCooldown());
            this.main.getServer().getScheduler().runTaskLater(this.main, () -> {
                player.setAllowFlight(false);
                player.setFlying(false);
            }, 200);
        };
    }

    @Override
    public int getCooldown() {
        return 60;
    }
}
