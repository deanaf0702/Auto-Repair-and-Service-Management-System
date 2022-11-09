package AutoCenter.customer;

import AutoCenter.ScanHelper;
import AutoCenter.UserFlowFunctionality;

public class ViewAndUpdateProfile implements UserFlowFunctionality {

    public static final String MENU_SEPARATOR = "##################################################";

    public ViewAndUpdateProfile () {

    }

    @Override
    public void run () {
        int selection = 0;
        display();
        do {
            System.out.println( "Enter choice(1-4)" );
            selection = ScanHelper.nextInt();
        }
        while ( ! ( selection >= 1 && selection <= 4 ) );

        navigate( selection );
    }

    @Override

    public void display () {
        System.out.println( MENU_SEPARATOR );
        System.out.println( "##### Customer: View and Update Profile Menu #####" );
        System.out.println( MENU_SEPARATOR );
        System.out.println( "# 1 View Profile                                 #" );
        System.out.println( "# 2 Add Car                                      #" );
        System.out.println( "# 3 Delete Car                                   #" );
        System.out.println( "# 4 Go Back                                      #" );
        System.out.println( MENU_SEPARATOR );
    }

    @Override
    public void navigate ( final int selection ) {
        switch ( selection ) {
            case 1:
                new ViewProfile().run();
                break;
            case 2:
                new AddCar().run();
                break;
            case 3:
                new DeleteCar().run();
                break;
            case 4:
                goBack();
                break;
            default:
                System.out.println( "Invalid selection" );
                break;
        }
    }

    @Override
    public void goBack () {
        new Customer().run();
    }
}
