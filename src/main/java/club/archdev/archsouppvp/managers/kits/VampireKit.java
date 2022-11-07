package club.archdev.archsouppvp.managers.kits;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.abilities.Ability;
import club.archdev.archsouppvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VampireKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public VampireKit() {
        super("Vampire", new String[]{
                "&7&oBat shooting ability while",
                "&7&oalso gaining regeneration V",
                "&7&oafter each kill"
        }, Material.MONSTER_EGG, 65, 5000);
    }

    public ItemStack getSword() {
        return new ItemBuilder(Material.IRON_SWORD).enchantment(Enchantment.DAMAGE_ALL,1).enchantment(Enchantment.DURABILITY,3).build();
    }

    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemBuilder(Material.CHAINMAIL_BOOTS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL).build(),
                new ItemBuilder(Material.CHAINMAIL_LEGGINGS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL).build(),
                new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL).build(),
                new ItemBuilder(Material.CHAINMAIL_HELMET).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL).build()
        };
    }

    public Ability getAbility() {
        return this.main.getAbilityManager().getAbilityByName("Vampire");
    }

    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[] {
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1)
        };
    }
}
