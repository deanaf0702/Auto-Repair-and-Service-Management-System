package AutoCenter;

import java.util.ArrayList;
import java.util.List;

public class LoginUser {
    private static int           id;
    private static String        role;
    private static String        firstName;
    private static String        lastName;
    private static int           centerId;
    private static List<Integer> cart;

    public LoginUser () {
        cart = new ArrayList<Integer>();
    }

    public static void setId ( final int value ) {
        id = value;
    }

    public static int getId () {
        return id;
    }

    public static void setRole ( final String value ) {
        role = value;
    }

    public static String getRole () {
        return role;
    }

    public static void setFirstName ( final String value ) {
        firstName = value;
    }

    public static String getFirstName () {
        return firstName;
    }

    public static void setLastName ( final String value ) {
        lastName = value;
    }

    public static String getLastName () {
        return lastName;
    }

    public static String getName () {
        return firstName + " " + lastName;
    }

    public static void setCenterId ( final int value ) {
        centerId = value;
    }

    public static int getCenterId () {
        return centerId;
    }

    public static List<Integer> getCart () {
        return cart;
    }

    public static boolean addToCart ( final int serviceId ) {
        if ( cart.contains( serviceId ) ) {
            return false;
        }
        cart.add( serviceId );
        return true;
    }

    public static void emptyCart () {
        cart = new ArrayList<Integer>();
    }
}
