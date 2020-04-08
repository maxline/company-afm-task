package com.company.afm.rest;

import com.company.afm.domain.Loan;
import com.company.afm.service.LoanService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(@Qualifier("loanServiceImpl") LoanService loanService) {
        this.loanService = loanService;
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

        return loanService.apply(newLoan, request.getRemoteAddr());
    }
}