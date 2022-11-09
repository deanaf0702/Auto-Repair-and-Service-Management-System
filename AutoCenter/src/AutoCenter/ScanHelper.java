package AutoCenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ScanHelper {
    public static Scanner  scanner   = new Scanner( System.in );
    private static boolean wentCatch = false;

    public static int nextInt () {
        
        try {
            final int value = scanner.nextInt();
            scanner.nextLine();
            return value;
        }
        catch ( final Exception e ) {
            scanner.nextLine();
            System.out.print( "Try again: " );
            return nextInt();
        }
    }

    public static String next () {
        try {
            final String val = scanner.next();
            scanner.nextLine();
            return val;
        }
        catch ( final Exception e ) {
            scanner.nextLine();
            System.out.println( "Try again: " );
            return scanner.next();
        }
    }

    public static Double nextDouble () {
        try {
            final double val = scanner.nextDouble();
            scanner.nextLine();
            return val;
        }
        catch ( final Exception e ) {
            wentCatch = true;
            scanner.nextLine();
            return scanner.nextDouble();
        }
    }

    public static String nextLine () {
        try {
            final String stringInput = scanner.nextLine();
            return stringInput;
        }
        catch ( final Exception e ) {
            wentCatch = true;
            System.out.println( "Try again: " );
            return scanner.nextLine();
        }
    }

    public static Date nextDate () {
       
        do {
            try {
                wentCatch = false;
                final SimpleDateFormat formatter = new SimpleDateFormat( "dd-MMM-yyyy" );
                final Date stringDate = formatter.parse( scanner.next() );
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
