package club.archdev.archsouppvp.managers.kits;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.abilities.Ability;
import club.archdev.archsouppvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FishermanKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public FishermanKit() {
        super("Fisherman", new String[]{
                "&7&oSpecial fishing rod that",
                "&7&oteleports players to you"
        }, Material.FISHING_ROD, 0, 6000);
    }

    public ItemStack getSword() {
        return new ItemStack(Material.DIAMOND_SWORD);
    }

    public ItemStack[] getArmor() {
        return new ItemStack[] {
                new ItemBuilder(Material.GOLD_BOOTS).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2).enchantment(Enchantment.DURABILITY,3).build(),
                new ItemStack(Material.IRON_LEGGINGS),
                new ItemStack(Material.IRON_CHESTPLATE),
                new ItemBuilder(Material.GOLD_HELMET).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2).enchantment(Enchantment.DURABILITY,3).build()
        };
    }

    public Ability getAbility() {
        return this.main.getAbilityManager().getAbilityByName("Fisherman");
    }

    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[] {
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0)
        };
    }
}
