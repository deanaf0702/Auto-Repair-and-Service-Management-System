package AutoCenter.customer;

import java.sql.ResultSet;
import java.util.List;

import AutoCenter.LoginUser;
import AutoCenter.ScanHelper;
import AutoCenter.UserFlowFunctionality;
import AutoCenter.repository.DbConnection;

public class ViewCartandSelectScheduleTime implements UserFlowFunctionality {

    @Override
    public void run () {
        final List<Integer> cart = LoginUser.getCart();
        System.out.println( "Items in cart:" );
        for ( final int id : cart ) {
            final String query = "select name from Services where serviceId = " + id;
            final DbConnection db = new DbConnection();
            try {
                final ResultSet rs = db.executeQuery( query );
                rs.next();
                System.out.println( id + ": " + rs.getString( 1 ).strip() );
            }
            catch ( final Exception e ) {
                System.out.println( "Error retrieving vehicles" );
                goBack();
            }
            finally {
                db.close();
            }
        }
        display();
        int selection = 0;
        do {
            System.out.print( "Enter choice (1-2) from the given options displayed above: " );
            selection = ScanHelper.nextInt();
        }
        while ( ! ( selection >= 1 && selection <= 2 ) );

        navigate( selection );

    }

    @Override
    public void display () {
        System.out.println( "####################################################" );
        System.out.println( "##### Customer: View Cart Menu                 #####" );
        System.out.println( "####################################################" );
        System.out.println( "# 1 Proceed With Scheduling                        #" );
        System.out.println( "# 2 Go Back                                        #" );
        System.out.println( "####################################################" );
    }

    @Override
    public void navigate ( final int selection ) {
        if ( selection == 1 ) {
            new ScheduleServicesInCart().run();
        }
        else {
            goBack();
        }

    }

    @Override
    public void goBack () {
        new ScheduleService().run();
    }
}
