package AutoCenter.manager;

import AutoCenter.UserFlowFunctionality;
import AutoCenter.ScanHelper;

public class SetupServicePrices implements UserFlowFunctionality {

  @Override
  public void run() {
    int selection = 3;
    display();
    do {
      System.out.print("Enter choice (1-3) from the given options displayed above: ");
      selection = ScanHelper.nextInt();
    } while (!(selection >= 1 && selection <= 3));
    navigate(selection);
  }

  @Override
  public void display() {
    System.out.println("######################################");
    System.out.println("#####   Manager: Setup Service   #####");
    System.out.println("#######      Prices Menu       #######");
    System.out.println("######################################");
    System.out.println("# 1 Setup maintenance service prices #");
    System.out.println("# 2 Setup repair service prices      #");
    System.out.println("# 3 Go Back                          #");
    System.out.println("######################################");
  }

  @Override
  public void navigate(int selection) {

    switch (selection) {
      case 1:
        new SetupMaintenanceServicePrices().run();
        break;
      case 2:
        new SetupRepairServicePrices().run();
        break;
      case 3:
        goBack();
    }
  }

  @Override
  public void goBack() {
    new SetupStore().run();
  }
}
