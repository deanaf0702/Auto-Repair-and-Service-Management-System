package AutoCenter.mechanic;

import AutoCenter.Interface;

/**
 * Handles functionality for request time off menu of mechanics including
 * display, input, and
 * output.
 *
 * @author jkersey
 */
public class RequestTimeOff implements Interface {

    @Override
    public void run() {
        do {
            displayDirection();
            reset();
            String input = ScanHelper.nextLine();
            String[] inputs = input.split(";");
            if (inputs.length >= EXPECTED_INPUT_LENGTH) {
                ABCPriceTier[0] = Integer.parseInt(inputs[0]);
                ABCPriceTier[1] = Integer.parseInt(inputs[1]);
                ABCPriceTier[2] = Integer.parseInt(inputs[2]);
                System.out.print("Enter choice (1-2) from the given options displayed above: ");
                selection = ScanHelper.nextInt();
            } else {
                System.out.println(
                        "Something went wrong. Please try again and make sure you provide all three input"
                                + " prices.");
                selection = 0;
            }
        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));
        navigate(selection);
    }

    public void reset() {
        // TODO
        // ABCPriceTier = new Integer[3];
    }

    public void displayDirection() {
        System.out.println("#############################");
        System.out.println("######      Usage      ######");
        System.out.println("#############################");
        System.out.println("# A. Time slots mechanic    #");
        System.out.println("# wants to be off           #");
        System.out.println("# (indicated by week, day,  #");
        System.out.println("# time slot start and end   #");
        System.out.println("# slot ids)                 #");
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
        System.out.println("######################################");
        System.out.println("#####   Manager: Request Time    #####");
        System.out.println("#######        Off Menu        #######");
        System.out.println("######################################");
        System.out.println("# 1 Send the request                 #");
        System.out.println("# 2 Go Back                          #");
        System.out.println("######################################");
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                new RequestTimeOff().run();
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
