
package Admin;

import static com.mysql.cj.conf.PropertyKey.USER;
import org.mindrot.jbcrypt.BCrypt;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class User extends javax.swing.JPanel {
           private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/data";  
           private static final String USER = "root";  
           private static final String PASS = "Chay00))";
           private File selectedImageFile = null;
           
    public User() {
        initComponents();
        table_user();
    }
     public class UserAdd {
         
    // Method to hash the password
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    public static void adduser(String username, String password, String position, String first_name, String last_name, String gender, String phone, String imagepath) {
        
        String hashedPassword = hashPassword(password);  // Hash the plain text password

        
        String query = "INSERT INTO Users (username, password, login_as, first_name, last_name, gender, phone_number, image_path_user) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {
             
            stmt.setString(1, username);                 
            stmt.setString(2, hashedPassword);                
            stmt.setString(3, position);             
            stmt.setString(4, first_name);                
            stmt.setString(5, last_name);             
            stmt.setString(6, gender);
            stmt.setString(7, phone);
            stmt.setString(8, imagepath);
            
          
            
            conn.setAutoCommit(true);
            
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    public void table_user() {
    DefaultTableModel model = (DefaultTableModel) jTableProduct.getModel();
    model.setRowCount(0);

    String sql = "SELECT user_id, first_name, last_name, gender, phone_number, login_as, date_of_joining FROM Users";

    try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("user_id"),      
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("gender"),
                rs.getString("phone_number"),
                rs.getString("login_as"),
                rs.getString("date_of_joining")
            });
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
   public void clear(){ 
        
       jTextFieldFirstName.setText("");
        jTextFieldLastname.setText("");
        jTextFieldPhone.setText("");
        jTextFielduser.setText("");
        jTextFieldps.setText("");
        jTextFieldSearch.setText("");
        
         selectedImageFile = null;
        jLabel8.setIcon(null);
        
        
    }
   
    public class UserUpdate {
        
        public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
        
    public static void updateuser(int Id, String username, String pass, String position, String first_name, String last_name, String gender, String phone, String imagepath) {
        String query = "UPDATE Users SET username=?, password=?, login_as=?, first_name=?, last_name=?, gender=?, phone_number=? , image_path_user =? " +
               "WHERE user_id=? AND login_as <> 'admin'";
       
        String hashedPassword = hashPassword(pass);  // Hash the password before updating it

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); 
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);      
            stmt.setString(2, hashedPassword); 
            stmt.setString(3, position);      
            stmt.setString(4, first_name);    
            stmt.setString(5, last_name);     
            stmt.setString(6, gender);        
            stmt.setString(7, phone);  
            stmt.setString(8, imagepath);  

            stmt.setInt(9, Id);              

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                JOptionPane.showMessageDialog(null, "Cannot update admin account!");
                return;
            }
            System.out.println("User information updated successfully.");
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating user information.");
        }
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
        jTextFieldFirstName = new javax.swing.JTextField();
        jTextFieldLastname = new javax.swing.JTextField();
        jTextFieldPhone = new javax.swing.JTextField();
        jTextFielduser = new javax.swing.JTextField();
        jButtonClear = new javax.swing.JButton();
        jButtonimport = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldps = new javax.swing.JPasswordField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProduct = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabelNumber = new javax.swing.JLabel();
        jLabelwarn = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(1163, 604));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelSearchID.setBackground(new java.awt.Color(204, 204, 204));

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

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel1.setText("First Name :");

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel2.setText("Last Name :");

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel3.setText("Gender :");

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel4.setText("Phone :");

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel5.setText("Position :");

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel6.setText("Username :");

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

        jTextFieldFirstName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextFieldFirstName.addActionListener(this::jTextFieldFirstNameActionPerformed);

        jTextFieldLastname.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextFieldLastname.addActionListener(this::jTextFieldLastnameActionPerformed);

        jTextFieldPhone.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextFieldPhone.addActionListener(this::jTextFieldPhoneActionPerformed);

        jTextFielduser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextFielduser.addActionListener(this::jTextFielduserActionPerformed);

        jButtonClear.setBackground(new java.awt.Color(153, 153, 153));
        jButtonClear.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jButtonClear.setForeground(new java.awt.Color(255, 255, 255));
        jButtonClear.setText("Clear");
        jButtonClear.addActionListener(this::jButtonClearActionPerformed);

        jButtonimport.setText("Import");
        jButtonimport.addActionListener(this::jButtonimportActionPerformed);

        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "cashier", "Stock Manager", "Lead Cashier" }));

        jLabel9.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel9.setText("Password :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jButtonAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonUpdateProduct)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonDeleteProduct)
                                .addGap(0, 36, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldFirstName))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButtonClear))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButtonimport)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldLastname)
                                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldps, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextFieldPhone, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFielduser))))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldLastname, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFielduser, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButtonClear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(jButtonimport)
                        .addGap(15, 130, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonUpdateProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonDeleteProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37))))
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 130, 370, 470));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

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
                "Employee ID", "First Name", "Last Name", "Gender", "Phone Number", "Position", "Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Long.class, java.lang.String.class, java.lang.String.class
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
        );

        add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 86, 781, 518));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Low Stock products : ");

        jLabelNumber.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelNumber.setForeground(new java.awt.Color(255, 0, 0));

        jLabelwarn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/image_admin/alert 1(1).png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(jLabelwarn)))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelwarn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addContainerGap())
        );

        add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 10, 370, -1));

        getAccessibleContext().setAccessibleName("");
        getAccessibleContext().setAccessibleDescription("");
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

        //Filter JTable by product_id
        sorter.setRowFilter(RowFilter.regexFilter("^" + id + "$", 0)); //Only show rows where column 0 matches this Product ID exactly

        //^ = start of the cell text
        //$ = end of the cell text

        if (jTableProduct.getRowCount() == 0) {
            sorter.setRowFilter(null); // reset
            JOptionPane.showMessageDialog(this, "Product ID not found in table!");
            return;
        }

        String query = "SELECT * FROM Users WHERE user_id = '"+search+"'  ";
        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadAndShowImage(id);
    }//GEN-LAST:event_jButtonSeaarchActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTableProduct.getModel();
        TableRowSorter<DefaultTableModel> sorter =
        (TableRowSorter<DefaultTableModel>) jTableProduct.getRowSorter();

        if (sorter == null) {
            sorter = new TableRowSorter<>(model);
            jTableProduct.setRowSorter(sorter);
        }
        jTextFieldSearch.setText("");
        sorter.setRowFilter(null);

        if (sorter == null) {
            sorter = new TableRowSorter<>(model);
            jTableProduct.setRowSorter(sorter);
        }

        // Clear search text + remove filter
        jTextFieldSearch.setText("");
        sorter.setRowFilter(null);

        

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddProductActionPerformed

        String first_name = jTextFieldFirstName.getText();
        String last_name = jTextFieldLastname.getText();
        String gender = jComboBox1.getSelectedItem().toString();
        String phone = jTextFieldPhone.getText();
        String position = jComboBox2.getSelectedItem().toString();
        String username = jTextFielduser.getText();
        String pass = jTextFieldps.getText();
        
        // Get image path (null if no image selected)
        String imagePath = null;
        if (selectedImageFile != null) {
            imagePath = selectedImageFile.getAbsolutePath();
        }

        UserAdd.adduser(username, pass, position, first_name, last_name, gender, phone, imagePath);
        table_user();

        clear();
        
        
        
    }//GEN-LAST:event_jButtonAddProductActionPerformed

    private void jButtonUpdateProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateProductActionPerformed
        DefaultTableModel dt = (DefaultTableModel) jTableProduct.getModel();
        int selectedRow = jTableProduct.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a user to update");
            return;
        }

        // Get product ID from the first column (index 0)
        String UserId = dt.getValueAt(selectedRow, 0).toString();

        
        String first_name = jTextFieldFirstName.getText();
        String last_name = jTextFieldLastname.getText();
        String gender = jComboBox1.getSelectedItem().toString();
        String phone = jTextFieldPhone.getText();
        String position = jComboBox2.getSelectedItem().toString();
        String username = jTextFielduser.getText();
        String pass = jTextFieldps.getText();

        String roleFromTable = dt.getValueAt(selectedRow, 5).toString(); // login_as

        if ("admin".equalsIgnoreCase(roleFromTable)) {
            JOptionPane.showMessageDialog(null, "Admins cannot be updated.");
            return;
        }
        
        String imagePath = null;
        if (selectedImageFile != null) {
            imagePath = selectedImageFile.getAbsolutePath();
        }
        
        UserUpdate.updateuser(Integer.parseInt(UserId), username, pass, position, first_name, last_name, gender, phone, imagePath);

        table_user();
        clear();
    }//GEN-LAST:event_jButtonUpdateProductActionPerformed

    private void jButtonDeleteProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteProductActionPerformed

        DefaultTableModel dt = (DefaultTableModel) jTableProduct.getModel();
        int selectedRow = jTableProduct.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a product to delete");
            return;
        }

        // Get product ID from the first column (index 0)
        String Id = dt.getValueAt(selectedRow, 0).toString();

        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(
            null,
            "Are you sure you want to delete this product?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String query = "DELETE FROM Users WHERE user_id = ? AND login_as <> 'admin'";

        try (java.sql.Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(Id));
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Product deleted successfully");
                table_user();
                clear();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete product");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting product: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid product ID format");
        }

    }//GEN-LAST:event_jButtonDeleteProductActionPerformed

    private void jTextFieldFirstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFirstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFirstNameActionPerformed

    private void jTextFieldLastnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLastnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLastnameActionPerformed

    private void jTextFieldPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPhoneActionPerformed

    private void jTextFielduserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFielduserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFielduserActionPerformed

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed

        clear();
    }//GEN-LAST:event_jButtonClearActionPerformed

    private void jButtonimportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonimportActionPerformed
       
         JFileChooser fileChooser = new JFileChooser();
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Image Files", "jpg", "jpeg", "png", "gif"
    ));

    int result = fileChooser.showOpenDialog(this);
    if (result != JFileChooser.APPROVE_OPTION) {
        System.out.println("File selection cancelled");
        return;
    }

    File originalFile = fileChooser.getSelectedFile();
    System.out.println("Original selected file: " + originalFile.getAbsolutePath());

    try {
        // 1) Create your app image folder (relative to project run folder)
        Path imagesDir = Paths.get("images_user");
        Files.createDirectories(imagesDir);

        // 2) Make a safe unique file name (keep extension)
        String ext = getFileExtension(originalFile.getName()); // like "png"
        String newFileName = "img_" + UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);

        // 3) Copy to new folder
        Path targetPath = imagesDir.resolve(newFileName);

        Files.copy(originalFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        // 4) Now selectedImageFile = COPIED file (new path)
        selectedImageFile = targetPath.toFile();
        System.out.println("Copied image path: " + selectedImageFile.getAbsolutePath());

        // 5) Read from the copied file and display
        BufferedImage image = ImageIO.read(selectedImageFile);
        if (image == null) {
            JOptionPane.showMessageDialog(this, "Failed to load image after copying.", "Error", JOptionPane.ERROR_MESSAGE);
            selectedImageFile = null;
            return;
        }

        int width = jLabel8.getWidth();
        int height = jLabel8.getHeight();
        if (width <= 0 || height <= 0) {
            width = 100;
            height = 100;
        }

        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        jLabel8.setIcon(new ImageIcon(scaledImage));

        JOptionPane.showMessageDialog(this,
                "Image saved to: " + selectedImageFile.getPath(),
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );

        // If you save to DB, save selectedImageFile.getPath() (THIS is the new folder path)

    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Error copying/loading image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        selectedImageFile = null;
    }
    
    }//GEN-LAST:event_jButtonimportActionPerformed
    
     // Put this INSIDE your class (same level as jButtonimportActionPerformed)
    private String getFileExtension(String fileName) {
        int dot = fileName.lastIndexOf('.');
        if (dot == -1 || dot == fileName.length() - 1) return "";
        return fileName.substring(dot + 1).toLowerCase();
    }
    
    private String getImagePathFromDB(int user_id) {
    String sql = "SELECT image_path_user FROM Users WHERE user_id = ?"; 
    // change table/column to yours

    try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, user_id);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("image_path_user"); // column name
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null; // not found or error
}
    private void displayImageFromPath(String imgPath) {
    try {
        if (imgPath == null || imgPath.trim().isEmpty()) {
            jLabel8.setIcon(null);
            jLabel8.setText("No image");
            return;
        }

        File imgFile = new File(imgPath);

        // If you stored relative path like "images/a.png"
        // it will load from your running folder (project folder / jar folder)

        if (!imgFile.exists()) {
            jLabel8.setIcon(null);
            jLabel8.setText("Image not found");
            return;
        }

        BufferedImage image = ImageIO.read(imgFile);
        if (image == null) {
            jLabel8.setIcon(null);
            jLabel8.setText("Invalid image");
            return;
        }

        int w = jLabel8.getWidth();
        int h = jLabel8.getHeight();
        if (w <= 0 || h <= 0) { w = 150; h = 150; }

        Image scaled = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        jLabel8.setIcon(new ImageIcon(scaled));
        jLabel8.setText("");

    } catch (Exception e) {
        e.printStackTrace();
        jLabel8.setIcon(null);
        jLabel8.setText("Load error");
    }
}
    public void loadAndShowImage(int id) {
    String path = getImagePathFromDB(id);
    displayImageFromPath(path);
}
    
    private void jTableProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableProductMouseClicked

    }//GEN-LAST:event_jTableProductMouseClicked

    private void jTableProductKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableProductKeyPressed
        int r = jTableProduct.getSelectedRow();
    if (r < 0) return;

    String id = jTableProduct.getValueAt(r, 0).toString();
    String first_name = jTableProduct.getValueAt(r, 1).toString();
    String last_name = jTableProduct.getValueAt(r, 2).toString();
    String gender = jTableProduct.getValueAt(r, 3).toString();
    String phone = jTableProduct.getValueAt(r, 4).toString();
    String position = jTableProduct.getValueAt(r, 5).toString();

    jTextFieldSearch.setText(id);
    jTextFieldFirstName.setText(first_name);
    jTextFieldLastname.setText(last_name);
    jComboBox1.setSelectedItem(gender);
    jTextFieldPhone.setText(phone);
    jComboBox2.setSelectedItem(position);

    boolean isAdmin = "admin".equalsIgnoreCase(position);

    jTextFieldFirstName.setEditable(!isAdmin);
    jTextFieldLastname.setEditable(!isAdmin);
    jTextFieldPhone.setEditable(!isAdmin);
    jTextFielduser.setEditable(!isAdmin);
    jTextFieldps.setEditable(!isAdmin);

    jComboBox1.setEnabled(!isAdmin);
    jComboBox2.setEnabled(!isAdmin);

    jButtonUpdateProduct.setEnabled(!isAdmin);
    jButtonDeleteProduct.setEnabled(!isAdmin);
        
   
    }//GEN-LAST:event_jTableProductKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonAddProduct;
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonDeleteProduct;
    private javax.swing.JButton jButtonSeaarch;
    private javax.swing.JButton jButtonUpdateProduct;
    private javax.swing.JButton jButtonimport;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelNumber;
    private javax.swing.JLabel jLabelSearchID;
    private javax.swing.JLabel jLabelwarn;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelSearchID;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableProduct;
    private javax.swing.JTextField jTextFieldFirstName;
    private javax.swing.JTextField jTextFieldLastname;
    private javax.swing.JTextField jTextFieldPhone;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JPasswordField jTextFieldps;
    private javax.swing.JTextField jTextFielduser;
    // End of variables declaration//GEN-END:variables
}
