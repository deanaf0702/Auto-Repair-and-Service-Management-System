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
		User user = authenticateEmployee(userId, password);
		if(user == null)
		{
			user = authenticateManager(userId, password);
		}
		if(user == null)
		{
			user = authenticateCustomer(userId, password);
		}
		return user;
	}
	public User authenticateEmployee(String userId, String password)
	{
		
		User user = null;
		//return administrator, manager, receptionist, mechanic or customer
		String query = "SELECT e.employeeId, e.firstName, e.lastName, e.roleType, e.centerId"
				+ " FROM Employees e, Roles r "
				+ "Where e.roleType = r.roleType AND username='"
				+ userId + "' AND PASSWORD='"
				+ password + "'";
		DbConnection db = new DbConnection();
		
		try {
			ResultSet rs = db.executeQuery(query);
			if(rs == null)
				user = authenticateCustomer(userId, password);
			else {
				while(rs.next()) {
					user = new User();
					user.setId(rs.getInt("employeeId"));
					user.setRole(rs.getString("roleType").trim());
					user.setFirstName(rs.getString("firstName").trim());
					user.setLastName(rs.getString("lastName").trim());
					user.setCenterId(rs.getInt("centerId"));
				}
			}
		}catch(Throwable oops ) {
			oops.printStackTrace();
		}finally {
			db.close();
		}
		return user;
	}
	public User authenticateManager(String userId, String password)
	{
		
		User user = null;
		//return administrator, manager, receptionist, mechanic or customer
		String query = "SELECT m.managerId, m.firstName, m.lastName, m.roleType, s.centerId"
				+ " FROM Managers m, ServiceCenters s "
				+ "Where m.managerId = s.managerId AND m.username='"
				+ userId + "' AND m.PASSWORD='"
				+ password + "'";
		DbConnection db = new DbConnection();
		
		try {
			ResultSet rs = db.executeQuery(query);
			if(rs == null)
				user = authenticateCustomer(userId, password);
			else {
				while(rs.next()) {
					user = new User();
					user.setId(rs.getInt("managerId"));
					user.setRole(rs.getString("roleType").trim());
					user.setFirstName(rs.getString("firstName").trim());
					user.setLastName(rs.getString("lastName").trim());
					user.setCenterId(rs.getInt("centerId"));
				}
			}
		}catch(Throwable oops ) {
			oops.printStackTrace();
		}finally {
			db.close();
		}
		return user;
	}
	public User authenticateCustomer(String firstName, String lastName)
	{
		
		User user = null;
		//return administrator, manager, receptionist, mechanic or customer
		String query = "SELECT customerId, firstName, lastName"
				+ " FROM Customer"
				+ "Where firstName='"
				+ firstName + "' AND LastName='"
				+ lastName + "'";
		DbConnection db = new DbConnection();
		try {
			ResultSet rs = db.executeQuery(query);
			while(rs.next())
			{
				user = new User();
				user.setId(rs.getInt("customerId"));
				user.setRole("Customer");
				user.setFirstName(rs.getString("firstName").trim());
				user.setLastName(rs.getString("lastName").trim());
				user.setCenterId(rs.getInt("centerId"));
			}
		}catch(Throwable oops){
			oops.printStackTrace();
		}finally {
			db.close();
		}
		
		return user;
		
	}
	public void logout()
	{
		if(Home.getUser() != null) Home.setUser(null);
		
		
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
		if(!(repoService.validateMinAndMaxWage(employee.salaryOrWage)))
			return false;
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
