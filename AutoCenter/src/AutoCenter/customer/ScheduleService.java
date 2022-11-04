package AutoCenter.customer;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;

public class ScheduleService implements Interface {

    @Override
    public void run() {
        int selection = 0;
        display();
        do {
            System.out.print("Enter choice (1-4) from the given options displayed above: ");
            selection = ScanHelper.nextInt();
        } while (!(selection >= 1 && selection <= 4));

        navigate(selection);
    }

    @Override
    public void display() {
        System.out.println("##########################################");
        System.out.println("##### Customer Schedule Service Menu #####");
        System.out.println("##########################################");
        System.out.println("# 1 Add Schedule Maintenance             #");
        System.out.println("# 2 Add Schedule Repair                  #");
        System.out.println("# 3 View cart and select schedule time   #");
        System.out.println("# 4 Go Back                              #");
        System.out.println("##########################################");
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                new AddScheduleMaintenance().run();
                break;
            case 2:
                new AddScheduleRepair().run();
                break;
            case 3:
                new ViewCartandSelectScheduleTime().run();
                break;
            case 5:
                goBack();
        }
    }

    @Override
    public void goBack() {
        new ScheduleService().run();
    }
}
