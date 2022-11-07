package AutoCenter;

public class LoginUser {
    private int    id;
    private String role;
    private String firstName;
    private String lastName;
    private int    centerId;

    public LoginUser () {
    }

    public void setId ( final int value ) {
        this.id = value;
    }

    public int getId () {
        return this.id;
    }

    public void setRole ( final String value ) {
        this.role = value;
    }

    public String getRole () {
        return this.role;
    }

    public void setFirstName ( final String value ) {
        this.firstName = value;
    }

    public String getFirstName () {
        return this.firstName;
    }

    public void setLastName ( final String value ) {
        this.lastName = value;
    }

    public String getLastName () {
        return this.lastName;
    }

    public String getName () {
        return this.firstName + " " + this.lastName;
    }

    public void setCenterId ( final int value ) {
        this.centerId = value;
    }

    public int getCenterId () {
        return this.centerId;
    }
}
