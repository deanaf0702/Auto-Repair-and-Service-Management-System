package AutoCenter.mechanic;

import AutoCenter.Interface;

public class RequestTimeOff implements Interface {

  @Override
  public void run() {
    // TODO Auto-generated method stub

  }

  public void displayDirection() {
    System.out.println("#############################");
    System.out.println("######      Usage      ######");
    System.out.println("#############################");
    System.out.println("# A. Time slots mechanic    #");
    System.out.println("# slots wants to be off     #");
    System.out.println("# (indicated by week, day,  #");
    System.out.println("# time slot start and end   #");
    System.out.println("# slot ids)                 #");
    System.out.println("#############################");
    System.out.println("#####      Example     ######");
    System.out.println("#############################");
    // TODO rewrite here
    System.out.println("##         6; 7; 8        ###");
    System.out.println("#############################");
    System.out.println();
    System.out.println("NOTE: It's important to enter information following");
    System.out.println("the example provided above using the delimiter, `;`");
    System.out.println();
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
