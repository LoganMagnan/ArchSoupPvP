package club.archdev.archsouppvp.managers.kits;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.abilities.Ability;
import club.archdev.archsouppvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DefaultKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public DefaultKit() {
        super("Default", new String[]{
                        "&7&oDefault kit"
                }, Material.DIAMOND_SWORD, 0, 0);
    }

    @Override
    public ItemStack getSword() {
        return new ItemBuilder(Material.DIAMOND_SWORD).enchantment(Enchantment.DAMAGE_ALL, 1).enchantment(Enchantment.DURABILITY, 3).build();
    }

    @Override
    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemStack(Material.IRON_BOOTS),
                new ItemStack(Material.IRON_LEGGINGS),
                new ItemStack(Material.IRON_CHESTPLATE),
                new ItemBuilder(Material.IRON_HELMET).enchantment(Enchantment.PROTECTION_PROJECTILE).build()
        };
    }

    @Override
    public Ability getAbility() {
        return null;
    }

    @Override
    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[]{
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0)
        };
    }
}
