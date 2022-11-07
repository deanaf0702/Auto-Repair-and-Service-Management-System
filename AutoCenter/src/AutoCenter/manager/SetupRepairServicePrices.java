package AutoCenter.manager;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;
import AutoCenter.models.PriceModel;
import AutoCenter.models.Service;
import AutoCenter.repository.DbConnection;
import AutoCenter.services.RepositoryService;

public class SetupRepairServicePrices implements UserFlowFunctionality{

	private RepositoryService repoService = null;
	private static final String DIRECTION_SEPARATOR = "#############################";
    private static final String MENU_SEPARATOR = "#######################################";
    private static final int EXPECTED_INPUT_LENGTH = 12;
    private static final int MIN_SELECTION = 1;
    private static final int MAX_SELECTION = 2;
    private String[] models = new String[] { "Honda", "Nissan", "Toyota" };
    private List<Service> repairServices = null;
    private String[] serviceNames = new String[EXPECTED_INPUT_LENGTH];
    ArrayList<PriceModel> list = new ArrayList<PriceModel>();
    
	public SetupRepairServicePrices()
	{
		repoService = new RepositoryService();
		repairServices = repoService.ServiceLookup(repairServiceQuery());
		int count = 0;
		for(Service s: repairServices) {
			serviceNames[count++] = s.getName();
		}
	}
	public void run() {
		
		int selection = MAX_SELECTION;
		
		do {
			display();
			reset();
			for (int i = 0; i < models.length; i++) {
                
                System.out.println("NOTE: there are 12 service pairs. Each pair has the hour and price values of a service and is sperated with the delimiter ';'. ");
                System.out.println("##  Ex (hours, price): 2, 90.00; 2, 100.00; 3, 120.00; 2, 90.00; 2, 100.00; 3, 120.00; 2, 90.00; 2, 100.00; 3, 120.00; 2, 90.00; 2, 100.00; 3, 120.00  ##");
                System.out.println();
                System.out.println("Enter the service hours and prices for " + models[i] + " ?");
                String input = ScanHelper.nextLine();
                String[] inputs = input.split(";");
                if (inputs.length == EXPECTED_INPUT_LENGTH) {
                	for(int j = 0; j < EXPECTED_INPUT_LENGTH; j++)
                	{
                		String items[] = inputs[j].split(",");
                        if (items.length == 2) {
                            PriceModel pm = new PriceModel();
                            pm.serviceName = serviceNames[j];
                            pm.model = models[i];
                            pm.hours = Integer.parseInt(items[0].trim());
                            pm.price = Double.parseDouble(items[1].trim());
                            list.add(pm);
                        } else {
                            System.out.println("Input Format Error");
                            break;
                        }
                	}   
                } else {
                    System.out.println("Input Format Error");
                    break;
                }
            }
			
			displayMenu();
			System.out.println("Enter choice (1-2) from the given options displayed above:");
			selection = ScanHelper.nextInt();
			
		}while(!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));
		navigate(selection);

	}

	public void reset() {
		
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
		System.out.println("## Ex (hours, price):2, 90.00; 2, 100.00; 3, 120.00; 2, 90.00; 2, 100.00; 3, 120.00; 2, 90.00; 2, 100.00; 3, 120.00; 2, 90.00; 2, 100.00; 3, 120.00 ##");
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
					if (save()) {
	                    System.out.println("Added Successfully!!");
	                    goBack();
	                } else {
	                    System.out.println("Something went wrong!");
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
		boolean valid = false;
		try {
            DbConnection db = new DbConnection();
            String query = "insert into Prices (centerId, serviceId, model, price, hours) values(?,?,?,?,?)";
            int centerId = repoService.getCenterId();
            
            try {
            	int count = 0;
                for (PriceModel item : list) {
                    PreparedStatement preStmt = db.getConnection().prepareStatement(query);
                    preStmt.setInt(1, centerId);
                    preStmt.setInt(2, repoService.findServiceId(repairServices, item.serviceName));
                    preStmt.setString(3, item.model);
                    preStmt.setDouble(4, item.price);
                    preStmt.setInt(5, item.hours);
                    int result = preStmt.executeUpdate();
                    if (result < 1) {
                        System.out.println("Database error: " + item.serviceName
                                + ", " + item.model
                                + ", " + centerId);
                        valid = false;
                    }
                    count++;
                    preStmt.close();
                }
                if(count == (EXPECTED_INPUT_LENGTH * models.length)) valid = true;
            }finally {
                db.close();
            }

        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println(e.getMessage());
            valid = false;
        }
        return valid;
	}

	@Override
	public void goBack() {
		new SetupServicePrices().run();
	}

	public String repairServiceQuery()
	{
		String repairServiceQuery = "Select s.serviceid as serviceId, s. name as name "
				+ "from RepairServices r, Services s where r.serviceId = s.serviceId";
		return repairServiceQuery;
	}
}
