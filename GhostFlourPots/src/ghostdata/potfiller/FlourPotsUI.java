package ghostdata.potfiller;

import ghostdata.potfiller.locations.ClayLocation;
import ghostdata.potfiller.locations.PotteryLocation;
import ghostdata.potfiller.locations.WheatLocation;
import ghostdata.potfiller.locations.WindmillLocation;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.container.impl.bank.BankLocation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.net.URL;

public class FlourPotsUI {

    private static final KeyAdapter NUMBERS_ONLY_ADAPTER = new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent event) {
            // https://stackhowto.com/how-to-make-jtextfield-accept-only-numbers/
            char c = event.getKeyChar();
            if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                event.consume();  // if it's not a number, ignore the event
            }
            super.keyTyped(event);
        }
    };

    public JPanel rootPanel;
    private JPanel mainPanel;
    private JPanel aioPanel;
    private JPanel pofPanel;
    private JPanel gatherMatsPanel;
    private JRadioButton gmPickGrainsEnabled;
    private JTextField gmGrainsHowMany; // TODO - Update to JFormattedTextField
    private JRadioButton gmMakePotsEnabled;
    private JTextField gmPotsHowMany; // TODO - Update to JFormattedTextField
    private JButton gmStartButton;
    private JComboBox gmMineClayAt;
    private JComboBox gmMakePotsAt;
    private JComboBox gmPickGrainsAt;
    private JRadioButton gmBuyGrains;
    private JRadioButton gmBuyPots;
    private JComboBox gmBuyPotsType;
    private JTextField gmPotsGEPercent; // TODO - Update to JFormattedTextField
    private JComboBox gmBuyGrainsType;
    private JTextField gmGrainsGEPercent; // TODO - Update to JFormattedTextField
    private JComboBox pofWindmillLocation;
    private JButton pofMakeFlourPotsStartButton;
    private JButton potMakeCornflourStartButton;
    private JLabel gmGEGrainsCostAmount;
    private JLabel gmGEPotsCostAmount;
    private JLabel pofWarningIconLeft;
    private JLabel pofWarningIconRight;
    private JButton aioStartButton;
    private JButton aioMakePotsOfFlour;
    private JButton aioMakePotsOfCornflour;
    private JButton freshAOIButton;
    private JButton setupAllInOneButton;
    private JButton setupPotOfFlourButton;
    private JButton setupGatherMaterialsMinimumButton;
    private JTabbedPane tabbedPane;
    private JLabel flourPotIconField;
    private JComboBox pofBankingLocation;
    private JComboBox aioBankingLocation;
    private JComboBox aioWindmillLocation;
    private JRadioButton aioPickGrainsEnabled;
    private JRadioButton aioMakePotsEnabled;
    private JRadioButton aioBuyGrains;
    private JRadioButton aioBuyPots;
    private JComboBox aioMakePotsAt;
    private JTextField aioGrainsHowMany;
    private JComboBox aioBuyPotsType;
    private JTextField aioGrainsGEPercent;
    private JTextField aioPotsGEPercent;
    private JComboBox aioBuyGrainsType;
    private JComboBox aioPickGrainsAt;
    private JTextField aioPotsHowMany;
    private JComboBox aioMineClayAt;
    private JLabel aioGEGrainCostAmount;
    private JLabel aioGEPotsCostAmount;
    private JLabel sourceLinkLabel;
    private JLabel aioWarningIconLeft;
    private JLabel aioWarningIconRight;

    public FlourPotsUI() {
        //Fill Combo Boxes
        for (ClayLocation loc : ClayLocation.values()) {
            gmMineClayAt.addItem(loc.name);
            aioMineClayAt.addItem(loc.name);
        }
        for (PotteryLocation loc : PotteryLocation.values()) {
            gmMakePotsAt.addItem(loc.name);
            aioMakePotsAt.addItem(loc.name);
        }
        for (WheatLocation loc : WheatLocation.values()) {
            gmPickGrainsAt.addItem(loc.name);
            aioPickGrainsAt.addItem(loc.name);
        }

        aioBankingLocation.addItem("CLOSEST");
        pofBankingLocation.addItem("CLOSEST");

        boolean bankAdded = false;
        for (WindmillLocation loc : WindmillLocation.values()) {
            aioWindmillLocation.addItem(loc.name);
            pofWindmillLocation.addItem(loc.name);

            if (!bankAdded) {
                bankAdded = true;

                for (BankLocation bankLocation : loc.supportedBanks) {
                    aioBankingLocation.addItem(bankLocation.name());
                    pofBankingLocation.addItem(bankLocation.name());
                }
            }
        }

        for (ItemIDs item : new ItemIDs[] {ItemIDs.GRAIN, ItemIDs.CORNFLOUR, ItemIDs.SWEETCORN}) {
            aioBuyGrainsType.addItem(item.name);
            gmBuyGrainsType.addItem(item.name);
        }

        for (ItemIDs item : new ItemIDs[] {ItemIDs.POT, ItemIDs.CLAY, ItemIDs.SOFT_CLAY, ItemIDs.UNFIRED_POT}) {
            aioBuyPotsType.addItem(item.name);
            gmBuyPotsType.addItem(item);
        }

        // Main Menu
        sourceLinkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (Desktop.isDesktopSupported()) {
                        final URI uri = new URI("https://github.com/GhostDataIsDreaming/GhostIsDreamingScripts/tree/main/GhostFlourPots");

                        new SwingWorker<Void, Void>() {

                            @Override
                            protected Void doInBackground() throws Exception {
                                Desktop.getDesktop().browse(uri);
                                return null;
                            }

                            @Override
                            public void done() {
                                try {
                                    get();
                                } catch (Exception e) {
                                    MethodProvider.logError(e);
                                }
                            }
                        }.execute();
                    }
                } catch (Exception ex) {
                    MethodProvider.logError(ex);
                }
            }
        });

        Image flourPotImage = getImageFromURL("https://i.imgur.com/AlhYEm1.png", 50, 50);
        if (flourPotImage != null) {
            ImageIcon icon = new ImageIcon(flourPotImage);
            flourPotIconField.setIcon(icon);
        }

        flourPotIconField.setText("");

        for (JButton button : new JButton[] { setupAllInOneButton, freshAOIButton}) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tabbedPane.setSelectedIndex(1);
                }
            });
        }
        setupPotOfFlourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(2);
            }
        });
        setupGatherMaterialsMinimumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(3);
            }
        });

        // Gather Materials
        gmPickGrainsEnabled.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean selected = gmPickGrainsEnabled.isSelected();

                gmPickGrainsAt.setEnabled(selected);
                gmGrainsHowMany.setEnabled(selected);
