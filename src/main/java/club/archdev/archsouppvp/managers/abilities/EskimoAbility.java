package club.archdev.archsouppvp.managers.abilities;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.playerdata.PlayerState;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EskimoAbility extends Ability {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public EskimoAbility() {
        super("Eskimo", new ItemBuilder(Material.PACKED_ICE).name("&bIce summoner").lore(Arrays.asList(
                "&7&oRight click to spawn",
                "&7&oa sphere of packed ice around you"
        )).build());
    }

    @Override
    public AbilityCallable getAbilityCallable() {
        return player -> {
            PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

            if (playerData.getPlayerState() == PlayerState.SPAWN) {
                return;
            }

            if (this.main.getCooldownManager().isOnCooldown(player)) {
                player.sendMessage(Utils.translate("&cYou have to wait &c&l" + this.main.getCooldownManager().getCooldownTime(player) + " seconds &cto use the ability again"));

                return;
            }

            if (player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.AIR) {
                player.sendMessage(Utils.translate("&cYou have to be standing on the floor to use the ability"));

                return;
            }

            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 0));

            for (Location location : this.makeSphere(player.getLocation(), 4, true)) {
                if (location.getBlock().getType() == Material.AIR) {
                    this.main.getServer().getScheduler().runTaskLater(this.main, () -> location.getBlock().setType(Material.AIR), 140);

                    location.getBlock().setType(Material.PACKED_ICE);
                }
            }

            this.main.getCooldownManager().setCooldownSet(player, true, this.getCooldown());
            this.main.getCooldownManager().setCooldownTime(player, this.getCooldown());
        };
    }

    @Override
    public int getCooldown() {
        return 30;
    }

    public Set<Location> makeSphere(Location location, int radius, boolean hollow) {
        Set<Location> blocks = new HashSet<>();
        World world = location.getWorld();
        int X = location.getBlockX();
        int Y = location.getBlockY();
        int Z = location.getBlockZ();
        int radiusSquared = radius * radius;
        if (hollow) {
            for (int i = X - radius; i <= X + radius; i++) {
                for (int y = Y - radius; y <= Y + radius; y++) {
                    for (int z = Z - radius; z <= Z + radius; z++) {
                        if ((X - i) * (X - i) + (Y - y) * (Y - y) + (Z - z) * (Z - z) <= radiusSquared) {
                            Location block = new Location(world, i, y, z);
                            blocks.add(block);
                        }
                    }
                }
            }
            return makeHollow(blocks, true);
        }
        for (int x = X - radius; x <= X + radius; x++) {
            for (int y = Y - radius; y <= Y + radius; y++) {
                for (int z = Z - radius; z <= Z + radius; z++) {
                    if ((X - x) * (X - x) + (Y - y) * (Y - y) + (Z - z) * (Z - z) <= radiusSquared) {
                        Location block = new Location(world, x, y, z);
                        blocks.add(block);
                    }
                }
            }
        }
        return blocks;
    }

    public Set<Location> makeHollow(Set<Location> blocks, boolean sphere) {
        Set<Location> edge = new HashSet<>();
        if (!sphere) {
            for (Location l : blocks) {
                World w = l.getWorld();
                int X = l.getBlockX();
                int Y = l.getBlockY();
                int Z = l.getBlockZ();
                Location front = new Location(w, (X + 1), Y, Z);
                Location back = new Location(w, (X - 1), Y, Z);
                Location left = new Location(w, X, Y, (Z + 1));
                Location right = new Location(w, X, Y, (Z - 1));
                if (!blocks.contains(front) || !blocks.contains(back) || !blocks.contains(left) || !blocks.contains(right))
                    edge.add(l);
            }
            return edge;
        }
        for (Location l : blocks) {
            World w = l.getWorld();
            int X = l.getBlockX();
            int Y = l.getBlockY();
            int Z = l.getBlockZ();
            Location front = new Location(w, (X + 1), Y, Z);
            Location back = new Location(w, (X - 1), Y, Z);
            Location left = new Location(w, X, Y, (Z + 1));
            Location right = new Location(w, X, Y, (Z - 1));
            Location top = new Location(w, X, (Y + 1), Z);
            Location bottom = new Location(w, X, (Y - 1), Z);
            if (!blocks.contains(front) || !blocks.contains(back) || !blocks.contains(left) || !blocks.contains(right) || !blocks.contains(top) || !blocks.contains(bottom))
                edge.add(l);
        }
        return edge;
    }
}
