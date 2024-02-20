package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    private  Connection cnx;
    private  String url="jdbc:mysql://localhost:3306/batah";
    private  String login="root";
    private String pwd="";
    private static DataSource instance;

    private DataSource() { //connexion sera etabli
        try {
            cnx= DriverManager.getConnection(url,login,pwd);
            System.out.println("success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DataSource getInstance(){
        if(instance==null)
            instance=new DataSource();
        return instance;
    }//lezimha tkoun static

    public Connection getCnx() {
        return cnx;
    }
}
