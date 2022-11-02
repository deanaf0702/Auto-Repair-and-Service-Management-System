package AutoCenter.models;

import java.util.Date;

import AutoCenter.services.UserService;

public class Employee {
	public UserService userService = null;
	public int employeeId = 0;
	public int centerId = 0;
	public String firstName = null;
	public String lastName = null;
	public String username = null;
	public String password = null;
	public String address = null;
	public String email = null;
	public String phone = null;
	public String role = null;
	public Date startDate = null;
	public Date endDate = null;
	public double salaryOrWage = 0.0;
	
	public void reset()
	{
		employeeId = 0;
		centerId = 0;
		password = null;
		firstName = null;
		lastName = null;
		username = null;
		address = null;
		email = null;
		phone = null;
		role = null;
		startDate = null;
		endDate = null;
		salaryOrWage = 0.0;
	}
}
