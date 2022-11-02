package database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionManager {
	
	static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";
	
	public static Connection getConnection() {
		try {
            Class.forName("oracle.jdbc.OracleDriver");
            
            Properties prop = new Properties();
    		prop.load(new FileInputStream("database.properties"));
    		String user = prop.getProperty("username");
    		String pass = prop.getProperty("password");
    		
    		Connection conn = DriverManager.getConnection(jdbcURL, user, pass);
    		Statement stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery("SELECT COF_NAME, PRICE FROM COFFEES1");
    		while (rs.next()) {
    		    String s = rs.getString("COF_NAME");
    		    float n = rs.getFloat("PRICE");
    		    System.out.println(s + "   " + n);
    		}
			return conn;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
	}
	
	public static void close(Connection conn) {
        if(conn != null) {
            try { conn.close(); } catch(Throwable whatever) {}
        }
    }
}
