package club.archdev.archsouppvp.managers.kits;

import club.archdev.archsouppvp.ArchSoupPvP;
import club.archdev.archsouppvp.managers.abilities.Ability;
import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public abstract class Kit {

    private String name;
    private String[] lore;
    private Material material;
    private int data;
    private int price;

    public abstract ItemStack getSword();

    public abstract ItemStack[] getArmor();

    public abstract Ability getAbility();

    public abstract PotionEffect[] getPotionEffects();

    public void useKit(Player player) {
        PlayerData playerData = ArchSoupPvP.getInstance().getPlayerDataManager().getPlayerData(player.getUniqueId());

        player.getInventory().clear();

        Arrays.stream(this.getPotionEffects()).forEach(player :: addPotionEffect);

        player.getInventory().setItem(0, this.getSword());
        player.getInventory().setArmorContents(this.getArmor());

        if (this.getAbility() != null) {
            player.getInventory().setItem(1, this.getAbility().getMaterial());
        }

//        if (playerData.isPotions()) {
//            this.givePlayerPotions(player);
//        } else {
            this.givePlayerSoups(player);
//        }

        player.updateInventory();
        player.sendMessage(Utils.translate("&eYou have chosen the &b" + this.name + " &ekit"));
    }

    public void givePlayerPotions(Player player) {
        ItemStack itemStack = new ItemStack(Material.POTION, 1);

        Potion potion = new Potion(PotionType.INSTANT_HEAL, 1, true);
        potion.apply(itemStack);

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null) {
                continue;
            }

            player.getInventory().addItem(itemStack);
        }
    }

    public void givePlayerSoups(Player player) {
        ItemStack itemStack = new ItemStack(Material.MUSHROOM_SOUP, 1);

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null) {
                continue;
            }

            player.getInventory().addItem(itemStack);
        }
    }
}
