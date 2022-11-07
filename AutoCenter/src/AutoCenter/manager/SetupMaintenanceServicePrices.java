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

public class SetupMaintenanceServicePrices implements UserFlowFunctionality {

    private RepositoryService repoService = null;
    private String[] models = new String[] { "Honda", "Nissan", "Toyota" };
    private String[] serviceNames = new String[] { "A", "B", "C" };
    ArrayList<PriceModel> list = new ArrayList<PriceModel>();
    private static final String DIRECTION_SEPARATOR = "#############################";
    private static final String MENU_SEPARATOR = "#######################################";
    private static final int EXPECTED_INPUT_LENGTH = 3;
    private static final int EXPECTED_PRICEMODEL_LENGTH = 9;
    private static final int MIN_SELECTION = 1;
    private static final int MAX_SELECTION = 2;

    public SetupMaintenanceServicePrices() {
        repoService = new RepositoryService();
    }

    @Override
    public void run() {
        int selection;

        do {
            display();
            for (int i = 0; i < models.length; i++) {
                System.out.println("Enter Schedule A, B, and C hours and prices for " + models[i] + " ?");
                System.out.println("##  Ex(hours, price): 4, 100.00; 5, 110.00; 2, 120.00  ##");
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
        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));
        navigate(selection);
    }

    public void displayMenu() {
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
        System.out.println("# A. Schedule A Price  #");
        System.out.println("# B. Schedule B Price  #");
        System.out.println("# C. Schedule C Price  #");
        System.out.println(DIRECTION_SEPARATOR);
        System.out.println("#####      Example      ######");
        System.out.println("##       Ex (hours, price): 2, 100.0; 3, 110.0; 4, 120.0       ##");
        System.out.println(DIRECTION_SEPARATOR);
        System.out.println();
        System.out.println("NOTE: It's important to enter information following");
        System.out.println("the example provided above using the delimiter, `;`");
        System.out.println();
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                if (save()) {
                    System.out.println("Added Successfully!!");
                    goBack();
                } else {
                    System.out.println("Something went wrong!");
                    goBack();
                }

                break;
            case 2:
                goBack();
                break;
            default:
                goBack();
                break;
        }
    }

    public boolean save() {
        boolean valid = false;
        if (list.size() != EXPECTED_PRICEMODEL_LENGTH)
            return valid;
        String maintServiceQuery = "Select s.serviceid as serviceId, s. name as name"
                + " from MaintenanceServices m, Services s where m.serviceId = s.serviceId";
        try {
            DbConnection db = new DbConnection();
            String query = "insert into Prices (centerId, serviceId, model, price, hours) values(?,?,?,?,?)";
            int centerId = repoService.getCenterId();
            List<Service> maintServices = repoService.ServiceLookup(maintServiceQuery);
            try {
            	int count = 0;
                for (PriceModel item : list) {
                    PreparedStatement preStmt = db.getConnection().prepareStatement(query);
                    preStmt.setInt(1, centerId);
                    preStmt.setInt(2, repoService.findServiceId(maintServices, item.serviceName));
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
                if(count == 9) valid = true;
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

    public String addMaintServicePricedQuery(int serviceId, int centerId, String model, int priceTier, double price) {
        return ("insert into Prices (serviceId, centerId, model, price, hours) "
                + "values(" + serviceId + ", " + centerId + ", '" + model + "', " + priceTier + ", " + price + ")");
    }

    @Override
    public void goBack() {
        new SetupServicePrices().run();

    }

}
