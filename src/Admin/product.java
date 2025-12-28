/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Admin;


import java.sql.*;

public class product extends javax.swing.JPanel {   
    
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/p";  
    private static final String USER = "root";  
    private static final String PASS = "Chay00))";     
    
    public class ProductAdd {
    public static void addProduct(String name, int quantity, double price, int categoryId, String supplier, int lowStockThreshold) {
     
        String query = "INSERT INTO Products (product_name, quantity, price, category_id, supplier, low_stock_threshold) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, name);
            stmt.setInt(2, quantity);
            stmt.setDouble(3, price);
            stmt.setInt(4, categoryId);
            stmt.setString(5, supplier);
            stmt.setInt(6, lowStockThreshold);
             // Ensure auto-commit is enabled
            conn.setAutoCommit(true);
            // Execute the update (insert)
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    public product(){
        initComponents();
    }

    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelSearchID = new javax.swing.JLabel();
        jTextFieldSearrchProduct = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButtonAddProduct = new javax.swing.JButton();
        jButtonUpdateProduct = new javax.swing.JButton();
        jButtonDeleteProduct = new javax.swing.JButton();
        jTextFieldProductName = new javax.swing.JTextField();
        jTextFieldProductCategory = new javax.swing.JTextField();
        jTextFieldProductQuantity = new javax.swing.JTextField();
        jTextFieldProductPrice = new javax.swing.JTextField();
        jTextFieldProductSupplier = new javax.swing.JTextField();
        jTextFieldProductLowstock = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProduct = new javax.swing.JTable();

        setBackground(new java.awt.Color(153, 153, 153));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabelSearchID.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabelSearchID.setText("Search ID :");

        jTextFieldSearrchProduct.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jTextFieldSearrchProduct.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextFieldSearrchProduct.addActionListener(this::jTextFieldSearrchProductActionPerformed);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/image_admin/search.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelSearchID)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldSearrchProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                .addGap(32, 32, 32)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelSearchID, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldSearrchProduct))
                .addContainerGap(11, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 22, -1, 50));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel1.setText("Product Name :");

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel2.setText("Category :");

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel3.setText("Quantity :");

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel4.setText("Price :");

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel5.setText("Supplier :");

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel6.setText("Low Stock :");

        jButtonAddProduct.setBackground(new java.awt.Color(0, 204, 0));
        jButtonAddProduct.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jButtonAddProduct.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAddProduct.setText("Add Product");
        jButtonAddProduct.addActionListener(this::jButtonAddProductActionPerformed);

        jButtonUpdateProduct.setBackground(new java.awt.Color(0, 153, 204));
        jButtonUpdateProduct.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jButtonUpdateProduct.setForeground(new java.awt.Color(255, 255, 255));
        jButtonUpdateProduct.setText("Update");

        jButtonDeleteProduct.setBackground(new java.awt.Color(255, 0, 51));
        jButtonDeleteProduct.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jButtonDeleteProduct.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDeleteProduct.setText("Delete");

        jTextFieldProductName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextFieldProductName.addActionListener(this::jTextFieldProductNameActionPerformed);

        jTextFieldProductCategory.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextFieldProductCategory.addActionListener(this::jTextFieldProductCategoryActionPerformed);

        jTextFieldProductQuantity.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextFieldProductQuantity.addActionListener(this::jTextFieldProductQuantityActionPerformed);

        jTextFieldProductPrice.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextFieldProductPrice.addActionListener(this::jTextFieldProductPriceActionPerformed);

        jTextFieldProductSupplier.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextFieldProductSupplier.addActionListener(this::jTextFieldProductSupplierActionPerformed);

        jTextFieldProductLowstock.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextFieldProductLowstock.addActionListener(this::jTextFieldProductLowstockActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldProductName)
                            .addComponent(jTextFieldProductCategory, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldProductQuantity, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldProductPrice)
                            .addComponent(jTextFieldProductSupplier, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldProductLowstock, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonAddProduct)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonUpdateProduct)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonDeleteProduct)
                        .addGap(0, 49, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldProductCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldProductQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldProductSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldProductLowstock, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonUpdateProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDeleteProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(793, 170, -1, -1));

        jTableProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Product Name", "Category", "Quantity", "Price", "Supplier", "Low sotck"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableProduct);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
        );

        add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 86, 781, 518));
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldSearrchProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSearrchProductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSearrchProductActionPerformed

    private void jTextFieldProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldProductNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldProductNameActionPerformed

    private void jTextFieldProductCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldProductCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldProductCategoryActionPerformed

    private void jTextFieldProductQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldProductQuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldProductQuantityActionPerformed

    private void jTextFieldProductPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldProductPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldProductPriceActionPerformed

    private void jTextFieldProductSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldProductSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldProductSupplierActionPerformed

    private void jTextFieldProductLowstockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldProductLowstockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldProductLowstockActionPerformed

    private void jButtonAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddProductActionPerformed
        
        String name = jTextFieldProductName.getText();
        String category = jTextFieldProductCategory.getText();
        String quantity = jTextFieldProductQuantity.getText();
        String price = jTextFieldProductPrice.getText();
        String supplier = jTextFieldProductSupplier.getText();
        String lowstock = jTextFieldProductLowstock.getText();
        
            // Convert string values to appropriate types
            int quantityInt = Integer.parseInt(quantity);
            double priceDouble = Double.parseDouble(price);
            int lowStockInt = Integer.parseInt(lowstock);

            // Insert into the database
            ProductAdd.addProduct(name, quantityInt, priceDouble, 1, supplier, lowStockInt);
            
     jTextFieldProductName.setText("0");
                
    }//GEN-LAST:event_jButtonAddProductActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonAddProduct;
    private javax.swing.JButton jButtonDeleteProduct;
    private javax.swing.JButton jButtonUpdateProduct;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelSearchID;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableProduct;
    private javax.swing.JTextField jTextFieldProductCategory;
    private javax.swing.JTextField jTextFieldProductLowstock;
    private javax.swing.JTextField jTextFieldProductName;
    private javax.swing.JTextField jTextFieldProductPrice;
    private javax.swing.JTextField jTextFieldProductQuantity;
    private javax.swing.JTextField jTextFieldProductSupplier;
    private javax.swing.JTextField jTextFieldSearrchProduct;
    // End of variables declaration//GEN-END:variables
}
