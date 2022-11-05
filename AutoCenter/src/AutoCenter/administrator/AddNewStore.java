package AutoCenter.administrator;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;

public class AddNewStore implements Interface {

    private static final int MIN_SELECTION = 1;
    private static final int MAX_SELECTION = 2;

    private static final String MENU_SEPARATOR = "#####################################";
    private static final String DIRECTION_SEPARATOR = "#####################################";

    @Override
    public void run() {
        int selection;
        display();

        do {
            // displayDirection();
            // TODO add file parsing here
            System.out.print("Enter choice (" + MIN_SELECTION + "-" + MAX_SELECTION + ") from the given options displayed above: ");
            selection = ScanHelper.nextInt();
        } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));

        navigate(selection);
    }

    @Override
    public void display() {
        System.out.println(MENU_SEPARATOR);
        System.out.println("##### Admin: Add New Store Menu #####");
        System.out.println(MENU_SEPARATOR);
        System.out.println("# 1 Add Store                       #");
        System.out.println("# 2 Go back                         #");
        System.out.println(MENU_SEPARATOR);
    }

    @Override
    public void navigate(int selection) {
        switch (selection) {
            case 1:
                new AddNewStore().run();
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
        new Administration().run();
    }
}
