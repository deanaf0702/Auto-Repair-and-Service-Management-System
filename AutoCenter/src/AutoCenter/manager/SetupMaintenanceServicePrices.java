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
	private static final String DIRECTION_SEPARATOR = "#############################";
    private static final String MENU_SEPARATOR = "#######################################";
    private static final int EXPECTED_INPUT_LENGTH = 3;
    private static final int MIN_SELECTION = 1;
    private static final int MAX_SELECTION = 2;
    
	public SetupMaintenanceServicePrices()
	{
		repoService = new RepositoryService();
	}
	@Override
	public void run() {
		int selection = MAX_SELECTION;
		
		do {
			display();
			reset();
			String input = ScanHelper.nextLine();
			String[] inputs = input.split(";");
			if(inputs.length == EXPECTED_INPUT_LENGTH)
			{
				for(int i = 0; i < EXPECTED_INPUT_LENGTH; i++)
				{
					ABCPriceTier[i] = Integer.parseInt(inputs[i].trim());
				}
				displayMenu();
				System.out.println("Enter choice (1-2) from the given options displayed above:");
				selection = ScanHelper.nextInt();
			}else {
				System.out.println(
                        "Something went wrong. Please try again."
                                + " Take a look at the usage detailed above if you need help.");
			}	
		}while(!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));
		navigate(selection);
	}

	public void reset()
	{
		ABCPriceTier = new Integer[EXPECTED_INPUT_LENGTH];
		
	}
	public void displayMenu()
	{
		System.out.println("#######################################");
		System.out.println("##### Manager: Setup Maintenance Service Prices Menu #####");
		System.out.println(MENU_SEPARATOR);
		System.out.println("## 1 Setup prices                    ##");
		System.out.println("## 2 Go Back                         ##");
		System.out.println(MENU_SEPARATOR);
	}
	@Override
	public void display() {
		System.out.println("#############################");
		System.out.println("######      Usage      ######");
		System.out.println(DIRECTION_SEPARATOR);
		System.out.println("# A. Schedule A Price Tier  #");
		System.out.println("# B. Schedule B Price Tier  #");
		System.out.println("# C. Schedule C Price Tier  #");
		System.out.println(DIRECTION_SEPARATOR);
		System.out.println("#####      Example      ######");
		System.out.println("##       Ex: 6; 7; 8       ##");
		System.out.println(DIRECTION_SEPARATOR);
		System.out.println();
		System.out.println("NOTE: It's important to enter information following");
        System.out.println("the example provided above using the delimiter, `;`");
        System.out.println();
	}

	@Override
	public void navigate(int selection) {
		switch(selection)
		{
			case 1: if(save()) {
						System.out.println("Added Successfully!!");
						goBack();
						}		
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
				if(list.size() == EXPECTED_INPUT_LENGTH && carModels.size() == 3)
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
