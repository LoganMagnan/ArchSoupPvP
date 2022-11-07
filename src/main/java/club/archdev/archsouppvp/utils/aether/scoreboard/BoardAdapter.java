package club.archdev.archsouppvp.utils.aether.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import club.archdev.archsouppvp.utils.aether.scoreboard.cooldown.BoardCooldown;

import java.util.List;
import java.util.Set;

public interface BoardAdapter {

    String getTitle(Player player);

    List<String> getScoreboard(Player player, Board board, Set<BoardCooldown> cooldowns);

    void onScoreboardCreate(Player player, Scoreboard board);
}
