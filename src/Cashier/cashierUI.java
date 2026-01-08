/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Cashier;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class cashierUI extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(cashierUI.class.getName());
    
    private ArrayList<ProductCard> productCards = new ArrayList<>();
    private DefaultTableModel cartModel;
    private double subTotal = 0;
    private double tax = 0;
    private double grandTotal = 0;
    private double discount = 0;
    
    
    private void initDefault(){
        subtotalText.setText(formatMoney(subTotal));
        taxRateText.setText(formatMoney(tax));
        grandTotalText.setText(formatMoney(grandTotal));
        discountInput.setText("0");
    }
 
    private void discountButton(){
        applyButton.addActionListener(e -> {
            
            if(cartModel.getRowCount() == 0){
                JOptionPane.showMessageDialog(this, "Please add some products!");
                return;
            }
            
            int discountRate;

            try {
                discountRate = Integer.parseInt(discountInput.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid discount value");
                return;
            }

            if (discountRate < 0 || discountRate > 100) {
                JOptionPane.showMessageDialog(this, "Discount must be 0–100%");
                discountInput.setText("0");
                return;
            }
            
            double totalBeforeDiscount = subTotal + tax;
            double totalDiscount = totalBeforeDiscount * (discountRate / 100.0);
            
            grandTotal = totalBeforeDiscount - totalDiscount;
            
            grandTotalText.setText(formatMoney(grandTotal));
            
            grandTotalText.setVisible(true);
        });
    }
    
    private void resetAllQuantities(){
        for(ProductCard cart : productCards){
            cart.getSpinner().setValue(0);
        }
    }
  
    private void loadProducts() {
        productContainer.removeAll(); // clear old cards

        ArrayList<Product> products = ProductQuery.getAllProducts();
        
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
            productCards.add(card);
        }   

        productContainer.revalidate();
        productContainer.repaint();
    }
    
    private void setActiveCategory(JButton activeBtn) {
        JButton[] buttons = {
            allButton, beverageButton, foodButton, snackButton
        };
    }
    
    private void initCategoryButtons() {
        allButton.addActionListener(e -> {
            setActiveCategory(allButton);
            displayProducts(ProductQuery.getAllProducts());
        });

        beverageButton.addActionListener(e -> {
            setActiveCategory(beverageButton);
            displayProducts(ProductQuery.getProductsByCategory("Beverage"));
        });

        foodButton.addActionListener(e -> {
            setActiveCategory(foodButton);
            displayProducts(ProductQuery.getProductsByCategory("Food"));
        });

        snackButton.addActionListener(e -> {
            setActiveCategory(snackButton);
            displayProducts(ProductQuery.getProductsByCategory("Snack"));
        });
    }
    
    private void displayProducts(ArrayList<Product> products) {
        productContainer.removeAll();
        productCards.clear();

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
            productCards.add(card);
        }

        productContainer.revalidate();
        productContainer.repaint();
    }

    private String formatMoney(double value) {
        return String.format("$ %.2f", value);
    }
    
    private void initDeleteButton(){
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(evt -> deleteButtonActionPerformed(evt));
    }
    
    private void initCartTable(){
        cartModel = (DefaultTableModel) cartTable.getModel();
        
        cartModel.setRowCount(0); // remove all empty rows
       
        cartTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean rowSelected = cartTable.getSelectedRow() != -1;
                deleteButton.setEnabled(rowSelected);
            }
        });
    }
    

    private void clear(){
        subTotal = 0;
        tax = 0;
        grandTotal = 0;
        discount = 0;
        resetAllQuantities();
        initDefault();
        cartModel.setRowCount(0); // remove all empty rows
    }
    
    private void initClearButton(){
        clearButton.addActionListener(e -> {
            clear();
        });
    }
    
    private void updateTotals() {
        double subtotal = 0;
        cartModel = (DefaultTableModel) cartTable.getModel();

        for (int i = 0; i < cartModel.getRowCount(); i++) {
            String totalStr = cartModel.getValueAt(i, 3).toString();
            subtotal += parseMoney(totalStr);
        }

        subTotal = subtotal;

        subtotalText.setText(formatMoney(subTotal));
        calculateTax();
        calculateGrandTotal();
    }
    
    private double parseMoney(String moneyText) {
        return Double.parseDouble(
                moneyText.replace("$", "").trim()
        );
    }
    
    private void calculateTax() {
        tax = subTotal * 0.10;
        taxRateText.setText(formatMoney(tax));
    }
    
    private void calculateGrandTotal() {
        tax = subTotal * 0.10;

        discount = 0;
        if (!discountInput.getText().isEmpty()) {
            discount = Double.parseDouble(discountInput.getText());
        }

        double discountAmount = subTotal * (discount / 100.0);
        grandTotal = subTotal + tax - discountAmount;

        grandTotalText.setText(formatMoney(grandTotal));
    }
    
    public void addProductToCart(Product product, int quantity) {
        grandTotalText.setVisible(false);
        
        cartModel = (DefaultTableModel) cartTable.getModel();

        String name = product.getName();
        double price = product.getPrice();
        double total = price * quantity;

        boolean found = false;

        for (int i = 0; i < cartModel.getRowCount(); i++) {
            String existingName = cartModel.getValueAt(i, 0).toString();

            if (existingName.equals(name)) {
                int oldQty = Integer.parseInt(
                        cartModel.getValueAt(i, 2).toString()
                );

                int newQty = oldQty + quantity;
                double newTotal = newQty * price;

                cartModel.setValueAt(newQty, i, 2);
                cartModel.setValueAt(formatMoney(newTotal), i, 3);

                found = true;
                break;
            }
        }

        if (!found) {
            cartModel.addRow(new Object[]{
                    name,
                    price,
                    quantity,
                    formatMoney(total)
            });
        }

        updateTotals();
    }
    
    private boolean isCartEmpty() {
        return cartTable.getRowCount() == 0;
    }
    
    private void clearCart() {
        cartModel.setRowCount(0);

        grandTotal = 0;
        subTotal = 0;
        tax = 0;

        initDefault();
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
        receipt.append(" THANK YOU! COME AGAIN\n");

        return receipt.toString();
    }

    private void showReceiptPreview(String receiptText) {
        JTextArea area = new JTextArea(15, 30);
        area.setText(receiptText);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane pane = new JScrollPane(area);
        pane.setPreferredSize(new Dimension(300, 400));

        JOptionPane.showMessageDialog(
            null,
            pane,
            "Receipt Preview",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    public cashierUI() {
        initComponents();
        loadProducts();
        initCategoryButtons();
        initDefault();
        initCartTable();
        initDeleteButton();
        initClearButton();
        discountButton();
        
        
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
        leftPanel = new javax.swing.JPanel();
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
        categoryPanel = new javax.swing.JPanel();
        allButton = new javax.swing.JButton();
        beverageButton = new javax.swing.JButton();
        foodButton = new javax.swing.JButton();
        snackButton = new javax.swing.JButton();

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

        productContainer.setLayout(new java.awt.GridLayout(0, 3, 5, 5));
        jScrollPane.setViewportView(productContainer);

        leftPanel.setBackground(java.awt.SystemColor.control);
        leftPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

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
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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

        applyButton.setText("Apply");
        applyButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

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

        javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(deleteButton))
                    .addGroup(rightPanelLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                    .addComponent(taxRateText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(11, 11, 11))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightPanelLayout.createSequentialGroup()
                                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(rightPanelLayout.createSequentialGroup()
                                        .addComponent(grandTotalLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(grandTotalText, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(rightPanelLayout.createSequentialGroup()
                                        .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(payButton, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(19, 19, 19)))))
                .addContainerGap())
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteButton)
                .addGap(30, 30, 30)
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(subtotalLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(subtotalText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(taxLabel)
                    .addComponent(taxRateText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(discountInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(applyButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(grandTotalLabel)
                    .addComponent(grandTotalText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(clearButton, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(payButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        snackButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        snackButton.setText("Snack");
        snackButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        snackButton.setContentAreaFilled(false);
        snackButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

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
                .addContainerGap(284, Short.MAX_VALUE))
        );
        categoryPanelLayout.setVerticalGroup(
            categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(categoryPanelLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(categoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(allButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(snackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(beverageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(foodButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane)
                    .addComponent(categoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rightPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(leftPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(0, 3, Short.MAX_VALUE)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rightPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addComponent(categoryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 729, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
        // TODO add your handling code here:
    }//GEN-LAST:event_clearButtonActionPerformed

    private void payButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payButtonActionPerformed
        // 1. Cart empty check
        if (isCartEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty.");
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

        // 6. Clear cart AFTER receipt
        clearCart();
    }//GEN-LAST:event_payButtonActionPerformed

    private void allButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_allButtonActionPerformed

    private void beverageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beverageButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_beverageButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        int selectedRow = cartTable.getSelectedRow();

        if (selectedRow == -1) return;

        DefaultTableModel model = (DefaultTableModel) cartTable.getModel();

        // 🔹 Get price & quantity BEFORE removing row
        double price = Double.parseDouble(model.getValueAt(selectedRow, 1).toString());
        int qty = Integer.parseInt(model.getValueAt(selectedRow, 2).toString());

        double rowTotal = price * qty;

        // 🔹 Remove row
        model.removeRow(selectedRow);

        // 🔹 Update subtotal
        subTotal -= rowTotal;
        if (subTotal < 0) subTotal = 0;

        updateTotals();

        // 🔹 Hide delete button again
        deleteButton.setEnabled(false);
        grandTotalText.setVisible(false);
    }//GEN-LAST:event_deleteButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new cashierUI().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton allButton;
    private javax.swing.JButton applyButton;
    private javax.swing.JButton beverageButton;
    private javax.swing.JTable cartTable;
    private javax.swing.JPanel categoryPanel;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField discountInput;
    private javax.swing.JButton foodButton;
    private javax.swing.JLabel grandTotalLabel;
    private javax.swing.JLabel grandTotalText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton payButton;
    private javax.swing.JPanel productContainer;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JButton snackButton;
    private javax.swing.JLabel subtotalLabel;
    private javax.swing.JLabel subtotalText;
    private javax.swing.JLabel taxLabel;
    private javax.swing.JLabel taxRateText;
    // End of variables declaration//GEN-END:variables
}
