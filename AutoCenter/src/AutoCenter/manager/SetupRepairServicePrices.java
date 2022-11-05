package AutoCenter.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.models.MaintenanceService;
import AutoCenter.models.RepairService;
import AutoCenter.repository.DbConnection;
import AutoCenter.services.RepositoryService;
import AutoCenter.services.UserService;

public class SetupRepairServicePrices implements Interface{

	private RepositoryService repoService = null;
	int inputLength = 12;
	int[] priceTiers;
	
	
	public SetupRepairServicePrices()
	{
		repoService = new RepositoryService();
	}
	public void run() {
		display();
		int selection = 2;
		do {
			reset();
			String input = ScanHelper.nextLine();
			String[] inputs = input.split(";");
			if(inputs.length == inputLength)
			{
				for(int i = 0; i < inputLength; i++)
				{
					priceTiers[i] = Integer.parseInt(inputs[i].trim());
				}
				
				displayMenu();
				System.out.println("Enter choices(1-2)");
				selection = ScanHelper.nextInt();
			}else {
				System.out.println("Went wrong. Try again");
			}	
		}while(!(selection >=1 && selection <=2));
		navigate(selection);
		
	}

	public void reset() {
		priceTiers = new int[inputLength];
	}
	@Override
	public void display() {
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
		
		System.out.println("## Ex:2; 3; 4; 2; 3; 3; 5; 3; 1; 2; 4; 3 ##");
		System.out.println("## It is required to have 12 price tiers ##");
		System.out.println("## Enter the information in the order as shown above with the delimiter ‘;’");
	}

	public void displayMenu()
	{
		System.out.println("## Setup Repair Service Prices Menu ##");
		System.out.println("1 Setup prices");
		System.out.println("2 Go Back");
		System.out.println("##########");
	}
	@Override
	public void navigate(int selection) {
		switch(selection)
		{
			case 1: 
				if(save()) goBack();
				else {
					System.out.println("Somthing went wrong!");
					goBack();
				}
			break;
			case 2: goBack();
			break;
			default: goBack();
				break;
		}
	}
	
	public boolean save()
	{
		boolean valid = true;
		String query = "Insert into RepairServicePriced (centerId, serviceId, model, pricetier, price) values(?, ?, ?, ?, ?)";
		
		try {
			int centerId = repoService.getCenterId();
			List<RepairService> list = repoService.repairServiceLookup();
			List<String> carModels = repoService.carModelLookup();
			if(list != null && carModels != null && centerId > 0)
			{
				if(list.size() == inputLength && carModels.size() == 3)
				{
					DbConnection db = new DbConnection();
					try {
						int tierCount = 0;
						for(RepairService service: list)
						{
							for(String model: carModels)
							{
								double price = repoService.getServicePrice(centerId, model, priceTiers[tierCount]);
								PreparedStatement preStmt = db.getConnection().prepareStatement(query);
								preStmt.setInt(1, centerId);
								preStmt.setInt(2, service.getServiceId());
								preStmt.setString(3, model);
								preStmt.setInt(4, priceTiers[tierCount]);
								preStmt.setDouble(5, price);
								int result = preStmt.executeUpdate();
								if(result < 1)
								{
									System.out.println("Database error: " + service.getName() 
									+ ", " + model
									+ ", " + priceTiers[tierCount]);
									valid = false;
								}
								preStmt.close();
							}
						}
					}finally {
						db.close();
					}
				}
			}	
		}catch(Exception e) {
			e.printStackTrace();
		};
		return valid;
	}

	@Override
	public void goBack() {
		new SetupServicePrices().run();
		
	}

}
