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
import user.ForgotPassword;

public class ForgotPasswordDao {
    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;
    // kiem tra email 
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
                ForgotPassword.jTextField2.setText(rs.getString(6));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "email chua ton tai");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ForgotPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ForgotPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    // kiem tra cau tra loi
    public boolean getAns(String email, String newAns) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM user WHERE uemail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                String oldAns = rs.getString(7);
                if (newAns.equals(oldAns)) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "cau tra loi k dung");
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ForgotPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ForgotPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    // cap nhap mat khau moi
public void setPassword(String email, String pass) {
    Connection con = null; // Khai báo biến kết nối ở đây
    PreparedStatement ps = null;
    try {
        con = MyConnection.getConnection(); // Khởi tạo kết nối
        String sql = "update user set upassword = ? where uemail = ?";
        ps = con.prepareStatement(sql);
        ps.setString(1, pass);
        ps.setString(2, email);
        
        if (ps.executeUpdate() > 0) {
            JOptionPane.showMessageDialog(null, "doi mat khau thanh cong");
        } else {
            JOptionPane.showMessageDialog(null, "doi mat khau that bai");
        }
    } catch (SQLException ex) {
        Logger.getLogger(ForgotPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ForgotPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
}
