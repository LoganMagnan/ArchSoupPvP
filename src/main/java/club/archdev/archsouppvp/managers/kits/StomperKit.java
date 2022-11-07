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

public class StomperKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public StomperKit() {
        super("Stomper", new String[]{
                "&7&oLand on your enemies, any",
                "&7&odamage you would have taken",
                "&7&ogets transferred to your",
                "&7&oenemies"
        }, Material.IRON_BOOTS, 0, 4500);
    }

    public ItemStack getSword() {
        return new ItemBuilder(Material.IRON_SWORD).enchantment(Enchantment.DAMAGE_ALL,1).build();
    }

    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemStack(Material.IRON_BOOTS),
                new ItemBuilder(Material.GOLD_LEGGINGS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2).enchantment(Enchantment.DURABILITY,10).build(),
                new ItemBuilder(Material.GOLD_CHESTPLATE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2).enchantment(Enchantment.DURABILITY, 10).build(),
                new ItemBuilder(Material.LEATHER_HELMET).color(Color.RED).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,1).enchantment(Enchantment.DURABILITY,20).build()
        };
    }

    public Ability getAbility() {
        return this.main.getAbilityManager().getAbilityByName("Stomper");
    }

    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[] {
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0)
        };
    }
}
