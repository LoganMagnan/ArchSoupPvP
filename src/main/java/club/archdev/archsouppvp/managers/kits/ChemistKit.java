package club.archdev.archsouppvp.managers.kits;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.abilities.Ability;
import club.archdev.archsouppvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ChemistKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public ChemistKit() {
        super("Chemist", new String[]{
                        "&7&oSlow down your enemies with",
                        "&7&opoison or take them down with",
                        "&7&oupon kills"
                }, Material.POTION, 44, 4500);
    }

    public ItemStack getSword() {
        return new ItemBuilder(Material.IRON_SWORD).enchantment(Enchantment.DAMAGE_ALL,1).enchantment(Enchantment.DURABILITY,3).build();
    }

    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemBuilder(Material.CHAINMAIL_BOOTS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL).build(),
                new ItemBuilder(Material.CHAINMAIL_LEGGINGS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL).build(),
                new ItemBuilder(Material.IRON_CHESTPLATE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL).build(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL).build()
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
