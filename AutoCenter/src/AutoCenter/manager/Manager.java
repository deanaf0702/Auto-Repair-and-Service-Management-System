package AutoCenter.manager;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.services.UserService;

public class Manager implements Interface {

    private static int INITIAL_SELECTION = 3;
    private static int MIN_SELECTION = 1;
    private static int MAX_SELECTION = 3;
    private UserService userService = null;

    public Manager() {
        userService = new UserService();
    }

    public void run() {
        int selection = INITIAL_SELECTION;
        display();
        do {
            System.out.print("Enter choice (1-3) from the given options displayed above: ");
            selection = ScanHelper.nextInt();
        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));

        navigate(selection);
    }

    public void navigate(int selection) {
        switch (selection) {
            case 1:
                new SetupStore().run();
                break;
            case 2:
                new AddNewEmployee().run();
                break;
            case 3:
                goBack();
                break;
        }
    }

    @Override
    public void display() {
        System.out.println("#################################");
        System.out.println("##### Manager: Landing Page #####");
        System.out.println("#################################");
        System.out.println("# 1 Setup Store                 #");
        System.out.println("# 2 Add New Employee            #");
        System.out.println("# 3 Logout                      #");
        System.out.println("#################################");
    }

    @Override
    public void goBack() {
        userService.logout();
    }
}
