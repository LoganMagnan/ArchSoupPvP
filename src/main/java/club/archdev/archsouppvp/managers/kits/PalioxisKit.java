package club.archdev.archsouppvp.managers.kits;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.abilities.Ability;
import club.archdev.archsouppvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PalioxisKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public PalioxisKit() {
        super("Palioxis", new String[]{
                "&7&oMaster the art of escape",
                "&7&oand retreat, gain 10",
                "&7&odurability, an enderpearl",
                "&7&oand speed 3 per kill"
        }, Material.ENDER_PEARL, 0, 3000);
    }

    public ItemStack getSword() {
        return new ItemBuilder(Material.IRON_SWORD).enchantment(Enchantment.DAMAGE_ALL,1).enchantment(Enchantment.DURABILITY,3).build();
    }

    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemStack(Material.IRON_BOOTS),
                new ItemStack(Material.IRON_LEGGINGS),
                new ItemStack(Material.IRON_CHESTPLATE),
                new ItemStack(Material.IRON_HELMET)
        };
    }

    public Ability getAbility() {
        return this.main.getAbilityManager().getAbilityByName("Palioxis");
    }

    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[] {
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1)
        };
    }
}
