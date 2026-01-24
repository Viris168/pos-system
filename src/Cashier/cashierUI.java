/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Cashier;

import static Cashier.ProductQuery.addProductToBillItems;
import static Cashier.ProductQuery.updateProductStock;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import login.login;

/**
 *
 * @author ASUS
 */
public class cashierUI extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(cashierUI.class.getName());
    private DefaultTableModel cartModel;
    private double subTotal = 0;
    private double tax = 0;
    private double grandTotal = 0;
    private JButton activeCategoryButton;

    private void initDefault(){
        subtotalText.setText(String.format("$ %.2f", subTotal));
        taxRateText.setText(String.format("$ %.2f", tax));
        grandTotalText.setText(String.format("$ %.2f", grandTotal));
        discountInput.setText("0");
        increaseButton.setEnabled(false);
        decreaseButton.setEnabled(false);
        deleteButton.setEnabled(false);
        grandTotalText.setVisible(false);
        time.setText(getCurrentDateTime());
    }
    
    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        return sdf.format(new Date());
    }
    // load all products from database then make a product card to display in grid container
    private void displayProducts(ArrayList<Product> products) {
        productContainer.removeAll();
        
        for (Product p : products) {
            String imageName = ProductImageMapper.getImage(p.getName());

            ProductCard card = new ProductCard(
                this,
                p,
                p.getName(),
                p.getPrice(),
                imageName
            );

            productContainer.add(card);
        }

        productContainer.revalidate();
        productContainer.repaint();
    }
    
    void initCartTable(){
        cartModel = (DefaultTableModel) cartTable.getModel();
        cartModel.setRowCount(0); // remove all empty rows
        
        // listen for row selection changes in the table
        cartTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // check if any row is selected, -1 = nothing selected, 0+ = something selected
                boolean rowSelected = cartTable.getSelectedRow() != -1;
                // enable delete button only if a row is selected
                decreaseButton.setEnabled(rowSelected);
                increaseButton.setEnabled(rowSelected);
                deleteButton.setEnabled(rowSelected);
            }
        });
    }
    
    public void addProductToCart(Product product, int quantity) {
        grandTotalText.setVisible(false);
        
        cartModel = (DefaultTableModel) cartTable.getModel();

        String name = product.getName();
        double price = product.getPrice();
        double total = price * quantity;

        boolean found = false;

        for (int i = 0; i < cartModel.getRowCount() ; i++) {
            // loop thru every row to check if there's existing product name
            String existingName = cartModel.getValueAt(i, 0).toString();
            
            if (existingName.equals(name)) {
                int oldQty = Integer.parseInt(cartModel.getValueAt(i, 2).toString());
                int newQty = oldQty + quantity;
                double newTotal = newQty * price;

                cartModel.setValueAt(newQty, i, 2);
                cartModel.setValueAt(String.format("$ %.2f", newTotal), i, 3);

                found = true;
                break;
            }
        }
        
        // if there's no same product name in the cart table
        if (!found) {
            cartModel.addRow(new Object[]{
                    name,
                    price,
                    quantity,
                    String.format("$ %.2f", total)
            });
        }
        
        // update subtotal and tax, then display
        subTotal += total;
        tax = subTotal * 0.1;
        
        subtotalText.setText(String.format("$ %.2f", subTotal));
        taxRateText.setText(String.format("$ %.2f", tax));
    }
    
    private void setActiveCategory(JButton clickedButton) {
        
        // skip at first cuz activeCategoryButton == null once we run
        if (activeCategoryButton != null) {
            activeCategoryButton.setBackground(
                UIManager.getColor("Button.background")
            );
        }

        clickedButton.setBackground(Color.BLACK);

        activeCategoryButton = clickedButton;
    }
    
    private String generateReceipt(double cashPaid, double change) {
        StringBuilder receipt = new StringBuilder();

        receipt.append("        MINI POS RECEIPT\n");
        receipt.append("--------------------------------\n");

        DefaultTableModel model = (DefaultTableModel) cartTable.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {

            String name = model.getValueAt(i, 0).toString();
            double price = Double.parseDouble(model.getValueAt(i, 1).toString());
            int qty = Integer.parseInt(model.getValueAt(i, 2).toString());
            double total = Double.parseDouble(
                    model.getValueAt(i, 3).toString().replace("$", "")
            );

            receipt.append(
                String.format("%-10s x%d   $%.2f\n", name, qty, total)
            );
        }
        
        receipt.append("--------------------------------\n");
        receipt.append(String.format("TOTAL  : $%.2f\n", grandTotal));
        receipt.append(String.format("CASH   : $%.2f\n", cashPaid));
        receipt.append(String.format("CHANGE : $%.2f\n", change));
        receipt.append("--------------------------------\n");
        receipt.append("        THANK YOU!\n");

        return receipt.toString();
    }

    private void showReceiptPreview(String receiptText) {
        JTextArea area = new JTextArea(15, 30);
        area.setText(receiptText);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane pane = new JScrollPane(area);
        pane.setPreferredSize(new Dimension(300, 400));

        // Create dialog
        JDialog dialog = new JDialog((Frame) null, "Receipt Preview", true);
        dialog.setLayout(new BorderLayout());
        dialog.add(pane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnPrint = new JButton("Print");
        JButton btnOk = new JButton("OK");

        buttonPanel.add(btnPrint);
        buttonPanel.add(btnOk);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // OK closes dialog
        btnOk.addActionListener(e -> dialog.dispose());

        // Print button (logic later)
        btnPrint.addActionListener(e -> {
            try {
                boolean printed = area.print();
                if (!printed) {
                    JOptionPane.showMessageDialog(dialog,
                            "Printing was cancelled.",
                            "Print",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog,
                        "Failed to print receipt.",
                        "Print Error",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public cashierUI() {
        initComponents();
        
        // loads all products from database
        displayProducts(ProductQuery.getAllProducts());
        // make every text label to display in default value, which is 0
        initDefault();
        
        // init cart table
        initCartTable();
        
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        mainPanel = new javax.swing.JPanel();
        jScrollPane = new javax.swing.JScrollPane();
        productContainer = new javax.swing.JPanel();
        rightPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        cartTable = new javax.swing.JTable();
        subtotalLabel = new javax.swing.JLabel();
        taxLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        discountInput = new javax.swing.JTextField();
        applyButton = new javax.swing.JButton();
        taxRateText = new javax.swing.JLabel();
        deleteButton = new javax.swing.JButton();
        subtotalText = new javax.swing.JLabel();
        clearButton = new javax.swing.JButton();
        payButton = new javax.swing.JButton();
        grandTotalLabel = new javax.swing.JLabel();
        grandTotalText = new javax.swing.JLabel();
        decreaseButton = new javax.swing.JButton();
        increaseButton = new javax.swing.JButton();
        categoryPanel = new javax.swing.JPanel();
        allButton = new javax.swing.JButton();
        beverageButton = new javax.swing.JButton();
        foodButton = new javax.swing.JButton();
        snackButton = new javax.swing.JButton();
        searchBar = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        time = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setBackground(java.awt.SystemColor.controlHighlight);

        jScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        productContainer.setLayout(new java.awt.GridLayout(0, 4, 5, 5));
        jScrollPane.setViewportView(productContainer);

        rightPanel.setBackground(java.awt.SystemColor.control);
        rightPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cartTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Product", "Price", "Quantity", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(cartTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
        );

        subtotalLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        subtotalLabel.setText("Subtotal:");

        taxLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        taxLabel.setText("Tax (10%):");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Discount%:");

        discountInput.addActionListener(this::discountInputActionPerformed);

        applyButton.setText("Apply");
        applyButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        applyButton.addActionListener(this::applyButtonActionPerformed);

        taxRateText.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        deleteButton.setText("Delete");
        deleteButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteButton.addActionListener(this::deleteButtonActionPerformed);

        subtotalText.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        clearButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        clearButton.setText("Clear");
        clearButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        clearButton.setContentAreaFilled(false);
        clearButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearButton.addActionListener(this::clearButtonActionPerformed);

        payButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        payButton.setText("Pay");
        payButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        payButton.setContentAreaFilled(false);
        payButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        payButton.addActionListener(this::payButtonActionPerformed);

        grandTotalLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        grandTotalLabel.setText("Grand Total:");

        grandTotalText.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        decreaseButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        decreaseButton.setText("-");
        decreaseButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        decreaseButton.addActionListener(this::decreaseButtonActionPerformed);

        increaseButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        increaseButton.setText("+");
        increaseButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        increaseButton.addActionListener(this::increaseButtonActionPerformed);

        javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(rightPanelLayout.createSequentialGroup()
                        .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(rightPanelLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(rightPanelLayout.createSequentialGroup()
                                        .addGap(156, 156, 156)
                                        .addComponent(decreaseButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(increaseButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(deleteButton))
                                    .addGroup(rightPanelLayout.createSequentialGroup()
                                        .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(subtotalLabel)
                                            .addComponent(taxLabel))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, rightPanelLayout.createSequentialGroup()
                                                .addComponent(discountInput, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(applyButton))
                                            .addComponent(subtotalText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(taxRateText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addGroup(rightPanelLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(rightPanelLayout.createSequentialGroup()
                                        .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightPanelLayout.createSequentialGroup()
                                                .addGap(143, 143, 143)
                                                .addComponent(grandTotalText, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(rightPanelLayout.createSequentialGroup()
                                                .addComponent(grandTotalLabel)
                                                .addGap(186, 186, 186)))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(rightPanelLayout.createSequentialGroup()
                                        .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(payButton, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(19, 19, 19)))))
                        .addGap(11, 11, 11)))
                .addContainerGap())
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(increaseButton)
                        .addComponent(decreaseButton))
                    .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(60, 60, 60)
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(subtotalLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(subtotalText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(taxLabel)
                    .addComponent(taxRateText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(discountInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(applyButton))
                .addGap(64, 64, 64)
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(grandTotalLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(grandTotalText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(payButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        categoryPanel.setBackground(java.awt.SystemColor.control);
        categoryPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        allButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        allButton.setText("All");
        allButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        allButton.setContentAreaFilled(false);
        allButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        allButton.addActionListener(this::allButtonActionPerformed);

        beverageButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        beverageButton.setText("Beverage");
        beverageButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        beverageButton.setContentAreaFilled(false);
        beverageButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        beverageButton.addActionListener(this::beverageButtonActionPerformed);

        foodButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        foodButton.setText("Food");
        foodButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        foodButton.setContentAreaFilled(false);
        foodButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        foodButton.addActionListener(this::foodButtonActionPerformed);

        snackButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        snackButton.setText("Snack");
        snackButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        snackButton.setContentAreaFilled(false);
        snackButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        snackButton.addActionListener(this::snackButtonActionPerformed);

        searchBar.addActionListener(this::searchBarActionPerformed);

        searchButton.setText("Search");
        searchButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchButton.addActionListener(this::searchButtonActionPerformed);

        javax.swing.GroupLayout categoryPanelLayout = new javax.swing.GroupLayout(categoryPanel);
        categoryPanel.setLayout(categoryPanelLayout);
        categoryPanelLayout.setHorizontalGroup(
            categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(categoryPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(allButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(beverageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(foodButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(snackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(searchBar, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchButton)
                .addContainerGap())
        );
        categoryPanelLayout.setVerticalGroup(
            categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(categoryPanelLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(allButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(snackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(beverageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(foodButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setText("Logout");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("POS SYSTEM");

        jPanel3.setBackground(java.awt.SystemColor.inactiveCaption);
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Username:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Time:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Role:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Sethar TyKun");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Admin");

        time.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(time, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(129, 129, 129)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 235, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(categoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(categoryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane))
                    .addComponent(rightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        if(cartModel.getRowCount() == 0){
            JOptionPane.showMessageDialog(this, "Cart is empty.");
            return;
        }
        
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to clear the cart?",
            "Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            cartModel.setRowCount(0);
            grandTotal = 0;
            subTotal = 0;
            tax = 0;

            initDefault();
        }
    }//GEN-LAST:event_clearButtonActionPerformed

    private void payButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payButtonActionPerformed
        ProductQuery p = new ProductQuery();
        

        // 1. Cart empty check
        if(cartModel.getRowCount() == 0){
            JOptionPane.showMessageDialog(this, "Cart is empty.");
            return;
        }
        
        // check if the grand total text is visible
        if(!grandTotalText.isVisible()){
            JOptionPane.showMessageDialog(this, "Please, check the grand total first");
            return;
        }

        // 2. Ask for cash
        String input = JOptionPane.showInputDialog(
            this,
            "Enter cash amount:",
            "Cash Payment",
            JOptionPane.PLAIN_MESSAGE
        );

        if (input == null) return; // user cancelled

        double cashPaid;

        try {
            cashPaid = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid cash amount.");
            return;
        }

        // 3. Check enough cash
        if (cashPaid < grandTotal) {
            JOptionPane.showMessageDialog(this, "Not enough cash.");
            return;
        }

        // 4. Calculate change
        double change = cashPaid - grandTotal;

        // 5. Generate & show receipt
        String receipt = generateReceipt(cashPaid, change);
        showReceiptPreview(receipt);
        
            
        double totalAmount = calculateTotalAmountForCart(); 
        int billId = p.createNewBill(totalAmount);  

        // Insert each item from the cart into the Bill_Items table
        for (int i = 0; i < cartModel.getRowCount(); i++) {
            String name = cartModel.getValueAt(i, 0).toString();
            double price = Double.parseDouble(cartModel.getValueAt(i, 1).toString());
            int quantity = Integer.parseInt(cartModel.getValueAt(i, 2).toString());

            
            int productId = p.getProductIdByName(name);  
            String category = p.getCategoryByName(name); 

            
            Product product = new Product(productId, name, category, price, quantity);

            
            addProductToBillItems(billId, product, quantity);
            
            updateProductStock(productId, quantity);
        }



        // 6. Clear cart AFTER receipt
        cartModel.setRowCount(0);
        grandTotal = 0;
        subTotal = 0;
        tax = 0;
        
        initDefault();
    }//GEN-LAST:event_payButtonActionPerformed

    private void allButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allButtonActionPerformed
        setActiveCategory(allButton);
        displayProducts(ProductQuery.getAllProducts());
    }//GEN-LAST:event_allButtonActionPerformed

    private void beverageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beverageButtonActionPerformed
        setActiveCategory(beverageButton);
        displayProducts(ProductQuery.getProductsByCategory("Beverage"));
    }//GEN-LAST:event_beverageButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // read where the selected row is?
        int selectedRow = cartTable.getSelectedRow();

        DefaultTableModel model = (DefaultTableModel) cartTable.getModel();

        // get price and qty before delete
        // column index starts from 0:
        // 0 = Product, 1 = Price, 2 = Quantity, 3 = Total
        double price = Double.parseDouble(model.getValueAt(selectedRow, 1).toString());
        int qty = Integer.parseInt(model.getValueAt(selectedRow, 2).toString());
        
        // total price for this row
        double rowTotal = price * qty;
        
        // remove row
        model.removeRow(selectedRow);
        
        // update subtotal and tax, then display
        subTotal -= rowTotal;
        if (subTotal < 0) subTotal = 0;
        tax = subTotal * 0.1;
        subtotalText.setText(String.format("$ %.2f", subTotal));
        taxRateText.setText(String.format("$ %.2f", tax));
        
        // 🔹 Hide delete button again
        deleteButton.setEnabled(false);
        grandTotalText.setVisible(false);
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void foodButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foodButtonActionPerformed
        setActiveCategory(foodButton);
        displayProducts(ProductQuery.getProductsByCategory("Food"));
    }//GEN-LAST:event_foodButtonActionPerformed

    private void snackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_snackButtonActionPerformed
        setActiveCategory(snackButton);
        displayProducts(ProductQuery.getProductsByCategory("Snack"));
    }//GEN-LAST:event_snackButtonActionPerformed

    private void applyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyButtonActionPerformed
        if(cartModel.getRowCount() == 0){
            JOptionPane.showMessageDialog(this, "Please add some products!");
            return;
        }
        
        int discountRate;
        
        // get discount from input, also check if it's not letter
        try {
            discountRate = Integer.parseInt(discountInput.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid discount value");
            return;
        }
        
        // check if discount != (0,100)
        if (discountRate < 0 || discountRate > 100) {
            JOptionPane.showMessageDialog(this, "Discount must be 0–100%");
            discountInput.setText("0");
            return;
        }
        
        // calculate grandtotal using subtotal, tax and discount
        double totalBeforeDiscount = subTotal + tax;
        double totalDiscount = totalBeforeDiscount * (discountRate / 100.0);
        grandTotal = totalBeforeDiscount - totalDiscount;
     
        grandTotalText.setText(String.format("$ %.2f", grandTotal));
        grandTotalText.setVisible(true);
    }//GEN-LAST:event_applyButtonActionPerformed

    private void decreaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decreaseButtonActionPerformed
        // 1. get selected row
        int selectedRow = cartTable.getSelectedRow();

        // safety check
        if (selectedRow == -1) {
            return;
        }

        // 2. get table model
        DefaultTableModel model = (DefaultTableModel) cartTable.getModel();

        // 3. read price & quantity
        double price = Double.parseDouble(model.getValueAt(selectedRow, 1).toString());
        int qty = Integer.parseInt(model.getValueAt(selectedRow, 2).toString());

        // 4. decrease quantity
        qty--;

        // 5. subtract ONE item price from subtotal
        subTotal -= price;
        if (subTotal < 0) subTotal = 0;

        // 6. if qty becomes 0 → remove row
        if (qty <= 0) {
            model.removeRow(selectedRow);
        }
        
        // 7. otherwise update qty & row total
        else {
            model.setValueAt(qty, selectedRow, 2);           // Quantity column
            model.setValueAt(String.format("$ %.2f", price * qty), selectedRow, 3);   // Total column
        }

        // 8. update tax & UI
        tax = subTotal * 0.1;
        subtotalText.setText(String.format("$ %.2f", subTotal));
        taxRateText.setText(String.format("$ %.2f", tax));
    }//GEN-LAST:event_decreaseButtonActionPerformed

    private void increaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_increaseButtonActionPerformed
        // 1. get selected row
        int selectedRow = cartTable.getSelectedRow();

        // safety check
        if (selectedRow == -1) {
            return;
        }

        // 2. get table model
        DefaultTableModel model = (DefaultTableModel) cartTable.getModel();

        // 3. read price & quantity
        double price = Double.parseDouble(model.getValueAt(selectedRow, 1).toString());
        int qty = Integer.parseInt(model.getValueAt(selectedRow, 2).toString());

        // 4. increase quantity
        qty++;

        // 5. add ONE item price to subtotal
        subTotal += price;

        // 6. update table values
        model.setValueAt(qty, selectedRow, 2);           // Quantity column
        model.setValueAt(String.format("$ %.2f", price * qty), selectedRow, 3);   // Total column

        // 7. update tax & UI
        tax = subTotal * 0.1;
        subtotalText.setText(String.format("$ %.2f", subTotal));
        taxRateText.setText(String.format("$ %.2f", tax));
    }//GEN-LAST:event_increaseButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         login l = new login();
         l.setVisible(true);
         this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void discountInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discountInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_discountInputActionPerformed

    private void searchBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchBarActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
       String keyword = searchBar.getText().trim().toUpperCase();
       
        if (keyword.isEmpty()) {
            displayProducts(ProductQuery.getAllProducts());
            return;
        }
        
        if(!keyword.isEmpty() && Character.isDigit(keyword.charAt(0))){
            JOptionPane.showMessageDialog(this, "Product Not Found!");
            searchBar.setText("");
            return;
        }

        ArrayList<Product> results = ProductQuery.searchProductsByName(keyword);
        
        displayProducts(results);
    }//GEN-LAST:event_searchButtonActionPerformed

    public void addProductToCartAndDatabase(Product product, int quantity) {
        
        ProductQuery ProductQuery = new ProductQuery();
               double totalAmount = calculateTotalAmountForCart();  

        
        int billId = ProductQuery.createNewBill(totalAmount);  

       
        addProductToCart(product, quantity);  

        addProductToBillItems(billId, product, quantity);  
    }

    public double calculateTotalAmountForCart() {
        double total = 0.0;

        
        DefaultTableModel model = (DefaultTableModel) cartTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            double lineTotal = Double.parseDouble(model.getValueAt(i, 3).toString().replace("$", ""));
            total += lineTotal;  
        }

        return total;
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new cashierUI().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton allButton;
    private javax.swing.JButton applyButton;
    private javax.swing.JButton beverageButton;
    private javax.swing.JTable cartTable;
    private javax.swing.JPanel categoryPanel;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton decreaseButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField discountInput;
    private javax.swing.JButton foodButton;
    private javax.swing.JLabel grandTotalLabel;
    private javax.swing.JLabel grandTotalText;
    private javax.swing.JButton increaseButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton payButton;
    private javax.swing.JPanel productContainer;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JTextField searchBar;
    private javax.swing.JButton searchButton;
    private javax.swing.JButton snackButton;
    private javax.swing.JLabel subtotalLabel;
    private javax.swing.JLabel subtotalText;
    private javax.swing.JLabel taxLabel;
    private javax.swing.JLabel taxRateText;
    private javax.swing.JLabel time;
    // End of variables declaration//GEN-END:variables
}
