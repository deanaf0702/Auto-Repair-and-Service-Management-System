package AutoCenter;

public class Home
{
	
	private static User user = null;
	
	public static void main(String[] args) 
	{
		run();
	}
	public static void run()
	{
		int selection = 2;
		do {
			display();
			System.out.println("Enter choice(1-2):");
			selection = ScanHelper.nextInt();
			
			
			
		}while(!(selection >=1 && selection <=2));
		
		navigate(selection);
	}
	public static void display() {
		System.out.println("##Home Menu##");
		System.out.println("1 Login");
		System.out.println("2 Exit");
		System.out.println("###########");

	}
	public static void navigate(int value)
	{
		switch (value) {
		case 1: login();
			break;
		case 2: exit();
			break;
		}
	}
	public static void exit()
	{
		System.out.println("Good-Bye!");
	}
	public static void login()
	{
		Login login = new Login();
		login.run();
	}
	public static void setUser(User appUser)
	{
		user = appUser;
	}
	public static User getUser()
	{
		return user;
	}

}
