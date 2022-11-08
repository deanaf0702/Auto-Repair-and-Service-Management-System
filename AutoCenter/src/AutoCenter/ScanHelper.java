package AutoCenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ScanHelper {
    public static Scanner  scanner   = new Scanner( System.in );
    private static boolean wentCatch = false;

    public static int nextInt () {
        // Scanner is skipping sometimes, so I have found a solution through
        // google
        // https://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-or-nextfoo

        final int selection = 0;
        try {
            wentCatch = false;
            final int value = scanner.nextInt();
            return value;
        }
        catch ( final Exception e ) {
            wentCatch = true;
        }
        return selection;
    }

    public static String next () {
        try {
            wentCatch = false;
            return scanner.next();
        }
        catch ( final Exception e ) {
            wentCatch = true;
            scanner.nextLine();
            System.out.println( "Try again: " );
        }
        return null;
    }

    public static Double nextDouble () {
        try {
            wentCatch = false;
            return scanner.nextDouble();
        }
        catch ( final Exception e ) {
            wentCatch = true;
            scanner.nextLine();
            System.out.println( "Try again: " );
        }

        return 0.0;
    }

    public static String nextLine () {
        try {
            wentCatch = false;
            final String stringInput = scanner.nextLine();
            return stringInput;
        }
        catch ( final Exception e ) {
            wentCatch = true;
            // scanner.nextLine();
            System.out.println( "Try again: " );
        }

        return null;
    }

    public static Date nextDate () {
        // scanner.nextLine();
        do {
            try {
                wentCatch = false;
                final SimpleDateFormat formatter = new SimpleDateFormat( "dd-MMM-yyyy" );
                final Date stringDate = formatter.parse( scanner.next() );
                // DateTimeFormatter formatter =
                // DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");

                return stringDate;

            }
            catch ( final Exception e ) {
                wentCatch = true;
                scanner.nextLine();
                System.out.println( "Try again: " );
            }
        }
        while ( wentCatch );
        return null;
    }
}
