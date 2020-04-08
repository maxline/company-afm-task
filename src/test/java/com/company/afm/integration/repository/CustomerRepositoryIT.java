package com.company.afm.integration.repository;

import com.company.afm.domain.Customer;
import com.company.afm.repository.CustomerRepository;
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
public class CustomerRepositoryIT {

    private static final long NON_EXISTING_CUSTOMER_ID = 9999L;

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer1;
    private Customer customer2;
    private Customer customer3;

    @BeforeEach
    public void setup() {
        customer1 = new Customer("test_name_1", "test_surname_1");
        customer2 = new Customer("test_name_2", "test_surname_2");
        customer3 = new Customer("test_name_3", "test_surname_3");

        entityManager.persist(customer1);
        entityManager.persist(customer2);
        entityManager.persist(customer3);
        entityManager.flush();
    }

    @Test
    public void shouldReturnAllCustomers_whenFindAll() {
        List<Customer> actual = new ArrayList<>();
        customerRepository.findAll().forEach(actual::add);

        assertThat(actual).hasSize(3);
        assertThat(actual.get(0)).isEqualTo(customer1);
        assertThat(actual.get(1)).isEqualTo(customer2);
        assertThat(actual.get(2)).isEqualTo(customer3);
    }

    @Test
    public void shouldReturnCustomer_whenFindById() {
        Long givenId = (Long) entityManager.getId(customer1);
        Customer actual = customerRepository.findById(givenId).get();

        assertThat(actual).isEqualTo(customer1);
    }

    @Test
    public void shouldNotReturnCustomer_whenFindByNonExistingId() {
        Optional<Customer> actual = customerRepository.findById(NON_EXISTING_CUSTOMER_ID);

        assertThat(actual).isEmpty();
    }
}