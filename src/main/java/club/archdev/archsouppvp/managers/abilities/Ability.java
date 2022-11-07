package club.archdev.archsouppvp.managers.abilities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public abstract class Ability {

    private String name;
    private ItemStack material;

    public abstract AbilityCallable getAbilityCallable();

    public abstract int getCooldown();
}
