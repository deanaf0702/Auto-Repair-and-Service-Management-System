package AutoCenter.mechanic;

import AutoCenter.UIHelpers;
import AutoCenter.UserFlowFunctionality;

/**
 * Handles functionality for manage swap requests menu of mechanics including
 * display, input, and
 * output.
 *
 * @author jkersey
 */
public class ManageSwapRequests implements UserFlowFunctionality {

    /**
     * The minimum selection for the menu options range
     */
    private static final int MIN_SELECTION = 1;

    /**
     * The maximum selection for the menu options range
     */
    private static final int MAX_SELECTION = 2;

    /**
     * The separator to use between the menu title and the options
     */
    private static final String MENU_SEPARATOR = "#######################################";

    /**
     * The separator to use between the directions components
     */
    private static final String DIRECTION_SEPARATOR = "#############################";

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

    public void displayDirection() {
        System.out.println("#############################");
        System.out.println("######      Usage      ######");
        System.out.println("#############################");
        System.out.println("# A. The requestID          #");
        System.out.println("#############################");
        System.out.println("#####      Example     ######");
        System.out.println("#############################");
        // TODO rewrite here
        System.out.println("##         6; 7; 8        ###");
        System.out.println("#############################");
        System.out.println();
        System.out.println("NOTE: It's important to enter information following");
        System.out.println("the example provided above using the delimiter, `;`");
        System.out.println();
    }

    @Override
    public void display() {
        System.out.println("#########################################");
        System.out.println("#####     Mechanic: Manage Swap     #####");
        System.out.println("#######       Requests Menu       #######");
        System.out.println("#########################################");
        System.out.println("# 1 Accept swap                         #");
        System.out.println("# 2 Reject swap                         #");
        System.out.println("# 3 Go Back                             #");
        System.out.println("#########################################");
        String[] menuOptions = {
                "# 1 Send the request                  #",
                "# 2 Go Back                           #",
        };

        UIHelpers.displayMenu(" Mechanic: Manage Swap Requests Menu ", menuOptions, MENU_SEPARATOR);
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                acceptSwap();
                new AcceptRejectSwap().run();
                break;
            case 2:
                rejectSwap();
                new AcceptRejectSwap().run();
                break;
            case 3:
                goBack();
                break;
            default:
                System.out.println("Invalid selection.");
                break;
        }
    }

    private void rejectSwap() {
    }

    private void acceptSwap() {
    }

    @Override
    public void goBack() {
        // TODO Auto-generated method stub

    }
}
