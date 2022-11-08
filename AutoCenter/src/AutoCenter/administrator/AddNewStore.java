package AutoCenter.administrator;

import java.sql.PreparedStatement;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.models.User;
import AutoCenter.repository.DbConnection;
import AutoCenter.repository.UserRepository;
import AutoCenter.services.RepositoryService;
import AutoCenter.ScanHelper;
import AutoCenter.UIHelpers;

/**
 * Handles functionality for add new store menu of
 * administrators including display, input, and output.
 *
 * @author jkersey, tsenck12, deoks
 */
public class AddNewStore implements UserFlowFunctionality {

    /*
     * The minimum selection for the menu options range
     */
    private static final int MIN_SELECTION = 1;

    /*
     * The maximum selection for the menu options range
     */
    private static final int MAX_SELECTION = 2;

    /*
     * The separator to use between the menu title and the options
     */
    private static final String MENU_SEPARATOR = "#####################################";

    /*
     * The separator to use between the usage header and example header
     */
    private static final String DIRECTION_SEPARATOR = "#####################################";

    private UserRepository userRepo = null;
    private User manager;
    private int centerId;
    private String address;
    private Double minWage;
    private Double maxWage;

    public AddNewStore() {
        userRepo = new UserRepository();
    }

    @Override
    public void run() {
        int selection = MAX_SELECTION;
        display();

        do {
            displayDirections();
            // TODO add file parsing here
            System.out.println("Enter Store ID (ex:30004) ?");
            int storeId = ScanHelper.nextInt();
            System.out.println("Enter Store Address (ex:1378 University Woods, Raleigh, NC 27612)?");
            String storeAddress = ScanHelper.nextLine();
            System.out.println("Enter Manager's information?");
            System.out.println(
                    "Ex:653186733;Deana;Franks;dfranks;Franks;1234 Pyxis Court, Raleigh, NC 27605;dfranks@gmail.com;9199994567;Manager;200000.00");
            String managerInfo = ScanHelper.nextLine();

            System.out.println("Enter Min and max wage for mechanics using the delimiter, ';' (ex: 30.00; 75.00 )?");
            String minMax = ScanHelper.nextLine();

            if (validateInput(storeId, storeAddress, managerInfo, minMax)) {
                System.out.print("Enter choice (" + MIN_SELECTION + "-" + MAX_SELECTION
                        + ") from the given options displayed above: ");
                selection = ScanHelper.nextInt();
            }

        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));

        navigate(selection);
    }

    private void displayDirections() {
        String[] usageComponents = {
                "# A. Store ID                       #",
                "# B. Address                        #",
                "# C. Manager's information (first   #\n" +
                        "#    name, lastname,                #\n" +
                        "#    password, salary and           #\n" +
                        "#    employeeid)                    #"
        };

        // TODO add example here
        UIHelpers.displayUsageDirections(
                usageComponents,
                "TODO ADD EXAMPLE HERE",
                "   Add New Store: Usage    ",
                "   Add New Store: Example  ",
                DIRECTION_SEPARATOR);
    }

    @Override
    public void display() {
        String[] menuOptions = {
                "# 1 Add Store                       #",
                "# 2 Go back                         #"
        };

        UIHelpers.displayMenu(
                " Admin: Add New Store Menu ",
                menuOptions,
                MENU_SEPARATOR);
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                if (save())
                    System.out.println("Added Successfully");
                else
                    System.out.println("Failed adding a new User");
                goBack();
                break;
            case 2:
                goBack();
                break;
            default:
                System.out.println("Invalid selection.");
                break;
        }
    }

    @Override
    public void goBack() {
        new Administration().run();
    }

    private boolean save() {
        boolean valid = false;
        String storeQuery = "insert into ServiceCenters (centerId, minWage, maxWage, address, phone, satOpen, isActive)"
                + " values (?, ?, ?, ?, ?, ?, ?)";

        try {
            DbConnection db = new DbConnection();
            try {
                PreparedStatement preStmt = db.getConnection().prepareStatement(storeQuery);
                preStmt.setInt(1, centerId);
                preStmt.setDouble(2, minWage);
                preStmt.setDouble(3, maxWage);
                preStmt.setString(4, address);
                preStmt.setString(5, "1234567899");
                preStmt.setInt(6, 0);
                preStmt.setInt(7, 1);
                int result2 = preStmt.executeUpdate();
                if (result2 > 0)
                    valid = true;
            } finally {
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean result = userRepo.add(manager);
        if (result)
            valid = true;

        return valid;
    }

    private boolean validateInput(int storeId, String address, String managerInfo, String minMax) {
        boolean valid = false;

        if (storeId < 1)
            return valid;
        centerId = storeId;
        if (address.isEmpty())
            return valid;
        this.address = address;
        String[] minMaxWages = minMax.split(";");
        if (minMaxWages.length != 2)
            return valid;
        minWage = Double.parseDouble(minMaxWages[0]);
        maxWage = Double.parseDouble(minMaxWages[1]);
        manager = new User();
        String[] inputs = managerInfo.split(";");
        if (inputs.length != 10)
            return valid;

        manager.setUserId(Integer.parseInt(inputs[0]));
        manager.setServiceCenterId(centerId);
        manager.setFirstName(inputs[1].trim());
        manager.setLastName(inputs[2].trim());
        manager.setUsername(inputs[3].trim());
        manager.setPassword(inputs[4].trim());
        manager.setAddress(inputs[5].trim());
        manager.setEmail(inputs[6].trim());
        manager.setPhone(inputs[7].trim());
        manager.setRole(inputs[8].trim());
        manager.setSalaryOrWage(Double.parseDouble(inputs[9]));
        return true;
    }
}
