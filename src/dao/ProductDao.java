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

public class ProductDao {

    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    // geet product table max row
    public int getMaxRow() {
        int row = 0;
        try {
            con = MyConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select max(pid) from product");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
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
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
            }

            return row + 1;
        }
    }

    // 
    public int countCategories() {
        int total = 0;
        try {
            con = MyConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select count(*) as 'total'from category");
            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;

    }

    public String[] getCat() {
        String[] categories = new String[countCategories()];
        try {
            con = MyConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select * from category");
            int i = 0;
            while (rs.next()) {
                categories[i] = rs.getString(2);
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;

    }

    // kiem tra san pham ton tai hay chua
    public boolean isIDExist(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM product WHERE pid = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    // ktra pro va cat ton tai hay chua
    public boolean isProCatExist(String pro, String cat) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM product WHERE pname = ? and  cname = ?");
            ps.setString(1, pro);
            ps.setString(2, cat);
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    // luu san pham 
    public void insert(int id, String pname, String cname, int qty, double price) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = MyConnection.getConnection();
            String sql = "insert into product values(?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, pname);
            ps.setString(3, cname);
            ps.setInt(4, qty);
            ps.setDouble(5, price);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Thêm san pham thành công");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    // update san pham
    // update san pham

    public void update(int id, String pname, String cname, int qty, double price) {
        Connection con = null; // Khởi tạo biến kết nối
        PreparedStatement ps = null;

        try {
            con = MyConnection.getConnection();
            String sql = "update product set pname = ?, cname = ?, pqty = ?, pprice = ?  where pid = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, pname);
            ps.setString(2, cname);
            ps.setInt(3, qty);
            ps.setDouble(4, price);
            ps.setInt(5, id);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật thành công");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
// xoa san pham
    public void delete(int id) {
        int x = JOptionPane.showConfirmDialog(null, "Bạn muốn xóa san pham này?", "Delete Product", JOptionPane.OK_CANCEL_OPTION, 0);
        if (x == JOptionPane.OK_OPTION) {
            Connection con = null;
            PreparedStatement ps = null;

            try {
                con = MyConnection.getConnection();
                // Kiểm tra kết nối trước khi thực hiện câu lệnh SQL
                if (con != null && !con.isClosed()) {
                    ps = con.prepareStatement("DELETE FROM product WHERE pid = ?");
                    ps.setInt(1, id);

                    if (ps.executeUpdate() > 0) {
                        JOptionPane.showMessageDialog(null, "Đã xóa san pham . ");
                    }
                } else {
                    // Xử lý khi kết nối đã bị đóng hoặc không tồn tại
                    JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // tao product data 
    public void getProductsValue(JTable table, String search) {
        String sql = "SELECT * FROM product WHERE CONCAT(pid, pname,cname) LIKE ? ORDER BY pid asc";
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
                row = new Object[5];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getInt(4);
                row[4] = rs.getDouble(5);

                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
