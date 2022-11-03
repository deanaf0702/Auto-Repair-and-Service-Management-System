package AutoCenter.repository;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DbConnection {

    private Connection connection = null;
    private Statement  statement  = null;
    private ResultSet  result     = null;

    public DbConnection () {
        connect();
    }

    protected void connect () {
        try {
            Class.forName( "oracle.jdbc.OracleDriver" );

            final Properties prop = new Properties();
            prop.load( new FileInputStream( "database.properties" ) );
            final String user = prop.getProperty( "username" );
            final String pass = prop.getProperty( "password" );
            final String jdbcUrl = prop.getProperty( "jdbcUrl" );

            connection = DriverManager.getConnection( jdbcUrl, user, pass );
            statement = connection.createStatement();
        }
        catch ( final Throwable oops ) {
            oops.printStackTrace();
        }

    }

    public Connection getConnection () {
        return connection;
    }

    public void setStatement ( final Statement stat ) {
        statement = stat;
    }

    public ResultSet executeQuery ( final String query ) {
        result = null;
        if ( connection == null || statement == null ) {
            return result;
        }
        try {
            result = statement.executeQuery( query );

        }
        catch ( final Throwable oops ) {
            oops.printStackTrace();
        }
        return result;
    }

    public boolean executeUpdate ( final String query ) {
        int result = -1;
        if ( connection == null && statement == null ) {
            return false;
        }
        try {
            result = statement.executeUpdate( query );

        }
        catch ( final Throwable oops ) {
            oops.printStackTrace();
        }
        return result >= 0;
    }

    public void closeResult () {
        try {
            if ( result != null ) {
                result.close();
            }
        }
        catch ( final Throwable oops ) {
            oops.printStackTrace();
        }
    }

    public void closeStatement () {
        try {
            if ( statement != null ) {
                statement.close();
            }
        }
        catch ( final Throwable oops ) {
            oops.printStackTrace();
        }
    }

    public void close () {
        try {
            closeResult();
            closeStatement();
            if ( connection != null ) {
                connection.close();
            }
        }
        catch ( final Throwable oops ) {
            oops.printStackTrace();
        }
    }
}
