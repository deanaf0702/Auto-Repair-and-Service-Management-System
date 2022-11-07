package AutoCenter.customer;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import AutoCenter.LoginUser;
import AutoCenter.ScanHelper;
import AutoCenter.UserFlowFunctionality;
import AutoCenter.repository.DbConnection;

public class IndividualServices implements UserFlowFunctionality {

    @Override
    public void run () {
        final String category = AddScheduleRepair.getCategory();
        final String query = "select s.name, s.serviceId from Services s inner join RepairServices rs on s.serviceId = rs.serviceId where rs.category = '"
                + category + "'";
        final DbConnection db = new DbConnection();
        try {
            final ResultSet rs = db.executeQuery( query );
            int i = 1;
            final Map<Integer, Integer> serviceIds = new HashMap<Integer, Integer>();
            System.out.println( "***********************" );
            while ( rs.next() ) {
                System.out.println( i + " " + rs.getString( 1 ).strip() );
                serviceIds.put( i, rs.getInt( 2 ) );
                i++;
            }
            System.out.println( i + " " + "Go Back" );
            System.out.println( "***********************" );
            int selection = 0;
            do {
                System.out.print( "Select service to add to cart: " );
                selection = ScanHelper.nextInt();
            }
            while ( selection < 1 || selection > i );
            if ( selection == i ) {
                goBack();
            }
            else {
                final int sid = serviceIds.get( selection );
                if ( LoginUser.addToCart( sid ) ) {
                    new ScheduleService().run();
                }
                else {
                    System.out.println( "You have already added this service" );
                    goBack();
                }
            }

        }
        catch ( final Exception e ) {
            System.out.println( "Error retrieving vehicles" );
            goBack();
        }
        finally {
            db.close();
        }
    }

    @Override
    public void display () {
        // Not needed

    }

    @Override
    public void navigate ( final int selection ) {
        // Not needed
    }

    @Override
    public void goBack () {
        new AddScheduleRepair().run();
    }

}
