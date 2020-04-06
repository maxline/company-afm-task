package com.company.afm.service;

import com.company.afm.domain.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();

    Customer findById(long id);

    Customer save(Customer customer);

    Customer findOrCreateCustomer(Customer givenCustomer);
}