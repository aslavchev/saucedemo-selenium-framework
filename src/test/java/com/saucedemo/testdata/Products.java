package com.saucedemo.testdata;

/**
 * Available products in SauceDemo inventory.
 */
public enum Products {
    BACKPACK("sauce-labs-backpack"),
    BIKE_LIGHT("sauce-labs-bike-light"),
    BOLT_TSHIRT("sauce-labs-bolt-t-shirt"),
    FLEECE_JACKET("sauce-labs-fleece-jacket"),
    ONESIE("sauce-labs-onesie"),
    TSHIRT_RED("test.allthethings()-t-shirt-(red)");

    private final String id;

    Products(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}
