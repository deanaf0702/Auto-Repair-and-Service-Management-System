package AutoCenter.administrator;

import AutoCenter.UIHelpers;
import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;
import AutoCenter.services.UserService;

/**
 * Handles functionality for the landing page of
 * administrators including display, input, and output.
 *
 * @author jkersey
 */
public class Administration implements UserFlowFunctionality {

    /*
     * The minimum selection for the menu options range
     */
    private static final int MIN_SELECTION = 1;

    /*
     * The maximum selection for the menu options range
     */
    private static final int MAX_SELECTION = 4;

    /*
     * The separator to use between the menu title and the options
     */
    private static final String MENU_SEPARATOR = "###############################";

    private UserService userService = null;

    public Administration() {
        userService = new UserService();
    }

    @Override
    public void run() {
        int selection;
        display();
        do {
            System.out.print("Enter choice (" + MIN_SELECTION + "-" + MAX_SELECTION
                    + ") from the given options displayed above: ");
            selection = ScanHelper.nextInt();
        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));

        navigate(selection);
    }

    @Override
    public void display() {
        String[] menuOptions = {
                "# 1 System Set Up             #",
                "# 2 Add New Store             #",
                "# 3 Add New Service           #",
                "# 4 Logout                    #"
        };

        UIHelpers.displayMenu(" Admin: Landing Page ", menuOptions, MENU_SEPARATOR);
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                new SystemSetUp().run();
                break;
            case 2:
                new AddNewStore().run();
                break;
            case 3:
                new AddNewService().run();
                break;
            case 4:
                goBack();
                break;
            default:
                System.out.println("Invalid selection.");
                break;
        }
    }

    @Override
    public void goBack() {
        userService.logout();
    }
}
