
package Admin;


import java.sql.*;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
          
            conn.setAutoCommit(true);
            
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    
    public class ProductUpdate {
    public static void updateProduct(int productId, String name, int quantity, double price, int categoryId, String supplier, int lowStockThreshold) {
        String query = "UPDATE Products SET product_name = ?, quantity = ?, price = ?, category_id = ?, supplier = ?, low_stock_threshold = ? WHERE product_id = ?";
        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, name);
            stmt.setInt(2, quantity);
            stmt.setDouble(3, price);
            stmt.setInt(4, categoryId);
            stmt.setString(5, supplier);
            stmt.setInt(6, lowStockThreshold);
            stmt.setInt(7, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }
    public class ProductDelete {
    public static void deleteProduct(int productId) {
        String query = "DELETE FROM Products WHERE product_id = ?";
        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setInt(1, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    public class LowStockAlert {
    public static void checkLowStock() {
        String query = "SELECT * FROM Products WHERE quantity <= low_stock_threshold";
        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Low Stock Alert: " + rs.getString("product_name") + " - Stock: " + rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
}

    public void clear(){ 
        jTextFieldProductName.setText("");
        jTextFieldProductCategory.setText("");
        jTextFieldProductQuantity.setText("");
        jTextFieldProductPrice.setText("");
        jTextFieldProductSupplier.setText("");
        jTextFieldProductLowstock.setText("");
        jTextFieldSearch.setText("");
    }

    public product(){
        initComponents();
        table_product();
    }
    
    public void table_product(){
        String query = "SELECT * FROM Products ";
        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            DefaultTableModel dt = (DefaultTableModel) jTableProduct.getModel();
            dt.setRowCount(0);
             
            ResultSet rs = stmt.executeQuery();
 
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                v.add(rs.getString(3));
                v.add(rs.getString(4));
                v.add(rs.getString(5));
                v.add(rs.getString(6));
                v.add(rs.getString(7));
                
                dt.addRow(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelSearchID = new javax.swing.JPanel();
        jLabelSearchID = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();
        jButtonSeaarch = new javax.swing.JButton();
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
        jButtonClear = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProduct = new javax.swing.JTable();

        setBackground(new java.awt.Color(153, 153, 153));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelSearchID.setBackground(new java.awt.Color(255, 255, 255));

        jLabelSearchID.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabelSearchID.setText("Search ID :");

        jTextFieldSearch.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jTextFieldSearch.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextFieldSearch.addActionListener(this::jTextFieldSearchActionPerformed);

        jButtonSeaarch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/image_admin/search.png"))); // NOI18N
        jButtonSeaarch.addActionListener(this::jButtonSeaarchActionPerformed);

        javax.swing.GroupLayout jPanelSearchIDLayout = new javax.swing.GroupLayout(jPanelSearchID);
        jPanelSearchID.setLayout(jPanelSearchIDLayout);
        jPanelSearchIDLayout.setHorizontalGroup(
            jPanelSearchIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSearchIDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelSearchID)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                .addGap(32, 32, 32)
                .addComponent(jButtonSeaarch, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanelSearchIDLayout.setVerticalGroup(
            jPanelSearchIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSearchIDLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanelSearchIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelSearchID, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldSearch))
                .addContainerGap(11, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSearchIDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonSeaarch, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanelSearchID, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 22, -1, 50));

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
        jButtonUpdateProduct.addActionListener(this::jButtonUpdateProductActionPerformed);

        jButtonDeleteProduct.setBackground(new java.awt.Color(255, 0, 51));
        jButtonDeleteProduct.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jButtonDeleteProduct.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDeleteProduct.setText("Delete");
        jButtonDeleteProduct.addActionListener(this::jButtonDeleteProductActionPerformed);

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

        jButtonClear.setBackground(new java.awt.Color(153, 153, 153));
        jButtonClear.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jButtonClear.setForeground(new java.awt.Color(255, 255, 255));
        jButtonClear.setText("Clear");
        jButtonClear.addActionListener(this::jButtonClearActionPerformed);

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
                        .addComponent(jButtonAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonUpdateProduct)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonDeleteProduct)
                        .addGap(0, 61, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonClear)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
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
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Product Name", "Quantity", "Price", "Category", "Supplier", "Low sotck"
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
        jTableProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableProductMouseClicked(evt);
            }
        });
        jTableProduct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableProductKeyPressed(evt);
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

    private void jTextFieldSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSearchActionPerformed

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
            int categoryInt = Integer.parseInt(category);

            // Insert into the database
            ProductAdd.addProduct(name, quantityInt, priceDouble, categoryInt, supplier, lowStockInt);
            table_product();
            
            clear();
        
    }//GEN-LAST:event_jButtonAddProductActionPerformed

    private void jButtonSeaarchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSeaarchActionPerformed
        
        String search = jTextFieldSearch.getText();
        String query = "SELECT * FROM Products WHERE product_id = '"+search+"'  ";
        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                jTextFieldProductName.setText(rs.getString("product_name"));
                jTextFieldProductQuantity.setText(rs.getString("quantity"));
                jTextFieldProductPrice.setText(rs.getString("price"));
                jTextFieldProductCategory.setText(rs.getString("category_id"));
                jTextFieldProductSupplier.setText(rs.getString("supplier"));
                jTextFieldProductLowstock.setText(rs.getString("low_stock_threshold"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButtonSeaarchActionPerformed

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        
        clear();
    }//GEN-LAST:event_jButtonClearActionPerformed

    private void jTableProductKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableProductKeyPressed
        // TODO add your handling code here:
        int r = jTableProduct.getSelectedRow();
        String id = jTableProduct.getValueAt(r, 0).toString();
        String name = jTableProduct.getValueAt(r, 1).toString();
        String category = jTableProduct.getValueAt(r, 2).toString();
        String quantity = jTableProduct.getValueAt(r, 3).toString();
        String price = jTableProduct.getValueAt(r, 4).toString();
        String supplier = jTableProduct.getValueAt(r, 5).toString();
        String low_stock = jTableProduct.getValueAt(r, 6).toString();
        
        
                jTextFieldSearch.setText(id);
                jTextFieldProductName.setText(name);
                jTextFieldProductQuantity.setText(quantity);
                jTextFieldProductPrice.setText(price);
                jTextFieldProductCategory.setText(category);
                jTextFieldProductSupplier.setText(supplier);
                jTextFieldProductLowstock.setText(low_stock);
        
    }//GEN-LAST:event_jTableProductKeyPressed

    private void jButtonUpdateProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateProductActionPerformed
        
        String name = jTextFieldProductName.getText();
        String category = jTextFieldProductCategory.getText();
        String quantity = jTextFieldProductQuantity.getText();
        String price = jTextFieldProductPrice.getText();
        String supplier = jTextFieldProductSupplier.getText();
        String lowstock = jTextFieldProductLowstock.getText();
        
        
            int productId = Integer.parseInt(jTextFieldSearch.getText());
            int quantityInt = Integer.parseInt(quantity);
            double priceDouble = Double.parseDouble(price);
            int lowStockInt = Integer.parseInt(lowstock);
            int categoryInt = Integer.parseInt(category);
            
            ProductUpdate.updateProduct(productId, name, quantityInt, priceDouble, categoryInt, supplier, lowStockInt);
            
            table_product();  
            clear();
    }//GEN-LAST:event_jButtonUpdateProductActionPerformed

    private void jButtonDeleteProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteProductActionPerformed
         String id = jTextFieldSearch.getText();
        int idInt = Integer.parseInt(id);

        ProductDelete.deleteProduct(idInt);
  
        table_product();
        clear();
    }//GEN-LAST:event_jButtonDeleteProductActionPerformed

    private void jTableProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableProductMouseClicked

    }//GEN-LAST:event_jTableProductMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddProduct;
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonDeleteProduct;
    private javax.swing.JButton jButtonSeaarch;
    private javax.swing.JButton jButtonUpdateProduct;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelSearchID;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelSearchID;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableProduct;
    private javax.swing.JTextField jTextFieldProductCategory;
    private javax.swing.JTextField jTextFieldProductLowstock;
    private javax.swing.JTextField jTextFieldProductName;
    private javax.swing.JTextField jTextFieldProductPrice;
    private javax.swing.JTextField jTextFieldProductQuantity;
    private javax.swing.JTextField jTextFieldProductSupplier;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables
}
