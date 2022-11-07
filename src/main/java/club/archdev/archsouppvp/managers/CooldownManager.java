package club.archdev.archsouppvp.managers;

import club.archdev.archsouppvp.ArchSoupPvP;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class CooldownManager {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    private Set<Player> cooldownSet = new HashSet<Player>();

    private Map<Player, Integer> cooldownTimeMap = new HashMap<Player, Integer>();

    private Set<Player> refillSet = new HashSet<Player>();

    private Map<Player, Integer> refillTimeMap = new HashMap<Player, Integer>();

    private int cooldownCount = 0;
    private int refillCount = 0;

    public void setCooldownSet(Player player, boolean inSpawn, int number) {
        if (inSpawn) {
            this.cooldownSet.add(player);
            this.cooldownTimeMap.put(player, number);
        } else {
            this.cooldownSet.remove(player);
            this.cooldownTimeMap.remove(player);
        }
    }

    public int getCooldownTime(Player player) {
        return this.cooldownTimeMap.get(player);
    }

    public void setCooldownTime(Player player, int time) {
        this.cooldownTimeMap.remove(player);
        this.cooldownTimeMap.put(player, time);
    }

    public boolean isOnCooldown(Player player) {
        return this.cooldownSet.contains(player);
    }

    public void setRefillSet(Player player, boolean inSpawn) {
        if (inSpawn) {
            this.refillSet.add(player);
            this.refillTimeMap.put(player, 60);
        } else {
            this.refillSet.remove(player);
            this.refillTimeMap.remove(player);
        }
    }

    public int getRefillTime(Player player) {
        return this.refillTimeMap.get(player);
    }

    public void setRefillTime(Player player, int time) {
        this.refillTimeMap.remove(player);
        this.refillTimeMap.put(player, time);
    }

    public boolean isOnRefillCooldown(Player player) {
        return this.refillSet.contains(player);
    }
}
