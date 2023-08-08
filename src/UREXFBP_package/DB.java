package UREXFBP_package;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
    public static final String DBMS = "mysql";
    public static final String HOST = "localhost";
    public static final int PORT = 3306;
    public static final String USER = "root";
    public static final String PASSWORD = "root";
    public static Connection conn = null;
    public Connection getConnection() throws SQLException {
        Properties connProp = new Properties();
        connProp.put("user", DB.USER);
        connProp.put("password", DB.PASSWORD);

        conn = DriverManager.getConnection("jdbc:" + DBMS + "://" +
                HOST +
                ":" + PORT + "/"  + "javabase", connProp);

        System.out.println("Connected to database");
        return conn;
    }

    public void createTable() throws SQLException{
        String statement = "CREATE TABLE `javabase`.`bankomattable` ( `Номер карты` LONGTEXT NOT NULL , " +
                "`Пароль` VARCHAR(16) NOT NULL , " +
                "`Срок действия:месяц` VARCHAR(2) NOT NULL , " +
                "`Срок действия:год` VARCHAR(2) NOT NULL , " +
                "`CVV` VARCHAR(3) NOT NULL ) ENGINE = InnoDB;";
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(statement);
    }

    public void InsertDataToTable(String CardNumber, String Password, String Month, String Year, String CVV) throws SQLException {
        String statement = "INSERT INTO `bankomattable`" +
                " (`Номер карты`, `Пароль`, `Срок действия:месяц`, `Срок действия:год`, `CVV`) VALUES ("+CardNumber+","+Password+","+Month+","+Year+","+CVV+")";
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(statement);
    }
}
