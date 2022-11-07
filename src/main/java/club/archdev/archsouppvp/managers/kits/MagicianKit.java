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

public class MagicianKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public MagicianKit() {
        super("Magician", new String[]{
                "&7&oUsing your magic wand will",
                "&7&oallow you to disappear right",
                "&7&oin front of your enemies, your",
                "&7&oknowledge of hands allows you",
                "&7&opick pocket your enemies"
        }, Material.STICK, 0, 2600);
    }

    public ItemStack getSword() {
        return new ItemBuilder(Material.WOOD_SWORD).enchantment(Enchantment.DAMAGE_ALL,3).enchantment(Enchantment.DURABILITY,10).build();
    }

    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemStack(Material.DIAMOND_BOOTS),
                new ItemBuilder(Material.LEATHER_LEGGINGS).color(Color.WHITE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,1).enchantment(Enchantment.DURABILITY,10).build(),
                new ItemBuilder(Material.IRON_CHESTPLATE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2).build(),
                new ItemBuilder(Material.LEATHER_HELMET).color(Color.BLACK).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,1).enchantment(Enchantment.DURABILITY,10).build()
        };
    }

    public Ability getAbility() {
        return null;
    }

    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[] {
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0)
        };
    }
}
