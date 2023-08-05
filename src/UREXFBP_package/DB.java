package UREXFBP_package;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
    public static final String DBMS = "mysql";
    public static final String HOST = "localhost";
    public static final int PORT = 3306;
    public static final String USER = "root";
    public static final String PASSWORD = "root";
    public Connection getConnection() throws SQLException {
        Connection conn = null;
        Properties connProp = new Properties();
        connProp.put("user", DB.USER);
        connProp.put("password", DB.PASSWORD);

        conn = DriverManager.getConnection("jdbc:" + DBMS + "://" +
                HOST +
                ":" + PORT + "/", connProp);

        System.out.println("Connected to database");
        return conn;
    }
}
