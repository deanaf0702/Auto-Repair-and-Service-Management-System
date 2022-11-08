package AutoCenter.customer;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import AutoCenter.LoginUser;
import AutoCenter.ScanHelper;
import AutoCenter.UIHelpers;
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
        int totalHours = 0;
        for ( final int id : cart ) {
            final String query2 = "select hours from Prices where centerId = " + centerId + " and serviceId = " + id
                    + " and model = '" + model + "'";
            final DbConnection db2 = new DbConnection();
            try {
                final ResultSet rs = db2.executeQuery( query2 );
                rs.next();
                totalHours += rs.getInt( 1 );
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
        query = "select userId from Mechanics where serviceCenterId = " + centerId;
        db = new DbConnection();
        final List<Integer> mechanicIds = new ArrayList<Integer>();
        try {
            final ResultSet rs = db.executeQuery( query );
            while ( rs.next() ) {
                mechanicIds.add( rs.getInt( 1 ) );
            }
        }
        catch ( final Exception e ) {
            System.out.println( "Error retrieving available times" );
            goBack();
        }
        finally {
            db.close();
        }
        final Map<Integer, List<Integer>> m = getAvailableTimeSlots( totalHours, mechanicIds, centerId, satOpen );
        System.out.println( "Available time slots:" );
        for ( int i = 1; i <= m.size(); i++ ) {
            final List<Integer> li = m.get( i );
            System.out.println( i + ": " + UIHelpers.convertToStartDate( li.get( 0 ), li.get( 1 ), li.get( 2 ) ) + " - "
                    + UIHelpers.convertToEndDate( li.get( 0 ), li.get( 1 ), li.get( 3 ) ) + ", Mechanic "
                    + li.get( 4 ) );

        }
        System.out.println( ( m.size() + 1 ) + ": Go Back" );
        int selection = 0;
        do {
            System.out.print( "Enter choice from the given options displayed above: " );
            selection = ScanHelper.nextInt();
        }
        while ( ! ( selection >= 1 && selection <= m.size() + 1 ) );
        if ( selection == m.size() + 1 ) {
            goBack();
        }
        else {
            final List<Integer> info = m.get( selection );
            final String query1 = "select SERVICE_EVENT_ID_SEQ.nextval from dual";
            db = new DbConnection();
            int nextVal = 0;
            try {
                final ResultSet rs = db.executeQuery( query );
                rs.next();
                nextVal = rs.getInt( 1 );
            }
            catch ( final Exception e ) {
                System.out.println( "Error retrieving vehicles" );
                goBack();
            }
            finally {
                db.close();
            }
            final String query2 = "insert into ServiceEvents values (" + nextVal + ", " + vin + ", " + centerId + ", "
                    + info.get( 4 ) + ", " + info.get( 0 ) + ", " + info.get( 1 ) + ", " + info.get( 2 ) + ", "
                    + info.get( 3 ) + ", 0)";
            db = new DbConnection();
            try {
                db.executeUpdate( query2 );
            }
            catch ( final Exception e ) {
                System.out.println( "Error retrieving vehicles" );
                goBack();
            }
            finally {
                db.close();
            }
            for ( final Integer item : cart ) {
                final String query3 = "insert into EventOnServices values (" + nextVal + ", " + item + ")";
                db = new DbConnection();
                try {
                    db.executeUpdate( query3 );
                }
                catch ( final Exception e ) {
                    System.out.println( "Error retrieving vehicles" );
                    goBack();
                }
                finally {
                    db.close();
                }
            }
            for ( int i = info.get( 2 ); i <= info.get( 3 ); i++ ) {
                final String query4 = "insert into Schedule values (" + info.get( 4 ) + ", " + centerId + ", "
                        + info.get( 0 ) + ", " + info.get( 1 ) + ", " + i + ", 'Work', " + nextVal + ")";
                try {
                    db.executeUpdate( query4 );
                }
                catch ( final Exception e ) {
                    System.out.println( "Error retrieving vehicles" );
                    goBack();
                }
                finally {
                    db.close();
                }
            }
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
        new Customer().run();
    }

    public Map<Integer, List<Integer>> getAvailableTimeSlots ( final int totalHours, final List<Integer> mechanicIds,
            final int centerId, final int satOpen ) {
        int numRows = 1;
        final Map<Integer, List<Integer>> results = new HashMap<Integer, List<Integer>>();
        for ( int week = 1; week <= 4; week++ ) {
            for ( int day = 1; day <= 5; day++ ) {
                for ( int timeSlot = 1; timeSlot <= 11; timeSlot++ ) {
                    for ( final int mechanic : mechanicIds ) {
                        final int endTimeSlot = timeSlot + totalHours - 1;
                        if ( endTimeSlot <= 11 ) {
                            final String query = "select timeSlot from Schedule where mechanicId = " + mechanic
                                    + " and " + "centerId = " + centerId + " and " + "week = " + week + " and "
                                    + "day = " + day + " and " + "timeSlot >= " + timeSlot + "and timeSlot <= "
                                    + endTimeSlot;
                            final DbConnection db = new DbConnection();
                            try {
                                final ResultSet rs = db.executeQuery( query );
                                if ( !rs.next() && numRows <= 20 ) {
                                    final List<Integer> l = new ArrayList<Integer>();
                                    l.add( 0, week );
                                    l.add( 1, day );
                                    l.add( 2, timeSlot );
                                    l.add( 3, endTimeSlot );
                                    l.add( 4, mechanic );
                                    results.put( numRows, l );
                                    numRows++;
                                }
                                else if ( numRows > 20 ) {
                                    return results;
                                }
                            }
                            catch ( final Exception e ) {
                                System.out.println( "Error retrieving available times" );
                                goBack();
                            }
                            finally {
                                db.close();
                            }
                        }
                    }
                }
            }
        }
        if ( satOpen == 1 ) {
            for ( int week = 1; week <= 4; week++ ) {
                for ( int timeSlot = 1; timeSlot <= 4; timeSlot++ ) {
                    for ( final int mechanic : mechanicIds ) {
                        final int endTimeSlot = timeSlot + totalHours - 1;
                        if ( endTimeSlot <= 4 ) {
                            final String query = "select timeSlot from Schedule where mechanicId = " + mechanic
                                    + " and " + "centerId = " + centerId + " and " + "week = " + week + " and "
                                    + "day = " + 6 + " and " + "timeSlot >= " + timeSlot + "and timeSlot <= "
                                    + endTimeSlot;
                            final DbConnection db = new DbConnection();
                            try {
                                final ResultSet rs = db.executeQuery( query );
                                if ( !rs.next() && numRows <= 20 ) {
                                    final List<Integer> l = new ArrayList<Integer>();
                                    l.add( 0, week );
                                    l.add( 1, 6 );
                                    l.add( 2, timeSlot );
                                    l.add( 3, endTimeSlot );
                                    l.add( 4, mechanic );
                                    results.put( numRows, l );
                                    numRows++;
                                }
                                else if ( numRows > 20 ) {
                                    return results;
                                }
                            }
                            catch ( final Exception e ) {
                                System.out.println( "Error retrieving available times" );
                                goBack();
                            }
                            finally {
                                db.close();
                            }
                        }
                    }
                }
            }
        }
        return results;
    }
}
