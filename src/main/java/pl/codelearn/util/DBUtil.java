package pl.codelearn.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_PARAMS = "?useSSL=false&characterEncoding=utf8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "coderslab";

    //jdbc:mysql://localhost:3306/ + dbName + ?useSSL=false&characterEncoding=utf8
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL + "codeschool" + DB_PARAMS, DB_USER, DB_PASSWORD);
    }

}
