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

public class MonkKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public MonkKit() {
        super("Monk", new String[]{
                "&7&oDisorientate your enemies",
                "&7&oby switching the item in",
                "&7&otheir hand with a random one",
                "&7&ofrom their inventory"
        }, Material.BLAZE_ROD, 0, 2500);
    }

    public ItemStack getSword() {
        return new ItemBuilder(Material.WOOD_SWORD).enchantment(Enchantment.DAMAGE_ALL,3).build();
    }

    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemStack(Material.IRON_BOOTS),
                new ItemStack(Material.IRON_LEGGINGS),
                new ItemStack(Material.IRON_CHESTPLATE),
                new ItemBuilder(Material.LEATHER_HELMET).color(Color.ORANGE).enchantment(Enchantment.DURABILITY,4).build()
        };
    }

    public Ability getAbility() {
        return this.main.getAbilityManager().getAbilityByName("Monk");
    }

    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[] {
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0)
        };
    }
}