//                gmBuyGrains.setEnabled(selected);

                if (gmBuyGrains.isSelected() && selected) {
                    gmBuyGrainsType.setEnabled(true);
                    gmGrainsGEPercent.setEnabled(true);
                }
            }
        });
        gmMakePotsEnabled.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean selected = gmMakePotsEnabled.isSelected();

                gmMakePotsAt.setEnabled(selected);
                gmPotsHowMany.setEnabled(selected);
                gmMineClayAt.setEnabled(selected);
//                gmBuyPots.setEnabled(selected);

                if (gmBuyPots.isSelected() && selected) {
                    gmBuyPotsType.setEnabled(true);
                    gmPotsGEPercent.setEnabled(true);
                }
            }
        });
        gmBuyGrains.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean selected = gmBuyGrains.isSelected();

                gmGrainsGEPercent.setEnabled(selected);
                gmBuyGrainsType.setEnabled(selected);
                gmPickGrainsAt.setEnabled(!selected);
            }
        });
        gmBuyPots.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean selected = gmBuyPots.isSelected();
                gmPotsGEPercent.setEnabled(selected);
                gmBuyPotsType.setEnabled(selected);
                gmMineClayAt.setEnabled(!selected);
            }
        });

        // Pots of Flour
        pofWindmillLocation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pofWindmillLocation.getUI().setPopupVisible(pofWindmillLocation, false);

                String selected = pofWindmillLocation.getSelectedItem().toString();
                WindmillLocation location = WindmillLocation.find(selected);

                pofBankingLocation.removeAllItems();
                pofBankingLocation.addItem("CLOSEST");

                for (BankLocation loc : location.supportedBanks) {
                    pofBankingLocation.addItem(loc.name());
                }

                pofBankingLocation.setSelectedIndex(0);
                pofWindmillLocation.getUI().setPopupVisible(pofWindmillLocation, false);
            }
        });

        Image warningIcon = getImageFromURL("https://icon-library.com/images/warning-icon-svg/warning-icon-svg-27.jpg", 50, 50);
        if (warningIcon != null) {
            ImageIcon icon = new ImageIcon(warningIcon);
            pofWarningIconLeft.setIcon(icon);
            pofWarningIconRight.setIcon(icon);
            pofWarningIconLeft.setText("");
            pofWarningIconRight.setText("");
        }

        // All in One
        if (warningIcon != null) {
            ImageIcon icon = new ImageIcon(warningIcon);
            aioWarningIconLeft.setIcon(icon);
            aioWarningIconRight.setIcon(icon);
            aioWarningIconRight.setText("");
            aioWarningIconLeft.setText("");
        }

        aioWindmillLocation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aioWindmillLocation.getUI().setPopupVisible(aioWindmillLocation, false);

                String selected = aioWindmillLocation.getSelectedItem().toString();
                WindmillLocation location = WindmillLocation.find(selected);

                aioBankingLocation.removeAllItems();
                aioBankingLocation.addItem("CLOSEST");

                for (BankLocation loc : location.supportedBanks) {
                    aioBankingLocation.addItem(loc.name());
                }

                aioBankingLocation.setSelectedIndex(0);
                aioWindmillLocation.getUI().setPopupVisible(aioWindmillLocation, false);
            }
        });
        aioMakePotsOfFlour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aioMakePotsOfCornflour.setEnabled(true);
                aioMakePotsOfFlour.setEnabled(false);
                aioStartButton.setEnabled(true);
                aioStartButton.setToolTipText("Start Making Pots of Flour");
            }
        });
        aioMakePotsOfCornflour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aioMakePotsOfFlour.setEnabled(true);
                aioMakePotsOfCornflour.setEnabled(false);
                aioStartButton.setEnabled(false);
                aioStartButton.setToolTipText("Pots of Cornflour not currently Supported.");
            }
        });
        aioPickGrainsEnabled.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean selected = aioPickGrainsEnabled.isSelected();

                aioPickGrainsAt.setEnabled(selected);
                aioGrainsHowMany.setEnabled(selected);
