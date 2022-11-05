package AutoCenter.services;

import java.sql.ResultSet;
import AutoCenter.Home;
import AutoCenter.User;
import AutoCenter.models.Employee;
import AutoCenter.repository.DbConnection;
import AutoCenter.repository.EmployeeRepository;

public class UserService {
	
	private RepositoryService repoService = null;
	public UserService()
	{
		repoService = new RepositoryService();
	}
	
	public User authenticate(String userId, String password)
	{
		
		User user = null;
		//return administrator, manager, receptionist, mechanic or customer
		String query = "SELECT userId, firstName, lastName, role"
				+ " FROM Users "
				+ "Where username='"
				+ userId + "' AND password='"
				+ password + "'";
		DbConnection db = new DbConnection();
		
		try {
			ResultSet rs = db.executeQuery(query);
			if(rs != null)
			{
				while(rs.next()) {
					user = new User();
					user.setId(rs.getInt("userId"));
					user.setRole(rs.getString("role").trim());
					user.setFirstName(rs.getString("firstName").trim());
					user.setLastName(rs.getString("lastName").trim());
				}
				String tableName = "";
				if(user.getRole().equals("Customer"))
					tableName = "Customers";
				else if(user.getRole().equals("Manager"))
					tableName = "Managers";
				else if(user.getRole().equals("Mechanic"))
					tableName = "Mechanics";
				else if(user.getRole().equals("Receptionist"))
					tableName = "Receptionists";
				else user = null;
				
				String query2 = "select serviceCenterId from " + tableName 
						+ " where userId = " + user.getId();
				
				ResultSet rs2 = db.executeQuery(query2);
				rs2.next();
				int centerId = rs2.getInt("serviceCenterId");
				user.setCenterId(centerId);
			
			}
		}catch(Throwable oops ) {
			//oops.printStackTrace();
			System.out.println("Not found In Managers.");
		}finally {
			db.close();
		}
		return user;
	}
	
	public void logout()
	{
		if(Home.getUser() != null) Home.setUser(null);
		Home.exit();
		
		
	}
	public int getCenterId()
	{
		User user = Home.getUser();
		if(user == null) return 0;
		else return user.getCenterId();
	}
	//Repository
	public boolean addEmployee(Employee employee)
	{
		employee.centerId = getCenterId();
		if(employee.role.equals("mechanic")) {
			if(!(repoService.validateMinAndMaxWage(employee.salaryOrWage))) {
				System.out.println("This isn't in the range of minimum or maximum wage.");
				return false;
			}
				
		}
		
		EmployeeRepository er = new EmployeeRepository();
		boolean result= false;
		try {
			result = er.add(employee);
		}catch(Exception e) {
			System.out.println("Went Wrong!");
			
		}
		return result;
	}
	//Permission
	public boolean permissionForManager(User user)
	{
		DbConnection db = new DbConnection();
		boolean valid = false;
		String query = "Select * from Managers where user.id = " + user.getId();
		try {
			ResultSet rs = db.executeQuery(query);
			if(rs.last())
				return valid = true;
			
		}catch(Exception e) {
			
		}finally {
			db.close();
		};
		return valid;
	}
	
}
