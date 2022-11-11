/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package SelfCheckOut_Software.software.gui;

import java.util.ArrayList;

import SelfCheckOut_Hardware.external.ProductDatabases;
import SelfCheckOut_Software.attendant.AttendantStationGuiFacade;

/**
 *
 * @author matth
 */
public class AttendantVisCat extends javax.swing.JPanel {
	private static final long serialVersionUID = 7298527930314997735L;
	private AttendantStationGuiFacade guiFacade;
	
    /**
     * Creates new form AttendantVisCat
     */
    public AttendantVisCat(AttendantStationGuiFacade gf) {
    	this.guiFacade = gf;
    	
        initComponents();
    }
    
    
    public String getSearchText() {
    	setWeightText("");
    	setPriceText("");
        return searchTextPane.getText().toLowerCase();
    }
    
    public void setPriceText(String txt) {
        priceTextPane.setText(txt);
    }
    
    public void setWeightText(String txt) {
        weightTextPane.setText(txt);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        searchTextPane = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        searchList = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        searchButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        priceTextPane = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        weightTextPane = new javax.swing.JTextPane();
        backToStationManagerButton = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 30)); // NOI18N
        jLabel1.setText("Search via keyword for a product");

        jScrollPane1.setViewportView(searchTextPane);

        searchList.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jScrollPane2.setViewportView(searchList);
        searchList.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jList1FocusGained(evt);
            }
        });
        searchList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel2.setText("Results:");

        searchButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        searchButton.setText("Search");
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel3.setText("Price ($)");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel4.setText("Weight (kg)");

        jScrollPane3.setViewportView(priceTextPane);

        jScrollPane4.setViewportView(weightTextPane);

        backToStationManagerButton.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        backToStationManagerButton.setText("Back to Station Manager");
        backToStationManagerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(368, 368, 368))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2)
                                .addGap(47, 47, 47)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(backToStationManagerButton))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1))
                        .addGap(0, 436, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(backToStationManagerButton)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ArrayList<String> desclist = new ArrayList<String>();
        ProductDatabases.PLU_PRODUCT_DATABASE.forEach((k, v) -> {
            if (v.getDescription().toLowerCase().contains(getSearchText())) {
            	desclist.add(v.getDescription());
            }
        });
        ProductDatabases.BARCODED_PRODUCT_DATABASE.forEach((k, v) -> {
            if (v.getDescription().toLowerCase().contains(getSearchText())) {
            	desclist.add(v.getDescription());
            }
        });
        
        if (desclist.size() > 0) {
        	String[] descarr = new String[desclist.size()];
        	for (int i = 0; i < desclist.size(); i++) {
                descarr[i] = desclist.get(i);
            }
        	setSearchResults(descarr);
        } else {
        	String[] descarr = new String[1];
        	descarr[0] = "";
        	setSearchResults(descarr);
        }
        searchTextPane.setText("");
    	setWeightText("");
    	setPriceText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    	String[] strarr = new String[0];
    	setSearchResults(strarr);
    	searchTextPane.setText("");
    	setWeightText("");
    	setPriceText("");
        guiFacade.exitVisCat();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jList1FocusGained(java.awt.event.FocusEvent evt) {
        ProductDatabases.PLU_PRODUCT_DATABASE.forEach((k, v) -> {
            if (v.getDescription().equals(searchList.getSelectedValue())) {
            	setPriceText("$" + v.getPrice().toString());
            	setWeightText("N/A");
            }
        });
        ProductDatabases.BARCODED_PRODUCT_DATABASE.forEach((k, v) -> {
            if (v.getDescription().equals(searchList.getSelectedValue())) {
            	setPriceText("$" + v.getPrice().toString());
            	setWeightText(Double.toString(v.getExpectedWeight()) + " grams");
            }
        });
    }
    
    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {                                    
    	ProductDatabases.PLU_PRODUCT_DATABASE.forEach((k, v) -> {
            if (v.getDescription().equals(searchList.getSelectedValue())) {
            	setPriceText("$" + v.getPrice().toString());
            	setWeightText("N/A");
            }
        });
        ProductDatabases.BARCODED_PRODUCT_DATABASE.forEach((k, v) -> {
            if (v.getDescription().equals(searchList.getSelectedValue())) {
            	setPriceText("$" + v.getPrice().toString());
            	setWeightText(Double.toString(v.getExpectedWeight()) + " grams");
            }
        });
    }  
    
    public void setSearchResults(String[] array) {
        searchList.setModel(new javax.swing.AbstractListModel<String>() {
			private static final long serialVersionUID = 1L;
			String[] strings = array.clone();
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
    }
    
    public void fauxClickSearchList() {
    	jList1MouseClicked(null);
    }
    
    public void fauxFocusSearchList() {
    	jList1FocusGained(null);
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton searchButton;
    public javax.swing.JButton backToStationManagerButton;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JList<String> searchList;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JTextPane searchTextPane;
    public javax.swing.JTextPane priceTextPane;
    public javax.swing.JTextPane weightTextPane;
    // End of variables declaration//GEN-END:variables
}
