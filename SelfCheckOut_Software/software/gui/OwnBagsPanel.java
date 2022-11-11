package ca.ucalgary.seng300.selfcheckout.software.gui;

import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationLogic;

public class OwnBagsPanel extends AbstractSCSGuiPanel {
	private static final long serialVersionUID = 4955828494835478006L;
	public javax.swing.JButton yesButton;
	public javax.swing.JButton noButton;
	public javax.swing.JLabel titleLabel;

	public OwnBagsPanel(SelfCheckoutStationLogic l) {
		super(l);

		initComponents();
	}

	private void initComponents() {

		titleLabel = new javax.swing.JLabel();
		yesButton = new javax.swing.JButton();
		noButton = new javax.swing.JButton();

		titleLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 36)); // NOI18N
		titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		titleLabel.setText("Do you have your own bags?");

		yesButton.setText("Yes, I have my own bags");

		noButton.setText("No, I don't have my own bags");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
						.addGroup(layout.createSequentialGroup()
								.addGap(292, 292, 292)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(yesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(noButton, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(73, 73, 73)
								.addComponent(yesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(73, 73, 73)
								.addComponent(noButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 180, Short.MAX_VALUE))
		);

		noButton.addActionListener(e -> scsl.scsi.cancelAddOwnBags());

		yesButton.addActionListener(e -> scsl.scsi.finishAddOwnBags());
	}
}
