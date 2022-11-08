package AutoCenter.mechanic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import AutoCenter.ScanHelper;
import AutoCenter.UIHelpers;
import AutoCenter.UserFlowFunctionality;
import AutoCenter.repository.DbConnection;

/**
 * Handles functionality for manage swap requests menu of mechanics including
 * display, input, and
 * output.
 *
 * @author jkersey
 */
public class ManageSwapRequests implements UserFlowFunctionality {

    /**
     * The minimum selection for the menu options range
     */
    private static final int INITIAL_SELECTION = -1;

    /**
     * The minimum selection for the menu options range
     */
    private static final int MIN_SELECTION = 1;

    /**
     * The maximum selection for the menu options range
     */
    private static final int MAX_SELECTION = 3;

    /**
     * The separator to use between the menu title and the options
     */
    private static final String MENU_SEPARATOR = "###############################################";

    /**
     * The separator to use between the directions components
     */
    private static final String DIRECTION_SEPARATOR = "#############################";

    /**
     * The ID of the swap request to manage
     */
    private int swapRequestID;

    private int mechanicId1;
    private int mechanicId2;
    private int week1;
    private int week2;
    private int day1;
    private int day2;
    private int timeSlot1;
    private int timeSlot2;

    @Override
    public void run() {
        int selection;

        do {
            displayDirections();
            System.out.print(
                    "Please enter the ID of the swap request you would like to approve or reject (e.g. 1, 17, etc.): ");
            swapRequestID = ScanHelper.nextInt();
            boolean validInputs = validateSwapRequestId(swapRequestID);
            if (validInputs) {
                display();
                System.out.print("Enter choice (" + MIN_SELECTION + "-" + MAX_SELECTION
                        + ") from the given options displayed above: ");
                selection = ScanHelper.nextInt();
            } else {
                selection = INITIAL_SELECTION;
            }
        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));
        navigate(selection);
    }

    public void displayDirections() {
        String[] usageComponents = {
                "# A. The requestID                 #",
        };

        UIHelpers.displayUsageDirections(
                usageComponents,
                "###               1              ###",
                "   Request Swap: Usage    ",
                "   Request Swap: Example  ",
                DIRECTION_SEPARATOR);
    }

    @Override
    public void display() {
        String[] menuOptions = {
                "# 1 Accept swap                               #",
                "# 2 Reject swap                               #",
                "# 3 Go Back                                   #",
        };

        UIHelpers.displayMenu(" Mechanic: Manage Swap Requests Menu ", menuOptions, MENU_SEPARATOR);
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                acceptSwap();
                new AcceptRejectSwap().run();
                break;
            case 2:
                rejectSwap();
                new AcceptRejectSwap().run();
                break;
            case 3:
                goBack();
                break;
            default:
                System.out.println("Invalid selection.");
                break;
        }
    }

    @Override
    public void goBack() {
        new AcceptRejectSwap().run();
    }

    /**
     * Ensures that the swap request ID is valid and exists in the database. If the
     * swap request exists, then loads the swap request data into the class
     * variables.
     *
     * @return true if the swap request ID is valid and exists in the database,
     *         false otherwise
     */
    private boolean validateSwapRequestId(Integer swapRequestID) {
        // Establish connection to database
        final DbConnection db = new DbConnection();
        final Connection conn = db.getConnection();
        ResultSet rs = null;
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            // deepcode ignore NoStringConcat: <no need to use string builder>
            rs = stmt.executeQuery(swapRequestExistsQuery(swapRequestID));

            // If the query returns a result, the swap request exists
            // and the swap request data can be loaded into the class variables
            if (rs.next()) {
                mechanicId1 = rs.getInt("mechanicId1");
                mechanicId2 = rs.getInt("mechanicId2");
                week1 = rs.getInt("week1");
                week2 = rs.getInt("week2");
                day1 = rs.getInt("day1");
                day2 = rs.getInt("day2");
                timeSlot1 = rs.getInt("timeSlot1");
                timeSlot2 = rs.getInt("timeSlot2");
                return true;
            } else {
                System.out.println("Invalid swap request ID. The swap request ID does not exist. Please try again.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
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
            if (rs != null) {
                try {
                    rs.close();
                } catch (final SQLException e) {
                    e.printStackTrace();
                }
            }
            db.close();
        }

        return true;
    }

    private void acceptSwap() {
        // Establish connection to database
        final DbConnection db = new DbConnection();
        final Connection conn = db.getConnection();
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            // Update the swap request to be accepted
            // deepcode ignore NoStringConcat: <no need to use string builder>
            stmt.executeUpdate(acceptSwapStatement(swapRequestID));

            // Update the first mechanic's schedule to reflect the swap
            // deepcode ignore NoStringConcat: <no need to use string builder>
            stmt.executeUpdate(
                    updateMechanic1ShiftStatement(mechanicId1,
                            week1,
                            day1,
                            timeSlot1,
                            week2,
                            day2,
                            timeSlot2));

            // Update the second mechanic's schedule to reflect the swap
            // deepcode ignore NoStringConcat: <no need to use string builder>
            stmt.executeUpdate(
                    updateMechanic2ShiftStatement(mechanicId2,
                            week1,
                            day1,
                            timeSlot1,
                            week2,
                            day2,
                            timeSlot2));
            System.out.println("Swap request successfully accepted.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
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

    private void rejectSwap() {
        // Establish connection to database
        final DbConnection db = new DbConnection();
        final Connection conn = db.getConnection();
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            // Update the swap request to be rejected
            // deepcode ignore NoStringConcat: <no need to use string builder>
            stmt.executeUpdate(rejectSwapStatement(swapRequestID));

            System.out.println("Swap request rejected.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
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

    private static String swapRequestExistsQuery(Integer swapRequestID) {
        return "SELECT requestId, centerId, mechanicId1, mechanicId2, week1, week2, day1, day2, timeSlot1, timeSlot2 " +
                " FROM SwapRequests " +
                " WHERE requestId = " + swapRequestID +
                " AND status = 0";
    }

    private static String acceptSwapStatement(Integer swapRequestID) {
        return "UPDATE SwapRequests " +
                " SET status = 1 " +
                " WHERE requestId = " + swapRequestID;
    }

    private static String rejectSwapStatement(Integer swapRequestID) {
        return "UPDATE SwapRequests " +
                " SET status = 2 " +
                " WHERE requestId = " + swapRequestID;
    }

    private String updateMechanic1ShiftStatement(Integer mechanicId1, Integer week1, Integer day1,
            Integer timeSlot1, Integer week2, Integer day2, Integer timeSlot2) {
        return "UPDATE Schedule " +
                " SET week = " + week2 +
                " AND day = " + day2 +
                " AND timeSlot = " + timeSlot2 +
                " WHERE mechanicId = " + mechanicId1 +
                " AND week = " + week1 +
                " AND day = " + day1 +
                " AND timeSlot = " + timeSlot1;
    }

    private String updateMechanic2ShiftStatement(Integer mechanicId2, Integer week1, Integer day1,
            Integer timeSlot1, Integer week2, Integer day2, Integer timeSlot2) {
        return "UPDATE Schedule " +
                " SET week = " + week1 +
                " AND day = " + day1 +
                " AND timeSlot = " + timeSlot1 +
                " WHERE mechanicId = " + mechanicId2 +
                " AND week = " + week2 +
                " AND day = " + day2 +
                " AND timeSlot = " + timeSlot2;
    }
}
