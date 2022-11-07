 package club.archdev.archsouppvp.utils;

 import org.bukkit.*;
 import org.bukkit.block.Block;
 import org.bukkit.entity.Player;

 import java.util.*;


 public final class WorldUtils
 {
   public static String locationToString(Location l) {
     return l.getWorld().getName() + "," + l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ() + "," + l
       .getPitch() + "," + l.getYaw();
   }








   public static String locationToLegibleString(Location l) {
     return l.getWorld().getName() + " (x:" + l.getBlockX() + ", y:" + l.getBlockY() + ", z:" + l.getBlockZ() + ")";
   }







   public static Location locationFromString(String s) {
     String[] args = s.split(",");
     try {
       World world = Bukkit.getWorld(args[0]);
       return new Location(world, Integer.parseInt(args[1]) + 0.5D, Integer.parseInt(args[2]),
           Integer.parseInt(args[3]) + 0.5D, Float.parseFloat(args[4]), Float.parseFloat(args[5]));
     } catch (Exception e) {
       return null;
     }
   }

   public static String chunkToString(Chunk c) {
     return c.getWorld().getName() + "," + c.getX() + "," + c.getZ();
   }

   public static Chunk chunkFromString(String s) {
     String[] args = s.split(",");
     try {
       World world = Bukkit.getWorld(args[0]);
       return world.getChunkAt(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
     } catch (Exception e) {
       return null;
     }
   }







   public static Block getNearestBlockUnder(Location l) {
     return l.getWorld().getBlockAt(getNearestLocationUnder(l));
   }


   public static Location getNearestLocationUnder(Location l) {
     Location location = new Location(l.getWorld(), l.getBlockX() + 0.5D, l.getBlockY(), l.getBlockZ() + 0.5D);
     while (!location.getBlock().getType().isSolid()) {
       location = location.add(0.0D, -1.0D, 0.0D);
       if (location.getY() < 0.0D) {
         return null;
       }
     }
     return location;
   }












   public static Block getBlockAboveOrBelow(Block block, Material blockType, byte blockData) {
     return getBlockAboveOrBelow(block, blockType, blockData, 1);
   }


   private static Block getBlockAboveOrBelow(Block block, Material blockType, byte blockData, int distance) {
     boolean maxHeightReached = (block.getLocation().getBlockY() + distance > block.getWorld().getMaxHeight() - 1);
     boolean minHeightReached = (block.getLocation().getBlockY() - distance < 1);

     if (maxHeightReached && minHeightReached) {
       return null;
     }

     if (!maxHeightReached) {
       Block blockAbove = block.getWorld().getBlockAt(block.getLocation().add(0.0D, distance, 0.0D));
       if (blockAbove.getType() == blockType && blockAbove.getData() == blockData) {
         return blockAbove;
       }
     }

     if (!minHeightReached) {
       Block blockBelow = block.getWorld().getBlockAt(block.getLocation().subtract(0.0D, distance, 0.0D));
       if (blockBelow.getType() == blockType && blockBelow.getData() == blockData) {
         return blockBelow;
       }
     }

     return getBlockAboveOrBelow(block, blockType, blockData, distance + 1);
   }

   public static boolean isEmptyColumn(Location loc) {
     return isEmptyColumn(loc.getWorld(), loc.getBlockX(), loc.getBlockZ(), loc.getBlockY());
   }

   public static boolean isEmptyColumn(World world, int x, int z) {
     return isEmptyColumn(world, x, z, -1);
   }

   public static boolean isEmptyColumn(World world, int x, int z, int yException) {
     for (int y = 0; y < world.getMaxHeight(); y++) {
       if (yException != y && world.getBlockAt(x, y, z).getType() != Material.AIR) {
         return false;
       }
     }
     return true;
   }








   public static Set<Player> getNearbyPlayers(Location location, double range) {
     double rangeSquared = range * range;
     Set<Player> nearbyPlayers = new HashSet<>();
     World world = location.getWorld();
     for (Player player : world.getPlayers()) {
       if (player != null && player.getGameMode() != GameMode.SPECTATOR &&
         player.getLocation().distanceSquared(location) <= rangeSquared) {
         nearbyPlayers.add(player);
       }
     }

     return nearbyPlayers;
   }








   public static Player getNearestPlayer(Location location, double maxRange) {
     double rangeSquared = maxRange * maxRange;

     Player nearest = null;
     double nearestDistSquared = (maxRange <= 0.0D) ? Double.MAX_VALUE : rangeSquared;

     for (Player player : location.getWorld().getPlayers()) {
       if (player.getGameMode() != GameMode.SPECTATOR) {
         double distSquared = player.getLocation().distanceSquared(location);
         if (distSquared < nearestDistSquared) {
           nearest = player;
           nearestDistSquared = distSquared;
         }
       }
     }
     return nearest;
   }

   public static Set<Player> getPlayersInCuboid(Location origin, double width, double height, double depth) {
     if (width < 0.0D) {
       origin.setX(origin.getX() + width);
       width *= -1.0D;
     }
     if (height < 0.0D) {
       origin.setY(origin.getY() + height);
       height *= -1.0D;
     }
     if (depth < 0.0D) {
       origin.setZ(origin.getZ() + depth);
       depth *= -1.0D;
     }

     Set<Player> nearbyPlayers = new HashSet<>();
     World world = origin.getWorld();
     for (Player player : world.getPlayers()) {
       if (player.getGameMode() != GameMode.SPECTATOR) {
         Location ploc = player.getLocation();
         if (ploc.getX() > origin.getX() && ploc.getX() < origin.getBlockX() + width &&
           ploc.getY() > origin.getY() && ploc.getY() < origin.getY() + height &&
           ploc.getZ() > origin.getZ() && ploc.getZ() < origin.getZ() + depth) {
           nearbyPlayers.add(player);
         }
       }
     }


     return nearbyPlayers;
   }

   public static List<Location> getCircle(Location center, double radius, int amount) {
     World world = center.getWorld();
     double increment = 6.283185307179586D / amount;
     List<Location> locations = new ArrayList<>();
     for (int i = 0; i < amount; i++) {
       double angle = i * increment;
       double x = center.getX() + radius * Math.cos(angle);
       double z = center.getZ() + radius * Math.sin(angle);
       locations.add(new Location(world, x, center.getY(), z));
     }
     return locations;
   }

   public static int compareLocations(Location l1, Location l2) {
     if (l1.getY() > l2.getY()) {
       return -1;
     }
     if (l1.getY() < l2.getY()) {
       return 1;
     }

     if (l1.getX() > l2.getX()) {
       return -1;
     }
     if (l1.getX() < l2.getX()) {
       return 1;
     }

     return Double.compare(l2.getZ(), l1.getZ());
   }


   public static List<Chunk> getChunksDiamond(Chunk c, int radius) {
     if (radius <= 0) {
       return Collections.singletonList(c);
     }

     List<Chunk> chunks = new ArrayList<>();
     World world = c.getWorld();
     int ix = c.getX();
     int iz = c.getZ();
     int xmin = ix - radius, xmax = ix + radius;
     int x = xmax, z = iz;
     for (; x > ix; x--) {
       chunks.add(world.getChunkAt(x, z));
       z++;
     }
     for (; x > xmin; x--) {
       chunks.add(world.getChunkAt(x, z));
       z--;
     }
     for (; x < ix; x++) {
       chunks.add(world.getChunkAt(x, z));
       z--;
     }
     for (; x < xmax; x++) {
       chunks.add(world.getChunkAt(x, z));
       z++;
     }
     return chunks;
   }

   public static List<Chunk> getChunksSquare(Chunk c, int radius) {
     if (radius <= 0) {
       return Collections.singletonList(c);
     }

     List<Chunk> chunks = new ArrayList<>();
     World world = c.getWorld();
     int ix = c.getX();
     int iz = c.getZ();
     int xmin = ix - radius, xmax = ix + radius;
     int zmin = iz - radius, zmax = iz + radius;
     for (int x = xmin; x < xmax; x++) {
       chunks.add(world.getChunkAt(x, zmin));
       chunks.add(world.getChunkAt(x, zmax));
     }
     for (int z = zmin + 1; z < zmax - 1; z++) {
       chunks.add(world.getChunkAt(xmin, z));
       chunks.add(world.getChunkAt(xmax, z));
     }
     return chunks;
   }


   public static List<Location> getSphere(Location center, double radius) {
     radius++;
     List<Location> sphere = new ArrayList<>();
     int bx = center.getBlockX();
     int by = center.getBlockY();
     int bz = center.getBlockZ(); double x;
     for (x = bx - radius; x <= bx + radius; x++) {
       double y; for (y = by - radius; y <= by + radius; y++) {
         double z; for (z = bz - radius; z <= bz + radius; z++) {
           double distance = (bx - x) * (bx - x) + (bz - z) * (bz - z) + (by - y) * (by - y);
           if (distance < radius * radius && distance >= (radius - 1.0D) * (radius - 1.0D)) {
             sphere.add(new Location(center.getWorld(), x, y, z));
           }
         }
       }
     }

     return sphere;
   }
 }


