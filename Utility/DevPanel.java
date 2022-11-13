package Utility;

import SelfCheckOut_Software.software.SelfCheckoutStationLogic;
import SelfCheckOut_Hardware.*;
import SelfCheckOut_Hardware.devices.*;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.*;

public class DevPanel extends JPanel {
	private static final long serialVersionUID = 8138692584938206967L;
	public ButtonGroup cardTypeButtonGroup;
    public ButtonGroup itemButtonGroup;
    public JButton nickelButton;
    public JButton oneHundredButton;
    public JButton tapButton;
    public JButton insertButton;
    public JButton swipeButton;
    public JButton removeDanglingBanknoteButton;
    public JButton scanButton;
    public JButton placeInBaggingAreaButton;
    public JButton removeButton;
    public JButton placeOwnBagsButton;
    public JButton placeOnWeighScaleButton;
    public JButton dimeButton;
    public JButton quarterButton;
    public JButton loonieButton;
    public JButton toonieButton;
    public JButton fiveButton;
    public JButton tenButton;
    public JButton twentyButton;
    public JButton fiftyButton;
    public JLabel titleLabel;
    public JLabel cashLabel;
    public JLabel cardLabel;
    public JLabel miscLabel;
    public JLabel recieptLabel;
    public JRadioButton creditCardButton;
    public JRadioButton debitCardButton;
    public JRadioButton membershipButton;
    public JRadioButton appleButton;
    public JRadioButton milkButton;
    public JRadioButton bananasButton;
    public JRadioButton goldBarButton;
    public JRadioButton otherCardTypeButton;
    public JRadioButton giftCardButton;
    public JScrollPane recieptScrollPane;
    public JSpinner jSpinner1;
    public JTextField pinTextField;
    public JTextField numberTextField;
    public JTextField cardHolderTextField;
    public JTextField cvvTextField;
    public JTextPane recieptTextPane;
    public JTextPane jTextPane2;
    public JScrollPane jScrollPane2;
    public JLabel jLabel6;

    private final SelfCheckoutStation scs;
    private final SelfCheckoutStationLogic scsl;

    private final BarcodedItem milk, goldbar;
    private final PLUCodedItem apple, bananas;
    /**
     * Creates new form DevPanel
     */
    public DevPanel(SelfCheckoutStation s, SelfCheckoutStationLogic l) {
        new CustomerUsageUtilities();
        this.scs = s;
        this.scsl = l;
        milk = DatabaseUtilities.createBarcodedItem("1111", "Milk", new BigDecimal("3.75"), 10);
        goldbar = DatabaseUtilities.createBarcodedItem("2222", "Gold bar", new BigDecimal("999.99"), 100);

        apple = DatabaseUtilities.createPLUCodedItem("3000", "Apple", new BigDecimal("1.50"), 2d);
        bananas = DatabaseUtilities.createPLUCodedItem("4011", "Bananas", new BigDecimal("3.00"), 5d);

        initComponents();
    }

    public int getSpinnerValue() {
        return (Integer)jSpinner1.getValue();
    }

    public String getPINText() {
        return pinTextField.getText();
    }

    public String getCVVText() {
        return cvvTextField.getText();
    }

    public String getNumberText() {
        return numberTextField.getText();
    }

    public String getHolderText() {
        return cardHolderTextField.getText();
    }
    
    public String getCardPayAmountText() {
    	return jTextPane2.getText();
    }

