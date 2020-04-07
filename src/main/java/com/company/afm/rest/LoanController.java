package com.company.afm.rest;

import com.company.afm.domain.Customer;
import com.company.afm.domain.Loan;
import com.company.afm.service.CustomerService;
import com.company.afm.service.LoanService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("loans")
public class LoanController {

    private final LoanService loanService;
    private final CustomerService customerService;

    public LoanController(@Qualifier("loanServiceImpl") LoanService loanService,
                          @Qualifier("customerServiceImpl") CustomerService customerService) {
        this.loanService = loanService;
        this.customerService = customerService;
    }

    @GetMapping("/get/all")
    public List<Loan> findAll() {
        return loanService.findAll();
    }

    @GetMapping("/get/approved")
    public List<Loan> findByIsApproved() {
        return loanService.findByIsApproved();
    }

    @GetMapping("/get/customer/{id}")
    public List<Loan> findByCustomerIdAndIsApproved(@PathVariable Long id) {
        return loanService.findByCustomerIdAndIsApproved(id);
    }

    @PostMapping("/post")
    public Loan apply(@RequestBody Loan newLoan, HttpServletRequest request) {
        Customer customerForSave = customerService.findOrCreateCustomer(newLoan.getCustomer());

        newLoan.setCustomer(customerForSave);

        return loanService.apply(newLoan, request.getRemoteAddr());
    }
}