package models;

import java.util.Objects;

public class Customer extends User {

    private String  status;
    private boolean isActive;

    public Customer ( final String userId, final String password, final String firstName, final String lastName,
            final String address, final int serviceCenterId, final String role, final String status,
            final boolean isActive ) {
        super( userId, password, firstName, lastName, address, serviceCenterId, role );
        this.status = status;
        this.isActive = isActive;
    }

    public String getStatus () {
        return status;
    }

    public void setStatus ( final String status ) {
        this.status = status;
    }

    public boolean isActive () {
        return isActive;
    }

    public void setActive ( final boolean isActive ) {
        this.isActive = isActive;
    }

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash( isActive, status );
        return result;
    }

    @Override
    public boolean equals ( final Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( !super.equals( obj ) ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final Customer other = (Customer) obj;
        return isActive == other.isActive && Objects.equals( status, other.status );
    }

}
