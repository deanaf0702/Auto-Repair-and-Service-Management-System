package AutoCenter.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;
import AutoCenter.models.User;
import AutoCenter.services.UserService;

public class AddNewEmployee implements UserFlowFunctionality{

	private UserService userService = null;
	private User User = null;
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
			User = new User();
			String input = ScanHelper.nextLine();
			String[] inputs = input.split(";");
			if(inputs.length == EXPECTED_INPUT_LENGTH  )
			{
				User.setUserId(Integer.parseInt(inputs[0]));
				User.setFirstName(inputs[1]);
				User.setLastName(inputs[2]);
				User.setUsername(inputs[3]);
				User.setPassword(inputs[4]);
				User.setAddress(inputs[5]);
				User.setEmail(inputs[6]);
				User.setPhone(inputs[7]);
				User.setRole(inputs[8]);
				User.setSalaryOrWage(Double.parseDouble(inputs[10]));

				displayMenu();
				System.out.println("Enter choice (1-2) from the given options displayed above:");
				selection = ScanHelper.nextInt();
			}else {
				System.out.println(
                        "Something went wrong. Please try again and make sure you provide all Eleven inputs for an User."
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
		System.out.println("# A. User ID             #");
		System.out.println("# B. First Name              #");
		System.out.println("# C. Last Name               #");
		System.out.println("# D. UserName                #");
		System.out.println("# E. Password                #");
		System.out.println("# F. Address                 #");
		System.out.println("# G. EmilAddress             #");
		System.out.println("# H. Phone Number            #");
		System.out.println("# I. Role                    #");
		System.out.println("# J. Compensation($)         #");
		System.out.println(DIRECTION_SEPARATOR);
		System.out.println("#####      Example      ######");
		System.out.println("## Ex:423186759;Ellie;Clark;eclark;clark;3125 Avent Ferry Road, Raleigh, NC 27605;eclark@gmail.com;9892180921;mechanic;30.99 ##");
		System.out.println(DIRECTION_SEPARATOR);
		System.out.println();
		System.out.println("NOTE: It's important to enter information following");
        System.out.println("the example provided above using the delimiter, `;`");
        System.out.println();
	}

	public void displayMenu()
	{
		System.out.println("## Add New User Menu ##");
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
				result = userService.addUser(User);
				if(result) {
					System.out.println("Added Successfully");
					goBack();
				}
				else {
					System.out.println("Failed adding a new User");
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
