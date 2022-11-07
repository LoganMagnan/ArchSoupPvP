package club.archdev.archsouppvp.managers.kits;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.abilities.Ability;
import club.archdev.archsouppvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AstronautKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public AstronautKit() {
        super("Astronaut", new String[]{
                        "&7&oGet jump boost",
                        "&7&oand the ability",
                        "&7&oto radiate gravity"
                }, Material.GLASS, 0, 2000);
    }

    @Override
    public ItemStack getSword() {
        return new ItemBuilder(Material.IRON_SWORD).enchantment(Enchantment.DAMAGE_ALL, 1).enchantment(Enchantment.DURABILITY, 3).build();
    }

    @Override
    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemStack(Material.DIAMOND_BOOTS),
                new ItemStack(Material.IRON_LEGGINGS),
                new ItemStack(Material.DIAMOND_CHESTPLATE),
                new ItemStack(Material.IRON_HELMET)
        };
    }

    @Override
    public Ability getAbility() {
        return this.main.getAbilityManager().getAbilityByName("Astronaut");
    }

    @Override
    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[]{
                new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0)
        };
    }
}
