package dao;

import admin.AdminDashboard;
import connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIDefaults;
import supplier.SupplierDashboard;
import user.UserDashboard;

public class Statictics {

    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    private int total(String tableName) {
        int total = 0;
        try {
            con = MyConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select count(*) as 'total'from " + tableName + "");
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statictics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    private double totalSales() {
        double total = 0.0;
        try {
            con = MyConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select sum(total) as 'total'from  purchase ");
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statictics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    private double todaySales() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = df.format(date);
        double total = 0.0;
        try {
            con = MyConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select sum(total) as 'total'from  purchase where p_date = '"+today+"'");
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statictics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    //admin dashboard 
    public void admin() {
        AdminDashboard.jCat.setText(String.valueOf(total("category")));
        AdminDashboard.jPro.setText(String.valueOf(total("product")));
        AdminDashboard.jUser.setText(String.valueOf(total("user")));
        AdminDashboard.jSup.setText(String.valueOf(total("supplier")));
        AdminDashboard.jSales.setText(String.valueOf(totalSales()));
        AdminDashboard.jTotal.setText(String.valueOf(todaySales()));

    }
    
     private double totalPurchase(int uid) {
        double total = 0.0;
        try {
            con = MyConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select sum(total) as 'total'from  purchase where uid = "+uid +"");
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statictics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
     
     
    
    public void user(int id) {
        
        UserDashboard.jCat.setText(String.valueOf(total("category")));
        UserDashboard.jPro.setText(String.valueOf(total("product")));
        UserDashboard.jPur.setText(String.valueOf(totalPurchase(id)));
        
        
    }
    
    private int totalDeliveries(String name) {
        int total = 0;
        try {
            con = MyConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select count(*) as 'total'from purchase where supplier = '" + name + "'and status = 'Received'");
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statictics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    
    public  void supplier(String name){
        SupplierDashboard.jDel.setText(String.valueOf(totalDeliveries(name)));
        
        
    }
}
