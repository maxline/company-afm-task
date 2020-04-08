package com.company.afm.integration.repository;

import com.company.afm.domain.Customer;
import com.company.afm.domain.Loan;
import com.company.afm.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class LoanRepositoryIT {

    private static final boolean STATUS_APPROVED = true;
    private static final boolean STATUS_DECLINED = false;
    private static final long NON_EXISTING_LOAN_ID = 9999L;

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private LoanRepository loanRepository;

    private Customer customer1;
    private Customer customer2;

    private Loan loan1;
    private Loan loan2;
    private Loan loan3;
    private Loan loan4;

    @BeforeEach
    public void setup() {
        customer1 = new Customer("test_name_1", "test_surname_1");
        customer2 = new Customer("test_name_2", "test_surname_2");

        entityManager.persist(customer1);
        entityManager.persist(customer2);
        entityManager.flush();

        loan1 = new Loan(30, 3000, "UA", customer1, STATUS_APPROVED);
        loan2 = new Loan(40, 4000, "UA", customer1, STATUS_APPROVED);
        loan3 = new Loan(50, 5000, "USA", customer2, STATUS_APPROVED);
        loan4 = new Loan(60, 6000, "USA", customer2, STATUS_DECLINED);

        entityManager.persist(loan1);
        entityManager.persist(loan2);
        entityManager.persist(loan3);
        entityManager.persist(loan4);

        entityManager.flush();
    }

    @Test
    public void shouldReturnAllLoans_whenFindAll() {
        List<Loan> actual = new ArrayList<>();
        loanRepository.findAll().forEach(actual::add);

        assertThat(actual).hasSize(4);
        assertThat(actual.get(0)).isEqualTo(loan1);
        assertThat(actual.get(1)).isEqualTo(loan2);
        assertThat(actual.get(2)).isEqualTo(loan3);
        assertThat(actual.get(3)).isEqualTo(loan4);
    }

    @Test
    public void shouldReturnAllLoans_whenFindByIsApproved() {
        List<Loan> actual = loanRepository.findByIsApproved(STATUS_APPROVED);

        assertThat(actual).hasSize(3);
        assertThat(actual.get(0)).isEqualTo(loan1);
        assertThat(actual.get(1)).isEqualTo(loan2);
        assertThat(actual.get(2)).isEqualTo(loan3);
    }

    @Test
    public void shouldReturnAllLoans_whenFindByCustomerIdAndIsApproved() {
        long customer2Id = (Long) entityManager.getId(customer2);

        List<Loan> actual = loanRepository.findByCustomerIdAndIsApproved(customer2Id, STATUS_APPROVED);

        assertThat(actual).hasSize(1);
        assertThat(actual.get(0)).isEqualTo(loan3);
    }

    @Test
    public void shouldReturnLoan_whenFindById() {
        Long givenId = (Long) entityManager.getId(loan1);
        Loan actual = loanRepository.findById(givenId).get();

        assertThat(actual).isEqualTo(loan1);
    }

    @Test
    public void shouldNotReturnLoan_whenFindByNonExistingId() {
        Optional<Loan> actual = loanRepository.findById(NON_EXISTING_LOAN_ID);

        assertThat(actual).isEmpty();
    }
}