package AutoCenter.customer;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;
import AutoCenter.UIHelpers;
import AutoCenter.services.UserService;

public class Customer implements UserFlowFunctionality {

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
    private static final String MENU_SEPARATOR = "##################################";

    private UserService userService = null;

    public Customer() {
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
    public void navigate(final int selection) {
        switch (selection) {
            case 1:
                new ViewAndUpdateProfile().run();
                break;
            case 2:
                new ViewAndScheduleService().run();
                break;
            case 3:
                new Invoices().run();
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

    @Override
    public void display() {
        String[] menuOptions = {
                "# 1 View and Update Profile      #",
                "# 2 View and Schedule Service    #",
                "# 3 Invoices                     #",
                "# 4 Logout                       #"
        };

        UIHelpers.displayMenu(" Customer: Landing Page ", menuOptions, MENU_SEPARATOR);
    }
}
