package AutoCenter;

import java.util.Objects;
import java.util.Scanner;

import AutoCenter.administrator.Administration;
import AutoCenter.customer.Customer;
import AutoCenter.manager.Manager;
import AutoCenter.mechanic.Mechanic;
import AutoCenter.receptionist.Receptionist;
import AutoCenter.services.Helper;
import AutoCenter.services.UserService;

public class Login implements Interface
{
	private String userId = null;
	private String password = null;
	
	
	public Login()
	{
		
	}
	
	public void run()
	{
		
		int selection = 2;
		display();
		do {
			System.out.println("Enter UserID: ");
			userId = ScanHelper.next();
			System.out.println("Enter password: ");
			password = ScanHelper.next();
			System.out.println("Enter choice(1-2):");
			selection = ScanHelper.nextInt();
		}while(!(selection >=1 && selection <=2));
		
		navigate(selection);
		
	}
	public void display()
	{
		System.out.println("##Login Menu##");
		System.out.println("1.Sign-In");
		System.out.println("2.Go Back");
		System.out.println("##########");
	}
	public void navigate(int value)
	{
		switch(value) {
		case 1: signIn(userId, password);
			break;
		case 2: goBack();
			break;
		}
	}
	public void signIn(String userId, String password)
	{
		UserService userService = new UserService();
		User user = userService.authenticate(userId, password);
		if(user == null) {
			System.out.print("User not found!");
			runAgain();
		}else {
			System.out.println(user.getRole() + ": " + user.getName());
			
			Home.setUser(user);
			String role = user.getRole();
			if("manager".equals(role)) new Manager().run();
			else if("receptionist".equals(role)) new Receptionist().run();
			else if("mechanic".equals(role)) new Mechanic().run();
			else if("administrator".equals(role)) new Administration().run();
			else if("customer".equals(role)) new Customer().run();
			else {
				System.out.print("User not found!");
				runAgain();
			}
		}	 
	}
	public void runAgain()
	{
		System.out.println("Went wrong;");
		run();
	}
	public void goBack()
	{
		Home.run();
	}
	
}
