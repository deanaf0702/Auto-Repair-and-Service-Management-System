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

		do {
			display();
			reset();
			String input = ScanHelper.nextLine();
			String[] inputs = input.split(";");

			if(inputs.length == EXPECTED_INPUT_LENGTH )
			{
				displayMenu();
				System.out.println("Enter choices(1-2)");
				selection = ScanHelper.nextInt();
				userQuery = getUserQuery(inputs);
				int userId = Integer.parseInt(inputs[0].trim());
				String vinNumber = inputs[7].trim();
		        String carModel = inputs[8].trim();
		        int mileage = Integer.parseInt(inputs[9]);
		        int year = Integer.parseInt(inputs[10].trim());
		        int serviceCenterId = repoService.getCenterId();
				customerQuery = getCustomerQuery(userId, serviceCenterId);
				custVehicleQuery = getCustVehicleQuery(userId, serviceCenterId, vinNumber, carModel, mileage, year);
			}else {
				System.out.println("Went wrong and try again!");
			}
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
		System.out.println("# D Address:");
		System.out.println("# E EmailAddress:");
		System.out.println("# F Phone Number:");
		System.out.println("# G Username:");
		System.out.println("# H Vin Number:");
		System.out.println("# I Car manufacturer:");
		System.out.println("# J Current mileage:");
		System.out.println("# K Year:");
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
				boolean result = db.executeUpdate(customerQuery);
				if(result) {
					boolean result2 = db.executeUpdate(custVehicleQuery);
					if(result2) valid = true;

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
	public String getUserQuery(String[] inputs)
	{
		User user = new User();
		user.setUserId(Integer.parseInt(inputs[0]));
		user.setFirstName(inputs[1].trim());
		user.setLastName(inputs[2].trim());
		user.setUsername(inputs[3].trim());
		user.setPassword(inputs[4].trim());
		user.setAddress(inputs[5].trim());
		user.setEmail(inputs[6].trim());
		user.setPhone(inputs[7].trim());
		user.setRole(inputs[8].trim());
		user.setServiceCenterId(repoService.getCenterId());
		
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
				+ user.getPhone() + "'";
		
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
		
		String custVehicleQuery = "insert into CustomerVehicles (vin, userId, centerId, model, mileage, year) "
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
