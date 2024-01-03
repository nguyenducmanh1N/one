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

public class SupplierDao {

    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    // tao bang supplier 
    public int getMaxRow() {
        int row = 0;
        try {
            con = MyConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select max(sid) from supplier");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
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
            ps = con.prepareStatement("SELECT * FROM supplier WHERE semail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
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
            ps = con.prepareStatement("select * from supplier where sphone = ? ");
            ps.setString(1, phone);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;

            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

//     kiem tra ten da ton tai hay chua 
    public boolean isUsernameExist(String name) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement("select * from supplier where sname = ? ");
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;

            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
//     insert data into supplier table

    public void insert(int id, String username, String email, String pass, String phone, String address) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = MyConnection.getConnection();
            String sql = "INSERT INTO supplier VALUES (?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);  // Thêm giá trị cho cột sid
            ps.setString(2, username);
            ps.setString(3, email);
            ps.setString(4, pass);
            ps.setString(5, phone);
            ps.setString(6, address);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Thêm NCC thành công");
            }

        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // update supplier into data
    public void update(int id, String username, String email, String pass, String phone,
            String address) {
        Connection con = null; // Khởi tạo biến kết nối
        PreparedStatement ps = null;

        try {
            con = MyConnection.getConnection();
            String sql = "update supplier set sname = ?,semail = ?,spassword =?,sphone = ?  ,saddress =?  where sid = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, pass);
            ps.setString(4, phone);

            ps.setString(5, address);
            ps.setInt(6, id);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, " thanh cong");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    // tao data supplier 
    public void getSupplierValue(JTable table, String search) {
        String sql = "SELECT * FROM supplier WHERE CONCAT(sid, sname, semail) LIKE ? ORDER BY sid DESC";
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
                row = new Object[6];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);

                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // xoa tai khoan
    public void delete(int id) {
        int x = JOptionPane.showConfirmDialog(null, "Bạn muốn xóa tài khoản này?", "Delete Account", JOptionPane.OK_CANCEL_OPTION, 0);
        if (x == JOptionPane.OK_OPTION) {
            Connection con = null;
            PreparedStatement ps = null;

            try {
                con = MyConnection.getConnection();
                // Kiểm tra kết nối trước khi thực hiện câu lệnh SQL
                if (con != null && !con.isClosed()) {
                    ps = con.prepareStatement("DELETE FROM supplier  WHERE sid = ?");
                    ps.setInt(1, id);

                    if (ps.executeUpdate() > 0) {
                        JOptionPane.showMessageDialog(null, "Đã xóa tài khoản.");
                    }
                } else {
                    // Xử lý khi kết nối đã bị đóng hoặc không tồn tại
                    JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
// tao sup id

    public int getSupplierId(String email) {
        int id = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement("SELECT sid FROM supplier WHERE semail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getInt(1);

            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return id;
    }
    // tao sup name
    public String getSupplierName(String email) {
        String supplierName = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement("SELECT sname FROM supplier WHERE semail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                supplierName = rs.getString(1);

            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return supplierName;
    }

    // cap nhap tt nguoi dung
    public String[] getSupplierValue(int id) {
        String[] value = new String[6];
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = MyConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM supplier WHERE sid = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                value[0] = rs.getString(1);
                value[1] = rs.getString(2);
                value[2] = rs.getString(3);
                value[3] = rs.getString(4);
                value[4] = rs.getString(5);
                value[5] = rs.getString(6);

            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return value;
    }

    public int countSuppliers() {
        int total = 0;
        try {
            con = MyConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select count(*) as 'total'from supplier");
            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;

    }
     public String[] getSuppliers() {
        String[] suppliers = new String[countSuppliers()];
        try {
            con = MyConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select * from supplier");
            int i = 0;
            while (rs.next()) {
                suppliers[i] = rs.getString(2);
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return suppliers;

    }

}
