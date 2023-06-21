package com.fin.mngmnt.srvs.payroll;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Employee {

    private @Id @GeneratedValue Long id;
    //private String name;
    //^ replaced with fistname and lastname
    private String firstname;
    private String lastname;
    private String role;

    Employee() {}

    Employee(String firstname, String lastname, String role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }

//    public String getName() {
//        return name;
//    }
    //^replaced with appending getName() method

    /**
     * A "virtual" getter for the old name property, getName() is defined.
     * It uses the firstName and lastName fields to produce a value.
     * @return appended firstName and lastName fields
     */
    public String getName() {
        return this.firstname + " " + this.lastname;
    }

//    public void setName(String name) {
//        this.name = name;
//    }
    //^replaced with appending firstname and lastname

    /**
     * A "virtual" setter for the old name property is also defined, setName().
     * It parses an incoming string and stores it into the proper fields.
     * @param name
     */
    public void setName(String name) {
        String[] parts = name.split(" ");
        this.firstname = parts[0];
        this.lastname = parts[1];
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    @Override
    public int hashCode() {

        //same as Objects.hash(this.id, this.name, this.role)
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (!(obj instanceof Employee)) return false;

        Employee empl = (Employee) obj;

        return super.equals(obj) ||
            (Objects.equals(this.id, empl.id) &&
                Objects.equals(this.firstname, empl.firstname) &&
                Objects.equals(this.lastname, empl.lastname) &&
                Objects.equals(this.role, empl.role));
    }

    @Override
    public String toString() {
        return " Employee{id=" + this.id + ", " +
            "firstname='" + this.firstname + '\'' + ", " +
            "lastname='" + this.lastname + '\'' + ", " +
            "role='" + this.role + '\'' + '}' + "\n";
    }
}
