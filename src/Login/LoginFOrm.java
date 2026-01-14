package Login;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import Cashier.cashierUI;
import Admin.Admin_main;
import login.LoginSession;
import login.Operation;

public class LoginFOrm extends JFrame {

    // Theme Colors
    private static final Color PRIMARY   = new Color(0x54, 0x33, 0xFF);
    private static final Color SECONDARY = new Color(0x20, 0xBD, 0xFF);
    private static final Color BG_LIGHT  = new Color(0xF3, 0xF4, 0xF6);
    private static final Color CARD_LIGHT = Color.WHITE;

    // ✅ FIXED: Declare fields as instance variables so they're accessible
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    public LoginFOrm() {
        super("POS Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        // Page background
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BG_LIGHT);
        wrapper.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Main white card container
        JPanel card = new RoundedPanel(24);
        card.setLayout(new GridLayout(1, 2));
        card.setBackground(CARD_LIGHT);

        card.add(createLeftLoginPanel());
        card.add(createRightPromoPanel());

        wrapper.add(card, BorderLayout.CENTER);
        setContentPane(wrapper);
    }

    private JPanel createLeftLoginPanel() {
        JPanel left = new JPanel(new BorderLayout());
        left.setOpaque(false);

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(50, 60, 50, 60));

        // small icon circle
        content.add(createIconCircle());
        content.add(Box.createVerticalStrut(18));

        // title + subtitle
        content.add(createTitle());
        content.add(Box.createVerticalStrut(6));
        content.add(createSubtitle());
        content.add(Box.createVerticalStrut(28));

        // ✅ FIXED: Initialize instance variables
        content.add(createLabel("Username"));
        usernameField = createTextField("Enter your username");
        content.add(usernameField);
        content.add(Box.createVerticalStrut(16));

        content.add(createLabel("Password"));
        passwordField = createPasswordField("••••••••");
        content.add(passwordField);
        content.add(Box.createVerticalStrut(16));

        content.add(createLabel("Select Role"));
        roleComboBox = createRoleComboBox();
        content.add(roleComboBox);
        content.add(Box.createVerticalStrut(22));

        // login button
        JButton loginBtn = createLoginButton();
        content.add(loginBtn);

        // footer at bottom
        content.add(Box.createVerticalGlue());
        content.add(Box.createVerticalStrut(30));
        content.add(createFooter());

