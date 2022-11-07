package club.archdev.archsouppvp.managers.kits;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.abilities.Ability;
import club.archdev.archsouppvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KangarooKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public KangarooKit() {
        super("Kangaroo", new String[]{
                "&7&oJump into the sky",
                "&7&olike a kangaroo"
        }, Material.FIREWORK, 0, 2700);
    }

    public ItemStack getSword() {
        return new ItemBuilder(Material.DIAMOND_SWORD).enchantment(Enchantment.DAMAGE_ALL,1).enchantment(Enchantment.DURABILITY,3).build();
    }

    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemStack(Material.IRON_BOOTS),
                new ItemBuilder(Material.GOLD_LEGGINGS).enchantment(Enchantment.DURABILITY,4).build(),
                new ItemStack(Material.IRON_CHESTPLATE),
                new ItemStack(Material.IRON_HELMET)
        };
    }

    public Ability getAbility() {
        return this.main.getAbilityManager().getAbilityByName("Kangaroo");
    }

    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[] {
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0)
        };
    }
}
