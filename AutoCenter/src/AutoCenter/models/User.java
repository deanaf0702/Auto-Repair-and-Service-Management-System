package AutoCenter.models;

public class User {
  private int userId;
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private String address;
  private String email;
  private String phone;
  private String role;
  private int serviceCenterId;
  private double salaryOrWage;
public int getUserId() {
	return userId;
}
public void setUserId(int userId) {
	this.userId = userId;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public double getSalaryOrWage() {
	return salaryOrWage;
}
public void setSalaryOrWage(double salaryOrWage) {
	this.salaryOrWage = salaryOrWage;
}
public int getServiceCenterId() {
	return serviceCenterId;
}
public void setServiceCenterId(int serviceCenterId) {
	this.serviceCenterId = serviceCenterId;
}

  
}
