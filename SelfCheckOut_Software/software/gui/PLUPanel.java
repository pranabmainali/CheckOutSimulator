package SelfCheckOut_Software.software.gui;

import ca.ucalgary.seng300.selfcheckout.software.SelfCheckoutStationLogic;
import org.lsmr.selfcheckout.PriceLookupCode;
import org.lsmr.selfcheckout.external.ProductDatabases;
import org.lsmr.selfcheckout.products.PLUCodedProduct;

import javax.swing.*;

public class PLUPanel extends AbstractSCSGuiPanel {
	private static final long serialVersionUID = 3306147135659865009L;
	public JButton visCatButton;
    public JButton clearButton;
    public JButton lookupButton;
    public JButton backButton;
    public JButton jButton1;
    public JButton jButton2;
    public JButton jButton3;
    public JButton jButton4;
    public JButton jButton5;
    public JButton jButton6;
    public JButton jButton7;
    public JButton jButton8;
    public JButton jButton9;
    public JButton jButton0;
    public JButton addToCartButton;
    public JLabel label;
    public JLabel resultsLabel;
    public JLabel iteneranestnilabel;
    public JLabel jLabel4;
    public JLabel jLabel5;
    public JScrollPane jScrollPane1;
    public JScrollPane jScrollPane2;
    public JScrollPane pluNumberPaneScrollPane;
    public JTextPane itemNamePane;
    public JTextPane pricePane;
    public JTextPane pluNumberPane;

    private String pluNumber = "";

    /**
     * Creates new form PLUPanel
     */
    public PLUPanel(SelfCheckoutStationLogic l) {
        super(l);

        initComponents();
    }


    public void setItemNameText(String txt) {
        itemNamePane.setText(txt);
    }

    public void setPriceText(String txt) {
        pricePane.setText("Price: $" + txt);
    }

    public void setPLUNumText(String txt) {
    	pluNumberPane.setText(txt);
    }

    private void addDigit(String digit) {
        pluNumber = pluNumber + digit;
        setPLUNumText(pluNumber);
    }

