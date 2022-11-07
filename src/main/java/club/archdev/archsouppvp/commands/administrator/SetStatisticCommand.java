package club.archdev.archsouppvp.commands.administrator;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.utils.Utils;
import club.archdev.archsouppvp.utils.command.BaseCommand;
import club.archdev.archsouppvp.utils.command.Command;
import club.archdev.archsouppvp.utils.command.CommandArguments;
import org.bukkit.entity.Player;

public class SetStatisticCommand extends BaseCommand {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    @Command(name = "setstatistic", aliases = "setstat", permission = "archsouppvp.admin", usage = "&cUsage: /setstatistic <player> <kills/deaths> <amount>")
    @Override
    public void executeAs(CommandArguments command) {
        Player player = command.getPlayer();

        String[] args = command.getArgs();

        if (args.length < 3) {
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

        int amount = Integer.parseInt(args[2]);

        switch (args[1].toLowerCase()) {
            case "kills":
                targetData.setKills(amount);

                break;
            case "deaths":
                targetData.setDeaths(amount);

                break;
            default:
                player.sendMessage(Utils.translate("&c" + command.getCommand().getUsage()));

                break;
        }

        player.sendMessage(Utils.translate("&aSuccessfully changed " + target.getName() + "'s " + args[1] + " amount to " + amount));
    }
}