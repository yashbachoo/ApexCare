import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Connection conn;
    public Database() {
        try {
            // Load MySQL Driver
//            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/ApexCare?useSSL=false";
            String user = "root";
            String password = "";

            conn = DriverManager.getConnection(url, user, password);

            System.out.println("Connected to MySQL database successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() {
        return conn;
    }

    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}