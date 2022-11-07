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

public class ShadowKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public ShadowKit() {
        super("Shadow", new String[]{
                "&7&oSpawn in a ring of enderman",
                "&7&ofor defense, you gain 10",
                "&7&odurability back to your",
                "&7&oarmor each kil."
        }, Material.SULPHUR, 0, 2500);
    }

    public ItemStack getSword() {
        return new ItemStack(Material.DIAMOND_SWORD);
    }

    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemBuilder(Material.LEATHER_BOOTS).color(Color.fromRGB(808080)).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3).enchantment(Enchantment.DURABILITY,10).build(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).color(Color.fromRGB(808080)).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3).enchantment(Enchantment.DURABILITY,10).build(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).color(Color.fromRGB(808080)).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3).enchantment(Enchantment.DURABILITY,10).build(),
                new ItemBuilder(Material.LEATHER_HELMET).color(Color.fromRGB(808080)).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,3).enchantment(Enchantment.DURABILITY,10).build()
        };
    }

    public Ability getAbility() {
        return null;
    }

    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[] {
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1)
        };
    }
}
