package ghostdata.framework.utils;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.wrappers.interactive.Entity;

public class EntityUtils {

    public static void interactEntity(Entity entity, String action) {
        if (Calculations.random(0, 100) <= 15) {
            CameraUtils.randomCamera();
        }

        if (entity.isOnScreen()) {
            Camera.rotateToEntity(entity);

            if (Calculations.random(0, 100) <= 15) {
                entity.interactForceRight(action);
            } else {
                entity.interact(action);
            }
        }
    }
}