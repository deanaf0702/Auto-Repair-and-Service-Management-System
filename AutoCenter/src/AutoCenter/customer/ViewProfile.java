package AutoCenter.customer;

import java.sql.ResultSet;

import AutoCenter.Home;
import AutoCenter.ScanHelper;
import AutoCenter.UserFlowFunctionality;
import AutoCenter.repository.DbConnection;

public class ViewProfile implements UserFlowFunctionality {

    @Override
    public void run () {
        final DbConnection db = new DbConnection();
        try {
            final String query = "select * from Users where userId = " + Home.getUser().getId()
                    + "and serviceCenterId = " + Home.getUser().getCenterId();
            final ResultSet rs1 = db.executeQuery( query );
            rs1.next();
            System.out.println( "Customer ID: " + rs1.getInt( "USERID" ) );
            System.out.println( "Name: " + rs1.getString( "FIRSTNAME" ) + " " + rs1.getString( "LASTNAME" ) );
            System.out.println( "Address: " + rs1.getString( "ADDRESS" ) );
            System.out.println( "Email: " + rs1.getString( "EMAIL" ) );
            System.out.println( "Phone: " + rs1.getString( "PHONE" ) );
            System.out.println( "Vehicles:" );
            final String query2 = "select * from CustomerVehicles where customerId = " + Home.getUser().getId()
                    + "and centerId = " + Home.getUser().getCenterId();
            final ResultSet rs2 = db.executeQuery( query2 );
            while ( rs2.next() ) {
                System.out.println( rs2.getString( "YEAR" ) + " " + rs2.getString( "MODEL" ) + ", VIN: "
                        + rs2.getString( "VIN" ) + ", Mileage: " + rs2.getInt( "MILEAGE" ) );
            }
            System.out.println();
        }
        catch ( final Exception e ) {
            System.out.println( e.getMessage() );
            System.out.println( "Unable to access profile" );
        }
        finally {
            db.close();
        }
        int selection = 0;
        display();
        do {
            System.out.println( "Enter choice(1-1)" );
            selection = ScanHelper.nextInt();
        }
        while ( selection != 1 );
    }

    @Override
    public void display () {
        System.out.println( "##### View Profile Menu #####" );
        System.out.println( "1 Go Back" );
        System.out.println( "##########" );

    }

    @Override
    public void navigate ( final int selection ) {
        new ViewAndUpdateProfile().run();

    }

    @Override
    public void goBack () {
        new ViewAndUpdateProfile().run();
    }
}
