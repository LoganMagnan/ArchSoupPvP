package club.archdev.archsouppvp.managers.abilities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AbilityManager {

    private List<Ability> abilities = new ArrayList<Ability>();

    public AbilityManager() {
        this.abilities.add(new ShurikenAbility());
        this.abilities.add(new GrapplerAbility());
        this.abilities.add(new AstronautAbility());
        this.abilities.add(new FishermanAbility());
        this.abilities.add(new EskimoAbility());
        this.abilities.add(new PhantomAbility());
        this.abilities.add(new ZeusAbility());
        this.abilities.add(new SwitcherAbility());
        this.abilities.add(new MonkAbility());
        this.abilities.add(new PalioxisAbility());
        this.abilities.add(new StomperAbility());
        this.abilities.add(new KangarooAbility());
    }

    public Ability getAbilityByName(String string) {
        for (Ability ability : this.abilities) {
            if (!ability.getName().equalsIgnoreCase(string)) {
                continue;
            }

            return ability;
        }

        return null;
    }
}
