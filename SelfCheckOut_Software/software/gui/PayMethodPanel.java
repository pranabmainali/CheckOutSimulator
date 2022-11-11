package SelfCheckOut_Software.software.gui;

import SelfCheckOut_Software.software.SelfCheckoutStationLogic;

import javax.swing.*;

public class PayMethodPanel extends AbstractSCSGuiPanel {
	private static final long serialVersionUID = -360038696862702512L;
	private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JLabel jLabel1;

    public PayMethodPanel(SelfCheckoutStationLogic l) {
        super(l);

        initComponents();
    }

    private void initComponents() {

    	jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel1.setText("Select a payment method");

        jButton1.setBackground(new java.awt.Color(2, 115, 196));
        jButton1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jButton1.setText("Gift Card");
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(16, 138, 0));
        jButton2.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jButton2.setText("Credit/Debit");
        jButton2.setFocusPainted(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(150, 150, 0));
        jButton3.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jButton3.setText("Cash");
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jButton4.setText("Back to Scan Items");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(238, 238, 238)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton4)))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel1)
                .addGap(124, 124, 124)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addContainerGap())
        );
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        scsl.scsi.selectGiftPayment();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        scsl.scsi.selectDebitPayment();
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        scsl.scsi.selectCashPayment();
    }
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        scsl.scsi.addMoreItems();
    }
}
