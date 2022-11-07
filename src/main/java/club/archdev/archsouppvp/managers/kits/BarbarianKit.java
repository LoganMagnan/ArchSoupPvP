package club.archdev.archsouppvp.managers.kits;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.abilities.Ability;
import club.archdev.archsouppvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BarbarianKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public BarbarianKit() {
        super("Barbarian", new String[]{
                "&7&oSurround your enemies with",
                "&7&oa swarm of silverfish"
        }, Material.STONE_SWORD, 0, 4500);
    }

    public ItemStack getSword() {
        return new ItemBuilder(Material.STONE_SWORD).enchantment(Enchantment.DAMAGE_ALL,2).enchantment(Enchantment.DURABILITY,10).build();
    }

    public ItemStack[] getArmor() {
        return new ItemStack[0];
    }

    public Ability getAbility() {
        return this.main.getAbilityManager().getAbilityByName("Barbarian");
    }

    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[] {
                new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 2)
        };
    }
}
