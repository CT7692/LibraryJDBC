package org.example.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static Connection connection;

    private ConnectionFactory(){}

    public static Connection getConnection() {

        if(connection == null) {
            String url = "jdbc:mysql://localhost:3306/library_app";
            String user = "root";
            String password = "jojo";

            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return connection;
    }
}
