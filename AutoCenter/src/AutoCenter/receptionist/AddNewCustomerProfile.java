package AutoCenter.receptionist;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.models.Employee;
import AutoCenter.receptionist.Receptionist;
import AutoCenter.repository.DbConnection;
import AutoCenter.services.RepositoryService;
import AutoCenter.services.UserService;

public class AddNewCustomerProfile implements Interface
{
	private RepositoryService repoService = null;
	int inputLength = 11;
	String customerQuery = null;
	String custVehicleQuery = null;
	
	public AddNewCustomerProfile()
	{
		repoService = new RepositoryService();
	}
	@Override
	public void run() {
		int selection = 2;
		
		do {
			display();
			reset();
			String input = ScanHelper.nextLine();
			String[] inputs = input.split(";");
			
			if(inputs.length == inputLength )
			{
				displayMenu();
				System.out.println("Enter choices(1-2)");
				selection = ScanHelper.nextInt();
				customerQuery = getCustomeQuery(inputs);
				custVehicleQuery = getCustomeQuery(inputs);	
			}else {
				System.out.println("Went wrong and try again!");
			}
		}while(!(selection >= 1 && selection <= 2));
		navigate(selection);
	}

	@Override
	public void display() {
		System.out.println("A Customer ID:");
		System.out.println("B FirstName:");
		System.out.println("C LastName:");
		System.out.println("D Address:");
		System.out.println("E EmailAddress:");
		System.out.println("F Phone Number:");
		System.out.println("G Username:");
		System.out.println("H Vin Number:");
		System.out.println("I Car manufacturer:");
		System.out.println("J Current mileage:");
		System.out.println("K Year:");
		System.out.println("## Enter the information in the order as shown above with the delimiter ‘;’");
		System.out.println("## Ex:10053;Billy;Batson;1234 Address ST Raleigh NC 12345;"
				+ "dlfranks@ncsu.edu;1234567890;bbatson;5TR3K914;Nissan;111000;2015 ##");
		
	}

	public void displayMenu()
	{
		System.out.println("## Add New Customer Profile Menu ##");
		System.out.println("1 Save");
		System.out.println("2 Go Back");
		System.out.println("##########");
	}
	
	@Override
	public void navigate(int selection) {
		switch(selection)
		{
			case 1: if(save()) System.out.println("Added Successfully");
					else System.out.println("Went wrong and try again!");
					goBack();
				break;
			default: goBack();
				break;
		}
	}

	@Override
	public void goBack() {
		new Receptionist().run();
	}
	public void reset() {
		customerQuery = null;
		custVehicleQuery = null;
	}
	public boolean save()
	{
		boolean valid = false;
		try {
			DbConnection db = new DbConnection();
			try {
				boolean result = db.executeUpdate(customerQuery);
				if(result) {
					boolean result2 = db.executeUpdate(custVehicleQuery);
					if(result2) valid = true;
					
				}
			}finally {
				db.close();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}
		return valid;
	}
	public String getCustomeQuery(String[] inputs)
	{
		boolean valid = false;
		int customerId = Integer.parseInt(inputs[0].trim());
		String firstName = inputs[1].trim();
		String lastName = inputs[2].trim();
		String address = inputs[3].trim();
		String email = inputs[4].trim();
		String phone = inputs[5].trim();
		String username = inputs[6].trim().toLowerCase();
		String password = inputs[2].trim().toLowerCase();
		String vinNumber = inputs[7].trim();
		String carModel = inputs[8].trim();
		int mileage = Integer.parseInt(inputs[9]);
		int year = Integer.parseInt(inputs[10].trim());
		String status = "1";
		String active = "1";
		
		int centerId = repoService.getCenterId();
		
		String customerQuery = "insert into Customers (customerId, centerId, firstName, lastName, "
				+ "username, password, phone, address, status, active) "
				+ "values("
				+ customerId + ", "
				+ centerId +", "
				+ "'" + firstName + "', "
				+ "'"+ lastName + "', "
				+ "'"+ username + "', "
				+ "'"+ password + "', "
				+ "'"+ phone + "', "
				+ "'" + address + "', "
				+ "'" + status +"', "
				+ "'" + active +"')";
		return customerQuery;
	}
	public String getCustVehicleQuery(String[] inputs) {
		int customerId = Integer.parseInt(inputs[0].trim());
		String vinNumber = inputs[7].trim();
		String carModel = inputs[8].trim();
		int mileage = Integer.parseInt(inputs[9]);
		int year = Integer.parseInt(inputs[10].trim());
		
		String custVehicleQuery = "insert into CustomerVehicles (vin, customerId, model, mileage, year) "
				+ "values("
				+ "'" + vinNumber + "', "
				+  customerId + ", "
				+ "'" + carModel + "', "
				+ mileage + ", "
				+ year + ")";
		
		return custVehicleQuery;
	}
}
