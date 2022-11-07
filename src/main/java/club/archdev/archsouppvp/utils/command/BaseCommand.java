package club.archdev.archsouppvp.utils.command;

import club.archdev.archsouppvp.ArchSoupPvP;

public abstract class BaseCommand {

    public BaseCommand() {
        ArchSoupPvP.getInstance().getCommandFramework().registerCommands(this, null);
    }

    public abstract void executeAs(CommandArguments command);
}
