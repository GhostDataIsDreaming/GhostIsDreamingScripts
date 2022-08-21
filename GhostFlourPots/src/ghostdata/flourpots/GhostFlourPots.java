package ghostdata.flourpots;

import ghostdata.flourpots.vars.WindmillMessages;
import ghostdata.framework.FrameworkScript;
import ghostdata.framework.behaviortree.BTree;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Map;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.wrappers.widgets.message.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

@ScriptManifest(
        category = Category.MONEYMAKING,
        name = "Ghosts Flour Pot Maker",
        author = "GhostData",
        version = 0.2)
public class GhostFlourPots extends FrameworkScript {

    public static Random R = new Random();
    protected static final BTree bTree = new BTree();

    public static WindmillLocation selectedWindmillLocation;
    public static ScriptStep currentStep = ScriptStep.RESUME;

    private WindmillLocationSelector _windmillLocationSelector;
    private JFrame _selectorFrame;

    @Override
    public void onStart() {
        SwingUtilities.invokeLater(() -> {
            this._windmillLocationSelector = new WindmillLocationSelector();
            this._selectorFrame = new JFrame("Ghosts Flour Pot Maker");

            _selectorFrame.setContentPane(_windmillLocationSelector.rootPanel);
            _selectorFrame.pack();
            _selectorFrame.setVisible(true);

            _selectorFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    ScriptManager.getScriptManager().stop();
                }
            });
        });

        this.addChatListener(new ChatListener() {
            @Override
            public void onMessage(Message message) {
                if (!updateStep(message)) {
                    MethodProvider.log("Found Unknown Message: " + message.getMessage());
                }
            }

            @Override
            public void onGameMessage(Message message) {
                if (!updateStep(message)) {
                    MethodProvider.log("Found Unknown Game Message: " + message.getMessage());
                }
            }

            boolean updateStep(Message message) {
                switch (message.getMessage()) {
                    case WindmillMessages.NO_POT:
                    case WindmillMessages.FLOUR_BIN_EMPTY:
                        currentStep = ScriptStep.BANKING;
                        return true;
                    case WindmillMessages.FLOUR_BIN_FULL:
                    case WindmillMessages.FLOUR_BIN_SUCCESS:
                        currentStep = ScriptStep.COLLECTING_FLOUR;
                        return true;
                    case WindmillMessages.HOPPER_CONTROLS_SUCCESS:
                    case WindmillMessages.HOPPER_EMPTY:
                        currentStep = ScriptStep.ADDING_TO_HOPPER;
                        return true;
                    case WindmillMessages.HOPPER_FULL:
                    case WindmillMessages.HOPPER_SUCCESS:
                        currentStep = ScriptStep.USE_CONTROLS;
                        return true;
                }

                return false;
            }
        });

        this.addPaintListener(new PaintListener() {
            @Override
            public void onPaint(Graphics graphics) {
                if (selectedWindmillLocation == null) return;
                Area area = selectedWindmillLocation.getWindmillArea();

                for (Tile tile : area.getTiles()) {
                    if (Map.isVisible(tile)) {
                        graphics.drawPolygon(tile.getPolygon());
                    }
                }
            }
        });
    }

    @Override
    public int onLoop() {
        if (!bTree.hasBranches()) return 100;
//        MethodProvider.log("Current Step - " + currentStep);
        return bTree.tick();
    }

    @Override
    public void onExit() {
        _selectorFrame.setVisible(false);
    }

    @Override
    public void onResume() {
        GhostFlourPots.currentStep = ScriptStep.RESUME;
    }
}
