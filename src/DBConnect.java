import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Adrian Ispas on 07.06.2017.
 */
public class DBConnect {
     static Connection conn = null;

     static Connection createConnection(){

        final String HOST = "localhost";
        final String PORT = "3306";
        final String DATABASE_NAME = "ab4_systems_db";
        final String DATABASE_USER = "root";
        final String DATABASE_PASSWORD = "admin";

         try {
             Class.forName("com.mysql.jdbc.Driver");
         } catch (ClassNotFoundException e) {
             System.err.println(e);
         }
         try {
             conn = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/"+ DATABASE_NAME +"?verifyServerCertificate=false&useSSL=true",DATABASE_USER, DATABASE_PASSWORD);
         } catch (SQLException e) {
             System.err.println(e);
         }
         return conn;
    }

    static void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
}
