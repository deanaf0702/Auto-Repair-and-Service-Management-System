package AutoCenter.administrator;

import AutoCenter.UIHelpers;
import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;

public class SystemSetUp implements UserFlowFunctionality {

    private static final int MIN_SELECTION = 1;
    private static final int MAX_SELECTION = 3;

    private static final String MENU_SEPARATOR = "#####################################";
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
        System.out.println(DIRECTION_SEPARATOR);
        System.out.println("#####   System Set Up: Usage    #####");
        System.out.println(DIRECTION_SEPARATOR);
        System.out.println("# A. Input file that contains the   #");
        System.out.println("#    the service general            #");
        System.out.println("#    information                    #");
        System.out.println("# --------------------------------- #");
        System.out.println("# B. Input file that contains the   #");
        System.out.println("#    store general                  #");
        System.out.println("#    information                    #");
        System.out.println(DIRECTION_SEPARATOR);
        System.out.println("#####   System Set Up: Example  #####");
        System.out.println(DIRECTION_SEPARATOR);
        // TODO add example here
        System.out.println(DIRECTION_SEPARATOR);
        UIHelpers.displayUsageDirections();
    }

    @Override
    public void display() {
        String[] menuOptions = {
                "# 1 Upload service general          #\n#   general information             #",
                "# 2 Upload store general            #\n#   information                     #",
                "# 3 Go back                         #"
        };

        UIHelpers.displayMenu("Admin: System Set Up Menu", menuOptions, MENU_SEPARATOR);
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
