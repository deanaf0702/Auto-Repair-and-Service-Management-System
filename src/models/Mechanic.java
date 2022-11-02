package models;

import java.util.Objects;

public class Mechanic extends User {

    private String phone;
    private String email;
    private double wage;

    public Mechanic ( final String userId, final String password, final String firstName, final String lastName,
            final String address, final int serviceCenterId, final String role, final String phone, final String email,
            final double wage ) {
        super( userId, password, firstName, lastName, address, serviceCenterId, role );
        this.phone = phone;
        this.email = email;
        this.wage = wage;
    }

    public String getPhone () {
        return phone;
    }

    public void setPhone ( final String phone ) {
        this.phone = phone;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail ( final String email ) {
        this.email = email;
    }

    public double getWage () {
        return wage;
    }

    public void setWage ( final double wage ) {
        this.wage = wage;
    }

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash( email, phone, wage );
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
        final Mechanic other = (Mechanic) obj;
        return Objects.equals( email, other.email ) && Objects.equals( phone, other.phone )
                && Double.doubleToLongBits( wage ) == Double.doubleToLongBits( other.wage );
    }

}
