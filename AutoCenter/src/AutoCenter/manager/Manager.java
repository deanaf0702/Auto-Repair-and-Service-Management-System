package AutoCenter.manager;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.services.UserService;

public class Manager implements Interface {

    private static final int MIN_SELECTION = 1;
    private static final int MAX_SELECTION = 3;
    private static final String MENU_SEPARATOR = "#################################";

    private UserService userService;

    public Manager() {
        userService = new UserService();
    }

    public void run() {
        int selection;

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
            default:
                System.out.println("Invalid selection");
                break;
        }
    }

    @Override
    public void display() {
        System.out.println(MENU_SEPARATOR);
        System.out.println("##### Manager: Landing Page #####");
        System.out.println(MENU_SEPARATOR);
        System.out.println("# 1 Setup Store                 #");
        System.out.println("# 2 Add New Employee            #");
        System.out.println("# 3 Logout                      #");
        System.out.println(MENU_SEPARATOR);
    }

    @Override
    public void goBack() {
        userService.logout();
    }
}
