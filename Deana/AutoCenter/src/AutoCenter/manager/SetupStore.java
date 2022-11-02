package AutoCenter.manager;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;

public class SetupStore implements Interface {

	@Override
	public void run() {
		int selection = 4;
		display();
		do {
			System.out.println("Enter choice(1-4): ");
			selection = ScanHelper.nextInt();
		}while(!(selection >=1 && selection <=4));
		navigate(selection);
	}

	@Override
	public void display() {
		System.out.println("## Setup Store Menu ##");
		System.out.println("1 Add Employees");
		System.out.println("2 Setup Operational Hours");
		System.out.println("3 Setup Service Prices");
		System.out.println("4 Go Back");
		System.out.println("##########");
	}

	@Override
	public void goBack() {
		new Manager().run();
		
	}

	@Override
	public void navigate(int selection) {
		switch (selection)
		{
		case 1: new AddEmployees().run();
		break;
		case 2: new SetupOperationalHours().run();
		break;
		case 3: new SetupServicePrices().run();
		break;
		case 4: goBack();
		break;
		}
		
	}
}
