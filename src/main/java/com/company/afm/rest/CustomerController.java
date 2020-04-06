package com.company.afm.rest;

import com.company.afm.domain.Customer;
import com.company.afm.service.CustomerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(@Qualifier("customerServiceImpl") CustomerService service) {
        this.service = service;
    }

    @GetMapping("/get/{id}")
    public Customer findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/get/all")
    public List<Customer> findAll() {
        return service.findAll();
    }

    @PostMapping("/post")
    public Customer save(@RequestBody Customer newCustomer) {
        return service.save(newCustomer);
    }
}