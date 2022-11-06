package AutoCenter.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;
import AutoCenter.models.RepairService;
import AutoCenter.repository.DbConnection;
import AutoCenter.services.RepositoryService;
import AutoCenter.services.UserService;

public class SetupRepairServicePrices implements UserFlowFunctionality{

	private RepositoryService repoService = null;
	int inputLength = 12;
	private int[] priceTiers;
	private static final String DIRECTION_SEPARATOR = "#############################";
    private static final String MENU_SEPARATOR = "#######################################";
    private static final int EXPECTED_INPUT_LENGTH = 12;
    private static final int MIN_SELECTION = 1;
    private static final int MAX_SELECTION = 2;

	public SetupRepairServicePrices()
	{
		repoService = new RepositoryService();
	}
	public void run() {
		display();
		int selection = MAX_SELECTION;
		do {
			reset();
			String input = ScanHelper.nextLine();
			String[] inputs = input.split(";");
			if(inputs.length == EXPECTED_INPUT_LENGTH)
			{
				for(int i = 0; i < EXPECTED_INPUT_LENGTH; i++)
				{
					priceTiers[i] = Integer.parseInt(inputs[i].trim());
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

	public void reset() {
		priceTiers = new int[EXPECTED_INPUT_LENGTH];
	}
	@Override
	public void display() {
		System.out.println("#############################");
		System.out.println("######      Usage      ######");
		System.out.println(DIRECTION_SEPARATOR);
		System.out.println("# A. Belt Replacement      #");
		System.out.println("# B. Engine Repair         #");
		System.out.println("# C. Exhaust Repair        #");
		System.out.println("# D. Muffler Repair        #");
		System.out.println("# E. Alternator Repair     #");
		System.out.println("# F. Power Lock Repair     #");
		System.out.println("# G. Axle Repair           #");
		System.out.println("# H. Brake Repair          #");
		System.out.println("# I. Tire Balancing        #");
		System.out.println("# J. Wheel Alignment       #");
		System.out.println("# K. Compressor Repair     #");
		System.out.println("# L. Evaporator Repair     #");
		System.out.println(DIRECTION_SEPARATOR);
		System.out.println("#####      Example      ######");
		System.out.println("## Ex:2; 3; 4; 2; 3; 3; 5; 3; 1; 2; 4; 3 ##");
		System.out.println(DIRECTION_SEPARATOR);
		System.out.println();
		System.out.println("NOTE: It's important to enter information following");
        System.out.println("the example provided above using the delimiter, `;`");
        System.out.println();

	}

	public void displayMenu()
	{
		System.out.println("#######################################");
		System.out.println("##### Manager: Setup Repair Service Prices Menu #####");
		System.out.println(MENU_SEPARATOR);
		System.out.println("## 1 Setup prices                    ##");
		System.out.println("## 2 Go Back                         ##");
		System.out.println(MENU_SEPARATOR);
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
