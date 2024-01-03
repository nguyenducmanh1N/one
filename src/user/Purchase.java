package user;

import com.sun.net.httpserver.Headers;
import dao.ProductDao;
import dao.PurchaseDao;
import dao.Statictics;
import dao.UserDao;
import java.awt.Color;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Purchase extends javax.swing.JFrame {

    PurchaseDao purchaseDao = new PurchaseDao();
    ProductDao productDao = new ProductDao();
    int xx, xy;
    Color notEdit = new Color(204, 204, 204);
    Color primaryColor = new Color(102,102,102);
    DefaultTableModel model;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();

    int rowIndex;
    private int qty = 0;
    private double price = 0.0;
    private double total = 0.0;
    private int pId;

    Statictics statictics = new Statictics();
    UserDao user = new UserDao();
    
    public Purchase() {
        initComponents();
        init();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên SP", "Danh mục", "Số lượng", "Giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable1);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 450, 150));

        jTable2.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Mua hàng", "Mã SP", "Tên SP", "Tổng SP", "Giá", "Tổng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable2);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, 450, 140));

        jTextField1.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, 200, 30));

        jTextField2.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        jTextField2.setToolTipText("");
        getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 110, 200, 30));

        jTextField3.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 160, 200, 30));

        jTextField4.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        jTextField4.setText("0");
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField4KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 210, 200, 30));

        jButton1.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jButton1.setText("Hủy");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 320, 90, 50));

        jButton2.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jButton2.setText("Thêm");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 260, 90, 50));

        jButton3.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jButton3.setText("Mua");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 260, 90, 50));

        jButton4.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jButton4.setText("In");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 320, 90, 50));

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel1.setText("Mã mua hàng :");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, -1, -1));

        jLabel2.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel2.setText("Tên sản phẩm :");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 140, -1, -1));

        jLabel3.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel3.setText("Số lượng :");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 190, -1, -1));

        jLabel4.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel4.setText("Tìm kiếm :");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 40, 80, -1));

        jLabel5.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel5.setText("Tổng cộng : 0.0");
        jLabel5.setName(""); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 200, -1, -1));
        jLabel5.getAccessibleContext().setAccessibleName("");

        jLabel6.setFont(new java.awt.Font("Eras Bold ITC", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText(" X");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, 29, 25));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Mua Hàng");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 110, 30));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void init() {
        setLocation(470, 230);
        jTextField2.setText(String.valueOf(purchaseDao.getMaxRow()));

        productsTable();
        purchaseTable();
        pId = purchaseDao.getMaxRow();

    }

    private void productsTable() {
        productDao.getProductsValue(jTable1, "");
        model = (DefaultTableModel) jTable1.getModel();
        jTable1.setRowHeight(30);
        jTable1.setShowGrid(true);
        jTable1.setGridColor(Color.BLACK);
        jTable1.setBackground(Color.WHITE);
        jTable1.setSelectionBackground(Color.LIGHT_GRAY);

    }

    private void purchaseTable() {

        model = (DefaultTableModel) jTable2.getModel();
        jTable2.setRowHeight(30);
        jTable2.setShowGrid(true);
        jTable2.setGridColor(Color.BLACK);
        jTable2.setBackground(Color.WHITE);
        jTable2.setSelectionBackground(Color.LIGHT_GRAY);

    }

    private void clear() {
        jTextField2.setText(String.valueOf(purchaseDao.getMaxRow()));
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField1.setText("");
        jTable1.clearSelection();
        price = 0.0;
        qty = 0;
    }

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        setVisible(false);
        UserDashboard.jPanel5.setBackground(primaryColor);
    }//GEN-LAST:event_jLabel6MouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here
        for (double i = 0.1; i <= 1.0; i += 0.1) {
            String s = "" + i;
            float f = Float.parseFloat(s);
            this.setOpacity(f);
            try {
                Thread.sleep(40);
            } catch (InterruptedException ex) {
                Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_formWindowOpened

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_formMouseDragged

    private void jTextField4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField4KeyTyped

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        model = (DefaultTableModel) jTable1.getModel();
        rowIndex = jTable1.getSelectedRow();

        jTextField3.setText(model.getValueAt(rowIndex, 1).toString());
        String s1 = model.getValueAt(rowIndex, 3).toString();
        String s2 = model.getValueAt(rowIndex, 4).toString();
        qty = Integer.parseInt(s1);
        price = Double.parseDouble(s2);
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (jTextField3.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "chon 1 san pham bat ki", "warning", 2);
        } else {
            model = (DefaultTableModel) jTable1.getModel();
            int proId = Integer.parseInt(model.getValueAt(rowIndex, 0).toString());
            if (!isProductExist(proId)) {
                if (!(qty <= 0)) {
                    int newQty = Integer.parseInt(jTextField4.getText());
                    if (newQty != 0) {
                        if (!(newQty > qty)) {

                            String pname = jTextField3.getText();
                            String t = String.format("%.2f", price * (double) newQty);
                            Object[] data = {pId, proId, pname, newQty, price, t};
                            model = (DefaultTableModel) jTable2.getModel();
                            model.addRow(data);
                            total += price * (double) newQty;
                            jLabel5.setText(String.format("Tổng :" + "%.2f", total));
                            pId++;
                            clear();

                        } else {
                            JOptionPane.showMessageDialog(this, "so luong hang k du ", "warning", 2);

                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "them so luong san pham muon dat ", "warning", 2);

                    }
                } else {
                    JOptionPane.showMessageDialog(this, "het hang ", "warning", 2);

                }
            } else {
                JOptionPane.showMessageDialog(this, "san pham da co trong danh sach mua ", "warning", 2);

            }

        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        model = (DefaultTableModel) jTable2.getModel();
        if (model.getRowCount() > 0) {
            String[] value = new String[4];
            String email = UserDashboard.userEmail.getText();
            value = purchaseDao.getUserValue(email);
            int uid = Integer.parseInt(value[0]);
            String uname = value[1];
            String uPhone = value[2];
            String address = value[3];
            String purchaseDate = df.format(date);
            for (int i = 0; i < model.getRowCount(); i++) {
                int id = Integer.parseInt(model.getValueAt(i, 0).toString());
                int pid = Integer.parseInt(model.getValueAt(i, 1).toString());
                String pName = model.getValueAt(i, 2).toString();
                int q = Integer.parseInt(model.getValueAt(i, 3).toString());
                double pri = Double.parseDouble(model.getValueAt(i, 4).toString());
                double tot = Double.parseDouble(model.getValueAt(i, 5).toString());
                purchaseDao.insert(id, uid, uname, uPhone, pid, pName, q, pri, tot, purchaseDate, address, null, null, "pending");
                int newQuantity = purchaseDao.getQty(pid) - q;
                purchaseDao.qtyUpdate(pid, newQuantity);
                


            }
            statictics.user(user.getUserId(email));
            
            JOptionPane.showMessageDialog(this, "thanh cong ");

        } else {
            JOptionPane.showMessageDialog(this, "ban chua chon san pham nao ", "warning", 2);

        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        jTable1.setModel(new DefaultTableModel(null, new Object[]{"Mã SP", "Tên SP",
            "Danh mục", "SL", "Giá"}));
        productDao.getProductsValue(jTable1, jTextField1.getText());
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            String email = UserDashboard.userEmail.getText();
            MessageFormat header = new MessageFormat("Receipt -->  " + "Email" +email+ "    " + "Total" + total);
            MessageFormat footer = new MessageFormat("Page{0,number,integer}");
            jTable2.print(JTable.PrintMode.FIT_WIDTH,header, footer);
        } catch (PrinterException ex) {
            Logger.getLogger(Purchase.class.getName()).log(Level.SEVERE, null, ex);

        }
        

    }//GEN-LAST:event_jButton4ActionPerformed

    private boolean isProductExist(int proID) {
        model = (DefaultTableModel) jTable2.getModel();
        if (model.getRowCount() > 0) {
            for (int i = 0; i < model.getRowCount(); i++) {
                int newProId = Integer.parseInt(model.getValueAt(i, 1).toString());
                if (newProId == proID) {
                    return true;
                }
            }
        }

        return false;

    }

    public void setDefautt() {

    }

    public static void main(String args[]) {
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Purchase().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
