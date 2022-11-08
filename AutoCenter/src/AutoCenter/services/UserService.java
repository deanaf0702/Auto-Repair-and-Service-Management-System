package AutoCenter.services;

import java.sql.ResultSet;

import AutoCenter.Home;
import AutoCenter.LoginUser;
import AutoCenter.repository.DbConnection;

public class UserService {

    public UserService() {
    }

    /**
     * Handles the login functionality for the user given the username and password.
     *
     * @param userId   the username of the user
     * @param password the password of the user
     * @return the user type of the user if the login was successful, otherwise null
     */
    public LoginUser authenticate(final String userId, final String password) {
        LoginUser currentUser = null;
        // return administrator, manager, receptionist, mechanic or customer
        final String query = "SELECT userId, firstName, lastName, role, serviceCenterId"
                + " FROM Users"
                + " WHERE username='" + userId + "'"
                + " AND password='" + password + "'";
        final DbConnection db = new DbConnection();

        try {
            final ResultSet rs = db.executeQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    currentUser = new LoginUser();
                    LoginUser.setId(rs.getInt("userId"));
                    LoginUser.setCenterId(rs.getInt("serviceCenterId"));
                    LoginUser.setFirstName(rs.getString("firstName").trim());
                    LoginUser.setLastName(rs.getString("lastName").trim());
                    LoginUser.setRole(rs.getString("role").trim());
                }
            }
        } catch (final Throwable oops) {
            oops.printStackTrace();
            System.out.println("Not found In Managers.");
        } finally {
            db.close();
        }
        return currentUser;
    }

    /**
     * Handles the logout functionality for the user.
     */
    public void logout() {
        if (Home.getUser() != null) {
            Home.setUser(null);
        }
        Home.run();
    }

    /**
     * Returns the centerId of the user.
     *
     * @return the centerId of the user
     */
    public int getCenterId() {
        final LoginUser user = Home.getUser();
        if (user == null) {
            return 0;
        } else {
            return LoginUser.getCenterId();
        }
    }

}
