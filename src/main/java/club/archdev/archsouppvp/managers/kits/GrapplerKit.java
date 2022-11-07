package club.archdev.archsouppvp.managers.kits;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.abilities.Ability;
import club.archdev.archsouppvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GrapplerKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public GrapplerKit() {
        super("Grappler", new String[]{
                "&7&oGrapple around the map,",
                "&7&oswinging from target",
                "&7&oto target"
        }, Material.LEASH, 0, 2500);
    }

    public ItemStack getSword() {
        return new ItemBuilder(Material.IRON_SWORD).enchantment(Enchantment.DAMAGE_ALL,1).enchantment(Enchantment.DURABILITY,3).build();
    }

    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemStack(Material.IRON_BOOTS),
                new ItemBuilder(Material.CHAINMAIL_LEGGINGS).enchantment(Enchantment.DURABILITY,2).build(),
                new ItemStack(Material.IRON_CHESTPLATE),
                new ItemStack(Material.IRON_HELMET)
        };
    }

    public Ability getAbility() {
        return this.main.getAbilityManager().getAbilityByName("Grappler");
    }

    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[] {
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0)
        };
    }
}
