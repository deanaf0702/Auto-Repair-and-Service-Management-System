package AutoCenter.mechanic;

import AutoCenter.Interface;

/**
 * Handles functionality for accept reject swap menu of mechanics including
 * display, input, and
 * output.
 *
 * @author jkersey
 */
public class AcceptRejectSwap implements Interface {

    private static int INITIAL_SELECTION = 0;
    private static int MIN_SELECTION = 1;
    private static int MAX_SELECTION = 2;

    @Override
    public void run() {
        int selection = INITIAL_SELECTION;
        display();
        do {
            displayDirection();
            System.out.print("Enter choice (1-2) from the given options displayed above: ");
            selection = ScanHelper.nextInt();
        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));
        navigate(selection);
    }

    public void displayDirection() {
        System.out.println("#############################");
        System.out.println("######      Usage      ######");
        System.out.println("#############################");
        System.out.println("# A. RequestID              #");
        System.out.println("# ------------------------- #");
        System.out.println("# B. The requesting         #");
        System.out.println("# mechanic's name           #");
        System.out.println("# ------------------------- #");
        System.out.println("# C. Timeslot range         #");
        System.out.println("# requested                 #");
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
        System.out.println("#####   Mechanic: Accept / Reject   #####");
        System.out.println("#######         Swap Menu         #######");
        System.out.println("#########################################");
        System.out.println("# 1 Manage swap requests                #");
        System.out.println("# 2 Go Back                             #");
        System.out.println("#########################################");
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
        }
    }

    @Override
    public void goBack() {
        new Mechanic().run();
    }
}
