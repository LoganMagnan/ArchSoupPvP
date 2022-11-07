package club.archdev.archsouppvp.commands.player;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.utils.command.BaseCommand;
import club.archdev.archsouppvp.utils.command.Command;
import club.archdev.archsouppvp.utils.command.CommandArguments;
import org.bukkit.entity.Player;

public class SpawnCommand extends BaseCommand {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    @Command(name = "spawn")
    @Override
    public void executeAs(CommandArguments command) {
        Player player = command.getPlayer();

        String[] args = command.getArgs();

        this.main.getSpawnManager().getPlayers().put(player.getUniqueId(), 5);
    }
}