        left.add(content, BorderLayout.CENTER);
        return left;
    }

    private JComponent createIconCircle() {
        JPanel iconRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        iconRow.setOpaque(false);

        RoundedPanel circle = new RoundedPanel(999);
        circle.setBackground(new Color(PRIMARY.getRed(), PRIMARY.getGreen(), PRIMARY.getBlue(), 25));
        circle.setPreferredSize(new Dimension(44, 44));
        circle.setLayout(new GridBagLayout());

        JLabel icon = new JLabel("🛒");
        icon.setFont(new Font("SansSerif", Font.PLAIN, 20));
        circle.add(icon);

        iconRow.add(circle);
        return iconRow;
    }

    private JLabel createTitle() {
        JLabel title = new JLabel("POS Login");
        title.setFont(new Font("Inter", Font.BOLD, 30));
        title.setForeground(new Color(0x11, 0x11, 0x11));
        return title;
    }

    private JLabel createSubtitle() {
        JLabel subtitle = new JLabel(
                "<html><span style='color:#6B7280;'>Welcome back! Please enter your credentials to access the terminal.</span></html>"
        );
        subtitle.setFont(new Font("Inter", Font.PLAIN, 13));
        return subtitle;
    }

    private JLabel createFooter() {
        JLabel footer = new JLabel("©2024 MartSys POS. All rights reserved.");
        footer.setFont(new Font("Inter", Font.PLAIN, 11));
        footer.setForeground(new Color(0x9C, 0xA3, 0xAF));
        return footer;
    }

    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Inter", Font.BOLD, 12));
        l.setForeground(new Color(0x37, 0x41, 0x51));
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        l.setBorder(new EmptyBorder(0, 2, 6, 0));
        return l;
    }

    private JTextField createTextField(String tooltip) {
        JTextField tf = new JTextField();
        styleInput(tf, tooltip);
        tf.setAlignmentX(Component.LEFT_ALIGNMENT);
        return tf;
    }

    private JPasswordField createPasswordField(String tooltip) {
        JPasswordField pf = new JPasswordField();
        styleInput(pf, tooltip);
        pf.setAlignmentX(Component.LEFT_ALIGNMENT);
        return pf;
    }

    private JComboBox<String> createRoleComboBox() {
        JComboBox<String> role = new JComboBox<>(new String[]{"Cashier", "Admin"});
        styleCombo(role);
        role.setAlignmentX(Component.LEFT_ALIGNMENT);
        return role;
    }

    private void styleInput(JTextField field, String tooltip) {
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        field.setPreferredSize(new Dimension(10, 46));
        field.setFont(new Font("Inter", Font.PLAIN, 14));
        field.setToolTipText(tooltip);

        field.setOpaque(true);
        field.setBackground(Color.WHITE);
        field.setForeground(new Color(0x11, 0x11, 0x11));
        field.setCaretColor(new Color(0x11, 0x11, 0x11));

        RoundedInputBorder border =
                new RoundedInputBorder(new Color(0xD1, 0xD5, 0xDB), 10, 1);

        field.setBorder(border);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                border.setColor(PRIMARY);
                field.repaint();
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                border.setColor(new Color(0xD1, 0xD5, 0xDB));
                field.repaint();
            }
        });
    }

    private void styleCombo(JComboBox<String> combo) {
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        combo.setPreferredSize(new Dimension(10, 46));
        combo.setFont(new Font("Inter", Font.PLAIN, 14));
        combo.setBackground(Color.WHITE);
        combo.setForeground(new Color(0x11, 0x11, 0x11));
        combo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        combo.setFocusable(false);

        combo.setBorder(new RoundedInputBorder(new Color(0xD1, 0xD5, 0xDB), 10, 1));
        combo.setUI(new CleanComboBoxUI(PRIMARY));

        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                lbl.setBorder(new EmptyBorder(8, 12, 8, 12));
                return lbl;
            }
        });
    }

    // ✅ FIXED: Login button now properly accesses instance variables
    private JButton createLoginButton() {
        JButton btn = new JButton("Login");

        btn.setFont(new Font("Inter", Font.BOLD, 14));
        btn.setBackground(PRIMARY);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);  // ✅ ADDED: Remove border
        btn.setContentAreaFilled(true);  // ✅ ADDED: Ensure background is filled
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(12, 18, 12, 18));
        btn.setOpaque(true);

        btn.setPreferredSize(new Dimension(10, 46));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(PRIMARY.getRed() - 20, PRIMARY.getGreen() - 20, PRIMARY.getBlue() - 20));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(PRIMARY);
            }
        });

        // ✅ FIXED: Login action
        btn.addActionListener(e -> handleLogin());

        return btn;
    }

    // ✅ NEW: Separate login handler method
    private void handleLogin() {
        try {
            // ✅ FIXED: Get text from instance variables correctly
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String usertype = roleComboBox.getSelectedItem().toString();

            // Validate input
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter both username and password",
                        "Input Required",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Attempt login
            if (Operation.isLogin(username, password, usertype, this)) {
                // ✅ FIXED: Check usertype with proper case
                if ("admin".equalsIgnoreCase(LoginSession.Usertype)) {
                    // Show success message BEFORE opening new window
                    JOptionPane.showMessageDialog(this,
                            "Login successful! Welcome " + LoginSession.Nickname,
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                    Admin_main admin = new Admin_main();
                    admin.setVisible(true);
                    this.dispose();

                } else if ("cashier".equalsIgnoreCase(LoginSession.Usertype)) {
                    // Show success message BEFORE opening new window
                    JOptionPane.showMessageDialog(this,
                            "Login successful! Welcome " + LoginSession.Nickname,
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                    cashierUI cash = new cashierUI();
                    cash.setVisible(true);
                    this.dispose();
                }

            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid username, password, or user type",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private JPanel createRightPromoPanel() {
        JPanel right = new JPanel(new BorderLayout());
        right.setBackground(PRIMARY);

        right.add(new ShapesPanel(), BorderLayout.CENTER);

        JPanel overlay = new JPanel();
        overlay.setOpaque(false);
        overlay.setLayout(new BoxLayout(overlay, BoxLayout.Y_AXIS));
        overlay.setBorder(new EmptyBorder(60, 60, 60, 60));

        overlay.add(Box.createVerticalGlue());
        overlay.add(createPosMockCard());
        overlay.add(Box.createVerticalStrut(30));

        JLabel headline = new JLabel("<html><div style='text-align:center;'>Fast &amp; Reliable<br/>Checkout</div></html>");
        headline.setAlignmentX(Component.CENTER_ALIGNMENT);
        headline.setFont(new Font("Inter", Font.BOLD, 28));
        headline.setForeground(Color.WHITE);
        overlay.add(headline);

        overlay.add(Box.createVerticalStrut(12));

        JLabel desc = new JLabel("<html><div style='text-align:center; color: rgba(255,255,255,0.80);'>Streamline your mart operations with our integrated Point of Sale system.</div></html>");
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        desc.setFont(new Font("Inter", Font.PLAIN, 13));
        overlay.add(desc);

        overlay.add(Box.createVerticalStrut(18));
        overlay.add(createDots());
        overlay.add(Box.createVerticalGlue());

        right.add(overlay, BorderLayout.SOUTH);
        return right;
    }

    private JComponent createPosMockCard() {
        RoundedPanel shell = new RoundedPanel(18);
        shell.setBackground(new Color(0x11, 0x12, 0x13));
        shell.setBorder(new EmptyBorder(12, 12, 12, 12));
        shell.setAlignmentX(Component.CENTER_ALIGNMENT);
        shell.setMaximumSize(new Dimension(420, 320));
        shell.setLayout(new BorderLayout());

        RoundedPanel inner = new RoundedPanel(14);
        inner.setBackground(Color.WHITE);
        inner.setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(PRIMARY);
        top.setBorder(new EmptyBorder(10, 12, 10, 12));

        JLabel title = new JLabel("POS TERMINAL 01");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Inter", Font.BOLD, 11));
        top.add(title, BorderLayout.WEST);

        JPanel lights = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        lights.setOpaque(false);
        lights.add(createCircle(new Color(255, 255, 255, 120)));
        lights.add(createCircle(new Color(255, 255, 255, 120)));
        lights.add(createCircle(new Color(134, 239, 172)));
        top.add(lights, BorderLayout.EAST);

        JPanel body = new JPanel(new GridLayout(1, 2));
        body.setBackground(Color.WHITE);

        body.add(createReceiptList());
        body.add(createActionsPanel());

        inner.add(top, BorderLayout.NORTH);
        inner.add(body, BorderLayout.CENTER);

        shell.add(inner, BorderLayout.CENTER);
        return shell;
    }

    private JPanel createReceiptList() {
        JPanel list = new JPanel(new BorderLayout());
        list.setBackground(Color.WHITE);

        JPanel items = new JPanel();
        items.setBackground(Color.WHITE);
        items.setLayout(new BoxLayout(items, BoxLayout.Y_AXIS));
        items.setBorder(new EmptyBorder(12, 12, 12, 12));

        items.add(createRow("Fresh Milk 1L", "$2.50", true));
        items.add(createRow("Whole Wheat Bread", "$3.20", true));
        items.add(createRow("Mineral Water", "$1.00", true));
        items.add(createRow("Chocolate Bar", "$1.50", false));
        items.add(Box.createVerticalGlue());

        JPanel total = new JPanel(new BorderLayout());
        total.setBackground(new Color(0xF9, 0xFA, 0xFB));
        total.setBorder(new EmptyBorder(10, 12, 10, 12));

        JLabel totalL = new JLabel("TOTAL");
        totalL.setForeground(new Color(0x6B, 0x72, 0x80));
        totalL.setFont(new Font("Inter", Font.BOLD, 12));

        JLabel totalV = new JLabel("$8.20");
        totalV.setForeground(PRIMARY);
        totalV.setFont(new Font("Inter", Font.BOLD, 18));

        total.add(totalL, BorderLayout.WEST);
        total.add(totalV, BorderLayout.EAST);

        list.add(items, BorderLayout.CENTER);
        list.add(total, BorderLayout.SOUTH);
        return list;
    }

    private JPanel createActionsPanel() {
        JPanel rightCol = new JPanel(new GridLayout(4, 1, 8, 8));
        rightCol.setBackground(new Color(0xF9, 0xFA, 0xFB));
        rightCol.setBorder(new EmptyBorder(10, 10, 10, 10));

        rightCol.add(createIconBox("🍱"));
        rightCol.add(createIconBox("🥤"));
        rightCol.add(createIconBox("📦"));

        RoundedPanel pay = new RoundedPanel(10);
        pay.setBackground(new Color(PRIMARY.getRed(), PRIMARY.getGreen(), PRIMARY.getBlue(), 230));
        pay.setLayout(new GridBagLayout());

        JLabel payLabel = new JLabel("PAY");
        payLabel.setForeground(Color.WHITE);
        payLabel.setFont(new Font("Inter", Font.BOLD, 14));

        pay.add(payLabel);
        rightCol.add(pay);

        return rightCol;
    }

    private JPanel createRow(String name, String price, boolean boldPrice) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setBorder(new EmptyBorder(6, 0, 6, 0));

        JLabel item = new JLabel(name);
        item.setFont(new Font("Inter", Font.PLAIN, 12));
        item.setForeground(new Color(0x11, 0x11, 0x11));

        JLabel value = new JLabel(price);
        value.setFont(new Font("Inter", boldPrice ? Font.BOLD : Font.PLAIN, 12));
        value.setForeground(boldPrice ? new Color(0x11, 0x11, 0x11) : new Color(0x9C, 0xA3, 0xAF));

        row.add(item, BorderLayout.WEST);
        row.add(value, BorderLayout.EAST);

        return row;
    }

    private JComponent createIconBox(String emoji) {
        RoundedPanel box = new RoundedPanel(10);
        box.setBackground(Color.WHITE);
        box.setBorder(new LineBorder(new Color(0xE5, 0xE7, 0xEB), 1));
        box.setLayout(new GridBagLayout());

        JLabel l = new JLabel(emoji);
        l.setFont(new Font("SansSerif", Font.PLAIN, 18));
        l.setForeground(new Color(0x9C, 0xA3, 0xAF));

        box.add(l);
        return box;
    }

    private JComponent createDots() {
        JPanel dots = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        dots.setOpaque(false);
        dots.add(createPill(24, 6, Color.WHITE));
        dots.add(createPill(6, 6, new Color(255, 255, 255, 110)));
        dots.add(createPill(6, 6, new Color(255, 255, 255, 110)));
        return dots;
    }

    private JComponent createCircle(Color c) {
        return new JComponent() {
            @Override public Dimension getPreferredSize() { return new Dimension(8, 8); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(c);
                g2.fillOval(0, 0, 8, 8);
                g2.dispose();
            }
        };
    }

    private JComponent createPill(int w, int h, Color c) {
        return new JComponent() {
            @Override public Dimension getPreferredSize() { return new Dimension(w, h); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(c);
                g2.fillRoundRect(0, 0, w, h, h, h);
                g2.dispose();
            }
        };
    }

    private class ShapesPanel extends JPanel {
        ShapesPanel() {
            setOpaque(true);
            setBackground(PRIMARY);
        }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(255, 255, 255, 14));
            g2.fillRoundRect(getWidth() - 260, 0, 260, 260, 140, 140);

            g2.setColor(new Color(255, 255, 255, 12));
            g2.fillRoundRect(0, getHeight() - 360, 360, 360, 220, 220);

            g2.setColor(new Color(SECONDARY.getRed(), SECONDARY.getGreen(), SECONDARY.getBlue(), 60));
            g2.fillOval(getWidth() - 220, 120, 160, 160);

            g2.dispose();
        }
    }

    private static class RoundedPanel extends JPanel {
        private final int radius;
        RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class RoundedInputBorder extends javax.swing.border.AbstractBorder {
        private Color color;
        private final int radius;
        private final int thickness;

        RoundedInputBorder(Color color, int radius, int thickness) {
            this.color = color;
            this.radius = radius;
            this.thickness = thickness;
        }

        public void setColor(Color c) { this.color = c; }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(12, 14, 12, 14);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = 14; insets.right = 14; insets.top = 12; insets.bottom = 12;
            return insets;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = width - 1;
            int h = height - 1;

            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.draw(new RoundRectangle2D.Float(x, y, w, h, radius, radius));

            g2.dispose();
        }
    }

    private static class CleanComboBoxUI extends BasicComboBoxUI {
        private final Color primary;

        CleanComboBoxUI(Color primary) {
            this.primary = primary;
        }

        @Override
        protected JButton createArrowButton() {
            JButton btn = new BasicArrowButton(SwingConstants.SOUTH);
            btn.setBorder(new EmptyBorder(0, 10, 0, 10));
            btn.setBackground(Color.WHITE);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.setFocusPainted(false);
            return btn;
        }

        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            // remove default highlight
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new LoginFOrm().setVisible(true));
    }
}