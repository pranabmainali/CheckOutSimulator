/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ca.ucalgary.seng300.selfcheckout.software.gui;

import org.lsmr.selfcheckout.PriceLookupCode;

import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationLogic;

/**
 *
 * @author matth
 */
public class PLUVisCatBb extends AbstractSCSGuiPanel {
	private static final long serialVersionUID = 2981187273039934052L;
	/**
     * Creates new form PLUVisCat
     */
    public PLUVisCatBb(SelfCheckoutStationLogic l) {
    	super(l);
    	
        initComponents();
    }
    
    public String getSearchedLetter() {
    	return ((String)letterComboBox.getSelectedItem()).toLowerCase();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        letterComboBox = new javax.swing.JComboBox<>();
        searchButton = new javax.swing.JButton();
        backToScanButton = new javax.swing.JButton();
        bananaButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 26)); // NOI18N
        jLabel1.setText("Search for a non-barcoded item via the first letter of its name:");

        letterComboBox.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        letterComboBox.setMaximumRowCount(26);
        letterComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aa", "Bb", "Cc", "Dd", "Ee", "Ff", "Gg", "Hh", "Ii", "Jj", "Kk", "Ll", "Mm", "Nn", "Oo", "Pp", "Qq", "Rr", "Ss", "Tt", "Uu", "Vv", "Ww", "Xx", "Yy", "Zz" }));
        letterComboBox.setSelectedItem("Bb");

        searchButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        backToScanButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        backToScanButton.setText("Back to PLU Keypad");
        backToScanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        bananaButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/banana200px.png"))); // NOI18N
        bananaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel2.setText("Banana: $3.00/kg");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(backToScanButton)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(letterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(bananaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(172, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchButton)
                    .addComponent(letterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bananaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 262, Short.MAX_VALUE)
                .addComponent(backToScanButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    	if (getSearchedLetter().equals("aa")) {
        	scsl.scsi.enterVisualPLUA();
        	letterComboBox.setSelectedItem("Bb");
        } else if (getSearchedLetter().equals("bb")) {
        	//already here
        	letterComboBox.setSelectedItem("Bb");
        } else {
        	scsl.scsi.enterVisualPLUNone();
        	letterComboBox.setSelectedItem("Bb");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    	scsl.scsi.exitVisualPLU();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    	scsl.scsi.enterPLU(new PriceLookupCode("4011"));
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton searchButton;
    public javax.swing.JButton backToScanButton;
    public javax.swing.JButton bananaButton;
    public javax.swing.JComboBox<String> letterComboBox;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
