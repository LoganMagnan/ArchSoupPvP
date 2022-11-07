package club.archdev.archsouppvp.runnables;

import club.archdev.archsouppvp.playerdata.PlayerData;
import club.archdev.archsouppvp.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.EntitySilverfish;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class BountyNameRunnable extends BukkitRunnable {

    private Player player;
    private PlayerData playerData;

    @Override
    public void run() {
        EntitySilverfish entitySilverfish = new EntitySilverfish(((CraftPlayer) this.player).getHandle().getWorld());
        entitySilverfish.setCustomName(Utils.translate("&aBounty: &e" + this.playerData.getBounty()));
        entitySilverfish.setCustomNameVisible(true);

        if (this.player == null) {
            entitySilverfish.die();

            this.cancel();

            return;
        }

        if (this.playerData == null) {
            entitySilverfish.die();

            this.cancel();

            return;
        }

        if (this.player.getPassenger() != null) {
            entitySilverfish.setCustomName(Utils.translate("&aBounty: &e" + this.playerData.getBounty()));

            return;
        }

        if (this.playerData.getBounty() <= 0) {
            entitySilverfish.setCustomName(Utils.translate(""));

            return;
        }

        this.player.setPassenger(entitySilverfish.getBukkitEntity());

        ((CraftWorld) this.player.getWorld()).getHandle().addEntity(entitySilverfish);

        entitySilverfish.setCustomName(Utils.translate("&aBounty: &e" + this.playerData.getBounty()));
    }
}
