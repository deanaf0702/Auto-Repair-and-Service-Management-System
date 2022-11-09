package AutoCenter.mechanic;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.repository.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import AutoCenter.LoginUser;
import AutoCenter.ScanHelper;
import AutoCenter.UIHelpers;

/**
 * Handles functionality for accept reject swap menu of mechanics including
 * display, input, and
 * output.
 *
 * @author jkersey
 */
public class AcceptRejectSwap implements UserFlowFunctionality {

    /**
     * The minimum selection for the menu options range
     */
    private static final int MIN_SELECTION = 1;

    /**
     * The maximum selection for the menu options range
     */
    private static final int MAX_SELECTION = 2;

    /**
     * The separator to use between the menu title and the options
     */
    private static final String MENU_SEPARATOR = "###############################################";

    @Override
    public void run() {
        int selection;
        do {
            displayDetails();
            display();
            System.out.print("Enter choice (" + MIN_SELECTION + "-" + MAX_SELECTION
                    + ") from the given options displayed above: ");
            selection = ScanHelper.nextInt();
        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));
        navigate(selection);
    }

    @Override
    public void display() {
        String[] menuOptions = {
                "# 1 Manage swap requests                      #",
                "# 2 Go Back                                   #",
        };

        UIHelpers.displayMenu(" Mechanic: Accept / Reject Swap Menu ", menuOptions, MENU_SEPARATOR);
    }

    private void displayDetails() {
        System.out.println("Detailed below are the swap requests that you have received.");

        // Establish connection to database
        final DbConnection db = new DbConnection();
        final Connection conn = db.getConnection();
        ResultSet rsSwapRequests = null;
        ResultSet rsRequestingMechanic = null;
        Statement stmt = null;
        Statement stmt2 = null;

        try {
            stmt = conn.createStatement();
            stmt2 = conn.createStatement();

            // Query the database for the swap requests to the current mechanic
            // deepcode ignore NoStringConcat: <no need to use string builder>
            rsSwapRequests = stmt.executeQuery(currentMechanicSwapRequests());

            // Display the swap requests
            if (rsSwapRequests.next()) {
                System.out.println(
                        "Swap Request ID\t\tRequesting Mechanic\t\tRequested Timeslot Range");
                do {
                    // Requested week
                    Integer requestedWeek = rsSwapRequests.getInt("week2");
                    // Requested day
                    Integer requestedDay = rsSwapRequests.getInt("day2");
                    // Requested range start
                    Integer requestedRangeStart = rsSwapRequests.getInt("timeSlot2Start");
                    // Requested range end
                    Integer requestedRangeEnd = rsSwapRequests.getInt("timeSlot2End");

                    String requestedRange = UIHelpers.convertToStartDate(requestedWeek, requestedDay,
                            requestedRangeStart)
                            + " - " + UIHelpers.convertToEndDate(requestedWeek, requestedDay, requestedRangeEnd);

                    Integer requestingMechanicId = rsSwapRequests.getInt("mechanicId1");

                    // Query the database for the requesting mechanic's name
                    // deepcode ignore NoStringConcat: <no need to use string builder>
                    rsRequestingMechanic = stmt2
                            .executeQuery(requestingMechanicNameQuery(requestingMechanicId));
                    // move the cursor to the first row
                    rsRequestingMechanic.next();

                    String requestingMechanicName = rsRequestingMechanic.getString("firstName") + " "
                            + rsRequestingMechanic.getString("lastName");

                    System.out.println(rsSwapRequests.getString("requestId") + "\t\t"
                            + requestingMechanicName + "\t\t" + requestedRange);
                } while (rsSwapRequests.next());
            } else {
                System.out.println("You have no incoming swap requests. Check back later.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (rsSwapRequests != null) {
                try {
                    rsSwapRequests.close();
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rsRequestingMechanic != null) {
                try {
                    rsRequestingMechanic.close();
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt2 != null) {
                try {
                    stmt2.close();
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                conn.close();
            } catch (final SQLException e) {
                e.printStackTrace();
            }
            db.close();
        }
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                new ManageSwapRequests().run();
                break;
            case 2:
                goBack();
                break;
            default:
                System.out.println("Invalid selection.");
                break;
        }
    }

    @Override
    public void goBack() {
        new Mechanic().run();
    }

    private static String currentMechanicSwapRequests() {
        return "SELECT requestId, mechanicId1, week2, day2, timeSlot2Start, timeSlot2End"
                + " FROM SwapRequests"
                + " WHERE mechanicId2 = " + LoginUser.getId()
                + " AND status = 0";
    }

    private static String requestingMechanicNameQuery(int mechanicId1) {
        return "SELECT firstName, lastName"
                + " FROM Users"
                + " WHERE userId = " + mechanicId1;
    }
}
