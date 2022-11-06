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
     * @param menuTitle     the title of the menu
     * @param menuContents  the options to display
     * @param menuSeparator the separator to use between the title and the options
     */
    public static void displayMenu(String menuTitle, String[] menuContents, String menuSeparator) {
        System.out.println(menuSeparator);
        System.out.println(MENU_TITLE_END + menuTitle + MENU_TITLE_END);
        System.out.println(menuSeparator);
        for (String item : menuContents) {
            System.out.println(item);
        }
        System.out.println(menuSeparator);
    }

    /**
     * Displays the usage directions for a particular menu.
     *
     * @param usageComponents    the components of the usage directions
     * @param example            the example to display
     * @param usageHeader        the usage header to display
     * @param exampleHeader      the example header to display
     * @param directionSeparator the separator to use between the usage
     *                           header and
     *                           example header
     */
    public static void displayUsageDirections(String[] usageComponents, String example, String usageHeader,
            String exampleHeader,
            String directionSeparator) {
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
        System.out.println("the example provided above using the delimiter, `;`");
        System.out.println();
    }
}
