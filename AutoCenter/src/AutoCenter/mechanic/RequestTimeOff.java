package AutoCenter.mechanic;

import AutoCenter.Interface;

public class RequestTimeOff implements Interface {

  @Override
  public void run() {
    // TODO Auto-generated method stub

  }

  @Override
  public void display() {
    System.out.println("######################################");
    System.out.println("#####   Manager: Request Time    #####");
    System.out.println("#######        Off Menu        #######");
    System.out.println("######################################");
    System.out.println("# 1 Send the request                 #");
    System.out.println("# 2 Go Back                          #");
    System.out.println("######################################");
  }

  @Override
  public void navigate(int selection) {
    // TODO Auto-generated method stub

  }

  @Override
  public void goBack() {
    // TODO Auto-generated method stub

  }
}
