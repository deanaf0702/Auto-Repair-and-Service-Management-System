package AutoCenter.mechanic;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.models.User;
import AutoCenter.repository.DbConnection;

import java.sql.*;

import AutoCenter.Home;
import AutoCenter.LoginUser;
import AutoCenter.ScanHelper;
import AutoCenter.UIHelpers;

/**
 * Handles functionality for view schedule menu of mechanics including display,
 * input, and output.
 *
 * @author jkersey
 */
public class ViewSchedule implements UserFlowFunctionality {

    /*
     * The minimum selection for the menu options range
     */
    private static final int MIN_SELECTION = 1;

    /*
     * The maximum selection for the menu options range
     */
    private static final int MAX_SELECTION = 1;

    /*
     * The separator to use between the menu title and the options
     */
    private static final String MENU_SEPARATOR = "###################################";

    @Override
    public void run() {
        int selection;
        displayDetails();
        display();

        do {
            System.out.print("Enter choice (" + MIN_SELECTION + "-" + MAX_SELECTION
                    + ") from the given options displayed above: ");
            selection = ScanHelper.nextInt();
        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));

        navigate(selection);
    }

    private String viewScheduleQuery() {
        return "select week, day, timeSlot from Schedule where mechanicId = " + Home.getUser().getId()
                + " and centerId = "
                + Home.getUser().getCenterId();
    }

    private void displayDetails() {
        // Establish connection to database
        final DbConnection db = new DbConnection();
        final Connection conn = db.getConnection();
        ResultSet rs = null;
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            // file deepcode ignore NoStringConcat: <not needed>
            rs = stmt.executeQuery(viewScheduleQuery());
            System.out.println("Your schedule is as follows:");
            int scheduleCount = 1;
            while (rs.next()) {
                System.out.println(scheduleCount + ". Week: " + rs.getInt("week") + ", Day: "
                        + rs.getString("day") + ", Time Slot: " + rs.getString("timeSlot"));
                scheduleCount++;
            }

            if (scheduleCount == 1) {
                System.out.println("You have no upcoming shifts.");
            }

            while (rs.next()) {
                System.out.println("VIN: " + rs.getString("vin"));
                System.out.println("Manufacturer: " + rs.getString("manufacturer"));
                System.out.println("Model: " + rs.getString("model"));
                System.out.println("Year: " + rs.getString("year"));
                System.out.println("Mileage: " + rs.getString("mileage"));
                System.out.println("Last Maintenance Class: " + rs.getString("lastMClass"));
            }
        } catch (final SQLException e) {
            // file deepcode ignore DontUsePrintStackTrace: <not needed>
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (final SQLException e) {
                e.printStackTrace();
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            }
            db.close();
        }
    }

    @Override
    public void display() {
        String[] menuOptions = {
                "# 1 Go back                       #",
        };

        UIHelpers.displayMenu(" Mechanic: View Schedule ", menuOptions, MENU_SEPARATOR);
    }

    @Override
    public void navigate(int selection) {
        if (selection == 1) {
            goBack();
        } else {
            System.out.println("Invalid selection.");
        }
    }

    @Override
    public void goBack() {
        new Mechanic().run();
    }
}
