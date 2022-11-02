package models;

import java.util.Objects;

public class User {

    private String userId;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private int    serviceCenterId;
    private String role;

    public User ( final String userId, final String password, final String firstName, final String lastName,
            final String address, final int serviceCenterId, final String role ) {
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.serviceCenterId = serviceCenterId;
        this.role = role;
    }

    public String getUserId () {
        return userId;
    }

    public void setUserId ( final String userId ) {
        this.userId = userId;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword ( final String password ) {
        this.password = password;
    }

    public String getFirstName () {
        return firstName;
    }

    public void setFirstName ( final String firstName ) {
        this.firstName = firstName;
    }

    public String getLastName () {
        return lastName;
    }

    public void setLastName ( final String lastName ) {
        this.lastName = lastName;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress ( final String address ) {
        this.address = address;
    }

    public int getServiceCenterId () {
        return serviceCenterId;
    }

    public void setServiceCenterId ( final int serviceCenterId ) {
        this.serviceCenterId = serviceCenterId;
    }

    public String getRole () {
        return role;
    }

    public void setRole ( final String role ) {
        this.role = role;
    }

    @Override
    public int hashCode () {
        return Objects.hash( address, firstName, lastName, password, role, serviceCenterId, userId );
    }

    @Override
    public boolean equals ( final Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals( address, other.address ) && Objects.equals( firstName, other.firstName )
                && Objects.equals( lastName, other.lastName ) && Objects.equals( password, other.password )
                && Objects.equals( role, other.role ) && serviceCenterId == other.serviceCenterId
                && Objects.equals( userId, other.userId );
    }
}
