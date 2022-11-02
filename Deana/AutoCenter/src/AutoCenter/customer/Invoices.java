package AutoCenter.customer;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.manager.AddNewEmployee;
import AutoCenter.manager.SetupStore;

public class Invoices implements Interface{

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
		System.out.println("##### Invoice Menu #####");
		System.out.println("1 View Invoice details ");
		System.out.println("2 Pay invoice");
		System.out.println("3 goBack");
		System.out.println("##########");
	}

	@Override
	public void navigate(int selection) {
		switch (selection)
		{
			case 1: new ViewInvoiceDetails().run();
				break;
			case 2: new PayInvoice().run();
				break;
			case 3: goBack();
				break;
		}
	}

	@Override
	public void goBack() {
		new Invoices().run();		
	}

}
