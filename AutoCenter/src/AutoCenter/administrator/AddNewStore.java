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
            System.out.print("Enter Store ID (ex:30004) ?");
            centerId = ScanHelper.nextInt();
            System.out.println("Enter Store Address (ex:1378 University Woods, Raleigh, NC 27612)?");
            address = ScanHelper.nextLine().trim();
            //String storeAddress = "1378 University Woods, Raleigh, NC 27612";
            System.out.println("Enter Manager's information?");
            manager = new User();
            manager.setServiceCenterId(centerId);
            System.out.print("A. Employee ID :");
            manager.setUserId(ScanHelper.nextInt());
            System.out.print("B. First Name :");
            manager.setFirstName(ScanHelper.next().trim());
            System.out.print("C. Last Name :");
            manager.setLastName(ScanHelper.next().trim());
            System.out.print("D. userName :");
            manager.setUsername(ScanHelper.next().trim());
            System.out.print("E. Password :");
            manager.setPassword(ScanHelper.next().trim());
            // System.out.print("F. Address :");
            manager.setAddress("1234 Main St, Raleigh, NC 27606-2972");
            System.out.print("G. EmilAddress :");
            manager.setEmail(ScanHelper.next().trim());
            System.out.print("H. Phone Number :");
            manager.setPhone(ScanHelper.next().trim());
            System.out.print("I. Role :");
            manager.setRole(ScanHelper.next().trim());
            System.out.print("J. Compensation($) :");
            manager.setSalaryOrWage(ScanHelper.nextDouble());

            System.out.println("Enter minimum wage? ");
            minWage = ScanHelper.nextDouble();
            System.out.println("Enter maximum wage? ");
            maxWage = ScanHelper.nextDouble();
            
            System.out.print("Enter choice (" + MIN_SELECTION + "-" + MAX_SELECTION
                    + ") from the given options displayed above: ");
            selection = ScanHelper.nextInt();

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

    
}
