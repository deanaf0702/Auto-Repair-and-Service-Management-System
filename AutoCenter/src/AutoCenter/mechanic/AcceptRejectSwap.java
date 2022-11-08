package AutoCenter.mechanic;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;
import AutoCenter.UIHelpers;

/**
 * Handles functionality for accept reject swap menu of mechanics including
 * display, input, and
 * output.
 *
 * @author jkersey
 */
public class AcceptRejectSwap implements UserFlowFunctionality {

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
    private static final String MENU_SEPARATOR = "###############################################";

    /**
     * The separator to use between the usage header and example header
     */
    private static final String DIRECTION_SEPARATOR = "#############################";

    @Override
    public void run() {
        int selection;
        display();
        do {
            displayDirection();
            System.out.print("Enter choice (" + MIN_SELECTION + "-" + MAX_SELECTION
                    + ") from the given options displayed above: ");
            selection = ScanHelper.nextInt();
        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));
        navigate(selection);
    }

    public void displayDirection() {
        System.out.println(DIRECTION_SEPARATOR);
        System.out.println("######      Usage      ######");
        System.out.println(DIRECTION_SEPARATOR);
        System.out.println("# A. RequestID              #");
        System.out.println("# ------------------------- #");
        System.out.println("# B. The requesting         #");
        System.out.println("# mechanic's name           #");
        System.out.println("# ------------------------- #");
        System.out.println("# C. Timeslot range         #");
        System.out.println("# requested                 #");
        System.out.println(DIRECTION_SEPARATOR);
        System.out.println("#####      Example     ######");
        System.out.println(DIRECTION_SEPARATOR);
        // TODO rewrite here
        System.out.println("##         6; 7; 8        ###");
        System.out.println(DIRECTION_SEPARATOR);
        System.out.println();
        System.out.println("NOTE: It's important to enter information following");
        System.out.println("the example provided above using the delimiter, `;`");
        System.out.println();
    }

    @Override
    public void display() {
        String[] menuOptions = {
                "# 1 Manage swap requests                      #",
                "# 2 Go Back                                   #",
        };

        UIHelpers.displayMenu(" Mechanic: Accept / Reject Swap Menu ", menuOptions, MENU_SEPARATOR);
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                new ManageSwapRequests().run();
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
        new Mechanic().run();
    }
}
