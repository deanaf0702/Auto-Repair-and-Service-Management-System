/** */
package AutoCenter.customer;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;

/**
 * @author deoks
 */
public class ViewAndScheduleService implements Interface {

    public ViewAndScheduleService() {
    }

    @Override
    public void run() {
        int selection = 0;
        display();
        do {
            System.out.print("Enter choice (1-3) from the given options displayed above: ");
            selection = ScanHelper.nextInt();
        } while (!(selection >= 1 && selection <= 3));

        navigate(selection);
    }

    @Override
    public void display() {
        System.out.println("####################################################");
        System.out.println("##### Customer: View and Schedule Service Menu #####");
        System.out.println("####################################################");
        System.out.println("# 1 View Service History                           #");
        System.out.println("# 2 Schedule Service                               #");
        System.out.println("# 3 Go Back                                        #");
        System.out.println("####################################################");
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                new ViewServiceHistory().run();
                break;
            case 2:
                new ScheduleService().run();
                break;
            case 3:
                goBack();
                break;
        }
    }

    @Override
    public void goBack() {
        // TODO Auto-generated method stub

    }
}
