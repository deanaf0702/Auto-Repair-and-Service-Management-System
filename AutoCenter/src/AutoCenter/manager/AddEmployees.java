package AutoCenter.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;
import AutoCenter.models.User;
import AutoCenter.repository.UserRepository;
import AutoCenter.services.UserService;

public class AddEmployees implements UserFlowFunctionality {

	private UserService userService = null;
    private UserRepository userRepo = null;
    private User user = null;
    private static final String DIRECTION_SEPARATOR = "#############################";
    private static final String MENU_SEPARATOR = "#######################################";
    private static final int EXPECTED_INPUT_LENGTH = 10;
    private static final int MIN_SELECTION = 1;
    private static final int MAX_SELECTION = 2;

    public AddEmployees() {
        userRepo = new UserRepository();
        userService = new UserService();
    }

    public void run() {
        int selection;

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
            //System.out.print("F. Address :");
            user.setAddress("1234 Main St, Raleigh, NC 27606-2972");
            System.out.print("G. EmilAddress :");
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
        System.out.println("# A. Employee ID             #");
        System.out.println("# B. First Name              #");
        System.out.println("# C. Last Name               #");
        System.out.println("# D. userName                #");
        System.out.println("# E. Password                #");
        System.out.println("# F. Address                 #");
        System.out.println("# G. EmilAddress             #");
        System.out.println("# H. Phone Number            #");
        System.out.println("# I. Role                    #");
        System.out.println("# J. Compensation($)         #");
        System.out.println(DIRECTION_SEPARATOR);
        System.out.println();
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
                if (result)
                {
                	System.out.println("Added Successfully!!");
                	System.out.println("Do you want to keep adding an employee (Yes: 1, No: 2)?");
                	int select = ScanHelper.nextInt();
                	if(select == 1) run();
                	else goBack();
                }
                    
                run();
                break;
            case 2:
                goBack();
                break;
            default:
                run();
                break;
        }
    }

    @Override
    public void goBack() {
        new SetupStore().run();

    }

}
