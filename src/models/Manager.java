package models;

import java.util.Objects;

public class Manager extends User {

    private String email;
    private String phone;
    private String salary;

    public Manager ( final String userId, final String password, final String firstName, final String lastName,
            final String address, final int serviceCenterId, final String role, final String email, final String phone,
            final String salary ) {
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

    public String getSalary () {
        return salary;
    }

    public void setSalary ( final String salary ) {
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
        final Manager other = (Manager) obj;
        return Objects.equals( email, other.email ) && Objects.equals( phone, other.phone )
                && Objects.equals( salary, other.salary );
    }

}
