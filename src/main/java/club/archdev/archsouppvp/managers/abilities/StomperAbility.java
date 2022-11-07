package club.archdev.archsouppvp.managers.abilities;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.playerdata.PlayerState;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class StomperAbility extends Ability {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public StomperAbility() {
        super("Stomper", new ItemBuilder(Material.IRON_BOOTS).name("&bStomper").lore(Arrays.asList(
                "&7&oRight click to jump into the air",
                "&7&oand stomp your enemies"
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

            Vector vector = new Vector(0, 2, 0);

            if (player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.AIR) {
                player.setVelocity(player.getVelocity().add(vector));
            }

            if (player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {
                player.setVelocity(vector);
            }

            this.main.getCombatManager().cancelNextFallDamage(player, 200);
            this.main.getCooldownManager().setCooldownSet(player, true, this.getCooldown());
            this.main.getCooldownManager().setCooldownTime(player, this.getCooldown());
        };
    }

    @Override
    public int getCooldown() {
        return 30;
    }
}
