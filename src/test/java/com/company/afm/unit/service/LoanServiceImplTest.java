package com.company.afm.unit.service;

import com.company.afm.domain.Customer;
import com.company.afm.domain.Loan;
import com.company.afm.repository.LoanRepository;
import com.company.afm.service.CountryResolverService;
import com.company.afm.service.CustomerService;
import com.company.afm.service.LoanServiceImpl;
import com.company.afm.service.ValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class LoanServiceImplTest {

    private static final boolean STATUS_APPROVED = true;
    private static final boolean STATUS_DECLINED = false;

    private final Customer customer1 = new Customer("test_name_1", "test_surname_1");
    private final Customer customer2 = new Customer("test_name_2", "test_surname_2");

    @Mock
    private LoanRepository loanRepository;
    @Mock
    private CustomerService customerService;
    @Mock
    private CountryResolverService countryResolverService;
    @Mock
    private LoanServiceImpl testInstance;
    @Mock
    private ValidatorService validatorService;

    @BeforeEach
    void setUp() {
        testInstance = new LoanServiceImpl(loanRepository, customerService, countryResolverService, validatorService);
    }

    @Test
    void findAll_shouldReturnAllLoans() {
        List<Loan> givenLoans = Arrays.asList(
                new Loan(30, 3000, "UA", customer1, STATUS_APPROVED),
                new Loan(40, 4000, "UA", customer1, STATUS_APPROVED),
                new Loan(50, 5000, "USA", customer2, STATUS_APPROVED),
                new Loan(60, 6000, "USA", customer2, STATUS_DECLINED));
        when(loanRepository.findAll()).thenReturn(givenLoans);

        List<Loan> actualLoans = testInstance.findAll();

        assertThat(actualLoans).isEqualTo(givenLoans);
    }

    @Test
    void findByIsApproved_shouldReturnApprovedLoans() {
        // given
        List<Loan> approvedLoans = Arrays.asList(
                new Loan(30, 3000, "UA", customer1, STATUS_APPROVED),
                new Loan(40, 4000, "UA", customer1, STATUS_APPROVED),
                new Loan(50, 5000, "USA", customer2, STATUS_APPROVED));

        when(loanRepository.findByIsApproved(STATUS_APPROVED)).thenReturn(approvedLoans);

        // when
        List<Loan> actualLoans = testInstance.findByIsApproved();

        // then
        assertThat(actualLoans).isEqualTo(approvedLoans);
    }

    @Test
    void findByCustomerIdAndIsApproved_shouldReturnLoansByCustomerAndIsApproved() {
        // given
        List<Loan> approvedLoans = Arrays.asList(new Loan(50, 5000, "USA", customer2, STATUS_APPROVED));

        when(loanRepository.findByCustomerIdAndIsApproved(997L, STATUS_APPROVED)).thenReturn(approvedLoans);

        // when
        List<Loan> actualLoans = testInstance.findByCustomerIdAndIsApproved(997L);

        // then
        assertThat(actualLoans).isEqualTo(approvedLoans);
    }

    @Test
    void apply_shouldApplyLoan_whenCustomerIsNotInBlacklist() {
        // given
        Loan appliedLoan = new Loan(30, 3000, "UA", customer1, null);

        when(customerService.findOrCreateCustomer(customer1)).thenReturn(customer1);
        when(validatorService.validateCustomer(customer1)).thenReturn(true);
        when(validatorService.validateOrdersQuantity(any())).thenReturn(true);
        when(loanRepository.save(appliedLoan)).thenReturn(appliedLoan);

        // when
        Loan actualLoans = testInstance.apply(appliedLoan, null);

        // then
        verify(loanRepository).save(appliedLoan);
        assertThat(actualLoans).isEqualTo(appliedLoan);
        assertThat(actualLoans.getIsApproved()).isEqualTo(STATUS_APPROVED);
    }

    @Test
    void apply_shouldDeclineLoan_whenCustomerIsInBlacklist() {
        // given
        Loan appliedLoan = new Loan(30, 3000, "UA", customer1, null);

        when(customerService.findOrCreateCustomer(customer1)).thenReturn(customer1);
        when(validatorService.validateCustomer(customer1)).thenReturn(false);
        when(validatorService.validateOrdersQuantity(any())).thenReturn(true);
        when(loanRepository.save(appliedLoan)).thenReturn(appliedLoan);

        // when
        Loan actualLoans = testInstance.apply(appliedLoan, null);

        // then
        verify(loanRepository).save(appliedLoan);
        assertThat(actualLoans).isEqualTo(appliedLoan);
        assertThat(actualLoans.getIsApproved()).isEqualTo(STATUS_DECLINED);
    }
}