package com.company.afm.service;

import com.company.afm.domain.Loan;

import java.util.List;

public interface LoanService {
    List<Loan> findAll();

    List<Loan> findByIsApproved();

    List<Loan> findByCustomerIdAndIsApproved(Long id);

    Loan apply(Loan newLoan);
}