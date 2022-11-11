package SelfCheckOut_Software.software.gui;

import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationLogic;

import javax.swing.*;

public class BeginPanel extends AbstractSCSGuiPanel {
	private static final long serialVersionUID = -3902159345245278010L;
	public JButton beginButton;
	public JLabel label;

	public BeginPanel(SelfCheckoutStationLogic l) {
		super(l);

		initComponents();
	}

	private void initComponents() {

		beginButton = new javax.swing.JButton();
		label = new javax.swing.JLabel();

		beginButton.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
		beginButton.setText("Begin");
		beginButton.addActionListener(evt -> scsl.scsi.startAddPhase());

		label.setFont(new java.awt.Font("Helvetica Neue", 0, 36)); // NOI18N
		label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		label.setText("Welcome to ShoppingCentre! ");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addContainerGap(388, Short.MAX_VALUE)
								.addComponent(beginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(365, 365, 365))
						.addComponent(label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								.addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(127, 127, 127)
								.addComponent(beginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(248, Short.MAX_VALUE))
		);
	}
}
