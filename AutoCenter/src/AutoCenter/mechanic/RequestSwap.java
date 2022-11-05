package AutoCenter.mechanic;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;

/**
 * Handles functionality for request swap menu of mechanics including display,
 * input, and output.
 *
 * @author jkersey
 */
public class RequestSwap implements UserFlowFunctionality {

    private static final int INITIAL_SELECTION = 0;
    private static final int MIN_SELECTION = 1;
    private static final int MAX_SELECTION = 2;
    private static final int EXPECTED_INPUT_LENGTH = 4;
    private static final String DIRECTION_SEPARATOR = "#############################";
    private static final String MENU_SEPARATOR = "#######################################";

    private Integer[] timeSlotParameters;

    @Override
    public void run() {
        int selection;
        display();

        do {
            displayDirection();
            reset();
            String input = ScanHelper.nextLine();
            String[] inputs = input.split(";");
            if (inputs.length >= EXPECTED_INPUT_LENGTH) {
                timeSlotParameters[0] = Integer.parseInt(inputs[0]);
                timeSlotParameters[1] = Integer.parseInt(inputs[1]);
                timeSlotParameters[2] = Integer.parseInt(inputs[2]);
                timeSlotParameters[3] = Integer.parseInt(inputs[3]);
                System.out.print("Enter choice (1-2) from the given options displayed above: ");
                selection = ScanHelper.nextInt();
            } else {
                System.out.println(
                        "Something went wrong. Please try again and make sure you provide all four input time"
                                + " slot parameters. Take a look at the usage detailed above if you need help.");
                selection = INITIAL_SELECTION;
            }
        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));
        navigate(selection);
    }

    public void displayDirection() {
        System.out.println(DIRECTION_SEPARATOR);
        System.out.println("######      Usage      ######");
        System.out.println(DIRECTION_SEPARATOR);
        System.out.println("# A. Timeslot range to swap #");
        System.out.println("# (identified by weekid,    #");
        System.out.println("# dayid, begin slot id,     #");
        System.out.println("# end slot id)              #");
        System.out.println("# ------------------------- #");
        System.out.println("# B. Employee ID of a       #");
        System.out.println("# mechanic that is being    #");
        System.out.println("# requested for swap        #");
        System.out.println("# ------------------------- #");
        System.out.println("# C. Timeslot range of the  #");
        System.out.println("# requested mechanic that   #");
        System.out.println("# is of interest            #");
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

    private void reset() {
    }

    @Override
    public void display() {
        System.out.println(MENU_SEPARATOR);
        System.out.println("##### Mechanic: Request Swap Menu #####");
        System.out.println(MENU_SEPARATOR);
        System.out.println("# 1 Send the request                  #");
        System.out.println("# 2 Go Back                           #");
        System.out.println(MENU_SEPARATOR);
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                new RequestSwap().run();
                break;
            case 2:
                goBack();
                break;
            default:
                System.out.println("Invalid selection. Please try again.");
                new RequestSwap().run();
                break;
        }
    }

    @Override
    public void goBack() {
        new Mechanic().run();
    }
}
