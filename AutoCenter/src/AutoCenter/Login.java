package AutoCenter;

import AutoCenter.administrator.Administration;
import AutoCenter.customer.Customer;
import AutoCenter.manager.Manager;
import AutoCenter.mechanic.Mechanic;
import AutoCenter.models.User;
import AutoCenter.receptionist.Receptionist;
import AutoCenter.services.UserService;

public class Login implements UserFlowFunctionality {

    private static int INITIAL_SELECTION = 2;
    private static int MIN_SELECTION = 1;
    private static int MAX_SELECTION = 2;
    private String userId = null;
    private String password = null;

    public Login() {
    }

    public void run() {
        int selection = INITIAL_SELECTION;
        display();
        do {
            System.out.println("Enter UserID: ");
            userId = ScanHelper.next();
            System.out.println("Enter password: ");
            password = ScanHelper.next();
            System.out.print("Enter choice (1-2) from the given options displayed above: ");
            selection = ScanHelper.nextInt();
        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));

        navigate(selection);
    }

    public void display() {
        System.out.println("################################");
        System.out.println("#####   User: Login Menu   #####");
        System.out.println("################################");
        System.out.println("# 1 Sign-In                    #");
        System.out.println("# 2 Go Back                    #");
        System.out.println("################################");
    }

    public void navigate(int value) {
        switch (value) {
            case 1:
                signIn(userId, password);
                break;
            case 2:
                goBack();
                break;
        }
    }

    public void signIn(String userId, String password) {
        UserService userService = new UserService();
        User user = userService.authenticate(userId, password);
        if (user == null) {
            System.out.print("User not found!");
            runAgain();
        } else {
            System.out.println(user.getRole() + ": " + user.getFirstName() + " " + user.getLastName());

            Home.setUser(user);
            String role = user.getRole();
            if ("Manager".equals(role))
                new Manager().run();
            else if ("Receptionist".equals(role))
                new Receptionist().run();
            else if ("Mechanic".equals(role))
                new Mechanic().run();
            else if ("Customer".equals(role))
                new Customer().run();
            else {
                System.out.print("User not found!");
                runAgain();
            }
        }
    }

    public void runAgain() {
        System.out.println("Went wrong;");
        run();
    }

    public void goBack() {
        Home.run();
    }
}
