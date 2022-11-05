package AutoCenter.mechanic;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;

/**
 * Handles functionality for request time off menu of mechanics including
 * display, input, and
 * output.
 *
 * @author jkersey
 */
public class RequestTimeOff implements UserFlowFunctionality {

    private static final int INITIAL_SELECTION = 0;
    private static final int MIN_SELECTION = 1;
    private static final int MAX_SELECTION = 2;
    private static final int EXPECTED_INPUT_LENGTH = 4;
    private static final String DIRECTION_SEPARATOR = "#############################";
    private static final String MENU_SEPARATOR = "######################################";

    private Integer[] timeSlotParameters;

    @Override
    public void run() {
        int selection = INITIAL_SELECTION;
        display();

        do {
            displayDirection();
            reset();
            System.out.println(
                    "Please enter time slots for desired time off following the general format described"
                            + " above.");
            String input = ScanHelper.nextLine();
            String[] inputs = input.split(";");
            if (inputs.length >= EXPECTED_INPUT_LENGTH) {
                boolean validTimeSlots = parseAndValidateTimeSlots(inputs);
                if (validTimeSlots) {
                    System.out.print("Enter choice (1-2) from the given options displayed above: ");
                    selection = ScanHelper.nextInt();
                }
            } else {
                System.out.println(
                        "Something went wrong. Please try again and make sure you provide all four input time"
                                + " slot parameters. Take a look at the usage detailed above if you need help.");
                selection = INITIAL_SELECTION;
            }
        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));
        navigate(selection);
    }

    private void reset() {
        timeSlotParameters = new Integer[4];
    }

    private boolean parseAndValidateTimeSlots(String[] inputs) {
        timeSlotParameters[0] = Integer.parseInt(inputs[0]);
        timeSlotParameters[1] = Integer.parseInt(inputs[1]);
        timeSlotParameters[2] = Integer.parseInt(inputs[2]);
        timeSlotParameters[3] = Integer.parseInt(inputs[3]);

        // valid week
        if (timeSlotParameters[0] < 1 || timeSlotParameters[0] > 4) {
            return false;
        }

        // valid day
        if (timeSlotParameters[1] < 1 || timeSlotParameters[1] > 7) {
            return false;
        }

        // valid time slot start
        if (timeSlotParameters[2] < 1 || timeSlotParameters[2] > 9) {
            return false;
        }

        // valid time slot end
        return timeSlotParameters[3] < 1 || timeSlotParameters[3] > 9;
    }

    public void displayDirection() {
        System.out.println(DIRECTION_SEPARATOR);
        System.out.println("######      Usage      ######");
        System.out.println(DIRECTION_SEPARATOR);
        System.out.println("# A. Time slots mechanic    #");
        System.out.println("# wants to be off           #");
        System.out.println("# (indicated by week, day,  #");
        System.out.println("# time slot start and end   #");
        System.out.println("# slot ids)                 #");
        System.out.println(DIRECTION_SEPARATOR);
        System.out.println("#####      Example     ######");
        System.out.println(DIRECTION_SEPARATOR);
        System.out.println("##        1; 3; 6; 9      ###");
        System.out.println(DIRECTION_SEPARATOR);
        System.out.println("## Corresponds to         ###");
        System.out.println("## (week: 1, day 3,       ###");
        System.out.println("## time start: 6,         ###");
        System.out.println("## time end: 9)           ###");
        System.out.println(DIRECTION_SEPARATOR);
        System.out.println();
        System.out.println("NOTE: It's important to enter information following");
        System.out.println("the example provided above using the delimiter, `;`");
        System.out.println();
    }

    @Override
    public void display() {
        System.out.println(MENU_SEPARATOR);
        System.out.println("#####   Manager: Request Time    #####");
        System.out.println("#######        Off Menu        #######");
        System.out.println(MENU_SEPARATOR);
        System.out.println("# 1 Send the request                 #");
        System.out.println("# 2 Go Back                          #");
        System.out.println(MENU_SEPARATOR);
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                processRequestedTimeOff();
                new RequestTimeOff().run();
                break;
            case 2:
                goBack();
                break;
            default:
                System.out.println("Invalid selection. Please try again.");
                new RequestTimeOff().run();
                break;
        }
    }

    private void processRequestedTimeOff() {
        // TODO function stub here
    }

    @Override
    public void goBack() {
        new Mechanic().run();
    }
}
