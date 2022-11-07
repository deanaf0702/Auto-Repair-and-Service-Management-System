package AutoCenter.customer;

import java.sql.ResultSet;

import AutoCenter.ScanHelper;
import AutoCenter.UIHelpers;
import AutoCenter.UserFlowFunctionality;
import AutoCenter.repository.DbConnection;

public class ViewServiceHistory implements UserFlowFunctionality {

    @Override
    public void run () {
        System.out.println( "Enter car VIN number: " );
        final String vin = ScanHelper.next();
        display();
        int selection = 0;
        do {
            System.out.print( "Enter choice (1-2) from the given options displayed above: " );
            selection = ScanHelper.nextInt();
        }
        while ( ! ( selection >= 1 && selection <= 2 ) );
        if ( selection == 1 ) {
            final DbConnection db = new DbConnection();
            final String query = "select eos.serviceId, se.vin, s.serviceType, "
                    + "p.price, u.firstName, u.lastName, se.week, se.day, " + "se.startTimeSlot, se.endTimeSlot "
                    + "from EventOnServices eos " + "inner join ServiceEvents se on se.serviceEventId = eos.eventId "
                    + "inner join Services s on s.serviceId = eos.serviceId "
                    + "inner join Users u on u.userId = se.mechanicId "
                    + "inner join CustomerVehicles cv on se.vin = cv.vin "
                    + "inner join Prices p on cv.model = p.model and s.serviceId = p.serviceId and se.centerId = p.centerId "
                    + "where se.vin = '" + vin + "'";
            try {
                System.out.println( "Services:" );
                final ResultSet rs = db.executeQuery( query );
                while ( rs.next() ) {
                    System.out.println( "Service Id: " + rs.getInt( 1 ) );
                    System.out.println( "VIN Number: " + rs.getString( 2 ).strip() );
                    System.out.println( "Service Type: " + rs.getString( 3 ).strip() );
                    System.out.println( "Service Cost: " + rs.getDouble( 4 ) );
                    System.out
                            .println( "Mechanic Name: " + rs.getString( 5 ).strip() + " " + rs.getString( 6 ).strip() );
                    System.out.println( "Service Start: "
                            + UIHelpers.convertToStartDate( rs.getInt( 7 ), rs.getInt( 8 ), rs.getInt( 9 ) ) );
                    System.out.println( "Service End: "
                            + UIHelpers.convertToEndDate( rs.getInt( 7 ), rs.getInt( 8 ), rs.getInt( 10 ) ) );
                    System.out.println();
                }
            }
            catch ( final Exception e ) {
                System.out.println( e.getMessage() );
                System.out.println( "Error getting Service data" );
            }
            finally {
                db.close();
            }
            goBack();
        }
        else {
            goBack();
        }
    }

    @Override
    public void display () {
        System.out.println( "####################################################" );
        System.out.println( "##### Customer: View Service History Menu      #####" );
        System.out.println( "####################################################" );
        System.out.println( "# 1 Show History                                   #" );
        System.out.println( "# 2 Go Back                                        #" );
        System.out.println( "####################################################" );

    }

    @Override
    public void navigate ( final int selection ) {
        // Not needed
    }

    @Override
    public void goBack () {
        new ViewAndScheduleService().run();
    }
}
