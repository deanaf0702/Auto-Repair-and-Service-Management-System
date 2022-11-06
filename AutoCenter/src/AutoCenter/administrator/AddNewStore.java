package AutoCenter.administrator;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;
import AutoCenter.UIHelpers;

/**
 * Handles functionality for add new store menu of
 * administrators including display, input, and output.
 *
 * @author jkersey
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

    @Override
    public void run() {
        int selection;
        display();

        do {
            displayDirections();
            // TODO add file parsing here
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
                "# C. Manager’s information (first   #\n" +
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
                new AddNewStore().run();
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
}
