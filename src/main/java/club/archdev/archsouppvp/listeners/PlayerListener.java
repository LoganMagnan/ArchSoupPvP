package club.archdev.archsouppvp.listeners;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.CombatManager;
import club.archdev.archsouppvp.managers.killstreaks.Killstreak;
import club.archdev.archsouppvp.managers.kits.Kit;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.playerdata.PlayerState;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import club.archdev.archsouppvp.utils.WorldUtils;
import club.archdev.archsouppvp.utils.menu.refill.RefillMenu;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerListener implements Listener {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Snowball)) {
            Snowball snowball = (Snowball) event.getDamager();

            Player shooter = (Player) snowball.getShooter();

            if (shooter == null) {
                return;
            }

            PlayerData shooterData = this.main.getPlayerDataManager().getPlayerData(shooter.getUniqueId());

            if (!shooterData.getCurrentKitName().equalsIgnoreCase("Switcher")) {
                return;
            }

            Player target = (Player) event.getEntity();

            Location shooterLocation = shooter.getLocation();
            Location targetLocation = target.getLocation();

            shooter.teleport(targetLocation);
            target.teleport(shooterLocation);
            shooter.getWorld().playSound(shooter.getLocation(), Sound.PISTON_RETRACT, 20F, 10F);
            target.getWorld().playSound(target.getLocation(), Sound.PISTON_RETRACT, 20F, 10F);

            this.main.getAbilityManager().getAbilityByName("Switcher").getAbilityCallable().executeAs(shooter);

            return;
        }

        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            return;
        }

        Player victim = (Player) event.getEntity();
        Player killer = (Player) event.getDamager();

        PlayerData victimData = this.main.getPlayerDataManager().getPlayerData(victim.getUniqueId());
        PlayerData killerData = this.main.getPlayerDataManager().getPlayerData(killer.getUniqueId());

        if (!victim.canSee(killer) && killer.canSee(victim)) {
            event.setCancelled(true);

            return;
        }

        if (this.main.getSpawnManager().getCuboid().isInside(victim) && this.main.getSpawnManager().getCuboid().isInside(killer) || !this.main.getSpawnManager().getCuboid().isInside(victim) && this.main.getSpawnManager().getCuboid().isInside(killer) || this.main.getSpawnManager().getCuboid().isInside(victim) && !this.main.getSpawnManager().getCuboid().isInside(killer)) {
            event.setCancelled(true);

            return;
        }

        this.main.getCombatManager().setCombatTime(victim.getUniqueId(), 16);
        this.main.getCombatManager().setCombatSet(victim.getUniqueId(), true);
        this.main.getCombatManager().setCombatTime(killer.getUniqueId(), 16);
        this.main.getCombatManager().setCombatSet(killer.getUniqueId(), true);

        switch (killerData.getCurrentKitName()) {
            case "Snail":
                if (Math.random() >= 0.40D) {
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 0));
                }

                break;
            case "Viper":
                if (Math.random() >= 0.30D) {
                    victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 0));
                }

                break;
            case "Monk":
                if (this.main.getCooldownManager().isOnCooldown(killer)) {
                    killer.sendMessage(Utils.translate("&cYou have to wait &c&l" + this.main.getCooldownManager().getCooldownTime(killer) + " seconds &cto use the ability again"));

                    return;
                }

                if (!killer.getItemInHand().isSimilar(this.main.getAbilityManager().getAbilityByName("Monk").getMaterial())) {
                    return;
                }

                if (victim.isDead()) {
                    return;
                }

                if (this.switchItem(victim, killer)) {
                    this.main.getAbilityManager().getAbilityByName("Monk").getAbilityCallable().executeAs(killer);
                }

                break;
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

        switch (playerData.getPlayerState()) {
            case SPAWN:
                break;
            case PLAYING:
                if (this.main.getSpawnManager().getCuboid().isInside(player)) {
                    event.setCancelled(true);

                    return;
                }

                if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    double damage = 0;

                    List<Entity> nearbyEntities = new ArrayList<Entity>();

                    if (this.main.getCombatManager().getFallDamageTasks().containsKey(player)) {
                        event.setCancelled(true);

                        Pair<Boolean, CombatManager.DamageModifier> pair = (Pair<Boolean, CombatManager.DamageModifier>) this.main.getCombatManager().getFallDamageModifiers().get(player);

                        if (pair.getLeft()) {
                            this.main.getCombatManager().removeFallProtection(player);
                        }
                    }

                    switch (playerData.getCurrentKitName()) {
                        case "Stomper":
                            damage = event.getDamage() * 2.20D;

                            nearbyEntities = player.getNearbyEntities(5, 5, 5);

                            for (Entity entity : nearbyEntities) {
                                if (!(entity instanceof Player)) {
                                    continue;
                                }

                                Player target = (Player) entity;

                                if (player.getLocation().getBlockY() >= target.getLocation().getBlockY()) {
                                    if (target.isSneaking()) {
                                        if (damage / 5.00D >= 10.00D) {
                                            target.damage(10, player);
                                        } else {
                                            target.damage(damage / 4.5, player);
                                        }
                                    } else {
                                        target.damage(damage, player);
                                    }

                                    player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 2F, 2F);
                                }
                            }

                            break;
                    }
                }
                break;
            case EVENT:
                break;
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity().getPlayer();

        PlayerData victimData = this.main.getPlayerDataManager().getPlayerData(victim.getUniqueId());

        event.setDeathMessage(null);
        event.getDrops().removeIf(itemStack -> itemStack.getType() != Material.MUSHROOM_SOUP);

        if (event.getEntity().getKiller() != null) {
            Player killer = event.getEntity().getKiller();

            PlayerData killerData = this.main.getPlayerDataManager().getPlayerData(killer.getUniqueId());

            ItemStack ninjaStar = null;
            ItemStack snowball = null;
            ItemStack harmingPotion = null;

            switch (killerData.getCurrentKitName()) {
                case "Copy cat":
                    this.main.getKitManager().getKitByName(victimData.getCurrentKitName()).useKit(killer);

                    killerData.setCurrentKitName(victimData.getCurrentKitName());

                    break;
                case "Pro":
                    killerData.setCoins(killerData.getCoins() + 10);

                    break;
                case "Ninja":
                    ninjaStar = new ItemStack(this.main.getAbilityManager().getAbilityByName("Shuriken").getMaterial().clone());
                    ninjaStar.setAmount(1);

                    killer.getInventory().addItem(ninjaStar);

                    for (ItemStack itemStack : killer.getInventory().getArmorContents()) {
                        if (itemStack != null && itemStack.getItemMeta() instanceof Repairable) {
                            itemStack.setDurability((short) (itemStack.getDurability() - 10));
                        }
                    }

                    break;
                case "Eskimo":
                    for (ItemStack itemStack : killer.getInventory().getArmorContents()) {
                        if (itemStack != null && itemStack.getItemMeta() instanceof Repairable) {
                            itemStack.setDurability((short) (itemStack.getDurability() - 10));
                        }
                    }

                    break;
                case "Vampire":
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 4));

                    break;
                case "Switcher":
                    snowball = new ItemStack(this.main.getAbilityManager().getAbilityByName("Switcher").getMaterial().clone());
                    snowball.setAmount(1);

                    killer.getInventory().addItem(snowball);

                    break;
                case "Palioxis":
                    killer.removePotionEffect(PotionEffectType.SPEED);
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 2));

                    this.main.getServer().getScheduler().runTaskLater(this.main, () -> killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1)), 301);

                    for (ItemStack itemStack : killer.getInventory().getArmorContents()) {
                        if (itemStack != null && itemStack.getItemMeta() instanceof Repairable) {
                            itemStack.setDurability((short) (itemStack.getDurability() - 10));
                        }
                    }

                    if (!killer.getInventory().contains(Material.ENDER_PEARL)) {
                        killer.getInventory().setItem(1, this.main.getAbilityManager().getAbilityByName("Palioxis").getMaterial());
                    }

                    break;
                case "Chemist":
                    harmingPotion = new ItemBuilder(Material.POTION).amount(1).durability(16428).build();

                    killer.getInventory().addItem(harmingPotion);

                    break;
            }

            victim.sendMessage(Utils.translate("&cYou have been killed by &a" + killer.getName()));
            killer.sendMessage(Utils.translate("&eYou have killed &d" + victim.getName() + " &e for &d" + (killerData.getCurrentKitName().equalsIgnoreCase("Pro") ? 20 : 10) + " &ecoins"));

            victimData.setPlayerState(PlayerState.SPAWN);
            victimData.setDeaths(victimData.getDeaths() + 1);
            victimData.setCurrentKillstreak(0);
            killerData.setKills(killerData.getKills() + 1);
            killerData.setCurrentKillstreak(killerData.getCurrentKillstreak() + 1);

            if (victimData.getBounty() > 0) {
                killerData.setCoins(killerData.getCoins() + victimData.getBounty());

                this.main.getServer().broadcastMessage(Utils.translate("&8[&6&lBounty&8] &d" + killer.getName() + " &ehas taken &d" + victim.getName() + "&e's bounty for &d" + victimData.getBounty() + " &ecoins"));

                victimData.setBounty(0);
            }

            for (Killstreak killstreak : this.main.getKillstreakManager().getKillstreaks()) {
                if (killstreak.getKillstreak() == killerData.getCurrentKillstreak()) {
                    killstreak.getKillstreaksCallable().executeAs(killer, killerData);
                }
            }

            if (killerData.getCurrentKillstreak() > killerData.getHighestKillstreak()) {
                killerData.setHighestKillstreak(killerData.getCurrentKillstreak());
            }
        } else {
            victim.sendMessage(Utils.translate("&cYou have died"));

            victimData.setPlayerState(PlayerState.SPAWN);
            victimData.setDeaths(victimData.getDeaths() + 1);
            victimData.setCurrentKillstreak(0);

            if (victimData.getBounty() > 0) {
                victimData.setBounty(0);
            }
        }

        for (Entity entity : victim.getWorld().getEntities()) {
            if ((entity instanceof Zombie || entity instanceof Wolf || entity instanceof Silverfish) && entity.getCustomName().contains(victim.getName())) {
                entity.remove();
            }
        }

        this.main.getServer().getScheduler().runTaskLater(this.main, () -> {
            victim.spigot().respawn();

            this.main.getPlayerDataManager().sendToSpawnAndResetPlayer(victim);
            this.main.getCombatManager().setCombatSet(victim.getUniqueId(), false);
            this.main.getCooldownManager().setCooldownSet(victim, false, 0);
            this.main.getCooldownManager().setRefillSet(victim, false);
        }, 1L);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        switch (event.getAction()) {
            case RIGHT_CLICK_AIR:
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                if (event.getItem() == null) {
                    return;
                }

                break;
            case RIGHT_CLICK_BLOCK:
                if (event.getClickedBlock().getState() instanceof Sign) {
                    Sign sign = (Sign) event.getClickedBlock().getState();

                    if (sign.getLine(1).equalsIgnoreCase("Refill")) {
                        if (this.main.getCooldownManager().isOnRefillCooldown(player)) {
                            player.sendMessage(Utils.translate("&cYou have to wait &c&l" + this.main.getCooldownManager().getRefillTime(player) + " seconds &cto use the refill sign again"));

                            return;
                        }

                        new RefillMenu().openMenu(player);
                    }
                }

                break;
        }
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

        if (this.main.getSpawnManager().getCuboid().isInside(player)) {
            return;
        }

        switch (playerData.getCurrentKitName()) {
            case "Stomper":
                if (event.isSneaking() && player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.AIR) {
                    Vector vector = player.getVelocity().setY(-5);

                    player.setVelocity(vector);
                }

                break;
        }
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        Player playerCaught = event.getPlayer();

        PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

        Kit kit = this.main.getKitManager().getKitByName(playerData.getCurrentKitName());

        if (kit == null) {
            return;
        }

        if (kit.getAbility() == null) {
            return;
        }

        if (!player.getItemInHand().isSimilar(kit.getAbility().getMaterial())) {
            return;
        }

        if (this.main.getCooldownManager().isOnCooldown(player)) {
            player.sendMessage(Utils.translate("&cYou have to wait &c&l" + this.main.getCooldownManager().getCooldownTime(player) + " seconds &cto use the ability again"));

            return;
        }

        switch (kit.getName()) {
            case "Grappler":
                if (event.getState() == PlayerFishEvent.State.FAILED_ATTEMPT || event.getState() == PlayerFishEvent.State.IN_GROUND) {
                    Location hook = event.getHook().getLocation();
                    hook.getBlock().getLocation().setY(hook.getBlock().getLocation().getY() - 1.0D);
                    Location hookLocation = null;
                    int check = 999;
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            for (int z = -1; z <= 1; z++) {
                                Location to = event.getHook().getLocation().clone().add(x, y, z);
                                int calculate = x * x + y * y + z * z;
                                if (to.getBlock().getType() == Material.WATER || to
                                        .getBlock().getType() == Material.STATIONARY_WATER || to
                                        .getBlock().getType() == Material.LAVA || to
                                        .getBlock().getType() == Material.STATIONARY_LAVA)
                                    return;
                                if (calculate < check) {
                                    hookLocation = to;
                                    check = calculate;
                                }
                            }
                        }
                    }
                    if (hookLocation == null)
                        return;
                    this.pullPlayerToLocation(player, hookLocation, 1.5D, false);
                    player.playSound(player.getLocation(), Sound.ZOMBIE_INFECT, 4.0F, 4.0F);
                    this.main.getAbilityManager().getAbilityByName(kit.getAbility().getName()).getAbilityCallable().executeAs(player);
                }

                break;
            case "Fisherman":
                if (!(event.getCaught() instanceof Player))
                    return;
                playerCaught = (Player) event.getCaught();
                if (playerCaught.equals(player))
                    return;
                playerCaught.teleport(player.getLocation());
                playerCaught.getWorld().playSound(playerCaught.getLocation(), Sound.CAT_PURR, 20.0F, 10.0F);
                this.main.getAbilityManager().getAbilityByName(kit.getAbility().getName()).getAbilityCallable().executeAs(player);

                break;
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if ((!(event.getEntity() instanceof Zombie) && !(event.getEntity() instanceof Wolf) && !(event.getEntity() instanceof Silverfish)) ||
                !(event.getTarget() instanceof Player))
            return;
        Entity entity = event.getEntity();
        Player player = (Player)event.getTarget();
        if (entity.getCustomName().contains(player.getName()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onSoupUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.getItemInHand().getType() == Material.MUSHROOM_SOUP && player.getHealth() < 19.50) {
            player.getItemInHand().setType(Material.BOWL);
            player.setHealth(Math.min(player.getHealth() + 7, 20));
            player.updateInventory();
        }
    }

    @EventHandler
    public void onSoupBowlDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        Material material = event.getItemDrop().getItemStack().getType();

        if (material.name().contains("SWORD") || material.name().contains("AXE")) {
            event.setCancelled(true);
        }

        if (material == Material.BOWL) {
            event.getItemDrop().remove();
        }
    }

    public boolean switchItem(Player victim, Player killer) {
        if (victim == null) {
            return false;
        }

        int random = ThreadLocalRandom.current().nextInt(26);

        ItemStack hand = victim.getItemInHand();
        ItemStack toSwitch = victim.getInventory().getItem(random);

        victim.getInventory().setItem(random, hand);
        victim.setItemInHand(toSwitch);
        victim.getWorld().playSound(victim.getLocation(), Sound.ENDERMAN_SCREAM, 20F, 10F);
        killer.sendMessage(Utils.translate("&aYou have successfully switched items with &a&l" + victim.getName()));

        return true;
    }

    public void pullPlayerToLocation(Player e, Location loc, double multiplier, boolean isPowerful) {
        Location entityLoc = e.getLocation();
        double g = -0.08D;
        double d = loc.distance(entityLoc);
        double v_x = (1.0D + 0.07D * d) * (loc.getX() - entityLoc.getX()) / d;
        double v_y = (1.0D + 0.03D * d) * (loc.getY() - entityLoc.getY()) / d - 0.5D * g * d;
        double v_z = (1.0D + 0.07D * d) * (loc.getZ() - entityLoc.getZ()) / d;
        Vector v = new Vector(v_x * 0.75D, v_y, v_z * 0.75D);
        v.add(new Vector(0.0D, 0.05D, 0.0D));
        if (e.isOnGround() || WorldUtils.isEmptyColumn(e.getLocation()))
            e.teleport(e.getLocation().clone().add(0.0D, 0.5D, 0.0D));
        v = v.multiply(multiplier);
        v_y = v.getY();
        v = v.multiply(multiplier);
        v.setY(v_y);
        e.setVelocity(v);
    }
}
