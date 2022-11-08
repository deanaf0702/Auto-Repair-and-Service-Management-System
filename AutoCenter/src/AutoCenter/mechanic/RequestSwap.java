package AutoCenter.mechanic;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.repository.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

import AutoCenter.LoginUser;
import AutoCenter.ScanHelper;
import AutoCenter.UIHelpers;

/**
 * Handles functionality for request swap menu of mechanics including display,
 * input, and output.
 *
 * @author jkersey
 */
public class RequestSwap implements UserFlowFunctionality {

    /*
     * The initial selection for the menu options range
     */
    private static final int INITIAL_SELECTION = 0;

    /*
     * The minimum selection for the menu options range
     */
    private static final int MIN_SELECTION = 1;

    /*
     * The maximum selection for the menu options range
     */
    private static final int MAX_SELECTION = 2;

    /*
     * The separator to use between the menu title and the options
     */
    private static final String MENU_SEPARATOR = "#######################################";

    /*
     * The separator to use between the directions components
     */
    private static final String DIRECTION_SEPARATOR = "#############################";

    /*
     * The separator to use between the directions components
     */
    private static final String DIRECTION_COMPONENT_SEPARATOR = "### ---------------------------- ###\n";

    /*
     * The time slot parameters for the swap request
     */
    private static Integer[] initialTimeSlotParameters;

    /*
     * The time slot parameters for the mechanic to swap with
     */
    private static Integer[] desiredTimeSlotParameters;

    /*
     * The mechanic id of the mechanic to swap with
     */
    private static Integer employeeIDForSwap;

    /*
     * Invalid input message
     */
    private static final String INVALID_INPUT_MESSAGE = "\nInvalid input. Please try again.\n";

    private int week1;
    private int day1;
    private int startTimeSlot1;
    private int endTimeSlot1;
    private int week2;
    private int day2;
    private int startTimeSlot2;
    private int endTimeSlot2;
    private int mechanicId;
    
    @Override
    public void run() {
        int selection;

        
            reset();
            displayDirections();
            System.out.print("Timeslot range to swap");
            System.out.print("Week :");
            week1 = ScanHelper.nextInt();
            System.out.print("Day :");
            day1 = ScanHelper.nextInt();
            System.out.print("Start time slot :");
            startTimeSlot1 = ScanHelper.nextInt();
            System.out.print("End time slot :");
            endTimeSlot1 = ScanHelper.nextInt();
            System.out.print("Employee ID:");
            mechanicId = ScanHelper.nextInt();
            System.out.println("Time slot range of the requested mechanic");
            System.out.print("Week :");
            week2 = ScanHelper.nextInt();
            System.out.print("Day :");
            day2 = ScanHelper.nextInt();
            System.out.print("Start time slot :");
            startTimeSlot2 = ScanHelper.nextInt();
            System.out.print("End time slot :");
            endTimeSlot2 = ScanHelper.nextInt();
            
            display();
            System.out.print("Enter choice (" + MIN_SELECTION + "-" + MAX_SELECTION
                    + ") from the given options displayed above: ");
            selection = ScanHelper.nextInt();
            navigate(selection);
    }

    public void displayDirections() {
        String[] usageComponents = {
                "# A. Timeslot range to swap        #\n" +
                        "#    (indicated by week, day, time #\n" +
                        "#    slot start and end ids)       #",
                "# B. Employee ID of a mechanic     #\n" +
                        "#    that is being requested for   #\n" +
                        "#    swap                          #",
                "# C. Timeslot range of the         #\n" +
                        "#    requested mechanic that is of #\n" +
                        "#    interest                      #"
        };

        UIHelpers.displayUsageDirections(
                usageComponents,
                "### Timeslot range to swap:      ###\n" +
                        "###          1; 3; 6; 9          ###\n" +
                        DIRECTION_COMPONENT_SEPARATOR +
                        "### Employee ID: 423186759       ###\n" +
                        DIRECTION_COMPONENT_SEPARATOR +
                        "### Timeslot range of requested  ###\n" +
                        "### mechanic:                    ###\n" +
                        "###          3; 4; 2; 7          ###",
                "   Request Swap: Usage    ",
                "   Request Swap: Example  ",
                DIRECTION_SEPARATOR);
    }

    /*
     * Resets the state of the input parameters
     */
    private static void reset() {
        initialTimeSlotParameters = new Integer[4];
        desiredTimeSlotParameters = new Integer[4];
        employeeIDForSwap = -1;
    }

