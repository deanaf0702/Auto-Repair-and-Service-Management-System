package AutoCenter.customer;

import AutoCenter.ScanHelper;
import AutoCenter.UserFlowFunctionality;
import AutoCenter.repository.DbConnection;

public class PayInvoice implements UserFlowFunctionality {

    @Override
    public void run () {
        System.out.println( "Enter ID of invoice to pay: " );
        final int id = ScanHelper.nextInt();
        final String query = "update ServiceEvents set isPaid = 1 where serviceEventId = " + id;
        display();
        int selection = 0;
        do {
            System.out.print( "Enter choice (1-3) from the given options displayed above: " );
            selection = ScanHelper.nextInt();
        }
        while ( ! ( selection >= 1 && selection <= 2 ) );

        if ( selection == 2 ) {
            goBack();
        }
        final DbConnection db = new DbConnection();
        try {
            db.executeUpdate( query );
            System.out.println( "Invoice paid successfully" );
        }
        catch ( final Exception e ) {
            System.out.println( "Error retrieving invoices" );
            goBack();
        }
        finally {
            db.close();
        }
        new PayInvoice().run();
    }

    @Override
    public void display () {
        System.out.println( "####################################################" );
        System.out.println( "##### Customer: Pay Invoices Menu              #####" );
        System.out.println( "####################################################" );
        System.out.println( "# 1 Pay Invoice                                    #" );
        System.out.println( "# 2 Go Back                                        #" );
        System.out.println( "####################################################" );
    }

    @Override
    public void navigate ( final int selection ) {
        // TODO Auto-generated method stub

    }

    @Override
    public void goBack () {
        new Invoices().run();

    }
}
