package club.archdev.archsouppvp.runnables;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.abilities.Ability;
import club.archdev.archsouppvp.managers.kits.Kit;
import club.archdev.archsouppvp.playerdata.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ExperienceRunnable extends BukkitRunnable {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    @Override
    public void run() {
        for (Player player : this.main.getCooldownManager().getCooldownSet()) {
            if (player == null) {
                continue;
            }

            PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

            Kit kit = this.main.getKitManager().getKitByName(playerData.getCurrentKitName());

            if (kit.getAbility() == null) {
                continue;
            }

            Ability ability = this.main.getAbilityManager().getAbilityByName(kit.getAbility().getName());

            long time = (long) this.main.getCooldownManager().getCooldownTime(player) * 1000L;

            int seconds = (int) Math.round(time / 1000.00D);

            player.setLevel(seconds);
            player.setExp(this.main.getCooldownManager().getCooldownTime(player) / ability.getCooldown());
        }
    }
}
