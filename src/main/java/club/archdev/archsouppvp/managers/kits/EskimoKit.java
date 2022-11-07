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

public class EskimoKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public EskimoKit() {
        super("Eskimo", new String[]{
                        "&7&oMake a sphere of packed ice",
                        "&7&oaround you",
                        "&7&oand get strength for 5 seconds"
                }, Material.PACKED_ICE, 0, 3500);
    }

    @Override
    public ItemStack getSword() {
        return new ItemBuilder(Material.IRON_SWORD).enchantment(Enchantment.DAMAGE_ALL,1).enchantment(Enchantment.DURABILITY,3).build();
    }

    @Override
    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemBuilder(Material.LEATHER_BOOTS)
                .color(Color.PURPLE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2).enchantment(Enchantment.DURABILITY,25).build(), (new ItemBuilder(Material.LEATHER_LEGGINGS))
                .color(Color.PURPLE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2).enchantment(Enchantment.DURABILITY,25).build(), (new ItemBuilder(Material.LEATHER_CHESTPLATE))
                .color(Color.PURPLE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2).enchantment(Enchantment.DURABILITY,25).build(), (new ItemBuilder(Material.LEATHER_HELMET))
                .color(Color.PURPLE).enchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2).enchantment(Enchantment.DURABILITY,25).build() };
    }

    @Override
    public Ability getAbility() {
        return this.main.getAbilityManager().getAbilityByName("Eskimo");
    }

    @Override
    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[]{
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0),
                new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0)
        };
    }
}
