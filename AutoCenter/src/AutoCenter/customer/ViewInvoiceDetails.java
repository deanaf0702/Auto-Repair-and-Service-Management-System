package AutoCenter.customer;

import java.util.List;

import AutoCenter.ScanHelper;
import AutoCenter.UserFlowFunctionality;

public class ViewInvoiceDetails implements UserFlowFunctionality {

    @Override
    public void run () {
        System.out.print( "Enter id of invoice to view: " );
        final int id = ScanHelper.nextInt();
        final List<Integer> invoiceList = Invoices.getInvoiceIds();
        if ( !invoiceList.contains( id ) ) {
            new Invoices().run();
        }
        display();
        int selection = 0;
        do {
            System.out.print( "Enter choice (1-2) from the given options displayed above: " );
            selection = ScanHelper.nextInt();
        }
        while ( ! ( selection >= 1 && selection <= 2 ) );
        if ( selection == 2 ) {
            goBack();
        }
        final String query = "select from ServiceEvents se inner join CustomerVehicles cv on se.vin = cv.vin"
                + "inner join Users u on u.userId = se.mechanicId";

    }

    @Override
    public void display () {
        System.out.println( "###################################" );
        System.out.println( "##### Customer: View Invoices #####" );
        System.out.println( "###################################" );
        System.out.println( "# 1 View Invoice                  #" );
        System.out.println( "# 2 Go Back                       #" );
        System.out.println( "###################################" );
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
