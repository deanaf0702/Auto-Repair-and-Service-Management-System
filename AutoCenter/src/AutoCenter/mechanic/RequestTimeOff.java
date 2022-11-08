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
 * Handles functionality for request time off menu of mechanics including
 * display, input, and
 * output.
 *
 * @author jkersey
 */
public class RequestTimeOff implements UserFlowFunctionality {

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
     * The expected number of inputs for a valid time off request
     */
    private static final int EXPECTED_INPUT_LENGTH = 4;

    /**
     * The minimum number of working mechanics at any given time
     */
    private static final int MIN_WORKING_MECHANICS = 3;

    /**
     * The separator to use between the menu title and the options
     */
    private static final String MENU_SEPARATOR = "###########################################";

    /**
     * The separator to use between the directions components
     */
    private static final String DIRECTION_SEPARATOR = "#####################################";

    /**
     * The timeSlotParameters for the time off request
     */
    private Integer[] timeSlotParameters;

    @Override
    public void run() {
        int selection = INITIAL_SELECTION;
        displayDirections();
        display();

        do {
            reset();
            String input = ScanHelper.nextLine();
            String[] inputs = input.split(";");
            if (inputs.length >= EXPECTED_INPUT_LENGTH) {
                boolean validTimeSlots = parseAndValidateTimeSlots(inputs);
                if (validTimeSlots) {
                    System.out.print("Enter choice (" + MIN_SELECTION + "-" + MAX_SELECTION
                            + ") from the given options displayed above: ");
                    selection = ScanHelper.nextInt();
                } else {
                    System.out.println("Invalid time slot parameters. Please try again.");
                }
            } else {
                System.out.println(
                        "\nSomething went wrong. Please try again and make sure you provide all four input time"
                                + " slot parameters. Take a look at the usage detailed above if you need help.\n");
                selection = INITIAL_SELECTION;
            }
        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));
        navigate(selection);
    }

    /**
     * Resets the state of the request process within the menu.
     */
    private void reset() {
        timeSlotParameters = new Integer[4];
    }

    /**
     * Displays the directions for usage of the menu to the user
     * along with an example.
     */
    private void displayDirections() {
        String[] usageComponents = {
                "# A. Time slots mechanic wants to   #\n" +
                        "#    be off (indicated by week, day #\n" +
                        "#    , time slot start and end ids) #",
        };

        UIHelpers.displayUsageDirections(
                usageComponents,
                "###           1; 3; 6; 9          ###\n" +
                        DIRECTION_SEPARATOR + "\n" +
                        "### Corresponds to (week: 1, day  ###\n" +
                        "### 3, time start: 6, time end: 9 ###",
                "   Add New Store: Usage    ",
                "   Add New Store: Example  ",
                DIRECTION_SEPARATOR);
    }

    @Override
    public void display() {
        String[] menuOptions = {
                "# 1 Send the request                      #",
                "# 2 Go Back                               #"
        };

        UIHelpers.displayMenu(" Mechanic: Request Time Off Menu ", menuOptions, MENU_SEPARATOR);
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                processRequestedTimeOff();
                new RequestTimeOff().run();
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
     * Processes the requested time off by the mechanic, handling validation
     * logic such as ensuring that the mechanic is not already working during
     * the requested time off and that there are at least three mechanics
     * working at any given time.
     */
    private void processRequestedTimeOff() {
        // Establish connection to database
        final DbConnection db = new DbConnection();
        final Connection conn = db.getConnection();
        ResultSet rs = null;
        Statement stmt = null;

        try {
            stmt = conn.createStatement();

            // check to see if the mechanic is scheduled to work during the requested time
            // off
            // file deepcode ignore NoStringConcat: <not needed>
            rs = stmt.executeQuery(viewTimeOffRequestsQuery(timeSlotParameters[0], timeSlotParameters[1],
                    timeSlotParameters[2], timeSlotParameters[3]));
            if (rs.next()) {
                System.out.println(
                        "0\nUnable to allow time off since you are already scheduled for work within that time range.\nPlease request another time range instead.");
            }

            // check to see if there are enough mechanics to cover the time off request
            rs = stmt.executeQuery(
                    viewWorkingMechanicsQuery(timeSlotParameters[0], timeSlotParameters[1],
                            timeSlotParameters[2], timeSlotParameters[3]));

            while (rs.next()) {
                int count = rs.getInt("numMechanics");
                if (count < MIN_WORKING_MECHANICS) {
                    System.out.println(
                            "0\nUnable to allow time off since there are not enough mechanics to cover the time off request.\nPlease request another time range instead.");
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
    }

    /**
     * Parses and validates the time slot parameters provided by the user
     * for the time off request, storing the parsed values in the
     * timeSlotParameters array.
     *
     * @param inputs the inputs provided by the user
     * @return true if the time slot parameters are valid, false otherwise
     *
     */
    private boolean parseAndValidateTimeSlots(String[] inputs) {
        try {
            timeSlotParameters[0] = Integer.parseInt(inputs[0]);
            timeSlotParameters[1] = Integer.parseInt(inputs[1]);
            timeSlotParameters[2] = Integer.parseInt(inputs[2]);
            timeSlotParameters[3] = Integer.parseInt(inputs[3]);
        } catch (NumberFormatException e) {
            System.out.println(
                    "Something went wrong. Please try again and make sure you provide all four input time"
                            + " slot parameters. Take a look at the usage detailed above if you need help.");
            return false;
        }

        return !(timeSlotParameters[0] < 1 || timeSlotParameters[0] > 4 /* valid week */
                || timeSlotParameters[1] < 1 || timeSlotParameters[1] > 7 /* valid day */
                || timeSlotParameters[2] < 1 || timeSlotParameters[2] > 9 /* valid slot tart */
                || timeSlotParameters[3] < 1 || timeSlotParameters[3] > 9 /* valid slot end */
        );
    }

    /*
     * Returns the query to view all work schedules for a given mechanic during
     * their requested time off
     *
     * @param week the week of the time off request
     *
     * @param day the day of the time off request
     *
     * @param timeSlotStart the start time of the time off request
     *
     * @param timeSlotEnd the end time of the time off request
     *
     * @return the query to view all work schedules for a given mechanic during
     */
    private String viewTimeOffRequestsQuery(Integer week, Integer day, Integer timeSlotStart,
            Integer timeSlotEnd) {
        return "SELECT week, day, timeSlot"
                + " FROM Schedule"
                + " WHERE mechanicId = " + LoginUser.getId()
                + " AND centerId = " + LoginUser.getCenterId()
                + " AND week = " + week
                + " AND day = " + day
                + " AND timeSlot >= " + timeSlotStart
                + " AND timeSlot <= " + timeSlotEnd;
    }

    /*
     * Returns the query to view the number of mechanics working during a given
     * time slot at a given center
     *
     * @param week the week of the time slot
     *
     * @param day the day of the time slot
     *
     * @param timeSlotStart the start of the time slot
     *
     * @param timeSlotEnd the end of the time slot
     *
     * @return the query to view the number of mechanics working during a given
     * time slot at a given center
     */
    private String viewWorkingMechanicsQuery(Integer week, Integer day, Integer timeSlotStart,
            Integer timeSlotEnd) {
        return "SELECT COUNT(*) AS numMechanics"
                + " FROM Schedule"
                + " WHERE centerId = " + LoginUser.getCenterId()
                + " AND week = " + week
                + " AND day = " + day
                + " AND timeSlot >= " + timeSlotStart
                + " AND timeSlot <= " + timeSlotEnd;
    }

}
