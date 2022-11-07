package club.archdev.archsouppvp.commands.player;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.utils.Utils;
import club.archdev.archsouppvp.utils.command.BaseCommand;
import club.archdev.archsouppvp.utils.command.Command;
import club.archdev.archsouppvp.utils.command.CommandArguments;
import org.bukkit.entity.Player;

public class CoinsCommand extends BaseCommand {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    @Command(name = "coins", aliases = "balance", usage = "&cUsage: /coins <player> <amount>")
    @Override
    public void executeAs(CommandArguments command) {
        Player player = command.getPlayer();

        String[] args = command.getArgs();

        PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

        if (args.length == 0) {
            player.sendMessage(Utils.translate("&eCoins: &b" + playerData.getCoins()));

            return;
        }

        Player target = this.main.getServer().getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(Utils.translate("&cThere are no players with the name '" + args[0] + "' online"));

            return;
        }

        PlayerData targetData = this.main.getPlayerDataManager().getPlayerData(target.getUniqueId());

        if (targetData == null) {
            return;
        }

        player.sendMessage(Utils.translate("&e" + target.getName() + "'s coins: &b" + targetData.getCoins()));
    }
}
