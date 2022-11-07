package club.archdev.archsouppvp.runnables;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.kits.Kit;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CombatTagRunnable extends BukkitRunnable {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    @Override
    public void run() {
        this.main.getCombatManager().setCount(this.main.getCombatManager().getCount() + 1);

        for (Player player : this.main.getServer().getOnlinePlayers()) {
            if (this.main.getCombatManager().isInCombat(player.getUniqueId())) {
                int count = this.main.getCombatManager().getCombatTime(player.getUniqueId());

                count--;

                this.main.getCombatManager().setCombatTime(player.getUniqueId(), count);

                if (count == 0) {
                    this.main.getCombatManager().getCombatSet().remove(player.getUniqueId());
                    this.main.getCombatManager().getTimeMap().remove(player.getUniqueId());

                    player.sendMessage(Utils.translate("&aYou are no longer in combat"));
                }
            }

            if (this.main.getCombatManager().getCount() == 160) {
                this.main.getCombatManager().setCount(0);
            }

            if (this.main.getCooldownManager().isOnCooldown(player)) {
                int count = this.main.getCooldownManager().getCooldownTime(player);
                count--;

                this.main.getCooldownManager().setCooldownTime(player, count);

                if (count == 0) {
                    this.main.getCooldownManager().getCooldownSet().remove(player);
                    this.main.getCooldownManager().getCooldownTimeMap().remove(player);

                    player.setLevel(0);
                    player.setExp(0);
                    player.sendMessage(Utils.translate("&aYou can use your ability again"));
                }
            }

            PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

            if (this.main.getKitManager().getKitByName(playerData.getCurrentKitName()) != null) {
                Kit kit = this.main.getKitManager().getKitByName(playerData.getCurrentKitName());

                if (kit.getAbility() != null && this.main.getCooldownManager().getCooldownCount() == kit.getAbility().getCooldown()) {
                    this.main.getCooldownManager().setCooldownCount(0);
                }
            }

            if (this.main.getCooldownManager().isOnRefillCooldown(player)) {
                int count = this.main.getCooldownManager().getRefillTime(player);
                count--;

                this.main.getCooldownManager().setRefillTime(player, count);

                if (count == 0) {
                    this.main.getCooldownManager().getRefillSet().remove(player);
                    this.main.getCooldownManager().getRefillTimeMap().remove(player);

                    player.sendMessage(Utils.translate("&aYou can refill your " + /*(playerData.isPotions() ? "potions" :*/ "soups"))/*)*/;
                }
            }

            if (this.main.getCooldownManager().getRefillCount() == 60) {
                this.main.getCooldownManager().setRefillCount(0);
            }
        }
    }
}
