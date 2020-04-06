package com.company.afm.repository;

import com.company.afm.domain.Loan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Long> {
    List<Loan> findByIsApproved(boolean isApproved);

    List<Loan> findByCustomerIdAndIsApproved(long customerId, boolean isApproved);
}