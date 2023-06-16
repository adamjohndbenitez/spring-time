package com.fin.mngmnt.srvs.payroll;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Employee {

    private @Id @GeneratedValue Long id;
    private String name;
    private String role;

    Employee() {}

    Employee(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
                Objects.equals(this.name, empl.name) &&
                Objects.equals(this.role, empl.role));
    }

    @Override
    public String toString() {
        return super.toString() +
            "Employee{id=" + this.id + ", "
            + "name='" + this.name + '\'' + ", "
            + "role='" + this.role + '\'' + '}';
    }
}
