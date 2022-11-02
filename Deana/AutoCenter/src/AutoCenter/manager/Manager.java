package AutoCenter.manager;

import AutoCenter.Home;
import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.services.UserService;

public class Manager implements Interface {

	private UserService userservice = null;
	
	public Manager()
	{
		userservice = new UserService();
	}
	
	public void run() 
	{
		int selection = 3;
		display();
		do{
			System.out.println("Enter choice(1-3)");
			selection = ScanHelper.nextInt();
		}while(!(selection >=1 && selection <=3));
		
		navigate(selection);
	}
	public void navigate(int selection)
	{
		switch (selection)
		{
			case 1: new SetupStore().run();
				break;
			case 2: new AddNewEmployee().run();
				break;
			case 3: goBack();
				break;
		}
	}
	@Override
	public void display() {
		System.out.println("##### Manager Home #####");
		System.out.println("1 Setup Store");
		System.out.println("2 Add New Employee");
		System.out.println("3 Logout");
		System.out.println("##########");
	}
	@Override
	public void goBack() {
		userservice.logout();
		
	}
}
