package AutoCenter.manager;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;

public class SetupStore implements UserFlowFunctionality {

  @Override
  public void run() {
    int selection = 4;
    display();
    do {
      System.out.print("Enter choice (1-4) from the given options displayed above: ");
      selection = ScanHelper.nextInt();
    } while (!(selection >= 1 && selection <= 4));
    navigate(selection);
  }

  @Override
  public void display() {
    System.out.println("######################################");
    System.out.println("#####    Manager: Setup Store    #####");
    System.out.println("######################################");
    System.out.println("# 1 Add employees                    #");
    System.out.println("# 2 Setup operational hours          #");
    System.out.println("# 3 Setup Service Prices             #");
    System.out.println("# 4 Go Back                          #");
    System.out.println("######################################");
  }

  @Override
  public void goBack() {
    new Manager().run();
  }

  @Override
  public void navigate(int selection) {
    switch (selection) {
      case 1:
        new AddEmployees().run();
        break;
      case 2:
        new SetupOperationalHours().run();
        break;
      case 3:
        new SetupServicePrices().run();
        break;
      case 4:
        goBack();
        break;
    }
  }
}
