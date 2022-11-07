package club.archdev.archsouppvp.managers.killstreaks;

import club.archdev.archsouppvp.playerdata.PlayerData;
import org.bukkit.entity.Player;

public interface KillstreaksCallable {

    void executeAs(Player player, PlayerData playerData);
}
