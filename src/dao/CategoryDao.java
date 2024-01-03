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

public class CategoryDao {
    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;
    // geet category table max row
    public int getMaxRow() {
        int row = 0;
        try {
            con = MyConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select max(cid) from category");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            return row + 1;
        }
    }

    // kiem tra danh muc toan tai hay chua
    public boolean isCategoryNameExist(String cname) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM category WHERE cname = ?");
            ps.setString(1, cname);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    // kiem tra danh muc toan tai hay chua
    public boolean isIDExist(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM category WHERE cid = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    // insert category into data 
    public void insert(int id, String cname, String desc) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = MyConnection.getConnection();
            String sql = "insert into category values(?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, cname);
            ps.setString(3, desc);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Thêm danh muc thành công");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
// xoa danh muc

    public void delete(int id) {
        int x = JOptionPane.showConfirmDialog(null, "Bạn muốn xóa danh muc này?", "Delete Category", JOptionPane.OK_CANCEL_OPTION, 0);
        if (x == JOptionPane.OK_OPTION) {
            Connection con = null;
            PreparedStatement ps = null;
            try {
                con = MyConnection.getConnection();
                // Kiểm tra kết nối trước khi thực hiện câu lệnh SQL
                if (con != null && !con.isClosed()) {
                    ps = con.prepareStatement("DELETE FROM category WHERE cid = ?");
                    ps.setInt(1, id);
                    if (ps.executeUpdate() > 0) {
                        JOptionPane.showMessageDialog(null, "Đã xóa danh muc.");
                    }
                } else {
                    // Xử lý khi kết nối đã bị đóng hoặc không tồn tại
                    JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // update category
    public void update(int id, String cname, String desc) {
        Connection con = null; // Khởi tạo biến kết nối
        PreparedStatement ps = null;
        try {
            con = MyConnection.getConnection();
            String sql = "update category set cname = ?,cdesc = ?  where cid = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, cname);
            ps.setString(2, desc);
            ps.setInt(3, id);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, " thanh cong");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // tao category data
    public void getCategoriesValue(JTable table, String search) {
        String sql = "SELECT * FROM category WHERE CONCAT(cid, cname) LIKE ? ORDER BY cid desc";
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
                row = new Object[3];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

