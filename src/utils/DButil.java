package utils;


import java.sql.*;

/**
 * Created by alwayslike on 2017/9/15.
 */
public class DButil {

    public static Connection getConnection(String bridge) {
        Connection connection = null;
        String dbUrl = "jdbc:mysql://localhost:3306/" + bridge;
        String dbUser = "root";
        String dbPwd = "1231Yyx";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static ResultSet getResultSet(String bridge, String command) {
        Connection connection = null;
        String dbUrl = "jdbc:mysql://localhost:3306/" + bridge;
        String dbUser = "root";
        String dbPwd = "1231Yyx";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
            Statement statement = connection.createStatement();

            ResultSet rs2 = statement.executeQuery(command);
            return rs2;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }
}
