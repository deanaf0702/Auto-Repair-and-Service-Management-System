package AutoCenter.administrator;

import AutoCenter.UIHelpers;
import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;

public class AddNewService implements UserFlowFunctionality {

    private static final int MIN_SELECTION = 1;
    private static final int MAX_SELECTION = 3;

    private static final String MENU_SEPARATOR = "#####################################";

    @Override
    public void run() {
        // TODO Auto-generated method stub
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
    }

    @Override
    public void display() {
        String[] menuOptions = {
                "# 1 Add Service                     #",
                "# 2 Go back                         #"
        };

        UIHelpers.displayMenu("Admin: System Set Up Menu", menuOptions, MENU_SEPARATOR);
    }

    @Override
    public void navigate(int selection) {
        // TODO Auto-generated method stub

    }

    @Override
    public void goBack() {
        // TODO Auto-generated method stub

    }
}
