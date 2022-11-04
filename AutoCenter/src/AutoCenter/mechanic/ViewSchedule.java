package AutoCenter.mechanic;

import AutoCenter.Interface;

/**
 * Handles functionality for view schedule menu of mechanics including display,
 * input, and output.
 *
 * @author jkersey
 */
public class ViewSchedule implements Interface {

    private static int INITIAL_SELECTION = 0;
    private static int MIN_SELECTION = 1;
    private static int MAX_SELECTION = 1;

    @Override
    public void run() {
        int selection = INITIAL_SELECTION;
        display();
        do {
            System.out.print("Enter choice (1) from the given options displayed above: ");
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
        switch (selection) {
            case 1:
                goBack();
                break;
        }
    }

    @Override
    public void goBack() {
        new Mechanic().run();
    }
}