    @Override
    public void display() {
        System.out.println(MENU_SEPARATOR);
        System.out.println("##### Mechanic: Request Swap Menu #####");
        System.out.println(MENU_SEPARATOR);
        System.out.println("# 1 Send the request                  #");
        System.out.println("# 2 Go Back                           #");
        System.out.println(MENU_SEPARATOR);
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                save();
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

    public boolean save()
    {
    	boolean valid = false;
    	if(validateRequestingMechanicWorkingWithinTimeSlotRange(week1,day1,startTimeSlot1, endTimeSlot1))
    	{
    		String query1 = "insert into SwapRequests (requestId, status) values(?, ?)";
    		String query2 = "insert into OnSwapRequests (requestId, mechanicId1, week1, day1, timeSlot1, mechanicId2, week2, day2, timeSlot2)"
    		+ " values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    	}
    	return valid;
    }
    // /*
    // * Parses and validates the desired time slot inputs for the request swap menu
    // */

    /*
     * Handles the parsing of the input parameters
     * along with short-circuiting validation logic
     */
    private static boolean parseAndValidateInputs() {
        Scanner scanner = new Scanner(System.in);

        boolean validInitialTimeSlots = parseAndValidateInitialTimeSlots(scanner);
        if (!validInitialTimeSlots) {
            return false;
        }

        boolean validEmployeeID = parseAndValidateEmployeeID(scanner);
        if (!validEmployeeID) {
            return false;
        }

        return parseAndValidateDesiredTimeSlots(scanner); // all inputs are valid
    }

    /*
     * Parses and validates the initial time slot inputs for the request swap menu
     *
     * @param scanner The scanner to use for parsing the inputs
     *
     * @return true if the desired time slot inputs are valid, false otherwise
     */
    private static boolean parseAndValidateInitialTimeSlots(Scanner scanner) {
        try {
            System.out.println("Timeslot range to swap:");

            System.out.print("Timeslot week (1-4): ");
            initialTimeSlotParameters[0] = scanner.nextInt();
            if (initialTimeSlotParameters[0] < 1 || initialTimeSlotParameters[0] > 4) {
                System.out.println("\nInvalid week number. Please enter a number between 1 and 4.\n");
                scanner.close();
                return false;
            }

            System.out.print("Timeslot day (1-7): ");
            initialTimeSlotParameters[1] = scanner.nextInt();
            if (initialTimeSlotParameters[1] < 1 || initialTimeSlotParameters[1] > 7) {
                System.out.println("\nInvalid day number. Please enter a number between 1 and 7.\n");
                scanner.close();
                return false;
            }

            System.out.print("Timeslot start (1-9): ");
            initialTimeSlotParameters[2] = scanner.nextInt();
            if (initialTimeSlotParameters[2] < 1 || initialTimeSlotParameters[2] > 9) {
                System.out.println("\nInvalid start time slot. Please enter a number between 1 and 9.\n");
                scanner.close();
                return false;
            }

            System.out.print("Timeslot end (1-9): ");
            initialTimeSlotParameters[3] = scanner.nextInt();
            if (initialTimeSlotParameters[3] < 1 || initialTimeSlotParameters[3] > 9) {
                System.out.println("\nInvalid end time slot. Please enter a number between 1 and 9.\n");
                scanner.close();
                return false;
            }
        } catch (InputMismatchException e) {
            System.out.println(INVALID_INPUT_MESSAGE);
            scanner.close();
            return false;
        }

        // validate the start time slot is less than the end time slot
        if (!validateTimeSlotRange(initialTimeSlotParameters[2], initialTimeSlotParameters[3])) {
            return false;
        }

        // validate the mechanic is working within the given time slot range
        return validateRequestingMechanicWorkingWithinTimeSlotRange(initialTimeSlotParameters[0],
                initialTimeSlotParameters[1],
                initialTimeSlotParameters[2], initialTimeSlotParameters[3]); // all inputs are valid
    }

    /*
     * Validates the mechanic requesting a swap is working within the given time
     * slot range
     *
     * @param week The week of the time slot range
     *
     * @param day The day of the time slot range
     *
     * @param startTimeSlot The start time slot of the time slot range
     *
     * @param endTimeSlot The end time slot of the time slot range
     *
     * @return true if the mechanic is working within the given time slot range,
     * false otherwise
     */
    private static boolean validateRequestingMechanicWorkingWithinTimeSlotRange(Integer week, Integer day,
            Integer startTimeSlot, Integer endTimeSlot) {

        // Establish connection to database
        final DbConnection db = new DbConnection();
        final Connection conn = db.getConnection();
        ResultSet rs = null;
        Statement stmt = null;

        try {
            stmt = conn.createStatement();

            // Query the database for the mechanic's time slots
            // deepcode ignore NoStringConcat: <not needed>
            rs = stmt.executeQuery(validRequestingMechanicWorkingQuery(week, day, startTimeSlot, endTimeSlot));

            // If the result set is empty, the mechanic is not working within the given time
            // slot range
            if (!rs.next()) {
                System.out.println(
                        "\nYou are not working within the specified initial time slot range. Try again with a time slot range in which you are working.\n");
                return false;
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

        return true;
    }

    /*
     * Returns the query to validate the mechanic requesting a swap is working
     * within
     * the given time slot range
     *
     * @param week The week of the time slot range
     *
     * @param day The day of the time slot range
     *
     * @param startTimeSlot The start time slot of the time slot range
     *
     * @param endTimeSlot The end time slot of the time slot range
     *
     * @return The query to validate the mechanic requesting a swap is working
     */
    private static String validRequestingMechanicWorkingQuery(Integer week, Integer day, Integer timeSlotStart,
            Integer timeSlotEnd) {
        return "SELECT COUNT(*) AS numMechanics"
                + " FROM Schedule"
                + " WHERE mechanicID = " + LoginUser.getId()
                + " WHERE centerId = " + LoginUser.getCenterId()
                + " AND week = " + week
                + " AND day = " + day
                + " AND timeSlot >= " + timeSlotStart
                + " AND timeSlot <= " + timeSlotEnd;
    }

    /*
     * Parses and validates the desired time slot inputs for the request swap menu
     *
     * @param scanner The scanner to use for parsing the inputs
     *
     * @return true if the desired time slot inputs are valid, false otherwise
     */
    private static boolean parseAndValidateDesiredTimeSlots(Scanner scanner) {
        try {
            System.out.println("\nDesired timeslot range to swap with:");

            System.out.print("Timeslot week (1-4): ");
            desiredTimeSlotParameters[0] = scanner.nextInt();
            if (desiredTimeSlotParameters[0] < 1 || desiredTimeSlotParameters[0] > 4) {
                System.out.println("\nInvalid week number. Please enter a number between 1 and 4.\n");
                scanner.close();
                return false;
            }

            System.out.print("Timeslot day (1-7): ");
            desiredTimeSlotParameters[1] = scanner.nextInt();
            if (desiredTimeSlotParameters[1] < 1 || desiredTimeSlotParameters[1] > 7) {
                System.out.println("\nInvalid day number. Please enter a number between 1 and 7.\n");
                scanner.close();
                return false;
            }

            System.out.print("Timeslot start (1-9): ");
            desiredTimeSlotParameters[2] = scanner.nextInt();
            if (desiredTimeSlotParameters[2] < 1 || desiredTimeSlotParameters[2] > 9) {
                System.out.println("\nInvalid start time slot. Please enter a number between 1 and 9.\n");
                scanner.close();
                return false;
            }

            System.out.print("Timeslot end (1-9): ");
            desiredTimeSlotParameters[3] = scanner.nextInt();
            if (desiredTimeSlotParameters[3] < 1 || desiredTimeSlotParameters[3] > 9) {
                System.out.println("\nInvalid end time slot. Please enter a number between 1 and 9.\n");
                scanner.close();
                return false;
            }
        } catch (InputMismatchException e) {
            System.out.println(INVALID_INPUT_MESSAGE);
            scanner.close();
            return false;
        }

        // validate the start time slot is less than the end time slot
        if (!validateTimeSlotRange(desiredTimeSlotParameters[2], desiredTimeSlotParameters[3])) {
            return false;
        }

        // TODO still need to check for overbooking and double booking here

        // validate the mechanic is working within the given time slot range
        return validateRequestedMechanicWorkingWithinTimeSlotRange(desiredTimeSlotParameters[0],
                desiredTimeSlotParameters[1],
                desiredTimeSlotParameters[2], desiredTimeSlotParameters[3]); // all inputs are valid
    }

    /*
     * Validates the mechanic requested for a swap is working within the given time
     * slot range
     *
     * @param week The week of the time slot range
     *
     * @param day The day of the time slot range
     *
     * @param startTimeSlot The start time slot of the time slot range
     *
     * @param endTimeSlot The end time slot of the time slot range
     *
     * @return true if the mechanic is working within the given time slot range,
     * false otherwise
     */
    private static boolean validateRequestedMechanicWorkingWithinTimeSlotRange(Integer week, Integer day,
            Integer startTimeSlot, Integer endTimeSlot) {

        // Establish connection to database
        final DbConnection db = new DbConnection();
        final Connection conn = db.getConnection();
        ResultSet rs = null;
        Statement stmt = null;

        try {
            stmt = conn.createStatement();

            // Query the database for the requested mechanic's time slots
            // deepcode ignore NoStringConcat: <not needed>
            rs = stmt.executeQuery(
                    validRequestedMechanicWorkingQuery(
                            week,
                            day,
                            startTimeSlot,
                            endTimeSlot,
                            employeeIDForSwap));

            // If the result set is empty, the mechanic is not working within the given time
            // slot range
            if (!rs.next()) {
                System.out.println(
                        "\nThe mechanic you are requesting to swap with is not working within the specified time slot range. Try again with a time slot range in which they are working.\n");
                return false;
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

        return true;
    }

    /*
     * Returns the query to validate the mechanic being swapped with is working
     * within the given time slot range requested
     *
     * @param week The week of the time slot range
     *
     * @param day The day of the time slot range
     *
     * @param startTimeSlot The start time slot of the time slot range
     *
     * @param endTimeSlot The end time slot of the time slot range
     *
     * @param mechanicId The mechanic ID of the mechanic being swapped with
     *
     * @return The query to validate the mechanic being swapped with is working
     */
    private static String validRequestedMechanicWorkingQuery(Integer week, Integer day, Integer timeSlotStart,
            Integer timeSlotEnd,
            Integer mechanicId) {
        return "SELECT COUNT(*) AS numMechanics"
                + " FROM Schedule"
                + " WHERE mechanicID = " + mechanicId
                + " WHERE centerId = " + LoginUser.getCenterId()
                + " AND week = " + week
                + " AND day = " + day
                + " AND timeSlot >= " + timeSlotStart
                + " AND timeSlot <= " + timeSlotEnd;
    }

    /*
     * Helper function for ensuring start time slot is less than or equal to end
     * time slot
     *
     * @param start The start time slot
     *
     * @param end The end time slot
     *
     * @return true if the start time slot is less than or equal to the end time
     */
    private static boolean validateTimeSlotRange(int start, int end) {
        if (start > end) {
            System.out.println(
                    "\nInvalid time slot range. Please enter a start time slot that is less than or equal to the end time slot.\n");
            return false;
        }

        return true;
    }

    /*
     * Parses and validates the desired employee id for the request swap menu
     *
     * @param scanner The scanner to use for parsing the inputs
     *
     * @return true if the employee id is valid (exists), false otherwise
     */
    private static boolean parseAndValidateEmployeeID(Scanner scanner) {
        try {
            System.out.println("Employee ID of the mechanic that is being requested for swap:");
            System.out.print("Employee ID: ");
            employeeIDForSwap = scanner.nextInt();
            return validateMechanicID(employeeIDForSwap);
        } catch (InputMismatchException e) {
            System.out.println(INVALID_INPUT_MESSAGE);
            scanner.close();
            return false;
        }
    }

    /*
     * Validates the employee id for the request swap menu
     *
     * @param employeeID The employee id to validate
     *
     * @return true if the employee id is valid (exists), false otherwise
     */
    private static boolean validateMechanicID(int employeeID) {
        // Establish connection to database
        final DbConnection db = new DbConnection();
        final Connection conn = db.getConnection();
        ResultSet rs = null;
        Statement stmt = null;

        try {
            stmt = conn.createStatement();

            // Query the database for the employee id
            rs = stmt.executeQuery(validMechanicQuery(employeeID));

            if (!rs.next()) {
                System.out.println("\nInvalid employee ID. Please enter a valid employee ID.\n");
                return false;
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

        return true;
    }

    /*
     * Returns the query for the employee id to see if it exists
     *
     * @param employeeID The employee id to query the database for
     *
     * @return true if the employee id exists, false otherwise
     */
    private static String validMechanicQuery(int employeeID) {
        return "SELECT *" +
                " FROM Mechanics" +
                " WHERE EmployeeID = " + employeeID;
    }
}
