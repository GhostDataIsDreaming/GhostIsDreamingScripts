package ghostdata.potfiller;

import ghostdata.framework.Framework;
import ghostdata.potfiller.locations.ClayLocation;
import ghostdata.potfiller.locations.PotteryLocation;
import ghostdata.potfiller.locations.WheatLocation;
import ghostdata.potfiller.locations.WindmillLocation;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;

public class Vars {

    public enum ScriptDuty {
        ALL_IN_ONE,
        FILL_POTS,
        GATHER_MATERIALS;
    }

    public static final int ANIMATION_PICK_GRAINS = 827;

    public static ScriptDuty SCRIPT_DUTY;
    public static Timer TIMER;

    public static ClayLocation CLAY_LOCATION;
    public static PotteryLocation POTTER_LOCATION;
    public static WheatLocation WHEAT_LOCATION;
    public static WindmillLocation WINDMILL_LOCATION;

    public static boolean BUY_FROM_GE = false;
    public static int MAIN_ITEM_GOT = 0; //POTS_FILLED
    public static int REQUIRED_ITEM_GOT = 0; //POTS_MADE
    public static int INGREDIENTS_GATHERED = 0; //GRAINS_GOT

    public static String POT_TITLE = "Flour"; // Flour, Cornflour
    public static String CUSTOM_STEP_TITLE = "Setup";

// --- Is Located as ItemIDs.{ITEM}.getGrandExchangePrice()
//    public static int PRICE_GRAINS;
//    public static int PRICE_SWEETCORN;
//    public static int PRICE_CORNFLOUR;
//    public static int PRICE_CLAY;
//    public static int PRICE_POT;
//    public static int PRICE_SOFT_CLAY;
//    public static int PRICE_POT_OF_FLOUR;
//    public static int PRICE_POT_OF_CORNFLOUR;

    public static String[] getPaint() {
        switch (SCRIPT_DUTY) {
            case ALL_IN_ONE -> {
                return generateIOPaint();
            }
            case FILL_POTS -> {
                return generateMakeFlourPotsPaint();
            }
            case GATHER_MATERIALS -> {
                return generateGatherMaterialsPaint();
            }
        }

        return null;
    }

    public static String[] generateGatherMaterialsPaint() {
        return new String[]{
                "Elapsed Time: " + TIMER.formatTime(),
                MAIN_ITEM_GOT != 0 ? "" : "Grains Collected: " + MAIN_ITEM_GOT,
                REQUIRED_ITEM_GOT != 0 ? "" : "Pots Collected: " + REQUIRED_ITEM_GOT,
                INGREDIENTS_GATHERED != 0 ? "" : "Clay Collected: " + INGREDIENTS_GATHERED
        };
    }

    public static String[] generateMakeFlourPotsPaint() {
        return new String[]{
                "Elapsed Time: " + TIMER.formatTime(),
                "Pots of " + POT_TITLE + " Made: " + MAIN_ITEM_GOT + " (" + TIMER.getHourlyRate(MAIN_ITEM_GOT) + "/hr)",
                "Estimated Profit: " + ((POT_TITLE.equalsIgnoreCase("flour") ? ItemIDs.POT_OF_FLOUR.getGrandExchangePrice() : ItemIDs.POT_OF_CORNFLOUR.getGrandExchangePrice()) * MAIN_ITEM_GOT), // TODO- find easier than equalsIgnoreCase
                Framework.isDebug() ? "Current Step: " + CUSTOM_STEP_TITLE : null // Dont show anything if not debug
        };
    }

    // --version
    // Elapsed Time: TIMER.formatTime()
    // Status: CUSTOM_STEP_TITLE
    //          {
    //              REQUIREMENTS, MINING_CLAY, MAKING_SOFT_CLAY, PICKING_WHEAT,
    //              PICKING_CORNFLOUR, FILLING_POTS, HOPPER_INTERACT, HOPPER_CONTROLS,
    //              WALKING_TO_BANK, WALKING_TO_WINDMILL, USING_BANK, MAKING_POTS
    //          }
    // Pots of (Flour,Cornflour) Filled: MAIN_ITEM_GOT
    // Grains Collected: REQUIRED_ITEM_GOT
    // Pots Made: INGREDIENTS_GATHERED
    public static String[] generateIOPaint() {
        return null;
    }

    public static String getVersionInfo() {
        AbstractScript script = ScriptManager.getScriptManager().getCurrentScript();
        ScriptManifest manifest = script.getManifest();

        String name = manifest.name();
        double version = manifest.version();
        String author = manifest.author();

        String duty = "";
        switch (SCRIPT_DUTY) {
            case ALL_IN_ONE -> duty = "AIO";
            case FILL_POTS -> duty = "Fill";
            case GATHER_MATERIALS -> duty = "Mats";
        }
        return "[" + duty + "] " + name + " v" + version + " by " + author;
    }
}
