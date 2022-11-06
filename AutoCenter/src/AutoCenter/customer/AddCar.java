package AutoCenter.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;

import AutoCenter.Home;
import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;
import AutoCenter.UIHelpers;
import AutoCenter.repository.DbConnection;

/**
 * Handles functionality for add car menu of
 * customers including display, input, and output.
 *
 * @author jkersey, tsenck12, deoks
 */
public class AddCar implements UserFlowFunctionality {

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
    private static final String MENU_SEPARATOR = "##################################";

    @Override
    public void run() {
        System.out.println("VIN Number: ");
        final String vin = ScanHelper.next();
        System.out.println("Car Manufacturer: ");
        final String manf = ScanHelper.next();
        System.out.println("Current Mileage: ");
        final int mileage = ScanHelper.nextInt();
        System.out.println("Car Year: ");
        final int year = ScanHelper.nextInt();
        int selection = 2;
        do {
            display();
            System.out.print("Enter choice (" + MIN_SELECTION + "-" + MAX_SELECTION
                    + ") from the given options displayed above: ");
            selection = ScanHelper.nextInt();

        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));
        if (selection == 1) {
            final DbConnection db = new DbConnection();
            final Connection conn = db.getConnection();
            try {
                final PreparedStatement stmt = conn.prepareStatement("insert into CustomerVehicles "
                        + "(vin, customerId, serviceCenter, model, mileage, year, lastMClass) values "
                        + "(?, ?, ?, ?, ?, ?, 'C')");
                stmt.setString(1, vin);
                stmt.setInt(2, Home.getUser().getId());
                stmt.setInt(3, Home.getUser().getCenterId());
                stmt.setString(4, manf);
                stmt.setInt(5, mileage);
                stmt.setInt(6, year);
                stmt.executeUpdate();
            } catch (final Exception e) {
                System.out.println("Error adding car");
            } finally {
                db.close();
            }

        }
        navigate(selection);
    }

    @Override
    public void display() {
        String[] menuOptions = {
                "# 1 Save Information             #",
                "# 2 Cancel                       #"
        };

        UIHelpers.displayMenu(
                " Customer: Add Car Menu ",
                menuOptions,
                MENU_SEPARATOR);
    }

    @Override
    public void navigate(final int selection) {
        goBack();
    }

    @Override
    public void goBack() {
        new Customer().run();
    }
}
