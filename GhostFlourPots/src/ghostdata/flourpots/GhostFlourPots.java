package ghostdata.flourpots;

import ghostdata.flourpots.vars.WindmillMessages;
import ghostdata.framework.FrameworkScript;
import ghostdata.framework.behaviortree.BehaviorTree;
import org.dreambot.api.Client;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Map;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
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

@ScriptManifest(
        category = Category.MONEYMAKING,
        name = "Ghosts Flour Pot Maker",
        author = "GhostData",
        version = 0.4,
        image = "https://i.imgur.com/AlhYEm1.png")
public class GhostFlourPots extends FrameworkScript implements ChatListener, PaintListener {

    private static GhostFlourPots instance;

    public static GhostFlourPots getInstance() {
        return instance;
    }

    public final BehaviorTree bTree = new BehaviorTree();

    public WindmillLocationSelector _windmillLocationSelector;
    private JFrame _selectorFrame;

    @Override
    public void onStart() {
        GhostFlourPots.instance = this;
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

//        Client.getInstance().setMouseMovementAlgorithm(new EaseMouse());
        Client.getInstance().setDrawMouse(true);
    }

    @Override
    public int onLoop() {
        if (!bTree.hasBranches()) return 100;
        return bTree.tick();
    }

    @Override
    public void onExit() {
        _selectorFrame.setVisible(false);
    }

    @Override
    public void onResume() {
        ScriptStats.TIMER.resume();
        ScriptStats.CURRENT_STEP = ScriptStep.RESUME;
    }

    @Override
    public void onPause() {
        ScriptStats.TIMER.pause();
    }

    @Override
    public void onMessage(Message message) {
        if (!updateStepBasedOnMessage(message)) {
            MethodProvider.log("Found Unknown Message: " + message.getMessage());
        }
    }

    @Override
    public void onGameMessage(Message message) {
        if (!updateStepBasedOnMessage(message)) {
            MethodProvider.log("Found Unknown Game Message: " + message.getMessage());
        }
    }

    @Override
    public void onPaint(Graphics graphics) {
        if (ScriptStats.WIMDMILL_LOCATION == null || !bTree.hasBranches()) return;
        Area area = ScriptStats.WIMDMILL_LOCATION.getWindmillArea();

        for (Tile tile : area.getTiles()) {
            if (Map.isVisible(tile)) {
                graphics.drawPolygon(tile.getPolygon());
            }
        }

        graphics.setColor(Color.CYAN);

        int start = 30;
        int sep = 15;

        String[] lines = {
                "Elapsed Time: " + ScriptStats.TIMER.formatTime(),
                "Pots Made: " + ScriptStats.POTS_OF_FLOUT_MADE + " (" + ScriptStats.TIMER.getHourlyRate(ScriptStats.POTS_OF_FLOUT_MADE) + "/hr)",
                "Potential Profit: " + (ScriptStats.GE_PRICE * ScriptStats.POTS_OF_FLOUT_MADE),
                "Current Step: " + ScriptStats.CURRENT_STEP + (ScriptStats.CURRENT_STEP == ScriptStep.NO_REQUIREMENTS ? " (No Grains or Pots)" : "")
        };

        for (String line : lines) {
            graphics.drawString(line, 10, (start += sep));
        }


        if (ScriptStats.WIMDMILL_LOCATION.getPathways() != null) {
            for (Tile[] path : ScriptStats.WIMDMILL_LOCATION.getPathways()) {
                for (Tile tile : path) {
                    if (Map.isVisible(tile)) {
                        graphics.drawPolygon(tile.getPolygon());
                    }
                }
            }
        }

        if (Walking.getDestination() != null) {
            if (Map.isVisible(Walking.getDestination())) {
                graphics.setColor(Color.GREEN);
                graphics.drawPolygon(Walking.getDestination().getPolygon());
            }
        }
    }

   private boolean updateStepBasedOnMessage(Message message) {
        switch (message.getMessage()) {
            case WindmillMessages.LAST_FLOUR_IN_BIN:
                ScriptStats.POTS_OF_FLOUT_MADE++;
            case WindmillMessages.NO_POT:
            case WindmillMessages.FLOUR_BIN_EMPTY:
                ScriptStats.CURRENT_STEP = ScriptStep.WALKING_TO_BANK;

//                ScriptStats.POTS_MADE += Inventory.count(FlourPotItems.POT_OF_FLOUR.id);
                return true;
            case WindmillMessages.FLOUR_BIN_SUCCESS:
                ScriptStats.POTS_OF_FLOUT_MADE++;
            case WindmillMessages.FLOUR_BIN_FULL:
                ScriptStats.CURRENT_STEP = ScriptStep.COLLECTING_FLOUR;
                return true;
            case WindmillMessages.HOPPER_CONTROLS_SUCCESS:
            case WindmillMessages.HOPPER_EMPTY:
                ScriptStats.CURRENT_STEP = ScriptStep.ADDING_TO_HOPPER;
                return true;
            case WindmillMessages.HOPPER_FULL:
            case WindmillMessages.HOPPER_SUCCESS:
            case WindmillMessages.HOPPER_CONTROLS_EXAMINE:
                ScriptStats.CURRENT_STEP = ScriptStep.USE_CONTROLS;
                return true;
        }

        return false;
    }
}
