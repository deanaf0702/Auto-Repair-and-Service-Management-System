package frontend;

import java.util.Scanner;

public class AUTORApplication {
	
	enum Role {
	    ADMIN,
	    MANAGER,
	    RECEPTIONIST,
	    CUSTOMER,
	    MECHANIC
	  }
	
	/** keep track of current user */
	public int userId;
	public Role userRole;
	
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Welcome to AUTOR.\n 1. Login \n 2. Sign Up \n 3. System Admin \n 4. Exit \nChoose an Option (1,2,3): ");
		while (!in.hasNextInt()) {
			in.nextLine();
			System.out.print("Welcome to AUTOR.\n 1. Login \n 2. Sign Up \n 3. System Admin \n 4. Exit \nChoose an Option (1,2,3): ");
		}
		int choice = in.nextInt();
		in.close();
		switch (choice) {
			case 1:
			// Login code
				break;
			case 2:
				// Sign Up code
				break;
			case 3:
				// Admin code
				break;
			case 4:
				System.exit(0);
				break;
			default:
		}
	}
}
