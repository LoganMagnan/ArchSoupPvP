package club.archdev.archsouppvp.managers;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.utils.CustomLocation;
import club.archdev.archsouppvp.utils.Utils;
import club.archdev.archsouppvp.utils.cuboid.Cuboid;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class SpawnManager {

    private ArchSoupPvP plugin = ArchSoupPvP.getInstance();
    private FileConfiguration config = this.plugin.getMainConfig().getConfig();

    private CustomLocation spawnLocation;
    private CustomLocation spawnMin;
    private CustomLocation spawnMax;

    private Cuboid cuboid;

    private Map<UUID, Integer> players = new HashMap<UUID, Integer>();

    public SpawnManager() {
        this.loadConfig();
    }

    private void loadConfig() {
        if (this.config.contains("spawn.location")) {
            try {
                this.spawnLocation = CustomLocation.stringToLocation(this.config.getString("spawn.location"));
                this.spawnMin = CustomLocation.stringToLocation(this.config.getString("spawn.min"));
                this.spawnMax = CustomLocation.stringToLocation(this.config.getString("spawn.max"));
                this.cuboid = new Cuboid(this.spawnMin.toBukkitLocation(), this.spawnMax.toBukkitLocation());
            } catch (NullPointerException exception) {
                Bukkit.getConsoleSender().sendMessage(Utils.translate("&cSpawn min & max locations not found!"));
            }
        }
    }

    public void saveConfig() {
        this.config.set("spawn.location", CustomLocation.locationToString(this.spawnLocation));
        this.config.set("spawn.min", CustomLocation.locationToString(this.spawnMin));
        this.config.set("spawn.max", CustomLocation.locationToString(this.spawnMax));
        this.plugin.getMainConfig().save();
    }
}
