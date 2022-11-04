package AutoCenter.customer;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.services.UserService;

public class Customer implements Interface {

    private UserService userservice = null;

    public Customer() {
        userservice = new UserService();
    }

    public void run() {
        int selection = 4;
        display();
        do {
            System.out.print("Enter choice (1-4) from given options displayed above: ");
            selection = ScanHelper.nextInt();
        } while (!(selection >= 1 && selection <= 4));

        navigate(selection);
    }

    @Override
    public void display() {
        System.out.println("#################################");
        System.out.println("##### Customer Landing Page #####");
        System.out.println("#################################");
        System.out.println("# 1 View and Update Profile     #");
        System.out.println("# 2 View and Schedule Service   #");
        System.out.println("# 3 Invoices                    #");
        System.out.println("# 4 Logout                      #");
        System.out.println("#################################");
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                new ViewAndUpdateProfile().run();
                break;
            case 2:
                new ViewAndScheduleService().run();
                break;
            case 3:
                new Invoices().run();
                break;
            case 4:
                goBack();
                break;
        }
    }

    @Override
    public void goBack() {
        userservice.logout();
    }
}
