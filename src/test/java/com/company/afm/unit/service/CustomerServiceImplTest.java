package com.company.afm.unit.service;

import com.company.afm.domain.Customer;
import com.company.afm.exception.CustomerNotFoundException;
import com.company.afm.repository.CustomerRepository;
import com.company.afm.service.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;

    private CustomerServiceImpl testInstance;

    private final Customer givenCustomer = new Customer("test_name", "test_surname");

    @BeforeEach
    void setUp() {
        testInstance = new CustomerServiceImpl(customerRepository);
    }

    @Test
    void findAll_shouldReturnAllCustomers() {
        List<Customer> customers = Arrays.asList(
                new Customer("test_name_1", "test_surname_1"),
                new Customer("test_name_2", "test_surname_2"));

        when(customerRepository.findAll()).thenReturn(customers);

        assertThat(testInstance.findAll()).isEqualTo(customers);
    }

    @Test
    void findById_shouldReturnCustomer_whenIdWasFound() {
        when(customerRepository.findById(997L)).thenReturn(Optional.of(givenCustomer));

        assertThat(testInstance.findById(997L)).isEqualTo(givenCustomer);
    }

    @Test
    void findById_shouldThrowCustomerNotFoundException_whenIdWasNotFound() {
        assertThatThrownBy(() -> testInstance.findById(997L))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("could not find customer 997");
    }

    @Test
    void save_shouldReturnSavedCustomer() {
        Customer savedCustomer = new Customer();
        when(customerRepository.save(givenCustomer)).thenReturn(savedCustomer);

        assertThat(testInstance.save(givenCustomer)).isEqualTo(savedCustomer);
    }

    @Test
    void findOrCreateCustomer_shouldReturnExistingCustomer_whenIdWasFound() {
        // given
        givenCustomer.setId(997L);
        when(customerRepository.findById(997L)).thenReturn(Optional.of(givenCustomer));

        // when
        Customer actualCustomer = testInstance.findOrCreateCustomer(givenCustomer);

        // then
        verify(customerRepository).findById(997L);
        verify(customerRepository, never()).save(any(Customer.class));
        assertThat(actualCustomer).isEqualTo(givenCustomer);
    }

    @Test
    void findOrCreateCustomer_shouldSaveCustomer_whenIdWasNotFound() {
        // given
        when(customerRepository.findById(997L)).thenReturn(Optional.of(givenCustomer));

        Customer expectedCustomer = new Customer("test_name", "test_surname");
        when(customerRepository.save(givenCustomer)).thenReturn(expectedCustomer);

        // when
        Customer actualCustomer = testInstance.findOrCreateCustomer(givenCustomer);

        // then
        verify(customerRepository).save(any(Customer.class));
        assertThat(actualCustomer.equals(expectedCustomer));
    }

    @Test
    void findOrCreateCustomer_shouldThrowException_whenCustomerIsNull() {
        assertThatThrownBy(() -> testInstance.findOrCreateCustomer(null))
                .isInstanceOf(IllegalArgumentException.class);

        verify(customerRepository, never()).save(any(Customer.class));
    }
}