package AutoCenter.customer;

import java.sql.ResultSet;

import AutoCenter.Home;
import AutoCenter.ScanHelper;
import AutoCenter.UserFlowFunctionality;
import AutoCenter.repository.DbConnection;

public class DeleteCar implements UserFlowFunctionality {

    @Override
    public void run () {
        DbConnection db = new DbConnection();
        try {
            System.out.println( "Vehicles:" );
            final String query2 = "select * from CustomerVehicles where customerId = " + Home.getUser().getId()
                    + "and centerId = " + Home.getUser().getCenterId();
            final ResultSet rs2 = db.executeQuery( query2 );
            while ( rs2.next() ) {
                System.out.println( rs2.getString( "YEAR" ).strip() + " " + rs2.getString( "MODEL" ).strip() + ", VIN: "
                        + rs2.getString( "VIN" ) + ", Mileage: " + rs2.getInt( "MILEAGE" ) );
            }
            System.out.println();
            display();
        }
        catch ( final Exception e ) {
            System.out.println( "Error retrieving vehicles" );
            goBack();
        }
        finally {
            db.close();
        }
        int choice = 2;
        do {
            System.out.print( "Enter a choice: " );
            choice = ScanHelper.nextInt();
        }
        while ( choice != 1 && choice != 2 );
        if ( choice == 1 ) {

            System.out.print( "Enter VIN of car to delete: " );
            final String vin = ScanHelper.next();
            final String query = "delete from CustomerVehicles where vin = '" + vin + "'";
            try {
                db = new DbConnection();
                db.executeUpdate( query );
            }
            catch ( final Exception e ) {
                System.out.println( "Error deleting vehicle" );
                goBack();
            }
            finally {
                db.close();
            }
            System.out.println( "Vehicle deleted successfully" );
            goBack();
        }
        else {
            goBack();
        }

    }

    @Override
    public void display () {
        System.out.println( "####################################################" );
        System.out.println( "##### Customer: Delete Car Menu                 #####" );
        System.out.println( "####################################################" );
        System.out.println( "# 1 Select Car to Delete                           #" );
        System.out.println( "# 2 Go Back                                        #" );
        System.out.println( "####################################################" );

    }

    @Override
    public void navigate ( final int selection ) {
        // Not needed for this page
    }

    @Override
    public void goBack () {
        new Customer().run();
    }
}
