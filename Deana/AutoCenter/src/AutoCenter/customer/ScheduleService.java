package AutoCenter.customer;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.manager.AddNewEmployee;
import AutoCenter.manager.SetupStore;

public class ScheduleService implements Interface{

	@Override
	public void run() {
		int selection = 0;
		display();
		do{
			System.out.println("Enter choice(1-4)");
			selection = ScanHelper.nextInt();
		}while(!(selection >=1 && selection <=4));
		
		navigate(selection);
		
	}

	@Override
	public void display() {
		System.out.println("##### Schedule Service Menu #####");
		System.out.println("1 Add Schedule Maintenance");
		System.out.println("2 Add Schedule Repair");
		System.out.println("3 View cart and select schedule time");
		System.out.println("4 Go Back");
		System.out.println("##########");
	}

	@Override
	public void navigate(int selection) {
		switch (selection)
		{
			case 1: new AddScheduleMaintenance().run();
				break;
			case 2: new AddScheduleRepair().run();
				break;
			case 3: new ViewCartandSelectScheduleTime().run();
				break;
			case 5: goBack();
		}
		
	}

	@Override
	public void goBack() {
		new ScheduleService().run();
		
	}

}
