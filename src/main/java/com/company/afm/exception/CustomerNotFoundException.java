package com.company.afm.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Long id) {
        super("could not find customer " + id);
    }
}