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

public class UserDao {

    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    // geet user table max row
    public int getMaxRow() {
        
        int row = 0;
        
        try {
            con = MyConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select max(uid) from user");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            return row + 1;
        }
    }

    // kiem tra email da ton tai hay chua
    public boolean isEmailExist(String email) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM user WHERE uemail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    // kiem tra sdt da ton tai hay chua
    public boolean isPhoneExist(String phone) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement("select * from user where uphone = ? ");
            ps.setString(1, phone);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // insert data into user table
    public void insert(int id, String username, String email, String pass, String phone,
            String seq, String ans, String address) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = MyConnection.getConnection();
            String sql = "insert into user values(?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, username);
            ps.setString(3, email);
            ps.setString(4, pass);
            ps.setString(5, phone);
            ps.setString(6, seq);
            ps.setString(7, ans);
            ps.setString(8, address);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Thêm người dùng thành công");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //update user into data
    public void update(int id, String username, String email, String pass, String phone,
            String seq, String ans, String address) {
        Connection con = null; // Khởi tạo biến kết nối
        PreparedStatement ps = null;

        try {
            con = MyConnection.getConnection();
            String sql = "update user set uname = ?,uemail = ?,upassword =?,uphone = ? ,usecqus = ? ,uans =? ,uaddress =?  where uid = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, pass);
            ps.setString(4, phone);
            ps.setString(5, seq);
            ps.setString(6, ans);
            ps.setString(7, address);
            ps.setInt(8, id);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, " thanh cong");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // xoa User 
    public void delete(int id) {
        int x = JOptionPane.showConfirmDialog(null, "Bạn muốn xóa tài khoản này?", "Delete Account", JOptionPane.OK_CANCEL_OPTION, 0);
        if (x == JOptionPane.OK_OPTION) {
            Connection con = null;
            PreparedStatement ps = null;
            try {
                con = MyConnection.getConnection();
                // Kiểm tra kết nối trước khi thực hiện câu lệnh SQL
                if (con != null && !con.isClosed()) {
                    ps = con.prepareStatement("DELETE FROM user WHERE uid = ?");
                    ps.setInt(1, id);
                    if (ps.executeUpdate() > 0) {
                        JOptionPane.showMessageDialog(null, "Đã xóa tài khoản.");
                    }
                } else {
                    // Xử lý khi kết nối đã bị đóng hoặc không tồn tại
                    JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // cap nhap tt nguoi dung
    public String[] getUserValue(int id) {
        String[] value = new String[8];
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM user WHERE uid = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                value[0] = rs.getString(1);
                value[1] = rs.getString(2);
                value[2] = rs.getString(3);
                value[3] = rs.getString(4);
                value[4] = rs.getString(5);
                value[5] = rs.getString(6);
                value[6] = rs.getString(7);
                value[7] = rs.getString(8);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return value;
    }

    // id nguoi dung
    public int getUserId(String email) {
        int id = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement("SELECT uid FROM user WHERE uemail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return id;
    }

    // tao user data
    public void getUsersValue(JTable table, String search) {
        String sql = "SELECT * FROM user WHERE CONCAT(uid, uname, uemail) LIKE ? ORDER BY uid DESC";
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
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
