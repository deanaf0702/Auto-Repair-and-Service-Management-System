package AutoCenter.manager;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;

public class SetupServicePrices implements Interface{

	@Override
	public void run() {
		int selection = 3;
		display();
		do {
			System.out.println("Enter Choice(1-3):");
			selection = ScanHelper.nextInt();
		}while(!(selection >=1 && selection <=3));
		navigate(selection);
	}

	@Override
	public void display() {
		System.out.println("## Setup Service Prices Menu ##");
		System.out.println("1 Setup Manintenance Service Prices");
		System.out.println("2 Setup Repair Service Prices");
		System.out.println("3 Go Back");
		System.out.println("##########");
		
	}

	@Override
	public void navigate(int selection) {
		
		switch(selection)
		{
		case 1: new SetupMaintenanceServicePrices().run();
			break;
		case 2: new SetupRepairServicePrices().run();
			break;
		case 3: goBack();
		}
		
	}

	@Override
	public void goBack() {
		new SetupStore().run();
		
	}

}
