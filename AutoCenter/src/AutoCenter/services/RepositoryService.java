package AutoCenter.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import AutoCenter.Home;
import AutoCenter.LoginUser;
import AutoCenter.ScanHelper;
import AutoCenter.models.User;
import AutoCenter.repository.DbConnection;
import AutoCenter.repository.UserRepository;
import AutoCenter.models.RepairService;
import AutoCenter.models.Service;

public class RepositoryService {

    public RepositoryService() {
    }

    public int getVehicleManfId(String VName) {
        int vId = 0;
        DbConnection db = new DbConnection();
        String query = "select vehicleManfId from VehicleManufacturers where manufacturerName='" + VName + "'";
        try {

            ResultSet rs = db.executeQuery(query);
            rs.next();
            vId = rs.getInt("vehicleManfId");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return vId;
    }

    public int getPrimaryKey(String table, String pkColumn) {
        DbConnection db = new DbConnection();
        String query = "select " + pkColumn + " from (select e.*, max(" + pkColumn + ")"
                + "over () as max_" + pkColumn + " from " + table + " e) "
                + "where " + pkColumn + " = max_" + pkColumn;
        ResultSet rs = db.executeQuery(query);

        int lastPK = 0;
        try {
            while (rs.next()) {
                lastPK = rs.getInt(pkColumn);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            db.close();
        }
        return lastPK + 1;
    }

    public List<String> carModelLookup() {
        DbConnection db = new DbConnection();
        String query = "Select * from CarModels";
        List<String> list = new ArrayList<String>();
        try {
            ResultSet rs = db.executeQuery(query);
            while (rs.next()) {
                String model = rs.getString("model").trim();
                list.add(model);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public double getServicePrice(int centerId, String model, int priceTier) {
        double price = 0.0;
        try {
            DbConnection db = new DbConnection();
            String query = "Select * from Prices Where centerId="
                    + centerId + " and model='" + model + "' and priceTier=" + priceTier;
            try {
                ResultSet rs = db.executeQuery(query);
                rs.next();
                price = rs.getDouble("dollar");

            } finally {
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return price;
        }
        return price;
    }

    public int findServiceId(List<Service> list, String search) {
        for (Service item : list) {
            if (item.getName().equals(search)) {
                return item.getServiceId();
            }
        }
        return 0;
    }

    public Map<Integer, Service> ServiceLookup(String query) {
    	Map<Integer, Service> list = new HashMap<Integer, Service>();
        try {
            DbConnection db = new DbConnection();

            try {
                ResultSet rs = db.executeQuery(query);
                int count = 0;
                while (rs.next()) {
                    Service ms = new Service();
                    ms.setServiceId(rs.getInt("serviceId"));
                    ms.setName(rs.getString("name").trim());
                    list.put(count, ms);
                    count++;
                }
            } finally {
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public HashMap<Integer, String> RepairCategoryLookup()
    {
    	HashMap<Integer, String> categories = new HashMap<Integer, String>();
    	String query = "select * from RepairServiceCategory";
        try {
            DbConnection db = new DbConnection();
            try {
                ResultSet rs = db.executeQuery(query);
                int count = 0;
                while (rs.next()) {
                	categories.put(count, rs.getString("category").trim());
                	count++;
                }
            } finally {
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return categories;
    }
    public boolean addService(String name, String category)
    {
    	boolean valid = false;
    	String query = "insert into Services (serviceId, Name, ServiceType) values(?, ?, 'Repair')";
    	String repairQuery = "insert into RepairServices (serviceId, category) values(?, ?)";
    	try {
    		DbConnection db = new DbConnection();
    		PreparedStatement stmt = db.getConnection().prepareStatement(query);
    		int id = getPrimaryKey("Services", "serviceId");
    		stmt.setInt(1, id);
    		stmt.setString(2, name);
    		int result = stmt.executeUpdate();
    		stmt.close();
    		if(result > 0) {
    			PreparedStatement stmt2 = db.getConnection().prepareStatement(repairQuery);
    			stmt2.setInt(1, id);
        		stmt2.setString(2, category.trim());
        		int result2 = stmt2.executeUpdate();
        		if(result2 > 0) valid = true;
        		stmt2.close();
    		}	
    	}catch(Exception e ) {
    		e.printStackTrace();
    		return false;
    	}
    	return valid;
    }
    public List<RepairService> repairServiceLookup() {
        List<RepairService> list = new ArrayList<RepairService>();
        String query = "select * from RepairServices";
        try {
            DbConnection db = new DbConnection();
            try {
                ResultSet rs = db.executeQuery(query);

                while (rs.next()) {
                    RepairService rps = new RepairService();
                    rps.setServiceId(rs.getInt("serviceId"));
                    rps.setCategory(rs.getString("category").trim());
                    rps.setName(rs.getString("name").trim());
                    rps.setHours(rs.getInt("hours"));
                    list.add(rps);
                }
            } finally {
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public int getCenterId() {
        LoginUser user = Home.getUser();
        if (user == null)
            return 0;
        else
            return user.getCenterId();
    }

    public boolean validateMinAndMaxWage(double wage) {
        boolean valid = false;
        int centerId = getCenterId();
        String query = "Select * from ServiceCenters where centerId=" + centerId;
        try {
            DbConnection db = new DbConnection();
            try {
                ResultSet rs = db.executeQuery(query);
                rs.next();
                double min = rs.getFloat("minWage");
                double max = rs.getFloat("maxWage");
                if (wage >= min && wage <= max)
                    valid = true;

            } finally {
                db.close();
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return valid;
    }

    // Repository
    public boolean addUser(User user) {
        user.setServiceCenterId(getCenterId());
        if (user.getRole().equals("Mechanic")) {
            if (!(validateMinAndMaxWage(user.getSalaryOrWage()))) {
                System.out.println("This isn't in the range of minimum or maximum wage.");
                return false;
            }

        }

        UserRepository er = new UserRepository();
        boolean result = false;
        try {
            result = er.add(user);
        } catch (Exception e) {
            System.out.println("Went Wrong!");

        }
        return result;
    }
}
