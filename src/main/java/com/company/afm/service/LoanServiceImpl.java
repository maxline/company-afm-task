package com.company.afm.service;

import com.company.afm.domain.Loan;
import com.company.afm.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private static final boolean STATUS_APPROVED = true;
    private final LoanRepository repository;

    public LoanServiceImpl(LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Loan> findAll() {
        List<Loan> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public List<Loan> findByIsApproved() {
        return repository.findByIsApproved(STATUS_APPROVED);
    }

    @Override
    public List<Loan> findByCustomerIdAndIsApproved(Long id) {
        return repository.findByCustomerIdAndIsApproved(id, true);
    }

    @Override
    public Loan apply(Loan newLoan) {
        return repository.save(newLoan);
    }
}