/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myapp.modules;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
        
/**
 *
 * @author Juri
 */
 // read "Database_readme" to see how to include this in your project
 
public class Database {
    
    private static Connection db;
    
    private static final String url = "jdbc:postgresql://12.34.56.78/mydatabase";
    private static final String user = "myuser";
    private static final String password = "mypassword";
    
    private Connection conn = null;

    private static final Logger _LOG = LoggerFactory.getLogger(Database.class);
    private SimpleDateFormat DATE_FORMAT_SECONDS = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private String NOW;
    
    
    public Database() {
        
        Date now = new Date();
        CreationDateTime creationDateTime = new CreationDateTime();
       /* NOW = DATE_FORMAT_SECONDS.format(now);
        creationDateTime.setValue(NOW);
        _LOG.debug(NOW);*/
        
        _LOG.info("Opening a connection to the DB");
        System.out.println("Opening a connection to the DB");
        
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver Registered!");
            conn = DriverManager.getConnection(url, user, password);
            
            if (conn != null) {
                System.out.println("Connected to DB");
                _LOG.info("Connected to DB");
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection Failed!");
            _LOG.error("Connection Failed!");
            System.exit(0);
        }
    }

 
    public void disconnect() {
        try {
            System.out.println("Disconnected");
            _LOG.info("Disconnected");
            conn.close();
            db.close();
        } catch (Exception e) {}
    }
    
    
    public void runSql(String sql){
       
       try {
           Statement stat = conn.createStatement();
           stat.executeQuery(sql);
           System.out.println("Records created successfully");
           _LOG.info("Records created successfully");
       }
       catch (SQLException e) {
           _LOG.error("Could not create records. Exiting");
           System.out.println("Could not create records. Exiting");
           System.err.println( e.getClass().getName()+": "+ e.getMessage() );
           disconnect();
           System.exit(0);
       }
   }
    
    public void updateSql(String sql){
       
       try {
           Statement stat = conn.createStatement();
           stat.executeUpdate(sql);
           System.out.println("Records updated successfully");
           _LOG.info("Records updated successfully");
       }
       catch (SQLException e) {
           _LOG.error("Could not update records. Exiting");
           System.out.println("Could not update records. Exiting");
           System.err.println( e.getClass().getName()+": "+ e.getMessage() );
           disconnect();
           System.exit(0);
       }
   }
    
 /*   public String result;
    
    public String Select(String sql) throws SQLException{
        
        Statement stat = conn.createStatement();
           try (ResultSet res = stat.executeQuery(sql)) {
               while ( res.next() ) {
                   result = res.getString("quantity");
               }
               res.close();
           }
       catch (SQLException e) {
           System.err.println( e.getClass().getName()+": "+ e.getMessage() );
           disconnect();
           System.exit(0);
       }
           return result;
   }
*/

    public List<String> arraySelect(String sql) throws SQLException{
        
        Statement stat = conn.createStatement();
        List<String> results = new ArrayList<>();
        int i = 1;
           try (ResultSet res = stat.executeQuery(sql)) {
               while ( res.next() ) {
               results.add(res.getString(i));
               i++;
               }
               res.close();
           }
       catch (SQLException e) {
           _LOG.error("Could not select the records");
           System.out.println("Could not select the records");
           System.err.println( e.getClass().getName()+": "+ e.getMessage() );
           disconnect();
           System.exit(0);
       }
           return results;
   }
    
    
}
