package club.archdev.archsouppvp.managers.killstreaks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public abstract class Killstreak {

    private String name;
    private int killstreak;
    private ItemStack material;

    public abstract KillstreaksCallable getKillstreaksCallable();
}
