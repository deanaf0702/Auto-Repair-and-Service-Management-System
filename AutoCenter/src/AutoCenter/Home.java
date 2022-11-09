package AutoCenter;

/**
 * Handles functionality for Home menu of users including display, input, and
 * output.
 *
 * @author jkersey, tsenck12, deoks
 */
public class Home {

    /*
     * The minimum selection for the menu options range
     */
    private static final int    MIN_SELECTION  = 1;

    /*
     * The maximum selection for the menu options range
     */
    private static final int    MAX_SELECTION  = 2;
    public static String        jdbcUrl;
    public static String        username;
    public static String        password;

    /*
     * The separator to use between the menu title and the options
     */
    private static final String MENU_SEPARATOR = "###############################";

    private static LoginUser    user           = null;

    public static void main ( final String[] args ) {
        System.out.print( "JDBC URL: " );
        jdbcUrl = ScanHelper.next();
        System.out.print( "Database username: " );
        username = ScanHelper.next();
        System.out.print( "Database password: " );
        password = ScanHelper.next();
        run();
    }

    public static void run () {
        int selection = MAX_SELECTION;
        display();
        System.out.print(
                "Enter choice (" + MIN_SELECTION + "-" + MAX_SELECTION + ") from the given options displayed above: " );
        selection = ScanHelper.nextInt();
        if ( selection == 0 ) {
            selection = MAX_SELECTION;
        }
        navigate( selection );
    }

    public static void display () {
        final String[] menuOptions = { "# 1 Login                     #", "# 2 Exit                      #" };

        UIHelpers.displayMenu( "   User: Home Menu   ", menuOptions, MENU_SEPARATOR );
    }

    public static void navigate ( final int value ) {
        switch ( value ) {
            case 1:
                login();
                break;
            case 2:
                exit();
                break;
            default:
                System.out.println( "Invalid selection" );
                break;
        }
    }

    public static void exit () {
        System.out.println(
                "Thanks for using Autor Repair and Service Management! We hope to see you again soon." + " Goodbye." );

    }

    public static void login () {
        final Login login = new Login();
        login.run();
    }

    public static void setUser ( final LoginUser appUser ) {
        user = appUser;
    }

    public static LoginUser getUser () {
        return user;
    }
}
