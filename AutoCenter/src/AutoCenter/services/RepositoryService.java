package AutoCenter.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import AutoCenter.Home;
import AutoCenter.LoginUser;
import AutoCenter.models.RepairService;
import AutoCenter.models.Service;
import AutoCenter.models.User;
import AutoCenter.repository.DbConnection;
import AutoCenter.repository.UserRepository;

public class RepositoryService {

    public RepositoryService () {
    }

    public int getVehicleManfId ( final String VName ) {
        int vId = 0;
        final DbConnection db = new DbConnection();
        final String query = "select vehicleManfId from VehicleManufacturers where manufacturerName='" + VName + "'";
        try {

            final ResultSet rs = db.executeQuery( query );
            rs.next();
            vId = rs.getInt( "vehicleManfId" );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }
        finally {
            db.close();
        }
        return vId;
    }

    public int getPrimaryKey ( final String table, final String pkColumn ) {
        final DbConnection db = new DbConnection();
        final String query = "select " + pkColumn + " from (select e.*, max(" + pkColumn + ")" + "over () as max_"
                + pkColumn + " from " + table + " e) " + "where " + pkColumn + " = max_" + pkColumn;
        final ResultSet rs = db.executeQuery( query );

        int lastPK = 0;
        try {
            while ( rs.next() ) {
                lastPK = rs.getInt( pkColumn );
            }
        }
        catch ( final SQLException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            db.close();
        }
        return lastPK + 1;
    }

    public List<String> carModelLookup () {
        final DbConnection db = new DbConnection();
        final String query = "Select * from CarModels";
        final List<String> list = new ArrayList<String>();
        try {
            final ResultSet rs = db.executeQuery( query );
            while ( rs.next() ) {
                final String model = rs.getString( "model" ).trim();
                list.add( model );
            }
            db.close();
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public double getServicePrice ( final int centerId, final String model, final int priceTier ) {
        double price = 0.0;
        try {
            final DbConnection db = new DbConnection();
            final String query = "Select * from Prices Where centerId=" + centerId + " and model='" + model
                    + "' and priceTier=" + priceTier;
            try {
                final ResultSet rs = db.executeQuery( query );
                rs.next();
                price = rs.getDouble( "dollar" );

            }
            finally {
                db.close();
            }
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return price;
        }
        return price;
    }

    public int findServiceId ( final List<Service> list, final String search ) {
        for ( final Service item : list ) {
            if ( item.getName().equals( search ) ) {
                return item.getServiceId();
            }
        }
        return 0;
    }

    public Map<Integer, Service> ServiceLookup ( final String query ) {
        final Map<Integer, Service> list = new HashMap<Integer, Service>();
        try {
            final DbConnection db = new DbConnection();

            try {
                final ResultSet rs = db.executeQuery( query );
                int count = 0;
                while ( rs.next() ) {
                    final Service ms = new Service();
                    ms.setServiceId( rs.getInt( "serviceId" ) );
                    ms.setName( rs.getString( "name" ).trim() );
                    list.put( count, ms );
                    count++;
                }
            }
            finally {
                db.close();
            }
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public HashMap<Integer, String> RepairCategoryLookup () {
        final HashMap<Integer, String> categories = new HashMap<Integer, String>();
        final String query = "select * from RepairServiceCategory";
        try {
            final DbConnection db = new DbConnection();
            try {
                final ResultSet rs = db.executeQuery( query );
                int count = 0;
                while ( rs.next() ) {
                    categories.put( count, rs.getString( "category" ).trim() );
                    count++;
                }
            }
            finally {
                db.close();
            }
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return null;
        }
        return categories;
    }

    public boolean addService ( final String name, final String category ) {
        boolean valid = false;
        final String query = "insert into Services (serviceId, Name, ServiceType) values(?, ?, 'Repair')";
        final String repairQuery = "insert into RepairServices (serviceId, category) values(?, ?)";
        try {
            final DbConnection db = new DbConnection();
            final PreparedStatement stmt = db.getConnection().prepareStatement( query );
            final int id = getPrimaryKey( "Services", "serviceId" );
            stmt.setInt( 1, id );
            stmt.setString( 2, name );
            final int result = stmt.executeUpdate();
            stmt.close();
            if ( result > 0 ) {
                final PreparedStatement stmt2 = db.getConnection().prepareStatement( repairQuery );
                stmt2.setInt( 1, id );
                stmt2.setString( 2, category.trim() );
                final int result2 = stmt2.executeUpdate();
                if ( result2 > 0 ) {
                    valid = true;
                }
                stmt2.close();
            }
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return false;
        }
        return valid;
    }

    public List<RepairService> repairServiceLookup () {
        final List<RepairService> list = new ArrayList<RepairService>();
        final String query = "select * from RepairServices";
        try {
            final DbConnection db = new DbConnection();
            try {
                final ResultSet rs = db.executeQuery( query );

                while ( rs.next() ) {
                    final RepairService rps = new RepairService();
                    rps.setServiceId( rs.getInt( "serviceId" ) );
                    rps.setCategory( rs.getString( "category" ).trim() );
                    rps.setName( rs.getString( "name" ).trim() );
                    rps.setHours( rs.getInt( "hours" ) );
                    list.add( rps );
                }
            }
            finally {
                db.close();
            }
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public int getCenterId () {
        final LoginUser user = Home.getUser();
        if ( user == null ) {
            return 0;
        }
        else {
            return user.getCenterId();
        }
    }

    public boolean validateMinAndMaxWage ( final double wage ) {
        boolean valid = false;
        final int centerId = getCenterId();
        final String query = "Select * from ServiceCenters where centerId=" + centerId;
        try {
            final DbConnection db = new DbConnection();
            try {
                final ResultSet rs = db.executeQuery( query );
                rs.next();
                final double min = rs.getFloat( "minWage" );
                final double max = rs.getFloat( "maxWage" );
                if ( wage >= min && wage <= max ) {
                    valid = true;
                }

            }
            finally {
                db.close();
            }
        }
        catch ( final Exception e ) {
            System.out.println( e );
            return false;
        }
        return valid;
    }

    // Repository s
    public boolean addUser ( final User user ) {
        user.setServiceCenterId( getCenterId() );
        if ( user.getRole().equals( "Mechanic" ) ) {
            if ( ! ( validateMinAndMaxWage( user.getSalaryOrWage() ) ) ) {
                System.out.println( "This isn't in the range of minimum or maximum wage." );
                return false;
            }

        }

        final UserRepository er = new UserRepository();
        boolean result = false;
        try {
            result = er.add( user );
        }
        catch ( final Exception e ) {
            System.out.println( "Went Wrong!" );

        }
        return result;
    }
}
