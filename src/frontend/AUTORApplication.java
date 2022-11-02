package frontend;

import java.sql.Connection;
import java.util.Scanner;

import database.ConnectionManager;

public class AUTORApplication {

    enum Role {
        ADMIN, MANAGER, RECEPTIONIST, CUSTOMER, MECHANIC
    }

    /** keep track of current user */
    public int  userId;
    public Role userRole;

    public static void main ( final String[] args ) {
        final Scanner in = new Scanner( System.in );
        System.out.println(
                "Welcome to AUTOR.\n 1. Login \n 2. Sign Up \n 3. System Admin \n 4. Exit \nChoose an Option (1,2,3): " );
        while ( !in.hasNextInt() ) {
            in.nextLine();
            System.out.print(
                    "Welcome to AUTOR.\n 1. Login \n 2. Sign Up \n 3. System Admin \n 4. Exit \nChoose an Option (1,2,3): " );
        }
        final int choice = in.nextInt();
        in.close();
        if ( choice == 1 ) {
            final Connection c = ConnectionManager.getConnection();
        }
        else if ( choice == 2 ) {

        }
        else if ( choice == 3 ) {

        }
        else if ( choice == 4 ) {

        }
        else {

        }
    }
}
