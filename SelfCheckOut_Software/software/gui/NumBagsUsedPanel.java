package ca.ucalgary.seng300.selfcheckout.software.gui;

import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationLogic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class NumBagsUsedPanel extends AbstractSCSGuiPanel {
	private static final long serialVersionUID = -9141370944569702513L;
	public javax.swing.JButton oneButton;
    public javax.swing.JButton zeroButton;
    public javax.swing.JButton jButtonclear;
    public javax.swing.JButton jButtonNobags;
    public javax.swing.JButton jButtonEnter;
    public javax.swing.JButton twoButton;
    public javax.swing.JButton threeButton;
    public javax.swing.JButton fourButton;
    public javax.swing.JButton fiveButton;
    public javax.swing.JButton sixButton;
    public javax.swing.JButton eightButton;
    public javax.swing.JButton nineButton;
    public javax.swing.JButton sevenButton;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTextPane jTextPane1;
    public javax.swing.JTextPane jTextPane2;
    public javax.swing.JTextPane jTextPane3;

    private String bagsusedString;
    public int plasticBagsUsed;
    private BigDecimal bagprice = new BigDecimal("0.05");
    private BigDecimal totalcost;
    private BigDecimal temp;
    private BigDecimal amountpaid;

    public NumBagsUsedPanel(SelfCheckoutStationLogic l) {

        super(l);

        initComponents();
        bagsusedString = "";
        plasticBagsUsed = 0;
        totalcost = scsl.pf.getTotalCost();
        amountpaid = scsl.pf.getTotalFunds();
        setAmtDueText(totalcost);
        setAmtPaidText(amountpaid.toString());
    }

    public void setNumBagsText(String txt) {
        jTextPane1.setText(txt);
    }

    public void setAmtDueText(BigDecimal txt) {
        jTextPane2.setText("Amount due: $"+txt.multiply(new BigDecimal("2")).setScale(1, RoundingMode.HALF_UP).divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP).toString());
    }

    public void setAmtPaidText(String txt) {
        jTextPane3.setText("Amount paid: $"+txt);
    }

    private void initComponents() {

        oneButton = new javax.swing.JButton();
        twoButton = new javax.swing.JButton();
        threeButton = new javax.swing.JButton();
        fourButton = new javax.swing.JButton();
        fiveButton = new javax.swing.JButton();
        sixButton = new javax.swing.JButton();
        eightButton = new javax.swing.JButton();
        nineButton = new javax.swing.JButton();
        sevenButton = new javax.swing.JButton();
        zeroButton = new javax.swing.JButton();
        jButtonclear = new javax.swing.JButton();
        jButtonNobags = new javax.swing.JButton();
        jButtonEnter = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane3 = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();


        oneButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        oneButton.setText("1");
        oneButton.addActionListener(evt -> oneButtonActionPerformed(evt));

        twoButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        twoButton.setText("2");
        twoButton.addActionListener(evt -> twoButtonActionPerformed(evt));

        threeButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        threeButton.setText("3");
        threeButton.addActionListener(evt -> threeButtonActionPerformed(evt));

        fourButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        fourButton.setText("4");
        fourButton.addActionListener(evt -> fourButtonActionPerformed(evt));

        fiveButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        fiveButton.setText("5");
        fiveButton.addActionListener(evt -> fiveButtonActionPerformed(evt));

        sixButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        sixButton.setText("6");
        sixButton.addActionListener(evt -> sixButtonActionPerformed(evt));

        eightButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        eightButton.setText("8");
        eightButton.addActionListener(evt -> eightButtonActionPerformed(evt));

        nineButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        nineButton.setText("9");
        nineButton.addActionListener(evt -> nineButtonActionPerformed(evt));

        sevenButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        sevenButton.setText("7");
        sevenButton.addActionListener(evt -> sevenButtonActionPerformed(evt));

        zeroButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        zeroButton.setText("0");
        zeroButton.addActionListener(evt -> zeroButtonActionPerformed(evt));

        jButtonclear.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jButtonclear.setText("Clear");
        jButtonclear.addActionListener(evt -> jButtonclearActionPerformed(evt));

        jButtonNobags.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jButtonNobags.setText("No Bags");
        jButtonNobags.addActionListener(evt -> jButtonNobagsActionPerformed(evt));

        jButtonEnter.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jButtonEnter.setText("Enter");
        jButtonEnter.addActionListener(evt -> jButtonEnterActionPerformed(evt));

        jTextPane1.setEditable(false);
        jTextPane1.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jScrollPane1.setViewportView(jTextPane1);

        jTextPane2.setEditable(false);
        jTextPane2.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jScrollPane2.setViewportView(jTextPane2);

        jTextPane3.setEditable(false);
        jTextPane3.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jScrollPane3.setViewportView(jTextPane3);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 56)); // NOI18N
        jLabel1.setText("Enter # of bags used");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel2.setText("A fee is charged per plastic bag used");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(jLabel1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(12, 12, 12)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(sevenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(eightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(fourButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(fiveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(jButtonclear, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(zeroButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nineButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(sixButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButtonNobags, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButtonEnter, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(oneButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(twoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(threeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(86, 86, 86)
                            .addComponent(jLabel2))))
                .addContainerGap(189, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(86, 86, 86)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(96, 96, 96)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(oneButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(twoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(threeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonNobags, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonEnter, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(fourButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fiveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sixButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(sevenButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(eightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nineButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(zeroButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonclear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(76, Short.MAX_VALUE))
        );
    }

    private void oneButtonActionPerformed(java.awt.event.ActionEvent evt) {
        bagsusedString = bagsusedString + "1";
        setNumBagsText(bagsusedString);
        plasticBagsUsed = Integer.parseInt(bagsusedString);
        temp = totalcost.add(bagprice.multiply(new BigDecimal(plasticBagsUsed)));
        setAmtDueText(temp);
        setAmtPaidText(amountpaid.toString());
    }

    private void twoButtonActionPerformed(java.awt.event.ActionEvent evt) {
        bagsusedString = bagsusedString + "2";
        setNumBagsText(bagsusedString);
        plasticBagsUsed = Integer.parseInt(bagsusedString);
        temp = totalcost.add(bagprice.multiply(new BigDecimal(plasticBagsUsed)));
        setAmtDueText(temp);
        setAmtPaidText(amountpaid.toString());
    }

    private void threeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        bagsusedString = bagsusedString + "3";
        setNumBagsText(bagsusedString);
        plasticBagsUsed = Integer.parseInt(bagsusedString);
        temp = totalcost.add(bagprice.multiply(new BigDecimal(plasticBagsUsed)));
        setAmtDueText(temp);
        setAmtPaidText(amountpaid.toString());
    }

    private void fourButtonActionPerformed(java.awt.event.ActionEvent evt) {
        bagsusedString = bagsusedString + "4";
        setNumBagsText(bagsusedString);
        plasticBagsUsed = Integer.parseInt(bagsusedString);
        temp = totalcost.add(bagprice.multiply(new BigDecimal(plasticBagsUsed)));
        setAmtDueText(temp);
        setAmtPaidText(amountpaid.toString());
    }

    private void fiveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        bagsusedString = bagsusedString + "5";
        setNumBagsText(bagsusedString);
        plasticBagsUsed = Integer.parseInt(bagsusedString);
        temp = totalcost.add(bagprice.multiply(new BigDecimal(plasticBagsUsed)));
        setAmtDueText(temp);
        setAmtPaidText(amountpaid.toString());
    }

    private void sixButtonActionPerformed(java.awt.event.ActionEvent evt) {
        bagsusedString = bagsusedString + "6";
        setNumBagsText(bagsusedString);
        plasticBagsUsed = Integer.parseInt(bagsusedString);
        temp = totalcost.add(bagprice.multiply(new BigDecimal(plasticBagsUsed)));
        setAmtDueText(temp);
        setAmtPaidText(amountpaid.toString());
    }

    private void eightButtonActionPerformed(java.awt.event.ActionEvent evt) {
        bagsusedString = bagsusedString + "8";
        setNumBagsText(bagsusedString);
        plasticBagsUsed = Integer.parseInt(bagsusedString);
        temp = totalcost.add(bagprice.multiply(new BigDecimal(plasticBagsUsed)));
        setAmtDueText(temp);
        setAmtPaidText(amountpaid.toString());
    }

    private void nineButtonActionPerformed(java.awt.event.ActionEvent evt) {
        bagsusedString = bagsusedString + "9";
        setNumBagsText(bagsusedString);
        plasticBagsUsed = Integer.parseInt(bagsusedString);
        temp = totalcost.add(bagprice.multiply(new BigDecimal(plasticBagsUsed)));
        setAmtDueText(temp);
        setAmtPaidText(amountpaid.toString());
    }

    private void sevenButtonActionPerformed(java.awt.event.ActionEvent evt) {
        bagsusedString = bagsusedString + "7";
        setNumBagsText(bagsusedString);
        plasticBagsUsed = Integer.parseInt(bagsusedString);
        temp = totalcost.add(bagprice.multiply(new BigDecimal(plasticBagsUsed)));
        setAmtDueText(temp);
        setAmtPaidText(amountpaid.toString());
    }

    private void zeroButtonActionPerformed(java.awt.event.ActionEvent evt) {
        bagsusedString = bagsusedString + "0";
        setNumBagsText(bagsusedString);
        plasticBagsUsed = Integer.parseInt(bagsusedString);
        temp = totalcost.add(bagprice.multiply(new BigDecimal(plasticBagsUsed)));
        setAmtDueText(temp);
        setAmtPaidText(amountpaid.toString());
    }

    private void jButtonclearActionPerformed(java.awt.event.ActionEvent evt) {
        bagsusedString = "";
        setNumBagsText(bagsusedString);
        setAmtDueText(totalcost);
    }

    private void jButtonNobagsActionPerformed(java.awt.event.ActionEvent evt) {
        scsl.scsi.setNumberBags(0);
    }

    private void jButtonEnterActionPerformed(java.awt.event.ActionEvent evt) {
        if (Objects.equals(bagsusedString, "")){
            plasticBagsUsed = 0;
        }
        totalcost = temp; //Since they pressed enter, the temp big decimal that was thrown around becomes the new cost
        int tempBags = plasticBagsUsed;
        plasticBagsUsed = 0;
        bagsusedString = "";
        setNumBagsText(bagsusedString);
        scsl.scsi.setNumberBags(tempBags);
    }

    public void updateFields() {
        totalcost = scsl.pf.getTotalCost();
        amountpaid = scsl.pf.getTotalFunds();
        setAmtDueText(totalcost);
        setAmtPaidText(amountpaid.toString());
    }
}
