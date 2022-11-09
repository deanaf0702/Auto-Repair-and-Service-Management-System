package AutoCenter.receptionist;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import AutoCenter.ScanHelper;
import AutoCenter.UserFlowFunctionality;
import AutoCenter.repository.DbConnection;
import AutoCenter.services.RepositoryService;
import AutoCenter.services.UserService;

public class FindCustomersWithPendingInvoices implements UserFlowFunctionality{
	
	private UserService userService = null;
	private static final String DIRECTION_SEPARATOR = "#############################";
    private static final String MENU_SEPARATOR = "#######################################";
    private static final String TABLE_COLUMN_SEPARATOR = ", ";
    private static final int YEAR = 2022;
    private static final int MONTH = 10;
    
    public FindCustomersWithPendingInvoices()
    {
    	userService = new UserService();
    }
	@Override
	public void run() {
		display();
		int selection = 1;
		String query = getQuery();
		try {
			DbConnection db = new DbConnection();
			try {
				ResultSet rs = db.executeQuery(query);
				if (!rs.isBeforeFirst() ) {    
				    System.out.println("No data"); 
				}else {
					while(rs.next())
					{
						System.out.print(rs.getInt("CustomerId"));
						System.out.print(TABLE_COLUMN_SEPARATOR);
						System.out.print(rs.getString("firstName").trim() + " " + rs.getString("lastName").trim());
						System.out.print(TABLE_COLUMN_SEPARATOR);
						System.out.print(rs.getInt("serviceEventId"));
						System.out.print(TABLE_COLUMN_SEPARATOR);
						String d = timeslotConvertToDate(rs.getInt("week"), rs.getInt("day"));
						System.out.print(d);
						System.out.print(TABLE_COLUMN_SEPARATOR);
						System.out.print(rs.getDouble("price"));
						System.out.print(TABLE_COLUMN_SEPARATOR);
						System.out.println();
					}
				}
			}finally {
				db.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		displayMenu();	
		System.out.println("Enter choice (1-2) from the given options displayed above:");
		selection = ScanHelper.nextInt();
		if (selection == (int)selection)
		{
		   navigate(selection);
		}else {
			goBack();
		}
	}

	@Override
	public void display() {
		System.out.println("#############################");
		System.out.println("# A. Customer ID            #");
		System.out.println("# B. Customer Name          #");
		System.out.println("# C. Invoice ID             #");
		System.out.println("# D. Invoice Date           #");
		System.out.println("# E. Amount                 #");
		System.out.println(DIRECTION_SEPARATOR);
		System.out.println("#####      Example      ######");
		System.out.println("## Ex: 3 ##");
		System.out.println(DIRECTION_SEPARATOR);
	}
	public void displayMenu() {
		System.out.println("#######################################");
		System.out.println("# Receptionist: Find Customers with Pending Invoices Menu #");
		System.out.println(MENU_SEPARATOR);
		System.out.println("## 1 Go Back                         ##");
		System.out.println(MENU_SEPARATOR);
	}

	@Override
	public void navigate(int selection) {
		goBack();
	}

	@Override
	public void goBack() {
		new Receptionist().run();

	}
	public String timeslotConvertToDate(int week, int day)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, week);
        calendar.set(Calendar.MONTH, MONTH);
        calendar.set(Calendar.YEAR, YEAR);
        return sdf.format(calendar.getTime());
        
	}
	private String getQuery()
	{
		int centerId = userService.getCenterId();
		
		String query = "select cv.customerId, u.firstName, u.lastName, e.serviceEventId, e.week, e.day, e.startTimeSlot, e.endTimeslot, c.isactive, p.price"
				+ " from Users u,CustomerVehicles cv, Customers c, ServiceEvents e, EventOnServices eos, Services s, Prices p"
				+ " where cv.centerId=" + centerId
				+ " and u.userId=cv.customerId and u.serviceCenterId = cv.centerId and cv.customerId = c.userId and cv.centerId = c.serviceCenterId"
				+ " and cv.vin = e.vin and e.serviceEventId = eos.eventid and eos.serviceId = s.serviceId"
				+ " and s.serviceId = p.serviceid and cv.centerId = p.centerId and cv.model = p.model";
		return query;
	}

}
