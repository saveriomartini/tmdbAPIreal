package ch.hearc.ig.themoviedb.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbService {
    public static final String DB_URL = "jdbc:oracle:thin:@db.ig.he-arc.ch:1521:ens";
    public static final String DB_USER = "SAVERIO_MARTINI";
    public static final String DB_PASSWORD = "SAVERIO_MARTINI";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
