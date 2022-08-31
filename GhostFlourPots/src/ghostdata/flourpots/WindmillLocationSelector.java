package ghostdata.flourpots;

import ghostdata.flourpots.behavior.*;
import ghostdata.flourpots.behavior.materials.gains.PickGrains;
import ghostdata.flourpots.behavior.materials.gains.WalkToGrains;
import ghostdata.flourpots.vars.FlourPotItems;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.utilities.Timer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class WindmillLocationSelector {
    public JPanel rootPanel;
    private JComboBox<String> comboBox1;
    private JList list1;
    private JButton startButton;
    private JList list2;
    private JLabel warningLabel;
    private JLabel warningIconLeft;
    private JLabel warningIconRight;
    public JRadioButton grainPickerEnabledRadioButton;
    private JTextField grainPickerAmountTextField;

    public WindmillLocationSelector() {
        try {
            Image img = ImageIO.read(new URL("https://icon-library.com/images/warning-icon-svg/warning-icon-svg-27.jpg"));
//            img = img.getScaledInstance(warningLabel.getWidth(), warningLabel.getHeight(), Image.SCALE_SMOOTH);
            img = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);

            ImageIcon icon = new ImageIcon(img);

            warningIconLeft.setIcon(icon);
            warningIconRight.setIcon(icon);
        } catch (Exception e) {
            warningLabel.setText("=========== Warning ===========");
            MethodProvider.logError(e);
            warningIconRight.setVisible(false);
            warningIconLeft.setVisible(false);
        }

        for (WindmillLocation location : WindmillLocation.values()) {
            if (location.hasRequirements()) {
                comboBox1.addItem(location.getName());
            }
        }

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = comboBox1.getSelectedItem().toString();

                for (WindmillLocation location : WindmillLocation.values()) {
                    if (location.getName().equalsIgnoreCase(selected)) {
                        ScriptStats.GE_PRICE = LivePrices.get(FlourPotItems.POT_OF_FLOUR.id);
                        ScriptStats.TIMER = new Timer();

                        ScriptStats.WIMDMILL_LOCATION = location;
                        GhostFlourPots.getInstance().bTree.addNodes(new ResumeNode(), new RequirementsNode(), new BankingNodeParent(), new WindmillNodeParent());

                        if (grainPickerEnabledRadioButton.isEnabled()) {
                            int amount = Integer.valueOf(grainPickerAmountTextField.getText());
                            GhostFlourPots.getInstance().bTree.addNode(new MaterialsParentNode(amount));
                        }
                        break;
                    }
                }
            }
        });
        grainPickerEnabledRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grainPickerAmountTextField.setEnabled(grainPickerEnabledRadioButton.isSelected());
            }
        });
    }
}
