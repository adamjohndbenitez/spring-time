package com.example.demo.relationaldataaccess;

public class Customer {
    private long id;
    private String firstname, lastname;

    public Customer(long id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, firstname='%s', lastname='%s']",
                id, firstname, lastname);
    }

    // getters & setters omitted for brevity
}
