package ghostdata.framework.utils;

import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.input.Camera;

public class CameraUtils {

    public static void randomPitch() {
        Camera.rotateTo(Camera.getYaw(), Calculations.random(300, 383));
    }

    public static void randomZoom() {
        int zoom = Calculations.random(615, 1121);

        if (800 >= Camera.getZoom()) {
            Mouse.scrollUpUntil(Calculations.random(500, 1000), () -> Camera.getZoom() >= zoom);
        } else {
            Mouse.scrollDownUntil(Calculations.random(500, 1000), () -> Camera.getZoom() < zoom);
        }
    }

    public static void randomCamera() {
        randomPitch();
        randomZoom();
    }
}
