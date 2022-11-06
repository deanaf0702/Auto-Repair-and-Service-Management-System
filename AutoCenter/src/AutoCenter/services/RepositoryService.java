package AutoCenter.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import AutoCenter.Home;
import AutoCenter.LoginUser;
import AutoCenter.ScanHelper;
import AutoCenter.models.User;
import AutoCenter.repository.DbConnection;
import AutoCenter.repository.UserRepository;
import AutoCenter.models.MaintenanceService;
import AutoCenter.models.RepairService;

public class RepositoryService {
	public RepositoryService() {}
	
	public int getVehicleManfId(String VName)
	{
		int VId = 0;
		DbConnection db = new DbConnection();
		String query = "select vehicleManfId from VehicleManufacturers where manufacturerName='" + VName +"'";
		try {
			
			ResultSet rs = db.executeQuery(query);
			rs.next();
			VId = rs.getInt("vehicleManfId");
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			db.close();
		}
		return VId;
	}
	
	public int getPrimaryKey(String table, String pkColumn) 
	{
		DbConnection db = new DbConnection();
		String query = "select "+ pkColumn +" from (select e.*, max(" + pkColumn + ")"
				+ "over () as max_" + pkColumn + " from " + table + " e) "
				+ "where " + pkColumn + " = max_" + pkColumn;
		ResultSet rs = db.executeQuery(query);
		
		int lastPK = 0; 
		try {
			while(rs.next())
			{
				lastPK = rs.getInt(pkColumn);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			db.close();
		}
		return lastPK+1;
	}
	public List<String> carModelLookup()
	{
		DbConnection db = new DbConnection();
		String query = "Select * from CarModels";
		List<String> list = new ArrayList<String>();
		try {
			ResultSet rs = db.executeQuery(query);
			while(rs.next()) {
				String model = rs.getString("model").trim();
				list.add(model);
			}
			db.close();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	public double getServicePrice(int centerId, String model, int priceTier)
	{
		double price = 0.0;
		try{
			DbConnection db = new DbConnection();
			String query = "Select * from Prices Where centerId=" 
			+ centerId + " and model='" + model + "' and priceTier=" + priceTier;
			try {
				ResultSet rs =  db.executeQuery(query);
				rs.next();
				price = rs.getDouble("dollar");
				
			}finally {
				db.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
			return price;
		}
	return price;	
	}
	public List<MaintenanceService> maintServiceLookup()
	{
		List<MaintenanceService> list = new ArrayList<MaintenanceService>();
		try{
			DbConnection db = new DbConnection();
			String query = "Select * from MaintenanceServices";
			
			try {
				ResultSet rs =  db.executeQuery(query);
				while(rs.next()) {
					MaintenanceService ms = new MaintenanceService();
					ms.setServiceId(rs.getInt("serviceId"));
					ms.setScheduleType(rs.getString("scheduleType").trim());
					ms.setServiceId(rs.getInt("hours"));
					list.add(ms);
				}
			}finally {
				db.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	return list;	
	}
	public List<RepairService> repairServiceLookup()
	{
		List<RepairService> list = new ArrayList<RepairService>();
		String query = "select * from RepairServices";
		try {
			DbConnection db = new DbConnection();
			try {
				ResultSet rs = db.executeQuery(query);
				
				while(rs.next())
				{
					RepairService rps = new RepairService();
					rps.setServiceId(rs.getInt("serviceId"));
					String category = rs.getString("category");
					rps.setCategory(rs.getString("category").trim());
					rps.setName(rs.getString("name").trim());
					rps.setHours(rs.getInt("hours"));
					list.add(rps);
				}
			}finally {
				db.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	public int getCenterId()
	{
		LoginUser user = Home.getUser();
		if(user == null) return 0;
		else return user.getCenterId();
	}
	public boolean validateMinAndMaxWage(double wage)
	{
		boolean valid = false;
		int centerId = getCenterId();
		String query = "Select * from ServiceCenters where centerId="+ centerId;
		try {
			DbConnection db = new DbConnection();
			try {
				ResultSet rs = db.executeQuery(query);
				rs.next();
				double min = rs.getFloat("minWage");
				double max = rs.getFloat("maxWage");
				if(wage >= min && wage <= max)
					valid = true;
				
			}finally {
				db.close();
			}
		}catch(Exception e) {
			System.out.println(e);
			return false;
		}
		return valid;
	}
	//Repository
	public boolean addUser(User user)
	{
		user.setServiceCenterId(getCenterId());
		if(user.getRole().equals("Mechanic")) {
			if(!(validateMinAndMaxWage(user.getSalaryOrWage()))) {
				System.out.println("This isn't in the range of minimum or maximum wage.");
				return false;
			}
				
		}
		
		UserRepository er = new UserRepository();
		boolean result= false;
		try {
			result = er.add(user);
		}catch(Exception e) {
			System.out.println("Went Wrong!");
			
		}
		return result;
	}		
}