    private void initComponents() {

        label = new javax.swing.JLabel();
        visCatButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton0 = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        lookupButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        itemNamePane = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        pricePane = new javax.swing.JTextPane();
        backButton = new javax.swing.JButton();
        pluNumberPaneScrollPane = new javax.swing.JScrollPane();
        pluNumberPane = new javax.swing.JTextPane();
        resultsLabel = new javax.swing.JLabel();
        iteneranestnilabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        addToCartButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        label.setFont(new java.awt.Font("Dialog", 0, 30)); // NOI18N
        label.setText("Enter the Price Look-Up code of the");

        visCatButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        visCatButton.setText("View Visual PLU Catalogue");
        visCatButton.setFocusPainted(false);
        visCatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                visCatButtonActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jButton1.setText("1");
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(evt -> addDigit("1"));

        jButton2.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jButton2.setText("2");
        jButton2.setFocusPainted(false);
        jButton2.addActionListener(evt -> addDigit("2"));

        jButton3.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jButton3.setText("3");
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(evt -> addDigit("3"));

        jButton4.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jButton4.setText("4");
        jButton4.setFocusPainted(false);
        jButton4.addActionListener(evt -> addDigit("4"));

        jButton5.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jButton5.setText("5");
        jButton5.setFocusPainted(false);
        jButton5.addActionListener(evt -> addDigit("5"));

        jButton6.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jButton6.setText("6");
        jButton6.setFocusPainted(false);
        jButton6.addActionListener(evt -> addDigit("6"));

        jButton7.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jButton7.setText("7");
        jButton7.setFocusPainted(false);
        jButton7.addActionListener(evt -> addDigit("7"));

        jButton8.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jButton8.setText("8");
        jButton8.setFocusPainted(false);
        jButton8.addActionListener(evt -> addDigit("8"));

        jButton9.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jButton9.setText("9");
        jButton9.setFocusPainted(false);
        jButton9.addActionListener(evt -> addDigit("9"));

        jButton0.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jButton0.setText("0");
        jButton0.setFocusPainted(false);
        jButton0.addActionListener(evt -> addDigit("0"));

        clearButton.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        clearButton.setText("Clear");
        clearButton.setFocusPainted(false);
        clearButton.addActionListener(evt -> clearButtonActionPerformed(evt));

        lookupButton.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        lookupButton.setText("Look Up");
        lookupButton.setFocusPainted(false);
        lookupButton.addActionListener(evt -> lookupButtonActionPerformed(evt));

        itemNamePane.setEditable(false);
        itemNamePane.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jScrollPane1.setViewportView(itemNamePane);

        pricePane.setEditable(false);
        pricePane.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jScrollPane2.setViewportView(pricePane);

        backButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        backButton.setText("Back to Scanning Screen");
        backButton.setFocusPainted(false);
        backButton.addActionListener(evt -> backButtonActionPerformed(evt));

        pluNumberPane.setEditable(false);
        pluNumberPane.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        pluNumberPaneScrollPane.setViewportView(pluNumberPane);

        resultsLabel.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        resultsLabel.setText("Results:");

        iteneranestnilabel.setFont(new java.awt.Font("Dialog", 0, 30)); // NOI18N
        iteneranestnilabel.setText("item you would like to query");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel4.setText("/kg");

        addToCartButton.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        addToCartButton.setText("Add to cart");
        addToCartButton.addActionListener(evt -> addToCartButtonActionPerformed(evt));

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel5.setText("$");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(backButton)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(visCatButton))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(177, 177, 177)
                                                                .addComponent(label)
                                                                .addGap(0, 0, Short.MAX_VALUE)))
                                                .addContainerGap())
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(6, 6, 6)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(pluNumberPaneScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(resultsLabel)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(jLabel4))
                                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(addToCartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(jButton0, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(18, 18, 18)
                                                                .addComponent(lookupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(101, 101, 101))))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(242, 242, 242)
                                .addComponent(iteneranestnilabel)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(label)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(iteneranestnilabel)
                                .addGap(49, 49, 49)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(pluNumberPaneScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(61, 61, 61)
                                                .addComponent(resultsLabel)
                                                .addGap(10, 10, 10)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(addToCartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(150, 150, 150)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel5)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(92, 92, 92)
                                                                                .addComponent(jLabel4))))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(12, 12, 12)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGap(18, 18, 18)
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                        .addComponent(jButton0, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                        .addComponent(lookupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(visCatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap())
        );
    }


    private void visCatButtonActionPerformed(java.awt.event.ActionEvent evt) {
        scsl.scsi.enterVisualPLU();
        setItemNameText("");
        pricePane.setText("");
        setPLUNumText("");
        pluNumber = "";
    }

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {
        pluNumber = "";
        setPLUNumText(pluNumber);
    }

    private void lookupButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (!ProductDatabases.PLU_PRODUCT_DATABASE.containsKey(new PriceLookupCode(pluNumber))) return;

        PLUCodedProduct product = ProductDatabases.PLU_PRODUCT_DATABASE.get(new PriceLookupCode(pluNumber));
        setItemNameText(product.getDescription());
        setPriceText(product.getPrice().toString());
    }

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	scsl.scsi.cancelPLU();
    	scsl.scsi.enterVisualPLU();
        setItemNameText("");
        pricePane.setText("");
        setPLUNumText("");
        pluNumber = "";
    }

    private void addToCartButtonActionPerformed(java.awt.event.ActionEvent evt) {
        scsl.scsi.enterPLU(new PriceLookupCode(pluNumber));
        scsl.scsi.enterVisualPLU();
        setItemNameText("");
        pricePane.setText("");
        setPLUNumText("");
        pluNumber = "";
    }
}
