package club.archdev.archsouppvp.commands.administrator;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.utils.CustomLocation;
import club.archdev.archsouppvp.utils.Utils;
import club.archdev.archsouppvp.utils.command.BaseCommand;
import club.archdev.archsouppvp.utils.command.Command;
import club.archdev.archsouppvp.utils.command.CommandArguments;
import org.bukkit.entity.Player;

public class SetLocationCommand extends BaseCommand {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    @Command(name = "setlocation", aliases = "setloc", permission = "archsouppvp.admin", usage = "&cUsage: /setlocation <spawn/min/max>")
    @Override
    public void executeAs(CommandArguments command) {
        Player player = command.getPlayer();

        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage(Utils.translate(command.getCommand().getUsage()));

            return;
        }

        switch (args[0]) {
            case "spawn":
                this.main.getSpawnManager().setSpawnLocation(CustomLocation.fromBukkitLocation(player.getLocation()));

                saveLocation(player, "spawn.location");

                player.sendMessage(Utils.translate("&aSuccessfully set the spawn location"));

                break;
            case "min":
                this.main.getSpawnManager().setSpawnMin(CustomLocation.fromBukkitLocation(player.getLocation()));

                saveLocation(player, "spawn.min");

                player.sendMessage(Utils.translate("&aSuccessfully set the min location"));

                break;
            case "max":
                this.main.getSpawnManager().setSpawnMax(CustomLocation.fromBukkitLocation(player.getLocation()));

                saveLocation(player, "spawn.max");

                player.sendMessage(Utils.translate("&aSuccessfully set the max location"));

                break;
            default:
                player.sendMessage(Utils.translate(command.getCommand().getUsage()));
                
                break;
        }
    }

    public void saveLocation(Player player, String location) {
        this.main.getMainConfig().getConfig().set(location, CustomLocation.locationToString(CustomLocation.fromBukkitLocation(player.getLocation())));
        this.main.getMainConfig().save();
    }
}