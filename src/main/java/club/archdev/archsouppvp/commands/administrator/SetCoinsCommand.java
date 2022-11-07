package club.archdev.archsouppvp.commands.administrator;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.utils.Utils;
import club.archdev.archsouppvp.utils.command.BaseCommand;
import club.archdev.archsouppvp.utils.command.Command;
import club.archdev.archsouppvp.utils.command.CommandArguments;
import org.bukkit.entity.Player;

public class SetCoinsCommand extends BaseCommand {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    @Command(name = "setcoins", aliases = "setbalance", permission = "archsouppvp.admin", usage = "&cUsage: /setcoins <player> <amount>")
    @Override
    public void executeAs(CommandArguments command) {
        Player player = command.getPlayer();

        String[] args = command.getArgs();

        if (args.length < 2) {
            player.sendMessage(Utils.translate(command.getCommand().getUsage()));

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

        int amount = Integer.parseInt(args[1]);

        targetData.setCoins(amount);

        player.sendMessage(Utils.translate("&aYou have successfully set &a&l" + target.getName() + "&a's coins to " + amount));
    }
}
