package AutoCenter.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import AutoCenter.models.User;
import AutoCenter.services.UserService;

public class UserRepository {

    protected UserService userService = null;
    static final String TABLE = "users";
    static final String PKCOLUMN = "userId";

    public UserRepository() {
        userService = new UserService();
    }

    public boolean add(User user) {
        boolean valid = false;
        // user.userId = getPrimaryKey();
        user.setServiceCenterId(userService.getCenterId());
        String userQuery = addUserQuery(user);
        DbConnection db = new DbConnection();

        try {
            boolean result1 = db.executeUpdate(userQuery);

            if (!result1) {
                return valid;
            }
            String childQuery = null;
            if (user.getRole().equals("Manager")) {
                childQuery = addManagerQuery(user.getUserId(), user.getSalaryOrWage(), user.getServiceCenterId());

            } else if (user.getRole().equals("Receptionist")) {
                childQuery = addReceptionistQuery(user.getUserId(), user.getSalaryOrWage(), user.getServiceCenterId());
            } else if (user.getRole().equals("Mechanic")) {
                childQuery = addMechanicQuery(user.getUserId(), user.getSalaryOrWage(), user.getServiceCenterId());
            } else if (user.getRole().equals("Customer"))
                if (childQuery.isEmpty()) {
                    return valid;
                }
            boolean result2 = db.executeUpdate(childQuery);
            if (result2)
                valid = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return valid;

    }

    public User details(int userId) {
        if (userId <= 0)
            return null;
        User u = new User();
        String query = selectUserQuery(userId);
        DbConnection db = new DbConnection();
        try {
            ResultSet rs = db.executeQuery(query);
            while (rs.next()) {
                u.setUserId(rs.getInt(PKCOLUMN));
                u.setServiceCenterId(rs.getInt("serviceCenterId"));
                u.setUsername(rs.getString("username").trim());
                u.setFirstName(rs.getString("firstName").trim());
                u.setLastName(rs.getString("lastName").trim());
                u.setAddress(rs.getString("address").trim());
                u.setEmail(rs.getString("email").trim());
                u.setPhone(rs.getString("phone").trim());
                u.setRole(rs.getString("role").trim());
            }
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } finally {
            db.close();
        }

        return u;
    }

    public String deleteUserQuery(int userId) {
        return "select * from users where userId="
                + userId;
    }

    public String selectUserQuery(int userId) {
        return "select * from users where userId="
                + userId;
    }

    public String addManagerQuery(int userId, double salary, int serviceCenterId) {
        return "insert into Managers (userId, serviceCenterId, salary) values("
                + userId + ", "
                + serviceCenterId + ", "
                + salary
                + ")";
    }

    public String addReceptionistQuery(int userId, double salary, int serviceCenterId) {
        return "insert into Receptionists (userId, serviceCenterId, salary) values("
                + userId + ", "
                + serviceCenterId + ", "
                + salary
                + ")";
    }

    public String addMechanicQuery(int userId, double wage, int serviceCenterId) {
        return "insert into Mechanics (userId, wage) values("
                + userId + ", "
                + serviceCenterId + ", "
                + wage
                + ")";
    }

    public String addUserQuery(User user) {

        return "insert into users (userId, serviceCenterId, role, "
                + "userName, password, firstName, lastName, address, "
                + "email, phone) values("
                + user.getUserId() + ",  "
                + user.getServiceCenterId() + ", '"
                + user.getRole() + "', '"
                + user.getUsername() + "', '"
                + user.getLastName() + "', '"
                + user.getFirstName() + "', '"
                + user.getPassword() + "', '"
                + user.getAddress() + "', '"
                + user.getEmail() + "', '"
                + user.getPhone() + "'";
    }

}
