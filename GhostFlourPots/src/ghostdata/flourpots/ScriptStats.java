package ghostdata.flourpots;

import org.dreambot.api.utilities.Timer;

import java.util.Random;

public class ScriptStats {

    public static int POTS_MADE = 0;
    public static int GE_PRICE = 0;

    public static Timer TIMER;
    public static Random R = new Random();
    public static WindmillLocation WIMDMILL_LOCATION;
    public static ScriptStep CURRENT_STEP = ScriptStep.RESUME;
}
