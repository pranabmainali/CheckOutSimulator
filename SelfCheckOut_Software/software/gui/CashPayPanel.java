package SelfCheckOut_Software.software.gui;

import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationLogic;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CashPayPanel extends AbstractSCSGuiPanel {
	private static final long serialVersionUID = -3380590239433657467L;
	private JButton changePaymentTypeButton;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTextPane jTextPane1;
    private JTextPane jTextPane2;

    public CashPayPanel(SelfCheckoutStationLogic l) {
        super(l);

        initComponents();
    }

    public void setAmountDue(BigDecimal amt) {
        jTextPane1.setText("Amount due: $" + amt.multiply(new BigDecimal("2")).setScale(1, RoundingMode.HALF_UP).divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP).toString());
    }

    public void setAmountPaid(BigDecimal amt) {
        jTextPane2.setText("Amount paid: $" + amt.toString());
    }

    private void initComponents() {

        jLabel1 = new JLabel();
        jScrollPane1 = new JScrollPane();
        jTextPane1 = new JTextPane();
        jScrollPane2 = new JScrollPane();
        jTextPane2 = new JTextPane();
        changePaymentTypeButton = new JButton();

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel1.setText("Please insert cash or change");

        jTextPane1.setEditable(false);
        jTextPane1.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jTextPane1.setText("Amount due: $0");
        jScrollPane1.setViewportView(jTextPane1);

        jTextPane2.setEditable(false);
        jTextPane2.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jTextPane2.setText("Amount paid: $0.00");
        jScrollPane2.setViewportView(jTextPane2);

        changePaymentTypeButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        changePaymentTypeButton.setText("Change Payment Type");
        changePaymentTypeButton.setFocusPainted(false);
        changePaymentTypeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePaymentPressed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(changePaymentTypeButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(262, 262, 262)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 371, GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 322, GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addComponent(jLabel1)))
                .addContainerGap(231, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel1)
                .addGap(155, 155, 155)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 172, Short.MAX_VALUE)
                .addComponent(changePaymentTypeButton, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }

    private void changePaymentPressed(java.awt.event.ActionEvent evt) {
        scsl.scsi.exitCurrentPaymentMethod();
    }
}
