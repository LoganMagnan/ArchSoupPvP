package club.archdev.archsouppvp.managers;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

@Getter
@Setter
public class CombatManager {

    private ArchSoupPvP plugin = ArchSoupPvP.getInstance();

    private Set<UUID> combatSet = new HashSet<UUID>();

    private Map<UUID, Integer> timeMap = new HashMap<UUID, Integer>();
    private Map<Player, Map<Player, BukkitTask>> assists = new HashMap<Player, Map<Player, BukkitTask>>();
    private Map<Player, Pair<Boolean, DamageModifier>> fallDamageModifiers = new HashMap<Player, Pair<Boolean, DamageModifier>>();
    private Map<Player, BukkitTask> fallDamageTasks = new HashMap<Player, BukkitTask>();

    private int count = 0;

    public void setCombatSet(UUID player, boolean inSpawn) {
        if (inSpawn) {
            this.combatSet.add(player);
            this.timeMap.put(player, 16);
        } else {
            this.combatSet.remove(player);
            this.timeMap.remove(player);
        }
    }

    public void setCombatTime(UUID player, int time) {
        this.timeMap.remove(player);
        this.timeMap.put(player, time);
    }

    public boolean isInCombat(UUID player) {
        return this.combatSet.contains(player);
    }

    public int getCombatTime(UUID player) {
        return this.timeMap.get(player);
    }

    public void cancelNextFallDamage(Player player, int ticks) {
        setNextFallDamage(player, damage -> 0.0D, ticks);
    }

    public void setNextFallDamage(Player player, DamageModifier modifier, int ticks) {
        setNextFallDamage(player, modifier, ticks, true);
    }

    public void setNextFallDamage(Player player, DamageModifier modifier, int ticks, boolean reset) {
        removeFallProtection(player);
        this.fallDamageModifiers.put(player, Pair.of(reset, modifier));
        if (ticks > 0)
            this.fallDamageTasks.put(player, Bukkit.getScheduler().runTaskLaterAsynchronously(this.plugin, () -> {
                this.fallDamageTasks.remove(player);
                this.fallDamageModifiers.remove(player);
            }, ticks));
    }

    public void removeFallProtection(Player player) {
        this.fallDamageModifiers.remove(player);
        if (this.fallDamageTasks.containsKey(player))
            ((BukkitTask)this.fallDamageTasks.remove(player)).cancel();
    }

    public static interface DamageModifier {
        double modify(double param1Double);
    }
}