package AutoCenter.manager;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.models.MaintenanceService;
import AutoCenter.repository.DbConnection;
import AutoCenter.services.RepositoryService;
import AutoCenter.services.UserService;
import java.util.List;

/**
 * @author jkersey
 */
public class SetupMaintenanceServicePrices implements Interface {

    private static int INITIAL_SELECTION = 2;
    private static int MIN_SELECTION = 1;
    private static int MAX_SELECTION = 2;
    private static int EXPECTED_INPUT_LENGTH = 3;
    private UserService userService = null;
    private RepositoryService repoService = null;
    private Integer[] ABCPriceTier;

    public SetupMaintenanceServicePrices() {
        userService = new UserService();
        repoService = new RepositoryService();
    }

    @Override
    public void run() {
        int selection = INITIAL_SELECTION;
        display();

        do {
            displayDirection();
            reset();
            String input = ScanHelper.nextLine();
            String[] inputs = input.split(";");
            if (inputs.length >= EXPECTED_INPUT_LENGTH) {
                ABCPriceTier[0] = Integer.parseInt(inputs[0]);
                ABCPriceTier[1] = Integer.parseInt(inputs[1]);
                ABCPriceTier[2] = Integer.parseInt(inputs[2]);
                System.out.print("Enter choice (1-2) from the given options displayed above: ");
                selection = ScanHelper.nextInt();
            } else {
                System.out.println(
                        "Something went wrong. Please try again and make sure you provide all three input"
                                + " prices.");
                selection = 0;
            }
        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));
        navigate(selection);
    }

    public void reset() {
        ABCPriceTier = new Integer[3];
    }

    public void displayDirection() {
        System.out.println("############################");
        System.out.println("######     Usage       #####");
        System.out.println("############################");
        System.out.println("# A. Schedule A Price Tier #");
        System.out.println("# ------------------------ #");
        System.out.println("# B. Schedule B Price Tier #");
        System.out.println("# ------------------------ #");
        System.out.println("# C. Schedule C Price Tier #");
        System.out.println("############################");
        System.out.println("#####     Example      #####");
        System.out.println("############################");
        System.out.println("##        6; 7; 8         ##");
        System.out.println("############################");
        System.out.println();
        System.out.println("NOTE: It's important to enter information following");
        System.out.println("the example provided above using the delimiter, `;`");
        System.out.println();
    }

    @Override
    public void display() {
        System.out.println("######################################");
        System.out.println("##### Manager: Setup Maintenance #####");
        System.out.println("#####    Service Prices Menu     #####");
        System.out.println("######################################");
        System.out.println("# 1 Setup prices                     #");
        System.out.println("# 2 Go Back                          #");
        System.out.println("######################################");
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                if (save())
                    goBack();
                else
                    run();
                break;
            case 2:
                goBack();
                break;
        }
    }

    public boolean save() {
        boolean valid = true;
        try {
            DbConnection db = new DbConnection();
            try {
                List<MaintenanceService> list = repoService.maintServiceLookup();
                List<String> carModels = repoService.carModelLookup();
                int centerId = repoService.getCenterId();
                if (list.size() == 3 && carModels.size() == 3) {
                    int priceCount = 0;
                    for (MaintenanceService item : list) {

                        for (String model : carModels) {
                            double price = repoService.getServicePrice(centerId, model, ABCPriceTier[priceCount]);
                            String query = addMaintServicePricedQuery(
                                    item.getServiceId(), centerId, model, ABCPriceTier[priceCount], price);

                            boolean result = db.executeUpdate(query);
                            if (!result) {
                                System.out.println(
                                        "Database error: "
                                                + item.getScheduleType()
                                                + ", "
                                                + model
                                                + ", "
                                                + ABCPriceTier[priceCount]);
                                valid = false;
                            }
                        }
                        priceCount++;
                    }
                } else {
                    valid = false;
                }
            } finally {
                db.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            valid = false;
        }
        return valid;
    }

    public String addMaintServicePricedQuery(
            int serviceId, int centerId, String model, int priceTier, double price) {
        return ("insert into MaintServicePriced (serviceId, centerId, model, priceTier, price) "
                + "values("
                + serviceId
                + ", "
                + centerId
                + ", '"
                + model
                + "', "
                + priceTier
                + ", "
                + price
                + ")");
    }

    @Override
    public void goBack() {
        new SetupServicePrices().run();
    }
}
