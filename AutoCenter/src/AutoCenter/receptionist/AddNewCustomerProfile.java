package AutoCenter.receptionist;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;
import AutoCenter.models.User;
import AutoCenter.repository.DbConnection;
import AutoCenter.services.RepositoryService;

public class AddNewCustomerProfile implements UserFlowFunctionality
{
	private RepositoryService repoService = null;
	int inputLength = 11;
	private String userQuery = null;
	private String customerQuery = null;
	private String custVehicleQuery = null;
	private static final String DIRECTION_SEPARATOR = "#############################";
    private static final String MENU_SEPARATOR = "#######################################";
    private static final int EXPECTED_INPUT_LENGTH = 11;
    private static final int MIN_SELECTION = 1;
    private static final int MAX_SELECTION = 2;
    
	public AddNewCustomerProfile()
	{
		repoService = new RepositoryService();
	}
	@Override
	public void run() {
		int selection = MAX_SELECTION;
		display();
		do {
			User user = new User();
            user.setServiceCenterId(repoService.getCenterId());
            System.out.print("A Customer ID :");
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
            System.out.print("G. EmailAddress :");
            user.setEmail(ScanHelper.next().trim());
            System.out.print("H. Phone Number :");
            user.setPhone(ScanHelper.next().trim());
            System.out.print("I. Role :");
            user.setRole(ScanHelper.next().trim());
            System.out.print("J. Vin Number :");
            String vinNumber = ScanHelper.next();
            System.out.print("K. Car manufacturer :");
            String carModel = ScanHelper.next().trim();
            System.out.print("L. Current mileage :");
            int mileage = ScanHelper.nextInt();
            System.out.print("M. Year :");
            int year = ScanHelper.nextInt();
            
            userQuery = getUserQuery(user);
            
	        int serviceCenterId = repoService.getCenterId();
			customerQuery = getCustomerQuery(user.getUserId(), serviceCenterId);
			custVehicleQuery = getCustVehicleQuery(user.getUserId(), serviceCenterId, vinNumber, carModel, mileage, year);
            
            displayMenu();
            System.out.println("Enter choice (1-2) from the given options displayed above:");
            selection = ScanHelper.nextInt();
			
		}while(!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));
		navigate(selection);
	}

	@Override
	public void display() {
		System.out.println("#############################");
		System.out.println("######      Usage      ######");
		System.out.println(DIRECTION_SEPARATOR);
		System.out.println("# A Customer ID:");
		System.out.println("# B FirstName:");
		System.out.println("# C LastName:");
		System.out.println("# D Username:");
		System.out.println("# E Password:");
		System.out.println("# F Address:");
		System.out.println("# G EmailAddress:");
		System.out.println("# H Phone Number:");
		System.out.println("# I Role:");
		System.out.println("# J Vin Number:");
		System.out.println("# K Car manufacturer:");
		System.out.println("# L Current mileage:");
		System.out.println("# M Year:");
		System.out.println(DIRECTION_SEPARATOR);
		System.out.println("#####      Example      ######");
		System.out.println("## Ex:10053;Billy;Batson;1234 Address ST Raleigh NC 12345;"
				+ "dlfranks@ncsu.edu;1234567890;bbatson;5TR3K914;Nissan;111000;2015 ##");
		System.out.println(DIRECTION_SEPARATOR);
		System.out.println();
		System.out.println("NOTE: It's important to enter information following");
        System.out.println("the example provided above using the delimiter, `;`");
        System.out.println();
	}

	public void displayMenu()
	{
		System.out.println("#######################################");
		System.out.println("#Receptionist: Add New Customer Profile Menu #");
		System.out.println(MENU_SEPARATOR);
		System.out.println("# 1 Save                               #");
		System.out.println("# 2 Go Back                           #");
		System.out.println(MENU_SEPARATOR);

	}

	@Override
	public void navigate(int selection) {
		switch(selection)
		{
			case 1: if(save()) System.out.println("Added Successfully");
					else System.out.println("Went wrong and try again!");
					goBack();
				break;
			default: goBack();
				break;
		}
	}

	@Override
	public void goBack() {
		new Receptionist().run();
	}
	public void reset() {
		customerQuery = null;
		custVehicleQuery = null;
		userQuery = null;
	}
	public boolean save()
	{
		boolean valid = false;
		try {
			DbConnection db = new DbConnection();
			try {
				boolean result1 = db.executeUpdate(userQuery);
				if(result1) {
					boolean result2 = db.executeUpdate(customerQuery);
					if(result2) {
						boolean result3 = db.executeUpdate(custVehicleQuery);
						if(result3) valid = true;
					}
				}
			}finally {
				db.close();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return valid;
	}
	public String getUserQuery(User user)
	{
		String userQuery = "insert into users (userId, serviceCenterId, role, "
				+ "userName, password, firstName, lastName, address, "
				+ "email, phone) values(" 
				+ user.getUserId() + ",  "
				+ user.getServiceCenterId() + ", '"
				+ user.getRole() + "', '"
				+ user.getUsername() + "', '"
				+ user.getLastName() + "', '"
				+ user.getFirstName() + "', '"
				+ user.getPassword() + "', '"
				+ user.getAddress() + "', '"
				+ user.getEmail() + "', '"
				+ user.getPhone() + "')";
		
		return userQuery;
	}
	public String getCustomerQuery(int userId, int serviceCenterId)
	{
		String inGoodStanding = "1";
		String isActive = "1";
		String customerQuery = "insert into Customers (userId, serviceCenterId, inGoodStanding, isActive) "
				+ "values("
				+ userId + ", "
				+ serviceCenterId +", "
				+ "'" + inGoodStanding +"', "
				+ "'" + isActive +"')";
		return customerQuery;
	}
	public String getCustVehicleQuery(int userId, int serviceCenterId, String vinNumber, String carModel, int mileage, int year ) {
		
		String custVehicleQuery = "insert into CustomerVehicles (vin, customerId, centerId, model, mileage, year) "
				+ "values("
				+ "'" + vinNumber + "', "
				+  userId + ", "
				+  serviceCenterId + ", "
				+ "'" + carModel + "', "
				+ mileage + ", "
				+ year + ")";

		return custVehicleQuery;
	}
}
