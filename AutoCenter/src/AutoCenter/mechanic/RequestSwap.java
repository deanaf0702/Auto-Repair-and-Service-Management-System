package AutoCenter.mechanic;

import AutoCenter.Interface;

public class RequestSwap implements Interface {

  @Override
  public void run() {
    // TODO Auto-generated method stub

  }

  public void displayDirection() {
    System.out.println("#############################");
    System.out.println("######      Usage      ######");
    System.out.println("#############################");
    System.out.println("# A. Timeslot range to swap #");
    System.out.println("# (identified by dayid,     #");
    System.out.println("# weekid, begin slot id,    #");
    System.out.println("# end slot id)              #");
    System.out.println("# ------------------------- #");
    System.out.println("# B. Employee ID of a       #");
    System.out.println("# mechanic that is being    #");
    System.out.println("# requested for swap        #");
    System.out.println("# ------------------------- #");
    System.out.println("# C. Timeslot range of the  #");
    System.out.println("# requested mechanic that   #");
    System.out.println("# is of interest            #");
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
    System.out.println("#######################################");
    System.out.println("##### Mechanic: Request Swap Menu #####");
    System.out.println("#######################################");
    System.out.println("# 1 Send the request                  #");
    System.out.println("# 2 Go Back                           #");
    System.out.println("#######################################");
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
