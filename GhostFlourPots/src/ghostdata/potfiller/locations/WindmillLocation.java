package ghostdata.potfiller.locations;

import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;

public enum WindmillLocation {
    WEST_OF_LUMBRIDGE("West of Lumbridge", BankLocation.DRAYNOR, BankLocation.LUMBRIDGE),
    COOKS_GUILD("Cooks' Guild", BankLocation.COOKS_GUILD, BankLocation.GRAND_EXCHANGE, BankLocation.VARROCK_WEST) {
        @Override public boolean hasRequirement() {
            return Skills.getRealLevel(Skill.COOKING) >= 32;// && Equipment.getNameForSlot(EquipmentSlot.HAT.getSlot()).equalsIgnoreCase("Chef's hat");
        }
    }
    ;

    public final String name;
    public final BankLocation[] supportedBanks;

    WindmillLocation(String name) {
        this(name, null);
    }

    WindmillLocation(String name, BankLocation... locations) {
        this.name = name;

        if (locations == null || locations.length == 0) {
            this.supportedBanks = BankLocation.values(); // Show All Let user decide on stupid bank cause i cannot
        } else {
            this.supportedBanks = locations;
        }
    }

    public boolean hasRequirement() {
        return true;
    }

    public static WindmillLocation find(String name) {
        for (WindmillLocation loc : values()) {
            if (loc.name.equalsIgnoreCase(name)) {
                return loc;
            }
        }
        return null;
    }
}
