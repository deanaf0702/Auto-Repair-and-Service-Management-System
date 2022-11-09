package AutoCenter.manager;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private int Expected_Input_Length;
    private static final int MIN_SELECTION = 1;
    private static final int MAX_SELECTION = 2;
    private String[] models = new String[] { "Honda", "Nissan", "Toyota" };
    private Map<Integer, Service> repairServices = null;
    ArrayList<PriceModel> list = new ArrayList<PriceModel>();
    
	public SetupRepairServicePrices()
	{
		repoService = new RepositoryService();
		repairServices = repoService.ServiceLookup(repairServiceQuery());
		Expected_Input_Length = repairServices.size();
	}
	public void run() {
		
		int selection = MAX_SELECTION;
		
		do {
			display();
			reset();
			for (int i = 0; i < models.length; i++) {
                
                System.out.println("## Enter the services hours and prices for " + models[i] + " :");
              
                int count = 0;
                while(Expected_Input_Length > count)
            	{
            		System.out.print("Enter the " + repairServices.get(count).getName() + " prices for " + models[i] + " ?");
            		Double price = ScanHelper.nextDouble();
            		System.out.print("Enter the " + repairServices.get(count).getName() + " hours for " + models[i] + " ?");
            		int hours = ScanHelper.nextInt();
            		if(price > 0.0 && hours > 0)
            		{
            			PriceModel pm = new PriceModel();
            			pm.serviceId = repairServices.get(count).getServiceId();
                        pm.serviceName = repairServices.get(count).getName();
                        pm.model = models[i];
                        pm.hours = hours;
                        pm.price = price;
                        list.add(pm);
                        count++;
            		}else {
            			System.out.println("Input Format Error");
            		}
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
                    preStmt.setInt(2, item.serviceId);
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
                if(count == (Expected_Input_Length * models.length)) valid = true;
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
