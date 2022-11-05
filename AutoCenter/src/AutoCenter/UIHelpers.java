package AutoCenter;

public class UIHelpers {
    private UIHelpers() {
    }

    public static void displayMenu(String menuTitle, String[] menuContents, String separator) {
        System.out.println(separator);
        System.out.println("##### " + menuTitle + " #####");
        System.out.println(separator);
        for (String item : menuContents) {
            System.out.println(item);
        }
        System.out.println(separator);
    }

    // General usage directions
    public static void displayUsageDirections() {
        System.out.println();
        System.out.println("Please enter the details following the usage description and example provided above.");
        System.out.println();
        System.out.println("NOTE: It's important to enter information following");
        System.out.println("the example provided above using the delimiter, `;`");
        System.out.println();
    }
}
