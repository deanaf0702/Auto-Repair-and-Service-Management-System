package AutoCenter.mechanic;

import AutoCenter.Interface;

/**
 * Handles functionality for view schedule menu of mechanics including display,
 * input, and output.
 *
 * @author jkersey
 */
public class ViewSchedule implements Interface {

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

    public void displayDirection() {
        System.out.println("#############################");
        System.out.println("######      Usage      ######");
        System.out.println("#############################");
        System.out.println("# A. The list of time slots #");
        System.out.println("# slots when mechanic is    #");
        System.out.println("# booked for the service    #");
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
        System.out.println("###################################");
        System.out.println("##### Mechanic: View Schedule #####");
        System.out.println("###################################");
        System.out.println("# 1 Go back                       #");
        System.out.println("###################################");
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
