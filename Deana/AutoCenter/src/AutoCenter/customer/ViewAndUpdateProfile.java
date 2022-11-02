package AutoCenter.customer;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.manager.AddNewEmployee;
import AutoCenter.manager.SetupStore;

public class ViewAndUpdateProfile implements Interface{

	public ViewAndUpdateProfile(){}
	@Override
	public void run() {
		int selection = 0;
		display();
		do{
			System.out.println("Enter choice(1-3)");
			selection = ScanHelper.nextInt();
		}while(!(selection >=1 && selection <=3));
		
		navigate(selection);
	}

	@Override
	public void display() {
		System.out.println("##### View and Update Profile Menu #####");
		System.out.println("1 View Profile");
		System.out.println("2 Add Car");
		System.out.println("3 Delete Car");
		System.out.println("4 Go Back");
		System.out.println("##########");
	}

	@Override
	public void navigate(int selection) {
		switch (selection)
		{
			case 1: new ViewProfile().run();
				break;
			case 2: new AddCar().run();
				break;
			case 3: new DeleteCar().run();
			break;
			case 4: goBack();
				break;
		}
	}

	@Override
	public void goBack() {
		new Customer().run();
		
	}

}
