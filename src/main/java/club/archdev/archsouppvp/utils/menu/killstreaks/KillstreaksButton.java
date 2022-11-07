package club.archdev.archsouppvp.utils.menu.killstreaks;

import club.archdev.archsouppvp.managers.killstreaks.Killstreak;
import club.archdev.archsouppvp.utils.menu.Button;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class KillstreaksButton extends Button {

    private Killstreak killstreak;

    @Override
    public ItemStack getButtonItem(Player player) {
        return this.killstreak.getMaterial();
    }
}
