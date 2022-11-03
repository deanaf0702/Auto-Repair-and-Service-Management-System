package AutoCenter.manager;

import java.sql.PreparedStatement;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.repository.DbConnection;
import AutoCenter.services.RepositoryService;
import AutoCenter.services.UserService;

public class SetupRepairServicePrices implements Interface{

	private RepositoryService repoService = null;
	private UserService userService = null;
	String[] prices;
	String vehicleName = null;
	
	public SetupRepairServicePrices()
	{
		repoService = new RepositoryService();
		userService = new UserService();
	}
	public void run() {
		display();
		int selection = 2;
		do {
			reset();
			System.out.println("Enter Vehicle Name (Ex:Honda): ");
			vehicleName = ScanHelper.next();
			displayDirection();
			String input = ScanHelper.nextLine();
			prices = input.split(",");
			System.out.println("Enter choice(1-2)");
			selection = ScanHelper.nextInt();
			
		}while(!(selection >=1 && selection <=2));
		navigate(selection);
		
	}

	public void reset() {
		prices = new String[12];
		vehicleName = null;
	}
	@Override
	public void display() {
		System.out.println("## Setup Repair Service Prices Menu ##");
		System.out.println("1 Setup prices");
		System.out.println("2 Go Back");
		System.out.println("##########");
		
	}

	public void displayDirection()
	{
		System.out.println("A. Belt Replacement\n"
				+ "B. Engine Repair\n"
				+ "C. Exhaust Repair\n"
				+ "D. Muffler Repair\n"
				+ "E. Alternator Repair\n"
				+ "F. Power Lock Repair\n"
				+ "G. Axle Repair\r\n"
				+ "H. Brake Repair\n"
				+ "I. Tire Balancing\n"
				+ "J. Wheel Alignment\n"
				+ "K. Compressor Repair\n"
				+ "L. Evaporator Repair");
		
		System.out.println("## Ex:20.00, 35.99, 55.99, 12.00, 55.56, 120.55, 256.99, 100.00, 35.00, 100.99, 120.66, 40.99 ##");
		System.out.println("## Enter the information in the order as shown above with the delimiter ‘,’");
		
	}
	@Override
	public void navigate(int selection) {
		switch(selection)
		{
			case 1: 
				save();
				goBack();
			break;
			case 2: goBack();
		}
		
	}
	
	public void save()
	{
		String query = "Insert into ServicePricedByManf (serviceId, centerId, vehicleManfId, price) values(?, ?, ?, ?)";
		
		try {
			int centerId = userService.getCenterId();
			int manfId = repoService.getVehicleManfId(vehicleName);
			int[] repairIds = repoService.getRepairServiceIds();
			DbConnection db = new DbConnection();
			try {
				for(int i = 0; i < prices.length; i++)
				{
					PreparedStatement preStmt = db.getConnection().prepareStatement(query);
					preStmt.setInt(2, centerId);
					preStmt.setInt(3, manfId);
					preStmt.setInt(1, repairIds[i]);
					preStmt.setDouble(4, Double.parseDouble(prices[i]));
					preStmt.executeUpdate();
					preStmt.close();
				}
			}finally {
				db.close();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		};
		
		
	}

	@Override
	public void goBack() {
		new SetupServicePrices().run();
		
	}

}
