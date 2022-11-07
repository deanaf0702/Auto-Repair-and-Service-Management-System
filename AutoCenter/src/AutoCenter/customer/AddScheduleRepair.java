package AutoCenter.customer;

import AutoCenter.ScanHelper;
import AutoCenter.UserFlowFunctionality;

public class AddScheduleRepair implements UserFlowFunctionality {

    public static String category;

    @Override
    public void run () {
        display();
        int selection = 0;
        do {
            System.out.print( "Enter choice (1-7) from the given options displayed above: " );
            selection = ScanHelper.nextInt();
        }
        while ( ! ( selection >= 1 && selection <= 7 ) );
        if ( selection == 1 ) {
            category = "Engine Services";
        }
        else if ( selection == 2 ) {
            category = "Exhaust Services";
        }
        else if ( selection == 3 ) {
            category = "Electrical Services";
        }
        else if ( selection == 4 ) {
            category = "Transmission Services";
        }
        else if ( selection == 5 ) {
            category = "Tire Services";
        }
        else if ( selection == 6 ) {
            category = "Heating and A/C Services";
        }
        navigate( selection );
    }

    @Override
    public void display () {
        System.out.println( "####################################################" );
        System.out.println( "##### Customer: Schedule Repair      Menu      #####" );
        System.out.println( "####################################################" );
        System.out.println( "# 1 Engine Services                                #" );
        System.out.println( "# 2 Exhaust Services                               #" );
        System.out.println( "# 3 Eectrical Services                             #" );
        System.out.println( "# 4 Transmission Services                          #" );
        System.out.println( "# 5 Tire Services                                  #" );
        System.out.println( "# 6 Heating and A/C Services                       #" );
        System.out.println( "# 7 Go Back                                        #" );
        System.out.println( "####################################################" );

    }

    @Override
    public void navigate ( final int selection ) {
        if ( selection == 7 ) {
            goBack();
        }
        else {
            new IndividualServices().run();
        }
    }

    @Override
    public void goBack () {
        new ScheduleService().run();

    }

    public static String getCategory () {
        return category;
    }
}
