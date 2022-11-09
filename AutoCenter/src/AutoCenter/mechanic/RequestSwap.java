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

    /**
     * The initial selection for the menu options range
     */
    private static final int INITIAL_SELECTION = 0;

    /**
     * The minimum selection for the menu options range
     */
    private static final int MIN_SELECTION = 1;

    /**
     * The maximum selection for the menu options range
     */
    private static final int MAX_SELECTION = 2;

    /**
     * The maximum number of hours a mechanic can work in a week
     */
    private static final int MAX_WORKING_HOURS = 50;

    /**
     * The separator to use between the menu title and the options
     */
    private static final String MENU_SEPARATOR = "#######################################";

    /**
     * The separator to use between the directions components
     */
    private static final String DIRECTION_SEPARATOR = "####################################";

    /**
     * The separator to use between the directions components
     */
    private static final String DIRECTION_COMPONENT_SEPARATOR = "### ---------------------------- ###\n";

    /**
     * The time slot parameters for the swap request
     */
    private static Integer[] initialTimeSlotParameters;

    /**
     * The time slot parameters for the mechanic to swap with
     */
    private static Integer[] desiredTimeSlotParameters;

    /**
     * The mechanic id of the mechanic to swap with
     */
    private static Integer employeeIDForSwap;

    /**
     * Invalid input error message
     */
    private static final String INVALID_INPUT_ERROR_MESSAGE = "\nInvalid input. Please try again.\n";

    @Override
    public void run() {
        int selection;

        do {
            reset();
            displayDirections();
            boolean validInputs = parseAndValidateInputs();
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

    /**
     * Displays usage directions for the mechanic to request a swap
     * along with an example of the input format.
     */
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

    @Override
    public void display() {
        String[] menuOptions = {
                "# 1 Send the request                  #",
                "# 2 Go Back                           #",
        };

        UIHelpers.displayMenu(" Mechanic: Request Swap Menu ", menuOptions, MENU_SEPARATOR);
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                processRequestedSwap();
                System.out.print("\nWould you like to request another swap? (y/n): ");
                String response = ScanHelper.next();
                if (response.equalsIgnoreCase("y")) {
                    new RequestSwap().run();
                }
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

    /**
     * Resets the state of the input parameters
     */
    private static void reset() {
        initialTimeSlotParameters = new Integer[4];
        desiredTimeSlotParameters = new Integer[4];
        employeeIDForSwap = -1;
    }

    /**
     * Processes the valid requested swap and issues a swap request to the
     * database.
     */
    private void processRequestedSwap() {
        // Establish connection to database
        final DbConnection db = new DbConnection();
        final Connection conn = db.getConnection();
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            // deepcode ignore NoStringConcat: <no need to use string builder>
            stmt.executeUpdate(insertSwapRequestStatement());
            System.out.println("\nSuccessfully created swap request with employee ID: "
                    + employeeIDForSwap + ". Swapped timeslot range: "
                    + UIHelpers.convertToStartDate(initialTimeSlotParameters[0], initialTimeSlotParameters[1],
                            initialTimeSlotParameters[2])
                    + " - " + UIHelpers.convertToEndDate(initialTimeSlotParameters[0], initialTimeSlotParameters[1],
                            initialTimeSlotParameters[3])
                    + " with timeslot range: "
                    + UIHelpers.convertToStartDate(desiredTimeSlotParameters[0], desiredTimeSlotParameters[1],
                            desiredTimeSlotParameters[2])
                    + " - "
                    + UIHelpers.convertToEndDate(desiredTimeSlotParameters[0], desiredTimeSlotParameters[1],
                            desiredTimeSlotParameters[3])
                    + ".\n");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
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

    /**
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

    /**
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
            System.out.println(INVALID_INPUT_ERROR_MESSAGE);
            scanner.close();
            return false;
        }

        // validate the start time slot is less than the end time slot
        if (!validateTimeSlotRange(initialTimeSlotParameters[2], initialTimeSlotParameters[3])) {
            return false;
        }

        // validate the requesting mechanic is working within the given time slot range
        return validateRequestingMechanicWorkingWithinTimeSlotRange(initialTimeSlotParameters[0],
                initialTimeSlotParameters[1],
                initialTimeSlotParameters[2], initialTimeSlotParameters[3]); // all inputs are valid
    }

    /**
     * Validates the mechanic requesting a swap is working within the given time
     * slot range
     *
     * @param week          The week of the time slot range
     *
     * @param day           The day of the time slot range
     *
     * @param startTimeSlot The start time slot of the time slot range
     *
     * @param endTimeSlot   The end time slot of the time slot range
     *
     * @return true if the mechanic is working within the given time slot range,
     *         false otherwise
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

    /**
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
            System.out.println(INVALID_INPUT_ERROR_MESSAGE);
            scanner.close();
            return false;
        }

        // validate the start time slot is less than the end time slot
        if (!validateTimeSlotRange(desiredTimeSlotParameters[2], desiredTimeSlotParameters[3])) {
            return false;
        }

        // validate the mechanic is working within the given time slot range
        if (!validateRequestedMechanicWorkingWithinTimeSlotRange(desiredTimeSlotParameters[0],
                desiredTimeSlotParameters[1],
                desiredTimeSlotParameters[2], desiredTimeSlotParameters[3])) {
            return false;
        }

        // validate no overbooking
        if (!validateRequestedMechanicNotOverbooked(initialTimeSlotParameters[0], initialTimeSlotParameters[2],
                initialTimeSlotParameters[3], employeeIDForSwap)) {
            return false;
        }

        // validate no double booking
        return validateRequestedMechanicNotDoublebooked(initialTimeSlotParameters[0], initialTimeSlotParameters[1],
                initialTimeSlotParameters[2], initialTimeSlotParameters[3], employeeIDForSwap); // all inputs are valid
    }

    /**
     * Validates the mechanic requested for a swap is working within the given time
     * slot range
     *
     * @param week          The week of the time slot range
     *
     * @param day           The day of the time slot range
     *
     * @param startTimeSlot The start time slot of the time slot range
     *
     * @param endTimeSlot   The end time slot of the time slot range
     *
     * @return true if the mechanic is working within the given time slot range,
     *         false otherwise
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

    /**
     * Validates the mechanic requested for a swap is not overbooked given the
     * requested time slot range and the employee ID of the mechanic requested for a
     * swap in a particular week
     *
     * @param week              The week of the time slot range
     *
     * @param startTimeSlot     The start time slot of the time slot range
     *
     * @param endTimeSlot       The end time slot of the time slot range
     *
     * @param employeeIDForSwap The employee ID of the mechanic requested for a swap
     *
     * @return true if the mechanic is not overbooked, false otherwise
     */
    private static boolean validateRequestedMechanicNotOverbooked(Integer week, Integer startTimeSlot,
            Integer endTimeSlot, Integer employeeIDForSwap) {
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
                    getNumTimeSlotsQuery(week, employeeIDForSwap));

            // If the count is greater than max working hours, the mechanic is overbooked
            while (rs.next()) {
                int numHours = rs.getInt("numHours");
                int additionalHours = endTimeSlot - startTimeSlot + 1;

                if (numHours + additionalHours > MAX_WORKING_HOURS) {
                    System.out.println(
                            "\nThe mechanic you are requesting to swap with is overbooked. Try again with a time slot range in which they are not overbooked.\n");
                    return false;
                }
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

    /**
     * Validates the mechanic requested for a swap is not double booked given the
     * requested time slot range and the employee ID of the mechanic requested for a
     * swap in a particular week and day
     *
     * @param week              The week of the time slot range
     *
     * @param day               The day of the time slot range
     *
     * @param startTimeSlot     The start time slot of the time slot range
     *
     * @param endTimeSlot       The end time slot of the time slot range
     *
     * @param employeeIDForSwap The employee ID of the mechanic requested for a swap
     *
     * @return true if the mechanic is not double booked, false otherwise
     */
    private static boolean validateRequestedMechanicNotDoublebooked(Integer week, Integer day,
            Integer startTimeSlot, Integer endTimeSlot, Integer employeeIDForSwap) {
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
                    getNumEntriesQuery(week, day, startTimeSlot, endTimeSlot, employeeIDForSwap));

            // If the number of entries is greater than 0, the mechanic is double booked
            while (rs.next()) {
                int numEntries = rs.getInt("numEntries");

                if (numEntries > 0) {
                    System.out.println(
                            "\nThe mechanic you are requesting to swap with is double booked if they accept your proposed swap. Try again with a time slot range in which they are not double booked.\n");
                    return false;
                }
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

    /**
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
            System.out.println(INVALID_INPUT_ERROR_MESSAGE);
            scanner.close();
            return false;
        }
    }

    /**
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
            // deepcode ignore NoStringConcat: <not needed>
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

    /**
     * Returns the query to validate the mechanic being swapped with is working
     * within the given time slot range requested
     *
     * @param week          The week of the time slot range
     *
     * @param day           The day of the time slot range
     *
     * @param startTimeSlot The start time slot of the time slot range
     *
     * @param endTimeSlot   The end time slot of the time slot range
     *
     * @param mechanicId    The mechanic ID of the mechanic being swapped with
     *
     * @return The query to validate the mechanic being swapped with is working
     */
    private static String validRequestedMechanicWorkingQuery(Integer week, Integer day, Integer timeSlotStart,
            Integer timeSlotEnd,
            Integer mechanicId) {
        return "SELECT COUNT(*) AS numMechanics"
                + " FROM Schedule"
                + " WHERE mechanicId = " + mechanicId
                + " AND centerId = " + LoginUser.getCenterId()
                + " AND week = " + week
                + " AND day = " + day
                + " AND timeSlot >= " + timeSlotStart
                + " AND timeSlot <= " + timeSlotEnd;
    }

    /**
     * Returns the query to validate the mechanic requesting a swap is working
     * within
     * the given time slot range
     *
     * @param week          The week of the time slot range
     *
     * @param day           The day of the time slot range
     *
     * @param startTimeSlot The start time slot of the time slot range
     *
     * @param endTimeSlot   The end time slot of the time slot range
     *
     * @return The query to validate the mechanic requesting a swap is working
     */
    private static String validRequestingMechanicWorkingQuery(Integer week, Integer day, Integer timeSlotStart,
            Integer timeSlotEnd) {
        return "SELECT COUNT(*) AS numMechanics"
                + " FROM Schedule"
                + " WHERE mechanicId = " + LoginUser.getId()
                + " AND centerId = " + LoginUser.getCenterId()
                + " AND week = " + week
                + " AND day = " + day
                + " AND timeSlot >= " + timeSlotStart
                + " AND timeSlot <= " + timeSlotEnd;
    }

    /**
     * Returns the query for the employee id to see if it exists
     *
     * @param employeeID The employee id to query the database for
     *
     * @return true if the employee id exists, false otherwise
     */
    private static String validMechanicQuery(int employeeID) {
        return "SELECT *" +
                " FROM Mechanics" +
                " WHERE userId = " + employeeID;
    }

    /**
     * Returns the number of timeSlots for the desired mechanic for the given week
     *
     * @param week       The week to get the number of time slots for
     *
     * @param mechanicID The mechanic ID to get the number of time slots for
     *
     * @return The number of time slots for the desired mechanic for the given week
     */
    private static String getNumTimeSlotsQuery(int week, int mechanicID) {
        return "SELECT SUM(COUNT(*)) AS numHours" +
                " FROM Schedule" +
                " WHERE week = " + week +
                " AND mechanicId = " + mechanicID +
                " AND centerId = " + LoginUser.getCenterId() +
                " GROUP BY timeSlot";
    }

    /**
     * Returns the number of entries for the desired mechanic during the proposed
     * swap time slot range. Used to validate that the mechanic is not double booked
     *
     * @param week          The week of the time slot range
     *
     * @param day           The day of the time slot range
     *
     * @param startTimeSlot The start time slot of the time slot range
     *
     * @param endTimeSlot   The end time slot of the time slot range
     *
     * @param mechanicId    The mechanic ID of the mechanic being swapped with
     *
     * @return The number of entries for the desired mechanic during the proposed
     */
    private static String getNumEntriesQuery(Integer week, Integer day, Integer timeSlotStart, Integer timeSlotEnd,
            Integer mechanicId) {
        return "SELECT COUNT(*) AS numEntries" +
                " FROM Schedule" +
                " WHERE mechanicId = " + mechanicId +
                " AND centerId = " + LoginUser.getCenterId() +
                " AND week = " + week +
                " AND day = " + day +
                " AND timeSlot >= " + timeSlotStart +
                " AND timeSlot <= " + timeSlotEnd;
    }

    /**
     * Helper function for ensuring start time slot is less than or equal to end
     * time slot
     *
     * @param start The start time slot
     *
     * @param end   The end time slot
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

    /**
     * Returns the SQL INSERT statement for the swap request based on the input
     * parameters into the SwapRequests table of the database initialized in the
     * pending state.
     */
    private static String insertSwapRequestStatement() {
        return "INSERT INTO SwapRequests (requestId, mechanicId1, week1, day1, timeSlot1Start, timeSlot1End, mechanicId2, week2, day2, timeSlot2Start, timeSlot2End, status) VALUES ("
                + "auto_increment_swap_request_id.nextval, "
                + LoginUser.getId() + ", "
                + initialTimeSlotParameters[0] + ", "
                + initialTimeSlotParameters[1] + ", "
                + initialTimeSlotParameters[2] + ", "
                + initialTimeSlotParameters[3] + ", "
                + employeeIDForSwap + ", "
                + desiredTimeSlotParameters[0] + ", "
                + desiredTimeSlotParameters[1] + ", "
                + desiredTimeSlotParameters[2] + ", "
                + desiredTimeSlotParameters[3] + ", "
                + 0 + ")";
    }
}
