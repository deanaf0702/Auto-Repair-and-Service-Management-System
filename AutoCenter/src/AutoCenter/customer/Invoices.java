package AutoCenter.customer;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import AutoCenter.LoginUser;
import AutoCenter.ScanHelper;
import AutoCenter.UserFlowFunctionality;
import AutoCenter.repository.DbConnection;

public class Invoices implements UserFlowFunctionality {

    private static int          INITIAL_SELECTION = 0;
    private static int          MIN_SELECTION     = 1;
    private static int          MAX_SELECTION     = 3;
    public static List<Integer> invoiceIds;

    @Override
    public void run () {
        System.out.println( "Invoices:" );
        final String query = "select se.serviceEventId, se.isPaid from ServiceEvents se "
                + "inner join CustomerVehicles on se.vin = cv.vin " + "where cv.customerId = " + LoginUser.getId();
        final DbConnection db = new DbConnection();
        invoiceIds = new ArrayList<Integer>();
        try {
            final ResultSet rs = db.executeQuery( query );
            while ( rs.next() ) {
                final int id = rs.getInt( 1 );
                invoiceIds.add( id );
                final int isPaid = rs.getInt( 2 );
                System.out.println( id + ": " + ( isPaid == 1 ? "paid" : "unpaid" ) );
            }
        }
        catch ( final Exception e ) {
            System.out.println( "Error retrieving invoices" );
            goBack();
        }
        finally {
            db.close();
        }
        int selection = INITIAL_SELECTION;
        display();
        do {
            System.out.print( "Enter choice (1-3) from the given options displayed above: " );
            selection = ScanHelper.nextInt();
        }
        while ( ! ( selection >= MIN_SELECTION && selection <= MAX_SELECTION ) );
        navigate( selection );
    }

    @Override
    public void display () {
        System.out.println( "###################################" );
        System.out.println( "##### Customer: Invoices Menu #####" );
        System.out.println( "###################################" );
        System.out.println( "# 1 View Invoice details          #" );
        System.out.println( "# 2 Pay invoice                   #" );
        System.out.println( "# 3 Go Back                       #" );
        System.out.println( "###################################" );
    }

    @Override
    public void navigate ( final int selection ) {
        switch ( selection ) {
            case 1:
                new ViewInvoiceDetails().run();
                break;
            case 2:
                new PayInvoice().run();
                break;
            case 3:
                goBack();
                break;
        }
    }

    @Override
    public void goBack () {
        new Invoices().run();
    }

    public static List<Integer> getInvoiceIds () {
        return invoiceIds;
    }
}
