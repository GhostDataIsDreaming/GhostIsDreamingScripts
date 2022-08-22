package ghostdata.framework;

import org.dreambot.api.script.ScriptManager;

import java.util.concurrent.TimeUnit;

public class ScriptStarter {

    public static void start(String scriptName, String... args) {
        new Thread(() -> {
            ScriptManager manager = ScriptManager.getScriptManager();
            manager.stop();

            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            manager.start(scriptName, args);
        }).start();
    }
}
