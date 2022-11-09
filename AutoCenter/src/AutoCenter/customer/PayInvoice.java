package AutoCenter.customer;

import java.sql.ResultSet;

import AutoCenter.LoginUser;
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
        final String query2 = "select cv.customerId, se.centerId, se.isPaid from ServiceEvents se, CustomerVehicles cv where se.vin = cv.vin and se.serviceEventId = "
                + id;
        final DbConnection db2 = new DbConnection();
        try {
            final ResultSet rs2 = db2.executeQuery( query2 );
            rs2.next();
            final int cid = rs2.getInt( 1 );
            final int centerId = rs2.getInt( 2 );
            final int isPaid = rs2.getInt( 3 );
            if ( isPaid == 1 ) {
                System.out.println( "This invoice is already paid" );
                new PayInvoice().run();
            }
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            System.out.println( "Error retrieving invoices" );
            goBack();
        }
        finally {
            db2.close();
        }
        DbConnection db = new DbConnection();
        try {
            db.executeUpdate( query );
            System.out.println( "Invoice paid successfully" );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            System.out.println( "Error retrieving invoices" );
            goBack();
        }
        finally {
            db.close();
        }
        db = new DbConnection();
        try {
            final String query8 = "select count(*) from ServiceEvents se inner join CustomerVehicles cv on se.vin = cv.vin where isPaid = 0 and se.centerId = "
                    + LoginUser.getCenterId() + " and cv.customerId = " + LoginUser.getId();
            final ResultSet rs4 = db.executeQuery( query8 );
            rs4.next();
            final int cnt = rs4.getInt( 1 );
            if ( cnt == 0 ) {
                db.executeUpdate( "update Customers set inGoodStanding = 1 where centerId = " + LoginUser.getCenterId()
                        + " and userId = " + LoginUser.getId() );
            }
            System.out.println( "Invoice paid successfully" );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
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
