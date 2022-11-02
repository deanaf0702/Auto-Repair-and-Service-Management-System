package AutoCenter.repository;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class DbConnection {

	static final String jdbcURL 
	= "jdbc:oracle:thin:@//localhost:1521/xe";
	static String user = "data_owner";
	static String password = "pa$$w0rd";
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet result = null;
	public DbConnection()
	{
		connect();
	}
	
	
	protected void connect()
	{
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			connection = DriverManager.getConnection(jdbcURL, user, password);
			statement = connection.createStatement();
		}catch(Throwable oops) {
			oops.printStackTrace();
		}
		
	}
	public Connection getConnection()
	{
		return connection;
	}
	public void setStatement(Statement stat)
	{
		statement = stat;
	}
	public ResultSet executeQuery(String query)
	{
		result = null;
		if(connection == null || statement == null) return result;
		try {
			result = statement.executeQuery(query);
			
		}catch(Throwable oops) 
		{
			oops.printStackTrace();
		}
		return result;
	}
	public boolean executeUpdate(String query)
	{
		int result = -1;
		if(connection == null && statement == null) return false;
		try {
			result = statement.executeUpdate(query);
			
		}catch(Throwable oops) 
		{
			oops.printStackTrace();
		}
		return result >= 0;
	}
	
	public void closeResult()
	{
		try {
			if(result != null)
				result.close();
		}catch(Throwable oops) 
		{
			oops.printStackTrace();
		}
	}
	public void closeStatement()
	{
		try {
			if(statement != null)
				statement.close();
		}catch(Throwable oops) 
		{
			oops.printStackTrace();
		}
	}
	public void close()
	{
		try {
			closeResult();
			closeStatement();
			if(connection != null)
				connection.close();
		}catch(Throwable oops) 
		{
			oops.printStackTrace();
		}
	}
}