/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package normalisasi;

import java.sql.*;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        // System.out.println(new App().getGreeting());
        

        try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3307/db_kasus", "root", "password")) {
            // create a Statement
            try (Statement stmt = conn.createStatement()) {
                //execute query
                
                try (ResultSet rs = stmt.executeQuery("SELECT * from movies_rented where membership_id = 1")) {
                    //position result to first
                    // rs.first();
                    System.out.println("Janet Jones Rents:"); 

                    int  i = 1;
                    while(rs.next()) {
                        System.out.println(i + ". " +rs.getString("movies_rented")); 
                        i = i + 1;
                    }
                }
            }
        } catch(Exception $e) {
            System.out.println($e);
        }
        
    }
}
