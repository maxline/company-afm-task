package com.company.afm.service;

import com.company.afm.domain.Country;
import com.company.afm.domain.Loan;
import com.company.afm.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    private static final boolean STATUS_DECLINED = false;
    private static final boolean STATUS_APPROVED = true;
    private final LoanRepository repository;
    private final CountryResolverService countryResolverService;
    private final ValidatorService validatorService;

    public LoanServiceImpl(LoanRepository repository, CountryResolverService countryResolverService, ValidatorService validatorService) {
        this.repository = repository;
        this.countryResolverService = countryResolverService;
        this.validatorService = validatorService;
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
    public Loan apply(Loan loan, String ipAddress) {
        Country country = countryResolverService.resolveCountryByIpAddress(ipAddress);

        if (validatorService.validateCustomer(loan.getCustomer()) &&
                validatorService.validateOrdersQuantity(country)) {
            loan.setIsApproved(STATUS_APPROVED);
        } else {
            loan.setIsApproved(STATUS_DECLINED);
        }
        return repository.save(loan);
    }
}