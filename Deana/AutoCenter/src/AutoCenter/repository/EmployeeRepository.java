package AutoCenter.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import AutoCenter.Home;
import AutoCenter.models.Employee;
import AutoCenter.services.Helper;
import AutoCenter.services.UserService;

public class EmployeeRepository {
	
	protected UserService userService = null;
	static final String table = "Employees";
	static final String pkColumn = "employeeId";
	
	public EmployeeRepository() {
		userService = new UserService();
	}
	public boolean add(Employee employee)
	{
		boolean valid = false;
				//employee.employeeId = getPrimaryKey();
				employee.centerId = userService.getCenterId();
				String employeeQuery = addEmployeeQuery(employee);
				DbConnection db = new DbConnection();
				
				try {
					
					boolean result1 = db.executeUpdate(employeeQuery);
					
					if(!result1) 
					{
						return valid;
					}
					String childQuery = null;
					if(employee.role.equals("manager"))
					{
						childQuery = addManagerQuery(employee.employeeId, employee.salaryOrWage);
						
					}else if(employee.role.equals("receptionist"))
					{
						childQuery = addReceptionistQuery(employee.employeeId, employee.salaryOrWage);
					}else if(employee.role.equals("mechanic")) {
						childQuery = addMechanicQuery(employee.employeeId, employee.salaryOrWage);
					}
					if(childQuery.isEmpty())
					{
						return valid;
					}
					
					boolean result2 = db.executeUpdate(childQuery);
					if(result2) valid = true;
					
		}catch(Exception e) {
			
		}finally{
			db.close();
		};
		return valid;
		
	}
	public boolean update(Employee employee)
	{
		
		String employeeQuery = updateEmployeeQuery(employee);
		DbConnection db = new DbConnection();
		boolean result2 = false;
		try{
			boolean result1 = db.executeUpdate(employeeQuery);
			
			if(!result1) 
			{
				System.out.println("Failed to add an Employee.");
				return false;
			}
			String childQuery = null;
			if(employee.role.equals("manager"))
			{
				childQuery = updateManagerQuery(employee.employeeId, employee.salaryOrWage);
				
			}else if(employee.role.equals("receptionist"))
			{
				childQuery = updateReceptionistQuery(employee.employeeId, employee.salaryOrWage);
			}else if(employee.role.equals("mechanic")) {
				childQuery = updateMechanicQuery(employee.employeeId, employee.salaryOrWage);
			}
			if(childQuery.isEmpty())
			{
				System.out.println("Failed to add an Employee.");
				return false;
			}
			 result2 = db.executeUpdate(childQuery);
			
			
		}catch(Exception e){
			
		}finally {
			db.close();
		}
		return result2;
	}
	public Employee details(int employeeId)
	{
		
		if(employeeId <= 0) return null;
		Employee e = new Employee();
		String query = selectEmployeeQuery(employeeId);
		DbConnection db = new DbConnection();
		try {
			ResultSet rs = db.executeQuery(query);
			e.employeeId = rs.getInt("employeeId");
			e.centerId = rs.getInt("centerId");
			e.username = rs.getString("username").trim();
			e.firstName = rs.getString("firstName").trim();
			e.lastName = rs.getString("lastName").trim();
			e.address = rs.getString("address").trim();
			e.email = rs.getString("email").trim();
			e.phone = rs.getString("phone").trim();
			e.role = rs.getString("role").trim();
			e.startDate = rs.getDate("startDate");
			e.endDate = rs.getDate("endDate");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			db.close();
		}
		
		return e;
	}
	public boolean delete(Employee model)
	{
		Employee employee = details(model.employeeId);
		boolean result = false;
		if(employee == null) 
		{
			System.out.println("Failed deleting");
			return false;
		}	
		DbConnection db = new DbConnection();
		try {
			String query = deleteEmployeeQuery(model.employeeId);
			result = db.executeUpdate(query);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			db.close();
		}
		
		return result;
	}
	public int getPrimaryKey() {
		String query = "select "+ pkColumn +" from (select e.*, max(" + pkColumn + ")"
				+ "over () as max_" + pkColumn + " from " + table + " e) "
				+ "where " + pkColumn + " = max_employeeId";
		
		DbConnection db = new DbConnection();
		
		
		
		int lastPK = 0; 
		try {
				ResultSet rs = db.executeQuery(query);
				while(rs.next())
				{
					lastPK = rs.getInt("employeeId");
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			db.close();
		}
		return lastPK+1;
	}
	
	public String deleteEmployeeQuery(int employeeId)
	{
		return "select * from Employees where employeeId="
				+ employeeId;	
	}
	public String selectEmployeeQuery(int employeeId)
	{
		return "select * from Employees where employeeId="
				+ employeeId;
	}
	public String addManagerQuery(int employeeId, double salary)
	{
		return "insert into Managers (employeeId, salary) values("
				+ employeeId + ", "
				+ salary
				+ ")";
	}
	public String addReceptionistQuery(int employeeId, double salary)
	{
		return "insert into Receptionists (employeeId, salary) values("
				+ employeeId + ", "
				+ salary
				+ ")";
	}
	public String addMechanicQuery(int employeeId, double wage)
	{
		return "insert into Mechanics (employeeId, wage) values("
				+ employeeId + ", "
				+ wage
				+ ")";
	}
	public String addEmployeeQuery(Employee employee)
	{
		
		String query = "insert into Employees (employeeId, centerId, roleType, "
				+ "userName, password, firstName, lastName, address, "
				+ "email, phone, startDate, endDate) values(" 
				+ employee.employeeId + ",  "
				+ employee.centerId + ", '"
				+ employee.role + "', '"
				+ employee.username + "', '"
				+ employee.lastName + "', '"
				+ employee.firstName + "', '"
				+ employee.lastName + "', '"
				+ employee.address + "', '"
				+ employee.email + "', '"
				+ employee.phone + "', "
				+ (employee.startDate != null
				? ("'" + (Helper.dateConvertToString(employee.startDate)) + "'") 
					: null) + ", "
				+ (employee.endDate != null
				? ("'" + Helper.dateConvertToString(employee.endDate) + "'") : null)
				+ ")";
		return query;
	}
	public String updateEmployeeQuery(Employee employee)
	{
		
		String query = "update  Employees "
				+ "set centerid=" + employee.centerId + ", "
				+ "set roleType=" + employee.role + ", "
				+ "set username=" + employee.username + ", "
				+ "set password=" + employee.lastName + ", "
				+ "set firstName=" + employee.firstName + ", "
				+ "set lastName=" + employee.lastName + ", "
				+ "set address=" + employee.address + ", "
				+ "set email=" + employee.email + ", "
				+ "set phone=" + employee.phone + ", "
				+ "set startDate=" + employee.startDate + ", "
				+ "set endDate=" + employee.endDate
				+ ") "
				+ "Where employeeId = " + employee.employeeId;
		return query;
	}
	public String updateManagerQuery(int employeeId, double salary)
	{
		return  "update Managers "
				+ "set salary=" + salary
				+ " where emaployeeId=" + employeeId;
	}
	public String updateReceptionistQuery(int employeeId, double salary)
	{
		return "update Receptionists"
				+ "set salary=" + salary
				+ " where emaployeeId=" + employeeId;
	}
	public String updateMechanicQuery(int employeeId, double wage)
	{
		return "update Mechanics"
				+ "set wage=" + wage
				+ " where emaployeeId=" + employeeId;
	}
}
