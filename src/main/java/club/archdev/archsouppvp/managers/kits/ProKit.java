package club.archdev.archsouppvp.managers.kits;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.abilities.Ability;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class ProKit extends Kit {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    public ProKit() {
        super("Pro", new String[]{
                        "&7&oDoubles coins when you",
                        "&7&okill another player"
                }, Material.DIAMOND, 0, 250);
    }

    @Override
    public ItemStack getSword() {
        return new ItemStack(Material.DIAMOND_SWORD);
    }

    @Override
    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemStack(Material.IRON_BOOTS),
                new ItemStack(Material.IRON_LEGGINGS),
                new ItemStack(Material.IRON_CHESTPLATE),
                new ItemStack(Material.IRON_HELMET)
        };
    }

    @Override
    public Ability getAbility() {
        return null;
    }

    @Override
    public PotionEffect[] getPotionEffects() {
        return new PotionEffect[0];
    }
}