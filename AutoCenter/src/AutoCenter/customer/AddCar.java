package AutoCenter.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;

import AutoCenter.Home;
import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.repository.DbConnection;

public class AddCar implements Interface {

    @Override
    public void run () {
        System.out.println( "VIN Number: " );
        final String vin = ScanHelper.next();
        System.out.println( "Car Manufacturer: " );
        final String manf = ScanHelper.next();
        System.out.println( "Current Mileage: " );
        final int mileage = ScanHelper.nextInt();
        System.out.println( "Car Year: " );
        final int year = ScanHelper.nextInt();
        int selection = 2;
        do {
            display();
            System.out.println( "Enter choice(1-2):" );
            selection = ScanHelper.nextInt();

        }
        while ( selection != 1 && selection != 2 );
        if ( selection == 1 ) {
            final DbConnection db = new DbConnection();
            final Connection conn = db.getConnection();
            try {
                final PreparedStatement stmt = conn.prepareStatement( "insert into CustomerVehicles "
                        + "(vin, customerId, serviceCenter, model, mileage, year, lastMClass) values "
                        + "(?, ?, ?, ?, ?, ?, 'C')" );
                stmt.setString( 1, vin );
                stmt.setInt( 2, Home.getUser().getId() );
                stmt.setInt( 3, Home.getUser().getCenterId() );
                stmt.setString( 4, manf );
                stmt.setInt( 5, mileage );
                stmt.setInt( 6, year );
                stmt.executeUpdate();
            }
            catch ( final Exception e ) {
                System.out.println( "Error adding car" );
            }
            finally {
                db.close();
            }

        }
        navigate( selection );
    }

    @Override
    public void display () {
        System.out.println( "##################################################" );
        System.out.println( "##### Customer: Add Car Menu #####" );
        System.out.println( "##################################################" );
        System.out.println( "# 1 Save Information                             #" );
        System.out.println( "# 2 Cancel                                       #" );
        System.out.println( "##################################################" );

    }

    @Override
    public void navigate ( final int selection ) {
        goBack();
    }

    @Override
    public void goBack () {
        new Customer().run();
    }
}
