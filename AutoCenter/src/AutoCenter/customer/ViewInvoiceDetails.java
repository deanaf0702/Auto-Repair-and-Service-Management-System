package AutoCenter.customer;

import java.sql.ResultSet;
import java.util.List;

import AutoCenter.ScanHelper;
import AutoCenter.UIHelpers;
import AutoCenter.UserFlowFunctionality;
import AutoCenter.repository.DbConnection;

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
        final String query = "select se.serviceEventId, cv.customerId, se.vin, se.week, se.day, se.startTimeSlot, se.endTimeSlot, se.isPaid, "
                + "u.firstName, u.lastName from ServiceEvents se inner join CustomerVehicles cv on se.vin = cv.vin "
                + "inner join Users u on u.userId = se.mechanicId where se.serviceEventId = " + id;
        final String query2 = "select eos.serviceId, s.serviceType, s.name, p.price from EventOnServices eos inner join ServiceEvents se on eos.eventId = se.serviceEventId "
                + "inner join Services s on s.serviceId = eos.serviceId "
                + "inner join CustomerVehicles cv on cv.vin = se.vin "
                + "inner join Prices p on cv.model = p.model and s.serviceId = p.serviceId and se.centerId = p.centerId "
                + "where se.serviceEventId = " + id;
        int isPaid = 0;
        String mechanicName = "";
        DbConnection db = new DbConnection();
        try {
            final ResultSet rs = db.executeQuery( query );
            rs.next();
            System.out.println( "Invoice ID: " + rs.getInt( 1 ) );
            System.out.println( "Customer ID " + rs.getInt( 2 ) );
            System.out.println( "VIN: " + rs.getString( 3 ) );
            System.out.println(
                    "Service Date: " + UIHelpers.convertToStartDate( rs.getInt( 4 ), rs.getInt( 5 ), rs.getInt( 6 ) )
                            + "-" + UIHelpers.convertToEndDate( rs.getInt( 4 ), rs.getInt( 5 ), rs.getInt( 7 ) ) );
            System.out.println( "Services: " );
            isPaid = rs.getInt( 8 );
            mechanicName = rs.getString( 9 ).strip() + " " + rs.getString( 10 ).strip();

        }
        catch ( final Exception e ) {
            System.out.println( "Error retrieving invoices" );
            goBack();
        }
        finally {
            db.close();
        }

        db = new DbConnection();
        try {
            final ResultSet rs = db.executeQuery( query2 );
            int totalCost = 0;
            while ( rs.next() ) {
                System.out.println( rs.getInt( 1 ) + ": " + rs.getString( 3 ).strip() + " (" + rs.getString( 2 ).strip()
                        + "), $" + (int) rs.getDouble( 4 ) );
                totalCost += (int) rs.getDouble( 4 );
            }
            System.out.println( "Invoice Status: " + ( isPaid == 1 ? "paid" : "unpaid" ) );
            System.out.println( "Mechanic: " + mechanicName );
            System.out.println( "Total cost: " + totalCost );
        }
        catch ( final Exception e ) {
            System.out.println( "Error retrieving invoices" );
            goBack();
        }
        finally {
            db.close();
        }
        new Invoices().run();

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
