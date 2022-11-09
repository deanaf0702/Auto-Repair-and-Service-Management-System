package AutoCenter.customer;

import java.sql.ResultSet;
import java.util.List;

import AutoCenter.LoginUser;
import AutoCenter.ScanHelper;
import AutoCenter.UserFlowFunctionality;
import AutoCenter.repository.DbConnection;

public class AddScheduleMaintenance implements UserFlowFunctionality {

    @Override
    public void run () {
        final String vin = ScheduleService.getVin();
        final int cid = LoginUser.getCenterId();
        final String query = "select lastMClass, model from CustomerVehicles where vin = '" + vin + "'";
        final DbConnection db = new DbConnection();
        try {
            final ResultSet rs = db.executeQuery( query );
            rs.next();
            final String lm = rs.getString( 1 ).strip();
            String nextM;
            if ( lm.startsWith( "A" ) ) {
                nextM = "B";
            }
            else if ( lm.startsWith( "B" ) ) {
                nextM = "C";
            }
            else {
                nextM = "A";
            }
            final String model = rs.getString( 2 ).strip();
            System.out.println( "Your next maintenance service is Schedule " + nextM );
            final String query2 = "select serviceId from Services where name = '" + nextM + "'";
            final ResultSet rs2 = db.executeQuery( query2 );
            rs2.next();
            final int serviceId = rs2.getInt( 1 );
            final String query3 = "select price from prices where centerId = " + cid + " and serviceId = " + serviceId
                    + " and model = '" + model + "'";
            final ResultSet rs3 = db.executeQuery( query3 );
            rs3.next();
            final double cost = rs3.getDouble( 1 );
            System.out.println( "Cost: " + cost );
            display();
            int selection = 0;
            do {
                System.out.print( "Enter choice (1-2) from the given options displayed above: " );
                selection = ScanHelper.nextInt();
            }
            while ( ! ( selection >= 1 && selection <= 2 ) );
            if ( selection == 1 ) {
                final List<Integer> cart = LoginUser.getCart();
                if ( cart.contains( 113 ) || cart.contains( 114 ) || cart.contains( 115 ) ) {
                    System.out.println( "You already have a maintenance service in your cart" );
                    goBack();
                }
                if ( LoginUser.addToCart( serviceId ) ) {
                    goBack();
                }
                else {
                    System.out.println( "This service is already added to your cart" );
                    goBack();
                }
            }
            else {
                goBack();
            }

        }
        catch ( final Exception e ) {
            System.out.println( "Error getting last service" );
            goBack();
        }
        finally {
            db.close();
        }

    }

    @Override
    public void display () {
        System.out.println( "####################################################" );
        System.out.println( "##### Customer: Schedule Maintenance Menu      #####" );
        System.out.println( "####################################################" );
        System.out.println( "# 1 Add to Cart                                    #" );
        System.out.println( "# 2 Go Back                                        #" );
        System.out.println( "####################################################" );
    }

    @Override
    public void navigate ( final int selection ) {
        // Not used
    }

    @Override
    public void goBack () {
        new ScheduleService().run();
    }
}
