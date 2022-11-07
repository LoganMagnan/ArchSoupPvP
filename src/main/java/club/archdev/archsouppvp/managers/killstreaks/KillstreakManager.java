package club.archdev.archsouppvp.managers.killstreaks;

import club.archdev.archsouppvp.ArchSoupPvP;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class KillstreakManager {

    private ArchSoupPvP main = ArchSoupPvP.getInstance();

    private List<Killstreak> killstreaks = new ArrayList<Killstreak>();

    public KillstreakManager() {
        this.killstreaks.add(new FullRepairOneKillstreak());
        this.killstreaks.add(new GoldenApplesKillstreak());
        this.killstreaks.add(new ResistanceKillstreak());
        this.killstreaks.add(new EnchantedSoupKillstreak());
        this.killstreaks.add(new FullRepairTwoKillstreak());
        this.killstreaks.add(new ChickenStrikeKillstreak());
        this.killstreaks.add(new SnowBombsKillstreak());
        this.killstreaks.add(new NukeKillstreak());
    }

    public Killstreak getKillstreakByName(String string) {
        for (Killstreak killstreak : this.killstreaks) {
            if (killstreak.getName().equalsIgnoreCase(string)) {
                return killstreak;
            }
        }

        return null;
    }
}
