package AutoCenter.services;

import java.sql.ResultSet;
import AutoCenter.Home;
import AutoCenter.LoginUser;
import AutoCenter.models.User;
import AutoCenter.repository.DbConnection;


public class UserService {
	
	private RepositoryService repoService = null;
	public UserService()
	{
		repoService = new RepositoryService();
	}
	
	public LoginUser authenticate(String userId, String password)
	{
		LoginUser currentUser = null;
		//return administrator, manager, receptionist, mechanic or customer
		String query = "SELECT userId, firstName, lastName, role, serviceCenterId"
				+ " FROM Users "
				+ "Where username='"
				+ userId + "' AND password='"
				+ password + "'";
		DbConnection db = new DbConnection();
		
		try {
			ResultSet rs = db.executeQuery(query);
			if(rs != null)
			{
				while(rs.next())
				{
					currentUser.setId(rs.getInt("userId"));
					currentUser.setCenterId(rs.getInt("serviceCenterId"));
					currentUser.setFirstName(rs.getString("firstName").trim());
					currentUser.setLastName(rs.getString("lastName").trim());
					currentUser.setRole(rs.getString("role").trim());
				}
			}
		}catch(Throwable oops ) {
			//oops.printStackTrace();
			System.out.println("Not found In Managers.");
		}finally {
			db.close();
		}
		return currentUser;
	}
	
	public void logout()
	{
		if(Home.getUser() != null) Home.setUser(null);
		Home.exit();
		
		
	}
	public int getCenterId()
	{
		LoginUser user = Home.getUser();
		if(user == null) return 0;
		else return user.getCenterId();
	}
	
	
}
