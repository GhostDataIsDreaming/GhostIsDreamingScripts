package ghostdata.framework;

public class Framework {

    private static boolean debug = false;

    public static void setDebug(boolean debug) {
        Framework.debug = debug;
    }

    public static boolean isDebug() {
        return Framework.debug;
    }
}
