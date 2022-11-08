package AutoCenter.mechanic;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;
import AutoCenter.UIHelpers;
import AutoCenter.services.UserService;

/**
 * Handles functionality for landing page of mechanics including display, input,
 * and output.
 *
 * @author jkersey
 */
public class Mechanic implements UserFlowFunctionality {

    /**
     * The minimum selection for the menu options range
     */
    private static final int MIN_SELECTION = 1;

    /**
     * The maximum selection for the menu options range
     */
    private static final int MAX_SELECTION = 5;

    /**
     * The user service to use for a mechanic
     */
    private UserService userService = null;

    /**
     * The separator to use between the menu title and the options
     */
    private static final String MENU_SEPARATOR = "##################################";

    /**
     * Instantiates a new mechanic with a new user service.
     */
    public Mechanic() {
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
                "# 1 View schedule                #",
                "# 2 Request time off             #",
                "# 3 Request swap                 #",
                "# 4 Accept / Reject swap         #",
                "# 5 Logout                       #",
        };

        UIHelpers.displayMenu(" Mechanic: Landing Page ", menuOptions, MENU_SEPARATOR);
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                new ViewSchedule().run();
                break;
            case 2:
                new RequestTimeOff().run();
                break;
            case 3:
                new RequestSwap().run();
                break;
            case 4:
                new AcceptRejectSwap().run();
                break;
            case 5:
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
