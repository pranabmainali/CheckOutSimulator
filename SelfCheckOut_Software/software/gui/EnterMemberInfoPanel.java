package SelfCheckOut_Software.software.gui;

import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationLogic;

import javax.swing.*;

public class EnterMemberInfoPanel extends AbstractSCSGuiPanel {
	private static final long serialVersionUID = -4840032767275051723L;
	public JButton oneButton;
    public JButton twoButton;
    public JButton threeButton;
    public JButton fourButton;
    public JButton fiveButton;
    public JButton sixButton;
    public JButton eightButton;
    public JButton nineButton;
    public JButton sevenButton;
    public JButton zeroButton;
    public JButton clearButton;
    public JButton notAMemberButton;
    public JButton enterButton;
    public JLabel jLabel1;
    public JLabel jLabel2;
    public JScrollPane jScrollPane1;
    public JScrollPane jScrollPane2;
    public JScrollPane jScrollPane3;
    public JTextPane jTextPane1;

    private String memberNumber = "";

    /**
     * Creates new form NumBagsUsed
     */
    public EnterMemberInfoPanel(SelfCheckoutStationLogic l) {
        super(l);

        initComponents();
    }

    private void initComponents() {

        oneButton = new JButton();
        twoButton = new JButton();
        threeButton = new JButton();
        fourButton = new JButton();
        fiveButton = new JButton();
        sixButton = new JButton();
        eightButton = new JButton();
        nineButton = new JButton();
        sevenButton = new JButton();
        zeroButton = new JButton();
        clearButton = new JButton();
        notAMemberButton = new JButton();
        enterButton = new JButton();
        jScrollPane1 = new JScrollPane();
        jTextPane1 = new JTextPane();
        jScrollPane2 = new JScrollPane();
        jScrollPane3 = new JScrollPane();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();


        oneButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        oneButton.setText("1");
        oneButton.addActionListener(evt -> addDigit("1"));

        twoButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        twoButton.setText("2");
        twoButton.addActionListener(evt -> addDigit("2"));

        threeButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        threeButton.setText("3");
        threeButton.addActionListener(evt -> addDigit("3"));

        fourButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        fourButton.setText("4");
        fourButton.addActionListener(evt -> addDigit("4"));

        fiveButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        fiveButton.setText("5");
        fiveButton.addActionListener(evt -> addDigit("5"));

        sixButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        sixButton.setText("6");
        sixButton.addActionListener(evt -> addDigit("6"));

        eightButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        eightButton.setText("8");
        eightButton.addActionListener(evt -> addDigit("8"));

        nineButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        nineButton.setText("9");
        nineButton.addActionListener(evt -> addDigit("9"));

        sevenButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        sevenButton.setText("7");
        sevenButton.addActionListener(evt -> addDigit("7"));

        zeroButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        zeroButton.setText("0");
        zeroButton.addActionListener(evt -> addDigit("0"));

        clearButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        clearButton.setText("Clear");
        clearButton.addActionListener(evt -> clearButtonActionPerformed(evt));

        notAMemberButton.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        notAMemberButton.setText("Not a Member");
        notAMemberButton.addActionListener(evt -> notAMemberButtonActionPerformed(evt));

        enterButton.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        enterButton.setText("Enter");
        enterButton.addActionListener(evt -> enterButtonActionPerformed(evt));

        jTextPane1.setEditable(false);
        jTextPane1.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jScrollPane1.setViewportView(jTextPane1);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 42)); // NOI18N
        jLabel1.setText("Enter or scan your membership information");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel2.setText("Or click Not a Member to continue");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)))
                                .addGap(69, 69, 69)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addComponent(sevenButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(eightButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                                                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addComponent(fourButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(fiveButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)))
                                            .addComponent(clearButton, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(zeroButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(nineButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(sixButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addComponent(notAMemberButton, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(enterButton, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(oneButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(twoButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(threeButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(221, 221, 221)
                        .addComponent(jLabel2)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(86, 86, 86)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                        .addGap(96, 96, 96)
                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(oneButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                            .addComponent(twoButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                            .addComponent(threeButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(notAMemberButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(enterButton, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(fourButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fiveButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sixButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(sevenButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(eightButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nineButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(zeroButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(clearButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(109, Short.MAX_VALUE))
        );
    }

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {
        memberNumber = "";
        setMemNumText(memberNumber);
    }

    private void notAMemberButtonActionPerformed(java.awt.event.ActionEvent evt) {
        scsl.scsi.cancelScanMembershipCard();
    }

    private void enterButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (memberNumber.equals("")) scsl.scsi.cancelScanMembershipCard();
        else scsl.scsi.enterMembershipManually(memberNumber);
    }

    private void addDigit(String digit) {
        memberNumber = memberNumber + digit;
        setMemNumText(memberNumber);
    }

    public void setMemNumText(String txt) {
        jTextPane1.setText(txt);
    }
}
