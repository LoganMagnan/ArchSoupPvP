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

public class AstronautAbility extends Ability {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public AstronautAbility() {
        super("Astronaut", new ItemBuilder(Material.GLASS).name(Utils.translate("&bGravity")).lore(Arrays.asList(
                "&7&oRight click to radiate gravity"
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

            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);

            for (Entity entity : nearbyEntities) {
                if (!(entity instanceof Player)) {
                    continue;
                }

                Player target = (Player) entity;

                if (player.getName().equalsIgnoreCase(target.getName())) {
                    continue;
                }

                target.getLocation().add(player.getLocation().getDirection().multiply(5).setY(10));
            }

            this.main.getCooldownManager().setCooldownSet(player, true, this.getCooldown());
            this.main.getCooldownManager().setCooldownTime(player, this.getCooldown());
        };
    }

    @Override
    public int getCooldown() {
        return 60;
    }
}
