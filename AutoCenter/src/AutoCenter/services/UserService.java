package AutoCenter.services;

import java.sql.ResultSet;

import AutoCenter.Home;
import AutoCenter.LoginUser;
import AutoCenter.repository.DbConnection;

public class UserService {

    private RepositoryService repoService = null;

    public UserService () {
        repoService = new RepositoryService();
    }

    public LoginUser authenticate ( final String userId, final String password ) {
        LoginUser currentUser = null;
        // return administrator, manager, receptionist, mechanic or customer
        final String query = "SELECT userId, firstName, lastName, role, serviceCenterId" + " FROM Users "
                + "Where username='" + userId + "' AND password='" + password + "'";
        final DbConnection db = new DbConnection();

        try {
            final ResultSet rs = db.executeQuery( query );
            if ( rs != null ) {
                while ( rs.next() ) {
                    currentUser = new LoginUser();
                    currentUser.setId( rs.getInt( "userId" ) );
                    currentUser.setCenterId( rs.getInt( "serviceCenterId" ) );
                    currentUser.setFirstName( rs.getString( "firstName" ).trim() );
                    currentUser.setLastName( rs.getString( "lastName" ).trim() );
                    currentUser.setRole( rs.getString( "role" ).trim() );
                }
            }
        }
        catch ( final Throwable oops ) {
            oops.printStackTrace();
            System.out.println( "Not found In Managers." );
        }
        finally {
            db.close();
        }
        return currentUser;
    }

    public void logout () {
        if ( Home.getUser() != null ) {
            Home.setUser( null );
        }
        Home.run();

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

}
