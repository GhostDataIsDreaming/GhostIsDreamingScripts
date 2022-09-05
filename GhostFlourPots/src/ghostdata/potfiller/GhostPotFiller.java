package ghostdata.potfiller;

import ghostdata.framework.FrameworkScript;
import ghostdata.framework.behaviortree.BehaviorTree;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.core.Instance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@ScriptManifest(
        category = Category.MONEYMAKING,
        name = "Ghosts Flour Pots",
        author = "GhostData",
        version = 0.5,
        image = "https://i.imgur.com/AlhYEm1.png")
public class GhostPotFiller extends FrameworkScript {

    private BehaviorTree behaviorTree = new BehaviorTree();

    private FlourPotsUI flourPotsUI;
    private JFrame _mainFrame;

    @Override
    public void onStart() {
        SwingUtilities.invokeLater(() -> {
            this.flourPotsUI = new FlourPotsUI();
            this._mainFrame = new JFrame("Ghosts Flour Pot Maker");

            this._mainFrame.setContentPane(flourPotsUI.rootPanel);
            this._mainFrame.pack();
            this._mainFrame.setVisible(true);

            this._mainFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    ScriptManager.getScriptManager().stop();
                }
            });
        });

        Instance.getInstance().setDrawMouse(true);
    }

    @Override
    public int onLoop() {
        if (!behaviorTree.hasBranches()) return 100;

        return behaviorTree.tick();
    }

    @Override
    public void onExit() {
        _mainFrame.setVisible(false);
    }

    @Override
    public void onPause() {
        Vars.TIMER.pause();
    }

    @Override
    public void onResume() {
        Vars.TIMER.resume();
    }

    @Override
    public void onPaint(Graphics graphics) {
        super.onPaint(graphics);

        if (!behaviorTree.hasBranches()) return;

        String version = Vars.getVersionInfo();
        String[] paint = Vars.getPaint();

        graphics.setColor(Color.CYAN);

        int start = 30;
        int spacing = 15;
        int indent = 10;

        graphics.drawString(version, indent, (start += spacing));

        if (paint != null && paint.length > 0) {
            for (String line : paint) {
                if (line == null || line.isEmpty()) continue;
                graphics.drawString(line, indent, (start += indent));
            }
        }
    }
}
