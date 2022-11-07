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

public class ZeusKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public ZeusKit() {
        super("Zeus", new String[]{
                "&7&oGain the ability to summon",
                "&7&olightning bolts to smite",
                "&7&odown on your enemies"
        }, Material.GLOWSTONE_DUST, 0, 2500);
    }

    public ItemStack getSword() {
        return new ItemStack(Material.DIAMOND_SWORD);
    }

    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemBuilder(Material.LEATHER_BOOTS).color(Color.YELLOW).enchantment(Enchantment.DURABILITY,20).build(),
                new ItemStack(Material.IRON_LEGGINGS),
                new ItemStack(Material.IRON_CHESTPLATE),
                new ItemBuilder(Material.LEATHER_HELMET).color(Color.YELLOW).enchantment(Enchantment.PROTECTION_FIRE).enchantment(Enchantment.DURABILITY,20).build()
        };
    }

    public Ability getAbility() {
        return this.main.getAbilityManager().getAbilityByName("Zeus");
    }

    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[] {
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0)
        };
    }
}