//                aioBuyGrains.setEnabled(selected);

                if (aioBuyGrains.isSelected() && selected) {
                    aioBuyGrainsType.setEnabled(true);
                    aioGrainsGEPercent.setEnabled(true);
                }
            }
        });
        aioMakePotsEnabled.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean selected = aioMakePotsEnabled.isSelected();

                aioMakePotsAt.setEnabled(selected);
                aioPotsHowMany.setEnabled(selected);
//                aioBuyPots.setEnabled(selected);
                aioMineClayAt.setEnabled(selected);

                if (aioBuyPots.isSelected() && selected) {
                    aioBuyPotsType.setEnabled(true);
                    aioPotsGEPercent.setEnabled(true);
                }
            }
        });

        // GE Cost Estimate
//        // TODO -- Move to a KeyListener for when we update the percent and/or item
//        int grainsHowMany = tabbedPane.getSelectedIndex() == 1 ? Integer.valueOf(aioGrainsHowMany.getText()) : Integer.valueOf(gmGrainsHowMany.getText());
//        int potsHowMany = tabbedPane.getSelectedIndex() == 1 ? Integer.valueOf(aioPotsHowMany.getText()) : Integer.valueOf(gmPotsHowMany.getText());;
//
//        int gePotsPercent = tabbedPane.getSelectedIndex() == 1 ? Integer.valueOf(aioPotsGEPercent.getText()) : Integer.valueOf(aioPotsGEPercent.getText());
//        int geGrainsPercent = tabbedPane.getSelectedIndex() == 1 ? Integer.valueOf(gmPotsGEPercent.getText()) : Integer.valueOf(gmPotsGEPercent.getText());
//
//        ItemIDs grains = ItemIDs.find(tabbedPane.getSelectedIndex() == 1 ? aioBuyGrainsType.getSelectedItem().toString() : gmBuyGrainsType.getSelectedItem().toString());
//        ItemIDs pots = ItemIDs.find(tabbedPane.getSelectedIndex() == 1 ? aioBuyPotsType.getSelectedItem().toString() : gmBuyPotsType.getSelectedItem().toString());
//
//        long grainsCost = (Math.round(grains.getGrandExchangePrice() / geGrainsPercent) + grains.getGrandExchangePrice()) * grainsHowMany;
//        long potsCost = (Math.round(pots.getGrandExchangePrice() / gePotsPercent) + pots.getGrandExchangePrice()) * potsHowMany;
//
//        aioGEGrainCostAmount.setText("Estimated Cost ( " + grainsCost + "gp)");
//        aioGEPotsCostAmount.setText("Estimated Cost ( " + potsCost + "gp)";
//        gmGEGrainsCostAmount.setText("Estimated Cost ( " + grainsCost + "gp)");
//        gmGEPotsCostAmount.setText("Estimated Cost ( " + potsCost + "gp)";

        // Updating Menus
        addUpdatingCostKeyAdapter(aioBuyGrains, aioGrainsHowMany, aioBuyGrainsType, aioGrainsGEPercent, aioGEGrainCostAmount);
        addUpdatingCostKeyAdapter(aioBuyPots, aioPotsHowMany, aioBuyPotsType, aioPotsGEPercent, aioGEPotsCostAmount);
        addUpdatingCostKeyAdapter(gmBuyGrains, gmGrainsHowMany, gmBuyGrainsType, gmGrainsGEPercent, gmGEGrainsCostAmount);
        addUpdatingCostKeyAdapter(gmBuyPots, gmPotsHowMany, gmBuyPotsType, gmPotsGEPercent, gmGEPotsCostAmount);

        // Start Buttons
        gmStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        pofMakeFlourPotsStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        potMakeCornflourStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        aioStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static Image getImageFromURL(String url, int width, int height) {
        try {
            Image img = ImageIO.read(new URL(url));

            return img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            MethodProvider.logError(e);
        }

        return null;
    }

    private void updatePrices(ItemIDs item, JRadioButton enabled, JTextField fhowMany, JTextField fgePercecnt, JLabel lcostAmount) {
        if (!enabled.isSelected()) return;

        int howMany = Integer.valueOf(fhowMany.getText());
        int percent = Integer.valueOf(fgePercecnt.getText());

        String label = "Estimated Cost ({cost}gp)";

        if (percent > 0) {
            int cost = item.getGrandExchangePrice() + (item.getGrandExchangePrice() * (percent / 100));
            label = label.replace("{cost}", String.valueOf(howMany * cost));
        } else {
           label = label.replace("{cost}", String.valueOf(howMany * item.getGrandExchangePrice()));
        }

        lcostAmount.setText(label);
    }

    private void addUpdatingCostKeyAdapter(JRadioButton enabled, JTextField numberField, JComboBox itemSelected, JTextField purchasePercent, JLabel updatingCostLabel) {
        numberField.addKeyListener(NUMBERS_ONLY_ADAPTER);
        purchasePercent.addKeyListener(NUMBERS_ONLY_ADAPTER);

        numberField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updatePrices(ItemIDs.find(itemSelected.getSelectedItem().toString()), enabled, numberField, purchasePercent, updatingCostLabel);
                super.keyReleased(e);
            }
        });

        itemSelected.addActionListener(e -> updatePrices(ItemIDs.find(itemSelected.getSelectedItem().toString()), enabled, numberField, purchasePercent, updatingCostLabel));

        purchasePercent.addActionListener(e -> updatePrices(ItemIDs.find(itemSelected.getSelectedItem().toString()), enabled, numberField, purchasePercent, updatingCostLabel));
    }
}
