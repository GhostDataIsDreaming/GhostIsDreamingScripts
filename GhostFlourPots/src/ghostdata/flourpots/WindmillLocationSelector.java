package ghostdata.flourpots;

import ghostdata.flourpots.behavior.BankingNodeParent;
import ghostdata.flourpots.behavior.RequirementsNode;
import ghostdata.flourpots.behavior.ResumeNode;
import ghostdata.flourpots.behavior.WindmillNodeParent;
import ghostdata.flourpots.windmills.WindmillLocation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindmillLocationSelector {
    public JPanel rootPanel;
    private JComboBox<String> comboBox1;
    private JList list1;
    private JButton startButton;
    private JList list2;

    public WindmillLocationSelector() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = comboBox1.getSelectedItem().toString();

                for (WindmillLocation location : GhostFlourPots.AVAILABLE_LOCATIONS) {
                    if (location.getName().equalsIgnoreCase(selected)) {
                        GhostFlourPots.selectedWindmillLocation = location;
                        GhostFlourPots.bTree.addNodes(new ResumeNode(), new RequirementsNode(), new BankingNodeParent(), new WindmillNodeParent());
                        break;
                    }
                }
            }
        });
    }
}
