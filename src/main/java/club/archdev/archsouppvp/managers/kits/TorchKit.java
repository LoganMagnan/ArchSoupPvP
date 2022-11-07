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

public class TorchKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public TorchKit() {
        super("Torch", new String[]{
                "&7&oImmune to fire, torching",
                "&7&oeverything around you"
        }, Material.BLAZE_POWDER, 0, 2500);
    }

    public ItemStack getSword() {
        return new ItemBuilder(Material.IRON_SWORD).enchantment(Enchantment.DAMAGE_ALL,2).build();
    }

    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemBuilder(Material.LEATHER_BOOTS).color(Color.RED).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,1).enchantment(Enchantment.DURABILITY,10).build(),
                new ItemBuilder(Material.IRON_LEGGINGS).build(),
                new ItemBuilder(Material.IRON_CHESTPLATE).build(),
                new ItemBuilder(Material.LEATHER_HELMET).color(Color.RED).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,1).enchantment(Enchantment.DURABILITY,10).build()
        };
    }

    public Ability getAbility() {
        return null;
    }

    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[] {
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0),
                new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1)
        };
    }
}
