package models;

import java.util.Objects;

public class Receptionist extends User {

    private String email;
    private String phone;
    private double salary;

    public Receptionist ( final String userId, final String password, final String firstName, final String lastName,
            final String address, final int serviceCenterId, final String role, final String email, final String phone,
            final double salary ) {
        super( userId, password, firstName, lastName, address, serviceCenterId, role );
        this.email = email;
        this.phone = phone;
        this.salary = salary;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail ( final String email ) {
        this.email = email;
    }

    public String getPhone () {
        return phone;
    }

    public void setPhone ( final String phone ) {
        this.phone = phone;
    }

    public double getSalary () {
        return salary;
    }

    public void setSalary ( final double salary ) {
        this.salary = salary;
    }

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash( email, phone, salary );
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
        final Receptionist other = (Receptionist) obj;
        return Objects.equals( email, other.email ) && Objects.equals( phone, other.phone )
                && Double.doubleToLongBits( salary ) == Double.doubleToLongBits( other.salary );
    }

}
