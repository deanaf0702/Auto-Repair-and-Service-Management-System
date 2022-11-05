package AutoCenter.mechanic;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;
import AutoCenter.services.UserService;

/**
 * Handles functionality for landing page of mechanics including display, input,
 * and output.
 *
 * @author jkersey
 */
public class Mechanic implements UserFlowFunctionality {

    private static int INITIAL_SELECTION = 5;
    private static int MIN_SELECTION = 1;
    private static int MAX_SELECTION = 5;
    private UserService userService = null;

    public Mechanic() {
        userService = new UserService();
    }

    @Override
    public void run() {
        int selection = INITIAL_SELECTION;
        display();
        do {
            System.out.print("Enter choice (1-5) from the given options displayed above: ");
            selection = ScanHelper.nextInt();
        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));

        navigate(selection);
    }

    @Override
    public void display() {
        System.out.println("##################################");
        System.out.println("##### Mechanic: Landing Page #####");
        System.out.println("##################################");
        System.out.println("# 1 View schedule                #");
        System.out.println("# 2 Request time off             #");
        System.out.println("# 3 Request swap                 #");
        System.out.println("# 4 Accept / Reject swap         #");
        System.out.println("# 5 Logout                       #");
        System.out.println("##################################");
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                new ViewSchedule().run();
                break;
            case 2:
                new RequestTimeOff().run();
                break;
            case 3:
                new RequestSwap().run();
                break;
            case 4:
                new AcceptRejectSwap().run();
                break;
            case 5:
                goBack();
                break;
        }
    }

    @Override
    public void goBack() {
        userService.logout();
    }
}
