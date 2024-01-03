package dao;

import connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PurchaseDao {

    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    // geet category table max row
    public int getMaxRow() {
        int row = 0;
        try {
            con = MyConnection.getConnection(); // Mở kết nối
            st = con.createStatement();
            rs = st.executeQuery("select max(id) from purchase");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Đóng tài nguyên trong khối finally để đảm bảo được gọi dù có lỗi xảy ra hay không
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close(); // Đóng kết nối
                }
            } catch (SQLException ex) {
                Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return row + 1;
    }

    // get user value
    public String[] getUserValue(String email) {
        String[] value = new String[4];
        String sql = "select uid , uname , uphone , uaddress from user where uemail = '" + email + "'";

        try (
                Connection con = MyConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                value[0] = rs.getString(1);
                value[1] = rs.getString(2);
                value[2] = rs.getString(3);
                value[3] = rs.getString(4);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return value;
    }

    // nhap data vao bang purchase 
    public void insert(int id, int uid, String uname, String uPhone,
            int pid, String pname, int qty, double price, double total, String pDate, String address,
            String rDate, String supplier, String status) {

        String sql = "insert into purchase values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            Connection con = MyConnection.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setInt(2, uid);
            ps.setString(3, uname);
            ps.setString(4, uPhone);
            ps.setInt(5, pid);
            ps.setString(6, pname);
            ps.setInt(7, qty);
            ps.setDouble(8, price);
            ps.setDouble(9, total);
            ps.setString(10, pDate);
            ps.setString(11, address);
            ps.setString(12, rDate);
            ps.setString(13, supplier);
            ps.setString(14, status);

            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // tinh tong product
    public int getQty(int pid) {
        int qty = 0;

        try {
            Connection con = MyConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select pqty from product where pid = " + pid + "");
            if (rs.next()) {
                qty = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return qty;
    }

    // update product quantity 
    public void qtyUpdate(int pid, int qty) {
        String sql = "update product set pqty = ? where pid = ?";
        try {
            Connection con = MyConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, qty);
            ps.setInt(2, pid);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // chi tiet san pham mua
    public void getProductsValue(JTable table, String search, int uid) {
        String sql = "SELECT * FROM purchase WHERE CONCAT(id, pid ,product_name) LIKE ? and uid =?  ORDER BY id desc";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            ps.setInt(2, uid);
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;

            while (rs.next()) {
                row = new Object[10];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(5);
                row[2] = rs.getString(6);
                row[3] = rs.getInt(7);
                row[4] = rs.getDouble(8);
                row[5] = rs.getDouble(9);
                row[6] = rs.getString(10);
                row[7] = rs.getString(12);
                row[8] = rs.getString(13);
                row[9] = rs.getString(14);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // tao bang tong hop 
    public void getOnthewayProducts(JTable table, String search, String supplier) {
        String sql = "SELECT * FROM purchase WHERE CONCAT(id, pid ,uname ,product_name) LIKE ? and supplier = ? and status = 'on the way' ORDER BY id desc";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            ps.setString(2, supplier);
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;

            while (rs.next()) {
                row = new Object[14];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getInt(5);
                row[5] = rs.getString(6);
                row[6] = rs.getInt(7);
                row[7] = rs.getDouble(8);
                row[8] = rs.getDouble(9);
                row[9] = rs.getString(10);
                row[10] = rs.getString(11);
                row[11] = rs.getString(12);
                row[12] = rs.getString(13);
                row[13] = rs.getString(14);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // tao bang tt van chuyen san pham ncc
       public void getSuppDeliProducts(JTable table, String search, String supplier) {
        String sql = "SELECT * FROM purchase WHERE CONCAT(id, pid ,uname ,product_name) LIKE ? and supplier = ? and status = 'Received' ORDER BY id desc";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            ps.setString(2, supplier);
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[14];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getInt(5);
                row[5] = rs.getString(6);
                row[6] = rs.getInt(7);
                row[7] = rs.getDouble(8);
                row[8] = rs.getDouble(9);
                row[9] = rs.getString(10);
                row[10] = rs.getString(11);
                row[11] = rs.getString(12);
                row[12] = rs.getString(13);
                row[13] = rs.getString(14);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
       
       

    public void setSuppStatus(int id, String supp, String status) {
        String sql = "update purchase set supplier = ? , status =? where id = ?";
        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, supp);
            ps.setString(2, status);
            ps.setInt(3, id);
            // Sử dụng executeUpdate() cho các câu lệnh cập nhật
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "NCC đã được chọn");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // đóng tài nguyên ở đây để đảm bảo không có tài nguyên rò rỉ
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
     public void setDateStatus(int id, String rDate, String status) {
        String sql = "update purchase set receive_date = ? , status =? where id = ?";
        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, rDate);
            ps.setString(2, status);
            ps.setInt(3, id);
            // Sử dụng executeUpdate() cho các câu lệnh cập nhật
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "SAn pham da dc giao");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // đóng tài nguyên ở đây để đảm bảo không có tài nguyên rò rỉ
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void getProductValue(JTable table, String search) {
        String sql = "SELECT * FROM purchase WHERE CONCAT(id, pid ,uname ,product_name) LIKE ?  and status = 'Pending' ORDER BY id desc";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[14];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getInt(5);
                row[5] = rs.getString(6);
                row[6] = rs.getInt(7);
                row[7] = rs.getDouble(8);
                row[8] = rs.getDouble(9);
                row[9] = rs.getString(10);
                row[10] = rs.getString(11);
                row[11] = rs.getString(12);
                row[12] = rs.getString(13);
                row[13] = rs.getString(14);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void refund(int id) {
        int x = JOptionPane.showConfirmDialog(null, "Bạn muốn hoan tra san pham nay này?", "Refund", JOptionPane.OK_CANCEL_OPTION, 0);
        if (x == JOptionPane.OK_OPTION) {
            Connection con = null;
            PreparedStatement ps = null;
            try {
                con = MyConnection.getConnection();
                // Kiểm tra kết nối trước khi thực hiện câu lệnh SQL
                if (con != null && !con.isClosed()) {
                    ps = con.prepareStatement("DELETE FROM purchase WHERE id = ?");
                    ps.setInt(1, id);
                    if (ps.executeUpdate() > 0) {
                        JOptionPane.showMessageDialog(null, "Da hoan .");
                    }
                } else {
                    // Xử lý khi kết nối đã bị đóng hoặc không tồn tại
                    JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                // Đóng tài nguyên trong khối finally để đảm bảo gọi dù có lỗi xảy ra hay không
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (con != null && !con.isClosed()) {
                        con.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void transaction(JTable table, String search) {
        String sql = "SELECT * FROM purchase WHERE CONCAT(id, pid ,uid ) LIKE ?  and status = 'Received' ORDER BY id desc";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[8];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(5);
                row[3] = rs.getInt(7);
                row[4] = rs.getDouble(8);
                row[5] = rs.getDouble(9);
                row[6] = rs.getString(12);
                row[7] = rs.getString(13);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
