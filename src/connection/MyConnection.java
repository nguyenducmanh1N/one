package connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class MyConnection {
    public static final  String username = "root";
    public static final String password = "";
    public static final String url ="jdbc:mysql://localhost:3306/online_shopping";
    public static Connection con = null;
    
    public static  Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
    public static Connection main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,username,password);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ""+ ex,"",JOptionPane.WARNING_MESSAGE);
        }
        return con;
    }
}
