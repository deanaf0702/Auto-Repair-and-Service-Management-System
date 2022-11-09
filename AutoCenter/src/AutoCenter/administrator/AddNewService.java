package AutoCenter.administrator;

import AutoCenter.UIHelpers;
import AutoCenter.UserFlowFunctionality;
import AutoCenter.services.RepositoryService;

import java.util.HashMap;

import AutoCenter.ScanHelper;

/**
 * Handles functionality for add new service menu of
 * administrators including display, input, and output.
 *
 * @author jkersey, tsenck12, deoks
 */
public class AddNewService implements UserFlowFunctionality {

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
    private static final String MENU_SEPARATOR = "#######################################";

    /*
     * The separator to use between the usage header and example header
     */
    private static final String DIRECTION_SEPARATOR = "#######################################";

    private HashMap<Integer, String>categories; 
    private RepositoryService repoService;
    private String serviceName = null;
    private int hours = 0;
    private String category = null;
    public AddNewService()
    {
    	repoService = new RepositoryService();
    	categories = repoService.RepairCategoryLookup();
    }
    @Override
    public void run() {
        int selection = MAX_SELECTION;
        display();

        do {
            System.out.println("Enter existing service category: ");
            category = ScanHelper.nextLine();
            if(!categories.containsValue(category))
            {
            	System.out.println("The category does not exist.");
            	selection = 0;
            }else {
            	System.out.print("Enter Service Name: ");
                serviceName = ScanHelper.next();
                System.out.print("Enter Service Duration: ");
                hours = ScanHelper.nextInt();
                System.out.print("Enter choice (" + MIN_SELECTION + "-" + MAX_SELECTION
                        + ") from the given options displayed above: ");
                selection = ScanHelper.nextInt();
            }
            
        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));

        
        navigate(selection);
    }

    // "#######################################"
    // "#####    Add New Service: Usage   #####"
    // "#######################################"
    // "# A. Enter existing service category  #"
    // "# (raise error if category doesn't    #"
    // "# exist)                              #"
    // "# ----------------------------------- #"
    // "# B. Service Name                     #"
    // "# ----------------------------------- #"
    // "# C. Duration of the service          #"
    // "#######################################"
    // "#####   Add New Service: Example  #####"
    // "#######################################"
    // "# TODO add example here               #"
    // "#######################################"
    private void displayDirections() {
        String[] usageComponents = {
                "# A. Enter existing service category  #\n" +
                        "#    (raise error if category doesn't #\n" +
                        "#    exist)                           #",
                "# B. Service Name                     #",
                "# C. Duration of the service          #"
        };

        // TODO add example here
        UIHelpers.displayUsageDirections(
                usageComponents,
                "TODO ADD EXAMPLE HERE",
                "    Add New Service: Usage   ",
                "   Add New Service: Example  ",
                DIRECTION_SEPARATOR);
    }

    // "#######################################"
    // "##### Admin: Add New Service Menu #####"
    // "#######################################"
    // "# 1 Add Service #"
    // "# 2 Go back #"
    // "#######################################"
    @Override
    public void display() {
        String[] menuOptions = {
                "# 1 Add Service                       #",
                "# 2 Go back                           #"
        };

        UIHelpers.displayMenu(" Admin: Add New Service Menu ", menuOptions, MENU_SEPARATOR);
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                	if(repoService.addService(serviceName, category))
                	{
                		System.out.println("The Service is Added Successfully!");
                	}else {
                		System.out.println("Failed adding the service.");
                	}
                	goBack();
                break;
            case 2:
                goBack();
                break;
            default:
                System.out.println("Invalid selection.");
                goBack();
                break;
        }
    }

    @Override
    public void goBack() {
        new Administration().run();
    }
}
