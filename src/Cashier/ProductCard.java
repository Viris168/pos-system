/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Cashier;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import jdk.jfr.Enabled;
/**
 *
 * @author ASUS
 */
public class ProductCard extends javax.swing.JPanel {

    private cashierUI cashierUI;
    private Product product;
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/data";  
    private static final String USER = "root";  
    private static final String PASS = "Chay00))";
    
private void setProductImage(String imgPath) {
    try {
        if (imgPath == null || imgPath.trim().isEmpty()) {
            imageLabel.setIcon(null);
            imageLabel.setText("No image");
            return;
        }

        File imgFile = new File(imgPath);

        if (!imgFile.exists()) {
            imageLabel.setIcon(null);
            imageLabel.setText("Not found");
            return;
        }

        BufferedImage image = ImageIO.read(imgFile);
        if (image == null) {
            imageLabel.setIcon(null);
            imageLabel.setText("Invalid");
            return;
        }

        int w = imageLabel.getWidth();
        int h = imageLabel.getHeight();
        if (w <= 0 || h <= 0) { w = 150; h = 150; }

        Image scaled = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaled));
        imageLabel.setText("");

    } catch (Exception e) {
        e.printStackTrace();
        imageLabel.setIcon(null);
        imageLabel.setText("Error");
    }
}

        public static String getImagePathFromDBByName(String productName) {
        String sql = "SELECT image_path FROM products WHERE product_name = ?";

        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, productName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("image_path");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ProductCard(cashierUI cashierUI, Product product, String name, double price, String imageName) {
        initComponents();
        this.product = product;
        this.cashierUI = cashierUI;
        
        setLayout(new GridBagLayout());   // center content
        add(contentPanel);
        
        jLabelName.setText(name);
        jLabelPrice.setText("$ " + price);
        quantitySpinner.setValue(1);
        setProductImage(imageName);
        
        // set fixed size for productCard to avoid from stretching
        setPreferredSize(new Dimension(220, 260));
        setMinimumSize(new Dimension(220, 260));
        setMaximumSize(new Dimension(220, 260));

        setOpaque(false); // important
    }    

    
    public JButton getPurchaseButton() {
        return addToCartButton;
    }
    
    public JSpinner getSpinner(){
        return quantitySpinner;
    }
    
    public Product getProduct() {
        return product;
    }

    public String getProductName() {
        return product.getName();
    }

    public double getProductPrice() {
        return product.getPrice();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        quantitySpinner = new javax.swing.JSpinner();
        addToCartButton = new javax.swing.JButton();
        jLabelName = new javax.swing.JLabel();
        jLabelPrice = new javax.swing.JLabel();
        imageLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(0, 0));
        setLayout(new java.awt.GridBagLayout());

        contentPanel.setBackground(java.awt.SystemColor.control);
        contentPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        contentPanel.setPreferredSize(new java.awt.Dimension(200, 250));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Name:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Quantity:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Price:");

        addToCartButton.setText("Add to cart");
        addToCartButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addToCartButton.addActionListener(this::addToCartButtonActionPerformed);

        imageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageLabel.setMaximumSize(new java.awt.Dimension(100, 180));
        imageLabel.setMinimumSize(new java.awt.Dimension(100, 180));
        imageLabel.setPreferredSize(new java.awt.Dimension(100, 180));

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                        .addGap(0, 6, Short.MAX_VALUE)
                        .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(contentPanelLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(quantitySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabelName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabelPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                                .addComponent(addToCartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)))))
                .addContainerGap())
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(quantitySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(addToCartButton)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        add(contentPanel, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents

    private void addToCartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addToCartButtonActionPerformed
        int qty = (int) quantitySpinner.getValue();
            
        if (qty <= 0) {
            JOptionPane.showMessageDialog(
                this,
                "Please select a quantity greater than 0",
                "Invalid Quantity",
                JOptionPane.WARNING_MESSAGE
            );
            quantitySpinner.setValue(1);
            return;
        }
            
        cashierUI.addProductToCart(product, qty);
        quantitySpinner.setValue(1);
    }//GEN-LAST:event_addToCartButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addToCartButton;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelPrice;
    private javax.swing.JSpinner quantitySpinner;
    // End of variables declaration//GEN-END:variables
}
