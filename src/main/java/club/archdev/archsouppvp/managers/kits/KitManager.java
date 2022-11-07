package club.archdev.archsouppvp.managers.kits;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class KitManager {

    private List<Kit> kits = new ArrayList<Kit>();

    public KitManager() {
        this.kits.add(new DefaultKit());
        this.kits.add(new ProKit());
        this.kits.add(new AstronautKit());
        this.kits.add(new SnailKit());
        this.kits.add(new WrathKit());
        this.kits.add(new TurtleKit());
        this.kits.add(new UnicornKit());
        this.kits.add(new ShadowKit());
        this.kits.add(new GrapplerKit());
        this.kits.add(new TorchKit());
        this.kits.add(new MonkKit());
        this.kits.add(new PhantomKit());
        this.kits.add(new ZeusKit());
        this.kits.add(new ViperKit());
        this.kits.add(new MagicianKit());
        this.kits.add(new KangarooKit());
        this.kits.add(new DwarfKit());
        this.kits.add(new PalioxisKit());
        this.kits.add(new SwitcherKit());
        this.kits.add(new EskimoKit());
        this.kits.add(new StomperKit());
        this.kits.add(new ChemistKit());
        this.kits.add(new BarbarianKit());
        this.kits.add(new CopyCatKit());
        this.kits.add(new NinjaKit());
        this.kits.add(new VampireKit());
        this.kits.add(new FishermanKit());
    }

    public Kit getKitByName(String string) {
        for (Kit kit : this.kits) {
            if (!kit.getName().equalsIgnoreCase(string)) {
                continue;
            }

            return kit;
        }

        return null;
    }
}
