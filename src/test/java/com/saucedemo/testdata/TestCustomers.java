package com.saucedemo.testdata;

/**
 * Test customer data for checkout flows.
 */
public enum TestCustomers {
    STANDARD("John", "Doe", "12345");

    private final String firstName;
    private final String lastName;
    private final String postalCode;

    TestCustomers(String firstName, String lastName, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String postalCode() {
        return postalCode;
    }
}
