package AutoCenter.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;
import AutoCenter.models.Employee;
import AutoCenter.services.UserService;

public class AddNewEmployee implements UserFlowFunctionality{

	private UserService userService = null;
	private Employee employee = null;
	private static final String DIRECTION_SEPARATOR = "#############################";
    private static final String MENU_SEPARATOR = "#######################################";
    private static final int EXPECTED_INPUT_LENGTH = 11;
    private static final int MIN_SELECTION = 1;
    private static final int MAX_SELECTION = 2;

	public AddNewEmployee()
	{
		userService = new UserService();
	}

	public void run() {
		int selection = 2;
		int inputLength = 11;

		do {
			display();
			employee = new Employee();
			String input = ScanHelper.nextLine();
			String[] inputs = input.split(";");
			if(inputs.length == EXPECTED_INPUT_LENGTH  )
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

				displayMenu();
				System.out.println("Enter choice (1-2) from the given options displayed above:");
				selection = ScanHelper.nextInt();
			}else {
				System.out.println(
                        "Something went wrong. Please try again and make sure you provide all Eleven inputs for an employee."
                                + " Take a look at the usage detailed above if you need help.");
				System.out.println("Enter choice (1-2) from the given options displayed above:");

			}
		}while(!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));
		navigate(selection);
	}

	@Override
	public void display() {
		System.out.println("#############################");
		System.out.println("######      Usage      ######");
		System.out.println(DIRECTION_SEPARATOR);
		System.out.println("# A. Employee ID             #");
		System.out.println("# B. First Name              #");
		System.out.println("# C. Last Name               #");
		System.out.println("# D. UserName                #");
		System.out.println("# E. Password                #");
		System.out.println("# F. Address                 #");
		System.out.println("# G. EmilAddress             #");
		System.out.println("# H. Phone Number            #");
		System.out.println("# I. Role                    #");
		System.out.println("# J. Start Date              #");
		System.out.println("# K. Compensation($)         #");
		System.out.println(DIRECTION_SEPARATOR);
		System.out.println("#####      Example      ######");
		System.out.println("## Ex:423186759;Ellie;Clark;eclark;clark;3125 Avent Ferry Road, Raleigh, NC 27605;eclark@gmail.com;9892180921;mechanic;11-JUN-2022;30.99 ##");
		System.out.println(DIRECTION_SEPARATOR);
		System.out.println();
		System.out.println("NOTE: It's important to enter information following");
        System.out.println("the example provided above using the delimiter, `;`");
        System.out.println();
	}

	public void displayMenu()
	{
		System.out.println("## Add New Employee Menu ##");
		System.out.println("1 Add");
		System.out.println("2 Go Back");
		System.out.println("##########");


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
				else {
					System.out.println("Failed adding a new employee");
					goBack();

					};

				break;
			case 2: goBack();
			break;
			default: run();
			break;
		}

	}

	@Override
	public void goBack() {
		new Manager().run();

	}



}
