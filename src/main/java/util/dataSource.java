package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dataSource {
    private  Connection con;
    private  String url="jdbc:mysql://localhost:3306/batah";
    private  String login="root";
    private  String pwd= "";
    private static dataSource Instance;

    private dataSource() {
        try {
            con= DriverManager.getConnection(url,login,pwd);
            System.out.println("succes");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static dataSource getInstance(){
        if(Instance==null)
            Instance=new dataSource();
        return Instance;
    }

    public Connection getCon() {
        return con;
    }
}
