package ghostdata.framework;

import org.dreambot.api.script.ScriptManager;
import org.dreambot.core.Instance;

import java.util.EventListener;

public class Framework {

    private static boolean debug = Instance.isDeveloperModeActive();

    public static void setDebug(boolean debug) {
        Framework.debug = debug;
    }

    public static boolean isDebug() {
        return Framework.debug;
    }

    public static void addListener(EventListener listener) {
        ScriptManager.getScriptManager().addListener(listener);
    }

}
