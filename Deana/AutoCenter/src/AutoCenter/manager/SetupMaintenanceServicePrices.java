package AutoCenter.manager;

import java.util.List;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.models.MaintenanceService;
import AutoCenter.repository.DbConnection;
import AutoCenter.services.RepositoryService;
import AutoCenter.services.UserService;

public class SetupMaintenanceServicePrices implements Interface {

	private UserService userService = null;
	private RepositoryService repoService = null;
	private Integer[] ABCPriceTier;
	int inputLength = 3;
	
	public SetupMaintenanceServicePrices()
	{
		userService = new UserService();
		repoService = new RepositoryService();
	}
	@Override
	public void run() {
		int selection = 2;
		display();
		do {
			displayDirection();
			reset();
			String input = ScanHelper.nextLine();
			String[] inputs = input.split(";");
			if(inputs.length >= inputLength)
			{
				ABCPriceTier[0] = Integer.parseInt(inputs[0]);
				ABCPriceTier[1] = Integer.parseInt(inputs[1]);
				ABCPriceTier[2] = Integer.parseInt(inputs[2]);
				System.out.println("Enter choics(1-2)");
				selection = ScanHelper.nextInt();
			}else {
				System.out.println("Went wrong. Try again");
				selection = 0;
			}	
		}while(!(selection >=1 && selection <=2));
		navigate(selection);
	}

	public void reset()
	{
		ABCPriceTier = new Integer[3];
		
	}
	public void displayDirection()
	{
		
		System.out.println("A Schedule A Price Tier");
		System.out.println("B Schedule B Price Tier");
		System.out.println("C Schedule C Price Tier");
		System.out.println("## Ex:6; 7; 8 ##");
		System.out.println("## Enter the information in the order as shown below with the delimiter ‘;’");
		
	}
	@Override
	public void display() {
		System.out.println("## Setup Maintenance Service Prices Menu ##");
		System.out.println("1 Setup prices");
		System.out.println("2 Go Back");
		System.out.println("##########");
		
		
	}

	@Override
	public void navigate(int selection) {
		switch(selection)
		{
			case 1: if(save())
						goBack();
					else
						run();
				break;
			case 2: goBack();
				break;
		}	
	}

	public boolean save()
	{
		boolean valid = true;
		try {
		DbConnection db = new DbConnection();
		try {
			List<MaintenanceService> list = repoService.maintServiceLookup();
			List<String> carModels = repoService.carModelLookup();
			int centerId = repoService.getCenterId();
			if(list.size() == 3 && carModels.size() == 3)
			{
				for(MaintenanceService item: list) {
					int priceCount = 0;
					for(String model: carModels) {
						double price = repoService.getServicePrice(centerId, model, ABCPriceTier[priceCount]);
						String query = addMaintServicePricedQuery(
								item.getServiceId(),
								model,
								ABCPriceTier[priceCount],
								price);
						priceCount++;
						boolean result = db.executeUpdate(query);
						if(!result) {
							System.out.println("Database error: " + item.getScheduleType() 
									+ ", " + model
									+ ", " + ABCPriceTier[priceCount]);
							valid = false;
						}
					}
					
				}
			}else {
				valid = false;
			}
		}finally {
			db.close();
		}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			valid = false;
		}
		return valid;
	}
	
	public String addMaintServicePricedQuery(int serviceId, String model, int priceTier, double price)
	{
		int centerId = userService.getCenterId();
		return ("insert into MaintServicePriced (serviceId, centerId, model, priceTier, price) "
				+ "values("+ serviceId + ", " + centerId + ", '" + model + "', " + priceTier + ", " + price + ")");
	}
		
	@Override
	public void goBack() {
		new SetupServicePrices().run();
		
	}

}
