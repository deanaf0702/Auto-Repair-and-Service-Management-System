package AutoCenter;

/**
 * Handles functionality for Home menu of users including display,
 * input, and output.
 *
 * @author jkersey, tsenck12, deoks
 */
public class Home {

    /*
     * The minimum selection for the menu options range
     */
    private static final int MIN_SELECTION = 1;

    /*
     * The maximum selection for the menu options range
     */
    private static final int MAX_SELECTION = 2;

    /*
     * The separator to use between the menu title and the options
     */
    private static final String MENU_SEPARATOR = "###############################";

    private static LoginUser user = null;

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        int selection;
        do {
            display();
            System.out.print("Enter choice (" + MIN_SELECTION + "-" + MAX_SELECTION
                    + ") from the given options displayed above: ");
            selection = ScanHelper.nextInt();

        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));

        navigate(selection);
    }

    public static void display() {
        String[] menuOptions = {
                "# 1 Login                     #",
                "# 2 Exit                      #"
        };

        UIHelpers.displayMenu("   User: Home Menu   ", menuOptions, MENU_SEPARATOR);
    }

    public static void navigate(int value) {
        switch (value) {
            case 1:
                login();
                break;
            case 2:
                exit();
                break;
            default:
                System.out.println("Invalid selection");
                break;
        }
    }

    public static void exit() {
        System.out.println(
                "Thanks for using Autor Repair and Service Management! We hope to see you again soon."
                        + " Goodbye.");
        run();
    }

    public static void login() {
        Login login = new Login();
        login.run();
    }

    public static void setUser(LoginUser appUser) {
        user = appUser;
    }

    public static LoginUser getUser() {
        return user;
    }
}
