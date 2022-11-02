package AutoCenter.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import AutoCenter.Home;
import AutoCenter.ScanHelper;
import AutoCenter.User;
import AutoCenter.repository.DbConnection;
import AutoCenter.models.MaintenanceService;

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
	public int[] getRepairServiceIds()
	{
		int[] repairIds= new int[12];
		DbConnection db = new DbConnection();
		String query = "select serviceId from RepairServices";
		try {
			
			ResultSet rs = db.executeQuery(query);
			int i = 0;
			while(rs.next())
			{
				repairIds[i] = rs.getInt("serviceId");
				i++;
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			db.close();
		}
		return repairIds;
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
				String model = rs.getString("model");
				list.add(model);
			}
			db.close();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	public boolean addCustomer(String[] inputs)
	{
		boolean valid = false;
		String firstName = inputs[0].trim();
		String lastName = inputs[1].trim();
		String address = inputs[3].trim();
		String email = inputs[4].trim();
		String phone = inputs[5].trim();
		String username = inputs[2].trim();
		String vinNumber = inputs[6].trim();
		String carManf = inputs[6].trim();
		int mileage = Integer.parseInt(inputs[6]);
		String year = inputs[6].trim();
		int status = '1';
		int active = '1';
		
		int centerId = getCenterId();
		int newCustomerId = getPrimaryKey("Customers", "customerId");
		
		if(centerId <= 0 || newCustomerId <= 0)
			return valid;
		
		String customerQuery = "insert into Customers (customerId, centerId, firstName, lastName, address, status, active) values("
				+ newCustomerId + ", "
				+ centerId +", "
				+ "'" + firstName + "', "
				+ "'"+ lastName + "', "
				+ "'" + address + "', "
				+ "'" + status +"', "
				+ "'" + active +"')";
		try {
			DbConnection db = new DbConnection();
			try {
				if(db.executeUpdate(customerQuery)) {
					String custVehicleQuery = "insert into CustomerVehicles (vin, customerId, vehicleManfId, mileage, year) values("
							+ "'" + vinNumber + "', "
							+  newCustomerId + ", "
							+ carManf + ", "
							+ mileage + ", "
							+ "'" + year + "')";
					
					if(db.executeUpdate(custVehicleQuery))
					{
						valid = true;
					}else valid = false;
					
				}else valid = false;
				
			}finally{
				db.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return valid;
	}
	public double getServicePrice(int centerId, String model, int priceTier)
	{
		double price = 0.0;
		try{
			DbConnection db = new DbConnection();
			String query = "Select * from Prices Where centerId=" 
			+ centerId + " and model='" + model + "', priceTier=" + priceTier;
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
					ms.setScheduleType(rs.getString("scheduleType"));
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
	public int getCenterId()
	{
		User user = Home.getUser();
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
	
}
