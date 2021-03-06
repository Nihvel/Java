package modules;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
        
/**
 *
 * @author Juri
 */
   
public class DatabasePgPass {
    
    private final Logger LOGGER = LogManager.getLogger(Database.class);
    private Connection conn = null;
    
    public Database(String host, String port, String db, String user) throws PgPassException {
        
        String driver = "org.postgresql.Driver";
        
        LOGGER.info("Opening a connection to "+db);
        System.out.println("Opening a connection to "+db);
        
        String passw = PgPass.get(host, port, db, user);
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;
            
        try {
            Class.forName(driver);
            System.out.println("PostgreSQL JDBC Driver Registered!");
            conn = DriverManager.getConnection(url, user, passw);
            
            if (conn != null) {
                System.out.println("Connected to "+db);
                LOGGER.info("Connected to "+db);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection Failed!");
            LOGGER.fatal("Connection Failed!");
            System.exit(0);
        }
    }
    
    public void disconnect() {
        try {
            conn.close();
            //db.close();
            System.out.println("Disconnected");
            LOGGER.info("Disconnected");
        } catch (SQLException e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
    }
    
    // Used for Prepared statements (Select upsert...)
    public void runSql(String sql){
       try {
           Statement stat = conn.createStatement();
           stat.executeQuery(sql);
           System.out.println("Record created.");
           LOGGER.info("Record created.");
       }
       catch (SQLException e) {
           LOGGER.fatal("Could not create record. Exiting");
           System.out.println("Could not create record. Exiting");
           System.err.println( e.getClass().getName()+": "+ e.getMessage() );
           disconnect();
           System.exit(0);
       }
   }
    
    // Used for INSERT
    public void executeSql(String sql){
       try {
           Statement stat = conn.createStatement();
           stat.execute(sql);
           System.out.println("Record created.");
           LOGGER.info("Record created.");
       }
       catch (SQLException e) {
           LOGGER.fatal("Could not create record. Exiting");
           System.out.println("Could not create record. Exiting");
           System.err.println( e.getClass().getName()+": "+ e.getMessage() );
           disconnect();
           System.exit(0);
       }
   }
    
    // Used for DELETE
    public void deleteSql(String sql){
       try {
           Statement stat = conn.createStatement();
           stat.executeUpdate(sql);
           System.out.println("Record deleted.");
           LOGGER.info("Record deleted.");
       }
       catch (SQLException e) {
           LOGGER.fatal("Could not delete record. Exiting");
           System.out.println("Could not delete record. Exiting");
           System.err.println( e.getClass().getName()+": "+ e.getMessage() );
           disconnect();
           System.exit(0);
       }
   }
    
    // Used for Update
    public void updateSql(String sql){
       try {
           Statement stat = conn.createStatement();
           stat.executeUpdate(sql);
           System.out.println("Record updated.");
           LOGGER.info("Record updated.");
       }
       catch (SQLException e) {
           LOGGER.fatal("Could not update record. Exiting");
           System.out.println("Could not update record. Exiting");
           System.err.println( e.getClass().getName()+": "+ e.getMessage() );
           disconnect();
           System.exit(0);
       }
   }
    
    // Used for selecting one single Integer value (select count (*) as name from...)
    public Integer SelectCount(String sql) throws SQLException{
        Integer result = 0;
        Statement stat = conn.createStatement();
           try (ResultSet res = stat.executeQuery(sql)) {
               while ( res.next() ) {
                   result = res.getInt(1);
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
    
    // Used for selecting multiple values
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
           LOGGER.fatal("Could not select the record");
           System.out.println("Could not select the record");
           System.err.println( e.getClass().getName()+": "+ e.getMessage() );
           disconnect();
           System.exit(0);
       }
           return results;
   }
    
    
}
