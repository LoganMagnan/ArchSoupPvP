package club.archdev.archsouppvp.managers.kits;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.abilities.Ability;
import club.archdev.archsouppvp.utils.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class TurtleKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public TurtleKit() {
        super("Turtle", new String[]{
                "&7&oSneaking and Shifting will grant",
                "&7&oyou near invincibility, at the",
                "&7&ocost of speed"
        }, Material.EMERALD, 0, 2000);
    }

    public ItemStack getSword() {
        return new ItemBuilder(Material.DIAMOND_SWORD).enchantment(Enchantment.DAMAGE_ALL,2).build();
    }

    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemBuilder(Material.LEATHER_BOOTS).color(Color.fromRGB(8000)).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL).enchantment(Enchantment.DURABILITY,25).build(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).color(Color.fromRGB(8000)).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL).enchantment(Enchantment.DURABILITY,25).build(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).color(Color.fromRGB(8000)).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL).enchantment(Enchantment.DURABILITY,25).build(),
                new ItemBuilder(Material.LEATHER_HELMET).color(Color.fromRGB(8000)).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL).enchantment(Enchantment.DURABILITY,25).build()
        };
    }

    public Ability getAbility() {
        return null;
    }

    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[0];
    }
}
