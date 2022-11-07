package AutoCenter.customer;

import java.sql.ResultSet;
import java.util.List;

import AutoCenter.LoginUser;
import AutoCenter.UserFlowFunctionality;
import AutoCenter.repository.DbConnection;

public class ScheduleServicesInCart implements UserFlowFunctionality {

    @Override
    public void run () {
        final List<Integer> cart = LoginUser.getCart();
        if ( cart.size() == 0 ) {
            System.out.println( "Cart is empty" );
            goBack();
        }
        final int centerId = LoginUser.getCenterId();
        final int customerId = LoginUser.getId();
        final String vin = ScheduleService.getVin();
        final int mileage = ScheduleService.getMileage();
        String model = "";
        String query = "select model from CustomerVehicles where vin = '" + vin + "'";
        DbConnection db = new DbConnection();
        try {
            final ResultSet rs = db.executeQuery( query );
            rs.next();
            model = rs.getString( 1 ).strip();
        }
        catch ( final Exception e ) {
            System.out.println( "Error retrieving vehicles" );
            goBack();
        }
        finally {
            db.close();
        }
        int totalPrice = 0;
        int totalHours = 0;
        for ( final int id : cart ) {
            final String query2 = "select price, hours from Prices where centerId = " + centerId + "and serviceId = "
                    + id + "and model = '" + model + "'";
            final DbConnection db2 = new DbConnection();
            try {
                final ResultSet rs = db2.executeQuery( query );
                rs.next();
                totalPrice += (int) rs.getDouble( 1 );
                totalHours += rs.getInt( 2 );
            }
            catch ( final Exception e ) {
                System.out.println( "Error in scheduling" );
                goBack();
            }
            finally {
                db2.close();
            }
        }

        System.out.println( "Total hours: " + totalHours );
        if ( totalHours > 11 ) {
            System.out.println( "Too many hours" );
            goBack();
        }

        query = "select satOpen from ServiceCenters where centerId = " + centerId;
        db = new DbConnection();
        int satOpen = 0;
        try {
            final ResultSet rs = db.executeQuery( query );
            rs.next();
            satOpen = rs.getInt( 1 );
        }
        catch ( final Exception e ) {
            System.out.println( "Error retrieving vehicles" );
            goBack();
        }
        finally {
            db.close();
        }
        int days = 5;
        if ( satOpen == 1 ) {
            days = 6;
        }
        System.out.println( "Available time slots:" );
        for ( int week = 1; week <= 4; week++ ) {
            for ( int day = 1; day < days; day++ ) {

            }
        }

    }

    @Override
    public void display () {
        // TODO Auto-generated method stub

    }

    @Override
    public void navigate ( final int selection ) {
        // TODO Auto-generated method stub

    }

    @Override
    public void goBack () {
        new Customer().run();
    }

}
