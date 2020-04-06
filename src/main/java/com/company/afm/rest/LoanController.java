package com.company.afm.rest;

import com.company.afm.domain.Loan;
import com.company.afm.service.LoanService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("loans")
public class LoanController {

    private final LoanService service;

    public LoanController(@Qualifier("loanServiceImpl") LoanService service) {
        this.service = service;
    }

    @GetMapping("/get/all")
    public List<Loan> findAll() {
        return service.findAll();
    }

    @GetMapping("/get/approved")
    public List<Loan> findByIsApproved() {
        return service.findByIsApproved();
    }

    @GetMapping("/get/customer/{id}")
    public List<Loan> findByCustomerIdAndIsApproved(@PathVariable Long id) {
        return service.findByCustomerIdAndIsApproved(id);
    }

    @PostMapping("/post")
    public Loan apply(@RequestBody Loan newLoan) {
        return service.apply(newLoan);
    }
}