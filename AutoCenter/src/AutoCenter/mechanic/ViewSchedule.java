package AutoCenter.mechanic;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;

/**
 * Handles functionality for view schedule menu of mechanics including display,
 * input, and output.
 *
 * @author jkersey
 */
public class ViewSchedule implements UserFlowFunctionality {

    private static final int MIN_SELECTION = 1;
    private static final int MAX_SELECTION = 1;

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

    public void displayDetails() {
        // TODO list of time slots when mechanic is booked for service
    }

    @Override
    public void display() {
        System.out.println("###################################");
        System.out.println("##### Mechanic: View Schedule #####");
        System.out.println("###################################");
        System.out.println("# 1 Go back                       #");
        System.out.println("###################################");
    }

    @Override
    public void navigate(int selection) {
        if (selection == 1) {
            goBack();
        } else {
            System.out.println("Invalid selection.");
        }
    }

    @Override
    public void goBack() {
        new Mechanic().run();
    }
}
