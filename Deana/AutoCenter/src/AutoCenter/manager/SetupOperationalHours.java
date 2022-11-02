package AutoCenter.manager;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.repository.DbConnection;
import AutoCenter.services.UserService;

public class SetupOperationalHours implements Interface {

	private char satOpen = '0';
	private UserService userService = null;
	private DbConnection db = null;
	
	public SetupOperationalHours()
	{
		userService = new UserService();
		db = new DbConnection();
	}
	@Override
	public void run() {
		int selection = 2;
		do {
			satOpen = '0';
			display();
			System.out.println("Enter choice(1-2)");
			selection = ScanHelper.nextInt();
			if(selection == 1) {
				System.out.println("A Operational on Saturdays? (Yes/No)");
				String openSat = ScanHelper.next();
				if(openSat.isEmpty()) selection = 2;
				char choice = openSat.trim().toUpperCase().charAt(0);
				if(choice == 'Y') satOpen = '1';
			}
		}while(!(selection >=1 && selection <=2));
		navigate(selection);
	}

	@Override
	public void display() {
		System.out.println("## Setup Operational Hours Menu ##");
		System.out.println("1 Setup operational hours");
		System.out.println("2 Go Back");
		System.out.println("##########");
		
	}

	@Override
	public void navigate(int selection) {
		switch (selection)
		{
			case 1:
				String query = openSatQuery();
				boolean result = db.executeUpdate(query);
				if(result) System.out.println("Updated Successfully");
				else System.out.println("Failed update");
				db.close();
			break;
		}
		goBack();
	}

	@Override
	public void goBack() {
		new SetupStore().run();
		
	}
	
	private String openSatQuery()
	{
		int centerId  = userService.getCenterId();
		
		String query = "UPDATE  ServiceCenters set satOpen = '" 
				+ satOpen + "' where centerId=" + centerId;
		return query;
		
	}

}
