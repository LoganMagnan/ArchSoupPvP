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

public class SwitcherKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public SwitcherKit() {
        super("Switcher", new String[]{
                "&7&oShoot players with snowballs",
                "&7&oto switch places with them"
        }, Material.SNOW_BALL, 0, 3500);
    }

    public ItemStack getSword() {
        return new ItemBuilder(Material.DIAMOND_SWORD).enchantment(Enchantment.DAMAGE_ALL,1).build();
    }

    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemBuilder(Material.LEATHER_BOOTS).color(Color.WHITE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2).enchantment(Enchantment.DURABILITY,3).build(),
                new ItemStack(Material.IRON_LEGGINGS),
                new ItemStack(Material.IRON_CHESTPLATE),
                new ItemBuilder(Material.LEATHER_HELMET).color(Color.WHITE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2).enchantment(Enchantment.DURABILITY,3).build()
        };
    }

    public Ability getAbility() {
        return this.main.getAbilityManager().getAbilityByName("Switcher");
    }

    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[] {
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0)
        };
    }
}
