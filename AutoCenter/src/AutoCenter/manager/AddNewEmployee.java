package AutoCenter.manager;


import AutoCenter.UserFlowFunctionality;
import AutoCenter.Home;
import AutoCenter.ScanHelper;
import AutoCenter.models.User;
import AutoCenter.repository.UserRepository;
import AutoCenter.services.UserService;

public class AddNewEmployee implements UserFlowFunctionality {

    /*
     * The expected number of arguments for adding a new employee
     */
    private static final int EXPECTED_INPUT_LENGTH = 10;

    /*
     * The minimum selection for the menu options range
     */
    private static final int MIN_SELECTION = 1;

    /*
     * The maximum selection for the menu options range
     */
    private static final int MAX_SELECTION = 2;

    private UserRepository userRepo = null;
    private User user = null;

    private static final String DIRECTION_SEPARATOR = "#############################";

    private static final String MENU_SEPARATOR = "#######################################";

    private UserService userService = null;
    
    public AddNewEmployee() {
        userRepo = new UserRepository();
        userService = new UserService();
    }

    public void run() {
        int selection = -1;
        display();
        do {
        	user = new User();
            user.setServiceCenterId(userService.getCenterId());
            System.out.print("A. Employee ID :");
            user.setUserId(ScanHelper.nextInt());
            System.out.print("B. First Name :");
            user.setFirstName(ScanHelper.next().trim());
            System.out.print("C. Last Name :");
            user.setLastName(ScanHelper.next().trim());
            System.out.print("D. userName :");
            user.setUsername(ScanHelper.next().trim());
            System.out.print("E. Password :");
            user.setPassword(ScanHelper.next().trim());
            System.out.print("F. Address :");
            //user.setAddress("1234 Main St, Raleigh, NC 27606-2972");
            user.setAddress(ScanHelper.nextLine().trim());
            System.out.print("G. Email Address :");
            user.setEmail(ScanHelper.next().trim());
            System.out.print("H. Phone Number :");
            user.setPhone(ScanHelper.next().trim());
            System.out.print("I. Role :");
            user.setRole(ScanHelper.next().trim());
            System.out.print("J. Compensation($) :");
            user.setSalaryOrWage(ScanHelper.nextDouble());
            
            displayMenu();
            System.out.println("Enter choice (1-2) from the given options displayed above:");
            selection = ScanHelper.nextInt();
        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));
        navigate(selection);
    }

    @Override
    public void display() {
        System.out.println("#############################");
        System.out.println("######      Usage      ######");
        System.out.println(DIRECTION_SEPARATOR);
        System.out.println("# A. User ID             #");
        System.out.println("# B. First Name              #");
        System.out.println("# C. Last Name               #");
        System.out.println("# D. UserName                #");
        System.out.println("# E. Password                #");
        System.out.println("# F. Address                 #");
        System.out.println("# G. Email Address            #");
        System.out.println("# H. Phone Number            #");
        System.out.println("# I. Role                    #");
        System.out.println("# J. Compensation($)         #");
        System.out.println(DIRECTION_SEPARATOR);
        
    }

    public void displayMenu() {
        System.out.println(MENU_SEPARATOR);
        System.out.println("##### Manager: Add Employees Menu #####");
        System.out.println(MENU_SEPARATOR);
        System.out.println("# 1 Add                               #");
        System.out.println("# 2 Go Back                           #");
        System.out.println(MENU_SEPARATOR);
    }

    @Override
    public void navigate(int selection) {
        boolean result = false;
        switch (selection) {
            case 1:
                result = userRepo.add(user);
                if (result) {
                    System.out.println("Added Successfully");
                    goBack();
                } else {
                    System.out.println("Failed adding a new User");
                    goBack();
                }
                break;
            case 2:
                goBack();
                break;
            default:
                System.out.println("Invalid selection.");
                break;
        }

    }

    @Override
    public void goBack() {
        new Manager().run();

    }

}
