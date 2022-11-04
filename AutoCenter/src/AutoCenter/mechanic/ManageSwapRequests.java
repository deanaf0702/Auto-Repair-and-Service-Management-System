package AutoCenter.mechanic;

import AutoCenter.Interface;

/**
 * Handles functionality for manage swap requests menu of mechanics including
 * display, input, and
 * output.
 *
 * @author jkersey
 */
public class ManageSwapRequests implements Interface {

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

    public void displayDirection() {
        System.out.println("#############################");
        System.out.println("######      Usage      ######");
        System.out.println("#############################");
        System.out.println("# A. The requestID          #");
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
        System.out.println("#####     Mechanic: Manage Swap     #####");
        System.out.println("#######       Requests Menu       #######");
        System.out.println("#########################################");
        System.out.println("# 1 Accept swap                         #");
        System.out.println("# 2 Reject swap                         #");
        System.out.println("# 3 Go Back                             #");
        System.out.println("#########################################");
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
