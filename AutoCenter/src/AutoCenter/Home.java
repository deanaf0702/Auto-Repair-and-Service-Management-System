package AutoCenter;

public class Home {

  private static int INITIAL_SELECTION = 2;
  private static int MIN_SELECTION = 1;
  private static int MAX_SELECTION = 2;
  private static User user = null;

  public static void main(String[] args) {
    run();
  }

  public static void run() {
    int selection = INITIAL_SELECTION;
    do {
      display();
      System.out.print("Enter choice (1-2) from the given options displayed above: ");
      selection = ScanHelper.nextInt();

    } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));

    navigate(selection);
  }

  public static void display() {
    System.out.println("###############################");
    System.out.println("#####   User: Home Menu   #####");
    System.out.println("###############################");
    System.out.println("# 1 Login                     #");
    System.out.println("# 2 Exit                      #");
    System.out.println("###############################");
  }

  public static void navigate(int value) {
    switch (value) {
      case 1:
        login();
        break;
      case 2:
        exit();
        break;
    }
  }

  public static void exit() {
    System.out.println(
        "Thanks for using Autor Repair and Service Management! We hope to see you again soon."
            + " Goodbye.");
  }

  public static void login() {
    Login login = new Login();
    login.run();
  }

  public static void setUser(User appUser) {
    user = appUser;
  }

  public static User getUser() {
    return user;
  }
}
