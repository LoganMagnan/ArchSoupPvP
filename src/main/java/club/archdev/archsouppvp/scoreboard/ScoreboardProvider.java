package club.archdev.archsouppvp.scoreboard;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.playerdata.PlayerState;
import club.archdev.archsouppvp.utils.Utils;
import club.archdev.archsouppvp.utils.aether.scoreboard.Board;
import club.archdev.archsouppvp.utils.aether.scoreboard.BoardAdapter;
import club.archdev.archsouppvp.utils.aether.scoreboard.cooldown.BoardCooldown;
import org.apache.commons.lang3.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ScoreboardProvider implements BoardAdapter {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    @Override
    public String getTitle(Player player) {
        return Utils.translate("&bArchSoupPvP");
    }

    @Override
    public List<String> getScoreboard(Player player, Board board, Set<BoardCooldown> cooldowns) {
        PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

        if (playerData == null) {
            return null;
        }

        if (!playerData.getPlayerSettings().isScoreboardEnabled()) {
            return null;
        }

        return this.getScoreboard(player, playerData);
    }

    @Override
    public void onScoreboardCreate(Player player, Scoreboard scoreboard) {
        PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

        if (scoreboard != null) {
            Team red = scoreboard.getTeam("red");
            if (red == null) {
                red = scoreboard.registerNewTeam("red");
            }

            Team green = scoreboard.getTeam("green");
            if (green == null) {
                green = scoreboard.registerNewTeam("green");
            }

            red.setPrefix(String.valueOf(ChatColor.RED));
            green.setPrefix(String.valueOf(ChatColor.GREEN));

            if (playerData.getPlayerState() != PlayerState.PLAYING) {
                Objective objective = player.getScoreboard().getObjective(DisplaySlot.BELOW_NAME);
                if (objective != null) {
                    objective.unregister();
                }

                for (String entry : red.getEntries()) {
                    red.removeEntry(entry);
                }

                for (String entry : green.getEntries()) {
                    green.removeEntry(entry);
                }

                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online == null) return;

                    Team spawn = scoreboard.getTeam(online.getName());
                    if (spawn == null) {
                        spawn = scoreboard.registerNewTeam(online.getName());
                    }

                    String onlinePlayer = online.getName();
                    if (spawn.hasEntry(onlinePlayer)) {
                        continue;
                    }
                    spawn.addEntry(onlinePlayer);

                    return;
                }

                return;
            }

            Objective objective = player.getScoreboard().getObjective(DisplaySlot.BELOW_NAME);

            if (objective == null) {
                objective = player.getScoreboard().registerNewObjective("showhealth", "health");
            }

            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            objective.setDisplayName(ChatColor.RED + StringEscapeUtils.unescapeJava("\u2764"));
            objective.getScore(player.getName()).setScore((int) Math.floor(player.getHealth()));

//            if (playerData.getBounty() > 0) {
//                Objective bountyObjective = player.getScoreboard().getObjective(DisplaySlot.BELOW_NAME);
//
//                if (bountyObjective == null) {
//                    bountyObjective = player.getScoreboard().registerNewObjective("bounty", "bounty");
//                }
//
//                bountyObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);
//                bountyObjective.setDisplayName(ChatColor.GOLD + "Bounty: " + ChatColor.YELLOW + playerData.getBounty());
//            }
        }
    }

    private List<String> getScoreboard(Player player, PlayerData playerData) {
        List<String> lines = new ArrayList<>();
        lines.add(Utils.scoreboardBar);
        lines.add("&fKills: &b" + playerData.getKills());
        lines.add("&fKillstreak: &b" + playerData.getCurrentKillstreak());
        lines.add("&fDeaths: &b" + playerData.getDeaths());
        lines.add("&fCoins: &b" + playerData.getCoins());

        if (this.main.getCombatManager().isInCombat(player.getUniqueId())) {
            lines.add("&cCombat tag: &f" + this.main.getCombatManager().getCombatTime(player.getUniqueId()) + "&fs");
        }

        lines.add("");
        lines.add("&barchdev.club");
        lines.add(Utils.scoreboardBar);

        return Utils.translate(lines);
    }
}
