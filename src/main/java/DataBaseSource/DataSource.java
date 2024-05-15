package DataBaseSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DataSource {
    private Connection cnx;
    private static DataSource instance;

    // Online database connection details
    private String onlineUrl = "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11685928";
    private String onlineLogin = "sql11685928";
    private String onlinePwd = "byRB9bfy4H";

    // Local database connection details
    private String localUrl = "jdbc:mysql://localhost:3306/batahapp1";
    private String localLogin = "root";
    private String localPwd = "";

    private DataSource() {
        try {
            // Connect to the online database by default
            cnx = DriverManager.getConnection(onlineUrl, onlineLogin, onlinePwd);
            System.out.println("Connected to online database successfully");
        } catch (SQLException e) {
            System.err.println("Failed to connect to online database. Trying local database...");
            try {
                // If online connection fails, connect to the local database
                cnx = DriverManager.getConnection(localUrl, localLogin, localPwd);
                System.out.println("Connected to local database successfully");
            } catch (SQLException ex) {
                System.err.println("Failed to connect to both online and local databases");
                openNotConnectedPage();
                throw new RuntimeException("Failed to connect to both online and local databases", ex);
            }
        }
    }

    // Singleton pattern to ensure only one instance of DataSource is created
    public static DataSource getInstance() {
        if (instance == null)
            instance = new DataSource();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }

    private void openNotConnectedPage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/InterfaceUserAdmin/404.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to open notConnected.fxml: " + e.getMessage());
        }
    }
}