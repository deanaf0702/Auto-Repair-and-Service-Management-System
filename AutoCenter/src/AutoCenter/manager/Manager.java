package AutoCenter.manager;

import AutoCenter.Interface;
import AutoCenter.ScanHelper;
import AutoCenter.services.UserService;

public class Manager implements Interface {

  private UserService userservice = null;

  public Manager() {
    userservice = new UserService();
  }

  public void run() {
    int selection = 3;
    display();
    do {
      System.out.print("Enter choice (1-3) from the given options displayed above: ");
      selection = ScanHelper.nextInt();
    } while (!(selection >= 1 && selection <= 3));

    navigate(selection);
  }

  public void navigate(int selection) {
    switch (selection) {
      case 1:
        new SetupStore().run();
        break;
      case 2:
        new AddNewEmployee().run();
        break;
      case 3:
        goBack();
        break;
    }
  }

  @Override
  public void display() {
    System.out.println("#################################");
    System.out.println("##### Manager: Landing Page #####");
    System.out.println("#################################");
    System.out.println("# 1 Setup Store                 #");
    System.out.println("# 2 Add New Employee            #");
    System.out.println("# 3 Logout                      #");
    System.out.println("#################################");
  }

  @Override
  public void goBack() {
    userservice.logout();
  }
}
