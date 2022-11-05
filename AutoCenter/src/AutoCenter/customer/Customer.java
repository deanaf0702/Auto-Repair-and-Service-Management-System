package AutoCenter.customer;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;
import AutoCenter.services.UserService;

public class Customer implements UserFlowFunctionality {

  private static int INITIAL_SELECTION = 4;
  private static int MIN_SELECTION = 1;
  private static int MAX_SELECTION = 4;
  private UserService userService = null;

  public Customer() {
    userService = new UserService();
  }

  @Override
  public void run() {
    int selection = INITIAL_SELECTION;
    display();
    do {
      System.out.print("Enter choice (1-4) from the given options displayed above: ");
      selection = ScanHelper.nextInt();
    } while (!(selection >= MIN_SELECTION && selection <= MAX_SELECTION));

    navigate(selection);
  }

  @Override
  public void navigate(final int selection) {
    switch (selection) {
      case 1:
        new ViewAndUpdateProfile().run();
        break;
      case 2:
        new ViewAndScheduleService().run();
        break;
      case 3:
        new Invoices().run();
        break;
      case 4:
        goBack();
        break;
    }
  }

  @Override
  public void goBack() {
    userService.logout();
  }

  @Override
  public void display() {
    System.out.println("##################################");
    System.out.println("##### Customer: Landing Page #####");
    System.out.println("##################################");
    System.out.println("# 1 View and Update Profile      #");
    System.out.println("# 2 View and Schedule Service    #");
    System.out.println("# 3 Invoices                     #");
    System.out.println("# 4 Logout                       #");
    System.out.println("##################################");
  }
}
