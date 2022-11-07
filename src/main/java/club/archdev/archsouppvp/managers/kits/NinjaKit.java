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

public class NinjaKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    private Color color = Color.fromRGB(0);

    public NinjaKit() {
        super("Ninja", new String[]{
                        "&7&oThrow ninja stars to make",
                        "&7&oyour enemies not be able to see",
                        "&7&oYou get a ninja back and 10+ durability",
                        "&7&oadded to your armor every single kill you get"
                }, Material.NETHER_STAR, 0, 5000);
    }

    @Override
    public ItemStack getSword() {
        return new ItemBuilder(Material.IRON_SWORD).enchantment(Enchantment.DAMAGE_ALL, 1).enchantment(Enchantment.DURABILITY, 3).build();
    }

    @Override
    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemBuilder(Material.LEATHER_BOOTS).color(color).enchantment(Enchantment.DURABILITY, 20).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build(),
                new ItemBuilder(Material.LEATHER_LEGGINGS).color(color).enchantment(Enchantment.DURABILITY, 20).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build(),
                new ItemBuilder(Material.LEATHER_CHESTPLATE).color(color).enchantment(Enchantment.DURABILITY, 20).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build(),
                new ItemBuilder(Material.LEATHER_HELMET).color(color).enchantment(Enchantment.DURABILITY, 20).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()
        };
    }

    @Override
    public Ability getAbility() {
        return this.main.getAbilityManager().getAbilityByName("Shuriken");
    }

    @Override
    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[]{
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1),
                new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0)
        };
    }
}