package database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionManager {

    static final String jdbcURL = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";

    public static Connection getConnection () {
        try {
            Class.forName( "oracle.jdbc.OracleDriver" );

            final Properties prop = new Properties();
            prop.load( new FileInputStream( "database.properties" ) );
            final String user = prop.getProperty( "username" );
            final String pass = prop.getProperty( "password" );

            final Connection conn = DriverManager.getConnection( jdbcURL, user, pass );
            return conn;
        }
        catch ( final Exception e ) {
            System.out.println( e.getMessage() );
            return null;
        }
    }

    public static void close ( final Connection conn ) {
        if ( conn != null ) {
            try {
                conn.close();
            }
            catch ( final Throwable whatever ) {
            }
        }
    }
}
