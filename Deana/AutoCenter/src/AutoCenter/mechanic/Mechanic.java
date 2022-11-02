package AutoCenter.mechanic;

import AutoCenter.Home;
import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.manager.AddNewEmployee;
import AutoCenter.manager.SetupStore;
import AutoCenter.services.UserService;

public class Mechanic implements Interface{

	private UserService userservice = null;
	public Mechanic()
	{
		userservice = new UserService();
	}
	@Override
	public void run() {
		int selection = 5;
		display();
		do{
			System.out.println("Enter choice(1-5)");
			selection = ScanHelper.nextInt();
		}while(!(selection >=1 && selection <=5));
		
		navigate(selection);
	}

	@Override
	public void display() {
		System.out.println("##### Mechanic Home #####");
		System.out.println("1 View Schedule");
		System.out.println("2 Request TimeOff");
		System.out.println("3 Request Swap");
		System.out.println("4 Accept/Reject Swap");
		System.out.println("5 Logout");
		System.out.println("##########");
	}

	@Override
	public void navigate(int selection) {
		switch (selection)
		{
			case 1: new ViewSchedule().run();
				break;
			case 2: new RequestTimeOff().run();
				break;
			case 3: new RequestSwap().run();
			break;
			case 4: new AcceptRejectSwap().run();
			break;
			case 5: goBack();
				break;
		}
	}

	@Override
	public void goBack() {
		userservice.logout();
		
	}

}
