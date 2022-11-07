package club.archdev.archsouppvp.managers.abilities;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.playerdata.PlayerState;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZeusAbility extends Ability {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public ZeusAbility() {
        super("Zeus", new ItemBuilder(Material.GLOWSTONE_DUST).name("&bLightning shooter").lore(Arrays.asList(
                "&7&oRight click to summon lightning",
                "&7&oon enemies that are nearby"
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

            List<Entity> nearbyEntities = new ArrayList<>(player.getNearbyEntities(5, 5, 5));

            if (nearbyEntities.isEmpty()) {
                player.sendMessage(Utils.translate("&cThere are no nearby players"));

                return;
            }

            for (Entity nearby : nearbyEntities) {
                if (nearby instanceof Player) {
                    ((Player) nearby).damage(10, player);

                    nearby.getWorld().strikeLightningEffect(nearby.getLocation());

                    this.main.getCooldownManager().setCooldownSet(player, true, this.getCooldown());
                    this.main.getCooldownManager().setCooldownTime(player, this.getCooldown());
                }
            }
        };
    }

    @Override
    public int getCooldown() {
        return 30;
    }
}
