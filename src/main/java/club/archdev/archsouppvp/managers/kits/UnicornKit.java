package club.archdev.archsouppvp.managers.kits;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.abilities.Ability;
import club.archdev.archsouppvp.utils.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UnicornKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public UnicornKit() {
        super("Unicorn", new String[]{
                "&7&oBe a unicorn"
        }, Material.WOOL, 2, 2000);
    }

    public ItemStack getSword() {
        return new ItemBuilder(Material.DIAMOND_SWORD).enchantment(Enchantment.DAMAGE_ALL,1).enchantment(Enchantment.DURABILITY,3).build();
    }

    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemBuilder(Material.LEATHER_BOOTS).color(Color.fromRGB(8000)).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2).enchantment(Enchantment.DURABILITY,20).build(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).color(Color.fromRGB(8000)).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2).enchantment(Enchantment.DURABILITY,20).build(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).color(Color.fromRGB(8000)).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2).enchantment(Enchantment.DURABILITY,20).build(),
                new ItemBuilder(Material.LEATHER_HELMET).color(Color.fromRGB(8000)).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2).enchantment(Enchantment.DURABILITY,20).build()
        };
    }

    public Ability getAbility() {
        return null;
    }

    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[] {
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0),
                new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1)
        };
    }
}
