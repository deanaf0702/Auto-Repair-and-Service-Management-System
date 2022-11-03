package AutoCenter.receptionist;

import AutoCenter.Home;
import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.manager.AddNewEmployee;
import AutoCenter.manager.SetupStore;
import AutoCenter.services.UserService;

public class Receptionist implements Interface{

	private UserService userservice = null;
	public Receptionist()
	{
		userservice = new UserService();
	}
	@Override
	public void run() {
		int selection = 3;
		display();
		do{
			System.out.println("Enter choice(1-3)");
			selection = ScanHelper.nextInt();
		}while(!(selection >=1 && selection <=3));
		
		navigate(selection);
	}

	@Override
	public void display() {
		System.out.println("##### Receptionist Page #####");
		System.out.println("1 Add New Customer Profile");
		System.out.println("2 Find Customers with Pending Invoices");
		System.out.println("3 Logout");
		System.out.println("##########");
	}

	@Override
	public void navigate(int selection) {
		switch (selection)
		{
			case 1: new AddNewCustomerProfile().run();
				break;
			case 2: new FindCustomersWithPendingInvoices().run();
				break;
			case 3: goBack();
				break;
		}
	}

	@Override
	public void goBack() {
		userservice.logout();
		
	}

}
