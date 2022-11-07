package AutoCenter.mechanic;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.repository.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
        return "SELECT week, day, timeSlot"
                + " FROM Schedule"
                + " WHERE mechanicId = " + LoginUser.getId()
                + " AND centerId = " + LoginUser.getCenterId();
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

            HashMap<String, ArrayList<Integer>> schedule = new HashMap<String, ArrayList<Integer>>();

            while (rs.next()) {
                int week = rs.getInt("week");
                int day = rs.getInt("day");
                int timeSlot = rs.getInt("timeSlot");

                String weekStr = Integer.toString(week);
                String dayStr = Integer.toString(day);
                String weekDay = weekStr + "_" + dayStr;

                ArrayList<Integer> timeSlots = schedule.get(weekDay);
                if (timeSlots == null) {
                    timeSlots = new ArrayList<>();
                    timeSlots.add(timeSlot);
                    schedule.put(weekDay, timeSlots);
                } else {
                    if (!timeSlots.contains(timeSlot))
                        timeSlots.add(timeSlot);
                }
            }

            int scheduleCount = 1;
            for (Map.Entry<String, ArrayList<Integer>> scheduleEntry : schedule.entrySet()) {
                String weekDay = scheduleEntry.getKey();
                ArrayList<Integer> timeSlots = scheduleEntry.getValue();
                Integer week = Integer.valueOf(weekDay.split("_")[0]);
                Integer day = Integer.valueOf(weekDay.split("_")[1]);
                Integer minTimeSlot = Collections.min(timeSlots);
                Integer maxTimeSlot = Collections.max(timeSlots);

                String startDate = UIHelpers.convertToStartDate(week, day, minTimeSlot);
                String endDate = UIHelpers.convertToEndDate(week, day, maxTimeSlot);

                System.out.println(scheduleCount + ". " + startDate + " - " + endDate);
                scheduleCount += 1;
            }

            if (scheduleCount == 1) {
                System.out.println("You have no upcoming shifts.");
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
