package club.archdev.archsouppvp.managers.abilities;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.playerdata.PlayerState;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ShurikenAbility extends Ability {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    private Map<Player, Pair<Item, BukkitTask>> shuriken = new HashMap<Player, Pair<Item, BukkitTask>>();

    public ShurikenAbility() {
        super("Shuriken", new ItemBuilder(Material.NETHER_STAR).name(Utils.translate("&bShuriken")).lore(Arrays.asList(
                "&7&oRight click to throw a shuriken",
                "&7&owhich makes people not be able to see"
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

            throwShuriken(player);
            ItemStack item = player.getItemInHand();
            if (item.getAmount() == 1) {
                player.setItemInHand(new ItemStack(Material.AIR));
            } else {
                item.setAmount(item.getAmount() - 1);
                player.setItemInHand(item);
            }
            player.updateInventory();
            (new BukkitRunnable() {
                public void run() {
                    for (Map.Entry entry : shuriken.entrySet()) {
                        Pair pair = (Pair)entry.getValue();
                        Item item = (Item)pair.getLeft();
                        Player target = (Player)entry.getKey();
                        Iterator<Entity> iterator = item.getNearbyEntities(1.0D, 1.0D, 1.0D).iterator();
                        if (iterator.hasNext()) {
                            Entity entity = iterator.next();
                            if (!(entity instanceof Player))
                                return;
                            Player p = (Player)entity;
                            if (p == target)
                                return;
                            if (!p.hasLineOfSight((Entity)item))
                                return;
                            if (p.getGameMode() != GameMode.SURVIVAL)
                                return;
                            if (ShurikenAbility.this.hitPlayer(target, p))
                                ShurikenAbility.this.removeShuriken(target);
                        }
                    }
                }
            }).runTaskTimer(this.main, 1, 1);
        };
    }

    @Override
    public int getCooldown() {
        return 10;
    }

    public void throwShuriken(Player player) {
        Item item = player.getWorld().dropItem(player.getLocation().clone().add(0.0D, 1.0D, 0.0D), new ItemStack(Material.NETHER_STAR));
        item.setPickupDelay(2147483647);
        item.setVelocity(player.getEyeLocation().getDirection().multiply(1.5D));
        this.main.getCooldownManager().setCooldownSet(player, true, getCooldown());
        this.main.getCooldownManager().setCooldownTime(player, getCooldown());
        this.shuriken.put(player, Pair.of(item, this.main.getServer().getScheduler().runTaskLaterAsynchronously(this.main, () -> removeShuriken(player), 60L)));
    }

    public void removeShuriken(Player player) {
        if (this.shuriken.containsKey(player)) {
            Pair<Item, BukkitTask> pair = this.shuriken.remove(player);
            ((Item)pair.getLeft()).remove();
            ((BukkitTask)pair.getRight()).cancel();
        }
    }

    public boolean hitPlayer(Player player, Player target) {
        target.getWorld().playSound(player.getLocation(), Sound.ANVIL_LAND, 20.0F, 10.0F);
        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
        target.damage(2.0D, player);
        return true;
    }
}
