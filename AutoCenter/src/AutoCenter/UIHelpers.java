package AutoCenter;

public class UIHelpers {

    /**
     * Separator for ends of the menu
     */
    private static final String MENU_TITLE_END = "#####";

    /**
     * Private constructor to prevent instantiation
     */
    private UIHelpers() {
    }

    /**
     * Displays the menu with the given title and options.
     *
     * @param menuTitle
     *                      the title of the menu
     * @param menuContents
     *                      the options to display
     * @param menuSeparator
     *                      the separator to use between the title and the options
     */
    public static void displayMenu(final String menuTitle, final String[] menuContents, final String menuSeparator) {
        System.out.println(menuSeparator);
        System.out.println(MENU_TITLE_END + menuTitle + MENU_TITLE_END);
        System.out.println(menuSeparator);
        for (final String item : menuContents) {
            System.out.println(item);
        }
        System.out.println(menuSeparator);
    }

    /**
     * Displays the usage directions for a particular menu.
     *
     * @param usageComponents
     *                           the components of the usage directions
     * @param example
     *                           the example to display
     * @param usageHeader
     *                           the usage header to display
     * @param exampleHeader
     *                           the example header to display
     * @param directionSeparator
     *                           the separator to use between the usage header and
     *                           example
     *                           header
     */
    public static void displayUsageDirections(final String[] usageComponents, final String example,
            final String usageHeader, final String exampleHeader, final String directionSeparator) {
        System.out.println(directionSeparator);
        System.out.println(MENU_TITLE_END + usageHeader + MENU_TITLE_END);
        System.out.println(directionSeparator);

        for (int i = 0; i < usageComponents.length - 1; i++) {
            System.out.println(usageComponents[i]);
            System.out.print("# ");
            for (int j = 0; j < directionSeparator.length() - 4; j++) {
                System.out.print("-");
            }
            System.out.println(" #");
        }
        System.out.println(usageComponents[usageComponents.length - 1]);
        System.out.println(directionSeparator);

        System.out.println(MENU_TITLE_END + exampleHeader + MENU_TITLE_END);
        System.out.println(directionSeparator);

        System.out.println(example);
        System.out.println(directionSeparator);

        displayUsageNote();
    }

    /**
     * Displays the general usage directions common among all menus.
     */
    public static void displayUsageNote() {
        System.out.println();
        System.out.println("Please enter the details following the usage description and example provided above.");
        System.out.println();
        System.out.println("NOTE: It's important to enter information following");
        System.out.println("the example provided above using the delimiter, `;` when necessary.");
        System.out.println();
    }

    public static String convertToStartDate(final int week, final int day, final int timeSlot) {
        final int dd = (week - 1) * 7 + day;
        String time = "";
        switch (timeSlot) {
            case 1:
                time = "8:00";
                break;
            case 2:
                time = "9:00";
                break;
            case 3:
                time = "10:00";
                break;
            case 4:
                time = "11:00";
                break;
            case 5:
                time = "13:00";
                break;
            case 6:
                time = "14:00";
                break;
            case 7:
                time = "15:00";
                break;
            case 8:
                time = "16:00";
                break;
            case 9:
                time = "17:00";
                break;
            case 10:
                time = "18:00";
                break;
            case 11:
                time = "19:00";
                break;
            default:
                break;
        }
        return "11/" + dd + "/2022 " + time;
    }

    public static String convertToEndDate(final int week, final int day, final int timeSlot) {
        final int dd = (week - 1) * 7 + day;
        String time = "";
        switch (timeSlot) {
            case 1:
                time = "9:00";
                break;
            case 2:
                time = "10:00";
                break;
            case 3:
                time = "11:00";
                break;
            case 4:
                time = "12:00";
                break;
            case 5:
                time = "14:00";
                break;
            case 6:
                time = "15:00";
                break;
            case 7:
                time = "16:00";
                break;
            case 8:
                time = "17:00";
                break;
            case 9:
                time = "18:00";
                break;
            case 10:
                time = "19:00";
                break;
            case 11:
                time = "20:00";
                break;
            default:
                break;
        }
        return "11/" + dd + "/2022 " + time;
    }
}
