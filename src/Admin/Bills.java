package Admin;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


public class Bills extends javax.swing.JPanel {
    
           private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/data";  
           private static final String USER = "root";  
           private static final String PASS = "Chay00))";
           
           product p = new product();
    
    public Bills() {
        initComponents();
        table_bills();
        check();
    }

      public void table_bills() {
        DefaultTableModel model = (DefaultTableModel) jTableProduct.getModel();
        model.setRowCount(0);

        String sql = "SELECT " +
                 "    Bills.bill_id, " +
                 "    Bills.bill_date, " +
                 "    Bills.total_amount, " +
                 "    GROUP_CONCAT(Products.product_name ORDER BY Bill_Items.product_id SEPARATOR ', ') AS products, " +
                 "    GROUP_CONCAT(Bill_Items.line_total ORDER BY Bill_Items.product_id SEPARATOR ', ') AS prices " +
                 "FROM Bills " +
                 "JOIN Bill_Items ON Bills.bill_id = Bill_Items.bill_id " +
                 "JOIN Products ON Bill_Items.product_id = Products.product_id " +
                 "GROUP BY Bills.bill_id " +
                 "ORDER BY Bills.bill_id DESC";

        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("bill_id"),      
                    rs.getString("bill_date"),
                    rs.getDouble("total_amount"),
                    rs.getString("products"),
                    rs.getString("prices")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading bills: " + e.getMessage());
        }
    }
    
    public void check() {
    String query = "SELECT COUNT(bill_id) AS total_bills FROM Bills;";
    
    try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        ResultSet rs = stmt.executeQuery();
        int x = 0;
        
        while (rs.next()) {
            int totalBills = rs.getInt("total_bills");
            jLabelNumber.setText(String.valueOf(totalBills));  
        }
        
        
    } catch (SQLException e) {
        e.printStackTrace();
        jLabelNumber.setText("Error loading stock data");
    }
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelSearchID = new javax.swing.JPanel();
        jLabelSearchID = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();
        jButtonSeaarch = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProduct = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabelNumber = new javax.swing.JLabel();
        jLabelwarn = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(1163, 604));
        setPreferredSize(new java.awt.Dimension(1163, 604));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelSearchID.setBackground(new java.awt.Color(255, 255, 255));

        jLabelSearchID.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabelSearchID.setText("Search ID :");

        jTextFieldSearch.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jTextFieldSearch.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTextFieldSearch.addActionListener(this::jTextFieldSearchActionPerformed);

        jButtonSeaarch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/image_admin/search.png"))); // NOI18N
        jButtonSeaarch.addActionListener(this::jButtonSeaarchActionPerformed);

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Refresh");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout jPanelSearchIDLayout = new javax.swing.GroupLayout(jPanelSearchID);
        jPanelSearchID.setLayout(jPanelSearchIDLayout);
        jPanelSearchIDLayout.setHorizontalGroup(
            jPanelSearchIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSearchIDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelSearchID)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonSeaarch, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanelSearchIDLayout.setVerticalGroup(
            jPanelSearchIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSearchIDLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanelSearchIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelSearchID, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldSearch))
                .addContainerGap(11, Short.MAX_VALUE))
            .addGroup(jPanelSearchIDLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSearchIDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonSeaarch, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        add(jPanelSearchID, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 22, 780, 50));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jTableProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Bill ID", "Date", "Total Amount", "Products", "Prices"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
        if (jTableProduct.getColumnModel().getColumnCount() > 0) {
            jTableProduct.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
        );

        add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 86, 781, 518));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Total Bill : ");

        jLabelNumber.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelNumber.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jLabelwarn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(98, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabelNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelwarn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addContainerGap())
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 10, 350, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSearchActionPerformed

    private void jButtonSeaarchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSeaarchActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTableProduct.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        jTableProduct.setRowSorter(sorter);
        String search = jTextFieldSearch.getText();

        if (search.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Product ID");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(search);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Product ID must be a number!");
            return;
        }

        // Filter JTable by product_id column index (example: column 0 = product_id)
        sorter.setRowFilter(RowFilter.regexFilter("^" + id + "$", 0));

        //  If no row found in JTable after filtering
        if (jTableProduct.getRowCount() == 0) {
            sorter.setRowFilter(null); // reset
            JOptionPane.showMessageDialog(this, "Product ID not found in table!");
            return;
        }

        String query = "SELECT * FROM Bills WHERE bill_id = '"+search+"'  ";
        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButtonSeaarchActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTableProduct.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        jTableProduct.setRowSorter(sorter);
        jTextFieldSearch.setText("");  // Clear the search field
        sorter.setRowFilter(null);  // Reset the filter

        check();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTableProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableProductMouseClicked

    }//GEN-LAST:event_jTableProductMouseClicked

    private void jTableProductKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableProductKeyPressed
        

    }//GEN-LAST:event_jTableProductKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonSeaarch;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelNumber;
    private javax.swing.JLabel jLabelSearchID;
    private javax.swing.JLabel jLabelwarn;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelSearchID;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableProduct;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables
}
