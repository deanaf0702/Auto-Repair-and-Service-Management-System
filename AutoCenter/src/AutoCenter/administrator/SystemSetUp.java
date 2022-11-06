package AutoCenter.administrator;

import AutoCenter.UIHelpers;
import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;

/**
 * Handles functionality for the System Set Up menu of
 * administrators including display, input, and output.
 *
 * @author jkersey, tsenck12, deoks
 */
public class SystemSetUp implements UserFlowFunctionality {

    /*
     * The minimum selection for the menu options range
     */
    private static final int MIN_SELECTION = 1;

    /*
     * The maximum selection for the menu options range
     */
    private static final int MAX_SELECTION = 3;

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
                "# A. Input file that contains the   #\n" +
                        "#    the service general            #\n" +
                        "#    information                    #",
                "# B. Input file that contains the   #\n" +
                        "#    store general                  #\n" +
                        "#    information                    #"
        };

        // TODO add example here
        UIHelpers.displayUsageDirections(
                usageComponents,
                "TODO ADD EXAMPLE HERE",
                "   System Set Up: Usage    ",
                "   System Set Up: Example  ",
                DIRECTION_SEPARATOR);
    }

    @Override
    public void display() {
        String[] menuOptions = {
                "# 1 Upload service general          #\n" +
                        "#   general information             #",
                "# 2 Upload store general            #\n" +
                        "#   information                     #",
                "# 3 Go back                         #"
        };

        UIHelpers.displayMenu(" Admin: System Set Up Menu ", menuOptions, MENU_SEPARATOR);
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                new SystemSetUp().run();
                break;
            case 2:
                new SystemSetUp().run();
                break;
            case 3:
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