    public String getCardType() { //https://stackoverflow.com/questions/201287/how-do-i-get-which-jradiobutton-is-selected-from-a-buttongroup
        for (Enumeration<AbstractButton> buttons = cardTypeButtonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText().toLowerCase();
            }
        }
        return null;
    }

    public String getItemSelected() {
        for (Enumeration<AbstractButton> buttons = itemButtonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText().toLowerCase();
            }
        }
        return null;
    }

    public void setReceiptText(String txt) {
        recieptTextPane.setText(txt);
    }

    private void initComponents() {

    	cardTypeButtonGroup = new ButtonGroup();
        itemButtonGroup = new ButtonGroup();
        titleLabel = new JLabel();
        cashLabel = new JLabel();
        cardLabel = new JLabel();
        miscLabel = new JLabel();
        recieptLabel = new JLabel();
        nickelButton = new JButton();
        dimeButton = new JButton();
        quarterButton = new JButton();
        loonieButton = new JButton();
        toonieButton = new JButton();
        fiveButton = new JButton();
        tenButton = new JButton();
        twentyButton = new JButton();
        fiftyButton = new JButton();
        oneHundredButton = new JButton();
        creditCardButton = new JRadioButton();
        debitCardButton = new JRadioButton();
        membershipButton = new JRadioButton();
        pinTextField = new JTextField();
        tapButton = new JButton();
        insertButton = new JButton();
        swipeButton = new JButton();
        numberTextField = new JTextField();
        cardHolderTextField = new JTextField();
        cvvTextField = new JTextField();
        removeDanglingBanknoteButton = new JButton();
        scanButton = new JButton();
        placeInBaggingAreaButton = new JButton();
        removeButton = new JButton();
        appleButton = new JRadioButton();
        milkButton = new JRadioButton();
        placeOwnBagsButton = new JButton();
        bananasButton = new JRadioButton();
        goldBarButton = new JRadioButton();
        recieptScrollPane = new JScrollPane();
        recieptTextPane = new JTextPane();
        jSpinner1 = new JSpinner();
        otherCardTypeButton = new JRadioButton();
        giftCardButton = new JRadioButton();
        placeOnWeighScaleButton = new JButton();
        jScrollPane2 = new JScrollPane();
        jTextPane2 = new JTextPane();
        jLabel6 = new JLabel();
        

        titleLabel.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        titleLabel.setText("SIMULATION CONTROL PANEL");

        cashLabel.setText("Cash Payments");

        cardLabel.setText("Card Payments");

        miscLabel.setText("Misc Functions");

        recieptLabel.setText("Reciept Output");

        nickelButton.setText("$0.05 Nickel");
        nickelButton.setFocusPainted(false);
        nickelButton.addActionListener(evt -> {
            try {
                nickelButtonActionPerformed(evt);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        dimeButton.setText("$0.10 Dime");
        dimeButton.setFocusPainted(false);
        dimeButton.addActionListener(evt -> {
            try {
                dimeButtonActionPerformed(evt);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        quarterButton.setText("$0.25 Quarter");
        quarterButton.setFocusPainted(false);
        quarterButton.addActionListener(evt -> {
            try {
                quarterButtonActionPerformed(evt);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        loonieButton.setText("$1 Loonie");
        loonieButton.setFocusPainted(false);
        loonieButton.addActionListener(evt -> {
            try {
                loonieButtonActionPerformed(evt);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        toonieButton.setText("$2 Toonie");
        toonieButton.setFocusPainted(false);
        toonieButton.addActionListener(evt -> {
            try {
                toonieButtonActionPerformed(evt);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        fiveButton.setText("$5 Bill");
        fiveButton.setFocusPainted(false);
        fiveButton.addActionListener(evt -> {
            try {
                fiveButtonActionPerformed(evt);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        tenButton.setText("$10 Bill");
        tenButton.setFocusPainted(false);
        tenButton.addActionListener(evt -> {
            try {
                tenButtonActionPerformed(evt);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        twentyButton.setText("$20 Bill");
        twentyButton.setFocusPainted(false);
        twentyButton.addActionListener(evt -> {
            try {
                twentyButtonActionPerformed(evt);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        fiftyButton.setText("$50 Bill");
        fiftyButton.setFocusPainted(false);
        fiftyButton.addActionListener(evt -> {
            try {
                fiftyButtonActionPerformed(evt);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        oneHundredButton.setText("$100 Bill");
        oneHundredButton.setFocusPainted(false);

        cardTypeButtonGroup.add(creditCardButton);
        creditCardButton.setSelected(true);
        creditCardButton.setText("Credit");

        cardTypeButtonGroup.add(debitCardButton);
        debitCardButton.setText("Debit");

        cardTypeButtonGroup.add(membershipButton);
        membershipButton.setText("Membership");

        pinTextField.setText("PIN");

        tapButton.setText("Tap");
        tapButton.setFocusPainted(false);
        tapButton.addActionListener(evt -> {
            try {
                tapButtonActionPerformed(evt);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        insertButton.setText("Insert");
        insertButton.setFocusPainted(false);
        insertButton.addActionListener(evt -> {
            try {
                insertButtonActionPerformed(evt);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        swipeButton.setText("Swipe");
        swipeButton.setFocusPainted(false);
        swipeButton.addActionListener(evt -> {
            try {
                swipeButtonActionPerformed(evt);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        numberTextField.setText("Number");

        cardHolderTextField.setText("Holder");

        cvvTextField.setText("CVV");

        removeDanglingBanknoteButton.setText("Remove Dangling Note(s)");
        removeDanglingBanknoteButton.setFocusPainted(false);
        removeDanglingBanknoteButton.addActionListener(evt -> removeDanglingBanknoteButtonActionPerformed(evt));

        scanButton.setText("Scan");
        scanButton.setFocusPainted(false);
        scanButton.addActionListener(evt -> scanButtonActionPerformed(evt));

        placeInBaggingAreaButton.setText("Place in bagging area");
        placeInBaggingAreaButton.setFocusPainted(false);
        placeInBaggingAreaButton.addActionListener(evt -> {
            try {
                placeInBaggingAreaButtonActionPerformed(evt);
            } catch (OverloadException e) {
                e.printStackTrace();
            }
        });

        removeButton.setText("Remove");
        removeButton.setFocusPainted(false);
        removeButton.addActionListener(evt -> removeButtonActionPerformed(evt));

        itemButtonGroup.add(appleButton);
        appleButton.setSelected(true);
        appleButton.setText("Apple ($1.50)/kg");

        itemButtonGroup.add(milkButton);
        milkButton.setText("Milk ($3.75)");

        placeOwnBagsButton.setText("Place Own Bag(s)");
        placeOwnBagsButton.setFocusPainted(false);
        placeOwnBagsButton.addActionListener(evt -> placeOwnBagsButtonActionPerformed(evt));

        itemButtonGroup.add(bananasButton);
        bananasButton.setText("Bananas ($3.00)/kg");

        itemButtonGroup.add(goldBarButton);
        goldBarButton.setText("Gold Bar ($999.99)");

        recieptTextPane.setEditable(false);
        recieptScrollPane.setViewportView(recieptTextPane);
        recieptTextPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN,  14));

        cardTypeButtonGroup.add(otherCardTypeButton);
        otherCardTypeButton.setText("Other (Invalid)");

        cardTypeButtonGroup.add(giftCardButton);
        giftCardButton.setText("Gift Card");

        placeOnWeighScaleButton.setText("Place on weigh scale");
        placeOnWeighScaleButton.setFocusPainted(false);
        placeOnWeighScaleButton.addActionListener(evt -> placeOnWeighScaleButtonActionPerformed(evt));
        
        jTextPane2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jScrollPane2.setViewportView(jTextPane2);

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel6.setText("Amount: $");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(titleLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addComponent(cashLabel)
                                .addGap(157, 157, 157)
                                .addComponent(cardLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(tenButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(toonieButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(nickelButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(quarterButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(fiftyButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addComponent(oneHundredButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(dimeButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(loonieButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(fiveButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(twentyButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(49, 49, 49))
                                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(tapButton)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(insertButton)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(swipeButton))
                                    .addComponent(otherCardTypeButton)
                                    .addComponent(creditCardButton)
                                    .addComponent(debitCardButton)
                                    .addComponent(membershipButton)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(pinTextField, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                                                .addGap(21, 21, 21)
                                                .addComponent(cvvTextField, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
                                            .addComponent(numberTextField)
                                            .addComponent(cardHolderTextField)))
                                    .addComponent(giftCardButton))))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(removeDanglingBanknoteButton)
                            .addComponent(appleButton)
                            .addComponent(milkButton)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(placeOwnBagsButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSpinner1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addComponent(bananasButton)
                            .addComponent(goldBarButton)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(miscLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(scanButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(removeButton, GroupLayout.Alignment.LEADING))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(placeInBaggingAreaButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(placeOnWeighScaleButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(recieptLabel)
                    .addComponent(recieptScrollPane, GroupLayout.PREFERRED_SIZE, 478, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(titleLabel)
                    .addComponent(recieptLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(cashLabel)
                            .addComponent(cardLabel)
                            .addComponent(miscLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(removeDanglingBanknoteButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(placeOwnBagsButton)
                                    .addComponent(jSpinner1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(appleButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(milkButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bananasButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(goldBarButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(scanButton)
                                    .addComponent(placeInBaggingAreaButton))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(placeOnWeighScaleButton)
                                    .addComponent(removeButton)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(nickelButton)
                                    .addComponent(dimeButton)
                                    .addComponent(creditCardButton))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(quarterButton)
                                    .addComponent(loonieButton)
                                    .addComponent(debitCardButton))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(toonieButton)
                                    .addComponent(fiveButton)
                                    .addComponent(membershipButton))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(tenButton)
                                    .addComponent(twentyButton)
                                    .addComponent(giftCardButton))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(fiftyButton)
                                    .addComponent(oneHundredButton)
                                    .addComponent(otherCardTypeButton))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(pinTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cvvTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numberTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cardHolderTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(swipeButton)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(tapButton)
                                        .addComponent(insertButton))
                                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(recieptScrollPane))
                .addContainerGap())
        );
    }

    private void scanButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (Objects.equals(getItemSelected(), "Milk ($3.75)".toLowerCase())){
            CustomerUsageUtilities.scanItem(milk,scs.mainScanner,scsl);
        }
        else if(Objects.equals(getItemSelected(), "Gold Bar ($999.99)".toLowerCase())){
            CustomerUsageUtilities.scanItem(goldbar,scs.mainScanner,scsl);
        }
    }

    private void nickelButtonActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
        CustomerUsageUtilities.insertCoin(new Coin(Currency.getInstance("CAD"), new BigDecimal("0.05")), scs.coinSlot, scs.coinTray, scsl);
    }

    private void dimeButtonActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
        CustomerUsageUtilities.insertCoin(new Coin(Currency.getInstance("CAD"), new BigDecimal("0.10")), scs.coinSlot, scs.coinTray, scsl);
    }

    private void quarterButtonActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
        CustomerUsageUtilities.insertCoin(new Coin(Currency.getInstance("CAD"), new BigDecimal("0.25")), scs.coinSlot, scs.coinTray, scsl);
    }

    private void loonieButtonActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
        CustomerUsageUtilities.insertCoin(new Coin(Currency.getInstance("CAD"), new BigDecimal("1.00")), scs.coinSlot, scs.coinTray, scsl);
    }

    private void toonieButtonActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
        CustomerUsageUtilities.insertCoin(new Coin(Currency.getInstance("CAD"), new BigDecimal("2.00")), scs.coinSlot, scs.coinTray, scsl);
    }

    private void fiveButtonActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
        CustomerUsageUtilities.insertBanknote(new Banknote(Currency.getInstance("CAD"), 5), scs.banknoteInput, scsl);
    }

    private void tenButtonActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
        CustomerUsageUtilities.insertBanknote(new Banknote(Currency.getInstance("CAD"), 10), scs.banknoteInput, scsl);
    }

    private void twentyButtonActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
        CustomerUsageUtilities.insertBanknote(new Banknote(Currency.getInstance("CAD"), 20), scs.banknoteInput, scsl);
    }

    private void fiftyButtonActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
        CustomerUsageUtilities.insertBanknote(new Banknote(Currency.getInstance("CAD"), 50), scs.banknoteInput, scsl);
    }


    private void tapButtonActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
        System.out.println("CVV: " + getCVVText());
        System.out.println("PIN: " + getPINText());
        System.out.println("Number: " + getNumberText());
        System.out.println("Holder: " + getHolderText());
        System.out.println("Type: " + getCardType());
        scsl.pf.setPaymentAmount(new BigDecimal(getCardPayAmountText()));
        BigDecimal diff = (scsl.pf.getTotalCost().subtract(scsl.pf.getTotalFunds()));
        if (new BigDecimal(getCardPayAmountText()).compareTo(diff) >= 0) scsl.pf.setPaymentAmount(diff);
        CustomerUsageUtilities.tapCard(new Card(getCardType(), getNumberText(), getHolderText(),
                getCVVText(), getPINText(), true, true), scs.cardReader, scsl);
    }

    private void swipeButtonActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
        System.out.println("CVV: " + getCVVText());
        System.out.println("PIN: " + getPINText());
        System.out.println("Number: " + getNumberText());
        System.out.println("Holder: " + getHolderText());
        System.out.println("Type: " + getCardType());
        scsl.pf.setPaymentAmount(new BigDecimal(getCardPayAmountText()));
        BigDecimal diff = (scsl.pf.getTotalCost().subtract(scsl.pf.getTotalFunds()));
        if (new BigDecimal(getCardPayAmountText()).compareTo(diff) >= 0) scsl.pf.setPaymentAmount(diff);
        CustomerUsageUtilities.swipeCard(new Card(getCardType(), getNumberText(), getHolderText(),
                getCVVText(), getPINText(), true, true),scs.cardReader, scsl);
    }

    private void insertButtonActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
        System.out.println("CVV: " + getCVVText());
        System.out.println("PIN: " + getPINText());
        System.out.println("Number: " + getNumberText());
        System.out.println("Holder: " + getHolderText());
        System.out.println("Type: " + getCardType());
        scsl.pf.setPaymentAmount(new BigDecimal(getCardPayAmountText()));
        BigDecimal diff = (scsl.pf.getTotalCost().subtract(scsl.pf.getTotalFunds()));
        if (new BigDecimal(getCardPayAmountText()).compareTo(diff) >= 0) scsl.pf.setPaymentAmount(diff);
        CustomerUsageUtilities.insertCard(new Card(getCardType(), getNumberText(), getHolderText(),
                getCVVText(), getPINText(), true, true), getPINText(),scs.cardReader, scsl);
    }

    private void removeDanglingBanknoteButtonActionPerformed(java.awt.event.ActionEvent evt) {
        scs.banknoteOutput.removeDanglingBanknotes();
    }

    private void placeOwnBagsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        scs.baggingArea.add(new Bag(5*getSpinnerValue()));

        System.out.println(getSpinnerValue() + " bags");
    }

    private void placeInBaggingAreaButtonActionPerformed(java.awt.event.ActionEvent evt) throws OverloadException {
        if (Objects.equals(getItemSelected(), "Milk ($3.75)".toLowerCase())){
            scs.baggingArea.add(milk);
        }
        else if(Objects.equals(getItemSelected(), "Gold Bar ($999.99)".toLowerCase())){
            scs.baggingArea.add(goldbar);
        }
        else if (Objects.equals(getItemSelected(), "Apple ($1.50)/kg".toLowerCase())){
            if (scs.scanningArea.getCurrentWeight() >= apple.getWeight() && scs.scanningArea.getCurrentWeight() <= apple.getWeight() + 0.1)
                scs.scanningArea.remove(apple);
            scs.baggingArea.add(apple);
        }
        else {
            if (scs.scanningArea.getCurrentWeight() >= bananas.getWeight() && scs.scanningArea.getCurrentWeight() <= bananas.getWeight() + 0.1)
                scs.scanningArea.remove(bananas);
            scs.baggingArea.add(bananas);
        }
    }

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (Objects.equals(getItemSelected(), "Milk ($3.75)".toLowerCase())){
            scs.baggingArea.remove(milk);
        }
        else if(Objects.equals(getItemSelected(), "Gold Bar ($999.99)".toLowerCase())){
            scs.baggingArea.remove(goldbar);
        }
        else if (Objects.equals(getItemSelected(), "Apple ($1.50)/kg".toLowerCase())){
            scs.baggingArea.remove(apple);
        }
        else {
            scs.baggingArea.remove(bananas);
        }
    }

    private void placeOnWeighScaleButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	if (Objects.equals(getItemSelected(), "Apple ($1.50)/kg".toLowerCase())) {
            scs.scanningArea.add(apple);
        }
        else if (Objects.equals(getItemSelected(), "Bananas ($3.00)/kg".toLowerCase())) {
            scs.scanningArea.add(bananas);
        }
    }
}
