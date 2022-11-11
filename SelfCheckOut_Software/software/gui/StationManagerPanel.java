package ca.ucalgary.seng300.selfcheckout.software.gui;

import ca.ucalgary.seng300.selfcheckout.attendant.AttendantStationGuiFacade;
import ca.ucalgary.seng300.selfcheckout.attendant.AttendantStationLogic;
import javax.swing.*;

public class StationManagerPanel extends JPanel {
	private static final long serialVersionUID = 8997732834234194242L;
	public JButton logoutButton;
    public JButton approveWeightButton;
    public JButton blockButton;
    public JButton unblockButton;
    public JButton removeItemButton;
    public JButton searchItemButton;
    public JComboBox<String> stationDropDown;
    public JLabel titleLabel;

	private final AttendantStationGuiFacade guiFacade;
	private final AttendantStationLogic asl;

    public StationManagerPanel(AttendantStationLogic a, AttendantStationGuiFacade gf) {
    	this.guiFacade = gf;
        this.asl = a;

        initComponents();
    }

    public void refreshComponents() {
    	initComponents();
    }


    public String getSelectedStation() {
        return (String) stationDropDown.getSelectedItem();
    }

	private void initComponents() {

    	stationDropDown = new JComboBox<>();
        titleLabel = new JLabel();
        logoutButton = new JButton();
        approveWeightButton = new JButton();
        blockButton = new JButton();
        unblockButton = new JButton();
        removeItemButton = new JButton();
        searchItemButton = new JButton();

        String[] managerString = new String[asl.getSCSLsize()];
        for (int i = 0; i < asl.getSCSLsize(); i++) {
        	managerString[i] = "Station Manager " + Integer.toString(i+1);
        }

        stationDropDown.setModel(new DefaultComboBoxModel<>(managerString));
        stationDropDown.addActionListener(evt -> jComboBox1ActionPerformed(evt));

        titleLabel.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        titleLabel.setText("Station Manager");

        logoutButton.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        logoutButton.setText("Logout");
        logoutButton.addActionListener(evt -> logoutButtonActionPerformed(evt));

        approveWeightButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        approveWeightButton.setText("Approve Weight");
        approveWeightButton.addActionListener(evt -> approveWeightButtonActionPerformed(evt));

        blockButton.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        blockButton.setText("Block");
        blockButton.addActionListener(evt -> blockButtonActionPerformed(evt));

        unblockButton.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        unblockButton.setText("Unblock");
        unblockButton.addActionListener(evt -> unblockButtonActionPerformed(evt));

        removeItemButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        removeItemButton.setText("Remove Item");
        removeItemButton.addActionListener(evt -> removeItemButtonActionPerformed(evt));

        searchItemButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        searchItemButton.setText("Search Item");
        searchItemButton.addActionListener(evt -> searchItemButtonActionPerformed(evt));

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(stationDropDown, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(blockButton, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                            .addComponent(unblockButton))
                        .addGap(18, 18, 18)
                        .addComponent(approveWeightButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeItemButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchItemButton))
                    .addComponent(logoutButton))
                .addContainerGap(101, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(stationDropDown, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(blockButton)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(approveWeightButton)
                        .addComponent(removeItemButton)
                        .addComponent(searchItemButton)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(unblockButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 396, Short.MAX_VALUE)
                .addComponent(logoutButton)
                .addContainerGap())
        );
    }

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
        //ignore this one
    }

    private void blockButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // get index of the selected station
        int id = Integer.parseInt(getSelectedStation().substring(getSelectedStation().length() - 1)) - 1;
        this.asl.asi.blockStation(id);
    }

    private void unblockButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // get index of the selected station
        int id = Integer.parseInt(getSelectedStation().substring(getSelectedStation().length() - 1)) - 1;
        this.asl.asi.unblockStation(id);
    }

    private void approveWeightButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // get index of the selected station
        int id = Integer.parseInt(getSelectedStation().substring(getSelectedStation().length() - 1)) - 1;
        this.asl.asi.approveWeight(id);
    }

    private void removeItemButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // get index of the selected station
        int id = Integer.parseInt(getSelectedStation().substring(getSelectedStation().length() - 1)) - 1;
        this.asl.asi.removeProduct(id, this.asl.asi.getLatestItem(id)); // idk what you want me to do here
    }

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        guiFacade.logoutOfManager();
    }

    private void searchItemButtonActionPerformed(java.awt.event.ActionEvent evt) {
        guiFacade.toVisCat();
    }
}
