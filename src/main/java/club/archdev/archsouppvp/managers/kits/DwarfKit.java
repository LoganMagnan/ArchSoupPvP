package club.archdev.archsouppvp.managers.kits;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.abilities.Ability;
import club.archdev.archsouppvp.utils.ItemBuilder;
import club.archdev.archsouppvp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class DwarfKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public DwarfKit() {
        super("Dwarf", new String[]{
                "&7&oCrouch to charge up",
                "&7&oa blast that knocks",
                "&7&oyour enemies away"
        }, Material.GOLD_AXE, 0, 2750);
    }

    public ItemStack getSword() {
        return new ItemBuilder(Material.GOLD_AXE).name(Utils.translate("&bDwarf's Axe")).lore(Utils.translate(Arrays.asList("&7&oCrouch to charge up a blast", "&7&othat knocks players away"))).enchantment(Enchantment.DAMAGE_ALL,4).enchantment(Enchantment.DURABILITY,25).build();
    }

    public ItemStack[] getArmor() {
        return new ItemStack[] {
                new ItemBuilder(Material.GOLD_BOOTS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2).enchantment(Enchantment.DURABILITY,10).build(),
                new ItemBuilder(Material.GOLD_LEGGINGS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2).enchantment(Enchantment.DURABILITY,10).build(),
                new ItemBuilder(Material.GOLD_CHESTPLATE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2).enchantment(Enchantment.DURABILITY,10).build(),
                new ItemBuilder(Material.GOLD_HELMET).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2).enchantment(Enchantment.DURABILITY,10).build()
        };
    }

    public Ability getAbility() {
        return null;
    }

    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[] {
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1)
        };
    }
}
