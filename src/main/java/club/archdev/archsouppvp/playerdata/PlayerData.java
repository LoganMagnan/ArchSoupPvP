package club.archdev.archsouppvp.playerdata;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.PlayerDataManager;
import club.archdev.archsouppvp.playerdata.currentgame.PlayerCurrentGameData;
import club.archdev.archsouppvp.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
public class PlayerData {

    private PlayerDataManager playerDataManager = ArchSoupPvP.getInstance().getPlayerDataManager();
    private PlayerState playerState = PlayerState.SPAWN;

    private PlayerSettings playerSettings = new PlayerSettings();
    private PlayerCurrentGameData currentGameData = new PlayerCurrentGameData();

    private final UUID uniqueId;
    private boolean loaded;

    private String currentKitName;
    private List<String> unlockedKits = new ArrayList<>();

    private double xp;
    private int level;
    private int kills;
    private int deaths;
    private int bounty;
    private int coins;
    private int currentKillstreak;
    private int highestKillstreak;

    public PlayerData(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.loaded = false;

        this.playerDataManager.loadPlayerData(this);
    }

    public void addRandomXp(Player player) {
        double xp = ThreadLocalRandom.current().nextDouble(0.01, 0.05);

        this.xp += xp;

        player.sendMessage(Utils.translate("&b&l+" + ((int) (xp * 100)) + "&b&l% XP"));

        if (this.xp >= 1) {
            this.level += 1;
            this.xp = this.xp - (long) this.xp;
        }
    }

    public boolean isInSpawn() {
        return (this.playerState == PlayerState.SPAWN);
    }
}
