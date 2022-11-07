package club.archdev.archsouppvp;

import club.archdev.archsouppvp.managers.CombatManager;
import club.archdev.archsouppvp.managers.CooldownManager;
import club.archdev.archsouppvp.managers.PlayerDataManager;
import club.archdev.archsouppvp.managers.SpawnManager;
import club.archdev.archsouppvp.managers.abilities.AbilityManager;
import club.archdev.archsouppvp.managers.hotbar.HotbarManager;
import club.archdev.archsouppvp.managers.killstreaks.KillstreakManager;
import club.archdev.archsouppvp.managers.kits.KitManager;
import club.archdev.archsouppvp.managers.mongo.MongoManager;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.runnables.BountyRunnable;
import club.archdev.archsouppvp.runnables.CombatTagRunnable;
import club.archdev.archsouppvp.runnables.ExperienceRunnable;
import club.archdev.archsouppvp.runnables.SpawnRunnable;
import club.archdev.archsouppvp.scoreboard.ScoreboardProvider;
import club.archdev.archsouppvp.utils.ClassRegistrationUtils;
import club.archdev.archsouppvp.utils.Utils;
import club.archdev.archsouppvp.utils.aether.Aether;
import club.archdev.archsouppvp.utils.command.CommandFramework;
import club.archdev.archsouppvp.utils.config.file.Config;
import club.archdev.archsouppvp.utils.menusystem.PlayerMenuUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class ArchSoupPvP extends JavaPlugin {

    @Getter private static ArchSoupPvP instance;

    private Config mainConfig;

    private MongoManager mongoManager;
    private PlayerDataManager playerDataManager;
    private SpawnManager spawnManager;
    private CombatManager combatManager;
    private HotbarManager hotbarManager;
    private KitManager kitManager;
    private AbilityManager abilityManager;
    private KillstreakManager killstreakManager;
    private CooldownManager cooldownManager;

    private CommandFramework commandFramework = new CommandFramework(this);

    private HashMap<Player, PlayerMenuUtil> playerMenuUtilMap = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        this.saveDefaultConfig();

        this.mainConfig = new Config("config", this);

        Bukkit.getConsoleSender().sendMessage("------------------------------------------------");
        Bukkit.getConsoleSender().sendMessage(Utils.translate("&dArchSoupPvP &8- &av" + this.getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(Utils.translate("&7Made by &eTrixkz"));
        Bukkit.getConsoleSender().sendMessage("------------------------------------------------");

        this.loadCommands();
        this.loadManagers();
        this.loadListeners();
        this.loadRunnables();

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ITEM_FRAME) {
                    entity.remove();
                }
            }

            world.setGameRuleValue("doDaylightCycle", "false");
            world.setTime(0L);
            world.setStorm(false);
        }
    }

    @Override
    public void onDisable() {
        instance = null;

        for (PlayerData playerData : this.playerDataManager.getAllPlayers()) {
            this.playerDataManager.savePlayerData(playerData);
        }

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getType() == EntityType.DROPPED_ITEM) {
                    entity.remove();
                }
            }

            for (Chunk chunk : world.getLoadedChunks()) {
                chunk.unload(true);
            }
        }

        this.mongoManager.disconnect();
    }

    private void loadCommands() {
        ClassRegistrationUtils.loadCommands("club.archdev.archsouppvp.commands");
    }

    private void loadManagers() {
        this.mongoManager = new MongoManager();
        this.playerDataManager = new PlayerDataManager();
        this.spawnManager = new SpawnManager();
        this.combatManager = new CombatManager();
        this.hotbarManager = new HotbarManager();
        this.kitManager = new KitManager();
        this.abilityManager = new AbilityManager();
        this.killstreakManager = new KillstreakManager();
        this.cooldownManager = new CooldownManager();
    }

    private void loadListeners() {
        ClassRegistrationUtils.loadListeners("club.archdev.archsouppvp.listeners");
    }

    private void loadRunnables() {
        new Aether(this, new ScoreboardProvider());
        new SpawnRunnable().runTaskTimer(this, 0, 20);
        new CombatTagRunnable().runTaskTimer(this, 0, 20);

        this.getServer().getScheduler().runTaskTimerAsynchronously(this, new BountyRunnable(), 0, 6000);
        this.getServer().getScheduler().runTaskTimerAsynchronously(this, new ExperienceRunnable(), 0, 20);
    }

    public PlayerMenuUtil getPlayerMenuUtil(Player player) {
        PlayerMenuUtil playerMenuUtil;

        if (playerMenuUtilMap.containsKey(player)) {
            return playerMenuUtilMap.get(player);
        } else {
            playerMenuUtil = new PlayerMenuUtil(player);

            playerMenuUtilMap.put(player, playerMenuUtil);

            return playerMenuUtil;
        }
    }
}
