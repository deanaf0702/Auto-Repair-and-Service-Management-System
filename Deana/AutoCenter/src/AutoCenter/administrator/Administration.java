package AutoCenter.administrator;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.manager.AddNewEmployee;
import AutoCenter.manager.SetupStore;
import AutoCenter.services.UserService;

public class Administration implements Interface{

	private UserService userservice = null;
	
	public Administration()
	{
		userservice = new UserService();
	}
	@Override
	public void run() {
		int selection = 4;
		display();
		do{
			System.out.println("Enter choice(1-4)");
			selection = ScanHelper.nextInt();
		}while(!(selection >=1 && selection <=4));
		
		navigate(selection);
	}

	@Override
	public void display() {
		System.out.println("##### Administration Page #####");
		System.out.println("1 System Set Up");
		System.out.println("2 Add New Store");
		System.out.println("3 Add New Service");
		System.out.println("4 Logout");
		System.out.println("##########");
	}

	@Override
	public void navigate(int selection) {
		switch (selection)
		{
			case 1: new SystemSetUp().run();
				break;
			case 2: new AddNewStore().run();
				break;
			case 3: new AddNewService().run();
			break;
			case 4: goBack();
				break;
		}
	}

	@Override
	public void goBack() {
		userservice.logout();
		
	}

}
