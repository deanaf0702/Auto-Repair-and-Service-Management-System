package AutoCenter.receptionist;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.models.Employee;
import AutoCenter.receptionist.Receptionist;
import AutoCenter.services.RepositoryService;
import AutoCenter.services.UserService;

public class AddNewCustomerProfile implements Interface{

	private UserService userService = null;
	private RepositoryService repoService = null;
	
	public AddNewCustomerProfile()
	{
		userService = new UserService();
		repoService = new RepositoryService();
	}
	@Override
	public void run() {
		int selection = 1;
		int inputLength = 9;
		display();
		do {
			displayDirection();
			String input = ScanHelper.nextLine();
			String[] inputs = input.split(";");
			if(inputs.length != inputLength )
			{
				System.out.println("Enter choice(1-2)");
				selection = ScanHelper.nextInt();
				boolean result = repoService.addCustomer(inputs);	
				if(result) {
					System.out.println("Added Successfully");
				}
				else {
					System.out.println("Went wrong and try again!");
					selection = 0;
				}
				
			}else 
			{
				System.out.println("Went wrong and try again!");
				selection = 0;
			}
			
			
		}while(!(selection <= 1 && selection >= 1));
		navigate(selection);
	}

	@Override
	public void display() {
		System.out.println("## Add New Customer Profile Menu ##");
		System.out.println("1 Go Back");
		System.out.println("##########");
		
	}

	public void displayDirection()
	{
		System.out.println("A FirstName:");
		System.out.println("B LastName:");
		System.out.println("C Address:");
		System.out.println("D EmilAddress:");
		System.out.println("E Phone Number:");
		System.out.println("F Username:");
		System.out.println("G Vin Number:");
		System.out.println("H Car manufacturer:");
		System.out.println("I Current mileage:");
		System.out.println("J Year:");
		System.out.println("## Enter the information in the order as shown above with the delimiter ‘;’");
		System.out.println("## Ex:DemoCustomer;Customer1-1;1234 Address ST Raleigh NC 12345;"
				+ "dlfranks@ncsu.edu;1234567890;demoCustomer1-1;ABC-1234;Honda;12000;2022 ##");
	}
	
	@Override
	public void navigate(int selection) {
		switch(selection)
		{
			case 1: 
				goBack();
				break;
			
		}
		
	}

	@Override
	public void goBack() {
		new Receptionist().run();
		
	}

}
