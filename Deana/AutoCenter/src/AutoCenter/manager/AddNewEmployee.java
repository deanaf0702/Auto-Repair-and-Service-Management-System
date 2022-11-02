package AutoCenter.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.models.Employee;
import AutoCenter.services.UserService;

public class AddNewEmployee implements Interface{

	private UserService userService = null;
	private Employee employee = null;
	
	public AddNewEmployee() 
	{
		userService = new UserService();
	}
	
	public void run() {
		int selection = 2;
		int inputLength = 11;
		display();
		do {
			displayDirection();
			employee = new Employee();
			System.out.println("Enter information in the order using the delimiter ';' as shown above: ");
			String input = ScanHelper.nextLine();
			String[] inputs = input.split(";");
			if(inputs.length == inputLength )
			{
				try {
					employee.employeeId = Integer.parseInt(inputs[0]);
				}catch(Exception e) {
					System.out.println(e);
					break;
				}
				
				employee.firstName = inputs[1];
				employee.lastName = inputs[2];
				employee.username = inputs[3];
				employee.password = inputs[4];
				employee.address = inputs[5];
				employee.email = inputs[6];
				employee.phone = inputs[7];
				employee.role = inputs[8];
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
				//Date stringDate = formatter.parse(input);
				String startDate = inputs[9];
				try {
					employee.startDate = formatter.parse(startDate);
				} catch (ParseException e) {
					
					System.out.println(e);
					break;
					
				}
				employee.salaryOrWage = Double.parseDouble(inputs[10]);
				display();
				System.out.println("Enter choice(1-2)");
				selection = ScanHelper.nextInt();
			}else {
				System.out.println("Went wrong and try again!");
				selection = 0;
			}
				
			
		}while(!(selection >= 1 && selection <= 2));
		navigate(selection);
	}

	@Override
	public void display() {
		System.out.println("## Add New Employees Menu ##");
		System.out.println("1 Add");
		System.out.println("2 Go Back");
		System.out.println("##########");
	}

	public void displayDirection()
	{
		System.out.println("## Enter the information in the order as shown below with the delimiter ‘;’");
		System.out.println("## Ex:423186759;Ellie;Clark;eclark;clark;3125 Avent Ferry Road, Raleigh, NC 27605;eclark@gmail.com;9892180921;mechanic;11-JUN-2022;30.99 ##");
		System.out.println("A EmployeeId:");
		System.out.println("B FirstName:");
		System.out.println("C LastName:");
		System.out.println("D UserName:");
		System.out.println("E Password:");
		System.out.println("F Address:");
		System.out.println("G EmilAddress:");
		System.out.println("H Phone Number:");
		System.out.println("I Role:");
		System.out.println("J Start Date:");
		System.out.println("K Compensation($):");
	}
	@Override
	public void navigate(int selection) {
		boolean result = false;
		switch(selection)
		{
			case 1: 
				result = userService.addEmployee(employee);	
				if(result) {
					System.out.println("Added Successfully");
					goBack();
				}
				else run();
				
				break;
			case 2: goBack();
			break;
			default: run();
			break;
		}
		
	}

	@Override
	public void goBack() {
		new SetupStore().run();
		
	}

	

}
