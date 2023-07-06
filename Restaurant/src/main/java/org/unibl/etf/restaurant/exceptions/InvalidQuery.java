package org.unibl.etf.restaurant.exceptions;

public class InvalidQuery extends Exception{
    public InvalidQuery() {
        super();
    }

    public InvalidQuery(String message) {
        super(message);
    }
}
