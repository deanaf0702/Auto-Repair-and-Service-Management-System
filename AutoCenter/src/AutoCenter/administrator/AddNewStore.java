package AutoCenter.administrator;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.Helpers;

public class AddNewStore implements Interface {

    private static final int MIN_SELECTION = 1;
    private static final int MAX_SELECTION = 2;

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
        System.out.println("#####   Add New Store: Usage    #####");
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
        Helpers.displayUsageDirections();
    }

    @Override
    public void display() {
        String[] menuOptions = {
                "# 1 Add Store                       #",
                "# 2 Go back                         #"
        };

        Helpers.displayMenu("Admin: Add New Store Menu", menuOptions, MENU_SEPARATOR);
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
