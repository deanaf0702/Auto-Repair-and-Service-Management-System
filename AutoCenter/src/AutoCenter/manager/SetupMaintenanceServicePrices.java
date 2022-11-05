package AutoCenter.manager;

import java.util.List;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.models.MaintenanceService;
import AutoCenter.repository.DbConnection;
import AutoCenter.services.RepositoryService;
import AutoCenter.services.UserService;

public class SetupMaintenanceServicePrices implements Interface {

	private RepositoryService repoService = null;
	private Integer[] ABCPriceTier;
	int inputLength = 3;
	
	public SetupMaintenanceServicePrices()
	{
		repoService = new RepositoryService();
	}
	@Override
	public void run() {
		int selection = 2;
		
		do {
			display();
			reset();
			String input = ScanHelper.nextLine();
			String[] inputs = input.split(";");
			if(inputs.length == inputLength)
			{
				for(int i = 0; i < inputLength; i++)
				{
					ABCPriceTier[i] = Integer.parseInt(inputs[i].trim());
				}
				displayDirection();
				System.out.println("Enter choices(1-2)");
				selection = ScanHelper.nextInt();
			}else {
				System.out.println("Went wrong. Try again");
			}	
		}while(!(selection >=1 && selection <=2));
		navigate(selection);
	}

	public void reset()
	{
		ABCPriceTier = new Integer[inputLength];
		
	}
	public void displayDirection()
	{
		System.out.println("## Setup Maintenance Service Prices Menu ##");
		System.out.println("1 Setup prices");
		System.out.println("2 Go Back");
		System.out.println("##########");
	}
	@Override
	public void display() {
		System.out.println("A Schedule A Price Tier");
		System.out.println("B Schedule B Price Tier");
		System.out.println("C Schedule C Price Tier");
		System.out.println("## Ex:6; 7; 8 ##");
		System.out.println("## Enter the information in the order as shown below with the delimiter ‘;’");	
	}

	@Override
	public void navigate(int selection) {
		switch(selection)
		{
			case 1: if(save())
						goBack();
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
		try {
			
		
			List<MaintenanceService> list = repoService.maintServiceLookup();
			List<String> carModels = repoService.carModelLookup();
			int centerId = repoService.getCenterId();
			if(list != null && carModels != null && centerId > 0)
			{
				if(list.size() == inputLength && carModels.size() == 3)
				{
					DbConnection db = new DbConnection();
					try {
						int priceCount = 0;
						for(MaintenanceService item: list) {
							
							for(String model: carModels) {
								double price = repoService.getServicePrice(centerId, model, ABCPriceTier[priceCount]);
								String query = addMaintServicePricedQuery(
										item.getServiceId(),
										centerId,
										model,
										ABCPriceTier[priceCount],
										price);
								
								boolean result = db.executeUpdate(query);
								if(!result) {
									System.out.println("Database error: " + item.getScheduleType() 
											+ ", " + model
											+ ", " + ABCPriceTier[priceCount]);
									valid = false;
								}
							}
							priceCount++;
						}
					}finally {
						db.close();
					}
				}else {
					valid = false;
				}
			
			}
			
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			valid = false;
		}
		return valid;
	}
	
	public String addMaintServicePricedQuery(int serviceId, int centerId, String model, int priceTier, double price)
	{
		return ("insert into MaintServicePriced (serviceId, centerId, model, priceTier, price) "
				+ "values("+ serviceId + ", " + centerId + ", '" + model + "', " + priceTier + ", " + price + ")");
	}
		
	@Override
	public void goBack() {
		new SetupServicePrices().run();
		
	}

}
